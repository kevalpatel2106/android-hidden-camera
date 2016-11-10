package com.androidhiddencamera;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Keval on 10-Nov-16.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public class CameraFacing {

    private CameraFacing(){
        throw new RuntimeException("Cannot initialize this class.");
    }

    public static final int REAR_FACING_CAMERA = 1;
    public static final int FRONT_FACING_CAMERA = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REAR_FACING_CAMERA,FRONT_FACING_CAMERA})
    @interface SupportedCameraFacing{}
}
