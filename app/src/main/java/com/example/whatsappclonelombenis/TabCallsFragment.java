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
import java.util.Calendar;

public class TabCallsFragment extends Fragment {
    //RecyclerView
    static RecyclerView callsRecView;

    //FloatingButtons
    private FloatingActionButton callContacts;

    static CallsRecViewAdapter callsAdapter;

    static LinearLayoutManager mCallsLinearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_calls, container, false);

        //Adapter
        callsAdapter= new CallsRecViewAdapter(getContext());
        callsAdapter.setData(App.calls);

        //RecyclerView
        callsRecView=view.findViewById(R.id.callsRecView);
        callsRecView.setAdapter(callsAdapter);

        mCallsLinearLayoutManager= new LinearLayoutManager(this.getContext());
        callsRecView.setLayoutManager(mCallsLinearLayoutManager);

        RecViewItemDivider dividerItemDecoration= new RecViewItemDivider(callsRecView.getContext());
        callsRecView.addItemDecoration(dividerItemDecoration);

        //callContacts FloatingButton
        callContacts=view.findViewById(R.id.callContactsFloatingButton);
        callContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callContactsIntent= new Intent(getContext(), NewCallActivity.class);
                startActivity(callContactsIntent);
            }
        });

        return view;
    }
}
