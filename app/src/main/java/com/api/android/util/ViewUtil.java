package com.api.android.util;

import android.view.View;

/**
 * Created by reda on 10/4/14.
 */
public final class ViewUtil {

    private ViewUtil() {
    }

    public static View switchView(View viewToDisappear, View viewToAppear) {
        viewToDisappear.setVisibility(View.GONE);
        viewToAppear.setVisibility(View.VISIBLE);
        return viewToAppear;
    }
}
