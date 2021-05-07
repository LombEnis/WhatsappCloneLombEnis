package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class CallContactsActivity extends AppCompatActivity {
    //Toolbar
    Toolbar callContactsToolbar;

    //SearchView
    private SearchView searchContact;

    //RecyclerView
    private RecyclerView callContactsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_contacts);

        callContactsToolbar= findViewById(R.id.callContactsActivityToolbar);
        setSupportActionBar(callContactsToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Contacts
        ArrayList<Contact> contacts= new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Occupato"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Disponibile"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Le rose son rosse"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Occupato"));

        NewCallRecViewAdapter newCallAdapter= new NewCallRecViewAdapter(this);
        newCallAdapter.setData(contacts);

        //RecyclerView
        callContactsRecView= findViewById(R.id.callContactsRecView);
        callContactsRecView.setAdapter(newCallAdapter);
        callContactsRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newmessage_menu, menu);

        MenuItem searchItem= menu.findItem(R.id.newmessage_contact_search);
        searchContact= (SearchView) searchItem.getActionView();
        searchContact.setMaxWidth(Integer.MAX_VALUE);

        searchContact.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });

        searchContact.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return false;
            }
        });

        return true;
    }
}