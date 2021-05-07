package com.example.whatsappclonelombenis;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TabCalls extends Fragment {
    //RecyclerView
    RecyclerView callsRecView;

    //FloatingButtons
    FloatingActionButton callContacts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_calls, container, false);

        //Calls
        ArrayList<Call> calls= new ArrayList<>();
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne"), "18:28", true, true, true));
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne"), "18:28", true, true, true));

        //Adapter
        CallsRecViewAdapter callsAdapter= new CallsRecViewAdapter(getContext());
        callsAdapter.setData(calls);

        //RecyclerView
        callsRecView=view.findViewById(R.id.callsRecView);
        callsRecView.setAdapter(callsAdapter);
        callsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecViewItemDivider dividerItemDecoration= new RecViewItemDivider(callsRecView.getContext());
        callsRecView.addItemDecoration(dividerItemDecoration);

        //callContacts FloatingButton
        callContacts=view.findViewById(R.id.callContactsFloatingButton);
        callContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callContactsIntent= new Intent(getContext(), CallContactsActivity.class);
                startActivity(callContactsIntent);
            }
        });

        return view;
    }
}
