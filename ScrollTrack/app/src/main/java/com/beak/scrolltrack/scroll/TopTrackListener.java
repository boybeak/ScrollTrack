package com.beak.scrolltrack.scroll;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaoyunfei on 15/7/26.
 */
public class TopTrackListener extends RecyclerView.OnScrollListener {

    private static final String TAG = TopTrackListener.class.getSimpleName();

    private int mLastDy = 0;
    private int mTotalDy = 0;

    private View mTargetView = null;

    private ObjectAnimator mAnimator = null;

    private boolean isAlreadyHide = false, isAlreadyShow = false;

    public TopTrackListener(View target) {
        if (target == null) {
            throw new IllegalArgumentException("target shouldn't be null");
        }
        mTargetView = target;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                final float transY = mTargetView.getTranslationY();
                int distance = -mTargetView.getBottom();

                if (transY == 0 || transY == distance) {
                    return;
                }
                if (mLastDy > 0) {
                    mAnimator = animateHide(mTargetView);
                } else {
                    mAnimator = animateShow(mTargetView);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                break;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mTotalDy -= dy;
        mLastDy = dy;
        final float transY = mTargetView.getTranslationY();
        float newTransY;
        int distance = -mTargetView.getBottom();

        if (mTotalDy >= distance && dy > 0) {
            return;
        }

        if (isAlreadyHide && dy > 0) {
            return;
        }

        if (isAlreadyShow && dy < 0) {
            return;
        }

        newTransY = transY - dy;
        if (newTransY < distance) {
            newTransY = distance;
        } else if (newTransY == distance) {
            return;
        } else if (newTransY > 0) {
            newTransY = 0;
        } else if (newTransY == 0) {
            return;
        }

        mTargetView.setTranslationY(newTransY);
        isAlreadyHide = newTransY == distance;
        isAlreadyShow = newTransY == 0;

    }

    private ObjectAnimator animateHide (View targetView) {
        int distance = -targetView.getBottom();
        return animationFromTo(targetView, targetView.getTranslationY(), distance);
    }

    private ObjectAnimator animateShow (View targetView) {
        return animationFromTo(targetView, targetView.getTranslationY(), 0);
    }

    private ObjectAnimator animationFromTo (View view, float start, float end) {
        String propertyName = "translationY";
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.start();
        return animator;
    }

}
