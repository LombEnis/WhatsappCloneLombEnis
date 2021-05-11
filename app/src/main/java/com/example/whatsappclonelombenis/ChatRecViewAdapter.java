package com.example.whatsappclonelombenis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatRecViewAdapter extends RecyclerView.Adapter<ChatRecViewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Contact> contacts= new ArrayList<>();
    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();

    static ContextualToolbarListener contextualToolbarListener = new ContextualToolbarListener();
    static RemoveSelectedListener removeSelectedListener= new RemoveSelectedListener();
    static SelectListener selectListener= new SelectListener();
    static LongRemoveSelectedListener longRemoveSelectedListener= new LongRemoveSelectedListener();

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameContact= itemView.findViewById(R.id.chatNameContact);
            txtMessageContact= itemView.findViewById(R.id.chatMessage);
            txtTime= itemView.findViewById(R.id.chatTime);
            profileImg= itemView.findViewById(R.id.chatProfileImg);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);

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
            //v.setOnLongClickListener(contextualToolbarListener);
            return true;
        }
    }

    public static class RemoveSelectedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(selectListener);
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
            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.chatContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                //v.setOnClickListener(selectListener);
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
            View check=v.findViewById(R.id.selectedCheck);
            check.setVisibility(View.VISIBLE);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
            }
            MainActivity.chatContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(removeSelectedListener);
            }
        }
    }
}

//TODO add contextual toolbar on chatTab and new message activity