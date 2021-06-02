package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
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

public class StatusActivity extends AppCompatActivity {
    // Layout view
    private RelativeLayout rootRelativeLayout;
    private LinearLayout progressLinearLayout;
    private Toolbar actionBar;
    private ImageView backgroundImageView;
    private TextView mainTextView;
    private Button replyButton;
    private Button viewsButton;
    private ConstraintLayout viewsDialogRootLayout;

    // Contacts
    private ArrayList<Contact> contacts;
    private int contactsType;
    private int currentContactPos;
    private Contact currentContact;

    private int startStoryPos;
    private Story currentStory;

    private int extraPosition;

    // Story button
    private Button leftButton;
    private Button rightButton;

    // Progress bar
    private ProgressBar currentProgressBar;
    private ObjectAnimator progressBarAnimator;

    // Story variables
    private boolean isStoryStopped;

    private boolean optionsAlertDialogOpened;

    private int statusBarHeight;

    // Touch variables
    private boolean isOnLongClickPressed;
    private boolean isSwiping;

    // Views dialog variables
    private boolean isViewsDialogOpened;
    private boolean isViewsDialogScrolling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get intent
        Intent intent = getIntent();

        // Get contactsType
        contactsType = intent.getIntExtra("contactsType", -1);
        // Get position extra
        extraPosition = intent.getIntExtra("position", -1);

        // Set contacts list and layout
        if (contactsType == 0) {
            // My contact
            // Set contacts list
            contacts = new ArrayList<>();
            contacts.add(StatusRecViewAdapter.myContact);

            // Set layout
            setContentView(R.layout.activity_my_status);
        } else if (contactsType == 1) {
            // Recent contacts
            // Set contacts list
            contacts = StatusRecViewAdapter.recentContacts;

            // Set layout
            setContentView(R.layout.activity_status);
        } else if (contactsType == 2) {
            // Seen contacts
            // Set contacts list
            contacts = StatusRecViewAdapter.seenContacts;

            // Set layout
            setContentView(R.layout.activity_status);
        } else {
            // Disabled contacts
            // Set contacts list
            contacts = StatusRecViewAdapter.disabledContacts;

            // Set layout
            setContentView(R.layout.activity_status);
        }

        // Instantiate layout view
        rootRelativeLayout = findViewById(R.id.root_layout);
        progressLinearLayout = findViewById(R.id.progress_linearlayout);
        actionBar = findViewById(R.id.action_bar);
        backgroundImageView = findViewById(R.id.background_imageview);
        mainTextView = findViewById(R.id.main_textview);

        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);

        // Instantiate onLongClick variables
        isOnLongClickPressed = false;
        isStoryStopped = false;

        // Set current story and layout variables
        if (contactsType == 0) {
            // Set current contact and story
            currentContactPos = 0;
            currentContact = contacts.get(currentContactPos);
            startStoryPos = extraPosition;

            currentContact.setCurrentStoriesPos(startStoryPos);
            currentStory = currentContact.getStatusStories().get(startStoryPos);

            // Get views button, views dialog and set views value
            viewsButton = findViewById(R.id.views_button);
            viewsDialogRootLayout = findViewById(R.id.dialog_views_root_layout);
            isViewsDialogOpened = false;
            isViewsDialogScrolling = false;

            viewsButton.setText(Integer.toString(currentStory.getViews()));

            // Open views dialog on views button click
            viewsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Views button click
                    stopStory();
                    openViewsDialog(0f);
                }
            });

            // Set long click listeners
            rightButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isViewsDialogScrolling) {
                        progressLinearLayout.setVisibility(View.GONE);
                        actionBar.setVisibility(View.GONE);
                        viewsButton.setVisibility(View.GONE);

                        isOnLongClickPressed = true;
                    }

                    return false;
                }
            });

            leftButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isViewsDialogScrolling) {
                        progressLinearLayout.setVisibility(View.GONE);
                        actionBar.setVisibility(View.GONE);
                        viewsButton.setVisibility(View.GONE);

                        isOnLongClickPressed = true;
                    }

                    return false;
                }
            });

            // Set touch listeners
            rightButton.setOnTouchListener(new OnMyStatusTouchListener(this, rightButton));
            leftButton.setOnTouchListener(new OnMyStatusTouchListener(this, leftButton));
        } else {
            // Set current contact and story
            currentContactPos = extraPosition;
            currentContact = contacts.get(currentContactPos);
            startStoryPos = currentContact.getLastStoriesPos();

            // Set current story
            currentContact.setCurrentStoriesPos(startStoryPos);
            currentStory = currentContact.getStatusStories().get(startStoryPos);

            // Get reply button
            replyButton = findViewById(R.id.reply_button);

            // Set long click listeners
            rightButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    progressLinearLayout.setVisibility(View.GONE);
                    actionBar.setVisibility(View.GONE);
                    replyButton.setVisibility(View.GONE);

                    isOnLongClickPressed = true;
                    return false;
                }
            });

            leftButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    progressLinearLayout.setVisibility(View.GONE);
                    actionBar.setVisibility(View.GONE);
                    replyButton.setVisibility(View.GONE);

                    isOnLongClickPressed = true;

                    return false;
                }
            });

            // Set touch listeners
            rightButton.setOnTouchListener(new OnStatusTouchListener(this, rightButton));
            leftButton.setOnTouchListener(new OnStatusTouchListener(this, leftButton));
        }

        // Set ActionBar
        setSupportActionBar(actionBar);
        // Add back button to ActionBar
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

        // Set top margin for the action bar
        int statusBarResourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarResourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(statusBarResourceId);
        }

        RelativeLayout.LayoutParams actionBarLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        actionBarLayoutParams.setMargins(0, statusBarHeight + 5, 0, 0);
        actionBar.setLayoutParams(actionBarLayoutParams);

        // Set bottom margin for the bottom button
        if (Build.VERSION.SDK_INT >= 19) {
            int navigationBarResourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (navigationBarResourceId > 0) {
                int navigationBarHeight = getResources().getDimensionPixelSize(navigationBarResourceId);

                Button button;
                if (contactsType == 0) {
                    button = viewsButton;
                } else {
                    button = replyButton;
                }

                RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
                buttonLayoutParams.bottomMargin = navigationBarHeight + 20;

                button.setLayoutParams(buttonLayoutParams);
            }
        }

        // Start first story
        changeContactLayout();
        setCurrentStoryLayout();
        startCurrentStory();
        // Set alert dialog opened variable to false
        optionsAlertDialogOpened = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (contactsType != 0) {
            getMenuInflater().inflate(R.menu.status_options_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (contactsType == 3) {
            menu.findItem(R.id.disable).setVisible(false);
        } else if (contactsType == 1 || contactsType == 2) {
            menu.findItem(R.id.enable).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        // Stop story
        stopStory();

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, @NonNull Menu menu) {
        // Resume story
        if (!optionsAlertDialogOpened) {
            resumeStory();
        }
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                leaveActivity();
                return true;
            case R.id.disable:
                // Disable contact item selected

                // Set alert dialog opened variable to false
                optionsAlertDialogOpened = true;
                // Create and show alert dialog that allow to disable contact status
                AlertDialog.Builder disableAlertDialog = new AlertDialog.Builder(this);

                disableAlertDialog.setTitle(getString(R.string.disable_status_dialog_title, currentContact.getName()));
                disableAlertDialog.setMessage(getString(R.string.disable_status_message, currentContact.getName()));

                disableAlertDialog.setPositiveButton(R.string.disattiva, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentContact.setStatusDisabled(true);
                    }
                });
                disableAlertDialog.setNegativeButton(R.string.annulla, null);

                disableAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        resumeStory();
                        // Set alert dialog opened variable to false
                        optionsAlertDialogOpened = false;
                    }
                });

                disableAlertDialog.show();
                break;
            case R.id.enable:
                // Enable contact item selected

                // Set alert dialog opened variable to false
                optionsAlertDialogOpened = true;
                // Create and show alert dialog that allow to disable contact status
                AlertDialog.Builder enableAlertDialog = new AlertDialog.Builder(this);

                enableAlertDialog.setTitle(getString(R.string.enable_status_dialog_title, currentContact.getName()));
                enableAlertDialog.setMessage(getString(R.string.enable_status_message, currentContact.getName()));

                enableAlertDialog.setPositiveButton(R.string.attiva, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentContact.setStatusDisabled(false);
                    }
                });
                enableAlertDialog.setNegativeButton(R.string.annulla, null);

                enableAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        resumeStory();
                        // Set alert dialog opened variable to false
                        optionsAlertDialogOpened = false;
                    }
                });

                enableAlertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void stopStory() {
        // Cancel progress bar animator and return current progress time
        progressBarAnimator.getCurrentPlayTime();
        progressBarAnimator.cancel();
    }

    private void resumeStory() {
        progressBarAnimator.setIntValues(currentProgressBar.getProgress(), 100);
        progressBarAnimator.setDuration(2000 - (2000 * currentStory.getProgressBar().getProgress()) / 100);
        progressBarAnimator.start();
    }

    @Override
    public void onBackPressed() {
        leaveActivity();
    }

    public void leaveActivity() {
        super.onBackPressed();

        progressBarAnimator.cancel();
        currentProgressBar.setProgress(100);
        progressLinearLayout.removeAllViews();

        // Increase last story pos and set last story as seen
        currentContact.increaseLastStoriesPos();
        currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos()).setSeen(true);
        currentContact.setCurrentStoriesPos(0);

        if (contactsType == 0) {
            // My contact
            for (Story story : currentContact.getStatusStories()) {
                story.getProgressBar().setProgress(0);
            }
        } else if (contactsType == 1) {
            // Recent contacts

            // Set progress bar value to 0
            for (Contact contact : contacts) {
                if (contact.isAllStoriesSeen() || contact.isStatusDisabled()) {
                    for (Story story : contact.getStatusStories()) {
                        story.getProgressBar().setProgress(0);
                    }
                    contact.setLastStoriesPos(0);
                }
            }
        } else if (contactsType == 2) {
            // Seen contacts

            // Set progress bar value to 0
            for (Contact contact : contacts) {
                for (Story story : contact.getStatusStories()) {
                    story.getProgressBar().setProgress(0);
                }
            }
        } else if (contactsType == 3) {
            // Disabled contacts

            // Set progress bar and last position to 0
            for (Contact contact : contacts) {
                contact.setLastStoriesPos(0);
                for (Story story : contact.getStatusStories()) {
                    story.getProgressBar().setProgress(0);
                }
            }
        }

        TabStatusFragment.recViewAdapter.setContacts(App.contacts);
    }

    public void nextStory() {
        // Set previous story as seen
        currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos()).setSeen(true);

        // Set progress bar 100% and set current story time to 0
        currentProgressBar.setProgress(100);

        // Check last story
        if (currentContact.getCurrentStoriesPos() == (currentContact.getStatusStories().size() - 1)) {
            // This is the last story
            if (currentContactPos == (contacts.size() - 1)) {
                // This is the last contact
                onBackPressed();
                return;
            } else {
                // This is not the last contact
                currentContact.increaseCurrentStoriesPos();
                if (currentContact.getCurrentStoriesPos() > currentContact.getLastStoriesPos()) {
                    currentContact.increaseLastStoriesPos();
                }
                increaseContact();
            }
        } else {
            // This is not the last story
            currentContact.increaseCurrentStoriesPos();
            if (currentContact.getCurrentStoriesPos() > currentContact.getLastStoriesPos()) {
                currentContact.increaseLastStoriesPos();
            }
        }

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
                decreaseContact();
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

    // Change contact methods
    private void increaseContact() {
        progressLinearLayout.removeAllViews();

        currentContactPos += 1;
        currentContact = contacts.get(currentContactPos);

        if (currentContact.getCurrentStoriesPos() == -1)
            currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());

        changeContactLayout();
    }

    private void decreaseContact() {
        progressLinearLayout.removeAllViews();

        currentContactPos -= 1;
        currentContact = contacts.get(currentContactPos);

        if (currentContact.getCurrentStoriesPos() == -1)
            currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());

        changeContactLayout();
    }

    private void changeContactLayout() {
        // Set action bar title
        if (contactsType != 0)
            getSupportActionBar().setTitle(currentContact.getName());
        else
            getSupportActionBar().setTitle("Me");
        // Set actionBar icon
        Glide.with(StatusActivity.this)
                .load(currentContact.getProfilePicture())
                .circleCrop()
                .apply(new RequestOptions().override(100, 100))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        getSupportActionBar().setLogo(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
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

        // Set ProgressBar 100% for previous stories - my status
        if (contactsType == 0) {
            for (int i = 0; i < currentContact.getCurrentStoriesPos(); i++) {
                currentContact.getStatusStories().get(i).getProgressBar().setProgress(100);
            }
        }
    }

    // Current story methods
    private void setCurrentStoryLayout() {
        // Set story date in the actionBar subtitle
        String currentContactDateString = App.getDateString(currentStory.getDate());

        getSupportActionBar().setSubtitle(currentContactDateString);

        // Set layout of the story
        rootRelativeLayout.setBackgroundColor(currentStory.getBackgroundColorResource());

        Glide.with(this)
                .load(currentStory.getBackgroundImageString())
                .into(backgroundImageView);

        mainTextView.setText(currentStory.getMainTextString());
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
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentProgressBar.getProgress() == 100) {
                    nextStory();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        progressBarAnimator.start();
    }

    // Views dialog methods
    private void openViewsDialog(float startViewsDialogPos) {
        // Open views dialog with animation
        ObjectAnimator viewsPosAnimation = ObjectAnimator.ofFloat(viewsDialogRootLayout, "translationY",  startViewsDialogPos, -viewsDialogRootLayout.getHeight());
        viewsPosAnimation.setDuration(500);
        viewsPosAnimation.start();

        // Darken background with animation
        ValueAnimator viewsColorAnimation = new ValueAnimator();
        viewsColorAnimation.setIntValues(Color.rgb(255, 255, 255), Color.rgb(123, 123, 123));
        viewsColorAnimation.setEvaluator(new ArgbEvaluator());
        viewsColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                backgroundImageView.setColorFilter((Integer)valueAnimator.getAnimatedValue(), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        viewsColorAnimation.setDuration(500);
        viewsColorAnimation.start();

        isViewsDialogScrolling = false;
        isViewsDialogOpened = true;
    }

    private void closeViewsDialog(float startViewsDialogPos) {
        // Close views dialog with animation
        ObjectAnimator animation = ObjectAnimator.ofFloat(viewsDialogRootLayout, "translationY",  startViewsDialogPos, 0f);
        animation.setDuration(500);
        animation.start();

        // Darken background with animation
        ValueAnimator viewsColorAnimation = new ValueAnimator();
        viewsColorAnimation.setIntValues(Color.rgb(123, 123, 123), Color.rgb(255, 255, 255));
        viewsColorAnimation.setEvaluator(new ArgbEvaluator());
        viewsColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                backgroundImageView.setColorFilter((Integer)valueAnimator.getAnimatedValue(), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        viewsColorAnimation.setDuration(500);
        viewsColorAnimation.start();

        isViewsDialogScrolling = false;
        isViewsDialogOpened = false;
    }

    // Progress Bar class
    class StoryProgressBar extends ProgressBar {
        private Context context;

        public StoryProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context = context;
        }
    }

    // Swipe listener class
    class OnMyStatusTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;

        private float startViewY;
        private float startY;

        public OnMyStatusTouchListener(Context context, View view) {
            gestureDetector = new GestureDetector(context, new GestureListener(view));
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            gestureDetector.onTouchEvent(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Get start Y position of viewsDialog and finger
                    startViewY = viewsDialogRootLayout.getY();
                    startY = event.getRawY();
                    // Stop story when the finger is touching the screen
                    stopStory();
                    break;

                case MotionEvent.ACTION_MOVE:
                    isViewsDialogScrolling = true;
                    // Get scroll Y length and viewsDialog new position
                    float viewsDialogDiffY = startY - event.getRawY();
                    float viewsDialogY = startViewY - viewsDialogDiffY;
                    // Get viewsDialog default position when is open
                    float viewsDialogDefaultY = App.fullScreenHeight - viewsDialogRootLayout.getHeight();

                    if (viewsDialogY > viewsDialogDefaultY && viewsDialogY < App.fullScreenHeight) {
                        // ViewsDialog is being opened
                        // Set viewsDialog to the current position
                        viewsDialogRootLayout.animate()
                                .y(viewsDialogY)
                                .setDuration(0)
                                .start();
                    } else if (viewsDialogY < viewsDialogDefaultY) {
                        // ViewsDialog is fully open
                        // Set viewsDialog to the max position
                        viewsDialogRootLayout.animate()
                                .y(viewsDialogDefaultY)
                                .setDuration(0)
                                .start();
                    } else if (viewsDialogY > App.fullScreenHeight) {
                        // ViewsDialog is fully closed
                        // Set viewsDialog to the min position
                        viewsDialogRootLayout.animate()
                                .y(App.fullScreenHeight)
                                .setDuration(0)
                                .start();
                    }
                    break;
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // When the touch is released
                if (isViewsDialogScrolling) {
                    // ViewsDialog was scrolling
                    float viewsDialogYFromBottom = App.fullScreenHeight - viewsDialogRootLayout.getY();
                    if (viewsDialogYFromBottom >= (viewsDialogRootLayout.getHeight() / 2)) {
                        // ViewsDialog was more than half open - full open
                        openViewsDialog(-viewsDialogYFromBottom);
                    } else {
                        // ViewsDialog was less than half open - full close
                        closeViewsDialog(-viewsDialogYFromBottom);
                    }
                } else {
                    // ViewsDialog is not scrolling
                    if (isOnLongClickPressed) {
                        // Exit from long click
                        resumeStory();

                        progressLinearLayout.setVisibility(View.VISIBLE);
                        actionBar.setVisibility(View.VISIBLE);
                        viewsButton.setVisibility(View.VISIBLE);

                        isStoryStopped = false;
                        isOnLongClickPressed = false;
                    } else {
                        // Exit from short click
                        if (!isSwiping) {
                            if (isViewsDialogOpened) {
                                // Views dialog is opened
                                Rect viewRect = new Rect();
                                viewsDialogRootLayout.getGlobalVisibleRect(viewRect);
                                if (!viewRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                                    // Touch outside of viewsDialog
                                    closeViewsDialog(-viewsDialogRootLayout.getHeight());
                                }
                            }

                            if (view.getId() == R.id.right_button) {
                                nextStory();
                            } else {
                                previousStory();
                            }
                        }
                        isSwiping = false;
                        isStoryStopped = false;
                    }
                }
            } else if (!isStoryStopped) {
                // Click
                stopStory();
                isStoryStopped = true;
            }

            return false;
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            private View view;

            public GestureListener(View view) {
                this.view = view;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick(view);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        private void onClick(View view) {
            view.performClick();
        }

        private void onSwipeRight() {
            isSwiping = true;
            if (currentContactPos == 0) {
                // This is the first contact
                onBackPressed();
                return;
            } else {
                // This is not the first contact
                decreaseContact();

                // Set progress bar 0% and stop animator of the previous story
                progressBarAnimator.cancel();
                currentProgressBar.setProgress(0);
                // Set current story
                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                // Start current story
                setCurrentStoryLayout();
                startCurrentStory();
            }
        }

        private void onSwipeLeft() {
            isSwiping = true;
            if (currentContactPos == (contacts.size() - 1)) {
                // This is the last contact
                onBackPressed();
                return;
            } else {
                // This is not the last contact
                increaseContact();

                // Set progress bar 100% and stop animator of the previous story
                progressBarAnimator.cancel();
                currentProgressBar.setProgress(0);
                // Set current story
                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                // Start current story
                setCurrentStoryLayout();
                startCurrentStory();
            }
        }

        private void onSwipeDown() {
            isSwiping = true;
            if (isViewsDialogOpened) {
                closeViewsDialog(-viewsDialogRootLayout.getHeight());
                resumeStory();
            } else {
                leaveActivity();
            }
        }
    }

    class OnStatusTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;

        public OnStatusTouchListener(Context context, View view) {
            gestureDetector = new GestureDetector(context, new GestureListener(view));
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            gestureDetector.onTouchEvent(event);

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // When the touch is released
                if (isOnLongClickPressed) {
                    // Exit from long click
                    resumeStory();

                    progressLinearLayout.setVisibility(View.VISIBLE);
                    actionBar.setVisibility(View.VISIBLE);
                    replyButton.setVisibility(View.VISIBLE);

                    isStoryStopped = false;
                    isOnLongClickPressed = false;
                } else {
                    // Exit from short click
                    if (!isSwiping) {
                        if (view.getId() == R.id.right_button) {
                            nextStory();
                        } else {
                            previousStory();
                        }
                    }
                    isSwiping = false;
                    isStoryStopped = false;
                }
            } else if (!isStoryStopped) {
                // Click
                stopStory();
                isStoryStopped = true;
            }

            return false;
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            private View view;

            public GestureListener(View view) {
                this.view = view;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick(view);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        private void onClick(View view) {
            view.performClick();
        }

        private void onSwipeRight() {
            isSwiping = true;
            if (currentContactPos == 0) {
                // This is the first contact
                onBackPressed();
                return;
            } else {
                // This is not the first contact
                decreaseContact();

                // Set progress bar 0% and stop animator of the previous story
                progressBarAnimator.cancel();
                currentProgressBar.setProgress(0);
                // Set current story
                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                // Start current story
                setCurrentStoryLayout();
                startCurrentStory();
            }
        }

        private void onSwipeLeft() {
            isSwiping = true;
            if (currentContactPos == (contacts.size() - 1)) {
                // This is the last contact
                onBackPressed();
                return;
            } else {
                // This is not the last contact
                increaseContact();

                // Set progress bar 100% and stop animator of the previous story
                progressBarAnimator.cancel();
                currentProgressBar.setProgress(0);
                // Set current story
                currentStory = currentContact.getStatusStories().get(currentContact.getCurrentStoriesPos());
                // Start current story
                setCurrentStoryLayout();
                startCurrentStory();
            }
        }

        private void onSwipeDown() {
            isSwiping = true;
            leaveActivity();
        }
    }
}