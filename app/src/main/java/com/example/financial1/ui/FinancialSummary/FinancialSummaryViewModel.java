package com.example.financial1.ui.FinancialSummary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FinancialSummaryViewModel extends ViewModel {
    private MutableLiveData<Float> totalExpense = new MutableLiveData<>(0f);
    private MutableLiveData<Float> totalRevenue = new MutableLiveData<>(0f);
    private MutableLiveData<Float> financialSummary = new MutableLiveData<>(0f);

    public LiveData<Float> getTotalExpense() {
        return totalExpense;
    }

    public LiveData<Float> getTotalRevenue() {
        return totalRevenue;
    }

    public LiveData<Float> getFinancialSummary() {
        return financialSummary;
    }

    // Method to update the total spending and automatically update financial summary
    public void setTotalExpense(float total) {
        totalExpense.setValue(total);
        updateFinancialSummary();
    }

    // Method to update the total revenue and automatically update financial summary
    public void setTotalRevenue(float total) {
        totalRevenue.setValue(total);
        updateFinancialSummary();
    }

    // Calculate the financial summary (revenue - variableExpenses)
    private void updateFinancialSummary() {
        float spending = totalExpense.getValue() != null ? totalExpense.getValue() : 0f;
        float revenue = totalRevenue.getValue() != null ? totalRevenue.getValue() : 0f;
        financialSummary.setValue(revenue - spending);  // Subtract total variableExpenses from total revenue
    }
}
