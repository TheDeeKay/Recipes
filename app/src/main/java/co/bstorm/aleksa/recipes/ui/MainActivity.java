package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.api.API;
import co.bstorm.aleksa.recipes.api.FetchData;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.ui.adapter.RecipeListAdapter;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    Realm realm;
    RecipeListAdapter mAdapter;
    RealmChangeListener<RealmResults<Recipe>> changeListener;
    RealmResults<Recipe> recipes;

    // Used to unsubscribe from observables at the end of lifecycle
    CompositeSubscription cs;

    // Indicates whether we are currently loading more items
    boolean flagLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        cs = new CompositeSubscription();

        // Fetch all the data (recipes, tags and components) and add their subscriptions to the composite sub
        cs.add(FetchData.fetchDataFromObservable(API.getAllRecipes(), getApplicationContext()));
        cs.add(FetchData.fetchDataFromObservable(API.getAllComponents(), getApplicationContext()));
        cs.add(FetchData.fetchDataFromObservable(API.getAllTags(), getApplicationContext()));

        recipes = realm.where(Recipe.class).findAll();

        final ListView recipesList = (ListView) findViewById(R.id.recipes_list);
        mAdapter = new RecipeListAdapter(this, recipes);
        recipesList.setAdapter(mAdapter);

        // Set a footer view to be a simple progress bar
        // TODO make this disappear when there's no more items to load
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.progress_bar_background, null));
        recipesList.addFooterView(progressBar);

        // Monitor Realm data
        changeListener = new RealmChangeListener<RealmResults<Recipe>>() {
            @Override
            public void onChange(RealmResults<Recipe> element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        recipes.addChangeListener(changeListener);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetails(id);
            }
        });

        recipesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {

                if(totalItemCount != 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
                    if(!flagLoading) {
                        flagLoading = true;

                        // Get an observable for fetching offset recipes
                        Observable<ArrayList<Recipe>> observable = API.getOffsetRecipes(totalItemCount);
                        // Get an Observer interface for saving data to Realm
                        final Observer<ArrayList<Recipe>> observer =
                                FetchData.getObserverInterface(observable, getApplicationContext());

                        // Fetch data, store it to Realm, set flagLoading to false and store the subscription
                        cs.add(FetchData.prepareObservable(observable, getApplicationContext())
                                .subscribe(new Subscriber<ArrayList<Recipe>>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.e(TAG, "fetch completed");
                                        observer.onCompleted();
                                        // If we fetched anything, we just remove the loading flag
                                        // otherwise, we disable further updates by keeping the loading flag
                                        if (recipesList.getCount() != totalItemCount)
                                            flagLoading = false;
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "fetch error");
                                        observer.onError(e);
                                        flagLoading = false;
                                    }
                                    @Override
                                    public void onNext(ArrayList<Recipe> recipes) {
                                        Log.e(TAG, "fetch onNext");
                                        observer.onNext(recipes);
                                    }
                                }));
                    }
                }
            }
        });
    }

    /** TODO this will change when master/detail is implemented
     * Launches the details activity for the given recipe ID
     * @param id ID of the recipe whose details we want
     */
    private void launchDetails(long id){
        Intent detailsIntent = new Intent(this, DetailActivity.class);
        detailsIntent.putExtra(Constants.RECIPE_ID_EXTRA, id);
        startActivity(detailsIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cs.unsubscribe();

        recipes.removeChangeListener(changeListener);

        realm.close();
    }
}
