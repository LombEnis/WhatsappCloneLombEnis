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
    private static RecyclerView recView;
    public static StatusRecViewAdapter recViewAdapter;
    private FloatingActionButton textFloatingButton;

    private static LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_status, container, false);

        // Instantiate recycler view
        recView = view.findViewById(R.id.status_rec_view);

        // Set adapter for recyclerview
        recViewAdapter = new StatusRecViewAdapter(getContext());


        String sampleImageUrlString = "https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale";
        Bitmap sampleBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dark);

        // Create my contact + stories
        ArrayList<Story> myContactStatusStories = new ArrayList<Story>();
        Story myContactFirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, true);
        Story myContactSecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, true);
        Story myContactThirdStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orange), sampleImageUrlString,
                "Storia 3", "Questo è un caption", sampleBitmap, true);
        myContactStatusStories.add(myContactFirstStory);
        myContactStatusStories.add(myContactSecondStory);
        myContactStatusStories.add(myContactThirdStory);

        Contact myContact = new Contact("Matteo", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", myContactStatusStories);

        // Create stories
        ArrayList<Story> firstContactStatusStories = new ArrayList<Story>();
        Story firstContactFirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story firstContactSecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        Story firstContactThirdStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orange), sampleImageUrlString,
                "Storia 3", "Questo è un caption", sampleBitmap, false);
        firstContactStatusStories.add(firstContactFirstStory);
        firstContactStatusStories.add(firstContactSecondStory);
        firstContactStatusStories.add(firstContactThirdStory);

        ArrayList<Story> secondContactStatusStories = new ArrayList<Story>();
        Story secondContactfirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story secondContactsecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        secondContactStatusStories.add(secondContactfirstStory);
        secondContactStatusStories.add(secondContactsecondStory);

        ArrayList<Story> thirdContactStatusStories = new ArrayList<Story>();
        Story thirdContactfirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story thirdContactsecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        thirdContactStatusStories.add(thirdContactfirstStory);
        thirdContactStatusStories.add(thirdContactsecondStory);

        // Create contacts
        ArrayList<Contact> contacts = new ArrayList<>();

        Contact contact1 = new Contact("Leonardo DiCaprio1", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", firstContactStatusStories);
        contacts.add(contact1);
        Contact contact2 = new Contact("Leonardo DiCaprio2", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", secondContactStatusStories);
        contacts.add(contact2);
        Contact contact3 = new Contact("Leonardo DiCaprio3", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", thirdContactStatusStories);
        contacts.add(contact3);

        TabStatusFragment.recViewAdapter.setMyContact(myContact);
        TabStatusFragment.recViewAdapter.setContacts(contacts);



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
