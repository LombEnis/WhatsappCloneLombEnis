package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class NewMessage extends AppCompatActivity {
    //Toolbar
    private Toolbar newMessageToolbar;

    //Recycler View
    private RecyclerView newmessageRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        //Toolbar
        newMessageToolbar=findViewById(R.id.newMessageActivityToolbar);
        setSupportActionBar(newMessageToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView
        ArrayList<Contact> contacts= new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Oi fra, quando mi dai qualche consiglio sull'allenamento?"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che Leonardo sappia di noi"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg","Dwayne","Oi fra, quando mi dai qualche consiglio sull'allenamento?"));

        NewMessageRecViewAdapter newmessageRecViewAdapter= new NewMessageRecViewAdapter(this);
        newmessageRecViewAdapter.setData(contacts);

        newmessageRecView= findViewById(R.id.newmessageRecView);
        newmessageRecView.setAdapter(newmessageRecViewAdapter);
        newmessageRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newmessage_menu, menu);
        return true;
    }
}