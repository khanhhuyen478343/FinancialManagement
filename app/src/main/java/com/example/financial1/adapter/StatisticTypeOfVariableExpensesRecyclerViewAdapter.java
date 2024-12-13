package com.example.financial1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financial1.R;
import com.example.financial1.repository.ExpenseItem;

import java.util.List;

public class StatisticTypeOfVariableExpensesRecyclerViewAdapter
        extends RecyclerView.Adapter<StatisticTypeOfVariableExpensesRecyclerViewAdapter.StatisticTypeOfVariableExpensesViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ExpenseItem> mList;

    public StatisticTypeOfVariableExpensesRecyclerViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StatisticTypeOfVariableExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_statistic_type_of_variable_expenses, parent, false);
        return new StatisticTypeOfVariableExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticTypeOfVariableExpensesViewHolder holder, int position) {
        if (mList != null) {
            // Get ExpenseItem from the list and set values
            ExpenseItem spendingItem = mList.get(position);
            holder.tvTypeOfVariableExpensesName.setText(spendingItem.getName());
            holder.etTotalTypeOfVariableExpenses.setText(String.valueOf(spendingItem.getTotal()));
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public void setList(List<ExpenseItem> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public static class StatisticTypeOfVariableExpensesViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTypeOfVariableExpensesName;
        public EditText etTotalTypeOfVariableExpenses;

        public StatisticTypeOfVariableExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTypeOfVariableExpensesName = itemView.findViewById(R.id.tvTypeOfVariableExpensesName);
            etTotalTypeOfVariableExpenses = itemView.findViewById(R.id.etTotalTypeOfVariableExpenses);
        }
    }
}
