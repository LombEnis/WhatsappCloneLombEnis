package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity {
    // Layout view
    private RelativeLayout rootRelativeLayout;
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


    private int statusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        // Instantiate layout view
        rootRelativeLayout = findViewById(R.id.root_layout);
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

        // Delete status bar and expand the layout
        getWindow().setDecorFitsSystemWindows(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController controller = getWindow().getInsetsController();

            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
            }

            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        } else {
            //noinspection deprecation
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }


        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        // Set top margin for the action bar
        RelativeLayout.LayoutParams actionBarLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        actionBarLayoutParams.setMargins(0, statusBarHeight + 5, 0, 0);
        actionBar.setLayoutParams(actionBarLayoutParams);

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
        // Set actionBar title and subtitle
        getSupportActionBar().setTitle(currentContact.getName());
        getSupportActionBar().setSubtitle("Ora");

        // Set actionBar icon
        Glide.with(this)
                .load(currentContact.getProfilePicture())
                .circleCrop()
                .apply(new RequestOptions().override(100, 100))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        getSupportActionBar().setLogo(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });

        // Create progress bars
        int statusStoriesSize = currentContact.getStatusStories().size();
        for (int i = 0; i < statusStoriesSize; i++) {
            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

            // Create LayoutParams for the ProgressBar
            LinearLayout.LayoutParams progressBarLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 3, 1);

            // Set margins
            if (i == 0) {
                progressBarLayoutParams.setMargins(10, statusBarHeight, 5, 0);
            } else if (i == (statusStoriesSize - 1)) {
                progressBarLayoutParams.setMargins(5, statusBarHeight, 10, 0);
            } else {
                progressBarLayoutParams.setMargins(5, statusBarHeight, 5, 0);
            }

            progressBar.setLayoutParams(progressBarLayoutParams);
            progressBar.setBackgroundColor(getResources().getColor(R.color.transparent_grey));
            progressBar.setMax(2000);

            // Add ProgressBar to the layout
            progressLinearLayout.addView(progressBar);
        }

        rootRelativeLayout.setBackgroundColor(currentStory.getBackgroundColorResource());

        Glide.with(this)
                .load(currentStory.getBackgroundImageString())
                .into(backgroundImageView);

        mainTextView.setText(currentStory.getMainTextString());
    }

    public void startStory() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
    }
}