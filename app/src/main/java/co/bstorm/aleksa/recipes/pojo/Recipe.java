package co.bstorm.aleksa.recipes.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksa on 7/29/16.
 * TODO
 */
public class Recipe {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("image_file_name")
    private String imageUrl;
    @SerializedName("image_size")
    private String imageSize;
    @SerializedName("difficulty")
    private int difficulty;
    @SerializedName("default_serving_size")
    private int defaultServingSize;
    @SerializedName("preparation_time")
    private int preparationTime;
    @SerializedName("likes")
    private int likes;
    @SerializedName("is_featured")
    private int isFeatured;
    @SerializedName("steps")
    private List<Step> steps = new ArrayList<Step>();
    @SerializedName("tags")
    private List<Tag> tags = new ArrayList<Tag>();
    @SerializedName("ingredients")
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

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

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public int getImageWidth(){
        return Integer.parseInt(imageSize.split("x")[0]);
    }

    public int getImageHeight(){
        return Integer.parseInt(imageSize.split("x")[1]);
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
    public static class Ingredient {

        @SerializedName("id")
        private int id;
        @SerializedName("quantity")
        private float quantity;
        @SerializedName("preferred_measure")
        private String preferredMeasure;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }

    /**
     * Created by aleksa on 7/29/16.
     * TODO
     */
    public static class Tag {
        @SerializedName("id")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
