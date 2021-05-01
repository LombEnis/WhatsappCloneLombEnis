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

public class NewGroupActivity extends AppCompatActivity {
    //Toolbar
    Toolbar newGroupToolbar;

    //RecyclerView
    RecyclerView newgroupRecView;

    //SearchView
    SearchView searchContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        newGroupToolbar=findViewById(R.id.newgroupActivityToolbar);
        setSupportActionBar(newGroupToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView
        ArrayList<Contact> contacts= new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Oi fra, quando mi dai qualche consiglio sull'allenamento?"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che Leonardo sappia di noi"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Oi fra, quando mi dai qualche consiglio sull'allenamento?"));

        NewGroupRecViewAdapter newgroupRecViewAdapter= new NewGroupRecViewAdapter(this);
        newgroupRecViewAdapter.setData(contacts);

        newgroupRecView=findViewById(R.id.newgroupRecView);
        newgroupRecView.setAdapter(newgroupRecViewAdapter);
        newgroupRecView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newgroup_menu,menu);

        MenuItem item= menu.findItem(R.id.newgroup_contact_search);
        searchContact= (androidx.appcompat.widget.SearchView) item.getActionView();
        searchContact.setMaxWidth(Integer.MAX_VALUE);
        searchContact.setQueryHint(getResources().getString(R.string.cerca));

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