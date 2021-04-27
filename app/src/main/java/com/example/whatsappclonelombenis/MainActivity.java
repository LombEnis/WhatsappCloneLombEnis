package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private View filters;

    private MenuItem filterButtonItem;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filters = findViewById(R.id.filters);
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
        Button buttonClicked = (Button) view;

        filterButton.setText(buttonClicked.getText());
        filterButton.setCompoundDrawables(buttonClicked.getCompoundDrawables()[0], null, null, null);

        filterButtonItem.setVisible(true);
        filters.setVisibility(View.GONE);
    }
}