package com.androidhiddencamera;

/**
 * Created by Keval on 10-Nov-16.
 * This class holds all the possible error codes can occur while initializing the camera.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

public class CameraError {

    /**
     * This error can occur if there is any error while opening camera. Mostly because another
     * application is using the camera.
     */
    public static final int ERROR_CAMERA_OPEN_FAILED = 1122;

    /**
     * This error wil occur if the camera permission is not available. Developer should ask user for
     * the runtime permission and once the permission granted, should try the to initialize the camera
     * again.
     */
    public static final int ERROR_CAMERA_PERMISSION_NOT_AVAILABLE = 5472;

    /**
     * This error will occur if library is not able to capture the image.
     */
    public static final int ERROR_TAKE_IMAGE_FAILED = 3136;
}