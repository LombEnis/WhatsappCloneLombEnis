package com.example.whatsappclonelombenis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabChat extends Fragment {
    private RecyclerView chatRecView;
    private FloatingActionButton chatFloatingButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chat_layout= inflater.inflate(R.layout.tab_chat, container, false);

        //Chat RecyclerView
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che Leonardo sappia di noi"));
        contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?"));

        ChatRecViewAdapter chatRecViewAdapter= new ChatRecViewAdapter(this.getContext());
        chatRecViewAdapter.setData(contacts);

        chatRecView=chat_layout.findViewById(R.id.chatRecyclerView);
        chatRecView.setAdapter(chatRecViewAdapter);
        chatRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));

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
}
