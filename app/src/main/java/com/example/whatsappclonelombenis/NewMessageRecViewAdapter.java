package com.example.whatsappclonelombenis;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewMessageRecViewAdapter extends RecyclerView.Adapter<NewMessageRecViewAdapter.ViewHolder> {
    Context context;

    ArrayList<Contact> contacts= new ArrayList<>();

    static ArrayList<View> views= new ArrayList<>();
    static ArrayList<View> selected_views= new ArrayList<>();

    static ContextualToolbarListener contextualToolbarListener= new ContextualToolbarListener();
    static RemoveSelectedListener removeSelectedListener= new RemoveSelectedListener();
    static SelectListener selectListener= new SelectListener();
    static LongRemoveSelectedListener longRemoveSelectedListener=  new LongRemoveSelectedListener();

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
           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent newGroupIntent= new Intent(context, NewGroupActivity.class);
                   context.startActivity(newGroupIntent);
               }
           });

           ImageButton newGroupImageButton= view.findViewById(R.id.newMessageNewGroupImageButton);
           newGroupImageButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   view.performClick();
                   performRipple(view);
               }
           });
        }
        else if (viewType==1) {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_contact_layout,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newContactIntent = new Intent(Intent.ACTION_INSERT);
                    newContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    context.startActivity(newContactIntent);
                }
            });

            ImageButton newContactImageButton= view.findViewById(R.id.newContactImageButton);
            newContactImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.performClick();
                    performRipple(view);
                }
            });
        }
        else {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_contacts_recview_item,parent,false);
            views.add(view);
            view.setOnLongClickListener(contextualToolbarListener);
        }
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position!=0 && position!=1) {
            holder.name.setText(contacts.get(position-2).getName());
            holder.message.setText(contacts.get(position-2).getMessage());
            Glide.with(context)
                    .load(contacts.get(position-2).getProfilePicture())
                    .circleCrop()
                    .into(holder.profilePic);
        }
    }

    public void setData(ArrayList<Contact> contacts) {
        this.contacts= contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (contacts.size()+2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, message;
        private ImageView profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.allContactsName);
            message=itemView.findViewById(R.id.allContactsInfo);
            profilePic=itemView.findViewById(R.id.allContactsProfilePic);
        }
    }

    public static class ContextualToolbarListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            selected_views.add(v);

            View check = v.findViewById(R.id.newmessageSelectedCheck);
            check.setVisibility(View.VISIBLE);

            v.setOnLongClickListener(null);

            NewMessageActivity.newmessageToolbar.setVisibility(View.GONE);
            NewMessageActivity.newmessageContextualToolbar.setVisibility(View.VISIBLE);

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
            View check=v.findViewById(R.id.newmessageSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);

            if (selected_views.size() == 0) {
                NewMessageActivity.newmessageToolbar.setVisibility(View.VISIBLE);
                NewMessageActivity.newmessageContextualToolbar.setVisibility(View.GONE);
            }

            if (selected_views.size()!=0) {
                v.setOnClickListener(selectListener);
                v.setOnLongClickListener(contextualToolbarListener);  //////
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
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            View check=v.findViewById(R.id.newmessageSelectedCheck);
            check.setVisibility(View.GONE);
            selected_views.remove(v);

            if (selected_views.size() == 0) {
                NewMessageActivity.newmessageToolbar.setVisibility(View.VISIBLE);
                NewMessageActivity.newmessageContextualToolbar.setVisibility(View.GONE);
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
            View check=v.findViewById(R.id.newmessageSelectedCheck);
            check.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(null); //////
            if (!selected_views.contains(v)) {
                selected_views.add(v);
            }

            for(View view: selected_views) {
                view.setOnClickListener(removeSelectedListener);
                view.setOnLongClickListener(longRemoveSelectedListener); ////
            }
        }
    }

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
}
