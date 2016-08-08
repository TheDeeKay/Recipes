package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksa on 7/30/16.
 *
 * Represents a tag listed in recipe. A tag belongs to a certain tag category
 */
public class Tag extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("tag_category_id")
    private int tagCategoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(int tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }
}
