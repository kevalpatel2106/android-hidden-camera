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

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Keval Patel on 19/02/17.
 *
 * @author 'https://github.com/kevalpatel2106'
 */

public final class CameraRotation {

    /**
     * Rotate image by 90 degrees.
     */
    public static final int ROTATION_90 = 90;
    /**
     * Rotate image by 180 degrees.
     */
    public static final int ROTATION_180 = 180;
    /**
     * Rotate image by 270 (or -90) degrees.
     */
    public static final int ROTATION_270 = 270;
    /**
     * Don't rotate the image.
     */
    public static final int ROTATION_0 = 0;

    private CameraRotation() {
        throw new RuntimeException("Cannot initialize this class.");
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270})
    public @interface SupportedRotation {
    }
}
