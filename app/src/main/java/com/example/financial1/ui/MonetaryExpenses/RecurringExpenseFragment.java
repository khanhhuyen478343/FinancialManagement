package com.example.financial1.ui.MonetaryExpenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.financial1.adapter.RecurringExpenseAdapter;
import com.example.financial1.databinding.FragmentRecurringExpensesBinding;
import com.example.financial1.dialog.AddRecurringExpenseDialog;

public class RecurringExpenseFragment extends Fragment {

    private FragmentRecurringExpensesBinding binding;
    private RecurringExpenseViewModel recurringExpenseViewModel;
    private RecurringExpenseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecurringExpensesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up RecyclerView
        adapter = new RecurringExpenseAdapter(null);
        binding.recurringExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recurringExpensesRecyclerView.setAdapter(adapter);

        // Set up ViewModel
        recurringExpenseViewModel = new ViewModelProvider(this).get(RecurringExpenseViewModel.class);

        // Observe LiveData from ViewModel
        recurringExpenseViewModel.getAllRecurringExpenses().observe(getViewLifecycleOwner(), recurringExpenses -> {
            adapter.setRecurringExpenses(recurringExpenses);
        });

        // Handle button click to add a new recurring spending
        binding.btnAddRecurringExpense.setOnClickListener(v -> showAddRecurringExpenseDialog());
    }

    private void showAddRecurringExpenseDialog() {
        // Open a dialog to add a new recurring spending
        AddRecurringExpenseDialog dialog = new AddRecurringExpenseDialog(spending -> {
            recurringExpenseViewModel.insert(spending);
        });
        dialog.show(getChildFragmentManager(), "AddRecurringExpenseDialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
