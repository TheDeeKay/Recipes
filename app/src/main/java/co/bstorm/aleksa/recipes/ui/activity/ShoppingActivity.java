package co.bstorm.aleksa.recipes.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.DbColumns;
import co.bstorm.aleksa.recipes.pojo.ShoppingItem;
import co.bstorm.aleksa.recipes.ui.adapter.ShoppingExpandableAdapter;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by aleksa on 8/8/16.
 */
public class ShoppingActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<ShoppingItem> shoppingItems;
    private RealmChangeListener<RealmResults<ShoppingItem>> componentChangeListener;
    private ShoppingExpandableAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        getSupportActionBar().setTitle(R.string.shopping_activity_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        shoppingItems = realm.where(ShoppingItem.class).isNotEmpty(DbColumns.ShoppingItem.INGREDIENTS).findAll();
        componentChangeListener = new RealmChangeListener<RealmResults<ShoppingItem>>() {
            @Override
            public void onChange(RealmResults<ShoppingItem> element) {
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }
        };
        shoppingItems.addChangeListener(componentChangeListener);

        ExpandableListView shoppingList = (ExpandableListView) findViewById(R.id.shopping_list_view);

        mAdapter = new ShoppingExpandableAdapter(this, shoppingItems);

        shoppingList.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        shoppingItems.removeChangeListener(componentChangeListener);
        realm.close();
    }
}
