package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class TabChat extends Fragment {
    static RecyclerView chatRecView;
    private FloatingActionButton chatFloatingButton;
    static TextView archivedView;

    static ArrayList<Contact> archivedChats= new ArrayList<>();
    static ArrayList<View> archivedViews= new ArrayList<>();

    static  ChatRecViewAdapter chatRecViewAdapter;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chat_layout= inflater.inflate(R.layout.tab_chat, container, false);

        context=getContext();

        System.out.println("dsada");

        //TODO: instantiate contacts class members only when the tab is first launched
        SharedPreferences mPreferences = context.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        Boolean firstTime = mPreferences.getBoolean("firstTime", true);
        System.out.println(firstTime);
        if (firstTime) {
            getContext().getSharedPreferences("first_time", MODE_PRIVATE).edit().putBoolean("firstTime", false).commit();
            System.out.println("dsadsa");

        }

        //Chat RecyclerView
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", false));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", false));

        chatRecViewAdapter= new ChatRecViewAdapter(this.getContext());
        chatRecViewAdapter.setData(contacts);

        chatRecView=chat_layout.findViewById(R.id.chatRecyclerView);
        chatRecView.setAdapter(chatRecViewAdapter);
        chatRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Archived chats
        archivedView = chat_layout.findViewById(R.id.archivedChats);
        for (Contact c : contacts) {
            if (c.isArchived()) {
                archivedChats.add(c);
            }
        }

        /*if (archivedViews.size()!=0) {
            archivedView.setVisibility(View.VISIBLE);
            String archivedText= getResources().getString(R.string.archived)+" ("+archivedViews.size()+")";
            archivedView.setText(archivedText);
        }*/

        archivedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openArchivedChatsIntent= new Intent(getContext(), ArchivedChatsActivity.class);
                startActivity(openArchivedChatsIntent);
            }
        });

        //FloatingButton
        chatFloatingButton=chat_layout.findViewById(R.id.chatContactsFloatingButton);
        chatFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newMessageIntent= new Intent(getContext(), NewMessageActivity.class);
                startActivity(newMessageIntent);
            }
        });

        RecViewItemDivider dividerItemDecoration= new RecViewItemDivider(chatRecView.getContext());
        chatRecView.addItemDecoration(dividerItemDecoration);

        return chat_layout;
    }

    /*static void refreshAdapter() {
        chatRecView.setAdapter(null);
        chatRecView.setAdapter(new ChatRecViewAdapter(this.getContext()));
    }*/
}
