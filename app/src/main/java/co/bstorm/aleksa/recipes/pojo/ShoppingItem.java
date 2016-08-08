package co.bstorm.aleksa.recipes.pojo;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by aleksa on 8/8/16.
 *
 * Represents an item in our shopping list (a component and ingredients)
 */
public class ShoppingItem extends RealmObject{

    private Component component;
    private RealmList<Ingredient> ingredients;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public RealmList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
