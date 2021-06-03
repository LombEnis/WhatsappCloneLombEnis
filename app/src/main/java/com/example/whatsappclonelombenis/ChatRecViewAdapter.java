package com.example.whatsappclonelombenis;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class ChatRecViewAdapter extends RecyclerView.Adapter<ChatRecViewAdapter.ViewHolder>{
    private static Context context;

    static ArrayList<Contact> contacts= new ArrayList<>();

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();

    static HashMap<View, Contact> selected_views_contacts= new HashMap<View, Contact>();
    static HashMap<View, ViewHolder> selected_views_holders= new HashMap<View, ViewHolder>();

    static ContextualToolbarListener contextualToolbarListener;
    static RemoveSelectedListener removeSelectedListener;
    static SelectListener selectListener;
    static LongRemoveSelectedListener longRemoveSelectedListener;

    public ChatRecViewAdapter(Context context) {this.context=context;}

    @NonNull
    @Override
    public ChatRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recview_item, parent, false);
        views.add(view);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecViewAdapter.ViewHolder holder, int position) {
        holder.txtNameContact.setText(contacts.get(position).getName());

        //Setting time of last message
        Date date= contacts.get(position).getDate().getTime();
        Calendar contactCalendar= contacts.get(position).getDate();

        Calendar previousCalendar= Calendar.getInstance();
        previousCalendar.add(Calendar.DATE, -1);

        if (contactCalendar.get(Calendar.DAY_OF_MONTH)==Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            Calendar timeCalendar= Calendar.getInstance();
            timeCalendar.set(Calendar.HOUR_OF_DAY, contactCalendar.get(Calendar.HOUR_OF_DAY));
            timeCalendar.set(Calendar.MINUTE, contactCalendar.get(Calendar.MINUTE));

            Date timeDate= timeCalendar.getTime();

            holder.txtTime.setText(new SimpleDateFormat("HH").format(timeDate)+":"+new SimpleDateFormat("mm").format(timeDate));
        }
        else if (contactCalendar.get(Calendar.DAY_OF_MONTH)==previousCalendar.get(Calendar.DAY_OF_MONTH)
        && contactCalendar.get(Calendar.MONTH)==previousCalendar.get(Calendar.MONTH)
        && contactCalendar.get(Calendar.YEAR)==previousCalendar.get(Calendar.YEAR)) {
            holder.txtTime.setText(context.getResources().getString(R.string.yesterday));
        }
        else {
            holder.txtTime.setText(new SimpleDateFormat("dd").format(date)+"/"
                    + new SimpleDateFormat("MM").format(date)+"/"
                    + new SimpleDateFormat("yyyy").format(date));
        }

        //The rest
        holder.txtMessageContact.setText(contacts.get(position).getMessage());
        Glide.with(context)
                .load(contacts.get(position).getProfilePicture())
                .circleCrop()
                .into(holder.profileImg);

        //Click listeners
        contextualToolbarListener= new ContextualToolbarListener(position);
        removeSelectedListener= new RemoveSelectedListener(position);
        selectListener= new SelectListener(position);
        longRemoveSelectedListener= new LongRemoveSelectedListener(position);

        holder.chatLayout.setOnLongClickListener(contextualToolbarListener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts.clear();
        for (Contact c : contacts) {
            if (!c.isArchived()) {
                this.contacts.add(c);
            }
        }
        Collections.sort(this.contacts, new ContactsDateComparator());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameContact, txtMessageContact, txtTime;
        private ImageView profileImg;
        private ConstraintLayout chatLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameContact= itemView.findViewById(R.id.chatContactName);
            txtMessageContact= itemView.findViewById(R.id.chatMessage);
            txtTime= itemView.findViewById(R.id.chatTime);
            profileImg= itemView.findViewById(R.id.chatProfileImg);
            chatLayout= itemView.findViewById(R.id.chatLayout);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        int position;

        public ContextualToolbarListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);
            selected_views_contacts.put(v, contacts.get(position));
            //selected_views_holders.put(v, holder);

            View check = v.findViewById(R.id.chatSelectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            MainActivity.actionBar.setVisibility(View.GONE);
            MainActivity.chatContextualToolbar.setVisibility(View.VISIBLE);
            MainActivity.tabLayout.setBackgroundResource(R.color.contextual_background_color);

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));
            for (View view:views) {
                if (selected_views.contains(view)) {
                    view.setOnClickListener(new RemoveSelectedListener(position));
                    view.setOnLongClickListener(new LongRemoveSelectedListener(position));
                }
                else {
                    view.setOnClickListener(new SelectListener(position));
                }
            }
            return true;
        }
    }

    public static class RemoveSelectedListener implements View.OnClickListener {
        int position;

        public RemoveSelectedListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.chatSelectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);
            selected_views_holders.remove(v);

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(new SelectListener(position));
                v.setOnLongClickListener(new ContextualToolbarListener(position));
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(new ContextualToolbarListener(position));
                }
            }

        }
    }

    public static class LongRemoveSelectedListener implements View.OnLongClickListener {
        int position;

        public LongRemoveSelectedListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            View check=v.findViewById(R.id.chatSelectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);
            selected_views_holders.remove(v);

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnLongClickListener(new ContextualToolbarListener(position));
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(new ContextualToolbarListener(position));
                }
            }

            return false;
        }
    }

    public static class SelectListener implements View.OnClickListener {
        int position;

        public SelectListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.chatSelectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
                selected_views_contacts.put(v, contacts.get(position));
                //selected_views_holders.put(v, holder);
            }
            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(new RemoveSelectedListener(position));
                view.setOnLongClickListener(new LongRemoveSelectedListener(position));
            }
        }
    }

    public class ContactsDateComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}
