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

    public ChatRecViewAdapter(Context context) {this.context=context;}

    @NonNull
    @Override
    public ChatRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recview_item, parent, false);
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

}
