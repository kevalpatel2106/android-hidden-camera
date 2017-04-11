/*
 * Copyright 2016 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidhiddencamera.HiddenCameraFragment;

public class MainActivity extends AppCompatActivity {

    private HiddenCameraFragment mHiddenCameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_using_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
                    getSupportFragmentManager()
                            .beginTransaction()
                            .remove(mHiddenCameraFragment)
                            .commit();
                    mHiddenCameraFragment = null;
                }

                startActivity(new Intent(MainActivity.this, DemoCamActivity.class));
            }
        });

        findViewById(R.id.btn_using_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
                    getSupportFragmentManager()
                            .beginTransaction()
                            .remove(mHiddenCameraFragment)
                            .commit();
                    mHiddenCameraFragment = null;
                }

                startService(new Intent(MainActivity.this, DemoCamService.class));
            }
        });

        findViewById(R.id.btn_using_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHiddenCameraFragment = new DemoCamFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, mHiddenCameraFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mHiddenCameraFragment)
                    .commit();
            mHiddenCameraFragment = null;
        }else { //Kill the activity
            super.onBackPressed();
        }
    }
}
