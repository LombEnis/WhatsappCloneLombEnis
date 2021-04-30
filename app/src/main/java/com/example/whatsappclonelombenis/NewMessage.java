package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

public class NewMessage extends AppCompatActivity {
    Toolbar newMessageToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        newMessageToolbar=findViewById(R.id.newMessageActivityToolbar);
        setSupportActionBar(newMessageToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newmessage_menu, menu);
        return true;
    }
}