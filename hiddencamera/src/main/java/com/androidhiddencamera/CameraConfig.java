package com.androidhiddencamera;

import android.content.Context;
import android.support.annotation.NonNull;

import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;

import java.io.File;

/**
 * Created by Keval on 12-Nov-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public class CameraConfig {
    private Context mContext;

    @CameraResolution.SupportedResolution
    private int mResolution = CameraResolution.MEDIUM_RESOLUTION;

    @CameraFacing.SupportedCameraFacing
    private int mFacing = CameraFacing.REAR_FACING_CAMERA;

    @CameraImageFormat.SupportedImageFormat
    private int mImageFormat = CameraImageFormat.FORMAT_JPEG;

    private File mImageFile;

    public CameraConfig() {
    }

    public Builder getBuilder(Context context) {
        mContext = context;
        return new Builder();
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
        public CameraConfig.Builder setCameraResolution(@CameraResolution.SupportedResolution int resolution) {

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
        public CameraConfig.Builder setCameraFacing(@CameraFacing.SupportedCameraFacing int cameraFacing) {
            //Validate input
            if (cameraFacing != CameraFacing.REAR_FACING_CAMERA &&
                    cameraFacing != CameraFacing.FRONT_FACING_CAMERA) {
                throw new RuntimeException("Invalid camera facing value.");
            }

            mFacing = cameraFacing;
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
        public CameraConfig.Builder setImageFormat(@CameraImageFormat.SupportedImageFormat int imageFormat) {
            //Validate input
            if (imageFormat != CameraImageFormat.FORMAT_JPEG &&
                    imageFormat != CameraImageFormat.FORMAT_PNG) {
                throw new RuntimeException("Invalid output image format.");
            }

            mImageFormat = imageFormat;
            return this;
        }

        /**
         * Set the location of the out put image. If you do not set any file for the output image, by
         * default image will be stored in the application's cache directory.
         *
         * @param imageFile {@link File} where you want to store the image.
         * @return {@link Builder}
         */
        public CameraConfig.Builder setImageFile(File imageFile) {
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

        @NonNull
        private File getDefaultStorageFile() {
            return new File(HiddenCameraUtils.getCacheDir(mContext).getAbsolutePath()
                    + File.pathSeparator
                    + "IMG_" + System.currentTimeMillis()   //IMG_214515184113123.png
                    + (mImageFormat == CameraImageFormat.FORMAT_JPEG ? ".jpeg" : ".png"));
        }
    }

    int getResolution() {
        return mResolution;
    }

    int getFacing() {
        return mFacing;
    }

    int getImageFormat() {
        return mImageFormat;
    }

    File getImageFile() {
        return mImageFile;
    }
}
