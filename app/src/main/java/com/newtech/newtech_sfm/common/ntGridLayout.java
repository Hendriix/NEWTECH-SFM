package com.newtech.newtech_sfm.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

public class ntGridLayout extends GridLayout {

    View[] mChild = null;

    public ntGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ntGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ntGridLayout(Context context) {
        this(context, null);
    }

    private void arrangeElements() {

        mChild = new View[getChildCount()];
        for (int i = 0; i < getChildCount(); i++) {
            mChild[i] = getChildAt(i);
        }

        removeAllViews();
        for (int i = 0; i < mChild.length; i++) {
            if (mChild[i].getVisibility() != GONE)
                addView(mChild[i]);
        }
        for (int i = 0; i < mChild.length; i++) {
            if (mChild[i].getVisibility() == GONE)
                addView(mChild[i]);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        arrangeElements();
        super.onLayout(changed, left, top, right, bottom);

    }


}