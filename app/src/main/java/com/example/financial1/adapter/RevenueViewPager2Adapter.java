package com.example.financial1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.financial1.ui.MonetaryRevenue.AmountOfRevenueFragment;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueFragment;

public class RevenueViewPager2Adapter extends FragmentStateAdapter {

    public RevenueViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position ==0){
            fragment = AmountOfRevenueFragment.newInstance();
        }
        else {
            fragment = TypeOfRevenueFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
