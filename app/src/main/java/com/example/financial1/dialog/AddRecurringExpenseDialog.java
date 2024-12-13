package com.example.financial1.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.financial1.R;
import com.example.financial1.databinding.DialogAddRecurringExpenseBinding;
import com.example.financial1.entity.RecurringExpense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRecurringExpenseDialog extends DialogFragment {

    private DialogAddRecurringExpenseBinding binding;
    private final OnExpenseAddedListener listener;

    // Listener để callback khi thêm mới chi tiêu
    public interface OnExpenseAddedListener {
        void onExpenseAdded(RecurringExpense spending);
    }

    public AddRecurringExpenseDialog(OnExpenseAddedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogAddRecurringExpenseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.recurrence_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRecurrenceType.setAdapter(adapter);

        binding.btnSave.setOnClickListener(v -> saveRecurringExpense());
        binding.btnCancel.setOnClickListener(v -> dismiss());
    }

    private void saveRecurringExpense() {
        String name = binding.etName.getText().toString().trim();
        String amountStr = binding.etAmount.getText().toString().trim();
        String category = binding.etCategory.getText().toString().trim();
        String startDateStr = binding.etStartDate.getText().toString().trim();
        String endDateStr = binding.etEndDate.getText().toString().trim();

        // Get selected recurrence type from the spinner
        String recurrenceTypeStr = binding.spinnerRecurrenceType.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(amountStr) || TextUtils.isEmpty(category) ||
                TextUtils.isEmpty(startDateStr) || TextUtils.isEmpty(recurrenceTypeStr)) {
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = !TextUtils.isEmpty(endDateStr) ? dateFormat.parse(endDateStr) : null;

            // Convert recurrence type string to integer (for example, "Yearly" = 1)
            int recurrenceType = getRecurrenceType(recurrenceTypeStr);

            RecurringExpense spending = new RecurringExpense();
            spending.setName(name);
            spending.setAmount(amount);
            spending.setCategory(category);
            spending.setStartDate(startDate);
            spending.setEndDate(endDate);
            spending.setRecurrenceType(recurrenceType);

            listener.onExpenseAdded(spending);
            dismiss();
        } catch (NumberFormatException | ParseException e) {
            Toast.makeText(requireContext(), "Invalid input. Please check your entries.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getRecurrenceType(String recurrenceTypeStr) {
        switch (recurrenceTypeStr) {
            case "Yearly":
                return 1;
            case "Monthly":
                return 2;
            case "Daily":
                return 3;
            default:
                return 0; // Default value
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
