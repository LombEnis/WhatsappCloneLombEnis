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

public class CallsRecViewAdapter extends RecyclerView.Adapter<CallsRecViewAdapter.ViewHolder>{
    Context context;

    ArrayList<Call> calls;

    public CallsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CallsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.calls_recview_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallsRecViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(calls.get(position).getContact().getProfilePicture())
                .circleCrop()
                .into(holder.profilePic);
        holder.name.setText(calls.get(position).getContact().getName());
        holder.time.setText(calls.get(position).getCallTime());

        //Call info
        if (calls.get(position).isCallAccepted() && calls.get(position).isIncomingCall()) {
            holder.callInfo.setImageResource(R.drawable.ic_call_received_green);
        }
        else if ((!calls.get(position).isCallAccepted()) && calls.get(position).isIncomingCall()) {
            holder.callInfo.setImageResource(R.drawable.ic_call_received_red);
        }
        else if (calls.get(position).isCallAccepted() && (!calls.get(position).isIncomingCall())) {
            holder.callInfo.setImageResource(R.drawable.ic_call_made_green);
        }
        else {
            holder.callInfo.setImageResource(R.drawable.ic_call_made_red);
        }

        //VideoCall or VoiceCall
        if (calls.get(position).isVoiceCall()) {
            holder.voiceOrVideoCall.setImageResource(R.drawable.ic_call);
        }
        else {
            holder.voiceOrVideoCall.setImageResource(R.drawable.ic_videocall);
        }
    }

    public void setData(ArrayList<Call> calls) {
        this.calls=calls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic, callInfo, voiceOrVideoCall;
        TextView name, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.callProfileImg);
            callInfo=itemView.findViewById(R.id.callInfo);
            name=itemView.findViewById(R.id.callContactName);
            time=itemView.findViewById(R.id.callTime);
            voiceOrVideoCall=itemView.findViewById(R.id.callImageView);

            voiceOrVideoCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("dsad");
                }
            });
        }
    }
}
