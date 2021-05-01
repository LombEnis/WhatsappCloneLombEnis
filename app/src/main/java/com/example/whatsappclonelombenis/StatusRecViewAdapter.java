package com.example.whatsappclonelombenis;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StatusRecViewAdapter extends RecyclerView.Adapter<StatusRecViewAdapter.ViewHolder> {
    private String myProfilePicture;
    private ArrayList<Contact> contacts;
    private Context context;

    public StatusRecViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        else return 2;
    }

    @NonNull
    @Override
    public StatusRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_first_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_item, parent, false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusRecViewAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            Glide.with(context)
                    .load(myProfilePicture)
                    .circleCrop()
                    .into(holder.myProfileImageButton);
        } else {
            Glide.with(context)
                    .load(contacts.get(position - 1).getProfilePicture())
                    .circleCrop()
                    .into(holder.profileImageButton);


            holder.nameTextView.setText(contacts.get(position - 1).getName());
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size() + 1;
    }

    public void setMyProfilePicture(String myProfilePicture) {
        this.myProfilePicture = myProfilePicture;
        notifyDataSetChanged();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton myProfileImageButton;

        private ImageButton profileImageButton;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myProfileImageButton = itemView.findViewById(R.id.my_profile_image_button);

            profileImageButton = itemView.findViewById(R.id.profile_image_button);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }
}
