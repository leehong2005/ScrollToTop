package com.megabox.android.scrolltotop.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.megabox.android.scrolltotop.IScrollToTopInterface;
import com.megabox.android.scrolltotop.ScrollToTopHelper;

/**
 * 支持平滑滚动到顶部的ScrollView
 *
 * @author lihong
 * @since 2016/11/03
 */
public class ScrollToTopWebView extends WebView implements IScrollToTopInterface {

    /**
     * 是否支持滚动到顶部
     */
    private boolean mIsScrollToTopEnabled = true;

    public ScrollToTopWebView(Context context) {
        super(context);

        init(context);
    }

    public ScrollToTopWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public ScrollToTopWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        setScrollToTopEnabled(ScrollToTopHelper.isScrollToTopEnabled());
    }

    /**
     * Scroll to top
     */
    @Override
    public void scrollToTop() {
        if (isScrollToTopEnabled()) {
            int scrollY = getScrollY();
            if (scrollY == 0) {
                return;
            }

            int scrollHeight = getHeight() * 2;
            if (scrollY > scrollHeight) {
                scrollTo(0, scrollHeight);
            }
            flingScroll(0, -10000);
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
}
