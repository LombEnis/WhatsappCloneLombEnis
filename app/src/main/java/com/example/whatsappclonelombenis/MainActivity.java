package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.LinearLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    //TODO: delete divider custom layout when finished
    //TODO: adjust layout height

    // TabLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // ActionBar
    private Toolbar actionBar;
    private View filters;

    private androidx.appcompat.widget.SearchView searchView;

    private MenuItem filterButtonItem;
    private Button searchFilterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate ActionBar variables
        actionBar = findViewById(R.id.action_bar);
        filters = findViewById(R.id.filters);

        // Setting ActionBar
        setSupportActionBar(actionBar);

        // Creating TabLayout
        tabLayout = findViewById(R.id.tabLayout);

        viewPager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(1);

        // Setting Tabs weight
        LinearLayout layout0 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams0 = (LinearLayout.LayoutParams) layout0.getLayoutParams();
        layoutParams0.weight = 3;
        layout0.setLayoutParams(layoutParams0);

        LinearLayout layout1 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(1));
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) layout1.getLayoutParams();
        layoutParams1.weight = 8;
        layout1.setLayoutParams(layoutParams1);

        LinearLayout layout2 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(2));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layout2.getLayoutParams();
        layoutParams2.weight = 8;
        layout2.setLayoutParams(layoutParams2);

        LinearLayout layout3 = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(3));
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) layout3.getLayoutParams();
        layoutParams3.weight = 8;
        layout3.setLayoutParams(layoutParams3);

        // Setting tab selected listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // When a tab is selected
                viewPager.setCurrentItem(tab.getPosition());

                filters.setVisibility(View.GONE);
                filterButtonItem.setVisible(false);
                tabLayout.setVisibility(View.VISIBLE);
                searchView.setIconified(true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflating ActionBar menu
        getMenuInflater().inflate(R.menu.main_options_menu, menu);

        // Instantiating the button showing the selected filter
        filterButtonItem = menu.findItem(R.id.app_bar_filter_button);
        searchFilterButton = filterButtonItem.getActionView().findViewById(R.id.filter_button);

        // Instantiating SearchView
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.cerca));

        // Set listeners for search item
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When search view expanded
                filters.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.GONE);
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
                // When query text changed
                if (searchView.getQuery().length() != 0) {
                    filters.setVisibility(View.GONE);
                } else {
                    filters.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // Instantiating the close button of the SearchView
        View closeSearchViewButton = searchView.findViewById(R.id.search_close_btn);

        // Setting a click listener on the close button of the SearchView
        closeSearchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getQuery().length() != 0 ||
                        filterButtonItem.isVisible()) {
                    searchView.setQuery("", false);
                    filterButtonItem.setVisible(false);
                    filters.setVisibility(View.VISIBLE);
                } else {
                    searchView.onActionViewCollapsed();
                    filters.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onFilterButtonClick(View view) {
        // When search filter button is clicked
        Button buttonClicked = (Button) view;

        searchFilterButton.setText(buttonClicked.getText());
        searchFilterButton.setCompoundDrawables(buttonClicked.getCompoundDrawables()[0], null, null, null);

        filterButtonItem.setVisible(true);
        filters.setVisibility(View.GONE);
    }
}