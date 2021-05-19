package com.example.whatsappclonelombenis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class TabStatusFragment extends Fragment {
    private RecyclerView recView;
    public static StatusRecViewAdapter recViewAdapter;
    private FloatingActionButton textFloatingButton;

    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_status, container, false);

        // Instantiate recycler view
        recView = view.findViewById(R.id.status_rec_view);

        // Set adapter for recyclerview
        recViewAdapter = new StatusRecViewAdapter(getContext());

        recViewAdapter.setMyProfilePicture("https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale");

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
