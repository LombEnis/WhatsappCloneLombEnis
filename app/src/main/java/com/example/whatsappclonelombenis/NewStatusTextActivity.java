package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NewStatusTextActivity extends AppCompatActivity {
    private ConstraintLayout statusTextConstraintLayout;

    private ImageButton emojiImageButton;
    private ImageButton textImageButton;
    private ImageButton colorImageButton;

    private int[] colorLoop;
    private int colorLoopPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status_text);

        statusTextConstraintLayout = findViewById(R.id.status_text_constraintlayout);

        emojiImageButton = findViewById(R.id.emoji_imagebutton);
        textImageButton = findViewById(R.id.text_imagebutton);
        colorImageButton = findViewById(R.id.color_imagebutton);

        colorLoop = getResources().getIntArray(R.array.status_colors);
        colorLoopPos = 0;

        colorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTextConstraintLayout.setBackgroundColor(colorLoop[colorLoopPos]);

                if (colorLoopPos == (colorLoop.length - 1)) {
                    colorLoopPos = 0;
                } else {
                    colorLoopPos += 1;}
            }
        });
    }
}