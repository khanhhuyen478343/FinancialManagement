package com.example.financial1.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financial1.databinding.ItemRecurringExpenseBinding;
import com.example.financial1.entity.RecurringExpense;

import java.util.List;

public class RecurringExpenseAdapter extends RecyclerView.Adapter<RecurringExpenseAdapter.RecurringExpenseViewHolder> {

    private List<RecurringExpense> recurringExpenses;

    public RecurringExpenseAdapter(List<RecurringExpense> recurringExpenses) {
        this.recurringExpenses = recurringExpenses;
    }

    @NonNull
    @Override
    public RecurringExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecurringExpenseBinding binding = ItemRecurringExpenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecurringExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecurringExpenseViewHolder holder, int position) {
        RecurringExpense recurringExpense = recurringExpenses.get(position);
        holder.bind(recurringExpense);
    }

    @Override
    public int getItemCount() {
        return recurringExpenses != null ? recurringExpenses.size() : 0;
    }

    public void setRecurringExpenses(List<RecurringExpense> recurringExpenses) {
        this.recurringExpenses = recurringExpenses;
        notifyDataSetChanged();
    }

    public static class RecurringExpenseViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecurringExpenseBinding binding;

        public RecurringExpenseViewHolder(ItemRecurringExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(RecurringExpense recurringExpense) {
            binding.recurringExpenseName.setText(recurringExpense.getName());
            binding.recurringExpenseAmount.setText(String.format("$%.2f", recurringExpense.getAmount()));
            binding.recurringExpenseCategory.setText(recurringExpense.getCategory());
        }
    }
}
