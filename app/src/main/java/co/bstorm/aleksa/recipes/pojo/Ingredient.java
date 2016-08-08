package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.constants.DbColumns;
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
        Component comp =  realm.where(Component.class).equalTo(DbColumns.Component.ID, componentId).findFirst();
        realm.close();
        return comp;
    }

    // Returns a formatted amount (<number> [unit])
    public String getFormattedAmount(){
        if (quantity == ((int) quantity))
            return String.format("%d %s", quantity != 0 ? (int)quantity : 1, getUnit(preferredMeasure, getComponent().getQuantityType()));
        else
            return String.format("%f.2 %s", quantity != 0 ? quantity : 1, getUnit(preferredMeasure, getComponent().getQuantityType()));
    }

    // Gets a preferred unit for the given quantity type and preferred measure
    private String getUnit (String preferredMeasure, String quantityType){
        if (preferredMeasure == null || quantityType == null)
            return "";

        if (quantityType.equals(Constants.Measures.QUANTITY_TYPE_NUMBER))
            return "";

        // Preferred measure "thousand" in weight means milliliters
        if (quantityType.equals(Constants.Measures.QUANTITY_TYPE_VOLUME)
                && preferredMeasure.equals(Constants.Measures.PREFERRED_MEASURE_THOUSAND))
            return Constants.Measures.VOLUME_UNIT_MILLILITER;

        if (preferredMeasure.equals(Constants.Measures.PREFERRED_MEASURE_REGULAR)) {
            return Constants.Measures.MEASURE_MAP.get(quantityType);
        }
        // Preferred measure "thousand" in weight means grams
        else if (preferredMeasure.equals(Constants.Measures.PREFERRED_MEASURE_THOUSAND)
                && quantityType.equals(Constants.Measures.QUANTITY_TYPE_WEIGHT)){
            return Constants.Measures.WEIGHT_UNIT_GRAM;
        }
        else {
            return preferredMeasure;
        }
    }
}
