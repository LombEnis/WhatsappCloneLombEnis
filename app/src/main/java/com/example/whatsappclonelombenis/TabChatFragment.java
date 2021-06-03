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


public class TabChatFragment extends Fragment {
    static RecyclerView chatRecView;
    private FloatingActionButton chatFloatingButton;
    static TextView archivedView;

    static  ChatRecViewAdapter chatRecViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View chat_layout= inflater.inflate(R.layout.tab_chat, container, false);

        chatRecViewAdapter= new ChatRecViewAdapter(this.getContext());
        chatRecViewAdapter.setData(App.chat_contacts);

        chatRecView=chat_layout.findViewById(R.id.chatRecyclerView);
        chatRecView.setAdapter(chatRecViewAdapter);
        chatRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Archived chats
        archivedView = chat_layout.findViewById(R.id.archivedChats);
        if (MainActivity.archived_contacts.size()!=0) {
            archivedView.setVisibility(View.VISIBLE);
            String archivedText= getResources().getString(R.string.archived)+" ("+ MainActivity.archived_contacts.size()+")";
            archivedView.setText(archivedText);
        }

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
}
