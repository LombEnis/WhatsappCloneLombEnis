package com.example.whatsappclonelombenis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChatRecViewAdapter extends RecyclerView.Adapter<ChatRecViewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Contact> contacts= new ArrayList<>();

    private ActionMode mActionMode;

    public ChatRecViewAdapter(Context context) {this.context=context;}

    @NonNull
    @Override
    public ChatRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recview_item, parent, false);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.actionBar.setVisibility(View.GONE);
                MainActivity.contextualToolbar.setVisibility(View.VISIBLE);
                MainActivity.tabLayout.setBackgroundResource(R.color.contextual_background_color);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.actionBar.setVisibility(View.VISIBLE);
                        MainActivity.contextualToolbar.setVisibility(View.GONE);
                        MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
                    }
                });
                return true;
            }
        });
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecViewAdapter.ViewHolder holder, int position) {
        holder.txtNameContact.setText(contacts.get(position).getName());
        holder.txtTime.setText("time");
        holder.txtMessageContact.setText(contacts.get(position).getMessage());
        //holder.profileImg.setImageResource(R.drawable.ic_default_avatar);
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

    //Contextual Action Bar
    public ActionMode.Callback mActionModeCallback= new ActionMode.Callback() {
        //This inflation occurs only when ActionMode is created
        @SuppressLint("ResourceType")
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.chat_contextual_action_bar, menu);

            return true;
        }

        //Always called after onCreateActionMode, each time ActionMode occurs
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        //Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode=null;
        }
    };

}
