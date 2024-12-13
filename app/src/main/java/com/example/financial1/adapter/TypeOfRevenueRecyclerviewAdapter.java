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
import com.example.financial1.entity.TypeOfRevenue;

import java.util.List;

public class TypeOfRevenueRecyclerviewAdapter extends RecyclerView.Adapter<TypeOfRevenueRecyclerviewAdapter.TypeOfRevenueViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<TypeOfRevenue> mList;

    public static ItemClickListener itemEditClickListener;
    public static ItemClickListener itemViewClickListener;

    public TypeOfRevenueRecyclerviewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemEditClickListener(ItemClickListener itemEditClickListener) {
        TypeOfRevenueRecyclerviewAdapter.itemEditClickListener = itemEditClickListener;
    }

    public void setOnItemViewClickListener(ItemClickListener itemViewClickListener) {
        TypeOfRevenueRecyclerviewAdapter.itemViewClickListener = itemViewClickListener;
    }

    @NonNull
    @Override
    public TypeOfRevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_type_of_revenue_item, parent, false);
        return new TypeOfRevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeOfRevenueViewHolder holder, int position) {
        if(mList != null){
            holder.tvName.setText(mList.get(position).name);
            holder.position = position;
        }
    }
    @Override
    public int getItemCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    public TypeOfRevenue getItem(int position){
        if(mList == null || position >= mList.size()){
            return null;
        }
        return mList.get(position);
    }

    public void setList(List<TypeOfRevenue> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public static class TypeOfRevenueViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public ImageView ivEdit, ivView;
        public CardView cv;
        public int position;
        public TypeOfRevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivView = itemView.findViewById(R.id.ivView);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            cv = (CardView) itemView;
            ivView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemViewClickListener != null){
                        itemViewClickListener.onItemClick(position);
                    }
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemEditClickListener != null){
                        itemEditClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
