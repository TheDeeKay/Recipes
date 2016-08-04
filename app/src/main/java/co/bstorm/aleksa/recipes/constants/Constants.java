package co.bstorm.aleksa.recipes.constants;

import java.util.HashMap;

/**
 * Created by aleksa on 7/29/16.
 */
public class Constants {

    public static final String RECIPE_ID_EXTRA = "recipe_id";
    public static final String RECIPE_TITLE_EXTRA = "recipe_title";
    public static final String IMAGE_URL_EXTRA = "image_url";
    public static final String SERVINGS_EXTRA = "servings";


    public static class APIConstants {

        public static final String BASE_URL = "http://46.101.236.188/v1/";
    }

    public static class Measures {

        public static final String PREFERRED_MEASURE_REGULAR = "regular";
        public static final String PREFERRED_MEASURE_THOUSAND = "thousand";

        public static final String QUANTITY_TYPE_WEIGHT = "weight";
        public static final String WEIGHT_UNIT_GRAM = "g";
        public static final String WEIGHT_UNIT_KILOGRAM = "kg";

        public static final String QUANTITY_TYPE_VOLUME = "volume";
        public static final String VOLUME_UNIT = "ml";

        public static final String QUANTITY_TYPE_NUMBER = "number";
        public static final String NUMBER_UNIT = "";

        public static final HashMap<String, String> MEASURE_MAP = new HashMap<>();

        static {
            MEASURE_MAP.put(QUANTITY_TYPE_WEIGHT, WEIGHT_UNIT_GRAM);
            MEASURE_MAP.put(QUANTITY_TYPE_VOLUME, VOLUME_UNIT);
            MEASURE_MAP.put(QUANTITY_TYPE_NUMBER, NUMBER_UNIT);
        }

    }
}
