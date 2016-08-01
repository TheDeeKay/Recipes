package co.bstorm.aleksa.recipes.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.bstorm.aleksa.recipes.api.retrofit.RecipesApiInterface;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.gson.MyDeserializer;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.Tag;
import co.bstorm.aleksa.recipes.pojo.TagCategory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleksa on 7/29/16.
 *
 * Serves as a wrapper for Retrofit calls
 */
public class API {

    private static Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
    private static Type componentListType = new TypeToken<ArrayList<Component>>(){}.getType();
    private static Type tagListType = new TypeToken<ArrayList<TagCategory>>(){}.getType();

    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Recipe.class, new MyDeserializer<Recipe>())
            .registerTypeAdapter(recipeListType, new MyDeserializer<List<Recipe>>())
            .registerTypeAdapter(Component.class, new MyDeserializer<Component>())
            .registerTypeAdapter(componentListType, new MyDeserializer<List<Component>>())
            .registerTypeAdapter(TagCategory.class, new MyDeserializer<TagCategory>())
            .registerTypeAdapter(tagListType, new MyDeserializer<List<TagCategory>>())
            .create();

    private static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(Constants.APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


    private static RecipesApiInterface recipesInterface = retrofit.create(RecipesApiInterface.class);

    public static Call<ArrayList<Recipe>> getAllRecipes(){
        return recipesInterface.listAllRecipes();
    }

    public static Call<ArrayList<Recipe>> getOffsetRecipes(int offset){
        return recipesInterface.listOffsetRecipes(offset);
    }

    public static Call<ArrayList<Component>> getAllIngredients(){
        return recipesInterface.listAllIngredients();
    }

    public static Call<ArrayList<Tag>> getAllTags(){
        return recipesInterface.listAllTags();
    }
}
