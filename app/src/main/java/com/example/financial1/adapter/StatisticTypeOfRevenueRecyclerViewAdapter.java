package com.example.financial1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financial1.R;
import com.example.financial1.entity.StatisticTypeOfRevenue;

import java.util.List;

public class StatisticTypeOfRevenueRecyclerViewAdapter
        extends RecyclerView.Adapter<StatisticTypeOfRevenueRecyclerViewAdapter.StatisticTypeOfRevenueViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<StatisticTypeOfRevenue> mList;

    public StatisticTypeOfRevenueRecyclerViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StatisticTypeOfRevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_statistic_type_of_revenue, parent, false);

        return new StatisticTypeOfRevenueViewHolder(view);
    }


@Override
public void onBindViewHolder(@NonNull StatisticTypeOfRevenueViewHolder holder, int position) {
    if(mList != null){
        holder.tvTypeOfRevenueName.setText(mList.get(position).getName());
        holder.etTotalTypeOfRevenue.setText(String.valueOf(mList.get(position).getTotal()));
    }
}

    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    public void setList(List<StatisticTypeOfRevenue> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public static class StatisticTypeOfRevenueViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTypeOfRevenueName;
        public EditText etTotalTypeOfRevenue;
        public StatisticTypeOfRevenueViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTypeOfRevenueName = itemView.findViewById(R.id.tvTypeOfRevenueName);
            etTotalTypeOfRevenue = itemView.findViewById(R.id.etTotalTypeOfRevenue);
        }
    }
}
