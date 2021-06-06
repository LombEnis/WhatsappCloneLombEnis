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

public class ArchivedChatsActivity extends AppCompatActivity {
    //Toolbar
    static Toolbar toolbar;
    static Toolbar contextualToolbar;

    //RecyclerView
    static RecyclerView archivedChatsRecView;
    static ArchivedChatsRecViewAdapter archivedChatsRecViewAdapter;

    static ArrayList<Contact> archived_contacts;

    static LinearLayoutManager archivedLinearLayoutManager;

    //Contextual toolbar items
    static MenuItem createConnectionItem;
    static MenuItem showContactItem;
    static MenuItem toReadItem;
    static MenuItem selectAllItem;

    static boolean isToolbarAsActionBarCalled = false;
    static boolean isContextualToolbarAsActionBarCalled = false;

    private boolean isContextualMenu=false;
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
                closeArchivedChatsContextualToolbar();
            }
        });

        showContactItem = findViewById(R.id.archivedShowContact);
        toReadItem = findViewById(R.id.archivedToRead);
        selectAllItem = findViewById(R.id.archivedSelectAll);

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

        archivedLinearLayoutManager= new LinearLayoutManager(this);
        archivedChatsRecView.setLayoutManager(archivedLinearLayoutManager);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!isContextualMenu) {
            isContextualToolbarAsActionBarCalled= false;
            System.out.println("toolbar");
            if (!isToolbarAsActionBarCalled) {
                setSupportActionBar(toolbar);
                isToolbarAsActionBarCalled =true;
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            menu.clear();
            getMenuInflater().inflate(R.menu.archived_chats_action_bar, menu);
        }
        else {
            isToolbarAsActionBarCalled =false;
            System.out.println("contextualToolbar");
            if (!isContextualToolbarAsActionBarCalled) {
                setSupportActionBar(contextualToolbar);
                isContextualToolbarAsActionBarCalled =true;
            }
            contextualToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeArchivedChatsContextualToolbar();
                    invalidateOptionsMenu();
                }
            });

            menu.clear();
            getMenuInflater().inflate(R.menu.archived_chats_contextual_action_bar, menu);

            selectAllItem= menu.findItem(R.id.archivedSelectAll);
            selectAllItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (ArchivedChatsRecViewAdapter.selected_views.size()!=0) isContextualMenu=true;
        else isContextualMenu=false;
        return super.onCreateOptionsMenu(menu);
    }

    public void onUnarchiveButtonClick(MenuItem item) {
        ArrayList<Contact> updatedChatContacts= new ArrayList<>();
        updatedChatContacts.addAll(ChatRecViewAdapter.contacts);
        for (View view : ArchivedChatsRecViewAdapter.selected_views) {
            ArchivedChatsRecViewAdapter.selected_views_contacts.get(view).setArchived(false);
            updatedChatContacts.add(ArchivedChatsRecViewAdapter.selected_views_contacts.get(view));
            MainActivity.archived_contacts.remove(ArchivedChatsRecViewAdapter.selected_views_contacts.get(view));
            ArchivedChatsRecViewAdapter.contacts.remove(ArchivedChatsRecViewAdapter.selected_views_contacts.get(view));
        }
        TabChatFragment.chatRecViewAdapter.setData(updatedChatContacts);
        TabChatFragment.chatRecView.setAdapter(TabChatFragment.chatRecViewAdapter);
        if (MainActivity.archived_contacts.size()!=0) {
            String archivedText= getResources().getString(R.string.archived)+" ("+ archived_contacts.size()+")";
            TabChatFragment.archivedView.setText(archivedText);
        }
        else {
            onBackPressed();
            TabChatFragment.archivedView.setVisibility(View.GONE);
        }
        closeArchivedChatsContextualToolbar();
    }

    public void closeArchivedChatsContextualToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        contextualToolbar.setVisibility(View.GONE);

        ArchivedChatsRecViewAdapter.views.clear();
        archivedChatsRecView.setAdapter(null);
        archivedChatsRecView.setAdapter(new ArchivedChatsRecViewAdapter(this));

        ArchivedChatsRecViewAdapter.selected_views.clear();
        ArchivedChatsRecViewAdapter.selected_views_contacts.clear();
    }
}