package com.megabox.android.scrolltotop.ui;

import android.widget.AbsListView;

/**
 * @author lihong
 * @since 2016/12/28
 */
public class ScrollListenerProxy implements AbsListView.OnScrollListener {
    /**
     * Proxy listener
     */
    private AbsListView.OnScrollListener mOnScrollListener;

    /**
     * 要定位的索引
     */
    private int mPosition = -1;
    /**
     * 设置索引
     */
    public void setPosition(int position) {
        mPosition = position;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener scrollListener) {
        mOnScrollListener = scrollListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

        if (scrollState == SCROLL_STATE_IDLE) {
            if (mPosition >= 0) {
                final AbsListView absListView = view;
                // Fix for scrolling bug
                absListView.post(new Runnable() {
                    @Override
                    public void run() {
                        int firstPosition = absListView.getFirstVisiblePosition();
                        int lastPosition = absListView.getLastVisiblePosition();
                        if (mPosition >= firstPosition && mPosition <= lastPosition) {
                            // Already on screen
                            return;
                        }

                        absListView.setSelection(mPosition);

                    }
                });
                mPosition = -1;
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}
