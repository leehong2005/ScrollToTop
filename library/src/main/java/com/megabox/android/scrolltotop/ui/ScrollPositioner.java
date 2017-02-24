/*
 * Copyright (C) 2016. TBReader Inc. All rights reserved.
 */

package com.megabox.android.scrolltotop.ui;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * GridView或ListView的平滑滚动实现
 *
 * <p>
 * First call smothScrollToPositionFromTop(position) and then, when scrolling has finished, call
 * setSelection(position). The latter call corrects the incomplete scrolling by jumping directly to the desired
 * position. Doing so the user still has the impression that it is being animation-scrolled to this position.
 *
 * @author lihong
 * @since 2016/06/01
 */
public final class ScrollPositioner {
    /**
     * AbsListView
     */
    private final AbsListView mAbsListView;

    /**
     * proxy listener
     */
    private final ScrollListenerProxy mScrollListenerProxy;

    /**
     * 构造方法
     */
    public ScrollPositioner(AbsListView absListView, ScrollListenerProxy listener) {
        mAbsListView = absListView;
        mScrollListenerProxy = listener;
    }

    public void smoothScrollToPosition(final int position) {
        final AbsListView view = mAbsListView;
        View child = getChildAtPosition(mAbsListView, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        if (mScrollListenerProxy != null) {
            mScrollListenerProxy.setPosition(position);
        }

        // Perform scrolling to position
        view.post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }

    /**
     * 得到当前指定索引处理的View
     *
     * @param view     view
     * @param position 索引
     */
    private static View getChildAtPosition(final AdapterView view, final int position) {
        if (view == null) {
            return null;
        }

        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }
}
