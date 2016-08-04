package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.support.v4.widget.CursorAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.constants.DbColumns;
import co.bstorm.aleksa.recipes.ui.widget.SquareImageView;

/**
 * Created by aleksa on 7/30/16.
 *
 * An adapter to represent main Recipe data
 */
public class RecipeListAdapter extends CursorAdapter {

    private static final String TAG = "RecipeListAdapter";

    private static String from[] = new String[]{
            DbColumns.Recipe.TITLE,
            DbColumns.Recipe.LIKES,
    };
    private static int to[] = new int[]{
            R.id.list_item_recipe_title,
            R.id.list_item_likes
    };

    Cursor data;
    LayoutInflater inflater;

    int screenWidth;

    public RecipeListAdapter(Context context, Cursor c){
        this(context, c, 0);
    }

    public RecipeListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        data = c;
        inflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public long getItemId(int position) {
        if (getCursor() == null)
            return 0;
        getCursor().moveToPosition(position);
        return getCursor().getLong(getCursor().getColumnIndex(DbColumns.Recipe.REMOTE_ID));
    }

    public String getRecipeTitle(int position){
        if (getCursor() == null)
            return "";
        getCursor().moveToPosition(position);
        return getCursor().getString(getCursor().getColumnIndex(DbColumns.Recipe.TITLE));
    }

    public String getImageUrl(int position){
        if (getCursor() == null)
            return "";
        getCursor().moveToPosition(position);
        return getCursor().getString(getCursor().getColumnIndex(DbColumns.Recipe.IMAGE_URL));
    }

    public int getServings(int position){
        if (getCursor() == null)
            return 0;
        getCursor().moveToPosition(position);
        return getCursor().getInt(getCursor().getColumnIndex(DbColumns.Recipe.SERVING_SIZE));
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

        SquareImageView image = (SquareImageView) view.findViewById(R.id.list_item_image);
        TextView difficulty = (TextView) view.findViewById(R.id.list_item_difficulty);
        TextView prepTime = (TextView) view.findViewById(R.id.list_item_prep_time);
        TextView likes = (TextView) view.findViewById(R.id.list_item_likes);
        TextView title = (TextView) view.findViewById(R.id.list_item_recipe_title);

        ViewHolder holder = new ViewHolder(image, difficulty, title, prepTime, likes);

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.prepTimeView
                .setText(cursor.getInt(cursor.getColumnIndex(DbColumns.Recipe.PREP_TIME)) + "min");

        String difficulty =
                Constants.DIFFICULTIES.get(
                        cursor.getInt(cursor.getColumnIndex(DbColumns.Recipe.DIFFICULTY)));

        holder.difficultyView.setText(difficulty);

        holder.titleView.setText(cursor.getString(cursor.getColumnIndex(DbColumns.Recipe.TITLE)));

        holder.likesView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(DbColumns.Recipe.LIKES))));

        String imageUrl = cursor.getString(cursor.getColumnIndex(DbColumns.Recipe.IMAGE_URL));

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.imageView);
    }

    private static class ViewHolder {
        SquareImageView imageView;
        TextView difficultyView;
        TextView titleView;
        TextView prepTimeView;
        TextView likesView;

        public ViewHolder(SquareImageView image, TextView difficulty, TextView title, TextView prepTime, TextView likes){
            imageView = image;
            difficultyView = difficulty;
            titleView = title;
            prepTimeView = prepTime;
            likesView = likes;
        }
    }
}
