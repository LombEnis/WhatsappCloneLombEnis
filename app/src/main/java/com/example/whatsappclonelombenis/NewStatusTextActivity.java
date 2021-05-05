package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class NewStatusTextActivity extends AppCompatActivity {
    private ConstraintLayout statusTextConstraintLayout;

    private EmojiconEditText statusEditText;
    private EmojiconTextView emojiTextView;

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

        emojiImageButton = findViewById(R.id.emoji_imagebutton);
        fontImageButton = findViewById(R.id.text_imagebutton);
        colorImageButton = findViewById(R.id.color_imagebutton);

        sendImageButton = findViewById(R.id.send_imagebutton);

        // Appear/Disappear send button on text changing
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