/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.swipeback;

/*
 * Created by Hippo on 10/5/2016.
 */

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;

public class SwipeBackHelper {

    private static final int[] SB_ATTRS = new int[] {
        R.attr.sb_windowBackground
    };

    private final Activity mActivity;
    private final SwipeBackLayout mSwipeBackLayout;

    public SwipeBackHelper(Activity activity) {
        mActivity = activity;
        mSwipeBackLayout = new SwipeBackLayout(activity);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onSwipe(float percent) {}

            @Override
            public void onStateChange(int edge, int state) {}

            @Override
            public void onSwipeOverThreshold() {}

            @Override
            public void onFinish() {
                if (!mActivity.isFinishing()) {
                    mActivity.finish();
                    // Cancel finish animation
                    mActivity.overridePendingTransition(0, 0);
                }
            }
        });
    }

    /**
     * Call it in {@link Activity#onPostCreate(Bundle)}.
     */
    public void onPostCreate() {
        attachToActivity(mActivity, mSwipeBackLayout);
    }

    private void attachToActivity(Activity activity, SwipeBackLayout layout) {
        // Remove DecorView background
        final ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewCompat.setBackground(decor, null);

        // Set background for the first child of DecorView
        final TypedArray a = activity.getTheme().obtainStyledAttributes(SB_ATTRS);
        final Drawable background = a.getDrawable(0);
        a.recycle();
        final ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        ViewCompat.setBackground(decorChild, background);

        // Add this SwipeBackLayout between DecorView and decorChild
        decor.removeView(decorChild);
        // Set decorChild' LayoutParams to SwipeBackLayout
        final ViewGroup.LayoutParams lp = decorChild.getLayoutParams();
        if (lp != null) {
            layout.setLayoutParams(lp);
        }
        // Set default LayoutParams for decorChild
        decorChild.setLayoutParams(layout.generateDefaultLayoutParams());
        // Add view
        layout.addView(decorChild);
        decor.addView(layout);
    }

    /**
     * Return the {@code SwipeBackLayout}.
     */
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }
}
