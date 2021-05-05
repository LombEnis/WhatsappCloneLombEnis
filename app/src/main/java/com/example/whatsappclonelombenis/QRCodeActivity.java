package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.tabs.TabLayout;


public class QRCodeActivity extends AppCompatActivity {
    //Toolbar
    Toolbar qrToolbar;

    //TabLayout and ViewPager
    TabLayout qrTabLayout;
    ViewPager qrViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);

        //Toolbar
        qrToolbar=findViewById(R.id.qr_code_toolbar);
        setSupportActionBar(qrToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Tabs
        qrTabLayout=findViewById(R.id.qr_code_tablayout);
        qrViewPager=findViewById(R.id.qr_code_pager);

        QRCodePagerAdapter qrCodePagerAdapter= new QRCodePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        qrViewPager.setAdapter(qrCodePagerAdapter);

        qrViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(qrTabLayout));
        
        qrViewPager.setCurrentItem(1);

        qrTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                qrViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qrcode_menu, menu);
        return true;
    }
}