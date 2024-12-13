package com.example.financial1.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;

import com.example.financial1.R;
import com.example.financial1.adapter.TypeOfRevenueSpinnerAdapter;
import com.example.financial1.entity.Revenue;
import com.example.financial1.entity.TypeOfRevenue;
import com.example.financial1.ui.MonetaryRevenue.AmountOfRevenueFragment;
import com.example.financial1.ui.MonetaryRevenue.AmountOfRevenueViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class RevenueDialog {
    private AmountOfRevenueViewModel mViewModel;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mDialog;

    private TextInputEditText etId, etName, etAmount, etNote, etDate;  // Added etDate
    private Spinner spType;
    private boolean mEditMode;
    private TypeOfRevenueSpinnerAdapter mAdapter;

    public RevenueDialog(final Context context, AmountOfRevenueFragment fragment, Revenue... revenue) {
        mViewModel = fragment.getViewModel();
        mLayoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.dialog_revenue, null);

        // Bind the UI elements
        etId = view.findViewById(R.id.etId);
        etName = view.findViewById(R.id.etName);
        etAmount = view.findViewById(R.id.etAmount);
        etNote = view.findViewById(R.id.etNote);
        etDate = view.findViewById(R.id.etDate); // Added Date field
        spType = view.findViewById(R.id.spType);

        mAdapter = new TypeOfRevenueSpinnerAdapter(fragment.getActivity());

        // Observing data from ViewModel for spinner
        mViewModel.getAllTypeOfRevenue().observe(fragment.getActivity(), new Observer<List<TypeOfRevenue>>() {
            @Override
            public void onChanged(List<TypeOfRevenue> typeOfRevenues) {
                mAdapter.setList(typeOfRevenues);
            }
        });

        spType.setAdapter(mAdapter);

        // If editing an existing revenue entry, populate the fields
        if (revenue != null && revenue.length > 0) {
            etId.setText(""+revenue[0].rid);
            etName.setText(revenue[0].name);
            etAmount.setText(""+revenue[0].amountofmoney);
            etNote.setText(revenue[0].note);
            etDate.setText(revenue[0].date);  // Populate Date field
            mEditMode = true;
        } else {
            mEditMode = false;
        }

        // Create the dialog
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
                        // Create a new Revenue object
                        Revenue tor = new Revenue();
                        tor.name = etName.getText().toString();
                        tor.amountofmoney = Float.parseFloat(etAmount.getText().toString());
                        tor.note = etNote.getText().toString();
                        tor.date = etDate.getText().toString();  // Capture the Date field
                        tor.torid = ((TypeOfRevenue) mAdapter.getItem(spType.getSelectedItemPosition())).tid;

                        // Save or update the revenue entry
                        if (mEditMode) {
                            tor.rid = Integer.parseInt(etId.getText().toString());
                            mViewModel.update(tor);
                        } else {
                            mViewModel.insert(tor);
                            Toast.makeText(context, "Type of Revenue saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mDialog = builder.create();
    }

    public void show() {
        mDialog.show();
    }
}
