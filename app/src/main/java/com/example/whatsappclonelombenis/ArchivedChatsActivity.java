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
    public RecyclerView archivedChatsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_chats);

        toolbar=findViewById(R.id.archivedChatsToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //RecyclerView
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", false));

        ArchivedChatsRecViewAdapter archivedChatsRecViewAdapter= new ArchivedChatsRecViewAdapter(this);
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