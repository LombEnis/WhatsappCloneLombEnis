package com.example.whatsappclonelombenis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallInfoRecViewAdapter extends RecyclerView.Adapter<CallInfoRecViewAdapter.ViewHolder>{
    private Context context;

    private ArrayList<Call> calls;

    public CallInfoRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CallInfoRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_info_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallInfoRecViewAdapter.ViewHolder holder, int position) {
        if (calls.get(position).isCallAccepted() && calls.get(position).isIncomingCall()) {
            holder.callInfo.setText(R.string.incoming);
            holder.callImageInfo.setImageResource(R.drawable.ic_call_received_green);
        }
        else if ((!calls.get(position).isCallAccepted()) && calls.get(position).isIncomingCall()) {
            holder.callInfo.setText(R.string.lost);
            holder.callImageInfo.setImageResource(R.drawable.ic_call_received_red);
        }
        else if (!calls.get(position).isIncomingCall()) {
            holder.callInfo.setText(R.string.outgoing);
            holder.callImageInfo.setImageResource(R.drawable.ic_call_made_green);
        }

        Date callDate = calls.get(position).getDate().getTime();
        String callTimeText=new SimpleDateFormat("HH").format(callDate) +":"
                + new SimpleDateFormat("mm").format(callDate);
        holder.callTime.setText(callTimeText);

        holder.callDuration.setText(Integer.toString(calls.get(position).getCallDuration()));
        holder.callMB.setText(Double.toString(calls.get(position).getCallMB()));
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public void setData(ArrayList<Call> calls) {
        this.calls=calls;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView callInfo, callTime, callDuration, callMB;
        private ImageView callImageInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            callInfo= itemView.findViewById(R.id.callContactInfoCallInfo);
            callTime= itemView.findViewById(R.id.callContactInfoCallTime);
            callDuration= itemView.findViewById(R.id.callContactInfoCallDuration);
            callMB= itemView.findViewById(R.id.callContactInfoCallMB);
            callImageInfo= itemView.findViewById(R.id.callContactInfoImageInfo);
        }
    }
}
