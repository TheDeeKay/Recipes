package co.bstorm.aleksa.recipes.pojo;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 7/30/16.
 * TODO
 */
@Table(name = DbColumns.Component.TABLE_NAME, id = BaseColumns._ID)
public class Component extends Model{

    @Column(name = DbColumns.Component.REMOTE_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column(name = DbColumns.Component.NAME)
    @Expose
    @SerializedName("name")
    private String name;
    @Column(name = DbColumns.Component.QUANTITY_TYPE)
    @Expose
    @SerializedName("quantity_type")
    private String quantityType;

    public Component() {
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

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }
}
