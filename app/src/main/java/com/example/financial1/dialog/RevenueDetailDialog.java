package com.example.financial1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.financial1.R;
import com.example.financial1.entity.Revenue;
import com.example.financial1.ui.MonetaryRevenue.AmountOfRevenueFragment;

public class RevenueDetailDialog extends Dialog {

    private Revenue revenue;

    public RevenueDetailDialog(Context context, AmountOfRevenueFragment fragment, Revenue revenue) {
        super(context);
        if (revenue == null) {
            Log.e("RevenueDetailDialog", "Revenue is null");
        }
        this.revenue = revenue;  // Gán revenue vào thuộc tính của lớp
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_revenue);  // Layout cho dialog chi tiết

        TextView tvRevenueName = findViewById(R.id.tvRevenueName);
        TextView tvRevenueAmount = findViewById(R.id.tvRevenueAmount);
        TextView tvRevenueType = findViewById(R.id.tvRevenueType);
        TextView tvRevenueNote = findViewById(R.id.tvRevenueNote);
        TextView tvRevenueDate = findViewById(R.id.tvRevenueDate);

        // Kiểm tra nếu revenue không phải null
        if (revenue != null) {
            // Kiểm tra và gán giá trị cho từng trường nếu không phải null
            tvRevenueName.setText(revenue.name != null ? revenue.name : "No available");

            // Kiểm tra nếu amountofmoney là kiểu nguyên thủy int (hoặc float), có thể dùng giá trị mặc định (0)
            tvRevenueAmount.setText(revenue.amountofmoney != 0 ? "$" + revenue.amountofmoney : "No available");

            // Kiểm tra nếu torid là kiểu nguyên thủy int, nếu là 0 thì coi như không có giá trị hợp lệ
            tvRevenueType.setText(revenue.torid != 0 ? String.valueOf(revenue.torid) : "No available");

            tvRevenueNote.setText(revenue.note != null ? revenue.note : "No available");
            tvRevenueDate.setText(revenue.date != null ? revenue.date : "No available");

        } else {
            // Xử lý khi revenue là null
            tvRevenueName.setText("No available");
            tvRevenueAmount.setText("No available");
            tvRevenueType.setText("No available");
            tvRevenueNote.setText("No available");
            tvRevenueDate.setText("No available");
        }
    }

}
