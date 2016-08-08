package co.bstorm.aleksa.recipes.util;

import java.util.HashSet;
import java.util.Set;

import co.bstorm.aleksa.recipes.constants.DbColumns;
import co.bstorm.aleksa.recipes.pojo.TagCategory;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by aleksa on 8/7/16.
 *
 * Contains info related to filtering results
 */
public class FilterUtils {

    // Stores the filter info as filter tag IDs
    public static Set<Integer> filterTagIds = new HashSet<>();

    /**
     * Searches for all tag categories that have a Tag from them selected as a filter
     * @return set of all category IDs that have a selection
     */
    public static Set<Integer> getCategoriesWithSelection(){

        // If nothing is selected, there's nothing in this group also
        if (filterTagIds.isEmpty())
            return new HashSet<>();

        Realm realm = Realm.getDefaultInstance();

        // Find all cate
        RealmQuery<TagCategory> categories = realm.where(TagCategory.class);

        boolean firstItemPassed = false;

        for (Integer id: filterTagIds) {
            // Don't add .or() before adding the first item
            if (firstItemPassed)
                categories = categories.or();
            else
                firstItemPassed = true;

            categories.equalTo(DbColumns.TagCategory.TAGS + "." + DbColumns.Tag.ID, id);
        }

        RealmResults<TagCategory> tagCategories =  categories.findAll();

        realm.close();

        Set<Integer> tagIdsSet = new HashSet<>();

        for (TagCategory category :
                tagCategories) {
            tagIdsSet.add(category.getId());
        }

        return tagIdsSet;
    }

}
