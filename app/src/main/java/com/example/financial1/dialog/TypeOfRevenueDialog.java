package com.example.financial1.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfRevenue;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueFragment;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class TypeOfRevenueDialog {
    private TypeOfRevenueViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextInputEditText etId, etName;
    private boolean mEditMode;

    public TypeOfRevenueDialog(final Context context, TypeOfRevenueFragment fragment, TypeOfRevenue ... typeOfRevenue) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_type_of_revenue, null);
        etId = view.findViewById(R.id.etId);
        etName = view.findViewById(R.id.etName);
        if(typeOfRevenue != null&& typeOfRevenue.length>0){
            etId.setText(""+typeOfRevenue[0].tid);
            etName.setText(typeOfRevenue[0].name);
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
                        TypeOfRevenue tor = new TypeOfRevenue();
                        tor.name = etName.getText().toString();
                        if(mEditMode){
                            tor.tid = Integer.parseInt(etId.getText().toString());
                            mViewModel.update(tor);
                        }else {
                            mViewModel.insert(tor);
                            Toast.makeText(context, "Type of Revenue saved", Toast.LENGTH_SHORT).show();
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
