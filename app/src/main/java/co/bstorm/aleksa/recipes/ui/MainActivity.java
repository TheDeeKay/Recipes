package co.bstorm.aleksa.recipes.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Select;

import java.util.ArrayList;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.api.API;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.dummy.DummyData;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.RecipeTag;
import co.bstorm.aleksa.recipes.ui.adapter.RecipeListAdapter;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO refactor this
        API.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "Finished first");
                        Recipe recipe = new Select().from(Recipe.class).orderBy("RANDOM()").executeSingle();
                        if (recipe != null)
                            Log.e("Finished inserting", recipe.getTitle() /*String.valueOf(recipe.getTags().get(0).getRemoteId())*/);
                        else
                            Log.e("Finished inserting", "Nope.js");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ArrayList<Recipe> recipes) {
                        Log.e("TAG", "Begun first");
                        Recipe recipe;
                        ActiveAndroid.beginTransaction();
                        try {
                            int size = recipes.size();
                            for (int i = 0; i < size; i++) {
                                recipe = recipes.get(i);

                                int tagSize = recipe.getTags().size();
                                for (int j = 0; j < tagSize; j++) {
                                    new RecipeTag(recipe.getRemoteId(), recipe.getTags().get(j).getRemoteId()).save();
                                    recipe.getTags().get(j).save();
                                }

                                int stepSize = recipe.getSteps().size();
                                for (int j = 0; j < stepSize; j++) {
                                    recipe.getSteps().get(j).setRecipeId(recipe.getRemoteId());
                                    recipe.getSteps().get(j).save();
                                }

                                int ingredientSize = recipe.getIngredients().size();
                                for (int j = 0; j < ingredientSize; j++) {
                                    recipe.getIngredients().get(j).setRecipeId(recipe.getRemoteId());
                                    recipe.getIngredients().get(j).save();
                                }

                                recipe.save();
                        }
                            ActiveAndroid.setTransactionSuccessful();
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                });

        API.getAllComponents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Component>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getAllComponents", "Finished second");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getAllComponents", "Error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Component> components) {
                        Log.e("TAG", "Begun second");
                        ActiveAndroid.beginTransaction();
                        try {
                            Component component;
                            int size = components.size();
                            for (int i = 0; i < size; i++) {
                                component = components.get(i);

                                component.save();
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        }
                        finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                });


        final ListView recipesList = (ListView) findViewById(R.id.recipes_list);

        // TODO extract those into constants (column names)
        mAdapter = new RecipeListAdapter(this, null);
        recipesList.setAdapter(mAdapter);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetails(id, mAdapter.getRecipeTitle(position), mAdapter.getImageUrl(position), mAdapter.getServings(position));
            }
        });

        getLoaderManager().initLoader(0, null, this);

        DummyData.initDummyData(this);
    }

    /** TODO this will change when master/detail is implemented
     * Launches the details activity for the given recipe ID
     * @param id ID of the recipe whose details we want
     */
    private void launchDetails(long id, String recipeTitle, String imageUrl, int servings){
        Intent detailsIntent = new Intent(this, DetailActivity.class);
        detailsIntent.putExtra(Constants.RECIPE_ID_EXTRA, id);
        detailsIntent.putExtra(Constants.RECIPE_TITLE_EXTRA, recipeTitle);
        detailsIntent.putExtra(Constants.IMAGE_URL_EXTRA, imageUrl);
        detailsIntent.putExtra(Constants.SERVINGS_EXTRA, servings);
        startActivity(detailsIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ContentProvider.createUri(Recipe.class, null),
                null, null, null, null
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
