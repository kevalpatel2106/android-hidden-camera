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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Keval on 10-Nov-16.
 * This surface view works as the fake preview for the camera.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

@SuppressLint("ViewConstructor")
class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private CameraCallbacks mCameraCallbacks;

    private SurfaceHolder mHolder;
    private Camera mCamera;

    private CameraConfig mCameraConfig;

    private volatile boolean safeToTakePicture = false;

    CameraPreview(@NonNull Context context, CameraCallbacks cameraCallbacks) {
        super(context);

        mCameraCallbacks = cameraCallbacks;

        //Set surface holder
        initSurfaceView();
    }

    /**
     * Initialize the surface view holder.
     */
    private void initSurfaceView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //Do nothing
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //Do nothing
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mCamera == null) {  //Camera is not initialized yet.
            mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
            return;
        } else if (surfaceHolder.getSurface() == null) { //Surface preview is not initialized yet
            mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // Ignore: tried to stop a non-existent preview
        }

        // Make changes in preview size
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();

        //Sort descending
        Collections.sort(pictureSizes, new PictureSizeComparator());

        //set the camera image size based on config provided
        Camera.Size cameraSize;
        switch (mCameraConfig.getResolution()) {
            case CameraResolution.HIGH_RESOLUTION:
                cameraSize = pictureSizes.get(0);   //Highest res
                break;
            case CameraResolution.MEDIUM_RESOLUTION:
                cameraSize = pictureSizes.get(pictureSizes.size() / 2);     //Resolution at the middle
                break;
            case CameraResolution.LOW_RESOLUTION:
                cameraSize = pictureSizes.get(pictureSizes.size() - 1);       //Lowest res
                break;
            default:
                throw new RuntimeException("Invalid camera resolution.");
        }
        parameters.setPictureSize(cameraSize.width, cameraSize.height);

        // Set the focus mode.
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes.contains(mCameraConfig.getFocusMode())) {
            parameters.setFocusMode(mCameraConfig.getFocusMode());
        }

        requestLayout();

        mCamera.setParameters(parameters);

        try {
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();

            safeToTakePicture = true;
        } catch (IOException | NullPointerException e) {
            //Cannot start preview
            mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Call stopPreview() to stop updating the preview surface.
        if (mCamera != null) mCamera.stopPreview();
    }

    /**
     * Initialize the camera and start the preview of the camera.
     *
     * @param cameraConfig camera config builder.
     */
    void startCameraInternal(@NonNull CameraConfig cameraConfig) {
        mCameraConfig = cameraConfig;

        if (safeCameraOpen(mCameraConfig.getFacing())) {
            if (mCamera != null) {
                requestLayout();

                try {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                    mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
                }
            }
        } else {
            mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
        }
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            stopPreviewAndFreeCamera();

            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e("CameraPreview", "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    boolean isSafeToTakePictureInternal() {
        return safeToTakePicture;
    }

    void takePictureInternal() {
        safeToTakePicture = false;
        if (mCamera != null) {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(final byte[] bytes, Camera camera) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Convert byte array to bitmap
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            //Rotate the bitmap
                            Bitmap rotatedBitmap;
                            if (mCameraConfig.getImageRotation() != CameraRotation.ROTATION_0) {
                                rotatedBitmap = HiddenCameraUtils.rotateBitmap(bitmap, mCameraConfig.getImageRotation());

                                //noinspection UnusedAssignment
                                bitmap = null;
                            } else {
                                rotatedBitmap = bitmap;
                            }

                            //Save image to the file.
                            if (HiddenCameraUtils.saveImageFromFile(rotatedBitmap,
                                    mCameraConfig.getImageFile(),
                                    mCameraConfig.getImageFormat())) {
                                //Post image file to the main thread
                                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCameraCallbacks.onImageCapture(mCameraConfig.getImageFile());
                                    }
                                });
                            } else {
                                //Post error to the main thread
                                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCameraCallbacks.onCameraError(CameraError.ERROR_IMAGE_WRITE_FAILED);
                                    }
                                });
                            }

                            mCamera.startPreview();
                            safeToTakePicture = true;
                        }
                    }).start();
                }
            });
        } else {
            mCameraCallbacks.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
            safeToTakePicture = true;
        }
    }

    /**
     * When this function returns, mCamera will be null.
     */
    void stopPreviewAndFreeCamera() {
        safeToTakePicture = false;
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}