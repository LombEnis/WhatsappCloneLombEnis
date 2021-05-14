package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class CallInfoActivity extends AppCompatActivity {
    //Toolbar
    Toolbar callInfoToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_info);

        callInfoToolbar=findViewById(R.id.callInfoToolbar);
        setSupportActionBar(callInfoToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_info_menu, menu);

        MenuItem newChatItem= menu.findItem(R.id.new_message);
        /*View newChatView= newChatItem.getActionView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24,24);
        newChatView.setLayoutParams(params);*/

        Bitmap newChatBitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_message), 68, 68, false);

        newChatItem.setIcon(new BitmapDrawable(getResources(), newChatBitmap));

        return super.onCreateOptionsMenu(menu);
    }
}