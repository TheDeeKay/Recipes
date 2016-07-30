package co.bstorm.aleksa.recipes.api.retrofit;

import java.util.List;

import co.bstorm.aleksa.recipes.pojo.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aleksa on 7/29/16.
 *
 * Serves as a Retrofit interface for GETting recipe info
 */
public interface RecipesApiInterface {

    // Gets a list of all recipes
//    @GET("recipes/get-init-recipes")
    @GET("recipes/get-init-recipes")
    Call<List<Recipe>> listAllRecipes();

    // Gets an offset list of 50 recipes (starting with offset and ending with offset+49 indices)
    @GET("recipes/get-recipes")
    Call<List<Recipe>> listOffsetRecipes(@Query("offset") int offset);

    // Gets a list of all components
    @GET("components/get-components")
    Call<List<Recipe.Ingredient>> listAllIngredients();

    // Gets a list of all tags
    @GET("tags/get-tags")
    Call<List<Recipe.Tag>> listAllTags();
}
