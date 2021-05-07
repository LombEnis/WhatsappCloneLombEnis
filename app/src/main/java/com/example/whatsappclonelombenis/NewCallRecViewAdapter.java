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

public class NewCallRecViewAdapter extends RecyclerView.Adapter<NewCallRecViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Contact> contacts;

    public NewCallRecViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) return 0;
        else if (position==1) return 1;
        else return 2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==0) {
            view=LayoutInflater.from(context).inflate(R.layout.new_call_recview_item1, parent, false);
        }
        else if (viewType==1) {
            view= LayoutInflater.from(context).inflate(R.layout.new_call_recview_item2, parent, false);
        }
        else {
            view= LayoutInflater.from(context).inflate(R.layout.new_call_recview_item, parent, false);
        }
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position!=0 && position!=1) {
            Glide.with(context)
                    .load(contacts.get(position-2).getProfilePicture())
                    .circleCrop()
                    .into(holder.profilePic);
            holder.contactName.setText(contacts.get(position-2).getName());
            holder.contactInfo.setText(contacts.get(position-2).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size()+2;
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts=contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView contactName, contactInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic= itemView.findViewById(R.id.callNewContactProfileImg);
            contactName= itemView.findViewById(R.id.callNewContactName);
            contactInfo= itemView.findViewById(R.id.callNewContactInfo);
        }
    }
}
