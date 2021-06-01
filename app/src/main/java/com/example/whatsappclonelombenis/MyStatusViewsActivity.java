package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class MyStatusViewsActivity extends AppCompatActivity {
    private Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Delete default action bar and set layout
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status_views);

        // Set listener for touch events outside the activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        // Instantiate layout view
        Toolbar actionBar = findViewById(R.id.activity_status_views_action_bar);
        ConstraintLayout rootLayout = findViewById(R.id.activity_status_views_root_layout);

        // Set ActionBar
        setSupportActionBar(actionBar);
        setTitle("Prova");
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Set width, height and gravity BOTTOM
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindowManager().updateViewLayout(view, lp);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            // Touch event outside the activity
            onBackPressed();
        }
        return super.onTouchEvent(event);
    }
}