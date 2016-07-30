package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleksa on 7/29/16.
 * TODO
 */
public class Tag {
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
