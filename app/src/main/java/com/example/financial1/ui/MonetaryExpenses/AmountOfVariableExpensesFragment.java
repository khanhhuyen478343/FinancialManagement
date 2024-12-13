package com.example.financial1.ui.MonetaryExpenses;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.financial1.R;
import com.example.financial1.adapter.ItemClickListener;
import com.example.financial1.adapter.VariableExpensesRecyclerviewAdapter;
import com.example.financial1.dialog.VariableExpensesDetailDialog;
import com.example.financial1.dialog.VariableExpensesDialog;
import com.example.financial1.entity.VariableExpenses;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AmountOfVariableExpensesFragment extends Fragment {

    private AmountOfVariableExpensesViewModel mViewModel;
    private RecyclerView mRv;
    private VariableExpensesRecyclerviewAdapter mAdapter;
    private AtomicBoolean hasShownAlert = new AtomicBoolean(false);

    public static AmountOfVariableExpensesFragment newInstance() {
        return new AmountOfVariableExpensesFragment();
    }

    public AmountOfVariableExpensesViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Liên kết RecyclerView với Adapter
        mRv = view.findViewById(R.id.recyclerView);
        mAdapter = new VariableExpensesRecyclerviewAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mAdapter);

        // Liên kết nút btAddVariableExpense
        View btAddVariableExpense = view.findViewById(R.id.btAddAmountOfVariableExpense);
        btAddVariableExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở dialog để thêm mới VariableExpenses
                showAddVariableExpensesDialog();
            }
        });

        final AmountOfVariableExpensesFragment currentFragment = this;
        mAdapter.setOnItemEditClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                VariableExpenses variableExpenses = mAdapter.getItem(position);
                VariableExpensesDialog dialog = new VariableExpensesDialog(getActivity(), currentFragment, variableExpenses);
                dialog.show();
            }
        });

        mAdapter.setOnItemViewClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                VariableExpenses variableExpenses = mAdapter.getItem(position);
                VariableExpensesDetailDialog dialog = new VariableExpensesDetailDialog(requireContext(), currentFragment, variableExpenses);
                dialog.show();
            }
        });

        // Swipe để xóa
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        VariableExpenses variableExpenses = mAdapter.getItem(position);

                        Toast.makeText(getActivity(), "Variable Expenses deleted", Toast.LENGTH_SHORT).show();

                        mViewModel.delete(variableExpenses);
                    }
                }
        );
        helper.attachToRecyclerView(mRv);
    }

    // Hàm hiển thị dialog để thêm VariableExpenses mới
    private void showAddVariableExpensesDialog() {
        VariableExpensesDialog dialog = new VariableExpensesDialog(
                requireContext(), // Context của Fragment
                this              // Fragment hiện tại
        );
        dialog.show(); // Hiển thị dialog
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amount_of_variable_expenses, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AmountOfVariableExpensesViewModel.class);
        mViewModel.getAllVariableExpenses().observe(getActivity(), new Observer<List<VariableExpenses>>() {
            @Override
            public void onChanged(List<VariableExpenses> variableExpensess) {
                mAdapter.setList(variableExpensess);
            }
        });

        mViewModel.getAmountOfVariableExpensesAlert().observe(getViewLifecycleOwner(), alertMessage -> {
            if (alertMessage != null && !hasShownAlert.get()) {
                Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
                hasShownAlert.set(true);
            }
        });
    }
}
