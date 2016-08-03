package co.bstorm.aleksa.recipes.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 7/30/16.
 */
@Table(name = "Tags")
public class Tag extends Model{

    @Column(name = DbColumns.Tag.REMOTE_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column(name = DbColumns.Tag.NAME)
    @Expose
    @SerializedName("name")
    private String name;
    @Column(name = DbColumns.Tag.TAG_CATEGORY_ID)
    @Expose
    @SerializedName("tag_category_id")
    private int tagCategoryId;

    public Tag() {
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

    public int getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(int tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }
}
