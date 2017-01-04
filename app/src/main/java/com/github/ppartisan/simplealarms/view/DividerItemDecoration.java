package com.github.ppartisan.simplealarms.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.util.ViewUtils;

public final class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DIVIDER_HEIGHT = (int) ViewUtils.dpToPx(1);
    private Drawable mDivider;

    public DividerItemDecoration(Context context) {
        this(context, null);
    }

    public DividerItemDecoration(Context context, Drawable divider) {
        mDivider = (divider == null)
                ? ContextCompat.getDrawable(context, R.drawable.divider)
                : divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(parent.getChildAdapterPosition(view) == 0) return;

        outRect.top = DIVIDER_HEIGHT;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int dividerLeft = parent.getPaddingLeft();
        final int dividerRight = parent.getWidth() - parent.getPaddingRight();

        final int count = parent.getChildCount();

        for (int i = 0; i < count; i++) {

            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();

            final int dividerTop = child.getBottom() + params.bottomMargin;
            final int dividerBottom = dividerTop + DIVIDER_HEIGHT;

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDivider.draw(c);

        }

    }
}
