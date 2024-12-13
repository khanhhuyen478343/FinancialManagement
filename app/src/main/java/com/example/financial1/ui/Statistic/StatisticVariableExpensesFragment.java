package com.example.financial1.ui.Statistic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.financial1.R;
import com.example.financial1.adapter.StatisticTypeOfVariableExpensesRecyclerViewAdapter;
import com.example.financial1.entity.StatisticTypeOfVariableExpenses;
import com.example.financial1.entity.RecurringExpense;
import com.example.financial1.repository.ExpenseItem;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import androidx.annotation.NonNull;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class StatisticVariableExpensesFragment extends Fragment {
    private StatisticVariableExpensesViewModel mStatisticVariableExpensesViewModel;
    private EditText mEtTotalVariableExpenses;
    private RecyclerView rvStatisticTypeOfVariableExpenses;
    private StatisticTypeOfVariableExpensesRecyclerViewAdapter mStatisticTypeOfVariableExpensesAdapter;
    private CombinedChart mChart;

    public StatisticVariableExpensesFragment() {}

    public static StatisticVariableExpensesFragment newInstance() {
        return new StatisticVariableExpensesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_variable_expenses_statistic, container, false);

        mEtTotalVariableExpenses = view.findViewById(R.id.etTotalTypeOfVariableExpenses);
        rvStatisticTypeOfVariableExpenses = view.findViewById(R.id.rvStatisticTypeOfVariableExpenses);
        mChart = view.findViewById(R.id.cbSummary);

        mStatisticVariableExpensesViewModel = new ViewModelProvider(this).get(StatisticVariableExpensesViewModel.class);
        mStatisticTypeOfVariableExpensesAdapter = new StatisticTypeOfVariableExpensesRecyclerViewAdapter(getActivity());
        rvStatisticTypeOfVariableExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStatisticTypeOfVariableExpenses.setAdapter(mStatisticTypeOfVariableExpensesAdapter);

        setupChart();

        // Observe total variable spendings
        mStatisticVariableExpensesViewModel.getTotalVariableExpenses().observe(getViewLifecycleOwner(), total -> {
            AtomicReference<Float> totalExpenses = new AtomicReference<>((total != null) ? total : 0.0f);

            // Observe recurring spendings and calculate total
            mStatisticVariableExpensesViewModel.getRecurringExpenses().observe(getViewLifecycleOwner(), recurringExpenses -> {
                try {
                    if (recurringExpenses != null) {
                        for (RecurringExpense item : recurringExpenses) {
                            totalExpenses.set(totalExpenses.get() + (float) item.getAmount());
                        }
                    }
                    // Update total variable spendings in EditText
                    mEtTotalVariableExpenses.setText(String.format("%.2f $", totalExpenses.get()));
                } catch (Exception e) {
                    Log.e("StatisticFragment", "Error calculating total spendings", e);
                }
            });
        });

        // Observe both statistics and recurring spendings
        mStatisticVariableExpensesViewModel.getStatisticTypeOfVariableExpenses().observe(getViewLifecycleOwner(), statisticTypeOfVariableExpenses -> {
            try {
                List<ExpenseItem> spendingItems = new ArrayList<>();

                if (statisticTypeOfVariableExpenses != null) {
                    for (StatisticTypeOfVariableExpenses item : statisticTypeOfVariableExpenses) {
                        spendingItems.add(new ExpenseItem(item.getName(), item.getTotal(), false)); // false for variable spendings
                    }
                }

                // Add recurring spendings to the list
                mStatisticVariableExpensesViewModel.getRecurringExpenses().observe(getViewLifecycleOwner(), recurringExpenses -> {
                    try {
                        if (recurringExpenses != null) {
                            for (RecurringExpense item : recurringExpenses) {
                                spendingItems.add(new ExpenseItem(item.getName(), (float) item.getAmount(), true)); // true for recurring spendings
                            }
                        }

                        // Update the adapter with the combined list of ExpenseItems
                        mStatisticTypeOfVariableExpensesAdapter.setList(spendingItems);

                        // Update the chart with the new data
                        updateChart(statisticTypeOfVariableExpenses, recurringExpenses);
                    } catch (Exception e) {
                        Log.e("StatisticFragment", "Error updating spending items", e);
                    }
                });
            } catch (Exception e) {
                Log.e("StatisticFragment", "Error processing statistic data", e);
            }
        });

        return view;
    }

    private void setupChart() {
        try {
            mChart.getDescription().setEnabled(false);
            mChart.setBackgroundColor(Color.WHITE);
            mChart.setDrawGridBackground(false);
            mChart.setDrawBarShadow(false);
            mChart.setHighlightFullBarEnabled(false);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
        } catch (Exception e) {
            Log.e("StatisticFragment", "Error setting up chart", e);
        }
    }

    private void updateChart(List<StatisticTypeOfVariableExpenses> statisticTypeOfVariableExpenses, List<RecurringExpense> recurringExpenses) {
        try {
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            ArrayList<Entry> lineEntries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            // Add variable spendings to the chart
            if (statisticTypeOfVariableExpenses != null) {
                for (int i = 0; i < statisticTypeOfVariableExpenses.size(); i++) {
                    StatisticTypeOfVariableExpenses item = statisticTypeOfVariableExpenses.get(i);
                    barEntries.add(new BarEntry(i, item.getTotal()));
                    lineEntries.add(new Entry(i, item.getTotal()));
                    labels.add(item.getName());
                }
            }

            // Add recurring spendings to the chart
            if (recurringExpenses != null) {
                for (int i = 0; i < recurringExpenses.size(); i++) {
                    RecurringExpense item = recurringExpenses.get(i);
                    barEntries.add(new BarEntry(i + (statisticTypeOfVariableExpenses != null ? statisticTypeOfVariableExpenses.size() : 0), (float) item.getAmount()));
                    lineEntries.add(new Entry(i + (statisticTypeOfVariableExpenses != null ? statisticTypeOfVariableExpenses.size() : 0), (float) item.getAmount()));
                    labels.add(item.getName());
                }
            }

            // Create BarDataSet and LineDataSet for the chart
            BarDataSet barDataSet = new BarDataSet(barEntries, "Total Expenses");
            barDataSet.setColor(Color.BLUE);
            barDataSet.setDrawValues(false); // Ẩn các giá trị trên đầu cột

            LineDataSet lineDataSet = new LineDataSet(lineEntries, "Total Expenses");
            lineDataSet.setColor(Color.GREEN);
            lineDataSet.setLineWidth(2.5f);
            lineDataSet.setCircleColor(Color.GREEN);
            lineDataSet.setCircleRadius(5f);
            lineDataSet.setValueTextSize(10f);

            // Update chart
            CombinedData data = new CombinedData();
            data.setData(new BarData(barDataSet));
            data.setData(new LineData(lineDataSet));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (labels.size() == 0) return "";
                    int index = Math.round(value) % labels.size();
                    return labels.get(index);
                }
            });

            xAxis.setAxisMaximum(data.getXMax() + 0.25f);
            mChart.setData(data);
            mChart.invalidate();
        } catch (Exception e) {
            Log.e("StatisticFragment", "Error updating chart", e);
        }
    }

}
