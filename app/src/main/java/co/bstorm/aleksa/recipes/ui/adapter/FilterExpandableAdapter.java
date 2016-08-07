package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.pojo.Tag;
import co.bstorm.aleksa.recipes.pojo.TagCategory;
import co.bstorm.aleksa.recipes.util.FilterUtils;
import io.realm.RealmResults;

/**
 * Created by aleksa on 8/6/16.
 *
 * This adapter is used for Filter activity
 * Displays TagCategories as groups and Tags as its children in an ExpandableListView
 */
public class FilterExpandableAdapter extends BaseExpandableListAdapter {

    RealmResults<TagCategory> tagCategories;
    LayoutInflater inflater;
    SharedPreferences sharedPref;

    public FilterExpandableAdapter(Context context, RealmResults<TagCategory> tagCategories){
        inflater = LayoutInflater.from(context);
        this.tagCategories = tagCategories;
        sharedPref = context.getSharedPreferences(Constants.FILTER_SHARED_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public int getGroupCount() {
        if (tagCategories != null)
            return tagCategories.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (tagCategories != null && tagCategories.get(groupPosition) != null
                && tagCategories.get(groupPosition).getTags() != null)
            return tagCategories.get(groupPosition).getTags().size();
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (tagCategories != null)
            return tagCategories.get(groupPosition);
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (tagCategories != null && tagCategories.get(groupPosition) != null
                && tagCategories.get(groupPosition).getTags() != null)
            return tagCategories.get(groupPosition).getTags().get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (tagCategories != null && tagCategories.get(groupPosition) != null)
            return tagCategories.get(groupPosition).getId();
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (tagCategories != null && tagCategories.get(groupPosition) != null
                && tagCategories.get(groupPosition).getTags() != null)
            return tagCategories.get(groupPosition).getTags().get(childPosition).getId();
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.filter_group, parent, false);
        }

        TextView groupName = (TextView) convertView.findViewById(R.id.filter_group_name);
        groupName.setText(tagCategories.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.filter_item, parent, false);
        }

        final Tag tag = tagCategories.get(groupPosition).getTags().get(childPosition);
        final String tagSharedPrefKey = Constants.TAG_SHARED_PREF_KEY + tag.getId();

        TextView itemName = (TextView) convertView.findViewById(R.id.filter_item_name);
        itemName.setText(tag.getName());

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.filter_item_check);
        checkBox.setChecked(FilterUtils.filterTagIds.contains(tag.getId()));

        // Set a check listener to the whole item
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.filter_item_root);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newState = !FilterUtils.filterTagIds.contains(tag.getId());

                // Add or remove the tag from selected filters
                if (newState)
                    FilterUtils.filterTagIds.add(tag.getId());
                else
                    FilterUtils.filterTagIds.remove(tag.getId());

                checkBox.setChecked(newState);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
