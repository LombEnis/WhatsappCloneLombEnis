package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class NewStatusTextActivity extends AppCompatActivity {
    private ConstraintLayout statusTextConstraintLayout;

    private EditText statusEditText;

    private ImageButton emojiImageButton;
    private ImageButton textImageButton;
    private ImageButton colorImageButton;

    private ImageButton sendImageButton;

    private int[] colorLoop;
    private int colorLoopPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status_text);

        // Instante view
        statusTextConstraintLayout = findViewById(R.id.status_text_constraintlayout);

        statusEditText = findViewById(R.id.status_edittext);

        emojiImageButton = findViewById(R.id.emoji_imagebutton);
        textImageButton = findViewById(R.id.text_imagebutton);
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

        // Set color loop on button click
        colorLoop = getResources().getIntArray(R.array.status_colors);
        colorLoopPos = 0;

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
    }
}