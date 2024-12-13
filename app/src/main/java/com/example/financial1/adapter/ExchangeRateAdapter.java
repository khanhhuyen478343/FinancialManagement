package com.example.financial1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.financial1.R;
import com.example.financial1.dao.ExchangeRate_json;

import java.util.List;

public class ExchangeRateAdapter extends ArrayAdapter<ExchangeRate_json> {
    private Activity context;
    private int resource;
    private List<ExchangeRate_json> objects;

    public ExchangeRateAdapter(Activity context, int resource, List<ExchangeRate_json> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        row = layoutInflater.inflate(this.resource, null);

        ImageView img = row.findViewById(R.id.id_img);
        TextView icon = row.findViewById(R.id.txt_icon);
        TextView BuyCash = row.findViewById(R.id.txt_BuyCash);
        TextView SellCash = row.findViewById(R.id.txt_SellCash);
        TextView BuyTransfer = row.findViewById(R.id.txt_BuyTransfer);
        TextView SellTransfer = row.findViewById(R.id.txt_SellTransfer);

        ExchangeRate_json exchangeRate = this.objects.get(position);


        if (exchangeRate.getBitmap() != null) {
            img.setImageBitmap(exchangeRate.getBitmap());
        } else if (exchangeRate.getImageurl() != null) {
            Glide.with(context)
                    .load(exchangeRate.getImageurl())
                    .into(img);
        }


        icon.setText(exchangeRate.getType());
        BuyCash.setText(exchangeRate.getBuycash());
        SellCash.setText(exchangeRate.getSellcash());
        BuyTransfer.setText(exchangeRate.getBuytransfer());
        SellTransfer.setText(exchangeRate.getSelltransfer());

        return row;
    }
}
