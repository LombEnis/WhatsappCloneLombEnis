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

public class NewGroupCallRecViewAdapter extends RecyclerView.Adapter<NewGroupCallRecViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Contact> contacts;

    public NewGroupCallRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewGroupCallRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_contacts_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewGroupCallRecViewAdapter.ViewHolder holder, int position) {
        holder.info.setText(contacts.get(position).getMessage());
        holder.name.setText(contacts.get(position).getName());
        Glide.with(context)
                .load(contacts.get(position).getProfilePicture())
                .circleCrop()
                .into(holder.profilePic);
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts=contacts;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, info;
        ImageView profilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.allContactsName);
            info= itemView.findViewById(R.id.allContactsInfo);
            profilePic= itemView.findViewById(R.id.allContactsProfilePic);
        }
    }
}