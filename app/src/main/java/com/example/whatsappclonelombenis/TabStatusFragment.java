package com.example.whatsappclonelombenis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TabStatusFragment extends Fragment {
    // Recycler view
    private static RecyclerView recView;
    public static StatusRecViewAdapter recViewAdapter;
    private static LinearLayoutManager layoutManager;

    private FloatingActionButton textFloatingButton;

    public static boolean isDisabledContactsVisible;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_status, container, false);

        // Instantiate recycler view
        recView = view.findViewById(R.id.status_rec_view);

        // Set adapter for recyclerview
        recViewAdapter = new StatusRecViewAdapter(getContext());

        if (App.contacts != null && App.myContact != null) {
            // Connected successfully to internet
            TabStatusFragment.recViewAdapter.setMyContact(App.myContact);
            TabStatusFragment.recViewAdapter.setContacts(App.contacts);
        } else {
            // No internet connection
            TabStatusFragment.recViewAdapter.setMyContact(new Contact("", "", null,
                    "", new ArrayList<>()));
            TabStatusFragment.recViewAdapter.setContacts(new ArrayList<>());
        }

        recView.setAdapter(recViewAdapter);

        // Set LayoutManager for recyclerview
        layoutManager = new LinearLayoutManager(getContext());
        recView.setLayoutManager(layoutManager);


        // Set click listener on text floating button
        textFloatingButton = view.findViewById(R.id.text_floating_button);

        textFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statusTextIntent = new Intent(getContext(), NewStatusTextActivity.class);
                startActivity(statusTextIntent);
            }
        });

        return view;
    }
}
