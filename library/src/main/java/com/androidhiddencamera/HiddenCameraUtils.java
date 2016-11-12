/*
 * Copyright 2016 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidhiddencamera;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.io.File;

/**
 * Created by Keval on 11-Nov-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public class HiddenCameraUtils {

    /**
     * Check if the application has "Draw over other app" permission? This permission is available to all
     * the application below Android M (<API 23). But for the API 23 and above user has to enable it mannually
     * if the permission is not available by opening Settings -> Apps -> Gear icon on top-right corner ->
     * Draw Over other apps.
     *
     * @return true if the permission is available.
     * @see 'http://www.androidpolice.com/2015/09/07/android-m-begins-locking-down-floating-apps-requires-users-to-grant-special-permission-to-draw-on-other-apps/'
     */
    public static boolean canOverDrawOtherApps(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    /**
     * This will open settings screen to allow the "Draw over other apps" permission to the application.
     *
     * @param context instance of caller.
     */
    public static void openDrawOverPermissionSetting(Context context) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) return;

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    static File getCacheDir(Context context) {
        return context.getExternalCacheDir() == null ? context.getCacheDir() : context.getExternalCacheDir();
    }
}
