package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static android.icu.text.Normalizer.NO;

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

    // Progress bar
    private ProgressBar currentProgressBar;
    private ObjectAnimator progressBarAnimator;


    private int statusBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        // Instantiate variables
        rootRelativeLayout = findViewById(R.id.root_layout);
        progressLinearLayout = findViewById(R.id.progress_linearlayout);
        actionBar = findViewById(R.id.action_bar);
        backgroundImageView = findViewById(R.id.background_imageview);
        mainTextView = findViewById(R.id.main_textview);
        replyButton = findViewById(R.id.reply_button);

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);

        contacts = StatusRecViewAdapter.contacts;

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
        currentContactPos = getIntent().getIntExtra("position", -1);
        currentContact = contacts.get(currentContactPos);

        // Set current story
        currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());
        currentContact.increaseCurrentStoriesPos();
        currentContact.increaseLastStoriesPos();

        if (currentContact.getCurrentStoriesPos() == currentContact.getStatusStories().size()) {
            currentContact.setLastStoriesPos(-1);
            currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());
            currentContact.increaseCurrentStoriesPos();
            currentContact.increaseLastStoriesPos();
        }

        currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());

        // Start first story
        changeContactLayout();
        setCurrentStoryLayout();
        startCurrentStory();

        // Set change story listeners
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStory();
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousStory();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                progressBarAnimator.cancel();
                currentProgressBar.setProgress(100);
                progressLinearLayout.removeAllViews();
                currentContact.setCurrentStoriesPos(0);
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

     @Override
     public void onBackPressed() {
         progressBarAnimator.cancel();
         currentProgressBar.setProgress(100);
         progressLinearLayout.removeAllViews();
         currentContact.setCurrentStoriesPos(0);
         super.onBackPressed();
     }

     public void nextStory() {
         // Check last story
         if (currentContact.getCurrentStoriesPos() == (currentContact.getStatusStories().size() - 1)) {
             // This is the last story
             if (currentContactPos == (contacts.size() - 1)) {
                 // This is the last contact
                 onBackPressed();
                 return;
             } else {
                 // This is not the last contact
                 progressLinearLayout.removeAllViews();

                 currentContactPos += 1;
                 currentContact = contacts.get(currentContactPos);

                 currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());
                 currentContact.increaseCurrentStoriesPos();
                 currentContact.increaseLastStoriesPos();

                 if (currentContact.getCurrentStoriesPos() == currentContact.getStatusStories().size()) {
                     currentContact.setLastStoriesPos(-1);
                     currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());
                     currentContact.increaseCurrentStoriesPos();
                     currentContact.increaseLastStoriesPos();
                 }

                 changeContactLayout();
             }
         } else {
             // This is not the last story
             currentContact.increaseCurrentStoriesPos();
             if (currentContact.getCurrentStoriesPos() > currentContact.getLastStoriesPos()) {
                 currentContact.setLastStoriesPos(currentContact.getCurrentStoriesPos());
             }
         }

         // Set progress bar 100% and stop animator of the previous story
         progressBarAnimator.cancel();
         currentProgressBar.setProgress(100);
         // Set current story
         currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
         // Start current story
         setCurrentStoryLayout();
         startCurrentStory();
     }

     public void previousStory() {
         // Set current contact
         if (currentContact.getCurrentStoriesPos() == 0) {
             // This is the first story
             if (currentContactPos == 0) {
                 // This is the first contact
                 onBackPressed();
                 return;
             } else {
                 // This is not the first contact
                 progressLinearLayout.removeAllViews();

                 currentContactPos -= 1;
                 currentContact = contacts.get(currentContactPos);
                 changeContactLayout();
             }
         } else {
             // This is not the first story
             currentContact.decreaseCurrentStoriesPos();
         }

        // Set progress bar 0% and stop animator of the previous story
         progressBarAnimator.cancel();
         currentProgressBar.setProgress(0);
         // Set current story
         currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
         // Start current story
         setCurrentStoryLayout();
         startCurrentStory();
     }

     private void setCurrentStoryLayout() {
        // Set actionBar subtitle
        getSupportActionBar().setSubtitle("Ora");

        rootRelativeLayout.setBackgroundColor(currentStory.getBackgroundColorResource());

        Glide.with(this)
                .load(currentStory.getBackgroundImageString())
                .into(backgroundImageView);

        mainTextView.setText(currentStory.getMainTextString());
    }

    private void changeContactLayout() {
        // Set action bar title
        getSupportActionBar().setTitle(currentContact.getName());
        // Set actionBar icon
        Glide.with(StoriesActivity.this)
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

        // Add progress bars for the current contact to the layout
        ArrayList<Story> statusStories = currentContact.getStatusStories();
        int statusStoriesSize = statusStories.size();
        for (int i = 0; i < statusStoriesSize; i++) {
            StoryProgressBar progressBar;
            if (statusStories.get(i).getProgressBar() == null) {
                progressBar = new StoryProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

                // Create LayoutParams for the ProgressBar
                LinearLayout.LayoutParams progressBarLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 4, 1);

                // Set margins
                if (i == 0) {
                    progressBarLayoutParams.setMargins(12, statusBarHeight, 5, 0);
                } else if (i == (statusStoriesSize - 1)) {
                    progressBarLayoutParams.setMargins(5, statusBarHeight, 12, 0);
                } else {
                    progressBarLayoutParams.setMargins(5, statusBarHeight, 5, 0);
                }

                progressBar.setLayoutParams(progressBarLayoutParams);
                progressBar.setBackgroundColor(getResources().getColor(R.color.transparent_white));
                progressBar.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                progressBar.setMax(100);

                statusStories.get(i).setProgressBar(progressBar);
            } else {
                progressBar = statusStories.get(i).getProgressBar();
            }

            // Add ProgressBar to the layout
            progressLinearLayout.addView(progressBar);
        }
    }

    public void startCurrentStory() {
        currentProgressBar = currentStory.getProgressBar();
        currentProgressBar.setProgress(0);
        progressBarAnimator = ObjectAnimator
                .ofInt(currentProgressBar, "progress", 100)
                .setDuration(2000);
        progressBarAnimator.setInterpolator(new LinearInterpolator());
        progressBarAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentProgressBar.getProgress() == 100) {
                    nextStory();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        progressBarAnimator.start();
    }

    // Progress Bar class
    class StoryProgressBar extends ProgressBar {
         private Context context;

         public StoryProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
             super(context, attrs, defStyleAttr);
             this.context = context;
         }
     }
 }