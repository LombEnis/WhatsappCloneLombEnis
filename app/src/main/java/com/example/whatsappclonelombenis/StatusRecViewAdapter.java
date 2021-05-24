package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlomi.circularstatusview.CircularStatusView;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class StatusRecViewAdapter extends RecyclerView.Adapter<StatusRecViewAdapter.ViewHolder> {
    private Context context;

    public static Contact myContact;

    public static ArrayList<Contact> contacts;
    public static ArrayList<Contact> recentContacts;
    public static ArrayList<Contact> seenContacts;
    public static ArrayList<Contact> disabledContacts;

    private boolean recentContactsCreated;
    private boolean seenContactsCreated;

    private boolean disabledContactsVisible;

    public StatusRecViewAdapter(Context context) {
        this.context = context;

        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                recentContactsCreated = false;
                seenContactsCreated = false;
                super.onChanged();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        // Return different viewType depending on position
        if (position == 0) return 1;
        else return 2;
    }

    @NonNull
    @Override
    public StatusRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate different item layout depending on position
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_first_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_sub_recview, parent, false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusRecViewAdapter.ViewHolder holder, int position) {
        // Set different view depending on position
        if (position == 0) {
            // Set my profile image with glide
            Glide.with(context)
                    .load(myContact.getProfilePicture())
                    .circleCrop()
                    .into(holder.myProfileImageButton);

            // Set circular status view
            holder.myCircularStatusView.setPortionsCount(myContact.getStatusStories().size());
            if (myContact.getStatusStories().size() > 0) {
                // My contact have no stories
                holder.myPlusImageView.setVisibility(View.GONE);
            }

            // Set on click listener
            holder.myConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StoriesActivity.class);
                    intent.putExtra("contactsType", 0);
                    intent.putExtra("position", position);

                    context.startActivity(intent);
                }
            });

            holder.myProfileImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.myConstraintLayout.performClick();
                    performRipple(holder.myConstraintLayout);
                }
            });
            return;
        }


        RecyclerView contactsRecView = holder.subRecView;
        ContactsRecViewAdapter contactsRecViewAdapter;

        if (!recentContactsCreated && recentContacts != null && recentContacts.size() > 0) {
            holder.dividerTextView.setText(R.string.aggiornamenti_recenti);

            // Recent contacts
            contactsRecViewAdapter = new ContactsRecViewAdapter(context, 1);
            contactsRecViewAdapter.setContacts(recentContacts);

            recentContactsCreated = true;
        } else if (!seenContactsCreated && seenContacts != null && seenContacts.size() > 0) {
            holder.dividerTextView.setText(R.string.aggiornamenti_visti);

            // Seen contacts
            contactsRecViewAdapter = new ContactsRecViewAdapter(context, 2);
            contactsRecViewAdapter.setContacts(seenContacts);

            seenContactsCreated = true;
        } else {
            holder.dividerTextView.setText(R.string.aggiornamenti_disattivati);

            // Disabled contacts
            contactsRecViewAdapter = new ContactsRecViewAdapter(context, 3);
            contactsRecViewAdapter.setContacts(disabledContacts);

            // Set visibility GONE for disabled contacts
            if (!disabledContactsVisible) {
                for (int i = 0; i < disabledContacts.size(); i++) {
                    contactsRecView.setVisibility(View.GONE);
                }
            }

            // Set divider arrow visibility and listener
            holder.dividerArrowImageView.setVisibility(View.VISIBLE);
            holder.dividerConstraintlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (disabledContactsVisible) {
                        // Set visibility GONE for disabled contacts
                        holder.dividerArrowImageView.setImageResource(R.drawable.ic_arrow_up_vector_24);
                        holder.dividerArrowImageView.setColorFilter(ContextCompat.getColor(context,
                                R.color.dark_grey), android.graphics.PorterDuff.Mode.MULTIPLY);

                        for (int i = 0; i < disabledContacts.size(); i++) {
                            contactsRecView.setVisibility(View.GONE);
                        }

                        disabledContactsVisible = false;
                    } else {
                        // Set visibility VISIBLE for disabled contacts
                        holder.dividerArrowImageView.setImageResource(R.drawable.ic_arrow_down_vector_24);
                        holder.dividerArrowImageView.setColorFilter(
                                MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLACK),
                                android.graphics.PorterDuff.Mode.MULTIPLY);

                        for (int i = 0; i < disabledContacts.size(); i++) {
                            contactsRecView.setVisibility(View.VISIBLE);
                        }

                        disabledContactsVisible = true;
                    }
                }
            });
        }

        // Set LayoutManager for recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        contactsRecView.setLayoutManager(layoutManager);

        // Set divider for recyclerview
        RecViewItemDivider itemDivider = new RecViewItemDivider(context, 0);
        contactsRecView.addItemDecoration(itemDivider);

        // Set adapter for recyclerview
        contactsRecView.setAdapter(contactsRecViewAdapter);
    }

    @Override
    public int getItemCount() {
        int itemCount = 4;

        if (myContact == null) itemCount--;
        if (recentContacts == null || recentContacts.size() == 0) itemCount--;
        if (seenContacts == null || seenContacts.size() == 0) itemCount--;
        if (disabledContacts == null || disabledContacts.size() == 0) itemCount--;

        return itemCount;
    }

    public void setMyContact(Contact myContact) {
        this.myContact = myContact;
        notifyDataSetChanged();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        StatusRecViewAdapter.contacts = contacts;

        // Initialize the array lists of contacts
        recentContacts = new ArrayList<>();
        seenContacts = new ArrayList<>();
        disabledContacts = new ArrayList<>();

        // Insert the new contacts in the correct arraylist
        for (Contact contact:contacts) {
            if (contact.isStatusDisabled()) {
                disabledContacts.add(contact);
            } else if (contact.isAllStoriesSeen()) {
                seenContacts.add(contact);
                contact.setLastStoriesPos(0);
            } else {
                recentContacts.add(contact);
            }
        }

        // Sort contact arraylist by date
        Collections.sort(recentContacts, new ContactsDateComparator());
        Collections.sort(seenContacts, new ContactsDateComparator());
        Collections.sort(disabledContacts, new ContactsDateComparator());

        // Initialize contactsCreated variables
        recentContactsCreated = false;
        seenContactsCreated = false;

        notifyDataSetChanged();
    }

    // Custom methods

    // Perform ripple effect on a view
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

    // Comparator class to sort contacts by last story date
    class ContactsDateComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.getStatusStories().get(o1.getStatusStories().size() - 1).getDate()
                    .compareTo(o2.getStatusStories().get(o2.getStatusStories().size() - 1).getDate());
        }
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton myProfileImageButton;
        private ImageView myPlusImageView;
        private ConstraintLayout myConstraintLayout;
        private CircularStatusView myCircularStatusView;
        private TextView myNameTextView;

        private ConstraintLayout dividerConstraintlayout;
        private TextView dividerTextView;
        private ImageView dividerArrowImageView;

        private RecyclerView subRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Instantiate my contact vies
            myConstraintLayout = itemView.findViewById(R.id.constraint_layout);
            myProfileImageButton = itemView.findViewById(R.id.my_profile_image_button);
            myPlusImageView = itemView.findViewById(R.id.my_plus_imageview);
            myCircularStatusView = itemView.findViewById(R.id.my_profile_image_circular_status_view);
            myNameTextView = itemView.findViewById(R.id.name_text_view);

            // Instantiate divider view
            dividerConstraintlayout = itemView.findViewById(R.id.status_divider_constraintlayout);
            dividerTextView = itemView.findViewById(R.id.status_divider_textview);
            dividerArrowImageView = itemView.findViewById(R.id.status_divider_arrow_imageview);

            // Instantiate sub recview
            subRecView = itemView.findViewById(R.id.status_sub_recview);
        }
    }

    // Adapter class for contacts recycler view
    class ContactsRecViewAdapter extends RecyclerView.Adapter<ContactsRecViewAdapter.ViewHolder> {
        private Context context;
        private int contactsType;
        private ArrayList<Contact> contacts;

        public ContactsRecViewAdapter(Context context, int contactsType) {
            this.context = context;
            this.contactsType = contactsType;
        }

        @NonNull
        @Override
        public ContactsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_recview_item, parent, false);;
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Contact currentContact = contacts.get(position);
            // Set profile image
            Glide.with(context)
                    .load(currentContact
                            .getStatusStories()
                            .get(currentContact.getLastStoriesPos())
                            .getStoryPreviewBitmap())
                    .circleCrop()
                    .into(holder.profileImageButton);

            // Set name textview
            holder.nameTextView.setText(currentContact.getName());
            // Set date textview
            Calendar currentContactsDate = currentContact
                    .getStatusStories()
                    .get(currentContact.getStatusStories().size() - 1)
                    .getDate();

            int currentContactsDateDay = currentContactsDate.get(Calendar.DAY_OF_MONTH);
            int currentContactsDateHour = currentContactsDate.get(Calendar.HOUR_OF_DAY);
            int currentContactsDateMinute = currentContactsDate.get(Calendar.MINUTE);

            Calendar currentDate = Calendar.getInstance();
            int currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);
            int currentDateHour = currentDate.get(Calendar.HOUR_OF_DAY);
            int currentDateMinute = currentDate.get(Calendar.MINUTE);

            String currentContactDateString;
            if (currentContactsDateMinute == currentDateMinute) {
                // Less than a minute ago
                currentContactDateString = context.getString(R.string.ora);
            } else {
                if (currentContactsDateDay == currentDateDay &&
                        currentDateHour == currentContactsDateHour) {
                    // Less than an hour ago
                    if ((currentDateMinute - currentContactsDateMinute) == 1) {
                        // 1 minute ago (singular)
                        currentContactDateString = 1 + " " + context.getString(R.string.minuto_fa);
                    } else {
                        // More than 1 minute ago (plural)
                        currentContactDateString = (currentDateMinute - currentContactsDateMinute) + " " + context.getString(R.string.minuti_fa);
                    }
                } else {
                    if (currentContactsDateDay == currentDateDay) {
                        // More than an hour ago, the day before
                        currentContactDateString = context.getString(R.string.ieri) + ", " + currentContactsDateHour + ":" + currentContactsDateMinute;
                    } else {
                        // More than an hour ago, this day
                        currentContactDateString = context.getString(R.string.oggi) + ", " + currentContactsDateHour + ":" + currentContactsDateMinute;
                    }
                }
            }

            holder.dateTextView.setText(currentContactDateString);

            if (contactsType != 3) {
                // Set stories count for circular status view
                holder.circularStatusView.setPortionsCount(currentContact.getStatusStories().size());
                // Set different colors for seen stories in circular status view
                ArrayList<Story> currentStatusStories = currentContact.getStatusStories();
                for (int i = 0; i < currentStatusStories.size(); i++) {
                    if (!currentStatusStories.get(i).isSeen()) {
                        holder.circularStatusView.setPortionColorForIndex(i,
                                MaterialColors.getColor(context, R.attr.colorPrimary, Color.BLACK));
                    }
                }
            } else {
                holder.circularStatusView.setVisibility(View.GONE);
            }

            // Set click listener
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StoriesActivity.class);
                    intent.putExtra("contactsType", contactsType);
                    intent.putExtra("position", position);

                    context.startActivity(intent);
                }
            });

            // Set click listener on profile image click (background click and ripple)
            holder.profileImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform click
                    holder.constraintLayout.performClick();

                    // Perform ripple
                    if (Build.VERSION.SDK_INT >= 21) {
                        RippleDrawable rippleDrawable = (RippleDrawable) holder.constraintLayout.getBackground();

                        rippleDrawable.setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});

                        Handler exitRippleHandler = new Handler();
                        exitRippleHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rippleDrawable.setState(new int[]{});
                            }
                        }, 200);
                    }
                }
            });
            /*}*/
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public void setContacts(ArrayList<Contact> contacts) {
            this.contacts = contacts;
            notifyDataSetChanged();
        }

        // ViewHolder class
        public class ViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout dividerConstraintlayout;
            private TextView dividerTextView;
            private ImageView dividerArrowImageView;

            private ConstraintLayout constraintLayout;
            private ImageButton profileImageButton;
            private CircularStatusView circularStatusView;
            private TextView nameTextView;
            private TextView dateTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                dividerConstraintlayout = itemView.findViewById(R.id.status_divider_constraintlayout);
                dividerTextView = itemView.findViewById(R.id.status_divider_textview);
                dividerArrowImageView = itemView.findViewById(R.id.status_divider_arrow_imageview);

                constraintLayout = itemView.findViewById(R.id.constraint_layout);
                profileImageButton = itemView.findViewById(R.id.profile_image_button);
                circularStatusView = itemView.findViewById(R.id.profile_image_circular_status_view);
                nameTextView = itemView.findViewById(R.id.name_text_view);
                dateTextView = itemView.findViewById(R.id.date_text_view);
            }
        }
    }
}