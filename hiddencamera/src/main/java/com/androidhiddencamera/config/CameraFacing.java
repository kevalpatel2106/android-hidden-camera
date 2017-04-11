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
 * Created by Keval on 10-Nov-16.
 * Supported camera facings.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public final class CameraFacing {

    /**
     * Rear facing camera id.
     *
     * @see android.hardware.Camera.CameraInfo#CAMERA_FACING_BACK
     */
    public static final int REAR_FACING_CAMERA = 0;
    /**
     * Front facing camera id.
     *
     * @see android.hardware.Camera.CameraInfo#CAMERA_FACING_FRONT
     */
    public static final int FRONT_FACING_CAMERA = 1;

    private CameraFacing() {
        throw new RuntimeException("Cannot initialize this class.");
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REAR_FACING_CAMERA, FRONT_FACING_CAMERA})
    public @interface SupportedCameraFacing {
    }
}
