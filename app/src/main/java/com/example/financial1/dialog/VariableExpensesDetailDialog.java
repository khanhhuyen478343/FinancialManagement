package com.example.financial1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.financial1.R;
import com.example.financial1.entity.VariableExpenses;
import com.example.financial1.ui.MonetaryExpenses.AmountOfVariableExpensesFragment;
//import com.example.financial1.ui.MonetaryVariableExpenses.AmountOfVariableExpensesFragment;

public class VariableExpensesDetailDialog extends Dialog {

    private VariableExpenses variableExpenses;

    public VariableExpensesDetailDialog(Context context, AmountOfVariableExpensesFragment fragment, VariableExpenses variableExpenses) {
        super(context);
        if (variableExpenses == null) {
            Log.e("VariableExpensesDetailDialog", "VariableExpenses is null");
        }
        this.variableExpenses = variableExpenses;  // Gán variableExpenses vào thuộc tính của lớp
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_variable_expenses);  // Layout cho dialog chi tiết

        TextView tvVariableExpensesName = findViewById(R.id.tvVariableExpensesName);
        TextView tvVariableExpensesAmount = findViewById(R.id.tvVariableExpensesAmount);
        TextView tvVariableExpensesType = findViewById(R.id.tvVariableExpensesType);
        TextView tvVariableExpensesNote = findViewById(R.id.tvVariableExpensesNote);
        TextView tvVariableExpensesDate = findViewById(R.id.tvVariableExpensesDate);

        // Kiểm tra nếu variableExpenses không phải null
        if (variableExpenses != null) {
            // Kiểm tra và gán giá trị cho từng trường nếu không phải null
            tvVariableExpensesName.setText(variableExpenses.name != null ? variableExpenses.name : "No available");

            // Kiểm tra nếu amountofmoney là kiểu nguyên thủy int (hoặc float), có thể dùng giá trị mặc định (0)
            tvVariableExpensesAmount.setText(variableExpenses.amountofmoney != 0 ? "$" + variableExpenses.amountofmoney : "No available");

            // Kiểm tra nếu torid là kiểu nguyên thủy int, nếu là 0 thì coi như không có giá trị hợp lệ
            tvVariableExpensesType.setText(variableExpenses.toeid != 0 ? String.valueOf(variableExpenses.toeid) : "No available");

            tvVariableExpensesNote.setText(variableExpenses.note != null ? variableExpenses.note : "No available");
            // tvVariableExpensesDate.setText(variableExpenses.date != null ? variableExpenses.date : "No available");
            tvVariableExpensesDate.setText(variableExpenses.date != null ? variableExpenses.date : "No available");
            // tvVariableExpensesDate.setText(variableExpenses.date != null ? variableExpenses.date : "No available"); // Nếu có trường ngày tháng, thêm vào đây
        } else {
            // Xử lý khi variableExpenses là null
            tvVariableExpensesName.setText("No available");
            tvVariableExpensesAmount.setText("No available");
            tvVariableExpensesType.setText("No available");
            tvVariableExpensesNote.setText("No available");
            tvVariableExpensesDate.setText("No available");
        }
    }

}
