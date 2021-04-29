package com.example.whatsappclonelombenis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StatusRecViewAdapter extends RecyclerView.Adapter<StatusRecViewAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private Context context;

    public StatusRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StatusRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_item, parent, false);;
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusRecViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(contacts.get(position).getProfilePicture())
                .into(holder.profileImageButton);


        holder.nameTextView.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton profileImageButton;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageButton = itemView.findViewById(R.id.profile_image_button);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }
}
