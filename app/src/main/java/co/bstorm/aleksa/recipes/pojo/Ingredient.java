package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleksa on 7/29/16.
 * TODO
 */
public class Ingredient {

    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private float quantity;
    @SerializedName("preferred_measure")
    private String preferredMeasure;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getPreferredMeasure() {
        return preferredMeasure;
    }

    public void setPreferredMeasure(String preferredMeasure) {
        this.preferredMeasure = preferredMeasure;
    }
}
