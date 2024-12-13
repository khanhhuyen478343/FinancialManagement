package com.example.financial1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfRevenue;

import java.util.List;

public class TypeOfRevenueSpinnerAdapter extends BaseAdapter {
    private List<TypeOfRevenue> mList;
    private LayoutInflater mLayoutInflater;

    public TypeOfRevenueSpinnerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<TypeOfRevenue> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        if( mList == null)
            return null;
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AmountOfRevenueViewHolder holder;
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.spinner_revenue_item, null, false);
            holder = new AmountOfRevenueViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (AmountOfRevenueViewHolder) view.getTag();
        }
        holder.tvName.setText(mList.get(i).name);
        return view;
    }

    public static class AmountOfRevenueViewHolder{
        public TextView tvName;

        public AmountOfRevenueViewHolder(View view){
            tvName = view.findViewById(R.id.tvName);
        }
    }
}
