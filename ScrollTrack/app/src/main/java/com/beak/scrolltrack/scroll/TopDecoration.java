package com.beak.scrolltrack.scroll;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaoyunfei on 15/10/11.
 */
public class TopDecoration extends RecyclerView.ItemDecoration {

    private View mTopView = null;

    public TopDecoration (View topView) {
        mTopView = topView;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, mTopView.getHeight(), 0, 0);
    }
}
