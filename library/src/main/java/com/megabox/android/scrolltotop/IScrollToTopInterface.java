package com.megabox.android.scrolltotop;

/**
 * @author lihong
 * @since 2016/11/01
 */
public interface IScrollToTopInterface {
    /**
     * Scroll to top
     */
    void scrollToTop();

    /**
     * The scroll to top is enabled or not
     *
     * @return true/false
     */
    boolean isScrollToTopEnabled();

    /**
     * Set the scroll to top enabled
     *
     * @param enabled true/false
     */
    void setScrollToTopEnabled(boolean enabled);
}
