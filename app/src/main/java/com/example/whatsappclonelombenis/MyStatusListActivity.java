package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MyStatusListActivity extends AppCompatActivity {
    private Toolbar actionBar;

    // Recycler View
    private RecyclerView recView;
    private MyStatusRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status_list);

        actionBar = findViewById(R.id.actionbar);
        setSupportActionBar(actionBar);
        // Add back button to ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recView = findViewById(R.id.recyclerView);

        // Get arraylist of my stories
        ArrayList<Story> myStories = StatusRecViewAdapter.myContact.getStatusStories();

        // Create adapter for recycler view
        adapter = new MyStatusRecViewAdapter(this);
        adapter.setMyStories(myStories);

        // Set layout manager for recycler view
        recView.setLayoutManager(new LinearLayoutManager(this));

        // Set divider for recycler view
        RecViewItemDivider itemDivider = new RecViewItemDivider(this, 0);
        recView.addItemDecoration(itemDivider);

        // Set adapter for recycler view
        recView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}