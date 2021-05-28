package com.example.whatsappclonelombenis;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import java.util.Calendar;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
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

        Calendar currentDate = Calendar.getInstance();
        int currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentDateHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentDateMinute = currentDate.get(Calendar.MINUTE);

        String dateString;
        if (currentContactsDateMinute == currentDateMinute) {
            // Less than a minute ago
            dateString = App.getContext().getString(R.string.ora);
        } else {
            if (currentContactsDateDay == currentDateDay &&
                    currentDateHour == currentContactsDateHour) {
                // Less than an hour ago
                if ((currentDateMinute - currentContactsDateMinute) == 1) {
                    // 1 minute ago (singular)
                    dateString = App.getContext().getString(R.string.minuto_fa, 1);
                } else {
                    // More than 1 minute ago (plural)
                    dateString = App.getContext().getString(R.string.minuti_fa, (currentDateMinute - currentContactsDateMinute));
                }
            } else {
                if (currentContactsDateDay == currentDateDay) {
                    // More than an hour ago, the day before
                    dateString = App.getContext().getString(R.string.ieri) + ", " + currentContactsDateHour + ":" + currentContactsDateMinute;
                } else {
                    // More than an hour ago, this day
                    dateString = App.getContext().getString(R.string.oggi) + ", " + currentContactsDateHour + ":" + currentContactsDateMinute;
                }
            }
        }

        return dateString;
    }
}
