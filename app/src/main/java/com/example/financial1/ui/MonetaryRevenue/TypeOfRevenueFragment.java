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
import com.example.financial1.adapter.TypeOfRevenueRecyclerviewAdapter;
import com.example.financial1.dialog.TypeOfRevenueDetailDialog;
import com.example.financial1.dialog.TypeOfRevenueDialog;
import com.example.financial1.entity.TypeOfRevenue;

import java.util.List;

public class TypeOfRevenueFragment extends Fragment {
    private RecyclerView mRv;
    private TypeOfRevenueRecyclerviewAdapter mAdapter;
    private TypeOfRevenueViewModel mViewModel;

    public static TypeOfRevenueFragment newInstance() {
        return new TypeOfRevenueFragment();
    }

    public TypeOfRevenueViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_of_revenue, container, false);

        // Tìm nút btAddRevenueType
        View btAddRevenueType = view.findViewById(R.id.btAddRevenueType);

        // Đặt sự kiện click cho nút
        btAddRevenueType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút được nhấn, hiển thị TypeOfRevenueDialog
                TypeOfRevenueDialog dialog = new TypeOfRevenueDialog(getActivity(), TypeOfRevenueFragment.this, null); // null nếu không có đối tượng TypeOfRevenue
                dialog.show();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRv = view.findViewById(R.id.recyclerView);
        mAdapter = new TypeOfRevenueRecyclerviewAdapter(getActivity());
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mAdapter);
        final TypeOfRevenueFragment currentFragment = this;

        mAdapter.setOnItemEditClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TypeOfRevenue typeOfRevenue = mAdapter.getItem(position);
                TypeOfRevenueDialog dialog = new TypeOfRevenueDialog(getActivity(),currentFragment, typeOfRevenue);
                dialog.show();
            }
        });
        mAdapter.setOnItemViewClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TypeOfRevenue typeOfRevenue = mAdapter.getItem(position);
                TypeOfRevenueDetailDialog dialog = new TypeOfRevenueDetailDialog(getActivity(), currentFragment,
                        typeOfRevenue);
                dialog.show();
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                ) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        TypeOfRevenue tor = mAdapter.getItem(position);

                        Toast.makeText(getActivity(), "Type Of Revenue deleted", Toast.LENGTH_SHORT).show();
                        mViewModel.delete(tor);
                    }
                }
        );
        helper.attachToRecyclerView(mRv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TypeOfRevenueViewModel.class);
        mViewModel.getAllTypeOfRevenue().observe(getActivity(), new Observer<List<TypeOfRevenue>>() {
            @Override
            public void onChanged(List<TypeOfRevenue> typeOfRevenues) {
                mAdapter.setList(typeOfRevenues);
            }
        });
    }

}