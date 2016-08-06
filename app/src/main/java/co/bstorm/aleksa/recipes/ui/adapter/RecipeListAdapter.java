package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.ui.widget.SquareImageView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by aleksa on 7/30/16.
 *
 * An adapter to represent main Recipe data
 */
public class RecipeListAdapter extends RealmBaseAdapter<Recipe> {

    private static final String TAG = "RecipeListAdapter";

    // A string representing format in which preparation time should be displayed
    private static final String PREP_TIME_FORMAT = "%d min";

    LayoutInflater inflater;

    int screenWidth;

    public RecipeListAdapter(Context context, OrderedRealmCollection<Recipe> data) {
        super(context, data);
        inflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.recipe_list_item, null);

            ListView.LayoutParams params = (ListView.LayoutParams) convertView.getLayoutParams();
            if (params == null) {
                params = new ListView.LayoutParams(screenWidth, screenWidth);
            } else {
                params.height = screenWidth;
            }
            convertView.setLayoutParams(params);

            holder = new ViewHolder();

            holder.titleView = (TextView) convertView.findViewById(R.id.list_item_recipe_title);
            holder.likesView = (TextView) convertView.findViewById(R.id.list_item_likes);
            holder.difficultyView = (TextView) convertView.findViewById(R.id.list_item_difficulty);
            holder.prepTimeView = (TextView) convertView.findViewById(R.id.list_item_prep_time);
            holder.imageView = (SquareImageView) convertView.findViewById(R.id.list_item_image);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Recipe recipe = adapterData.get(position);

        holder.titleView.setText(recipe.getTitle());
        holder.difficultyView.setText(Constants.DIFFICULTIES.get(recipe.getDifficulty()));
        holder.prepTimeView.setText(String.format(PREP_TIME_FORMAT, recipe.getPreparationTime()));
        holder.likesView.setText(String.valueOf(recipe.getLikes()));
        Glide.with(context)
                .load(recipe.getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.imageView);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return adapterData.get(position).getId();
    }

    private static class ViewHolder {
        SquareImageView imageView;
        TextView difficultyView;
        TextView titleView;
        TextView prepTimeView;
        TextView likesView;
    }
}
