package com.example.whatsappclonelombenis;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class App extends Application {
    public static Context context;

    public static Contact myContact;
    public static ArrayList<Contact> contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        // Create contacts
        String sampleImageUrlString = "https://img.huffingtonpost.com/asset/5e1710b4250000bee1d323e7.jpeg?cache=iA1K1GPWo5&ops=scalefit_630_noupscale";
        Bitmap sampleBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dark);

        // Create my contact + stories
        ArrayList<Story> myContactStatusStories = new ArrayList<Story>();
        Story myContactFirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, true);
        Story myContactSecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, true);
        Story myContactThirdStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orange), sampleImageUrlString,
                "Storia 3", "Questo è un caption", sampleBitmap, true);
        myContactStatusStories.add(myContactFirstStory);
        myContactStatusStories.add(myContactSecondStory);
        myContactStatusStories.add(myContactThirdStory);

        myContact = new Contact("Matteo", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", myContactStatusStories);

        // Create stories
        ArrayList<Story> firstContactStatusStories = new ArrayList<Story>();
        Story firstContactFirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story firstContactSecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        Story firstContactThirdStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orange), sampleImageUrlString,
                "Storia 3", "Questo è un caption", sampleBitmap, false);
        firstContactStatusStories.add(firstContactFirstStory);
        firstContactStatusStories.add(firstContactSecondStory);
        firstContactStatusStories.add(firstContactThirdStory);

        ArrayList<Story> secondContactStatusStories = new ArrayList<Story>();
        Story secondContactfirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story secondContactsecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        secondContactStatusStories.add(secondContactfirstStory);
        secondContactStatusStories.add(secondContactsecondStory);

        ArrayList<Story> thirdContactStatusStories = new ArrayList<Story>();
        Story thirdContactfirstStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.black), sampleImageUrlString,
                "Storia 1", "Questo è un caption", sampleBitmap, false);
        Story thirdContactsecondStory = new Story(Calendar.getInstance(), ContextCompat.getColor(getContext(), R.color.orchid), sampleImageUrlString,
                "Storia 2", "Questo è un caption", sampleBitmap, false);
        thirdContactStatusStories.add(thirdContactfirstStory);
        thirdContactStatusStories.add(thirdContactsecondStory);

        // Create contacts
        contacts = new ArrayList<>();

        Contact contact1 = new Contact("Leonardo DiCaprio1", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", firstContactStatusStories);
        contacts.add(contact1);
        Contact contact2 = new Contact("Leonardo DiCaprio2", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", secondContactStatusStories);
        contacts.add(contact2);
        Contact contact3 = new Contact("Leonardo DiCaprio3", "3666875674", sampleImageUrlString,
                "Questo è il mio stato", thirdContactStatusStories);
        contacts.add(contact3);


        // Initialize isDisabledContactsVisible to false
        TabStatusFragment.isDisabledContactsVisible = false;
    }

    public static Context getContext() {
        return context;
    }

    // Custom methods
    // Perform ripple effect on a view
    public static void performRipple(View view) {
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

    // Get string date from calendar object
    public static String getDateString(Calendar date) {
        int currentContactsDateDay = date.get(Calendar.DAY_OF_MONTH);
        int currentContactsDateHour = date.get(Calendar.HOUR_OF_DAY);
        int currentContactsDateMinute = date.get(Calendar.MINUTE);
        int currentContactsDateTimeSeconds = (int) (date.getTimeInMillis() / 1000);

        Calendar currentDate = Calendar.getInstance();
        int currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentDateHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentDateMinute = currentDate.get(Calendar.MINUTE);
        int currentDateTimeSeconds = (int) (currentDate.getTimeInMillis() / 1000);

        int secondsDiff = currentDateTimeSeconds - currentContactsDateTimeSeconds;
        int minutesDiff = secondsDiff / 60;

        String dateString;
        if (secondsDiff < 60) {
            // Less than a minute ago
            dateString = App.getContext().getString(R.string.ora);
        } else {
            if (currentContactsDateDay == currentDateDay &&
                    minutesDiff < 60) {
                // Less than an hour ago
                dateString = getContext()
                        .getResources()
                        .getQuantityString(R.plurals.numberOfMinutesAgo,
                                minutesDiff, minutesDiff);
            } else {
                if (currentContactsDateDay == currentDateDay) {
                    // More than an hour ago, this day
                    dateString = App.getContext().getString(R.string.oggi) + ", " +
                            currentContactsDateHour + ":" + currentContactsDateMinute;
                } else {
                    // More than an hour ago, the day before
                    dateString = App.getContext().getString(R.string.ieri) + ", " +
                            currentContactsDateHour + ":" + currentContactsDateMinute;
                }
            }
        }

        return dateString;
    }
}
