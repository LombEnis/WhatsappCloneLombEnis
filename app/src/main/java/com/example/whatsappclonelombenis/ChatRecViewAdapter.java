package com.example.whatsappclonelombenis;

import android.content.Context;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRecViewAdapter extends RecyclerView.Adapter<ChatRecViewAdapter.ViewHolder>{
    private static Context context;

    static ArrayList<Contact> contacts= new ArrayList<>();
    static ArrayList<Contact> selected_contacts= new ArrayList<>();

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();

    static HashMap<View, Contact> selected_views_contacts= new HashMap<View, Contact>();

    static ContextualToolbarListener contextualToolbarListener;
    static RemoveSelectedListener removeSelectedListener;
    static SelectListener selectListener;
    static LongRemoveSelectedListener longRemoveSelectedListener;

    //final Vibrator vibe= (Vibrator) ChatRecViewAdapter.this.getSystemService(Context.VIBRATOR_SERVICE);

    public ChatRecViewAdapter(Context context) {this.context=context;}

    @NonNull
    @Override
    public ChatRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recview_item, parent, false);
        views.add(view);
        view.setOnLongClickListener(contextualToolbarListener);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecViewAdapter.ViewHolder holder, int position) {
        holder.txtNameContact.setText(contacts.get(position).getName());
        holder.txtTime.setText("time");
        holder.txtMessageContact.setText(contacts.get(position).getMessage());
        Glide.with(context)
                .load(contacts.get(position).getProfilePicture())
                .circleCrop()
                .into(holder.profileImg);
        View view= views.get(holder.getAdapterPosition());

        //Click listeners
        contextualToolbarListener= new ContextualToolbarListener(holder);
        removeSelectedListener= new RemoveSelectedListener(holder);
        selectListener= new SelectListener(holder);
        longRemoveSelectedListener= new LongRemoveSelectedListener(holder);

        view.setOnLongClickListener(contextualToolbarListener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts=contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameContact, txtMessageContact, txtTime;
        private ImageView profileImg;
        private int pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameContact= itemView.findViewById(R.id.chatNameContact);
            txtMessageContact= itemView.findViewById(R.id.chatMessage);
            txtTime= itemView.findViewById(R.id.chatTime);
            profileImg= itemView.findViewById(R.id.chatProfileImg);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        ViewHolder holder;

        public ContextualToolbarListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);
            selected_views_contacts.put(v, contacts.get(holder.getAdapterPosition()));
            System.out.println(selected_views_contacts);

            View check = v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            MainActivity.actionBar.setVisibility(View.GONE);
            MainActivity.chatContextualToolbar.setVisibility(View.VISIBLE);
            MainActivity.tabLayout.setBackgroundResource(R.color.contextual_background_color);

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));
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
        ViewHolder holder;

        public RemoveSelectedListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);
            System.out.println(selected_views_contacts); ////

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
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
        ViewHolder holder;

        public LongRemoveSelectedListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onLongClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.GONE);

            selected_views.remove(v);
            selected_views_contacts.remove(v);
            System.out.println(selected_views_contacts); ////

            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
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
        ViewHolder holder;

        public SelectListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
                selected_views_contacts.put(v, contacts.get(holder.getAdapterPosition()));
                System.out.println(selected_views_contacts); ////
            }
            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(removeSelectedListener);
                view.setOnLongClickListener(longRemoveSelectedListener);
            }
        }
    }
}
