package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.LinearLayout;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    // TabLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
  
    // ActionBar
    private Toolbar toolbar;
    private View filters;

    private MenuItem filterButtonItem;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        // ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filters = findViewById(R.id.filters);

        // TabLayout creation
        tabLayout= findViewById(R.id.tabLayout);

        viewPager= findViewById(R.id.pager);
        PagerAdapter pagerAdapter= new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(1);

        // Setting Tabs weight
        LinearLayout layout0 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams0 = (LinearLayout.LayoutParams) layout0.getLayoutParams();
        layoutParams0.weight = 1;
        layout0.setLayoutParams(layoutParams0);

        LinearLayout layout1 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(1));
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) layout1.getLayoutParams();
        layoutParams1.weight = 2;
        layout1.setLayoutParams(layoutParams1);

        LinearLayout layout2 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(2));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layout2.getLayoutParams();
        layoutParams2.weight = 2;
        layout2.setLayoutParams(layoutParams2);

        LinearLayout layout3 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(3));
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) layout3.getLayoutParams();
        layoutParams3.weight = 2;
        layout3.setLayoutParams(layoutParams3);

        // Setting tab listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options_menu, menu);

        // Declare search item
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set listeners for search item
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Search view is expanded
                filters.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // When search view is collapsed
                filters.setVisibility(View.GONE);
                filterButtonItem.setVisible(false);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When query submitted
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // For auto complete search process
                return false;
            }
        });

        filterButtonItem = menu.findItem(R.id.app_bar_filter_button);
        filterButton = filterButtonItem.getActionView().findViewById(R.id.filter_button);

        return super.onCreateOptionsMenu(menu);
    }

    public void onFilterButtonClick(View view) {
        // When filter button is clicked
        Button buttonClicked = (Button) view;

        filterButton.setText(buttonClicked.getText());
        filterButton.setCompoundDrawables(buttonClicked.getCompoundDrawables()[0], null, null, null);

        filterButtonItem.setVisible(true);
        filters.setVisibility(View.GONE);
    }
}