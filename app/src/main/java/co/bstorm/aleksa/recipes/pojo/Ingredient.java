package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksa on 7/29/16.
 *
 * Represents an ingredient listed in recipe (it's just a reference to a real component, together with amount)
 *
 * We generate a primary key to avoid storing same elements over and over (since Ingredient doesn't provide us with a natural UID)
 * We do this using Szudzik's function
 * TODO
 */
public class Ingredient extends RealmObject {

    @PrimaryKey
    private long uniqueId;
    @SerializedName("id")
    private int componentId;
    @SerializedName("quantity")
    private float quantity;
    @SerializedName("preferred_measure")
    private String preferredMeasure;

    private int recipeId;

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
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

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId() {
        uniqueId = recipeId >= componentId ? recipeId * recipeId + recipeId + componentId : recipeId + componentId * componentId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Component getComponent(){
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Component.class).equalTo("id", componentId).findFirst(); // TODO extract
    }
}
