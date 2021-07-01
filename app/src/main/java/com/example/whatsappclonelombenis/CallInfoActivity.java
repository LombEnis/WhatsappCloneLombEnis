package com.example.whatsappclonelombenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;

public class CallInfoActivity extends AppCompatActivity {
    //Toolbar
    Toolbar callInfoToolbar;

    //Views
    ImageView contactProfileImg, imageCallInfo;
    TextView contactNameView;
    TextView contactInfoView;
    TextView callDayView;
    TextView callInfoView;
    TextView callTimeView;

    RecyclerView callsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_info);

        callInfoToolbar=findViewById(R.id.callInfoToolbar);
        setSupportActionBar(callInfoToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent= getIntent();

        //RecView
        Calendar callCalendar1= Calendar.getInstance();
        callCalendar1.set(Calendar.HOUR_OF_DAY, 7);
        callCalendar1.set(Calendar.DAY_OF_MONTH, 7);

        Calendar callCalendar2= Calendar.getInstance();
        callCalendar2.set(Calendar.HOUR_OF_DAY, 15);

        callsRecView=findViewById(R.id.callContactInfoCallsRecView);

        ArrayList<Call> calls = new ArrayList<>();
       
        CallInfoRecViewAdapter recViewAdapter = new CallInfoRecViewAdapter(this);
        recViewAdapter.setData(calls);

        callsRecView.setAdapter(recViewAdapter);
        callsRecView.setLayoutManager(new LinearLayoutManager(this));

        //EXTRAS
        String contactImageUrl= intent.getStringExtra(CallsRecViewAdapter.EXTRA_IMAGE);
        String contactName= intent.getStringExtra(CallsRecViewAdapter.EXTRA_NAME);
        String contactInfo= intent.getStringExtra(CallsRecViewAdapter.EXTRA_INFO);
        boolean isAcceptedCall= intent.getBooleanExtra(CallsRecViewAdapter.EXTRA_IS_ACCEPTED, false);
        boolean isIncomingCall= intent.getBooleanExtra(CallsRecViewAdapter.EXTRA_IS_INCOMING, false);
        //getting Contact Calendar object
        long contactTimeInMillis= intent.getLongExtra(CallsRecViewAdapter.EXTRA_DATE, 0);
        Calendar contactCalendar= Calendar.getInstance();
        contactCalendar.setTimeInMillis(contactTimeInMillis);

        //Setting the EXTRA values to the Views
        contactProfileImg=findViewById(R.id.callContactInfoProfileImg);
        Glide.with(this)
                .load(contactImageUrl)
                .circleCrop()
                .into(contactProfileImg);

        contactNameView=findViewById(R.id.callContactInfoName);
        contactNameView.setText(contactName);

        contactInfoView= findViewById(R.id.callContactInfoInfo);
        contactInfoView.setText(contactInfo);

        //TODO: set call day and call time

        callDayView=findViewById(R.id.callContactInfoCallDay);
        //callDayView.setText(callDay);

        callTimeView=findViewById(R.id.callContactInfoCallTime);
        //callTimeView.setText(callTime);

        /*callInfoView=findViewById(R.id.callContactInfoCallInfo);
        imageCallInfo=findViewById(R.id.callContactInfoImageInfo);
        if (isAcceptedCall && isIncomingCall) {
            imageCallInfo.setImageResource(R.drawable.ic_call_received_green);
            callInfoView.setText(R.string.incoming);
        }
        else if (!isAcceptedCall && isIncomingCall) {
            imageCallInfo.setImageResource(R.drawable.ic_call_received_red);
            callInfoView.setText(R.string.lost);
        }
        else if (!isIncomingCall) {
            imageCallInfo.setImageResource(R.drawable.ic_call_made_green);
            callInfoView.setText(R.string.outgoing);
        }*/

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

        Bitmap newChatBitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_message), 68, 68, false);

        newChatItem.setIcon(new BitmapDrawable(getResources(), newChatBitmap));

        //TODO: add to notes
        return super.onCreateOptionsMenu(menu);
    }
}