/*
 * Copyright 2017 Keval Patel.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.androidhiddencamera.config;

import android.hardware.Camera;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Keval on 10-Nov-16.
 * Supported camera focus.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public final class CameraFocus {

    /**
     * Camera should focus automatically. This is the default focus mode if the camera focus
     * is not set.
     *
     * @see Camera.Parameters#FOCUS_MODE_AUTO
     */
    public static final int AUTO = 0;
    /**
     * Camera should focus automatically.
     *
     * @see Camera.Parameters#FOCUS_MODE_CONTINUOUS_PICTURE
     */
    public static final int CONTINUOUS_PICTURE = 1;
    /**
     * Do not focus the camera.
     */
    public static final int NO_FOCUS = 2;

    private CameraFocus() {
        throw new RuntimeException("Cannot initialize this class.");
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({AUTO, CONTINUOUS_PICTURE, NO_FOCUS})
    public @interface SupportedCameraFocus {
    }
}
