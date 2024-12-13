package com.example.financial1.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;

import com.example.financial1.R;
import com.example.financial1.adapter.TypeOfVariableExpensesSpinnerAdapter;
import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.entity.TypeOfVariableExpenses;
//import com.example.financial1.ui.MonetaryVariableExpenses.AmountOfVariableExpensesFragment;
//import com.example.financial1.ui.MonetaryVariableExpenses.AmountOfVariableExpensesViewModel;
import com.example.financial1.ui.MonetaryExpenses.AmountOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.AmountOfVariableExpensesViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class VariableExpensesDialog {
    private AmountOfVariableExpensesViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextInputEditText etId, etName, etAmount, etNote, etDate; // Added etDate
    private Spinner spType;
    private boolean mEditMode;
    private TypeOfVariableExpensesSpinnerAdapter mAdapter;

    public VariableExpensesDialog(final Context context, AmountOfVariableExpensesFragment fragment, VariableExpenses... variableExpenses) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_variable_expenses, null);

        // Bind the fields with the layout elements
        etId = view.findViewById(R.id.etId);
        etName = view.findViewById(R.id.etName);
        etAmount = view.findViewById(R.id.etAmount);
        etNote = view.findViewById(R.id.etNote);
        etDate = view.findViewById(R.id.etDate); // Bind the Date field
        spType = view.findViewById(R.id.spType);

        // Setup adapter for the spinner
        mAdapter = new TypeOfVariableExpensesSpinnerAdapter(fragment.getActivity());
        mViewModel.getAllTypeOfVariableExpenses().observe(fragment.getActivity(), new Observer<List<TypeOfVariableExpenses>>() {
            @Override
            public void onChanged(List<TypeOfVariableExpenses> typeOfVariableExpensesList) {
                mAdapter.setList(typeOfVariableExpensesList);
            }
        });
        spType.setAdapter(mAdapter);

        // Check if the dialog is in edit mode
        if (variableExpenses != null && variableExpenses.length > 0) {
            etId.setText(String.valueOf(variableExpenses[0].rid));
            etName.setText(variableExpenses[0].name);
            etAmount.setText(String.valueOf(variableExpenses[0].amountofmoney));
            etNote.setText(variableExpenses[0].note);
            etDate.setText(variableExpenses[0].date); // Set Date value here
            mEditMode = true;
        } else {
            mEditMode = false;
        }

        // Create the dialog with positive and negative buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("Close", (dialogInterface, i) -> mDialog.dismiss())
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    // Collect data from the fields
                    VariableExpenses spending = new VariableExpenses();
                    spending.name = etName.getText().toString();
                    spending.amountofmoney = Float.parseFloat(etAmount.getText().toString());
                    spending.note = etNote.getText().toString();
                    spending.date = etDate.getText().toString(); // Save the Date
                    spending.toeid = ((TypeOfVariableExpenses) mAdapter.getItem(spType.getSelectedItemPosition())).tid;

                    if (mEditMode) {
                        spending.rid = Integer.parseInt(etId.getText().toString());
                        mViewModel.update(spending); // Update the existing spending
                    } else {
                        mViewModel.insert(spending); // Insert the new spending
                        Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show();
                    }
                });

        mDialog = builder.create();
    }

    public void show() {
        mDialog.show();
    }
}
