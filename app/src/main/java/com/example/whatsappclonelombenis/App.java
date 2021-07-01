package com.example.whatsappclonelombenis;

import android.app.Application;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class App extends Application {
    public static ArrayList<Contact> chat_contacts;

    public static ArrayList<Call> calls;

    @Override
    public void onCreate() {
        super.onCreate();

        //Chat RecyclerView
        chat_contacts = new ArrayList<>();
        Calendar calendar1= Calendar.getInstance();
        //calendar1.set(Calendar.DAY_OF_MONTH, 3);

        Calendar calendar2= Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 15);
        calendar2.set(Calendar.MONTH, 3);

        Calendar calendar3= Calendar.getInstance();
        calendar3.set(Calendar.DAY_OF_MONTH, 11);
        calendar3.set(Calendar.MONTH, 3);

        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg", "Margot <3", "Ho l'impressione che", calendar1, false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg", "Leonardo", "Ei Enis, sai dov'è Margot?", calendar2, false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!", calendar3, false));

        //Calls Tab
        calls= new ArrayList<>();
        Calendar callCalendar1= Calendar.getInstance();
        callCalendar1.set(Calendar.HOUR_OF_DAY, 7);
        //callCalendar1.set(Calendar.DAY_OF_MONTH, 7);
        callCalendar1.set(Calendar.MONTH, 4);

        Calendar callCalendar2= Calendar.getInstance();
        callCalendar2.set(Calendar.HOUR_OF_DAY, 15);
        callCalendar2.set(Calendar.MONTH, 1);

        Calendar callCalendar3= Calendar.getInstance();
        callCalendar3.set(Calendar.HOUR_OF_DAY, 11);

        Calendar callCalendar4= Calendar.getInstance();
        callCalendar4.set(Calendar.HOUR_OF_DAY, 11);
        callCalendar4.set(Calendar.MONTH, 2);

        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!"), callCalendar1, true, false, false, 3000, 4.4));
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!"), callCalendar2, true, false, true, 3000, 44));
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!"), callCalendar3, true, true, true, 3000, 44));
        calls.add(new Call(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Enis is my love :))"), callCalendar4,true, false, true, 3000, 44));
    }
}
