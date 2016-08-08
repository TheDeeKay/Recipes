package co.bstorm.aleksa.recipes.api;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.pojo.Ingredient;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by aleksa on 8/5/16.
 *
 * A wrapper class for fetching data from the network
 * Each method returns an observable that we just need to subscribe to
 *
 * Each observable is designed to retry connecting in case of network failure, by listening to changes
 */
public class FetchData {

    private static final String TAG = "FetchData";

    private static final int MAX_RETRIES = 12;

    // Use this as a form of singleton since we always use the same retryWhen strategy
    private static Func1<Observable<? extends Throwable>, Observable<?>> retryWhenFunc = null;

    // Used to avoid duplicating network error toasts
    private static Toast toast = null;

    /**
     * Fetches data using a given observable and returns its subscription
     *
     * @param observable An observable that fetches and emits fetched data
     * @param context A context used to observe network changes and get Realm instance
     * @return Subscription that can be used to cancel retry attempts
     */
    public static <T extends RealmObject> Subscription
    fetchDataFromObservable(Observable<ArrayList<T>> observable, final Context context) {

        final Observer<ArrayList<T>> observer = getObserverInterface(observable, context);

        return prepareObservable(observable, context)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<T>>() {
                    @Override
                    public void onCompleted() {
                        observer.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onNext(ArrayList<T> items) {
                        observer.onNext(items);
                    }
                });
    }

    /**
     * Prepares an observable by specifying retry strategy and threading
     *
     * @param observable The observable to prepare
     * @param context A context used with the observable
     * @return Prepared observable
     */
    public static <T extends RealmObject> Observable<ArrayList<T>> prepareObservable(Observable<ArrayList<T>> observable, Context context){
        return observable
                .retryWhen(getRetryWhenFunc(context))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }

    /**
     * Displays an error toast if there is not already another Toast displaying
     */
    private static void makeErrorToast(Context context, String message) {
        if (toast == null || toast.getView().getWindowVisibility() == View.INVISIBLE) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Creates an Observer interface for inserting data into Realm
     *
     * @return the Observer with the right methods to insert data
     */
    public static <T extends RealmObject> Observer<ArrayList<T>> getObserverInterface(
            final Observable<ArrayList<T>> observable, final Context context){
        return new Observer<ArrayList<T>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Successfully finished fetch and insert");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Encountered error during fetch/insert",e);
                if (e instanceof ConnectException)
                    makeErrorToast(context, context.getString(R.string.no_connection));
                else
                    makeErrorToast(context, context.getString(R.string.error_fetching));
            }

            @Override
            public void onNext(ArrayList<T> items) {
                Realm realm = Realm.getInstance(new RealmConfiguration.Builder(context).build());
                realm.beginTransaction();

                // If we're getting recipes, we need to initialize Ingredient primary key
                if (items != null && items.size() > 0 && (items.get(0).getClass().equals(Recipe.class))) {
                    for (Recipe recipe :
                            (ArrayList<Recipe>)items) {
                        recipe.setTitleLower(recipe.getTitle().toLowerCase());
                        for (Ingredient ingredient :
                                recipe.getIngredients()) {
                            ingredient.setRecipeId(recipe.getId());
                            ingredient.setUniqueId();
                        }
                    }
                }
                realm.copyToRealmOrUpdate(items);
                realm.commitTransaction();
                realm.close();
                Log.e(TAG, "Successfully fetched and inserted/updated " + items.size() + " items");
            }
        };
    }

    /** A function that's used to properly retry fetching data
     * Uses an observable that notifies us of network changes to decide when to trigger retry
     *
     * @param context the Context that is used to display Toast (the no-network error)
     */
    private static Func1<Observable<? extends Throwable>, Observable<?>> getRetryWhenFunc(final Context context){

        if (retryWhenFunc == null)

            retryWhenFunc = new Func1<Observable<? extends Throwable>, Observable<?>>() {
                @Override
                public Observable<?> call(final Observable<? extends Throwable> observable) {

                    // Use this to display the no-network message (because onError won't trigger when there's retry)
                    observable
                            .filter(new Func1<Throwable, Boolean>() {
                                @Override
                                public Boolean call(Throwable throwable) {
                                    return throwable.getClass().equals(ConnectException.class);
                                }
                            })
                            .take(1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    makeErrorToast(context, context.getString(R.string.no_connection));
                                }
                            });

                    return observable
                            .flatMap(new Func1<Throwable, Observable<?>>() {
                                int retries = 0;
                                @Override
                                public Observable<?> call(Throwable throwable) {

                                    // If there is no network, start retrying
                                    if (throwable.getClass().equals(ConnectException.class)) {
                                        if (retries < MAX_RETRIES) {
                                            retries++;
                                            return Observable.timer(5000, TimeUnit.MILLISECONDS);
                                        }
                                    }
                                    // else, just return the error
                                    return Observable.error(throwable);
                                }
                            });
                    // TODO this other approach could be much better but this is not working for some reason
                    // transform to an observable that emits stuff on network connection changes
//                            .flatMap(new Func1<Throwable, Observable<?>>() {
//                                @Override
//                                public Observable<?> call(Throwable throwable) {
//                                    if (throwable.getClass().equals(ConnectException.class)) {
//                                        Observable<Connectivity> obs =  ReactiveNetwork.observeNetworkConnectivity(context)
//                                                // emit only when there is actual connection
//                                                .filter(Connectivity.hasState(NetworkInfo.State.CONNECTED));
//                                        obs.subscribe(new Action1<Connectivity>() {
//                                            @Override
//                                            public void call(Connectivity connectivity) {
//                                                Log.e(TAG, "CONNECTION RESTORED");
//                                            }
//                                        });
//                                        return obs;
//                                    }
//                                    return Observable.error(throwable);
//                                }
//                            });
                }
            };

        return retryWhenFunc;
    }
}
