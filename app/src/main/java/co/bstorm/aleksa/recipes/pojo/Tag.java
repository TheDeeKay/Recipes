package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleksa on 7/30/16.
 */
public class Tag {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("tag_category_id")
    private String tagCategoryId;

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

    public String getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(String tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }
}