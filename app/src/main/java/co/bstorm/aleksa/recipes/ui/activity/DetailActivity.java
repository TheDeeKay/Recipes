package co.bstorm.aleksa.recipes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.ui.adapter.DetailListAdapter;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by aleksa on 7/28/16.
 *
 * Used to display details of a single recipe
 */
public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private DetailListAdapter mAdapter;
    private ListView mDetails;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        mDetails = (ListView) findViewById(R.id.details_root);

        // Check which recipe we should display
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RECIPE_ID_EXTRA)) {

            long recipeId = intent.getExtras().getLong(Constants.RECIPE_ID_EXTRA);

            Recipe recipe = realm.where(Recipe.class).equalTo("id", recipeId).findFirst(); // TODO extract this

            mAdapter = new DetailListAdapter(this, recipe);
            mDetails.setAdapter(mAdapter);
        }

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.removeAllChangeListeners();
        realm.close();
    }
}
