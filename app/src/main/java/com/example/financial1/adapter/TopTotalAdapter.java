package com.example.financial1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.financial1.R;
import com.example.financial1.entity.StatisticTypeOfVariableExpenses;

import java.util.List;

public class TopTotalAdapter extends RecyclerView.Adapter<TopTotalAdapter.ViewHolder> {

    private List<StatisticTypeOfVariableExpenses> expenses;

    // Constructor
    public TopTotalAdapter(List<StatisticTypeOfVariableExpenses> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_variable_expenses, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatisticTypeOfVariableExpenses expense = expenses.get(position);
        holder.name.setText(expense.getName());
        holder.total.setText(String.format("%.2f $", expense.getTotal()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expenseName);
            total = itemView.findViewById(R.id.expenseTotal);
        }
    }
}
