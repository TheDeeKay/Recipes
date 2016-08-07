package co.bstorm.aleksa.recipes.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.HashSet;
import java.util.Set;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.pojo.TagCategory;
import co.bstorm.aleksa.recipes.ui.adapter.FilterExpandableAdapter;
import co.bstorm.aleksa.recipes.util.FilterUtils;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by aleksa on 8/6/16.
 */
public class FilterActivity extends AppCompatActivity{

    private Realm realm;
    private FilterExpandableAdapter mAdapter;

    // Used to memorize which filters were on when we entered the activity, so we could restore them
    // if the user presses back (which means they wish to cancel newest changes)
    private HashSet<Integer> filtersOnEnter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filtersOnEnter = new HashSet<>(FilterUtils.filterTagIds);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.filter_activity_title);

        realm = Realm.getDefaultInstance();

        ExpandableListView filterList = (ExpandableListView) findViewById(R.id.filter_list_view);

        mAdapter = new FilterExpandableAdapter(this, realm.where(TagCategory.class).findAll());
        filterList.setAdapter(mAdapter);

        Button applyFiltersButton = (Button) findViewById(R.id.apply_filters);
        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just finish the activity
                finish();
            }
        });

        // Find all categories with selected filters

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                mAdapter.notifyDataSetChanged();
            }
        });

        if (!FilterUtils.filterTagIds.isEmpty()) {

            Set<Integer> set = FilterUtils.getCategoriesWithSelection();

            for (int i = 0, size = mAdapter.getGroupCount(); i < size; i++) {
                // If this group has a selected item, expand it
                TagCategory tagCat = (TagCategory) mAdapter.getGroup(i);
                if (set.contains(tagCat.getId()))
                    filterList.expandGroup(i);
            }
        }
    }

    @Override
    public void onBackPressed() {

        FilterUtils.filterTagIds = new HashSet<>(filtersOnEnter);

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.removeAllChangeListeners();
        realm.close();
    }
}
