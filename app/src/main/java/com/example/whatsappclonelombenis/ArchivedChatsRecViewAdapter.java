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

public class ArchivedChatsRecViewAdapter extends RecyclerView.Adapter<ArchivedChatsRecViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Contact> contacts= new ArrayList<>();

    static ArrayList<View> views= new ArrayList<>();

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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNameContact, txtMessageContact, txtTime;
        private ImageView profileImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameContact=itemView.findViewById(R.id.archivedChatsContactName);
            txtMessageContact=itemView.findViewById(R.id.archivedChatsMessage);
            txtTime=itemView.findViewById(R.id.archivedChatsTime);
            profileImg=itemView.findViewById(R.id.archivedChatsProfileImg);
        }
    }
}
