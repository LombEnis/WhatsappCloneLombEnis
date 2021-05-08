package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {
    // Layout view
    private RelativeLayout rootLayout;
    private LinearLayout progressLinearLayout;
    private Toolbar actionBar;
    private ImageView backgroundImageView;
    private TextView mainTextView;
    private Button replyButton;

    // Contacts
    private ArrayList<Contact> contacts;
    private int currentContactPos;
    private Contact currentContact;

    private Story currentStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        // Instantiate layout view
        rootLayout = findViewById(R.id.root_layout);
        progressLinearLayout = findViewById(R.id.progress_linearlayout);
        actionBar = findViewById(R.id.action_bar);
        backgroundImageView = findViewById(R.id.background_imageview);
        mainTextView = findViewById(R.id.main_textview);
        replyButton = findViewById(R.id.reply_button);


        // Set ActionBar
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set current contact
        contacts = StatusRecViewAdapter.contacts;

        currentContactPos = getIntent().getIntExtra("position", -1);
        currentContact = contacts.get(currentContactPos);

        // Get current contact attributes
        currentStory = currentContact.getStatusStories().get(0);

        // Set story values in the layout
        rootLayout.setBackgroundColor(currentStory.getBackgroundColorResource());

        Glide.with(this)
                .load(currentStory.getBackgroundImageString())
                .into(backgroundImageView);

        mainTextView.setText(currentStory.getMainTextString());


        // Create progress bars

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}