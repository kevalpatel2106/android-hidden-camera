package com.androidhiddencamera;

import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

/**
 * Created by Keval on 10-Nov-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {
    private HiddenCameraActivity mHiddenCameraActivity;


    private SurfaceHolder mHolder;
    private Camera mCamera;

    CameraPreview(@NonNull Context context) {
        super(context);

        //validate the context
        if (context instanceof HiddenCameraActivity){
            mHiddenCameraActivity = (HiddenCameraActivity) context;
        }else {
            throw new IllegalArgumentException("You must inherit HiddenCameraActivity in your parent class.");
        }

        //create the surface view
        SurfaceView surfaceView = new SurfaceView(context);
        addView(surfaceView);

        //Set surface holder
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // Now that the size is known, set up the camera parameters and begin the preview.
        Camera.Parameters parameters = mCamera.getParameters();

        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        parameters.setPreviewSize(previewSizes.get(previewSizes.size() - 1).width, previewSizes.get(previewSizes.size() - 1).height);
        requestLayout();
        mCamera.setParameters(parameters);

        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Call stopPreview() to stop updating the preview surface.
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    public void startPreview(int id) {
        if (safeCameraOpen(id)) {
            stopPreviewAndFreeCamera();

            if (mCamera != null) {
                requestLayout();

                try {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();

                    mHiddenCameraActivity.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
                }
            }
        } else {
            mHiddenCameraActivity.onCameraError(CameraError.ERROR_CAMERA_OPEN_FAILED);
        }
    }

    boolean safeCameraOpen(int id) {
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

    /**
     * When this function returns, mCamera will be null.
     */
    public void stopPreviewAndFreeCamera() {

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}