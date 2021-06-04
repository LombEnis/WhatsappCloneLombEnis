package com.example.whatsappclonelombenis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;

public class StatusViewsDialogRecViewAdapter extends RecyclerView.Adapter<StatusViewsDialogRecViewAdapter.ViewHolder> {

    private Story currentStory;
    private Context context;

    public StatusViewsDialogRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact currentViewsContact = (Contact) currentStory.getViewsContacts().get(position)[0];
        Calendar currentViewsDate = (Calendar) currentStory.getViewsContacts().get(position)[1];

        // Set profile image
        Glide.with(context)
                .load(currentViewsContact.getProfilePicture())
                .circleCrop()
                .into(holder.viewsDialogPreviewImageView);

        // Set title
        holder.viewsDialogTitleTextView.setText(currentViewsContact.getName());

        // Set subtitle
        holder.viewsDialogSubtitleTextView.setText(App.getDateString(currentViewsDate));
    }

    @Override
    public int getItemCount() {
        return currentStory.getViewsContacts().size();
    }

    public void setViewsContacts(Story currentStory) {
        this.currentStory = currentStory;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout viewsDialogConstraintLayout;
        private ImageView viewsDialogPreviewImageView;
        private TextView viewsDialogTitleTextView;
        private TextView viewsDialogSubtitleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewsDialogConstraintLayout = itemView.findViewById(R.id.status_constraint_layout);
            viewsDialogPreviewImageView = itemView.findViewById(R.id.status_preview_imageview);
            viewsDialogTitleTextView = itemView.findViewById(R.id.status_title_text_view);
            viewsDialogSubtitleTextView = itemView.findViewById(R.id.status_subtitle_text_view);
        }
    }
}
