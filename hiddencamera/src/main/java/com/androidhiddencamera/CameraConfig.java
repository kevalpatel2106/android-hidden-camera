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

package com.androidhiddencamera;

import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraFocus;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;

/**
 * Created by Keval on 12-Nov-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public final class CameraConfig {
    private Context mContext;

    @CameraResolution.SupportedResolution
    private int mResolution = CameraResolution.MEDIUM_RESOLUTION;

    @CameraFacing.SupportedCameraFacing
    private int mFacing = CameraFacing.REAR_FACING_CAMERA;

    @CameraImageFormat.SupportedImageFormat
    private int mImageFormat = CameraImageFormat.FORMAT_JPEG;

    @CameraRotation.SupportedRotation
    private int mImageRotation = CameraRotation.ROTATION_0;

    @CameraFocus.SupportedCameraFocus
    private int mCameraFocus = CameraFocus.AUTO;

    private File mImageFile;

    public CameraConfig() {
        // Do nothing
    }

    public Builder getBuilder(Context context) {
        mContext = context;
        return new Builder();
    }

    @CameraResolution.SupportedResolution
    int getResolution() {
        return mResolution;
    }

    @Nullable
    String getFocusMode() {
        switch (mCameraFocus) {
            case CameraFocus.AUTO:
                return Camera.Parameters.FOCUS_MODE_AUTO;
            case CameraFocus.CONTINUOUS_PICTURE:
                return Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
            case CameraFocus.NO_FOCUS:
                return null;
            default:
                throw new RuntimeException("Invalid camera focus mode.");
        }
    }

    @CameraFacing.SupportedCameraFacing
    int getFacing() {
        return mFacing;
    }

    @CameraImageFormat.SupportedImageFormat
    int getImageFormat() {
        return mImageFormat;
    }

    File getImageFile() {
        return mImageFile;
    }

    @CameraRotation.SupportedRotation
    int getImageRotation() {
        return mImageRotation;
    }

    public class Builder {

        /**
         * Set the resolution of the output camera image. If you don't specify any resolution,
         * default image resolution will set to {@link CameraResolution#MEDIUM_RESOLUTION}.
         *
         * @param resolution Any resolution from:
         *                   <li>{@link CameraResolution#HIGH_RESOLUTION}</li>
         *                   <li>{@link CameraResolution#MEDIUM_RESOLUTION}</li>
         *                   <li>{@link CameraResolution#LOW_RESOLUTION}</li>
         * @return {@link Builder}
         * @see CameraResolution
         */
        public Builder setCameraResolution(@CameraResolution.SupportedResolution int resolution) {

            //Validate input
            if (resolution != CameraResolution.HIGH_RESOLUTION &&
                    resolution != CameraResolution.MEDIUM_RESOLUTION &&
                    resolution != CameraResolution.LOW_RESOLUTION) {
                throw new RuntimeException("Invalid camera resolution.");
            }

            mResolution = resolution;
            return this;
        }

        /**
         * Set the camera facing with which you want to capture image.
         * Either rear facing camera or front facing camera. If you don't provide any camera facing,
         * default camera facing will be {@link CameraFacing#FRONT_FACING_CAMERA}.
         *
         * @param cameraFacing Any camera facing from:
         *                     <li>{@link CameraFacing#REAR_FACING_CAMERA}</li>
         *                     <li>{@link CameraFacing#FRONT_FACING_CAMERA}</li>
         * @return {@link Builder}
         * @see CameraFacing
         */
        public Builder setCameraFacing(@CameraFacing.SupportedCameraFacing int cameraFacing) {
            //Validate input
            if (cameraFacing != CameraFacing.REAR_FACING_CAMERA &&
                    cameraFacing != CameraFacing.FRONT_FACING_CAMERA) {
                throw new RuntimeException("Invalid camera facing value.");
            }

            mFacing = cameraFacing;
            return this;
        }

        /**
         * Set the camera focus mode. If you don't provide any camera focus mode,
         * default focus mode will be {@link CameraFocus#AUTO}.
         *
         * @param focusMode Any camera focus mode from:
         *                  <li>{@link CameraFocus#AUTO}</li>
         *                  <li>{@link CameraFocus#CONTINUOUS_PICTURE}</li>
         *                  <li>{@link CameraFocus#NO_FOCUS}</li>
         * @return {@link Builder}
         * @see CameraFacing
         */
        public Builder setCameraFocus(@CameraFocus.SupportedCameraFocus int focusMode) {
            //Validate input
            if (focusMode != CameraFocus.AUTO &&
                    focusMode != CameraFocus.CONTINUOUS_PICTURE &&
                    focusMode != CameraFocus.NO_FOCUS) {
                throw new RuntimeException("Invalid camera focus mode.");
            }

            mCameraFocus = focusMode;
            return this;
        }

        /**
         * Specify the image format for the output image. If you don't specify any output format,
         * default output format will be {@link CameraImageFormat#FORMAT_JPEG}.
         *
         * @param imageFormat Any supported image format from:
         *                    <li>{@link CameraImageFormat#FORMAT_JPEG}</li>
         *                    <li>{@link CameraImageFormat#FORMAT_PNG}</li>
         * @return {@link Builder}
         * @see CameraImageFormat
         */
        public Builder setImageFormat(@CameraImageFormat.SupportedImageFormat int imageFormat) {
            //Validate input
            if (imageFormat != CameraImageFormat.FORMAT_JPEG &&
                    imageFormat != CameraImageFormat.FORMAT_PNG) {
                throw new RuntimeException("Invalid output image format.");
            }

            mImageFormat = imageFormat;
            return this;
        }

        /**
         * Specify the output image rotation. The output image will be rotated by amount of degree specified
         * before stored to the output file. By default there is no rotation applied.
         *
         * @param rotation Any supported rotation from:
         *                 <li>{@link CameraRotation#ROTATION_0}</li>
         *                 <li>{@link CameraRotation#ROTATION_90}</li>
         *                 <li>{@link CameraRotation#ROTATION_180}</li>
         *                 <li>{@link CameraRotation#ROTATION_270}</li>
         * @return {@link Builder}
         * @see CameraRotation
         */
        public Builder setImageRotation(@CameraRotation.SupportedRotation int rotation) {
            //Validate input
            if (rotation != CameraRotation.ROTATION_0
                    && rotation != CameraRotation.ROTATION_90
                    && rotation != CameraRotation.ROTATION_180
                    && rotation != CameraRotation.ROTATION_270) {
                throw new RuntimeException("Invalid image rotation.");
            }

            mImageRotation = rotation;
            return this;
        }

        /**
         * Set the location of the out put image. If you do not set any file for the output image, by
         * default image will be stored in the application's cache directory.
         *
         * @param imageFile {@link File} where you want to store the image.
         * @return {@link Builder}
         */
        public Builder setImageFile(File imageFile) {
            mImageFile = imageFile;
            return this;
        }

        /**
         * Build the configuration.
         *
         * @return {@link CameraConfig}
         */
        public CameraConfig build() {
            if (mImageFile == null) mImageFile = getDefaultStorageFile();
            return CameraConfig.this;
        }

        /**
         * Get the new file to store the image if there isn't any custom file location available.
         * This will create new file into the cache directory of the application.
         */
        @NonNull
        private File getDefaultStorageFile() {
            return new File(HiddenCameraUtils.getCacheDir(mContext).getAbsolutePath()
                    + File.separator
                    + "IMG_" + System.currentTimeMillis()   //IMG_214515184113123.png
                    + (mImageFormat == CameraImageFormat.FORMAT_JPEG ? ".jpeg" : ".png"));
        }
    }
}
