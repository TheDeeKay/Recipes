package co.bstorm.aleksa.recipes.dummy;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.gson.MyDeserializer;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.TagCategory;

/**
 * Created by aleksa on 7/30/16.
 */
public class DummyData {

    private static final String TAG = "DummyData";

    static Type recipeType = new TypeToken<ArrayList<Recipe>>(){}.getType();
    static Type componentType = new TypeToken<ArrayList<Component>>(){}.getType();
    static Type tagType = new TypeToken<ArrayList<TagCategory>>(){}.getType();

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(recipeType, new MyDeserializer<List<Recipe>>())
            .registerTypeAdapter(Recipe.class, new MyDeserializer<Recipe>())
            .registerTypeAdapter(componentType, new MyDeserializer<List<Component>>())
            .registerTypeAdapter(Component.class, new MyDeserializer<Component>())
            .registerTypeAdapter(tagType, new MyDeserializer<List<TagCategory>>())
            .registerTypeAdapter(TagCategory.class, new MyDeserializer<TagCategory>())
            .create();

    public static ArrayList<Recipe> dummyRecipes;// = new ArrayList<>();
    public static ArrayList<Component> dummyComponents;// = new ArrayList<>();
    public static ArrayList<TagCategory> dummyTagCategories;// = new ArrayList<>();


    public static void initDummyData(final Context context, final DummyCallback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                dummyRecipes = gson.fromJson(readRawFile(context, R.raw.recipes), recipeType);
                dummyComponents = gson.fromJson(readRawFile(context, R.raw.components), componentType);
                dummyTagCategories = gson.fromJson(readRawFile(context, R.raw.tags), tagType);

                Handler handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.loadingFinished();
                    }
                });
            }
        }).start();
    }


    private static String readRawFile(Context context, int fileId){
        try {

            InputStream is = context.getResources().openRawResource(fileId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null)
                buffer.append(line);

            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
