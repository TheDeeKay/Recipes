package co.bstorm.aleksa.recipes.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.activeandroid.Cache;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.List;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.constants.DbColumns;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.Step;
import co.bstorm.aleksa.recipes.ui.adapter.DetailListAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by aleksa on 7/28/16.
 *
 * Used to display details of a single recipe
 */
public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private DetailListAdapter mDetailsAdapter;

    private ListView mDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetails = (ListView) findViewById(R.id.details_root);

        // Check which recipe we should display
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.RECIPE_ID_EXTRA)) {

            long recipeId = intent.getExtras().getLong(Constants.RECIPE_ID_EXTRA);
            String recipeTitle = intent.getExtras().getString(Constants.RECIPE_TITLE_EXTRA);
            String imageUrl = intent.getExtras().getString(Constants.IMAGE_URL_EXTRA);

            int servings = intent.getExtras().getInt(Constants.SERVINGS_EXTRA);
            String servingSize = String.format(getString(R.string.serving_size_format), servings);

            // TODO change this when DB is implemented
            initializeUI(recipeId, recipeTitle, imageUrl, servingSize);
        }
    }

    /** TODO this should also be changed when the DB is in
     * Initializes the UI components for the given recipe ID
     *
     * @param recipeId ID of the recipe whose details should be displayed
     */
    private void initializeUI(final long recipeId, final String recipeTitle, final String imageUrl, final String servingSize){

        List<Recipe.Ingredient> list =  new Select().from(Recipe.Ingredient.class).where(DbColumns.Ingredient.RECIPE_ID + " = ?", 2379).execute();
        for (Recipe.Ingredient item:
             list) {
            Log.e(TAG, String.valueOf(item.getQuantity()));
        }

        Observable<Cursor> ingredientObservable = Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {

                From query = new Select()
                        .from(Recipe.Ingredient.class)
                        .innerJoin(Component.class)
                        .on(DbColumns.Ingredient.TABLE_NAME + "." + DbColumns.Ingredient.COMPONENT_ID +
                                " = " +DbColumns.Component.TABLE_NAME + "." + DbColumns.Component.REMOTE_ID)
                        .where(DbColumns.Ingredient.RECIPE_ID + " = ?", recipeId);

                Cursor cursor = Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());

                subscriber.onNext(cursor);
            }
        });

        Observable<Cursor> stepObservable = Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                From query = new Select()
                        .from(Step.class)
                        .where(DbColumns.Step.RECIPE_ID + " = ?", recipeId)
                        .orderBy(DbColumns.Step.SEQUENCE_INDEX + " ASC");

                Cursor cursor = Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());

                if (cursor == null || cursor.getCount() == 0)
                    subscriber.onError(new Throwable());
                else
                    subscriber.onNext(cursor);
            }
        });

        Observable.zip(
                ingredientObservable, stepObservable, new Func2<Cursor, Cursor, DetailListAdapter>() {
                    @Override
                    public DetailListAdapter call(Cursor cursor, Cursor cursor2) {
                        return new DetailListAdapter(DetailActivity.this, cursor, cursor2, recipeTitle, imageUrl, servingSize);
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DetailListAdapter>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading from DB", e);
                        DetailActivity.this.finish();
                    }

                    @Override
                    public void onNext(DetailListAdapter detailListAdapter) {
                        mDetailsAdapter = detailListAdapter;
                        mDetails.setAdapter(detailListAdapter);
                    }
                });
    }
}
