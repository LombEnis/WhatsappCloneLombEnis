package com.example.whatsappclonelombenis;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class App extends Application {
    public static ArrayList<Contact> chat_contacts;

    @Override
    public void onCreate() {
        super.onCreate();

        //Chat RecyclerView
        chat_contacts = new ArrayList<>();
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", Calendar.getInstance(), false));
        //chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!", Calendar.getInstance(), true));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg","Margot <3","Ho l'impressione che", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg","Leonardo","Ei Enis, sai dov'è Margot?", Calendar.getInstance(), false));


        /*final Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.MONTH+1));
        System.out.println(calendar.HOUR_OF_DAY);
        final Date date = calendar.getTime();
        String day = new SimpleDateFormat("dd").format(date);    // always 2 digits
        String month = new SimpleDateFormat("MM").format(date);  // always 2 digits
        String year = new SimpleDateFormat("yyyy").format(date); // 4 digit year*/

    }
}
