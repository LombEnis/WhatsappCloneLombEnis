package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyStatusRecViewAdapter extends RecyclerView.Adapter<MyStatusRecViewAdapter.ViewHolder> {
    private ArrayList<Story> myStories = new ArrayList<>();
    private Context context;

    public MyStatusRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_status_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story currentStory = myStories.get(position);

        // Set preview image view
        Glide.with(context)
                .load(currentStory.getStoryPreviewBitmap())
                .circleCrop()
                .into(holder.previewImageView);

        // Set title text
        holder.titleTextView.setText(context.getString(R.string.my_status_story_views,
                currentStory.getViews()));

        // Set subtitle text
        holder.subtitleTextView.setText(App.getDateString(currentStory.getDate()));

        // Set on click listener
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoriesActivity.class);
                intent.putExtra("contactsType", 0);
                intent.putExtra("position", position);

                context.startActivity(intent);
            }
        });

        holder.previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.performClick();
                App.performRipple(holder.constraintLayout);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myStories.size();
    }

    public void setMyStories(ArrayList<Story> myStories) {
        this.myStories = myStories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private ImageView previewImageView;
        private TextView titleTextView;
        private TextView subtitleTextView;
        private ImageButton threeDotsImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.my_status_constraint_layout);
            previewImageView = itemView.findViewById(R.id.my_status_preview_imageview);
            titleTextView = itemView.findViewById(R.id.my_Status_title_text_view);
            subtitleTextView = itemView.findViewById(R.id.my_status_subtitle_text_view);
            threeDotsImageButton = itemView.findViewById(R.id.my_Status_three_dots_imagebutton);
        }
    }
}
