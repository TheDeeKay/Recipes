package co.bstorm.aleksa.recipes.constants;

import java.util.HashMap;

/**
 * Created by aleksa on 7/29/16.
 */
public class Constants {

    public static final String RECIPE_ID_EXTRA = "recipe_id";

    public static final int REQUEST_CODE_FILTER = 1000;

    public static final String FILTER_SHARED_PREF = "filterSharedPref";
    public static final String TAG_SHARED_PREF_KEY = "tag";

    public static final HashMap<Integer, String> DIFFICULTIES = new HashMap<>();
    static {
        DIFFICULTIES.put(1, "EASY"); // TODO extract to strings
        DIFFICULTIES.put(2, "MEDIUM");
        DIFFICULTIES.put(3, "HARD");
    }


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
        public static final String VOLUME_UNIT_MILLILITER = "ml";

        public static final String QUANTITY_TYPE_NUMBER = "number";
        public static final String NUMBER_UNIT = "";

        public static final HashMap<String, String> MEASURE_MAP = new HashMap<>();

        static {
            MEASURE_MAP.put(QUANTITY_TYPE_WEIGHT, WEIGHT_UNIT_GRAM);
            MEASURE_MAP.put(QUANTITY_TYPE_VOLUME, VOLUME_UNIT_MILLILITER);
            MEASURE_MAP.put(QUANTITY_TYPE_NUMBER, NUMBER_UNIT);
        }

    }
}
