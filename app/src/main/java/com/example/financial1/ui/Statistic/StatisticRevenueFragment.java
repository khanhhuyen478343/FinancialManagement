package com.example.financial1.ui.Statistic;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.financial1.R;
import com.example.financial1.adapter.StatisticTypeOfRevenueRecyclerViewAdapter;
import com.example.financial1.entity.StatisticTypeOfRevenue;
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

import androidx.annotation.NonNull;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class StatisticRevenueFragment extends Fragment {
    private StatisticRevenueViewModel mStatisticRevenueViewModel;
    private EditText mEtTotalRevenue;
    private RecyclerView rvStatisticTypeOfRevenue;
    private StatisticTypeOfRevenueRecyclerViewAdapter mStatisticTypeOfRevenueAdapter;
    private CombinedChart mChart;

    public StatisticRevenueFragment() {
    }

    public static StatisticRevenueFragment newInstance() {
        return new StatisticRevenueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue_statistic, container, false);

        mEtTotalRevenue = view.findViewById(R.id.etTotalTypeOfRevenue);
        rvStatisticTypeOfRevenue = view.findViewById(R.id.rvStatisticTypeOfRevenue);
        mChart = view.findViewById(R.id.cbSummary);

        mStatisticRevenueViewModel = new ViewModelProvider(this).get(StatisticRevenueViewModel.class);
        mStatisticTypeOfRevenueAdapter = new StatisticTypeOfRevenueRecyclerViewAdapter(getActivity());
        rvStatisticTypeOfRevenue.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStatisticTypeOfRevenue.setAdapter(mStatisticTypeOfRevenueAdapter);

        setupChart();

        mStatisticRevenueViewModel.getTotalRevenue().observe(getViewLifecycleOwner(), total ->
                mEtTotalRevenue.setText(String.valueOf(total + " $"))
        );

        mStatisticRevenueViewModel.getStatisticTypeOfRevenues().observe(getViewLifecycleOwner(), statisticTypeOfRevenues -> {
            mStatisticTypeOfRevenueAdapter.setList(statisticTypeOfRevenues);
            updateChart(statisticTypeOfRevenues);
        });

        return view;
    }

    private void setupChart() {
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
    }

    private void updateChart(List<StatisticTypeOfRevenue> statisticTypeOfRevenues) {
        if (statisticTypeOfRevenues == null || statisticTypeOfRevenues.isEmpty()) {
            mChart.clear();
            return;
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> lineEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < statisticTypeOfRevenues.size(); i++) {
            StatisticTypeOfRevenue revenue = statisticTypeOfRevenues.get(i);
            barEntries.add(new BarEntry(i, revenue.getTotal()));
            lineEntries.add(new Entry(i, revenue.getTotal()));
            labels.add(revenue.getName());
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Total Revenue");
        barDataSet.setColor(Color.BLUE);
        barDataSet.setDrawValues(false); // Ẩn các giá trị trên đầu cột

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Total Revenue");
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setValueTextSize(10f); // Giảm kích thước chữ

        CombinedData data = new CombinedData();
        data.setData(new BarData(barDataSet));
        data.setData(new LineData(lineDataSet));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < labels.size()) {
                    return labels.get(index);
                }
                return "";
            }
        });

        xAxis.setGranularity(1f);  // Đảm bảo mỗi nhãn tương ứng với một giá trị
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);
        xAxis.setAxisMinimum(-0.5f);

        mChart.setData(data);
        mChart.invalidate();
    }


}
