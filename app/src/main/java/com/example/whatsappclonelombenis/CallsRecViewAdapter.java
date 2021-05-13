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

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views = new ArrayList<>();

    static ContextualToolbarListener contextualToolbarListener= new ContextualToolbarListener();
    static SelectListener selectListener= new SelectListener();
    static RemoveSelectedListener removeSelectedListener= new RemoveSelectedListener();
    static LongRemoveSelectedListener longRemoveSelectedListener= new LongRemoveSelectedListener();

    public CallsRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CallsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.calls_recview_item, parent, false);
        views.add(view);
        view.setOnLongClickListener(contextualToolbarListener);
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

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);

            View check = v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            MainActivity.actionBar.setVisibility(View.GONE);
            MainActivity.callsContextualToolbar.setVisibility(View.VISIBLE);
            MainActivity.tabLayout.setBackgroundResource(R.color.contextual_background_color);

            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));
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
            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.callsContextualToolbar.setVisibility(View.GONE);
                MainActivity.tabLayout.setBackgroundResource(R.color.purple_500);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(selectListener);
                v.setOnLongClickListener(contextualToolbarListener);
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
            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            if (selected_views.size() == 0) {
                MainActivity.actionBar.setVisibility(View.VISIBLE);
                MainActivity.callsContextualToolbar.setVisibility(View.GONE);
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
            View check=v.findViewById(R.id.callsSelectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null);
            if (!selected_views.contains(v)) {
                selected_views.add(v);
            }
            MainActivity.callsContextualToolbar.setTitle(Integer.toString(selected_views.size()));

            for(View view: selected_views) {
                view.setOnClickListener(removeSelectedListener);
                view.setOnLongClickListener(longRemoveSelectedListener);
            }
        }
    }
}
