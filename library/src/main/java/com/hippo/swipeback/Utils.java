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
import android.app.ActivityOptions;
import android.os.Build;

import java.lang.reflect.Method;

class Utils {
    private Utils() {}

    private static final Method sConvertFromTranslucentMethod;
    private static final Method sConvertToTranslucentMethod;
    private static final boolean sInit;

    // LOLLIPOP and after
    // void convertToTranslucent(TranslucentConversionListener, ActivityOptions)
    // KITKAT
    // void convertToTranslucent(TranslucentConversionListener)
    // JB m2 and before
    // None
    static {
        Method convertFromTranslucentMethod;
        Method convertToTranslucentMethod;
        boolean init;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                throw new IllegalStateException("Not supported before KITKAT.");
            }

            // Convert from translucent
            convertFromTranslucentMethod = Activity.class.getDeclaredMethod("convertFromTranslucent");
            convertFromTranslucentMethod.setAccessible(true);

            // Convert to translucent
            final Class<?> translucentConversionListenerClazz =
                    Class.forName("android.app.Activity$TranslucentConversionListener");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                convertToTranslucentMethod = Activity.class.getDeclaredMethod("convertToTranslucent",
                        translucentConversionListenerClazz, ActivityOptions.class);
            } else {
                convertToTranslucentMethod = Activity.class.getDeclaredMethod("convertToTranslucent",
                        translucentConversionListenerClazz);
            }
            convertToTranslucentMethod.setAccessible(true);
            init = true;
        } catch (Throwable t) {
            convertFromTranslucentMethod = null;
            convertToTranslucentMethod = null;
            init = false;
        }

        sConvertFromTranslucentMethod = convertFromTranslucentMethod;
        sConvertToTranslucentMethod = convertToTranslucentMethod;
        sInit = init;
    }

    public static void convertFromTranslucent(Activity activity) {
        if (sInit) {
            try {
                sConvertFromTranslucentMethod.invoke(activity);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void convertToTranslucent(Activity activity) {
        if (sInit) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sConvertToTranslucentMethod.invoke(activity, null, null);
                } else {
                    sConvertToTranslucentMethod.invoke(activity, (Object) null);
                }
            } catch (Throwable t) {
                // Ignore
            }
        }
    }
}
