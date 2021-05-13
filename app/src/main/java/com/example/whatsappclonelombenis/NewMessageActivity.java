package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class NewMessageActivity extends AppCompatActivity {
    //Toolbar
    static Toolbar newmessageToolbar;
    static Toolbar newmessageContextualToolbar;

    //Recycler View
    private RecyclerView newmessageRecView;

    //SeachView
    private SearchView searchContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        //Contextual Toolbar
        newmessageContextualToolbar=findViewById(R.id.newmessageContextualToolbar);
        newmessageContextualToolbar.inflateMenu(R.menu.newmessage_contextual_action_bar);
        newmessageContextualToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        newmessageContextualToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newmessageToolbar.setVisibility(View.VISIBLE);
                newmessageContextualToolbar.setVisibility(View.GONE);

                for (View view: NewMessageRecViewAdapter.selected_views) {
                    view.setOnLongClickListener(new NewMessageRecViewAdapter.ContextualToolbarListener());
                }

                NewMessageRecViewAdapter.selected_views.clear();

                for(View view : NewMessageRecViewAdapter.views) {
                    View check= view.findViewById(R.id.newmessageSelectedCheck);
                    check.setVisibility(View.GONE);
                }
            }
        });

        //Toolbar
        newmessageToolbar=findViewById(R.id.newmessageActivityToolbar);
        setSupportActionBar(newmessageToolbar);
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

    public void onQRButtonClick(View view) {
        Intent showQRCodeIntent= new Intent(this, QRCodeActivity.class);
        startActivity(showQRCodeIntent);
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
        searchContact= (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchContact.setQueryHint(getResources().getString(R.string.cerca));
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