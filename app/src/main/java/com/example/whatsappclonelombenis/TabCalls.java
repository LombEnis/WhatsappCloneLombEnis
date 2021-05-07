package com.example.whatsappclonelombenis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabCalls extends Fragment {
    //RecyclerView
    RecyclerView callsRecView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_calls, container, false);
        //TODO: change call layout to get a bigger ripple and adjust margin

        //Calls
        ArrayList<Call> calls= new ArrayList<>();
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne"), "18:28", true, true, true));

        //Adapter
        CallsRecViewAdapter callsAdapter= new CallsRecViewAdapter(getContext());
        callsAdapter.setData(calls);

        //RecyclerView
        callsRecView=view.findViewById(R.id.callsRecView);
        callsRecView.setAdapter(callsAdapter);
        callsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
