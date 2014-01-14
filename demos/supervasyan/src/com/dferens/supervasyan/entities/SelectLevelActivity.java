package com.dferens.supervasyan.entities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.dferens.supervasyan.GameActivity;
import com.dferens.supervasyan.R;

/**
 * Created by USER on 14.01.14.
 */
public class SelectLevelActivity extends Activity implements View.OnClickListener{

    ImageButton select_demo_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);
        //set full-screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        select_demo_btn = (ImageButton) findViewById(R.id.select_demo_btn);
        select_demo_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_demo_btn:
                Intent gameActivityIntent = new Intent(this, GameActivity.class);
                startActivity(gameActivityIntent);
                break;
        }
    }
}