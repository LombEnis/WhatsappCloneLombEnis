package com.example.whatsappclonelombenis;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class App extends Application {
    public static Context context;

    // Contacts
    public static Contact myContact;
    public static ArrayList<Contact> contacts;

    // Device size variables
    public static int screenHeight;
    public static int statusBarHeight;
    public static int navigationBarHeight;
    public static int fullScreenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        // Create device size variables
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        int navigationBarResourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationBarHeight = getResources().getDimensionPixelSize(navigationBarResourceId);

        int statusBarResourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = getResources().getDimensionPixelSize(statusBarResourceId);

        fullScreenHeight = screenHeight + navigationBarHeight + statusBarHeight + 36;

        // Create contacts
        createContacts();

        // Initialize isDisabledContactsVisible to false
        TabStatusFragment.isDisabledContactsVisible = false;
    }

    public static Context getContext() {
        return context;
    }

    // Custom methods

    // Update navigationBarHeight
    public static void updateDeviceSizeVariables(Context context) {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        int navigationBarResourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationBarHeight = context.getResources().getDimensionPixelSize(navigationBarResourceId);

        int statusBarResourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = context.getResources().getDimensionPixelSize(statusBarResourceId);

        fullScreenHeight = screenHeight + navigationBarHeight + statusBarHeight + 36;
    }

    // Create contacts
    public void createContacts() {
        Bitmap sampleDarkBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dark);

        // Create stories
        ArrayList<Story> firstContactStatusStories = new ArrayList<Story>();
        Story firstContactFirstStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        Story firstContactSecondStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        Story firstContactThirdStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        Story fourthContactThirdStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        firstContactStatusStories.add(firstContactFirstStory);
        firstContactStatusStories.add(firstContactSecondStory);
        firstContactStatusStories.add(firstContactThirdStory);
        firstContactStatusStories.add(fourthContactThirdStory);

        ArrayList<Story> secondContactStatusStories = new ArrayList<Story>();
        Story secondContactfirstStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        Story secondContactsecondStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        secondContactStatusStories.add(secondContactfirstStory);
        secondContactStatusStories.add(secondContactsecondStory);

        ArrayList<Story> thirdContactStatusStories = new ArrayList<Story>();
        Story thirdContactfirstStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        Story thirdContactsecondStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap);
        thirdContactStatusStories.add(thirdContactfirstStory);
        thirdContactStatusStories.add(thirdContactsecondStory);

        // Create contacts
        contacts = new ArrayList<>();

        Contact contact1 = new Contact("Leonardo DiCaprio1", "3666875674", sampleDarkBitmap,
                "Questo è il mio stato", firstContactStatusStories);
        contacts.add(contact1);
        Contact contact2 = new Contact("Leonardo DiCaprio2", "3666875674", sampleDarkBitmap,
                "Questo è il mio stato", secondContactStatusStories);
        contacts.add(contact2);
        Contact contact3 = new Contact("Leonardo DiCaprio3", "3666875674", sampleDarkBitmap,
                "Questo è il mio stato", thirdContactStatusStories);
        contacts.add(contact3);

        // Create my contact + stories
        ArrayList<Object[]> myContactViewsContacts = new ArrayList<>();

        Object[] myContactViewsContactsItem1 = {contact1, Calendar.getInstance()};
        Object[] myContactViewsContactsItem2 = {contact2, Calendar.getInstance()};
        Object[] myContactViewsContactsItem3 = {contact3, Calendar.getInstance()};

        myContactViewsContacts.add(myContactViewsContactsItem1);
        myContactViewsContacts.add(myContactViewsContactsItem2);
        myContactViewsContacts.add(myContactViewsContactsItem3);

        ArrayList<Story> myContactStatusStories = new ArrayList<Story>();
        Story myContactFirstStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap, myContactViewsContacts);
        Story myContactSecondStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap, myContactViewsContacts);
        Story myContactThirdStory = new Story(Calendar.getInstance(), sampleDarkBitmap,
                "Questo è un caption", sampleDarkBitmap, myContactViewsContacts);
        myContactStatusStories.add(myContactFirstStory);
        myContactStatusStories.add(myContactSecondStory);
        myContactStatusStories.add(myContactThirdStory);

        myContact = new Contact("Matteo", "3666875674", sampleDarkBitmap,
                "Questo è il mio stato", myContactStatusStories);
    }

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
                // More than an hour ago

                // Get time in format HH:mm
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String dateStringFormatted = dateFormat.format(date.getTime());

                if (currentContactsDateDay == currentDateDay) {
                    // More than an hour ago, this day
                    dateString = App.getContext().getString(R.string.oggi) + ", " + dateStringFormatted;
                } else {
                    // More than an hour ago, the day before
                    dateString = App.getContext().getString(R.string.ieri) + ", " + dateStringFormatted;
                }
            }
        }

        return dateString;
    }
}
