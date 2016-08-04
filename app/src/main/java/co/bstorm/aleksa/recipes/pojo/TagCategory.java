package co.bstorm.aleksa.recipes.pojo;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 7/30/16.
 */
@Table(name = DbColumns.TagCategory.TABLE_NAME, id = BaseColumns._ID)
public class TagCategory extends Model{

    @Column(name = DbColumns.TagCategory.REMOTE_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column(name = DbColumns.TagCategory.NAME)
    @Expose
    @SerializedName("name")
    private String name;
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
