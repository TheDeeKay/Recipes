package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 7/30/16.
 *
 * Temporary adapter to represent Recipe data
 * Will be replaced by a CursorAdapter or sth
 */
public class RecipeListAdapter extends SimpleCursorAdapter {

    private static final String TAG = "RecipeListAdapter";

    // TODO extract this to String constants (the column names)
    private static String from[] = new String[]{
            DbColumns.Recipe.TITLE,
            DbColumns.Recipe.DIFFICULTY,
            DbColumns.Recipe.PREP_TIME,
            DbColumns.Recipe.LIKES,
    };
    private static int to[] = new int[]{
            R.id.list_item_recipe_title,
            R.id.list_item_difficulty,
            R.id.list_item_prep_time,
            R.id.list_item_likes
    };

    Cursor data;
    LayoutInflater inflater;

    int screenWidth;

    public RecipeListAdapter(Context context, Cursor c){
        this(context, R.layout.recipe_list_item, c, from, to, 0);
    }

    public RecipeListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

        data = c;
        inflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.recipe_list_item, null);

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new RelativeLayout.LayoutParams(screenWidth, screenWidth);
        } else {
            params.height = screenWidth;
        }
        view.setLayoutParams(params);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        super.bindView(view, context, cursor);

        ImageView image = (ImageView) view.findViewById(R.id.list_item_image);

        String imageUrl = cursor.getString(cursor.getColumnIndex(DbColumns.Recipe.IMAGE_URL));

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);
    }
}
