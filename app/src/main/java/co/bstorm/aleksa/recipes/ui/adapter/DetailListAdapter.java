package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.Constants;
import co.bstorm.aleksa.recipes.constants.DbColumns;

/**
 * Created by aleksa on 8/4/16.
 *
 * A monstrous adapter that takes 5 types of views and makes them all a single ListView (so it can be scrolled nicely)
 *
 * This is absolutely not the best way to go, but due to lack of time this has to stay
 * It handles way too many things and knows way too much (it doesn't get much more opposite to Jon Snow than this)
 */
public class DetailListAdapter extends CursorAdapter {

    private static final int INGREDIENT = 0;
    private static final int STEP = 1;
    private static final int IMAGE = 2;
    private static final int INGREDIENT_SEPARATOR = 3;
    private static final int STEP_SEPARATOR = 4;

    private LayoutInflater inflater;

    Cursor ingredients;
    Cursor steps;

    String imageUrl;
    String recipeTitle;
    String servingSize;

    public DetailListAdapter(Context context, Cursor ingredients, Cursor steps,
                             String recipeTitle, String imageUrl, String servingSize){
        super(context, ingredients, 0);
        inflater = LayoutInflater.from(context);

        this.ingredients = ingredients;
        this.steps = steps;

        this.imageUrl = imageUrl;
        this.recipeTitle = recipeTitle;
        this.servingSize = servingSize;
    }

    @Override
    public int getCount() {
        int stepsCount = 0;
        int ingredientsCount = 0;
        if (steps != null)
            stepsCount = steps.getCount();
        if (ingredients != null)
            ingredientsCount = ingredients.getCount();

        return stepsCount + ingredientsCount + 3;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    // Returns the type of our View to be drawn
    // The first item is the image with the title, the second segment is list of ingredients
    // And the final segment is the list of steps
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return IMAGE;
        if (position == 1)
            return INGREDIENT_SEPARATOR;
        if (position - 2 < ingredients.getCount())
            return INGREDIENT;
        if (position == 2 + ingredients.getCount())
            return STEP_SEPARATOR;
        return STEP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)){
            case INGREDIENT: {
                convertView = newIngredientView(parent.getContext(), ingredients, parent);
                ingredients.moveToPosition(position - 2);
                bindIgredientView(convertView, parent.getContext(), ingredients);
                return convertView;
            }
            case STEP: {
                convertView = newStepView(parent.getContext(), steps, parent);
                steps.moveToPosition(position - ingredients.getCount() - 3);
                bindStepView(convertView, parent.getContext(), steps);
                return convertView;
            }
            case IMAGE: {
                convertView = newImageView(parent.getContext(), null, parent);
                bindImageView(convertView, parent.getContext(), null);
                return convertView;
            }
            case INGREDIENT_SEPARATOR: {
                convertView = inflater.inflate(R.layout.ingredient_separator, null);
                ((TextView)convertView.findViewById(R.id.ingredient_separator_servings))
                        .setText(servingSize);
                return convertView;
            }
            case STEP_SEPARATOR: {
                convertView = inflater.inflate(R.layout.step_separator, null);
                return convertView;
            }
            default: return null;
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    private View newIngredientView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.ingredient_list_item, null);

        TextView amount = (TextView) view.findViewById(R.id.ingredient_item_amount);
        TextView component = (TextView) view.findViewById(R.id.ingredient_item_component);

        IngredientViewHolder holder = new IngredientViewHolder(amount, component);

        view.setTag(holder);

        return view;
    }

    private View newStepView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.step_list_item, null);

        TextView number = (TextView) view.findViewById(R.id.step_item_number);
        TextView description = (TextView) view.findViewById(R.id.step_item_description);

        StepViewHolder holder = new StepViewHolder(number, description);

        view.setTag(holder);

        return view;
    }

    private View newImageView(Context context, Cursor cursor, ViewGroup parent){
        View view = inflater.inflate(R.layout.detail_image_title, null);

        TextView title = (TextView) view.findViewById(R.id.details_recipe_name);
        ImageView image = (ImageView) view.findViewById(R.id.details_image);

        ImageViewHolder holder = new ImageViewHolder(title, image);

        view.setTag(holder);

        return view;
    }

    private void bindIgredientView(View view, Context context, Cursor cursor){
        IngredientViewHolder holder = (IngredientViewHolder) view.getTag();

        float amount = cursor.getFloat(cursor.getColumnIndex(DbColumns.Ingredient.QUANTITY));

        String amountText;

        if (amount == 0)
            amountText = "";
        else {
            String quantityType = cursor.getString(cursor.getColumnIndex(DbColumns.Component.QUANTITY_TYPE));
            String preferredMeasure = cursor.getString(cursor.getColumnIndex(DbColumns.Ingredient.PREFERRED_MEASURE));

            // Remove trailing zeros if possible
            if ((int) amount == amount)
                amountText = String.format("%d %s", (int)amount, getUnit(preferredMeasure, quantityType));
            else
                amountText = String.format("%f %s", amount, getUnit(preferredMeasure, quantityType));
        }

        holder.amountView.setText(amountText.trim());

        holder.componentView.setText(cursor.getString(cursor.getColumnIndex(DbColumns.Component.NAME)));
    }

    private void bindImageView(View view, Context context, Cursor cursor){

        ImageViewHolder holder = (ImageViewHolder) view.getTag();

        holder.title.setText(recipeTitle);

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.image);
    }

    private void bindStepView(View view, Context context, Cursor cursor){

        StepViewHolder holder = (StepViewHolder) view.getTag();

        int number = cursor.getInt(cursor.getColumnIndex(DbColumns.Step.SEQUENCE_INDEX)) + 1;

        holder.numberView.setText(String.valueOf(number));;
        holder.descriptionView.setText(cursor.getString(cursor.getColumnIndex(DbColumns.Step.TEXT)));
    }



    // Gets a preferred unit for the given quantity type and preferred measure
    private String getUnit (String preferredMeasure, String quantityType){
        if (preferredMeasure == null || quantityType == null)
            return "";

        if (quantityType.equals(Constants.Measures.QUANTITY_TYPE_NUMBER))
            return "";

        if (preferredMeasure.equals(Constants.Measures.PREFERRED_MEASURE_REGULAR)) {
            return Constants.Measures.MEASURE_MAP.get(quantityType);
        }
        else if (preferredMeasure.equals(Constants.Measures.PREFERRED_MEASURE_THOUSAND)
                && quantityType.equals(Constants.Measures.QUANTITY_TYPE_WEIGHT)){
            return Constants.Measures.WEIGHT_UNIT_GRAM;
        }
        else {
            return preferredMeasure;
        }
    }

    private static class IngredientViewHolder {

        public TextView amountView;
        public TextView componentView;

        public IngredientViewHolder(TextView amount, TextView component){
            amountView = amount;
            componentView = component;
        }

    }

    private static class StepViewHolder {
        TextView numberView;
        TextView descriptionView;

        public StepViewHolder(TextView number, TextView description){
            numberView = number;
            descriptionView = description;
        }
    }

    private static class ImageViewHolder {
        TextView title;
        ImageView image;

        public ImageViewHolder(TextView title, ImageView image){
            this.title = title;
            this.image = image;
        }
    }
}
