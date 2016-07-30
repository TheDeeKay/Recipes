package co.bstorm.aleksa.recipes.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import co.bstorm.aleksa.recipes.api.retrofit.RecipesApiInterface;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.gson.MyDeserializer;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleksa on 7/29/16.
 *
 * Serves as a wrapper for Retrofit calls
 */
public class API {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Recipe.class, new MyDeserializer<Recipe>())
            .registerTypeAdapter(Recipe.Ingredient.class, new MyDeserializer<Recipe.Ingredient>())
            .registerTypeAdapter(Recipe.Tag.class, new MyDeserializer<Recipe.Tag>())
            .create();

    private static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(Constants.APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


    private static RecipesApiInterface recipesInterface = retrofit.create(RecipesApiInterface.class);

    public static Call<List<Recipe>> getAllRecipes(){
        return recipesInterface.listAllRecipes();
    }

    public static Call<List<Recipe>> getOffsetRecipes(int offset){
        return recipesInterface.listOffsetRecipes(offset);
    }

    public static Call<List<Recipe.Ingredient>> getAllIngredients(){
        return recipesInterface.listAllIngredients();
    }

    public static Call<List<Recipe.Tag>> getAllTags(){
        return recipesInterface.listAllTags();
    }
}
