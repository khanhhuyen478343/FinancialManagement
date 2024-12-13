package com.example.financial1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.financial1.R;
import com.example.financial1.entity.TypeOfVariableExpenses;

import java.util.List;

public class TypeOfVariableExpensesSpinnerAdapter extends BaseAdapter {
    private List<TypeOfVariableExpenses> mList;
    private LayoutInflater mLayoutInflater;

    public TypeOfVariableExpensesSpinnerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<TypeOfVariableExpenses> mList) {
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
        AmountOfVariableExpensesViewHolder holder;
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.spinner_variable_expenses_item, null, false);
            holder = new AmountOfVariableExpensesViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (AmountOfVariableExpensesViewHolder) view.getTag();
        }
        holder.tvName.setText(mList.get(i).name);
        return view;
    }

    public static class AmountOfVariableExpensesViewHolder{
        public TextView tvName;

        public AmountOfVariableExpensesViewHolder(View view){
            tvName = view.findViewById(R.id.tvName);
        }
    }
}
