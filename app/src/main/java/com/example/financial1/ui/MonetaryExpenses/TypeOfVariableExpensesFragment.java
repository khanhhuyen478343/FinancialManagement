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
import com.example.financial1.adapter.TypeOfVariableExpensesRecyclerviewAdapter;
import com.example.financial1.dialog.TypeOfVariableExpensesDetailDialog;
import com.example.financial1.dialog.TypeOfVariableExpensesDialog;
import com.example.financial1.entity.TypeOfVariableExpenses;
import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesViewModel;

import java.util.List;

public class TypeOfVariableExpensesFragment extends Fragment {
    private RecyclerView mRv;
    private TypeOfVariableExpensesRecyclerviewAdapter mAdapter;
    private TypeOfVariableExpensesViewModel mViewModel;

    public static TypeOfVariableExpensesFragment newInstance() {
        return new TypeOfVariableExpensesFragment();
    }

    public TypeOfVariableExpensesViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_type_of_variable_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Liên kết RecyclerView với Adapter
        mRv = view.findViewById(R.id.recyclerView);
        mAdapter = new TypeOfVariableExpensesRecyclerviewAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mAdapter);

        // Liên kết nút btAddVariableExpenseType
        View btAddVariableExpenseType = view.findViewById(R.id.btAddVariableExpenseType);
        btAddVariableExpenseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở dialog để thêm mới TypeOfVariableExpenses
                showAddTypeOfVariableExpensesDialog();
            }
        });

        final TypeOfVariableExpensesFragment currentFragment = this;
        mAdapter.setOnItemEditClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TypeOfVariableExpenses typeOfVariableExpenses = mAdapter.getItem(position);
                TypeOfVariableExpensesDialog dialog = new TypeOfVariableExpensesDialog(getActivity(), currentFragment, typeOfVariableExpenses);
                dialog.show();
            }
        });

        mAdapter.setOnItemViewClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TypeOfVariableExpenses typeOfVariableExpenses = mAdapter.getItem(position);
                TypeOfVariableExpensesDetailDialog dialog = new TypeOfVariableExpensesDetailDialog(getActivity(), currentFragment,
                        typeOfVariableExpenses);
                dialog.show();
            }
        });

        // Cài đặt swipe để xóa item
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
                        TypeOfVariableExpenses toe = mAdapter.getItem(position);

                        Toast.makeText(getActivity(), "Type Of Variable Expenses deleted", Toast.LENGTH_SHORT).show();
                        mViewModel.delete(toe);
                    }
                }
        );
        helper.attachToRecyclerView(mRv);
    }

    // Hàm hiển thị dialog để thêm TypeOfVariableExpenses mới
    private void showAddTypeOfVariableExpensesDialog() {
        TypeOfVariableExpensesDialog dialog = new TypeOfVariableExpensesDialog(
                requireContext(), // Context của Fragment
                this              // Fragment hiện tại
        );
        dialog.show(); // Hiển thị dialog
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TypeOfVariableExpensesViewModel.class);
        mViewModel.getAllTypeOfVariableExpenses().observe(getActivity(), new Observer<List<TypeOfVariableExpenses>>() {
            @Override
            public void onChanged(List<TypeOfVariableExpenses> typeOfVariableExpensess) {
                mAdapter.setList(typeOfVariableExpensess);
            }
        });
    }

}