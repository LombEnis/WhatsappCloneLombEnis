package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class TabChatFragment extends Fragment {
    private RelativeLayout sampleStoryRelativeLayout;
    private ImageView sampleBackgroundImageView;

    private String sampleImageUrlString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_story_layout, container, false);

        sampleImageUrlString = "https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale";

        // Set background color
        sampleStoryRelativeLayout = view.findViewById(R.id.sample_root_layout);
        sampleStoryRelativeLayout.setBackgroundColor(getResources().getColor(R.color.black));


        sampleBackgroundImageView = view.findViewById(R.id.sample_background_imageview);

        // Execute AsyncTask for setting the imageview and taking the bitmap from the layout, then putting it in the stories of the contacts
        CreateContactsTask createContactsTask = new CreateContactsTask();
        createContactsTask.execute();

        return view;
    }


    public class CreateContactsTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            try {
                url = new URL(sampleImageUrlString);
                Bitmap sampleImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sampleBackgroundImageView.setImageBitmap(sampleImage);

                        ArrayList<Contact> contacts = new ArrayList<>();

                        ArrayList<Story> sampleStatusStories = new ArrayList<Story>();

                        sampleStoryRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                sampleStoryRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                                sampleStoryRelativeLayout.setDrawingCacheEnabled(true);
                                sampleStoryRelativeLayout.buildDrawingCache();
                                Bitmap sampleBitmap = sampleStoryRelativeLayout.getDrawingCache();

                                Story firstStory = new Story(new Date(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                                        "Storia 1", "Questo è un caption", sampleBitmap);

                                Story secondStory = new Story(new Date(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                                        "Storia 2", "Questo è un caption", sampleBitmap);

                                sampleStatusStories.add(firstStory);
                                sampleStatusStories.add(secondStory);

                                contacts.add(new Contact("Leonardo DiCaprio", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", sampleStatusStories));
                                contacts.add(new Contact("Leonardo DiCaprio", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", sampleStatusStories));
                                contacts.add(new Contact("Leonardo DiCaprio", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", sampleStatusStories));


                                TabStatusFragment.recViewAdapter.setContacts(contacts);
                            }
                        });
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
