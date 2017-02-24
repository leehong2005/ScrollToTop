package com.megabox.android.scrolltotop.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import com.megabox.android.scrolltotop.IScrollToTopInterface;
import com.megabox.android.scrolltotop.ScrollToTopHelper;

/**
 * @author liyizhe
 * @since 17/2/9
 */
public class ScrollToTopExpandableListView extends ExpandableListView implements IScrollToTopInterface {

    /**
     * 是否支持滚动到顶部
     */
    private boolean mIsScrollToTopEnabled = true;

    /**
     * 要滚动到的位置
     */
    private int mScrollPosition = -1;

    /**
     * The scroll view wrapper
     */
    private final ScrollListenerProxy mScrollListenerProxy = new ScrollListenerProxy();

    /**
     * 滚动位置校准
     */
    private ScrollPositioner mScrollPositioner;

    public ScrollToTopExpandableListView(Context context) {
        super(context);

        init(context);
    }

    public ScrollToTopExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ScrollToTopExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        setOnScrollListener(null);
        setScrollToTopEnabled(ScrollToTopHelper.isScrollToTopEnabled());
    }

    /**
     * Scroll to top
     */
    @Override
    public void scrollToTop() {
        if (isScrollToTopEnabled()) {
            requestPositionToScreen(0);
        }
    }

    /**
     * The scroll to top is enabled or not
     *
     * @return true/false
     */
    @Override
    public boolean isScrollToTopEnabled() {
        return mIsScrollToTopEnabled;
    }

    /**
     * Set the scroll to top enabled
     *
     * @param enabled true/false
     */
    @Override
    public void setScrollToTopEnabled(boolean enabled) {
        mIsScrollToTopEnabled = enabled;
    }

    /**
     * Brings the specified position to view by optionally performing a jump-scroll maneuver: first
     * it jumps to some position near the one requested and then does a smooth scroll to the
     * requested position.  This creates an impression of full smooth scrolling without actually
     * traversing the entire list.  If smooth scrolling is not requested, instantly positions the
     * requested item at a preferred offset.
     */
    public void requestPositionToScreen(int position) {
        mScrollPosition = position;
        requestLayout();
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (mScrollPosition == -1) {
            return;
        }
        final int position = mScrollPosition;
        mScrollPosition = -1;

        // Already on screen
        int firstPosition = getFirstVisiblePosition() + 1;
        int lastPosition = getLastVisiblePosition();
        if (position >= firstPosition && position <= lastPosition) {
            return;
        }

        // We will first position the list a couple of screens before or after
        // the new selection and then scroll smoothly to it.
        int twoScreens = (lastPosition - firstPosition) * 2;

        int preliminaryPosition;
        if (position < firstPosition) {
            preliminaryPosition = position + twoScreens;
            if (preliminaryPosition >= getCount()) {
                preliminaryPosition = getCount() - 1;
            }
            if (preliminaryPosition < firstPosition) {
                setSelection(preliminaryPosition);
                super.layoutChildren();
            }
        } else {
            preliminaryPosition = position - twoScreens;
            if (preliminaryPosition < 0) {
                preliminaryPosition = 0;
            }
            if (preliminaryPosition > lastPosition) {
                setSelection(preliminaryPosition);
                super.layoutChildren();
            }
        }

        // 上滑
        if (mScrollPositioner == null) {
            mScrollPositioner = new ScrollPositioner(this, mScrollListenerProxy);
        }
        mScrollPositioner.smoothScrollToPosition(position);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListenerProxy.setOnScrollListener(l);
        super.setOnScrollListener(mScrollListenerProxy);
    }
}
