package com.example.financial1.ui.MonetaryExpenses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financial1.R;
import com.example.financial1.adapter.VariableExpensesViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VariableExpensesFragment extends Fragment {
    private ViewPager2 mVp;
    private TabLayout mTl;

    public VariableExpensesFragment() {
    }
    public static VariableExpensesFragment newInstance(String param1, String param2) {
        VariableExpensesFragment fragment = new VariableExpensesFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVp = view.findViewById(R.id.viewPager2);
        mTl = view.findViewById(R.id.tabLayout);

        VariableExpensesViewPager2Adapter adapter = new VariableExpensesViewPager2Adapter(getActivity());
        mVp.setAdapter(adapter);

        if (getActivity() != null) {
            View fab = getActivity().findViewById(R.id.fab); // Thay R.id.fab bằng ID thực tế của FAB
            if (fab != null) {
                fab.setVisibility(View.GONE);
            }
        }

        new TabLayoutMediator(mTl, mVp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position ==0){
                    tab.setIcon(R.drawable.ic_menu_camera);
                    tab.setText("Amount");
                } else{
                    tab.setIcon(R.drawable.ic_menu_camera);
                    tab.setText("Type");
                }
            }
        }).attach();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_variable_expenses, container, false);
    }
}