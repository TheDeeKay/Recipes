package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.pojo.Ingredient;
import co.bstorm.aleksa.recipes.pojo.ShoppingItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by aleksa on 8/8/16.
 *
 * Serves as an ExpandableListView adapter for shopping cart
 */
public class ShoppingExpandableAdapter extends BaseExpandableListAdapter {

    private RealmResults<ShoppingItem> shoppingItems;
    private LayoutInflater inflater;

    public ShoppingExpandableAdapter(Context context, RealmResults<ShoppingItem> shoppingItems){
        inflater = LayoutInflater.from(context);
        this.shoppingItems = shoppingItems;
    }

    @Override
    public int getGroupCount() {
        if (shoppingItems != null)
            return shoppingItems.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (shoppingItems != null && shoppingItems.get(groupPosition).getIngredients() != null) {

            return shoppingItems.get(groupPosition).getIngredients().size();
        }

        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (shoppingItems != null)
            return shoppingItems.get(groupPosition);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (shoppingItems != null && shoppingItems.get(groupPosition).getIngredients() != null) {
            return shoppingItems.get(groupPosition).getIngredients().get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (shoppingItems != null)
            return shoppingItems.get(groupPosition).getComponent().getId();
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (shoppingItems != null && shoppingItems.get(groupPosition).getIngredients() != null) {
            return shoppingItems.get(groupPosition).getIngredients().get(childPosition).getUniqueId();
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.shopping_group, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.shopping_group_name);
        name.setText(shoppingItems.get(groupPosition).getComponent().getName());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.shopping_item, null);
        }

        TextView amount = (TextView) convertView.findViewById(R.id.shopping_item_amount);

        final Ingredient ingredient = shoppingItems.get(groupPosition).getIngredients().get(childPosition);

        amount.setText(ingredient.getFormattedAmount());

        ImageButton shoppingButton = (ImageButton) convertView.findViewById(R.id.shopping_item_remove_button);

        shoppingButton.setImageResource(R.drawable.shopping_remove);

        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                shoppingItems.get(groupPosition).getIngredients().remove(ingredient);

                realm.commitTransaction();
                realm.close();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
