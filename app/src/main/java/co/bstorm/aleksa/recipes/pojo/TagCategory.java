package co.bstorm.aleksa.recipes.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksa on 7/30/16.
 */
@Table(name = "TagCategories")
public class TagCategory extends Model{

    @Column
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column
    @Expose
    @SerializedName("name")
    private String name;
    @Column
    @Expose
    @SerializedName("tags")
    private List<Tag> tags = new ArrayList<>();

    public TagCategory() {
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int id) {
        this.remoteId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
