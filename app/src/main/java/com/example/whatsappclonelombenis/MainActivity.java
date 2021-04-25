package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout= findViewById(R.id.tabLayout);
        viewPager= findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_camera));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabChat));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabStatus));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabCalls));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    }
}