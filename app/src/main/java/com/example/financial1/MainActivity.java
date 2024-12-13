package com.example.financial1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.financial1.databinding.ActivityMainBinding;
import com.example.financial1.dialog.VariableExpensesDialog;
import com.example.financial1.dialog.RevenueDialog;
import com.example.financial1.dialog.TypeOfVariableExpensesDialog;
import com.example.financial1.dialog.TypeOfRevenueDialog;
//import com.example.financial1.ui.MonetaryVariableExpenses.AmountOfVariableExpensesFragment;
//import com.example.financial1.ui.MonetaryVariableExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.AmountOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryExpenses.TypeOfVariableExpensesFragment;
import com.example.financial1.ui.MonetaryRevenue.AmountOfRevenueFragment;
import com.example.financial1.ui.MonetaryRevenue.TypeOfRevenueFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra trạng thái đăng nhập
        if (!isUserLoggedIn()) {
            // Nếu chưa đăng nhập, chuyển hướng tới màn hình đăng nhập
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Đóng MainActivity để không quay lại trang này
            return;
        }

        // Tiến hành thiết lập giao diện
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        // Khởi tạo NavHostFragment sau khi setContentView
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        final MainActivity currentContext = this;

        // Thực hiện hành động khi nút FAB được nhấn
        binding.fab.setOnClickListener(view -> {
            // Lấy danh sách các fragments hiện tại trong FragmentManager
            List<Fragment> fragments = getSupportFragmentManager().getFragments();

            // Lấy fragment hiện tại (fragment cuối cùng trong danh sách)
            Fragment currentFragment = fragments.get(fragments.size() - 1);

            // Kiểm tra loại fragment hiện tại và hiển thị dialog phù hợp
            if (currentFragment instanceof TypeOfVariableExpensesFragment) {
                TypeOfVariableExpensesDialog dialog = new TypeOfVariableExpensesDialog(currentContext, (TypeOfVariableExpensesFragment) currentFragment);
                dialog.show();
            } else if (currentFragment instanceof AmountOfVariableExpensesFragment) {
                VariableExpensesDialog dialog = new VariableExpensesDialog(currentContext, (AmountOfVariableExpensesFragment) currentFragment);
                dialog.show();
            } else if (currentFragment instanceof TypeOfRevenueFragment) {
                TypeOfRevenueDialog dialog = new TypeOfRevenueDialog(currentContext, (TypeOfRevenueFragment) currentFragment);
                dialog.show();
            } else if (currentFragment instanceof AmountOfRevenueFragment) {
                RevenueDialog dialog = new RevenueDialog(currentContext, (AmountOfRevenueFragment) currentFragment);
                dialog.show();
            }
        });


        // Thiết lập Drawer Layout và Navigation View
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Cấu hình AppBar với các menu items
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_home, R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        // Tạo NavController để quản lý navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Thêm listener khi di chuyển đến một destination trong navigation
        navController.addOnDestinationChangedListener((controller, destination, bundle) -> {
            // Kiểm tra nếu destination là exit, sẽ quay lại trang LoginActivity
            if (destination.getId() == R.id.nav_exit) {
                // Xóa trạng thái đăng nhập
                getSharedPreferences("UserPrefs", MODE_PRIVATE).edit().putBoolean("isLoggedIn", false).apply();
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Chuyển hướng tới LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng MainActivity để không quay lại trang này
            }

            // Kiểm tra xem có phải đang ở trang nav_home
            if (destination.getId() == R.id.nav_home ) {
                binding.fab.setVisibility(View.GONE); // Ẩn FAB trên nav_home
            }
            else if(destination.getId() == R.id.nav_financialsummary ) {
                binding.fab.setVisibility(View.GONE);
            }
            else if (destination.getId() == R.id.nav_statisticvariableexpenses ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else if (destination.getId() == R.id.nav_statisticrevenue ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else if (destination.getId() == R.id.fragment_variable_expenses ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else if (destination.getId() == R.id.fragment_type_of_variable_expenses ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else if (destination.getId() == R.id.fragment_amount_of_variable_expenses ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else if (destination.getId() == R.id.nav_recurringExpenses ) {
                binding.fab.setVisibility(View.GONE); // Hiện FAB ở các trang khác
            }
            else {
                binding.fab.setVisibility(View.VISIBLE); // Hiện FAB ở các trang khác
            }

        });

        // Liên kết AppBar với NavController và setup Navigation UI
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public ActivityMainBinding getBinding() {
        return binding;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu vào ActionBar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Quản lý di chuyển trong Navigation Graph khi nhấn nút back hoặc up
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Phương thức kiểm tra xem người dùng đã đăng nhập chưa
    private boolean isUserLoggedIn() {
        // Kiểm tra trong SharedPreferences để xác nhận trạng thái đăng nhập
        return getSharedPreferences("UserPrefs", MODE_PRIVATE).getBoolean("isLoggedIn", false);
    }
}
