package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.pojo.Recipe;

/**
 * Created by aleksa on 7/30/16.
 *
 * Temporary adapter to represent Recipe data
 * Will be replaced by a CursorAdapter or sth
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    ArrayList<Recipe> data;
    LayoutInflater inflater;
    int resource;

    int screenWidth;

    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
        data = objects;
        inflater = LayoutInflater.from(context);
        this.resource = resource;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(resource, parent, false);
        }

        // Set the height of the view to be equal to screen width (essentially making it a square)
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new RelativeLayout.LayoutParams(screenWidth, screenWidth);
        } else {
            params.height = screenWidth;
        }
        view.setLayoutParams(params);

        view.setDrawingCacheEnabled(true);

        ImageView image = (ImageView) view.findViewById(R.id.list_item_image);
        TextView title = (TextView) view.findViewById(R.id.list_item_recipe_title);
        TextView difficulty = (TextView) view.findViewById(R.id.list_item_difficulty);
        TextView prepTime = (TextView) view.findViewById(R.id.list_item_prep_time);
        TextView likes = (TextView) view.findViewById(R.id.list_item_likes);

        Glide.with(view.getContext())
                .load(data.get(position).getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.oatmeal)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);

        title.setText(data.get(position).getTitle());
        difficulty.setText(String.valueOf(data.get(position).getDifficulty()));
        prepTime.setText(String.valueOf(data.get(position).getPreparationTime()));
        likes.setText(String.valueOf(data.get(position).getLikes()));

        return view;
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }
}
