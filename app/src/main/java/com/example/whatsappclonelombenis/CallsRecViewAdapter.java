package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CallsRecViewAdapter extends RecyclerView.Adapter<CallsRecViewAdapter.ViewHolder>{
    static Context context;
    static ArrayList<Call> calls;

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();
    static ArrayList<Object> allCalls;

    static ContextualToolbarListener contextualToolbarListener= new ContextualToolbarListener();
    static SelectListener selectListener= new SelectListener();
    static RemoveSelectedListener removeSelectedListener= new RemoveSelectedListener();
    static LongRemoveSelectedListener longRemoveSelectedListener= new LongRemoveSelectedListener();

    static OpenCallInfo openCallInfo = new OpenCallInfo();

    //EXTRAS
    public static final String EXTRA_IMAGE="url";
    public static final String EXTRA_NAME="name";
    public static final String EXTRA_INFO="info";
    public static final String EXTRA_IS_ACCEPTED="isAccepted";
    public static final String EXTRA_IS_INCOMING="isIncoming";
    public static final String EXTRA_DATE="date";
    public static final String EXTRA_CALL_INDEX="callIndex";

    public CallsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CallsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.calls_recview_item, parent, false);
        views.add(view);

        view.setOnLongClickListener(contextualToolbarListener);
        view.setOnClickListener(openCallInfo);

        //Checking if this call contains other calls
        if (allCalls.get(views.indexOf(view)) instanceof ArrayList) {
            TextView numberOfCalls = view.findViewById(R.id.numberInsideCalls);
            List callsList = new ArrayList<>((Collection<?>)allCalls.get(views.indexOf(view)));
            String text= "("+callsList.size()+")";
            numberOfCalls.setText(text);
            numberOfCalls.setVisibility(View.VISIBLE);
        }

        ImageView callImage= view.findViewById(R.id.callImageView);
        callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRipple(callImage);
            }
        });

        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallsRecViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(calls.get(position).getContact().getProfilePicture())
                .circleCrop()
                .into(holder.profilePic);
        holder.name.setText(calls.get(position).getContact().getName());

        //Setting call time
        Calendar currentCalendar = Calendar.getInstance();
        Calendar contactCalendar = calls.get(position).getDate();
        Date contactDate = contactCalendar.getTime();
        Calendar previousCalendar= Calendar.getInstance();
        previousCalendar.add(Calendar.DATE, -1);

        if (contactCalendar.get(Calendar.DAY_OF_MONTH)==currentCalendar.get(Calendar.DAY_OF_MONTH)
        && contactCalendar.get(Calendar.MONTH)==currentCalendar.get(Calendar.MONTH)
        && contactCalendar.get(Calendar.YEAR)==currentCalendar.get(Calendar.YEAR)) {
            String output=context.getResources().getString(R.string.today)+", "+ new SimpleDateFormat("HH").format(contactDate)+":"
                    +new SimpleDateFormat("mm").format(contactDate);
            holder.time.setText(output);
        }
        else if (contactCalendar.get(Calendar.DAY_OF_MONTH)==previousCalendar.get(Calendar.DAY_OF_MONTH)
        && contactCalendar.get(Calendar.MONTH)==previousCalendar.get(Calendar.MONTH)
        && contactCalendar.get(Calendar.YEAR)==previousCalendar.get(Calendar.YEAR)){
            String output=context.getResources().getString(R.string.yesterday)+", "+new SimpleDateFormat("HH").format(contactDate)+":"
                    +new SimpleDateFormat("mm").format(contactDate);
            holder.time.setText(output);
        }
        else if ((contactCalendar.get(Calendar.DAY_OF_MONTH)!=currentCalendar.get(Calendar.DAY_OF_MONTH)
                || contactCalendar.get(Calendar.MONTH)!=currentCalendar.get(Calendar.MONTH))
                && contactCalendar.get(Calendar.YEAR)==currentCalendar.get(Calendar.YEAR)) {
            String output=contactCalendar.get(Calendar.DAY_OF_MONTH) + " "+ new SimpleDateFormat("MMMM").format(contactDate)+", "+ new SimpleDateFormat("HH").format(contactDate)+":"
                    +new SimpleDateFormat("mm").format(contactDate);
            holder.time.setText(output);
        }
        else if (contactCalendar.get(Calendar.YEAR)!=currentCalendar.get(Calendar.YEAR)) {
            String output = new SimpleDateFormat("dd").format(contactDate)+"/"
                    + new SimpleDateFormat("MM").format(contactDate)+"/"
                    + new SimpleDateFormat("yy").format(contactDate)+", "+ new SimpleDateFormat("HH").format(contactDate)+":"
                    +new SimpleDateFormat("mm").format(contactDate);
            holder.time.setText(output);
        }

        //Call info
        if (calls.get(position).isCallAccepted() && calls.get(position).isIncomingCall()) {
            holder.callInfo.setImageResource(R.drawable.ic_call_received_green);
        }
        else if ((!calls.get(position).isCallAccepted()) && calls.get(position).isIncomingCall()) {
            holder.callInfo.setImageResource(R.drawable.ic_call_received_red);
        }
        else if (!calls.get(position).isIncomingCall()) {
            holder.callInfo.setImageResource(R.drawable.ic_call_made_green);
        }

        //VideoCall or VoiceCall
        if (calls.get(position).isVoiceCall()) {
            holder.voiceOrVideoCall.setImageResource(R.drawable.ic_call);
        }
        else {
            holder.voiceOrVideoCall.setImageResource(R.drawable.ic_videocall);
        }
    }

    public void setData(ArrayList<Call> rawCalls) {
        views.clear();

        allCalls= new ArrayList<>();
        ArrayList<Call> filteredCalls= new ArrayList<>();
        ArrayList<Call> addingArr = new ArrayList<>();

        Collections.sort(rawCalls, new ContactsDateComparator());

        //Storing all calls
        for (int i=0;  i<rawCalls.size()-1; i++ ) {
            if (isSameDayAndContact(rawCalls, i)
            && rawCalls.get(i).isIncomingCall()==rawCalls.get(i+1).isIncomingCall()
            && rawCalls.get(i).isCallAccepted()==rawCalls.get(i+1).isCallAccepted()
            || (isSameDayAndContact(rawCalls, i) && rawCalls.get(i).isCallAccepted() && rawCalls.get(i).isIncomingCall()
                    && !rawCalls.get(i+1).isIncomingCall())
            || (isSameDayAndContact(rawCalls, i) && !rawCalls.get(i).isIncomingCall() && rawCalls.get(i+1).isCallAccepted()
                    && rawCalls.get(i+1).isIncomingCall())) {
                if (!addingArr.contains(rawCalls.get(i))) {
                    addingArr.add(rawCalls.get(i));
                }
                addingArr.add(rawCalls.get(i+1));
            }
            else {
                if (addingArr.size()>1) {
                    ArrayList<Call> copyingArrayList= new ArrayList<>();
                    for (int z=0; z<addingArr.size(); z++) {
                        Call newOne= addingArr.get(z);
                        copyingArrayList.add(newOne);
                    }
                    allCalls.add(copyingArrayList);
                    addingArr.clear();
                }
                else {
                    allCalls.add(rawCalls.get(i));
                }
            }
        }
        if (addingArr.size()==0){
            allCalls.add(rawCalls.get(rawCalls.size()-1));
        }else{
            ArrayList<Call> copyingArrayList= new ArrayList<>();
            for (int z=0; z<addingArr.size(); z++) {
                Call newOne= addingArr.get(z);
                copyingArrayList.add(newOne);
            }
            allCalls.add(copyingArrayList);
        }

        //Filtering
        for (Object o : allCalls) {
            if (o instanceof ArrayList) {
                List<Call> list = new ArrayList<>((Collection<Call>)o);
                filteredCalls.add(list.get(0));
            }
            else {
                Call call = (Call) o;
                filteredCalls.add(call);
            }
        }
        this.calls=filteredCalls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic, callInfo, voiceOrVideoCall;
        TextView name, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.callProfileImg);
            callInfo=itemView.findViewById(R.id.callInfo);
            name=itemView.findViewById(R.id.callContactName);
            time=itemView.findViewById(R.id.callTime);
            voiceOrVideoCall=itemView.findViewById(R.id.callImageView);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);

            View check = v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            MainActivity.actionBar.setVisibility(View.GONE);
            MainActivity.callsContextualToolbar.setVisibility(View.VISIBLE);
            MainActivity.tabLayout.setBackgroundResource(R.color.contextual_background_color);

            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));
            for (View view:views) {
                if (selected_views.contains(view)) {
                    view.setOnClickListener(removeSelectedListener);
                    view.setOnLongClickListener(longRemoveSelectedListener);
                }
                else {
                    view.setOnClickListener(selectListener);
                }
            }
            return true;
        }
    }

    public static class RemoveSelectedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.callsContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(selectListener);
                v.setOnLongClickListener(contextualToolbarListener);
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(contextualToolbarListener);
                }
            }

        }
    }

    public static class LongRemoveSelectedListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.callsContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnLongClickListener(contextualToolbarListener);
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(contextualToolbarListener);
                }
            }

            return false;
        }
    }

    public static class SelectListener
            implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
            }
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(removeSelectedListener);
                view.setOnLongClickListener(longRemoveSelectedListener);
            }
        }
    }

    public static class OpenCallInfo implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent showCallInfoIntent= new Intent(context, CallInfoActivity.class);

            showCallInfoIntent.putExtra(EXTRA_IMAGE, calls.get(views.indexOf(v)).getContact().getProfilePicture());
            showCallInfoIntent.putExtra(EXTRA_NAME, calls.get(views.indexOf(v)).getContact().getName());
            showCallInfoIntent.putExtra(EXTRA_INFO, calls.get(views.indexOf(v)).getContact().getMessage());
            /*showCallInfoIntent.putExtra(EXTRA_DATE, calls.get(views.indexOf(v)).getDate().getTimeInMillis());
            showCallInfoIntent.putExtra(EXTRA_IS_ACCEPTED, calls.get(views.indexOf(v)).isCallAccepted());
            showCallInfoIntent.putExtra(EXTRA_IS_INCOMING, calls.get(views.indexOf(v)).isIncomingCall());*/

            showCallInfoIntent.putExtra(EXTRA_CALL_INDEX, TabCallsFragment.mCallsLinearLayoutManager.getPosition(v));
            context.startActivity(showCallInfoIntent);
        }
    }

    public void performRipple(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            RippleDrawable rippleDrawable = (RippleDrawable) view.getBackground();

            rippleDrawable.setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});

            Handler exitRippleHandler = new Handler();
            exitRippleHandler.postDelayed(new Runnable()
            {
                @Override public void run()
                {
                    rippleDrawable.setState(new int[]{});
                }
            }, 200);
        }
    }

    public class ContactsDateComparator implements Comparator<Call> {
        @Override
        public int compare(Call o1, Call o2) {
            if (o1.getDate().compareTo(o2.getDate())==-1) {
                return 1;
            }
            else if (o1.getDate().compareTo(o2.getDate())==1) {
                return -1;
            }
            else{
                return 0;
            }
        }
    }

    public boolean isSameDayAndContact(ArrayList<Call> rawCalls, int i) {
        return rawCalls.get(i).getContact().getName().equals(rawCalls.get(i + 1).getContact().getName())
                && rawCalls.get(i).getDate().get(Calendar.DAY_OF_MONTH)==rawCalls.get(i+1).getDate().get(Calendar.DAY_OF_MONTH)
                && rawCalls.get(i).getDate().get(Calendar.MONTH)==rawCalls.get(i+1).getDate().get(Calendar.MONTH)
                && rawCalls.get(i).getDate().get(Calendar.YEAR)==rawCalls.get(i+1).getDate().get(Calendar.YEAR);
    }

}
