package co.bstorm.aleksa.recipes.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import co.bstorm.aleksa.recipes.pojo.Ingredient;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.Tag;

/**
 * Created by aleksa on 7/30/16.
 */
public class MyDeserializer<T> implements JsonDeserializer<T>{
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String memberName;

        if (typeOfT instanceof Recipe)
            memberName = "recipes";
        else if (typeOfT instanceof Tag)
            memberName = "ingredients";
        else if (typeOfT instanceof Ingredient)
            memberName = "tag_categories";
        else{
            Log.e("Deserializer", "nada");
            return null;
        }

        Log.e("Deserializer", memberName);

        JsonElement element = json.getAsJsonObject().get(memberName);

        return new Gson().fromJson(json, typeOfT);
    }
}
