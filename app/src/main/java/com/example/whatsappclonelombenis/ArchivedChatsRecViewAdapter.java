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

public class ArchivedChatsRecViewAdapter extends RecyclerView.Adapter<ArchivedChatsRecViewAdapter.ViewHolder> {
    Context context;

    static ArrayList<Contact> contacts= new ArrayList<>();
    static HashMap<View, Contact> selected_views_contacts= new HashMap<View, Contact>();

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();

    public ArchivedChatsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ArchivedChatsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.archived_chats_recview_item, parent, false);
        views.add(view);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedChatsRecViewAdapter.ViewHolder holder, int position) {
        holder.txtNameContact.setText(contacts.get(position).getName());

        //Setting last message time
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

        //LongClick listener
        ContextualToolbarListener contextualToolbarListener= new ContextualToolbarListener();
        holder.rootLayout.setOnLongClickListener(contextualToolbarListener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts=contacts;
        Collections.sort(this.contacts, new ContactsDateComparator());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameContact, txtMessageContact, txtTime;
        private ImageView profileImg;
        private ConstraintLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameContact=itemView.findViewById(R.id.archivedChatsContactName);
            txtMessageContact=itemView.findViewById(R.id.archivedChatsMessage);
            txtTime=itemView.findViewById(R.id.archivedChatsTime);
            profileImg=itemView.findViewById(R.id.archivedChatsProfileImg);
            rootLayout=itemView.findViewById(R.id.archivedChatsItemLayout);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);
            selected_views_contacts.put(v, contacts.get(ArchivedChatsActivity.archivedLinearLayoutManager.getPosition(v)));

            View check = v.findViewById(R.id.archivedChatSelectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            ArchivedChatsActivity.toolbar.setVisibility(View.GONE);
            ArchivedChatsActivity.contextualToolbar.setVisibility(View.VISIBLE);

            ArchivedChatsActivity.contextualToolbar.setTitle(Integer.toString(selected_views.size()));
            for (View view:views) {
                if (selected_views.contains(view)) {
                    view.setOnClickListener(new RemoveSelectedListener());
                    view.setOnLongClickListener(new LongRemoveSelectedListener());
                }
                else {
                    view.setOnClickListener(new SelectListener());
                }
            }
            return true;
        }
    }

    public static class RemoveSelectedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.archivedChatSelectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);

            ArchivedChatsActivity.contextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                ArchivedChatsActivity.toolbar.setVisibility(View.VISIBLE);
                ArchivedChatsActivity.contextualToolbar.setVisibility(View.GONE);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(new SelectListener());
                v.setOnLongClickListener(new ContextualToolbarListener());
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(new ContextualToolbarListener());
                }
            }

        }
    }

    public static class LongRemoveSelectedListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            View check=v.findViewById(R.id.archivedChatSelectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);

            ArchivedChatsActivity.contextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                ArchivedChatsActivity.toolbar.setVisibility(View.VISIBLE);
                ArchivedChatsActivity.contextualToolbar.setVisibility(View.GONE);
            }

            if (selected_views.size()!=0) {
                v.setOnLongClickListener(new ContextualToolbarListener());
            }else {
                for (View view : views) {
                    view.setOnClickListener(null);
                    view.setOnLongClickListener(new ContextualToolbarListener());
                }
            }

            return false;
        }
    }

    public static class SelectListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.archivedChatSelectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
                selected_views_contacts.put(v, contacts.get(ArchivedChatsActivity.archivedLinearLayoutManager.getPosition(v)));
            }
            ArchivedChatsActivity.contextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(new RemoveSelectedListener());
                view.setOnLongClickListener(new LongRemoveSelectedListener());
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
