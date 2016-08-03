package co.bstorm.aleksa.recipes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by aleksa on 8/3/16.
 *
 * Same as regular ImageView, only square in dimensions (taking width as side length)
 */
public class SquareImageView extends ImageView{
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        );
    }
}
