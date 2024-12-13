package com.example.financial1.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfVariableExpenses;
//import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class TypeOfVariableExpensesDialog {
    private TypeOfVariableExpensesViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextInputEditText etId, etName;
    private boolean mEditMode;

    public TypeOfVariableExpensesDialog(final Context context, TypeOfVariableExpensesFragment fragment, TypeOfVariableExpenses ... typeOfVariableExpenses) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_type_of_variable_expenses, null);
        etId = view.findViewById(R.id.etId);
        etName = view.findViewById(R.id.etName);
        if(typeOfVariableExpenses != null&& typeOfVariableExpenses.length>0){
            etId.setText(""+typeOfVariableExpenses[0].tid);
            etName.setText(typeOfVariableExpenses[0].name);
            mEditMode = true;
        }else {
            mEditMode = false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialog.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TypeOfVariableExpenses toe = new TypeOfVariableExpenses();
                        toe.name = etName.getText().toString();
                        if(mEditMode){
                            toe.tid = Integer.parseInt(etId.getText().toString());
                            mViewModel.update(toe);
                        }else {
                            mViewModel.insert(toe);
                            Toast.makeText(context, "Type of Variable Expenses saved", Toast.LENGTH_SHORT).show();
                        }
//
                    }
                });
        mDialog = builder.create();
    }
    public void show(){
        mDialog.show();
    }
}
