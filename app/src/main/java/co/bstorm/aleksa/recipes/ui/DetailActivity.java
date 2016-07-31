package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.dummy.DummyData;

/**
 * Created by aleksa on 7/28/16.
 *
 * Used to display details of a single recipe
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Check which recipe we should display
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RECIPE_ID_EXTRA)) {

            long recipeId = intent.getExtras().getLong(Constants.RECIPE_ID_EXTRA);

            // TODO change this when DB is implemented
            initializeUI(recipeId);
        }
    }

    /** TODO this should also be changed when the DB is in
     * Initializes the UI components for the given recipe ID
     *
     * @param recipeId ID of the recipe whose details should be displayed
     */
    private void initializeUI(long recipeId){

        // TODO
        TextView recipeName = (TextView) findViewById(R.id.details_recipe_name);
        recipeName.setText(DummyData.dummyRecipes.get((int) recipeId).getTitle());


    }
}
