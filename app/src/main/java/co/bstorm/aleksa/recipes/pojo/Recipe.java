package co.bstorm.aleksa.recipes.pojo;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 7/29/16.
 * TODO
 */
@Table(name = "Recipes", id = BaseColumns._ID)
public class Recipe extends Model {

    @Column(name = DbColumns.Recipe.REMOTE_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column(name = DbColumns.Recipe.TITLE)
    @Expose
    @SerializedName("title")
    private String title;
    @Column(name = DbColumns.Recipe.IMAGE_URL)
    @Expose
    @SerializedName("image_file_name")
    private String imageUrl;
    @Column(name = DbColumns.Recipe.DIFFICULTY)
    @Expose
    @SerializedName("difficulty")
    private int difficulty;
    @Column(name = DbColumns.Recipe.SERVING_SIZE)
    @Expose
    @SerializedName("default_serving_size")
    private int defaultServingSize;
    @Column(name = DbColumns.Recipe.PREP_TIME)
    @Expose
    @SerializedName("preparation_time")
    private int preparationTime;
    @Column(name = DbColumns.Recipe.LIKES)
    @Expose
    @SerializedName("likes")
    private int likes;
    @Column(name = DbColumns.Recipe.IS_FEATURED)
    @Expose
    @SerializedName("is_featured")
    private int isFeatured;
    @Expose
    @SerializedName("steps")
    private List<Step> steps;
    @Expose
    @SerializedName("tags")
    private List<Tag> tags;
    @Expose
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public Recipe() {
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int id) {
        this.remoteId = id;
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

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Created by aleksa on 7/29/16.
     * TODO
     */
    @Table(name = "Ingredients")
    public static class Ingredient extends Model{

        @Column(name = DbColumns.Ingredient.COMPONENT_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
        @Expose
        @SerializedName("id")
        private int componentId;
        @Column(name = DbColumns.Ingredient.QUANTITY)
        @Expose
        @SerializedName("quantity")
        private float quantity;
        @Column(name = DbColumns.Ingredient.PREFERRED_MEASURE)
        @Expose
        @SerializedName("preferred_measure")
        private String preferredMeasure;

        @Column(name = DbColumns.Ingredient.RECIPE_ID)
        private int recipeId;

        public Ingredient() {
        }

        public int getRemoteId() {
            return componentId;
        }

        public void setRemoteId(int id) {
            this.componentId = id;
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

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }
    }

}
