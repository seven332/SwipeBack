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
import android.os.Bundle;

public class SwipeBackHelper {

    private final Activity mActivity;
    private final SwipeBackLayout mSwipeBackLayout;

    public SwipeBackHelper(Activity activity) {
        mActivity = activity;
        mSwipeBackLayout = new SwipeBackLayout(activity);
    }

    /**
     * Call it in {@link Activity#onPostCreate(Bundle)}.
     */
    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    /**
     * Return the {@code SwipeBackLayout}.
     */
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }
}
