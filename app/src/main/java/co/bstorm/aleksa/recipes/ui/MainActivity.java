package co.bstorm.aleksa.recipes.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG = "MainActivity";

    private Realm realm;
    private RealmChangeListener<RealmResults<Recipe>> changeListener;
    private RealmResults<Recipe> recipes;

    private RecipeListAdapter mAdapter;
    private ListView mRecipesList;

    private Menu mMenu;

    // Used to unsubscribe from observables at the end of lifecycle
    private CompositeSubscription cs;

    // Indicates whether we are currently loading more items
    private boolean flagLoading = false;

    // Indicates whether we are currently using the search query
    // Used to disable new fetching on scroll down while using search
    private boolean queryActive = false;

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

        mRecipesList = (ListView) findViewById(R.id.recipes_list);
        mAdapter = new RecipeListAdapter(this, recipes);
        mRecipesList.setAdapter(mAdapter);

        // Set a footer view to be a simple progress bar
        // TODO make this disappear when there's no more items to load
        final ProgressBar progressBar = new ProgressBar(this);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.progress_bar_background, null));

        // Monitor Realm data
        changeListener = new RealmChangeListener<RealmResults<Recipe>>() {
            @Override
            public void onChange(RealmResults<Recipe> element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        recipes.addChangeListener(changeListener);

        mRecipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetails(id);
            }
        });

        mRecipesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {

                if(totalItemCount != 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
                    if(!flagLoading && !queryActive) {
                        flagLoading = true;

                        mRecipesList.addFooterView(progressBar);

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
                                        if (mRecipesList.getCount() -1 > totalItemCount)
                                            flagLoading = false;
                                        // Remove the progress
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mRecipesList.removeFooterView(progressBar);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        mMenu = menu;

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.main_search));

        mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(false);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cs.unsubscribe();

        recipes.removeChangeListener(changeListener);

        realm.close();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mMenu != null)
            mMenu.findItem(R.id.main_search).getActionView().clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        doQuery(newText);

        return true;
    }

    private void doQuery(String newText){
        if (newText != null && newText.length() > 0) {
            queryActive = true;
            String query = newText.toLowerCase();
            String queryInitialUpper;
            if (query.length() > 1)
                queryInitialUpper = query.substring(0, 1).toUpperCase() + query.substring(1);
            else
                queryInitialUpper = query.toUpperCase();

            // A hack since Realm doesn't support case insensitive search for non-english locales
            RealmResults<Recipe> queryResults = recipes.where().contains("title", query)
                    .or().contains("title", queryInitialUpper).findAll();

            mAdapter.updateData(queryResults);
        }
        else {
            mAdapter.updateData(recipes);
            queryActive = false;
        }
    }
}
