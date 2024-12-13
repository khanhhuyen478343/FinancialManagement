package com.example.financial1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//import com.example.financial1.ui.MonetaryVariableExpenses.AmountOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.AmountOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.TypeOfVariableExpensesFragment;
//import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesFragment;

public class VariableExpensesViewPager2Adapter extends FragmentStateAdapter {

    public VariableExpensesViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position ==0){
            fragment = AmountOfVariableExpensesFragment.newInstance();
        }
        else {
            fragment = TypeOfVariableExpensesFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
