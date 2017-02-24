package com.megabox.android.scrolltotop;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.megabox.android.scroll.BuildConfig;

/**
 * 自动滚动到顶部
 *
 * @author lihong
 * @since 2016/11/03
 */
public class ScrollToTopHelper {
    /**
     * DEBUG
     */
    public static boolean DEBUG = BuildConfig.DEBUG;
    /**
     * TAG
     */
    private static final String TAG = "ScrollToTopHelper";

    /**
     * 是否支持滚动到顶部
     */
    private static boolean mIsScrollToTopEnabled = true;

    /**
     * The scroll to top is enabled or not
     *
     * @return true/false
     */
    public static boolean isScrollToTopEnabled() {
        return mIsScrollToTopEnabled;
    }

    /**
     * Set the scroll to top enabled
     *
     * @param enabled true/false
     */
    public static void setScrollToTopEnabled(boolean enabled) {
        mIsScrollToTopEnabled = enabled;
    }

    /**
     * Scroll to top
     *
     * @param activity the activity
     * @return 滚动到顶部的view
     */
    public static View scrollToTop(final Activity activity) {
        if (activity == null) {
            return null;
        }

        if (!isScrollToTopEnabled()) {
            return null;
        }

        View rootView = activity.findViewById(android.R.id.content);
        ViewGroup viewGroup = (ViewGroup) rootView;
        return scrollViewGroup(viewGroup);
    }

    /**
     * Scroll to top
     *
     * @param viewGroup ViewGroup
     * @return 滚动到顶部的view
     */
    public static View scrollToTop(final ViewGroup viewGroup) {
        if (viewGroup == null) {
            return null;
        }

        if (!isScrollToTopEnabled()) {
            return null;
        }

        return scrollViewGroup(viewGroup);
    }

    /**
     * 滚动一个viewgroup中的子view
     *
     * @param viewGroup ViewGroup
     * @return 被滚动的子view
     */
    private static View scrollViewGroup(final ViewGroup viewGroup) {
        if (viewGroup == null) {
            return null;
        }

        int count = viewGroup.getChildCount();
        for (int i = count - 1; i >= 0; i --) {
            View view = viewGroup.getChildAt(i);

            if (scrollToTop(view)) {
                if (DEBUG) {
                    Log.i(TAG, "scrollToTop view : " + view.getClass().getName());
                }
                return view;

            } else if (view instanceof ViewGroup) {
                View scrollView = scrollViewGroup((ViewGroup) view);
                if (scrollView != null) {
                    return scrollView;
                }
            }
        }

        if (DEBUG) {
            Log.i(TAG, "scrollToTop view : null");
        }
        return null;
    }

    /**
     * 滚动一个view
     *
     * @param view 滚动的view
     * @return 是否执行滚动了
     */
    private static boolean scrollToTop(final View view) {
        if (view == null) {
            return false;
        }

        if (view != null && view.isShown() && view.isEnabled()
            && view instanceof IScrollToTopInterface
            && ((IScrollToTopInterface) view).isScrollToTopEnabled()) {

            Rect rect = new Rect();
            if (view.getGlobalVisibleRect(rect)) {
                ((IScrollToTopInterface) view).scrollToTop();
                return true;
            }
        }

        return false;
    }
}
