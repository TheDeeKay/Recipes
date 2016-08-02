package co.bstorm.aleksa.recipes.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by aleksa on 8/2/16.
 */
@Table(name = "RecipeTags")
public class RecipeTag extends Model {

    @Column
    private int recipeId;

    @Column
    private int tagId;

    public RecipeTag() {
    }

    public RecipeTag(int recipeId, int tagId) {
        this.recipeId = recipeId;
        this.tagId = tagId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
