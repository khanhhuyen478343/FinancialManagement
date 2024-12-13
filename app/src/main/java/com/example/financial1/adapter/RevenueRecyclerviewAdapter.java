package com.example.financial1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financial1.R;
import com.example.financial1.entity.Revenue;

import java.util.List;

public class RevenueRecyclerviewAdapter extends RecyclerView.Adapter<RevenueRecyclerviewAdapter.RevenueViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Revenue> mList;

    public static ItemClickListener itemEditClickListener;
    public static ItemClickListener itemViewClickListener;

    public RevenueRecyclerviewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemEditClickListener(ItemClickListener itemEditClickListener) {
        RevenueRecyclerviewAdapter.itemEditClickListener = itemEditClickListener;
    }

    public void setOnItemViewClickListener(ItemClickListener itemViewClickListener) {
        RevenueRecyclerviewAdapter.itemViewClickListener = itemViewClickListener;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_revenue_item, parent, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        if(mList != null){
            holder.tvName.setText(mList.get(position).name);
            holder.tvAmount.setText("" + mList.get(position).amountofmoney + "$");
            holder.position = position;
        }
    }
    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    public Revenue getItem(int position){
        if(mList == null || position >= mList.size()){
            return null;
        }
        return mList.get(position);
    }

    public void setList(List<Revenue> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvAmount;
        public ImageView ivEdit, ivView;
        public CardView cv;
        public int position;
        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            ivView = itemView.findViewById(R.id.ivView);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            cv = (CardView) itemView;
            ivView.setOnClickListener((View)->{
                    if(itemViewClickListener != null){
                        itemViewClickListener.onItemClick(position);
                }
            });
            ivEdit.setOnClickListener((View)->{
                    if(itemEditClickListener != null){
                        itemEditClickListener.onItemClick(position);
                }
            });
        }
    }
}
