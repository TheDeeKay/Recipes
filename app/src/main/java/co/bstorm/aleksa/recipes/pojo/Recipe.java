package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aleksa on 7/29/16.
 *
 * A POJO representing our recipe
 * TODO
 */
public class Recipe extends RealmObject{

    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("image_file_name")
    private String imageUrl;
    @SerializedName("difficulty")
    private int difficulty;
    @SerializedName("default_serving_size")
    private int defaultServingSize;
    @SerializedName("preparation_time")
    private int preparationTime;
    @SerializedName("likes")
    private int likes;
    @SerializedName("steps")
    private RealmList<Step> steps;
    @SerializedName("tags")
    private RealmList<RecipeTag> tags;
    @SerializedName("ingredients")
    private RealmList<Ingredient> ingredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDefaultServingSize() {
        return defaultServingSize;
    }

    public void setDefaultServingSize(int defaultServingSize) {
        this.defaultServingSize = defaultServingSize;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public RealmList<Step> getSteps() {
        return steps;
    }

    public void setSteps(RealmList<Step> steps) {
        this.steps = steps;
    }

    public RealmList<RecipeTag> getTags() {
        return tags;
    }

    public void setTags(RealmList<RecipeTag> tags) {
        this.tags = tags;
    }

    public RealmList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
