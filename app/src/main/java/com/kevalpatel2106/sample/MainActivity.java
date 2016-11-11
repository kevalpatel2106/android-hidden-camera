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
                startActivity(new Intent(MainActivity.this, HiddenCamActivity.class));
            }
        });

        findViewById(R.id.btn_using_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHiddenCameraFragment = new HiddenCamFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,mHiddenCameraFragment)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mHiddenCameraFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mHiddenCameraFragment).commit();
            mHiddenCameraFragment = null;
        }
    }
}
