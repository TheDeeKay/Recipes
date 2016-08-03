package co.bstorm.aleksa.recipes.constants;

/**
 * Created by aleksa on 8/3/16.
 */
public class DbColumns {

    public static class Recipe {

        public static final String REMOTE_ID = "remoteId";
        public static final String TITLE = "title";
        public static final String IMAGE_URL = "imageUrl";
        public static final String DIFFICULTY = "difficulty";
        public static final String SERVING_SIZE = "serving_size";
        public static final String PREP_TIME = "prepTime";
        public static final String LIKES = "likes";
        public static final String IS_FEATURED = "featured";

    }

    public static class Ingredient {

        public static final String COMPONENT_ID = "remoteId";
        public static final String QUANTITY = "quantity";
        public static final String PREFERRED_MEASURE = "measure";
        public static final String RECIPE_ID = "recipeId";

    }

    public static class RecipeTag {

        public static final String RECIPE_ID = "recipeId";
        public static final String TAG_ID = "tagId";

    }

    public static class Component {

        public static final String REMOTE_ID = "remoteId";
        public static final String NAME = "name";
        public static final String QUANTITY_TYPE = "quantityType";

    }

    public static class Step {

        public static final String REMOTE_ID = "remoteId";
        public static final String TEXT = "text";
        public static final String SEQUENCE_INDEX = "seqNum";
        public static final String TIMER = "timer";
        public static final String TIMER_NAME = "timerName";
        public static final String RECIPE_ID = "recipeId";

    }

    public static class Tag {

        public static final String REMOTE_ID = "remoteId";
        public static final String NAME = "name";
        public static final String TAG_CATEGORY_ID = "tagCategoryId";

    }

    public static class TagCategory {

        public static final String REMOTE_ID = "remoteId";
        public static final String NAME = "name";

    }
}
