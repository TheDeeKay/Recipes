package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.dummy.DummyCallback;
import co.bstorm.aleksa.recipes.dummy.DummyData;
import co.bstorm.aleksa.recipes.ui.adapter.RecipeAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView recipesList = (ListView) findViewById(R.id.recipes_list);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetails(id);
            }
        });

        DummyData.initDummyData(this, new DummyCallback() {
            @Override
            public void loadingFinished() {
                RecipeAdapter adapter = new RecipeAdapter(MainActivity.this, R.layout.recipe_list_item, DummyData.dummyRecipes);
                recipesList.setAdapter(adapter);
                Log.e("MainActivity", "Finished loading, has " + DummyData.dummyRecipes.size() + " items");
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
}
