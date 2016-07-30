package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleksa on 7/30/16.
 * TODO
 */
public class Component {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("quantity_type")
    private String quantityType;

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

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }
}
