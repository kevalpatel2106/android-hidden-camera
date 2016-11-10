package com.androidhiddencamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Keval on 27-Oct-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public abstract class HiddenCameraActivity extends AppCompatActivity implements CameraCallbacks{

    private CameraPreview mCameraPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraPreview = addPreView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCamera();
    }

    public void startCamera(@CameraFacing.SupportedCameraFacing int cameraFacing) {
        if (cameraFacing == CameraFacing.FRONT_FACING_CAMERA || cameraFacing == CameraFacing.REAR_FACING_CAMERA) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                onCameraError(CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE);
                return;
            }

            mCameraPreview.startPreview(cameraFacing);
        } else {
            throw new IllegalArgumentException("Invalid camera facing value.");
        }
    }

    public void stopCamera() {
        if (mCameraPreview != null) mCameraPreview.stopPreviewAndFreeCamera();
    }

    /**
     * Add camera preview to the root of the activity layout.
     *
     * @return {@link CameraPreview} that was added to the view.
     */
    private CameraPreview addPreView() {
        //create fake camera view
        CameraPreview cameraSourceCameraPreview = new CameraPreview(this);
        cameraSourceCameraPreview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View view = ((ViewGroup) getWindow().getDecorView().getRootView()).getChildAt(0);

        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, 1);
            linearLayout.addView(cameraSourceCameraPreview, params);
        } else if (view instanceof RelativeLayout) {
            RelativeLayout relativeLayout = (RelativeLayout) view;

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            relativeLayout.addView(cameraSourceCameraPreview, params);
        } else if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(1, 1);
            frameLayout.addView(cameraSourceCameraPreview, params);
        } else {
            throw new RuntimeException("Root view of the activity/fragment cannot be frame layout");
        }

        return cameraSourceCameraPreview;
    }


}