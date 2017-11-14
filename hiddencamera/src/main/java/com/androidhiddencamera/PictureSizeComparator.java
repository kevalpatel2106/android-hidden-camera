package com.androidhiddencamera;

import android.hardware.Camera;

import java.util.Comparator;

/**
 * Created by Keval on 14/11/17.
 * This comparator will sort all the {@link Camera.Size} into the dessending order of the total number
 * of pixels.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class PictureSizeComparator implements Comparator<Camera.Size> {
    // Used for sorting in ascending order of
    // roll name
    public int compare(Camera.Size a, Camera.Size b) {
        return (b.height * b.width) - (a.height * a.width);
    }
}
