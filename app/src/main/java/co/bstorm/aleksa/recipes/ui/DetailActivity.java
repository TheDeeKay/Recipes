package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.dummy.DummyData;
import co.bstorm.aleksa.recipes.pojo.Recipe;

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

        Recipe recipe = DummyData.dummyRecipes.get((int) recipeId);

        // TODO
        TextView recipeName = (TextView) findViewById(R.id.details_recipe_name);
        TextView ingredients = (TextView) findViewById(R.id.details_recipe_ingredients);
        TextView steps = (TextView) findViewById(R.id.details_recipe_instructions);

        recipeName.setText(recipe.getTitle());
        ingredients.setText(recipe.getIngredients().get(0).getPreferredMeasure());
        steps.setText(recipe.getSteps().get(0).getText());

        ImageView image = (ImageView) findViewById(R.id.details_image);
        Glide.with(this)
                .load(recipe.getImageUrl())
                .centerCrop()
                .into(image);
    }
}
