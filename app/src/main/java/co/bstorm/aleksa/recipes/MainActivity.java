package co.bstorm.aleksa.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import co.bstorm.aleksa.recipes.constants.Constants;

public class MainActivity extends AppCompatActivity {

    public static String DUMMY_DATA[] = new String[]{
            "Pica",
            "Štrudla",
            "Ovsena kaša",
            "Pasulj"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView recipesList = (ListView) findViewById(R.id.recipes_list);

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.recipe_list_item, R.id.list_item_recipe_name, DUMMY_DATA);

        recipesList.setAdapter(adapter);

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
}
