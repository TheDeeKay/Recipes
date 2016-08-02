package co.bstorm.aleksa.recipes.libs.retrofit;

import java.util.ArrayList;

import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.Tag;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by aleksa on 7/29/16.
 *
 * Serves as a Retrofit interface for GETting recipe info
 */
public interface RecipesApiInterface {

    // Gets a list of all recipes
    @Headers("Accept-Language: SRB")
    @GET("recipes/get-init-recipes")
    Observable<ArrayList<Recipe>> listAllRecipes();

    // Gets an offset list of 50 recipes (starting with offset and ending with offset+49 indices)
    @Headers("Accept-Language: SRB")
    @GET("recipes/get-recipes")
    Observable<ArrayList<Recipe>> listOffsetRecipes(@Query("offset") int offset);

    // Gets a list of all components
    @Headers("Accept-Language: SRB")
    @GET("components/get-components")
    Observable<ArrayList<Component>> listAllIngredients();

    // Gets a list of all tags
    @Headers("Accept-Language: SRB")
    @GET("tags/get-tags")
    Observable<ArrayList<Tag>> listAllTags();
}