package com.dferens.supervasyan.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.dferens.supervasyan.R;

/**
 * Created by USER on 14.01.14.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    ImageButton exit_image_btn, new_game_image_btn, about_image_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //set full-screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //find views
        exit_image_btn = (ImageButton) findViewById(R.id.exit_image_btn);
        new_game_image_btn  = (ImageButton) findViewById(R.id.new_game_image_btn);
        about_image_btn = (ImageButton) findViewById(R.id.about_image_btn);
        //and set listeners
        about_image_btn.setOnClickListener(this);
        exit_image_btn.setOnClickListener(this);
        new_game_image_btn.setOnClickListener(this);

        //load font
        TextView main_title_tv;
        main_title_tv = (TextView)findViewById(R.id.main_title_tv);
        Typeface tuscan_narrow = Typeface.createFromAsset(getAssets(), "tuscan_MF.ttf");
        main_title_tv.setTypeface(tuscan_narrow);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_game_image_btn:
                Intent selectLevelActivityIntent = new Intent(this, SelectLevelActivity.class);
                startActivity(selectLevelActivityIntent);
                break;
            case R.id.about_image_btn:
                Intent aboutActivityIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutActivityIntent);
                break;
            case R.id.exit_image_btn:
                finish();
        }
    }
}