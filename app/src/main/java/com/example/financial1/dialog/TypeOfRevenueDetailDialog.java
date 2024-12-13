package com.example.financial1.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfRevenue;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueFragment;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueViewModel;

public class TypeOfRevenueDetailDialog {
    private TypeOfRevenueViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextView tvId, tvName;

    public TypeOfRevenueDetailDialog(final Context context, TypeOfRevenueFragment fragment, TypeOfRevenue typeOfRevenue) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_detail_type_of_revenue, null);
        tvId = view.findViewById(R.id.tvId);
        tvName = view.findViewById(R.id.tvName);
        tvId.setText("" + typeOfRevenue.tid);
        tvName.setText(typeOfRevenue.name);

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
