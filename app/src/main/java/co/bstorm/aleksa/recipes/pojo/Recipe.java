package co.bstorm.aleksa.recipes.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aleksa on 7/29/16.
 * TODO
 */
@Table(name = "Recipes")
public class Recipe extends Model {

    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @Expose
    @SerializedName("id")
    private int remoteId;
    @Column
    @Expose
    @SerializedName("title")
    private String title;
    @Column
    @Expose
    @SerializedName("image_file_name")
    private String imageUrl;
    @Column
    @Expose
    @SerializedName("difficulty")
    private int difficulty;
    @Column
    @Expose
    @SerializedName("default_serving_size")
    private int defaultServingSize;
    @Column
    @Expose
    @SerializedName("preparation_time")
    private int preparationTime;
    @Column
    @Expose
    @SerializedName("likes")
    private int likes;
    @Column
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

        @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
        @Expose
        @SerializedName("id")
        private int remoteId; // Refers to Component ID
        @Column
        @Expose
        @SerializedName("quantity")
        private float quantity;
        @Column
        @Expose
        @SerializedName("preferred_measure")
        private String preferredMeasure;

        @Column
        private int recipeId;

        public Ingredient() {
        }

        public int getRemoteId() {
            return remoteId;
        }

        public void setRemoteId(int id) {
            this.remoteId = id;
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
