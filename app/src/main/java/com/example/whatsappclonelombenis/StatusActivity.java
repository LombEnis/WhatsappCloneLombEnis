package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {
    // Activity variables
    private boolean isLeavingActivity;

    // Layout view
    private ConstraintLayout rootConstraintLayout;
    private RelativeLayout statusRootRelativeLayout;
    private LinearLayout progressLinearLayout;
    private Toolbar actionBar;
    private ImageView storyImageView;
    private Button replyButton;
    // ViewsDialog
    private Button viewsButton;
    private ConstraintLayout viewsDialogRootLayout;
    private Toolbar viewsDialogActionBar;

    // Contacts
    private ArrayList<Contact> contacts;
    private int contactsType;
    private int currentContactPos;
    private Contact currentContact;

    private ArrayList<Integer> seenContactsPosDuringNavigation;

    // Stories
    private int startStoryPos;
    private Story currentStory;

    private int extraPosition;

    // Story button
    View.OnTouchListener leftButtonTouchListener, rightButtonTouchListener;
    private Button leftButton, rightButton;

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
    private boolean isViewsDialogOpen;
    private boolean isViewsDialogScrolling;
    private boolean isViewsDialogAnimationCancelled;
    float viewsDialogOpenPercentage;
    // Views button variables
    private float viewsDialogOpenY;
    private float viewsButtonClosedY;
    private float viewsButtonOpenY;
    // Views dialog animations
    private ObjectAnimator viewsDialogPosAnimation;
    private ObjectAnimator viewsButtonPosAnimation;
    private ObjectAnimator viewsButtonAlphaAnimation;
    private ValueAnimator viewsColorAnimation;
    // Views dialog recycler view
    private RecyclerView viewsDialogRecView;
    private StatusViewsDialogRecViewAdapter viewsDialogRecViewAdapter;
    private FrameLayout viewsDialogNoViewsFrameLayout;

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
            // Initialize contactsToUpdatePos variable
            seenContactsPosDuringNavigation = new ArrayList<Integer>();
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
        rootConstraintLayout = findViewById(R.id.root_layout);
        statusRootRelativeLayout = findViewById(R.id.status_root_layout);
        progressLinearLayout = findViewById(R.id.progress_linearlayout);
        actionBar = findViewById(R.id.action_bar);
        storyImageView = findViewById(R.id.story_imageview);

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

            // Get ViewsDialog view
            viewsButton = findViewById(R.id.views_button);
            viewsDialogRootLayout = findViewById(R.id.dialog_views_root_layout);
            viewsDialogActionBar = findViewById(R.id.dialog_views_action_bar);
            viewsDialogRecView = findViewById(R.id.dialog_views_rec_view);
            viewsDialogNoViewsFrameLayout = findViewById(R.id.dialog_views_no_views_frame_layout);

            // Initialize ViewsDialog variables
            isViewsDialogOpen = false;
            isViewsDialogScrolling = false;

            // Layout created listener
            statusRootRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            // Layout creation completed
                            statusRootRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            // Get ViewsDialog default position when is open
                            viewsDialogOpenY = App.fullScreenHeight - viewsDialogRootLayout.getHeight();
                            // Get ViewsButton default position when ViewsDialog is open
                            viewsButtonClosedY = viewsButton.getY();
                            viewsButtonOpenY = viewsDialogOpenY - (App.fullScreenHeight - viewsButtonClosedY);
                        }
                    });

            // Set views button text
            viewsButton.setText(Integer.toString(currentStory.getViewsContacts().size()));

            // Set ViewsDialog action bar values
            viewsDialogActionBar.setTitle(getString(R.string.visto_da, currentStory.getViewsContacts().size()));
            viewsDialogActionBar.inflateMenu(R.menu.dialog_views_actionbar_menu);
            viewsDialogActionBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.dialog_views_actionbar_menu_delete:
                            // Delete item pressed

                            // Create delete story dialog
                            AlertDialog.Builder deleteStoryAlertDialog = new AlertDialog.Builder(StatusActivity.this);

                            deleteStoryAlertDialog.setMessage(R.string.delete_story_message);

                            deleteStoryAlertDialog.setPositiveButton(R.string.elimina, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    currentContact.deleteStatusStory(currentContact.getCurrentStoriesPos());
                                    leaveActivity();

                                    Toast.makeText(StatusActivity.this,
                                            getString(R.string.deleted_story_message),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                            deleteStoryAlertDialog.setNegativeButton(R.string.annulla, null);

                            deleteStoryAlertDialog.show();
                            break;
                    }

                    return false;
                }
            });

            // Set bottom padding for root relative layout
            App.updateDeviceSizeVariables(this);
            viewsDialogRootLayout.setPadding(0, 0, 0, App.navigationBarHeight);
            if (currentStory.getViewsContacts().size() == 0) {
                // Set visibility VISIBLE for No Views Message
                viewsDialogRecView.setVisibility(View.GONE);
                viewsDialogNoViewsFrameLayout.setVisibility(View.VISIBLE);
            } else {
                // Set recycler view adapter
                viewsDialogRecViewAdapter = new StatusViewsDialogRecViewAdapter(this);
                viewsDialogRecViewAdapter.setViewsContacts(currentStory);

                viewsDialogRecView.setAdapter(viewsDialogRecViewAdapter);
                viewsDialogRecView.setLayoutManager(new LinearLayoutManager(this));
            }

            // Set ViewsButton onClickListener
            viewsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Views button click
                    stopStory();
                    openViewsDialog(0f);
                }
            });

            // Set right and left buttons longClickListeners
            rightButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isViewsDialogScrolling) {
                        if (!isViewsDialogOpen) {
                            progressLinearLayout.setVisibility(View.GONE);
                            actionBar.setVisibility(View.GONE);
                            viewsButton.setVisibility(View.GONE);
                        }
                        isOnLongClickPressed = true;
                    }

                    return false;
                }
            });

            leftButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isViewsDialogScrolling) {
                        if (!isViewsDialogOpen) {
                            progressLinearLayout.setVisibility(View.GONE);
                            actionBar.setVisibility(View.GONE);
                            viewsButton.setVisibility(View.GONE);
                        }
                        isOnLongClickPressed = true;
                    }

                    return false;
                }
            });

            leftButtonTouchListener = new OnMyStatusTouchListener(this, rightButton);
            rightButtonTouchListener = new OnMyStatusTouchListener(this, rightButton);
        } else {
            // Set current contact and story
            currentContactPos = extraPosition;
            currentContact = contacts.get(currentContactPos);
            startStoryPos = currentContact.getLastStoriesPos();

            if (contactsType == 1) seenContactsPosDuringNavigation.add(currentContactPos);

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

            leftButtonTouchListener = new OnStatusTouchListener(this, rightButton);
            rightButtonTouchListener = new OnStatusTouchListener(this, rightButton);
        }

        // Set touch listeners
        leftButton.setOnTouchListener(leftButtonTouchListener);
        rightButton.setOnTouchListener(rightButtonTouchListener);

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
            App.updateDeviceSizeVariables(this);

            Button button;
            if (contactsType == 0) {
                button = viewsButton;
            } else {
                button = replyButton;
            }

            RelativeLayout.LayoutParams buttonLayoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
            buttonLayoutParams.bottomMargin = App.navigationBarHeight + 20;

            button.setLayoutParams(buttonLayoutParams);
        }

        // Start first story
        changeContactLayout();
        setCurrentStoryLayout();
        startCurrentStory();

        // Initialize some variables
        optionsAlertDialogOpened = false;
        isLeavingActivity = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (contactsType != 0) {
            getMenuInflater().inflate(R.menu.status_actionbar_menu, menu);
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
                        // Click outside of dialog
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
        if (isViewsDialogOpen) closeViewsDialog(-viewsDialogRootLayout.getHeight());
        else leaveActivity();
    }

    @Override
    protected void onRestart() {
        leaveActivity();
        super.onRestart();
    }

    public void leaveActivity() {
        isLeavingActivity = true;
        super.onBackPressed();

        progressBarAnimator.cancel();
        currentProgressBar.setProgress(100);
        progressLinearLayout.removeAllViews();

        if (contactsType == 0) {
            // My contact
            for (Story story : currentContact.getStatusStories()) {
                story.getProgressBar().setProgress(0);
            }
        } else if (contactsType == 1) {
            // Recent contacts

            // Increase last story pos and set last story as seen for seen contacts during this navigation
            for (int pos:seenContactsPosDuringNavigation) {
                contacts.get(pos).increaseLastStoriesPos();
                contacts.get(pos).getStatusStories().get(currentContact.getCurrentStoriesPos()).setSeen(true);
                contacts.get(pos).setCurrentStoriesPos(0);
            }

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
                leaveActivity();
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
                leaveActivity();
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

        currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());

        if (!seenContactsPosDuringNavigation.contains(currentContactPos)) seenContactsPosDuringNavigation.add(currentContactPos);

        changeContactLayout();
    }

    private void decreaseContact() {
        progressLinearLayout.removeAllViews();

        currentContactPos -= 1;
        currentContact = contacts.get(currentContactPos);

        currentContact.setCurrentStoriesPos(currentContact.getLastStoriesPos());

        if (!seenContactsPosDuringNavigation.contains(currentContactPos)) seenContactsPosDuringNavigation.add(currentContactPos);

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

        // Set image of the story
        Glide.with(this)
                .load(currentStory.getStoryImageBitmap())
                .centerCrop()
                .into(storyImageView);
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
                if (currentProgressBar.getProgress() == 100) nextStory();
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
        float viewsDialogOpenPercentage = -startViewsDialogPos / viewsDialogRootLayout.getHeight();

        // Open ViewsDialog with animation
        viewsDialogPosAnimation = ObjectAnimator.ofFloat(viewsDialogRootLayout, "translationY",  startViewsDialogPos, -viewsDialogRootLayout.getHeight());
        viewsDialogPosAnimation.setDuration((int) (500 - 500 * viewsDialogOpenPercentage));
        viewsDialogPosAnimation.start();

        viewsDialogPosAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isViewsDialogScrolling = true;
                isViewsDialogAnimationCancelled = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isViewsDialogAnimationCancelled) {
                    // The animation reaches the end
                    isViewsDialogScrolling = false;
                    setViewsDialogOpen(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isViewsDialogAnimationCancelled = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        // Open ViewsButton with animation
        viewsButtonPosAnimation = ObjectAnimator.ofFloat(viewsButton, "translationY", startViewsDialogPos, -viewsDialogRootLayout.getHeight());
        viewsButtonPosAnimation.setDuration((int) (500 - 500 * viewsDialogOpenPercentage));
        viewsButtonPosAnimation.start();

        // Disappear ViewsButton with animation
        viewsButtonAlphaAnimation = ObjectAnimator.ofFloat(viewsButton, "alpha", 1 - viewsDialogOpenPercentage, 0);
        viewsButtonAlphaAnimation.setDuration((int) (500 - 500 * viewsDialogOpenPercentage));
        viewsButtonAlphaAnimation.start();

        // Darken background with animation
        viewsColorAnimation = new ValueAnimator();

        int startColorFilterValues = 255 - (int) (123 * viewsDialogOpenPercentage);
        viewsColorAnimation.setIntValues(Color.rgb(startColorFilterValues, startColorFilterValues, startColorFilterValues), Color.rgb(123, 123, 123));

        viewsColorAnimation.setEvaluator(new ArgbEvaluator());
        viewsColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                storyImageView.setColorFilter((Integer)valueAnimator.getAnimatedValue(), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        viewsColorAnimation.setDuration((int) (500 - 500 * viewsDialogOpenPercentage));
        viewsColorAnimation.start();
    }

    private void closeViewsDialog(float startViewsDialogPos) {
        viewsDialogOpenPercentage = -startViewsDialogPos / viewsDialogRootLayout.getHeight();

        // Close views dialog with animation
        viewsDialogPosAnimation = ObjectAnimator.ofFloat(viewsDialogRootLayout, "translationY",  startViewsDialogPos, 0f);
        viewsDialogPosAnimation.setDuration((int) (500 * viewsDialogOpenPercentage));
        viewsDialogPosAnimation.start();

        viewsDialogPosAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isViewsDialogScrolling = true;
                isViewsDialogAnimationCancelled = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isViewsDialogAnimationCancelled) {
                    // The animation reaches the end
                    isViewsDialogScrolling = false;
                    setViewsDialogOpen(false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isViewsDialogAnimationCancelled = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        // Close ViewsButton with animation
        viewsButtonPosAnimation = ObjectAnimator.ofFloat(viewsButton, "translationY", startViewsDialogPos, 0f);
        viewsButtonPosAnimation.setDuration((int) (500 * viewsDialogOpenPercentage));
        viewsButtonPosAnimation.start();

        // Appear ViewsButton with animation
        viewsButtonAlphaAnimation = ObjectAnimator.ofFloat(viewsButton, "alpha", 1 - viewsDialogOpenPercentage, 1);
        viewsButtonAlphaAnimation.setDuration((int) (500 * viewsDialogOpenPercentage));
        viewsButtonAlphaAnimation.start();

        // Delete darken background with animation
        viewsColorAnimation = new ValueAnimator();

        int startColorFilterValues = 255 - (int) (123 * viewsDialogOpenPercentage);
        viewsColorAnimation.setIntValues(Color.rgb(startColorFilterValues, startColorFilterValues, startColorFilterValues), Color.rgb(255, 255, 255));

        viewsColorAnimation.setEvaluator(new ArgbEvaluator());
        viewsColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                storyImageView.setColorFilter((Integer)valueAnimator.getAnimatedValue(), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });

        viewsColorAnimation.setDuration((int) (500 * viewsDialogOpenPercentage));
        viewsColorAnimation.start();
    }

    private void setViewsDialogOpen(boolean open) {
        if (open) {
            actionBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Touch outside of viewsDialog
                    closeViewsDialog(-viewsDialogRootLayout.getHeight());
                    resumeStory();
                }
            });
        } else {
            actionBar.setOnClickListener(null);
        }
        isViewsDialogOpen = open;
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

        private float startY;
        float moveDiffY;

        // ViewsDialog move variables
        private float startViewsDialogY;
        float viewsDialogY;
        private float viewsDialogYFromBottom;

        // ViewsButton move variables
        private float startViewsButtonY;
        private float viewsButtonY;

        public OnMyStatusTouchListener(Context context, View view) {
            gestureDetector = new GestureDetector(context, new GestureListener(view));
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            gestureDetector.onTouchEvent(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Stop ViewsDialog animations
                    if (viewsDialogPosAnimation != null &&
                            viewsButtonPosAnimation != null &&
                            viewsButtonAlphaAnimation != null &&
                            viewsColorAnimation != null) {
                        viewsDialogPosAnimation.cancel();
                        viewsButtonPosAnimation.cancel();
                        viewsButtonAlphaAnimation.cancel();
                        viewsColorAnimation.cancel();
                    }
                    // Get start Y position of finger and viewsDialog
                    startY = event.getRawY();
                    startViewsDialogY = viewsDialogRootLayout.getY();
                    startViewsButtonY = viewsButton.getY();
                    // Stop story when the finger is touching the screen
                    stopStory();

                    return false;

                case MotionEvent.ACTION_MOVE:
                    // Get scroll Y length and viewsDialog new position
                    moveDiffY = startY - event.getRawY();
                    viewsDialogY = startViewsDialogY - moveDiffY;
                    viewsButtonY = startViewsButtonY - moveDiffY;

                    // Change position for viewsDialog
                    if (viewsDialogY > viewsDialogOpenY && viewsDialogY < App.fullScreenHeight) {
                        isViewsDialogScrolling = true;
                        // ViewsDialog is being opened
                        viewsDialogOpenPercentage = (App.fullScreenHeight - viewsDialogY) /
                                (App.fullScreenHeight - viewsDialogOpenY);
                        // Set ViewsDialog to the current position
                        viewsDialogRootLayout.setY(viewsDialogY);
                        // Set ViewsButton to the current position
                        viewsButton.setY(viewsButtonY);
                        // Set alpha for ViewsButton for the current position
                        viewsButton.setAlpha(1 - viewsDialogOpenPercentage);
                        // Darken background
                        viewsDialogYFromBottom = App.fullScreenHeight - viewsDialogRootLayout.getY();
                        float viewsDialogOpenPercentage = viewsDialogYFromBottom / viewsDialogRootLayout.getHeight();
                        int colorFilterValues = 255 - (int) (123 * viewsDialogOpenPercentage);
                        storyImageView.setColorFilter(Color.rgb(colorFilterValues, colorFilterValues, colorFilterValues), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else if (viewsDialogY < viewsDialogOpenY) {
                        // ViewsDialog is fully open
                        // Set viewsDialog to the max position
                        viewsDialogRootLayout.setY(viewsDialogOpenY);
                        // Set viewsButton to the max position
                        viewsButton.setY(viewsButtonOpenY);
                        // Set alpha for ViewsButton to the max value
                        viewsButton.setAlpha(0);
                    } else if (viewsDialogY > App.fullScreenHeight) {
                        // ViewsDialog is fully closed
                        // Set viewsDialog to the min position
                        viewsDialogRootLayout.setY(App.fullScreenHeight);
                        // Set viewsButton to the min position
                        viewsButton.setY(viewsButtonClosedY);
                        // Set alpha for ViewsButton to the min value
                        viewsButton.setAlpha(1);
                    }

                    return false;
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // When the touch is released
                if (isViewsDialogScrolling) {
                    // ViewsDialog is scrolling
                    if (viewsDialogY > viewsDialogOpenY && viewsDialogY < App.fullScreenHeight) {
                        // The ViewsDialog is neither open nor closed
                        viewsDialogYFromBottom = App.fullScreenHeight - viewsDialogRootLayout.getY();
                        if (viewsDialogYFromBottom >= (viewsDialogRootLayout.getHeight() / 2)) {
                            // ViewsDialog is more than half open -> full open
                            openViewsDialog(-viewsDialogYFromBottom);
                        } else {
                            // ViewsDialog is less than half open -> full close
                            closeViewsDialog(-viewsDialogYFromBottom);
                            if (!isLeavingActivity) resumeStory();
                        }
                    } else if (viewsDialogY < viewsDialogOpenY) {
                        // ViewsDialog is fully open
                        isViewsDialogScrolling = false;
                        setViewsDialogOpen(true);
                        isOnLongClickPressed = false;
                    } else {
                        // ViewsDialog is fully closed
                        isViewsDialogScrolling = false;
                        setViewsDialogOpen(false);
                        resumeStory();
                    }
                } else {
                    // ViewsDialog is not scrolling
                    if (isOnLongClickPressed && !isViewsDialogOpen) {
                        // Exit from long click
                        resumeStory();

                        progressLinearLayout.setVisibility(View.VISIBLE);
                        actionBar.setVisibility(View.VISIBLE);
                        viewsButton.setVisibility(View.VISIBLE);

                        isStoryStopped = false;
                        isOnLongClickPressed = false;
                    } else if (isOnLongClickPressed) {
                        // Exit from long click when ViewsDialog is open
                        isOnLongClickPressed = false;
                    } else {
                        // Exit from short click
                        if (isViewsDialogOpen) {
                            Rect viewRect = new Rect();
                            viewsDialogRootLayout.getGlobalVisibleRect(viewRect);
                            if (!viewRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                                // Touch outside of viewsDialog
                                closeViewsDialog(-viewsDialogRootLayout.getHeight());
                                resumeStory();
                            }
                        } else if (!isSwiping) {
                            if (view.getId() == R.id.right_button) nextStory();
                            else previousStory();
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
                leaveActivity();
                return;
            } else {
                // This is not the first contact
                decreaseContact();

                // Set progress bar 0% and stop animator of the previous story
                progressBarAnimator.cancel();
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
                leaveActivity();
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
            if (!isViewsDialogOpen && !isViewsDialogScrolling) {
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
                leaveActivity();
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
                leaveActivity();
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