package co.bstorm.aleksa.recipes.libs.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.TagCategory;

/**
 * Created by aleksa on 7/30/16.
 */
public class MyDeserializer<T> implements JsonDeserializer<T>{

    static Type recipeType = new TypeToken<ArrayList<Recipe>>(){}.getType();
    static Type componentType = new TypeToken<ArrayList<Component>>(){}.getType();
    static Type tagType = new TypeToken<ArrayList<TagCategory>>(){}.getType();

    protected String memberName = null;

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement element = json;

        if (typeOfT.equals(Recipe.class) || typeOfT.equals(recipeType))
            memberName = "recipes";
        else if (typeOfT.equals(Component.class) || typeOfT.equals(componentType))
            memberName = "ingredients";
        else if (typeOfT.equals(TagCategory.class) || typeOfT.equals(tagType))
            memberName = "tag_categories";

        if (memberName != null && json.getAsJsonObject().has(memberName))
            element = json.getAsJsonObject().get(memberName);

        return new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create()
                .fromJson(element, typeOfT);
    }


}
