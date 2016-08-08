package co.bstorm.aleksa.recipes.constants;

/**
 * Created by aleksa on 8/3/16.
 */
public class DbColumns {

    public static class Recipe {

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String IMAGE_URL = "imageUrl";
        public static final String DIFFICULTY = "difficulty";
        public static final String SERVING_SIZE = "defaultServingSize";
        public static final String PREP_TIME = "preparationTime";
        public static final String LIKES = "likes";
        public static final String STEPS = "steps";
        public static final String TAGS = "tags";
        public static final String INGREDIENTS = "ingredients";
        public static final String TITLE_LOWER = "titleLower";

    }

    public static class Ingredient {

        public static final String ID = "uniqueId";
        public static final String COMPONENT_ID = "componentId";
        public static final String QUANTITY = "quantity";
        public static final String PREFERRED_MEASURE = "preferredMeasure";
        public static final String RECIPE_ID = "recipeId";
        public static final String IN_CART = "inCart";

    }

    public static class Component {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUANTITY_TYPE = "quantityType";

    }

    public static class Step {

        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String SEQUENCE_INDEX = "sequenceIndex";
        public static final String TIMER = "timer";
        public static final String TIMER_NAME = "timerName";

    }

    public static class Tag {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TAG_CATEGORY_ID = "tagCategoryId";

    }

    public static class TagCategory {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String EN_NAME = "enName";
        public static final String COUNTRY_ID = "countryId";
        public static final String TAGS = "tags";

    }

    public static class RecipeTag {

        public static final String ID = "id";
    }

    public static class ShoppingItem {

        public static final String COMPONENT = "component";
        public static final String INGREDIENTS = "ingredients";
    }
}
