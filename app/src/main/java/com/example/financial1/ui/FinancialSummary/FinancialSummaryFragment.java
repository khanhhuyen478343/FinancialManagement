package com.example.financial1.ui.FinancialSummary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financial1.R;
import com.example.financial1.adapter.TopTotalAdapter;
import com.example.financial1.entity.StatisticTypeOfRevenue;
import com.example.financial1.entity.StatisticTypeOfVariableExpenses;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FinancialSummaryFragment extends Fragment {

    private TextView textFinancialSummary;
    private PieChart cbSummary;
    private RecyclerView rvTopTotal;

    private ArrayList<StatisticTypeOfRevenue> revenues;
    private ArrayList<StatisticTypeOfVariableExpenses> expenses;

    private FinancialSummaryViewModel financialSummaryViewModel;

    public FinancialSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_financial_summary, container, false);

        // Initialize ViewModel
        financialSummaryViewModel = new ViewModelProvider(requireActivity()).get(FinancialSummaryViewModel.class);

        textFinancialSummary = rootView.findViewById(R.id.textFinancialSummary);
        cbSummary = rootView.findViewById(R.id.cbSummary);  // Use PieChart
        rvTopTotal = rootView.findViewById(R.id.rvTopTotal);

        // Observe the financial summary updates
        financialSummaryViewModel.getFinancialSummary().observe(getViewLifecycleOwner(), financialSummary -> {
            if (financialSummary != null) {
                textFinancialSummary.setText("" + financialSummary + " $");
                Log.d("FinancialSummary", "Total: " + financialSummary);
            }
        });

        // Get real data from your data source (e.g., database, API, etc.)
        revenues = getRevenueData();  // Replace with your actual database fetching logic
        expenses = getExpenseData();  // Replace with your actual database fetching logic

        // Log fetched data
        Log.d("RevenueData", "Revenues: " + revenues.size());
        Log.d("ExpenseData", "Expenses: " + expenses.size());

        // Test with dummy data if no data is fetched
        if (revenues.isEmpty() || expenses.isEmpty()) {
            Log.d("FinancialSummary", "Using dummy data...");
            revenues.add(new StatisticTypeOfRevenue("Salary", 3000));
            expenses.add(new StatisticTypeOfVariableExpenses("Rent", 1200));
            expenses.add(new StatisticTypeOfVariableExpenses("Utilities", 200));
        }

        // Check if no data is available
        if (revenues.isEmpty() || expenses.isEmpty()) {
            textFinancialSummary.setText("No financial data available.");
            cbSummary.setVisibility(View.GONE); // Hide the chart if no data
            rvTopTotal.setVisibility(View.GONE); // Hide RecyclerView if no data
        } else {
            // Calculate total revenue and total expense
            float totalRevenue = 0;
            float totalExpense = 0;
            for (StatisticTypeOfRevenue revenue : revenues) {
                totalRevenue += revenue.getTotal();
            }
            for (StatisticTypeOfVariableExpenses expense : expenses) {
                totalExpense += expense.getTotal();
            }

            // Update ViewModel with the calculated values
            financialSummaryViewModel.setTotalRevenue(totalRevenue);
            financialSummaryViewModel.setTotalExpense(totalExpense);

            // Call the updatePieChart method to update the chart with custom colors
            updatePieChart(totalRevenue, totalExpense);

            // Sort expenses in descending order based on the total amount of money
            sortExpensesDescending(expenses);

            // Display top expenses in RecyclerView
            rvTopTotal.setLayoutManager(new LinearLayoutManager(getContext()));
            TopTotalAdapter adapter = new TopTotalAdapter(expenses);
            rvTopTotal.setAdapter(adapter);
        }

        return rootView;
    }

    // Method to sort expenses by total money in descending order
    private void sortExpensesDescending(ArrayList<StatisticTypeOfVariableExpenses> expenses) {
        // Sort the expenses list in descending order by amount of money
        Collections.sort(expenses, new Comparator<StatisticTypeOfVariableExpenses>() {
            @Override
            public int compare(StatisticTypeOfVariableExpenses e1, StatisticTypeOfVariableExpenses e2) {
                // Compare by total (amountofmoney) in descending order
                return Float.compare(e2.getTotal(), e1.getTotal());  // Swap e2 and e1 to get descending order
            }
        });
    }

    private void updatePieChart(float totalRevenue, float totalExpense) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalRevenue, "Total Revenue"));
        entries.add(new PieEntry(totalExpense, "Total Expense"));

        // Set colors for Total Revenue and Total Expense
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.teal_700));  // Replace with your desired color
        colors.add(getResources().getColor(R.color.purple_700));  // Replace with your desired color

        // Create a PieDataSet and set the colors
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        // Set font size and color for the value labels (values on the PieChart)
        dataSet.setValueTextSize(14f);  // Change the value to the desired font size
        dataSet.setValueTextColor(getResources().getColor(R.color.black));  // Replace with your desired color

        // Create PieData
        PieData pieData = new PieData(dataSet);

        // Set data and update the PieChart
        cbSummary.setData(pieData);

        // Set font size and color for the entry labels (the labels "Total Revenue" and "Total Expense")
        cbSummary.setEntryLabelTextSize(16f);  // Set the font size for entry labels
        cbSummary.setEntryLabelColor(getResources().getColor(R.color.black));  // Set the color for entry labels

        // Set the legend text size (adjust as needed)
        Legend legend = cbSummary.getLegend();
        legend.setTextSize(15f);  // Set the font size for the legend

        // Disable the description (the text on the right side)
        Description description = cbSummary.getDescription();
        description.setEnabled(false);  // This will hide the description text

        cbSummary.invalidate();  // Refresh the chart
    }

    private ArrayList<StatisticTypeOfRevenue> getRevenueData() {
        ArrayList<StatisticTypeOfRevenue> revenues = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getActivity().openOrCreateDatabase("expense_management_db", Context.MODE_PRIVATE, null);
            cursor = db.rawQuery("SELECT name, amountofmoney FROM Revenue", null);

            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(0);
                float total = cursor.getFloat(1);
                Log.d("RevenueData", "Name: " + name + ", Total: " + total);  // Log data
                revenues.add(new StatisticTypeOfRevenue(name, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return revenues;
    }

    private ArrayList<StatisticTypeOfVariableExpenses> getExpenseData() {
        ArrayList<StatisticTypeOfVariableExpenses> expenses = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getActivity().openOrCreateDatabase("expense_management_db", Context.MODE_PRIVATE, null);

            // Query to fetch Variable Expenses
            cursor = db.rawQuery("SELECT name, amountofmoney FROM VariableExpenses", null);
            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(0);
                float total = cursor.getFloat(1);
                Log.d("ExpenseData", "Variable Expense - Name: " + name + ", Total: " + total);
                expenses.add(new StatisticTypeOfVariableExpenses(name, total));
            }

            // Query to fetch Recurring Expenses
            cursor = db.rawQuery("SELECT name, amount FROM recurring_expenses", null);
            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(0);
                float total = cursor.getFloat(1);
                Log.d("ExpenseData", "Recurring Expense - Name: " + name + ", Total: " + total);
                expenses.add(new StatisticTypeOfVariableExpenses(name, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return expenses;
    }
}
