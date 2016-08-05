package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.api.API;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.pojo.Ingredient;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.ui.adapter.RecipeListAdapter;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    Realm realm;
    RecipeListAdapter mAdapter;
    RealmChangeListener<RealmResults<Recipe>> changeListener;
    RealmResults<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        API.getAllRecipes()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Recipe>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Successfully finished fetch and insert");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Encountered error during fetch/insert", e);
                    }

                    @Override
                    public void onNext(ArrayList<Recipe> recipes) {
                        Realm realm = Realm.getInstance(new RealmConfiguration.Builder(MainActivity.this).build());
                        realm.beginTransaction();
                        for (Recipe recipe :
                                recipes) {
                            for (Ingredient ingredient :
                                    recipe.getIngredients()) {
                                ingredient.setRecipeId(recipe.getId());
                                ingredient.setUniqueId();
                            }
                        }
                        realm.copyToRealmOrUpdate(recipes);
                        realm.commitTransaction();
                        realm.close();
                    }
                });

        recipes = realm.where(Recipe.class).findAll();

        ListView recipesList = (ListView) findViewById(R.id.recipes_list);
        mAdapter = new RecipeListAdapter(this, recipes);
        recipesList.setAdapter(mAdapter);

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

        recipes.removeChangeListener(changeListener);

        realm.close();
    }
}
