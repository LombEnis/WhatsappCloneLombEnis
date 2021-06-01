package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class StatusViewsActivity extends AppCompatActivity {
    private Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Delete default action bar and set layout
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_status_views);

        // Instantiate layout view
        Toolbar actionBar = findViewById(R.id.activity_status_views_action_bar);

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
}