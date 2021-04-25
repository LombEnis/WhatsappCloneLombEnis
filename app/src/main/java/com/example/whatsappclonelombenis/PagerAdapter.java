package com.example.whatsappclonelombenis;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {
    int numOfTabs=4;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabCamera();
            case 1: return new TabChat();
            case 2: return new TabStatus();
            case 3: return new TabCalls();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
