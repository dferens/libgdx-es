package com.dferens.supervasyan.entities;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.dferens.supervasyan.R;

/**
 * Created by USER on 14.01.14.
 */
public class AboutActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        //set full-screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}