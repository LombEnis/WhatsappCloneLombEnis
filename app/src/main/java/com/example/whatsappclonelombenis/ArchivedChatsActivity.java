package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ArchivedChatsActivity extends AppCompatActivity {
    //Toolbar
    public Toolbar toolbar;

    //RecyclerView
    static RecyclerView archivedChatsRecView;
    static ArchivedChatsRecViewAdapter archivedChatsRecViewAdapter;

    static ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_chats);

        toolbar=findViewById(R.id.archivedChatsToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //RecyclerView
        contacts = new ArrayList<>();
        for (Contact c : MainActivity.archived_contacts) {
            contacts.add(c);
        }

        archivedChatsRecViewAdapter= new ArchivedChatsRecViewAdapter(this);
        archivedChatsRecViewAdapter.setData(contacts);

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