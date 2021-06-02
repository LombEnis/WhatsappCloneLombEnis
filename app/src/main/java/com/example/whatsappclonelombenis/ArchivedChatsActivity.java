package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class ArchivedChatsActivity extends AppCompatActivity {
    //Toolbar
    static Toolbar toolbar;
    static Toolbar contextualToolbar;

    //RecyclerView
    static RecyclerView archivedChatsRecView;
    static ArchivedChatsRecViewAdapter archivedChatsRecViewAdapter;

    static ArrayList<Contact> archived_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_chats);

        //Contextual toolbar
        contextualToolbar=findViewById(R.id.archivedChatsContextualToolbar);
        contextualToolbar.inflateMenu(R.menu.archived_chats_contextual_action_bar);
        contextualToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        contextualToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
                contextualToolbar.setVisibility(View.GONE);

                ArchivedChatsRecViewAdapter.views.clear();
                archivedChatsRecView.setAdapter(null);
                archivedChatsRecView.setAdapter(new ArchivedChatsRecViewAdapter(v.getContext()));

                ArchivedChatsRecViewAdapter.selected_views.clear();
                ArchivedChatsRecViewAdapter.selected_views_contacts.clear();
            }
        });

        toolbar=findViewById(R.id.archivedChatsToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //RecyclerView
        archived_contacts = new ArrayList<>();
        archived_contacts.addAll(MainActivity.archived_contacts);

        archivedChatsRecViewAdapter= new ArchivedChatsRecViewAdapter(this);
        archivedChatsRecViewAdapter.setData(archived_contacts);

        archivedChatsRecView=findViewById(R.id.archivedChatsRecView);
        archivedChatsRecView.setAdapter(archivedChatsRecViewAdapter);
        archivedChatsRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options_menu, menu);

        MenuItem settingsItem= menu.findItem(R.id.app_bar_settings);
        settingsItem.setVisible(false);

        MenuItem searchItem= menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }
}