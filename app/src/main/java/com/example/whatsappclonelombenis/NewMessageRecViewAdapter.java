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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewMessageRecViewAdapter extends RecyclerView.Adapter<NewMessageRecViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Contact> contacts= new ArrayList<>();

    public NewMessageRecViewAdapter(Context context) {
        this.context=context;
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
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newmessage_recview_item1,parent,false);
        }
        else if (viewType==1) {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newmessage_recview_item2,parent,false);
        }
        else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newmessage_recview_item,parent,false);
        }
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position!=0 && position!=1) {
            holder.name.setText(contacts.get(position).getName());
            holder.message.setText(contacts.get(position).getName());
            Glide.with(context)
                    .load(contacts.get(position).getProfilePicture())
                    .circleCrop()
                    .into(holder.profilePic);
        }
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts= contacts;
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, message;
        private ImageView profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.newmessageContactName);
            message=itemView.findViewById(R.id.newmessageContactInfo);
            profilePic=itemView.findViewById(R.id.newmessageContactProfilePic);
        }
    }
}
