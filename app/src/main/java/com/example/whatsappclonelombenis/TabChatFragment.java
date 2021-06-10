package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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

        // Execute new thread for setting the imageview and taking the bitmap from the layout, then putting it in the stories of the contacts

        /*Thread createContactsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(sampleImageUrlString);
                    Bitmap sampleImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sampleBackgroundImageView.setImageBitmap(sampleImage);

                        ArrayList<Contact> contacts = new ArrayList<>();

                        sampleStoryRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                // Create sample story layout
                                sampleStoryRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                                sampleStoryRelativeLayout.setDrawingCacheEnabled(true);
                                sampleStoryRelativeLayout.buildDrawingCache();
                                Bitmap sampleBitmap = sampleStoryRelativeLayout.getDrawingCache();

                                // Create my contact + stories
                                ArrayList<Story> myContactStatusStories = new ArrayList<Story>();
                                Story myContactFirstStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 1", "Questo è un caption", sampleBitmap);
                                Story myContactSecondStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 2", "Questo è un caption", sampleBitmap);
                                Story myContactThirdStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 3", "Questo è un caption", sampleBitmap);
                                myContactStatusStories.add(myContactFirstStory);
                                myContactStatusStories.add(myContactSecondStory);
                                myContactStatusStories.add(myContactThirdStory);

                                Contact myContact = new Contact("Matteo", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", myContactStatusStories);

                                // Create stories
                                ArrayList<Story> firstContactStatusStories = new ArrayList<Story>();
                                Story firstContactFirstStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 1", "Questo è un caption", sampleBitmap);
                                Story firstContactSecondStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 2", "Questo è un caption", sampleBitmap);
                                Story firstContactThirdStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 3", "Questo è un caption", sampleBitmap);
                                firstContactStatusStories.add(firstContactFirstStory);
                                firstContactStatusStories.add(firstContactSecondStory);
                                firstContactStatusStories.add(firstContactThirdStory);

                                ArrayList<Story> secondContactStatusStories = new ArrayList<Story>();
                                Story secondContactfirstStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 1", "Questo è un caption", sampleBitmap);
                                Story secondContactsecondStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 2", "Questo è un caption", sampleBitmap);
                                secondContactStatusStories.add(secondContactfirstStory);
                                secondContactStatusStories.add(secondContactsecondStory);

                                ArrayList<Story> thirdContactStatusStories = new ArrayList<Story>();
                                Story thirdContactfirstStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 1", "Questo è un caption", sampleBitmap);
                                Story thirdContactsecondStory = new Story(Calendar.getInstance(), sampleBitmap, sampleImageUrlString,
                                        "Storia 2", "Questo è un caption", sampleBitmap);
                                thirdContactStatusStories.add(thirdContactfirstStory);
                                thirdContactStatusStories.add(thirdContactsecondStory);

                                // Create contacts
                                Contact contact1 = new Contact("Leonardo DiCaprio1", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", firstContactStatusStories);
                                contacts.add(contact1);
                                Contact contact2 = new Contact("Leonardo DiCaprio2", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", secondContactStatusStories);
                                contacts.add(contact2);
                                Contact contact3 = new Contact("Leonardo DiCaprio3", "3666875674", sampleImageUrlString,
                                        "Questo è il mio stato", thirdContactStatusStories);
                                contacts.add(contact3);

                                TabStatusFragment.recViewAdapter.setMyContact(myContact);
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
            }
        });
        createContactsThread.start();*/

        return view;
    }
}
