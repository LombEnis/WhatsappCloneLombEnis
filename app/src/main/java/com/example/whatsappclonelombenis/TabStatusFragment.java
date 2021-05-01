package com.example.whatsappclonelombenis;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TabStatusFragment extends Fragment {
    private RecyclerView recView;
    private StatusRecViewAdapter recViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_status, container, false);

        // Implementing recycler view
        recView = view.findViewById(R.id.status_rec_view);

        ArrayList<Contact> contacts = new ArrayList<>();

        ArrayList<String> statusStories = new ArrayList<>();
        statusStories.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fbiografieonline.it%2Fbiografia-leonardo-dicaprio&psig=AOvVaw2zO8Ek2WM3HpZ_EGt4s-Y7&ust=1619820205056000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMj88tG6pPACFQAAAAAdAAAAABAD\"));\n");

        contacts.add(new Contact("Leonardo DiCaprio",
                "https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale",
                statusStories));
        contacts.add(new Contact("Leonardo DiCaprio",
                "https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale",
                statusStories));

        recViewAdapter = new StatusRecViewAdapter(getContext());
        recViewAdapter.setMyProfilePicture("https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale");
        recViewAdapter.setContacts(contacts);

        recView.setAdapter(recViewAdapter);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
