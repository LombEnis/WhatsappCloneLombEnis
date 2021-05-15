package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CallInfoActivity extends AppCompatActivity {
    //Toolbar
    Toolbar callInfoToolbar;

    TextView contactNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_info);

        callInfoToolbar=findViewById(R.id.callInfoToolbar);
        setSupportActionBar(callInfoToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent= getIntent();

        //EXTRAS
        String contactName= intent.getStringExtra(CallsRecViewAdapter.EXTRA_NAME);
        /*String contactInfo= intent.getStringExtra(CallsRecViewAdapter.EXTRA_INFO);
        String callDay= intent.getStringExtra(CallsRecViewAdapter.EXTRA_DAY);
        boolean isAcceptedCall= intent.getBooleanExtra(CallsRecViewAdapter.EXTRA_IS_ACCEPTED, false);
        boolean isIncomingCall= intent.getBooleanExtra(CallsRecViewAdapter.EXTRA_IS_INCOMING, false);*/

        contactNameView=findViewById(R.id.callContactInfoName);
        contactNameView.setText(contactName);

        System.out.println(contactName);

        //TODO: adjust not transporting data
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