package com.example.whatsappclonelombenis;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        Calendar calendar1= Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 3);

        Calendar calendar2= Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 15);

        Calendar calendar3= Calendar.getInstance();
        calendar3.set(Calendar.DAY_OF_MONTH, 11);

        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg", "Margot <3", "Ho l'impressione che", calendar1, false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg", "Leonardo", "Ei Enis, sai dov'è Margot?", calendar2, false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/4/42/The_ROCK.jpg", "Dwayne", "Hey, I'm the Rock!", calendar3, false));
        /*chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg", "Margot <3", "Ho l'impressione che", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg", "Leonardo", "Ei Enis, sai dov'è Margot?", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Margot_Robbie_%2828129125629%29.jpg/537px-Margot_Robbie_%2828129125629%29.jpg", "Margot <3", "Ho l'impressione che", Calendar.getInstance(), false));
        chat_contacts.add(new Contact("https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Leonardo_di_Caprio_%2823531475691%29.jpg/900px-Leonardo_di_Caprio_%2823531475691%29.jpg", "Leonardo", "Ei Enis, sai dov'è Margot?", Calendar.getInstance(), false));
*/
    }
}
