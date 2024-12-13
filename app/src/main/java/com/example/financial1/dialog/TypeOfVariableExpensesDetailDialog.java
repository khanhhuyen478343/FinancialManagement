package com.example.financial1.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfVariableExpenses;
//import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesViewModel;

public class TypeOfVariableExpensesDetailDialog {
    private TypeOfVariableExpensesViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextView tvId, tvName;

    public TypeOfVariableExpensesDetailDialog(final Context context, TypeOfVariableExpensesFragment fragment, TypeOfVariableExpenses typeOfVariableExpenses) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_detail_type_of_variable_expenses, null);
        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvId.setText("" + typeOfVariableExpenses.tid);
        tvName.setText(typeOfVariableExpenses.name);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("Close", (dialogInterface, i) -> {
                    mDialog.dismiss();
                });
        mDialog = builder.create();
    }
    public void show(){
        mDialog.show();
    }
}
