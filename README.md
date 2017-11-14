# Android Hidden Camera
[![Build Status](https://travis-ci.org/kevalpatel2106/android-hidden-camera.svg?branch=master)](https://travis-ci.org/kevalpatel2106/android-hidden-camera) [ ![Download](https://api.bintray.com/packages/kevalpatel2106/maven/android-hidden-camera/images/download.svg) ](https://bintray.com/kevalpatel2106/maven/android-hidden-camera/_latestVersion) [![API](https://img.shields.io/badge/API-16%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=16) <a href="http://www.methodscount.com/?lib=com.kevalpatel2106%3Ahiddencamera%3A1.3"><img src="https://img.shields.io/badge/Size-36 KB-e91e63.svg"/></a>

## What is this library for?
This library allows application to take the picture using the device camera without showing the preview of it. Any application can capture the image from front or rear camera from the background service and this library will handle all the complexity on behalf of the application. You can capture images from activity, fragment and **even from the background service** using this library.

## Gradle Dependency:
```
dependencies {
    compile 'com.kevalpatel2106:hiddencamera:1.3.3'
}
```

## How to integrate?

Step-1: Inherit the builtin class.

|       Component       |              Class to inherit              |                             Sample                          |
|-----------------------|:------------------------------------------:|------------------------------------------------------------:|
|Activity               |com.androidhiddencamera.HiddenCameraActivity|`public class DemoCamActivity extends HiddenCameraActivity {`|
|Fragment               |com.androidhiddencamera.HiddenCameraFragment|`public class DemoCamFragment extends HiddenCameraFragment {`|
|Service                |com.androidhiddencamera.HiddenCameraService |`public class DemoCamService extends HiddenCameraService {`  |

Step-2: Create the camera configuration. In this developer can define which camera they want to use, output image format, capture image resolution etc parameters.

```
//Setting camera configuration
mCameraConfig = new CameraConfig()
    .getBuilder(getActivity())
    .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
    .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
    .setImageFormat(CameraImageFormat.FORMAT_JPEG)
    .setImageRotation(CameraRotation.ROTATION_270)
    .build();
```

Step-3: Start the camera in `onCreate()` by calling `startCamera(CameraConfig)`. Before starting the camera, ask user for the camera runtime permission.

```
//Check for the camera permission for the runtime
if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

    //Start camera preview
    startCamera(mCameraConfig);
} else {
    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
}
```

- If you are capturing the image from the service, you have to check if the application has the draw ver other application permission or not? If the permission is not available, application can ask user to grat the permission using `HiddenCameraUtils.openDrawOverPermissionSetting()`.

```
if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
    if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
        ...
        ...
    } else {
        //Open settings to grant permission for "Draw other apps".
        HiddenCameraUtils.openDrawOverPermissionSetting(this);
    }
} else {
    //TODO Ask your parent activity for providing runtime permission
}
```

Step-4: Take image in background whenever you want by calling `takePicture()`. You will receive captured image file in `onImageCapture()` callback.

Step -5: Handle errors by overriding `onError()` callback. In this callback you will receive an error code for each error occurred. You can take specific actions based on the error code.

```
@Override
public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
    switch (errorCode) {
        case CameraError.ERROR_CAMERA_OPEN_FAILED:
            //Camera open failed. Probably because another application
            //is using the camera
            break;
        case CameraError.ERROR_IMAGE_WRITE_FAILED:
            //Image write failed. Please check if you have provided WRITE_EXTERNAL_STORAGE permission
            break;
        case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
            //camera permission is not available
            //Ask for the camra permission before initializing it.
            break;
        case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
            //Display information dialog to the user with steps to grant "Draw over other app"
            //permission for the app.
            HiddenCameraUtils.openDrawOverPermissionSetting(this);
            break;
        case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
            Toast.makeText(this, "Your device does not have front camera.", Toast.LENGTH_LONG).show();
            break;
    }
}
```

#### That's it.

## Demo
- You can download the sample apk from [here](/apk/sample.apk).
- ![sample](/apk/sample.png)

## Contribute:
#### Simple 3 step to contribute into this repo:

1. Fork the project.
2. Make required changes and commit.
3. Generate pull request. Mention all the required description regarding changes you made.

## Questions
Hit me on twitter [![Twitter](https://img.shields.io/badge/Twitter-@kevalpatel2106-blue.svg?style=flat)](https://twitter.com/kevalpatel2106)

## License
Copyright 2017 Keval Patel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

