package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class NewStatusTextActivity extends AppCompatActivity {
    private ConstraintLayout statusTextConstraintLayout;

    private EmojiconEditText statusEditText;
    private EmojiconTextView emojiTextView;

    private RelativeLayout bottomRelativeLayout;

    private ImageButton emojiImageButton;
    private ImageButton fontImageButton;
    private ImageButton colorImageButton;

    private ImageButton sendImageButton;

    // Font loop
    private int[] fontLoop;
    private int fontLoopPos;

    // Color loop
    private int[] colorLoop;
    private int colorLoopPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status_text);

        // Instantiate view
        statusTextConstraintLayout = findViewById(R.id.status_text_constraintlayout);

        statusEditText = findViewById(R.id.status_edittext);
        emojiTextView = findViewById(R.id.emoji_textview);

        bottomRelativeLayout = findViewById(R.id.bottom_relativelayout);

        emojiImageButton = findViewById(R.id.emoji_imagebutton);
        fontImageButton = findViewById(R.id.text_imagebutton);
        colorImageButton = findViewById(R.id.color_imagebutton);

        sendImageButton = findViewById(R.id.send_imagebutton);

        // Delete status bar and expand the layout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            final WindowInsetsController controller = getWindow().getInsetsController();
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

        // Set bottom margin for the bottom layout
        if (Build.VERSION.SDK_INT >= 19) {
            App.updateDeviceSizeVariables(this);

            ConstraintLayout.LayoutParams bottomRelativeLayoutLayoutParams = (ConstraintLayout.LayoutParams) bottomRelativeLayout.getLayoutParams();
            bottomRelativeLayoutLayoutParams.bottomMargin = App.navigationBarHeight;

            bottomRelativeLayout.setLayoutParams(bottomRelativeLayoutLayoutParams);
        }

        // Make send button appear/disappear on text changing
        statusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    sendImageButton.setVisibility(View.GONE);
                } else {
                    sendImageButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set font loop on button click
        fontLoop = new int[]{R.font.pragmatica, R.font.georgia,
                R.font.gelato_script, R.font.bubble_rainbow,
                R.font.triumvirate_inserat};
        fontLoopPos = 1;

        statusEditText.setTypeface(ResourcesCompat.getFont(NewStatusTextActivity.this, fontLoop[0]));

        fontImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusEditText.setTypeface(ResourcesCompat.getFont(NewStatusTextActivity.this, fontLoop[fontLoopPos]));

                if (fontLoopPos == (fontLoop.length - 1)) {
                    fontLoopPos = 0;
                } else {
                    fontLoopPos += 1;
                }
            }
        });

        // Set color loop on button click
        colorLoop = getResources().getIntArray(R.array.status_colors);
        colorLoopPos = 1;

        statusTextConstraintLayout.setBackgroundColor(colorLoop[0]);

        colorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTextConstraintLayout.setBackgroundColor(colorLoop[colorLoopPos]);

                if (colorLoopPos == (colorLoop.length - 1)) {
                    colorLoopPos = 0;
                } else {
                    colorLoopPos += 1;
                }
            }
        });

        EmojIconActions emojIcon = new EmojIconActions(this, emojiTextView, statusEditText, emojiImageButton);
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.ic_emoji_vector_24);
        emojIcon.ShowEmojIcon();
    }
}