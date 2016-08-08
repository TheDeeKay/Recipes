package co.bstorm.aleksa.recipes.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import co.bstorm.aleksa.recipes.R;
import co.bstorm.aleksa.recipes.constants.DbColumns;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Ingredient;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.ShoppingItem;
import co.bstorm.aleksa.recipes.pojo.Step;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;

/**
 * Created by aleksa on 8/4/16.
 *
 * An adapter that uses 5 types of views and makes them all a single ListView (so it can be scrolled nicely)
 *
 * While this is most certainly not the most elegant way to go about this (code-wise)
 * it does provide a natural UI experience
 */
public class DetailListAdapter extends RealmBaseAdapter {

    private static final int INGREDIENT = 0;
    private static final int STEP = 1;
    private static final int IMAGE = 2;
    private static final int INGREDIENT_SEPARATOR = 3;
    private static final int STEP_SEPARATOR = 4;

    private LayoutInflater inflater;

    private Recipe recipe;

    public DetailListAdapter(Context context, Recipe recipe) {
        super(context, null);
        inflater = LayoutInflater.from(context);
        this.recipe = recipe;
    }


    @Override
    public int getCount() {
        int count = 3;
        if (recipe.getSteps() != null)
            count += recipe.getSteps().size();
        if (recipe.getIngredients() != null)
            count += recipe.getIngredients().size();

        return count;
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
        if (position - 2 < recipe.getIngredients().size())
            return INGREDIENT;
        if (position == 2 + recipe.getIngredients().size())
            return STEP_SEPARATOR;
        return STEP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)){
            case INGREDIENT: {
                convertView = newIngredientView();
                bindIgredientView(convertView, position);
                return convertView;
            }
            case STEP: {
                convertView = newStepView();
                bindStepView(convertView, position);
                return convertView;
            }
            case IMAGE: {
                convertView = newImageView(parent.getContext(), null, parent);
                bindImageView(convertView, parent.getContext());
                return convertView;
            }
            case INGREDIENT_SEPARATOR: {
                convertView = inflater.inflate(R.layout.ingredient_separator, null);

                ((TextView)convertView.findViewById(R.id.ingredient_separator_servings))
                        .setText(String.format(parent.getContext().getString(R.string.serving_size_format),
                                recipe.getDefaultServingSize()));

                return convertView;

            }
            case STEP_SEPARATOR: {
                convertView = inflater.inflate(R.layout.step_separator, null);
                return convertView;
            }
            default: return null;
        }
    }

    private View newIngredientView() {
        View view = inflater.inflate(R.layout.ingredient_list_item, null);

        IngredientViewHolder holder = new IngredientViewHolder();

        holder.amountView = (TextView) view.findViewById(R.id.ingredient_item_amount);
        holder.componentView = (TextView) view.findViewById(R.id.ingredient_item_component);
        holder.shoppingView = (ImageButton) view.findViewById(R.id.ingredient_item_shopping_button);

        view.setTag(holder);

        return view;
    }

    private View newStepView() {
        View view = inflater.inflate(R.layout.step_list_item, null);

        StepViewHolder holder = new StepViewHolder();

        holder.descriptionView = (TextView) view.findViewById(R.id.step_item_description);
        holder.numberView = (TextView) view.findViewById(R.id.step_item_number);

        view.setTag(holder);

        return view;
    }

    private View newImageView(Context context, Cursor cursor, ViewGroup parent){
        View view = inflater.inflate(R.layout.detail_image_title, null);

        ImageViewHolder holder = new ImageViewHolder();

        holder.title = (TextView) view.findViewById(R.id.details_recipe_name);
        holder.image = (ImageView) view.findViewById(R.id.details_image);

        view.setTag(holder);

        return view;
    }

    private void bindIgredientView(final View view, int position){
        IngredientViewHolder holder = (IngredientViewHolder) view.getTag();

        final Ingredient ingredient = recipe.getIngredients().get(position - 2);

        float amount = ingredient.getQuantity();

        String amountText;

        final Component component = ingredient.getComponent();

        if (amount == 0)
            amountText = "";
        else {
            amountText = ingredient.getFormattedAmount();
        }

        holder.amountView.setText(amountText.trim());

        holder.componentView.setText(component != null ? component.getName() : "");

        Realm realm = Realm.getDefaultInstance();
        ShoppingItem item = realm.where(ShoppingItem.class)
                .equalTo(DbColumns.ShoppingItem.COMPONENT + "." + DbColumns.Component.ID, component.getId())
                .findFirst();
        realm.close();

        boolean inShoppingList = item != null && item.getIngredients().contains(ingredient);

        if (inShoppingList){
            holder.shoppingView.setImageResource(R.drawable.shopping_remove);
        }
        else {
            holder.shoppingView.setImageResource(R.drawable.shopping_add);
        }

        // Attach the add/remove from shopping list logic
        holder.shoppingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                ShoppingItem item = realm.where(co.bstorm.aleksa.recipes.pojo.ShoppingItem.class)
                        .equalTo(DbColumns.ShoppingItem.COMPONENT + "." + DbColumns.Component.ID, component.getId())
                        .findFirst();

                if (item == null){
                    item = realm.createObject(ShoppingItem.class);
                    item.setComponent(ingredient.getComponent());
                }

                if (!item.getIngredients().contains(ingredient))
                    item.getIngredients().add(ingredient);
                else
                    item.getIngredients().remove(ingredient);

                realm.commitTransaction();
                realm.close();
            }
        });
    }

    private void bindImageView(View view, Context context){

        ImageViewHolder holder = (ImageViewHolder) view.getTag();

        holder.title.setText(recipe.getTitle());

        Glide.with(context)
                .load(recipe.getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.image);
    }

    private void bindStepView(View view, int position){

        StepViewHolder holder = (StepViewHolder) view.getTag();

        Step step = recipe.getSteps().get(position - 3 - recipe.getIngredients().size());

        int number = step.getSequenceIndex() + 1;

        holder.numberView.setText(String.valueOf(number));;
        holder.descriptionView.setText(step.getText());
    }

    private static class IngredientViewHolder {
        ImageButton shoppingView;
        TextView amountView;
        TextView componentView;
    }

    private static class StepViewHolder {
        TextView numberView;
        TextView descriptionView;
    }

    private static class ImageViewHolder {
        TextView title;
        ImageView image;
    }
}
