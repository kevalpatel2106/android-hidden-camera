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

    @SuppressWarnings("WeakerAccess")
    public class Builder {

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

        public CameraConfig.Builder setCameraFacing(@CameraFacing.SupportedCameraFacing int cameraFacing) {
            //Validate input
            if (cameraFacing != CameraFacing.REAR_FACING_CAMERA &&
                    cameraFacing != CameraFacing.FRONT_FACING_CAMERA) {
                throw new RuntimeException("Invalid camera facing value.");
            }

            mFacing = cameraFacing;
            return this;
        }

        public CameraConfig.Builder setImageFormat(@CameraImageFormat.SupportedImageFormat int imageFormat) {
            //Validate input
            if (imageFormat != CameraImageFormat.FORMAT_JPEG &&
                    imageFormat != CameraImageFormat.FORMAT_PNG) {
                throw new RuntimeException("Invalid output image format.");
            }

            mImageFormat = imageFormat;
            return this;
        }

        public CameraConfig.Builder setImageFile(File imageFile) {
            mImageFile = imageFile;
            return this;
        }

        public CameraConfig build() {
            if (mImageFile == null) mImageFile = getDefaultStorageFile();
            return CameraConfig.this;
        }

        @NonNull
        private File getDefaultStorageFile() {
            return new File(HiddenCameraUtils.getCacheDir(mContext).getAbsolutePath()
                    + File.pathSeparator
                    + "IMG_" + System.currentTimeMillis()
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

    File getImageFile(){return mImageFile;}
}
