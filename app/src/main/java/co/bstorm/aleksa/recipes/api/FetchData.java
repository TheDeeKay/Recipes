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

    private static Func1<Observable<? extends Throwable>, Observable<?>> retryWhenFunc = null;

    // Used to avoid duplicating network error toasts
    private static Toast toast = null;

    /**
     * Fetches data using a given observable
     *
     * @param observable An observable that fetches and emits fetched data
     * @param context A context used to observe network changes and get Realm instance
     * @return Subscription that can be used to cancel retry attempts
     */
    public static <T extends RealmObject> Subscription
    fetchDataFromObservable(Observable<ArrayList<T>> observable, final Context context) {
        return observable
                .retryWhen(getRetryWhenFunc(context))
//                .retry()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<T>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Successfully finished fetch and insert of recipes");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Encountered error during fetch/insert", e);
                        if (e instanceof ConnectException)
                            makeMissingConnectionToast(context);
                    }

                    @Override
                    public void onNext(ArrayList<T> items) {
                        Realm realm = Realm.getInstance(new RealmConfiguration.Builder(context).build());
                        realm.beginTransaction();

                        // If we're getting recipes, we need to initialize Ingredient primary key
                        // TODO if there's time, extract this somewhere for better modularity
                        if (items != null && items.size() > 0 && (items.get(0).getClass().equals(Recipe.class)))
                            for (Recipe recipe :
                                    (ArrayList<Recipe>)items) {
                                for (Ingredient ingredient :
                                        recipe.getIngredients()) {
                                    ingredient.setRecipeId(recipe.getId());
                                    ingredient.setUniqueId();
                                }
                            }
                        realm.copyToRealmOrUpdate(items);
                        realm.commitTransaction();
                        realm.close();
                    }
                });
    }

    /**
     * Displays a network error toast if there is not already another one
     */
    private static void makeMissingConnectionToast(Context context) {
        if (toast == null || toast.getView().getWindowVisibility() == View.INVISIBLE) {
            toast = Toast.makeText(context, context.getString(R.string.no_connection), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /** A function that's used to properly retry fetching data
     * Uses an observable that notifies us of network changes to decide when to trigger retry
     *
     * @param context the Context that is used to
     */
    private static Func1<Observable<? extends Throwable>, Observable<?>> getRetryWhenFunc(final Context context){

        if (retryWhenFunc == null)

            retryWhenFunc = new Func1<Observable<? extends Throwable>, Observable<?>>() {
                @Override
                public Observable<?> call(final Observable<? extends Throwable> observable) {

                    observable
                            .take(1)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    makeMissingConnectionToast(context);
                                }
                            });

                    return observable
                            // emit only when the error is due to no network connection
                            .filter(new Func1<Throwable, Boolean>() {
                                @Override
                                public Boolean call(Throwable throwable) {
                                    return throwable.getClass().equals(ConnectException.class);
                                }
                            })
                            .flatMap(new Func1<Throwable, Observable<?>>() {
                                @Override
                                public Observable<?> call(Throwable throwable) {
                                    return Observable.timer(5000, TimeUnit.MILLISECONDS);
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
