package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlomi.circularstatusview.CircularStatusView;

import java.util.ArrayList;

public class StatusRecViewAdapter extends RecyclerView.Adapter<StatusRecViewAdapter.ViewHolder> {
    private String myProfilePicture;
    public static ArrayList<Contact> contacts;
    public static ArrayList<Contact> recentContacts;
    public static ArrayList<Contact> seenContacts;
    public static ArrayList<Contact> disabledContacts;
    private Context context;

    public StatusRecViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // Return different viewType depending on position
        if (position == 0) return 1;
        else if (position == 1
                || position == (recentContacts.size() + 2)
                || position == (recentContacts.size() + seenContacts.size() + 3))
            return 2;
        else return 3;
    }

    @NonNull
    @Override
    public StatusRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate different item layout depending on position
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_first_item, parent, false);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_divider, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_item, parent, false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusRecViewAdapter.ViewHolder holder, int position) {
        // Set different view depending on position
        if (position == 0) {
            Glide.with(context)
                    .load(myProfilePicture)
                    .circleCrop()
                    .into(holder.myProfileImageButton);

            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(true);
                }
            });

            holder.myProfileImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.constraintLayout.performClick();
                    performRipple(holder.constraintLayout);
                }
            });
        } else if (recentContacts.size() > 0 && position < (recentContacts.size() + 2)) {
            // Recent contacts
            if (position == 1) {
                holder.statusDividerTextView.setText(R.string.aggiornamenti_recenti);
            } else {
                Glide.with(context)
                        .load(recentContacts.get(position - 2)
                                .getStatusStories()
                                .get(recentContacts.get(position - 2).getLastStoriesPos())
                                .getStoryPreviewBitmap())
                        .circleCrop()
                        .into(holder.profileImageButton);

                holder.nameTextView.setText(recentContacts.get(position - 2).getName());

                // Set stories count for circular status view
                holder.circularStatusView.setPortionsCount(recentContacts.get(position - 2).getStatusStories().size());
                // Set different colors for seen stories in circular status view
                ArrayList<Story> currentStatusStories = recentContacts.get(position - 2).getStatusStories();
                for (int i = 0; i < currentStatusStories.size(); i++) {
                    if (currentStatusStories.get(i).isSeen()) {
                        holder.circularStatusView.setPortionColorForIndex(i, context.getResources().getColor(R.color.teal_500));
                    }
                }

                holder.constraintLayout.setOnClickListener(new ItemClick(position, 1));

                holder.profileImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.constraintLayout.performClick();
                        performRipple(holder.constraintLayout);
                    }
                });
            }

        } else if (seenContacts.size() > 0 && position < (recentContacts.size() + seenContacts.size() + 4)) {
            // Seen contacts
            if (position == (recentContacts.size() + 2)) {
                holder.statusDividerTextView.setText("Ciao");
            } else {
                Glide.with(context)
                        .load(seenContacts.get(position - recentContacts.size() - 3)
                                .getStatusStories()
                                .get(seenContacts.get(position - recentContacts.size() - 3).getLastStoriesPos())
                                .getStoryPreviewBitmap())
                        .circleCrop()
                        .into(holder.profileImageButton);

                holder.nameTextView.setText(seenContacts.get(position - recentContacts.size() - 3).getName());

                // Set stories count for circular status view
                holder.circularStatusView.setPortionsCount(seenContacts.get(position - recentContacts.size() - 3).getStatusStories().size());
                // Set different colors for seen stories in cricular status view
                ArrayList<Story> currentStatusStories = seenContacts.get(position - recentContacts.size() - 3).getStatusStories();
                for (int i = 0; i < currentStatusStories.size(); i++) {
                    if (currentStatusStories.get(i).isSeen()) {
                        holder.circularStatusView.setPortionColorForIndex(i, context.getResources().getColor(R.color.teal_500));
                    }
                }

                holder.constraintLayout.setOnClickListener(new ItemClick(position, 2));

                holder.profileImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.constraintLayout.performClick();
                        performRipple(holder.constraintLayout);
                    }
                });
            }
        } else if (disabledContacts.size() > 0) {
            // Disabled contacts
            if (position == (recentContacts.size() + seenContacts.size() + 3)) {

            } else {
                Glide.with(context)
                        .load(disabledContacts.get(position - 1)
                                .getStatusStories()
                                .get(disabledContacts.get(position - 1).getLastStoriesPos())
                                .getStoryPreviewBitmap())
                        .circleCrop()
                        .into(holder.profileImageButton);

                holder.nameTextView.setText(disabledContacts.get(position - 1).getName());

                // Set stories count for circular status view
                holder.circularStatusView.setPortionsCount(disabledContacts.get(position - 1).getStatusStories().size());
                // Set different colors for seen stories in cricular status view
                ArrayList<Story> currentStatusStories = disabledContacts.get(position - 1).getStatusStories();
                for (int i = 0; i < currentStatusStories.size(); i++) {
                    if (currentStatusStories.get(i).isSeen()) {
                        holder.circularStatusView.setPortionColorForIndex(i, context.getResources().getColor(R.color.teal_500));
                    }
                }

                holder.constraintLayout.setOnClickListener(new ItemClick(position, 3));

                holder.profileImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.constraintLayout.performClick();
                        performRipple(holder.constraintLayout);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        // Returning the number of contacts + my contact
        int size = 0;
        if (recentContacts != null && recentContacts.size() > 0) {
            // Add recent contacts size + table title item
            size += recentContacts.size() + 1;
        }
        if (seenContacts != null && seenContacts.size() > 0) {
            // Add seen contacts size + table title item
            size += seenContacts.size() + 1;
        }
        if (disabledContacts != null && disabledContacts.size() > 0) {
            // Add disabled contacts size + table title item
            size += disabledContacts.size() + 1;
        }

        return size + 1;
    }

    public void setMyProfilePicture(String myProfilePicture) {
        this.myProfilePicture = myProfilePicture;
        notifyDataSetChanged();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        StatusRecViewAdapter.contacts = contacts;

        // Initialize the arraylists of contacts
        recentContacts = new ArrayList<>();
        seenContacts = new ArrayList<>();
        disabledContacts = new ArrayList<>();

        // Insert the new contacts in the correct arraylist
        for (Contact contact:contacts) {
            if (contact.isStatusDisabled()) {
                disabledContacts.add(contact);
            } else if (contact.isAllStoriesSeen()) {
                seenContacts.add(contact);
            } else {
                recentContacts.add(contact);
            }
        }

        notifyDataSetChanged();
    }

    public void addContact(Contact contact) {
        // Insert the new contact in the correct arraylist
        if (contact.isStatusDisabled()) {
            disabledContacts.add(contact);
        } else if (contact.isAllStoriesSeen()) {
            seenContacts.add(contact);
        } else {
            recentContacts.add(contact);
        }

        notifyDataSetChanged();
    }

    // Custom methods
    public void performRipple(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            RippleDrawable rippleDrawable = (RippleDrawable) view.getBackground();

            rippleDrawable.setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});

            Handler exitRippleHandler = new Handler();
            exitRippleHandler.postDelayed(new Runnable()
            {
                @Override public void run()
                {
                    rippleDrawable.setState(new int[]{});
                }
            }, 200);
        }
    }

    // Click listeners
    class ItemClick implements View.OnClickListener {
        private int position;
        private int contactsType;

        public ItemClick(int position, int contactsType) {
            this.position = position;
            this.contactsType = contactsType;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, StoriesActivity.class);
            intent.putExtra("contactsType", contactsType);
            intent.putExtra("position", position - 2);

            context.startActivity(intent);
        }
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton myProfileImageButton;

        private TextView statusDividerTextView;

        private ConstraintLayout constraintLayout;
        private ImageButton profileImageButton;
        private CircularStatusView circularStatusView;
        private TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Instantiate ActionBar variables
            myProfileImageButton = itemView.findViewById(R.id.my_profile_image_button);

            statusDividerTextView = itemView.findViewById(R.id.status_divider_textview);

            constraintLayout = itemView.findViewById(R.id.constraint_layout);
            profileImageButton = itemView.findViewById(R.id.profile_image_button);
            circularStatusView = itemView.findViewById(R.id.profile_image_circular_status_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }
}