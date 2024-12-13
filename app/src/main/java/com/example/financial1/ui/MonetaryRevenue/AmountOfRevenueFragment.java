package com.example.financial1.ui.MonetaryRevenue;

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
import com.example.financial1.adapter.RevenueRecyclerviewAdapter;
import com.example.financial1.dialog.RevenueDetailDialog;
import com.example.financial1.dialog.RevenueDialog;
import com.example.financial1.entity.Revenue;

import java.util.List;

public class AmountOfRevenueFragment extends Fragment {

    private AmountOfRevenueViewModel mViewModel;
    private RecyclerView mRv;
    private RevenueRecyclerviewAdapter mAdapter;


    public static AmountOfRevenueFragment newInstance() {
        return new AmountOfRevenueFragment();
    }

    public AmountOfRevenueViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRv = view.findViewById(R.id.recyclerView);
        mAdapter = new RevenueRecyclerviewAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mAdapter);

        // Tìm nút btAddRevenueAmount
        View btAddRevenueAmount = view.findViewById(R.id.btAddRevenueAmount);

        // Đặt sự kiện click cho nút
        btAddRevenueAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị RevenueDialog
                RevenueDialog dialog = new RevenueDialog(getActivity(), AmountOfRevenueFragment.this, null); // null nếu không có đối tượng Revenue
                dialog.show();
            }
        });


        final AmountOfRevenueFragment currentFragment = this;
        mAdapter.setOnItemEditClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Revenue revenue = mAdapter.getItem(position);
                RevenueDialog dialog = new RevenueDialog(getActivity(),currentFragment, revenue);
                dialog.show();
            }
        });

        mAdapter.setOnItemViewClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Revenue revenue = mAdapter.getItem(position);
                RevenueDetailDialog dialog = new RevenueDetailDialog(requireContext(),currentFragment, revenue);
                dialog.show();
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Revenue revenue = mAdapter.getItem(position);

                        Toast.makeText(getActivity(), "Revenue deleted", Toast.LENGTH_SHORT).show();

                        mViewModel.delete(revenue);
                    }
                }
        );
        helper.attachToRecyclerView(mRv);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amount_of_revenue, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AmountOfRevenueViewModel.class);

        mViewModel.getAllRevenue().observe(getActivity(), new Observer<List<Revenue>>() {
            @Override
            public void onChanged(List<Revenue> revenues) {
                mAdapter.setList(revenues);
            }
        });

   }
}
