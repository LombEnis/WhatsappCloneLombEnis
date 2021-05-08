package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

    // Story button
    private Button leftButton;
    private Button rightButton;

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

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);


        // Set ActionBar
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set current contact
        contacts = StatusRecViewAdapter.contacts;

        currentContactPos = getIntent().getIntExtra("position", -1);
        currentContact = contacts.get(currentContactPos);

        // Get current contact attributes
        currentStory = currentContact.getStatusStories().get(currentContact.getLastStoriesPos());
        currentContact.setLastStoriesPos(currentContact.getLastStoriesPos() + 1);

        // Set first story layout
        setCurrentStoryLayout();

        // Set change story listener
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContact.getCurrentStoriesPos() == 0) {
                    if (currentContactPos == 0) {
                        onBackPressed();
                    } else {
                        currentContactPos -= 1;
                        currentContact = contacts.get(currentContactPos);
                    }
                } else {
                    currentContact.decreaseCurrentStoriesPos();
                }

                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                setCurrentStoryLayout();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContact.getCurrentStoriesPos() == (currentContact.getStatusStories().size() - 1)) {
                    if (currentContactPos == (contacts.size() - 1)) {
                        onBackPressed();
                    } else {
                        currentContactPos += 1;
                        currentContact = contacts.get(currentContactPos);
                    }
                } else {
                    currentContact.increaseCurrentStoriesPos();
                    currentContact.setLastStoriesPos(currentContact.getLastStoriesPos() + 1);
                }

                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                setCurrentStoryLayout();
            }
        });
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

    private void setCurrentStoryLayout() {
        rootLayout.setBackgroundColor(currentStory.getBackgroundColorResource());

        Glide.with(this)
                .load(currentStory.getBackgroundImageString())
                .into(backgroundImageView);

        mainTextView.setText(currentStory.getMainTextString());
    }
}