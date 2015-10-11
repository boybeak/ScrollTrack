package com.beak.scrolltrack.scroll;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by gaoyunfei on 15/7/27.
 */
public class BottomTrackListener extends RecyclerView.OnScrollListener {

    private static final String TAG = BottomTrackListener.class.getSimpleName();

    private View mTargetView = null;

    private boolean isAlreadyHide = false, isAlreadyShow = false;

    private ObjectAnimator mAnimator = null;

    private int mLastDy = 0;

    public BottomTrackListener(View target) {
        if (target == null) {
            throw new IllegalArgumentException("target shouldn't be null");
        }
        mTargetView = target;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            final float transY = mTargetView.getTranslationY();
            if (transY == 0 || transY == getDistance()) {
                return;
            }
            if (mLastDy > 0) {
                mAnimator = animateHide(mTargetView);
            } else {
                mAnimator = animateShow(mTargetView);
            }
        } else {
            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mLastDy = dy;
        final float transY = mTargetView.getTranslationY();
        float newTransY = transY + dy;
        final int distance = getDistance();

        if (newTransY < 0) {
            newTransY = 0;
        } else if (newTransY > distance) {
            newTransY = distance;
        }
        if (newTransY == 0 && isAlreadyShow) {
            return;
        }
        if (newTransY == distance && isAlreadyHide) {
            return;
        }

        mTargetView.setTranslationY(newTransY);
        isAlreadyHide = newTransY == distance;
        isAlreadyShow = newTransY == 0;
    }

    private int getDistance () {
        ViewParent parent = mTargetView.getParent();
        if (parent instanceof View) {
            return ((View)parent).getHeight() - mTargetView.getTop();
        }
        return 0;
    }

    private ObjectAnimator animateShow (View view) {
        return animationFromTo(view, view.getTranslationY(), 0);
    }

    private ObjectAnimator animateHide (View view) {
        return animationFromTo(view, view.getTranslationY(), getDistance());
    }

    private ObjectAnimator animationFromTo (View view, float start, float end) {
        String propertyName = "translationY";
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.start();
        return animator;
    }
}
