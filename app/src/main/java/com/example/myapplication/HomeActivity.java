package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.classes.Song;
import com.example.myapplication.fragment.GetAppFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.LibraryFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout myDrawer;
    NavigationView myNavigation;
    MaterialToolbar materialToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View headerView;
    TextView userName;
    private  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav);
        //Method
        addControl();
        addEvents();
        //

        //Lấy ID
        myDrawer = findViewById(R.id.drawerLayout);
        myNavigation = findViewById(R.id.navigationViewHome);
        materialToolbar= findViewById(R.id.materialToolbar);
        headerView = myNavigation.getHeaderView(0);
        userName = headerView.findViewById(R.id.activity_main_tv_email);
        Menu menu_option_home = materialToolbar.getMenu();
        MenuItem mnUser = menu_option_home.getItem(0);
        //

        //Event click vào hiển thị thông tin của user
        mnUser.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if(user != null){
                    Intent i = new Intent(HomeActivity.this, InfoUserActivity.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
        //
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, myDrawer, materialToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        myDrawer.addDrawerListener(actionBarDrawerToggle);
        // Load Fragment chỉ định khi khởi chạy app
        loadFragment(new HomeFragment());
        // Tạo ActionBarDrawerToggle để kết nối DrawerLayout với thanh toolbar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        myDrawer.addDrawerListener(actionBarDrawerToggle);
        myNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int size = myNavigation.getMenu().size();
                for (int i = 0; i < size; i++) {
                    myNavigation.getMenu().getItem(i).setChecked(false);
                }
                if(item.getItemId() == R.id.mnDrawerHomeLogout) {
                    // Tạo hộp thoại thông báo xác nhận
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Xác nhận đăng xuất");
                    builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
                    // Thêm nút đồng ý và hủy bỏ vào hộp thoại
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Đăng xuất người dùng
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Đóng hộp thoại nếu người dùng chọn hủy bỏ
                            dialogInterface.dismiss();
                        }
                    });
                    // Hiển thị hộp thoại
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                item.setChecked(true);
                myDrawer.close();
                return true;
            }
        });

        // Hiển thị icon của DrawerLayout trên thanh toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Đồng bộ hóa trạng thái của ActionBarDrawerToggle
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Sử dụng biến instance user đã khai báo ở đầu lớp
        if (user != null) {
            String emailUser = user.getEmail();
            userName.setText(emailUser);
        } else {
            // Xử lý trường hợp không có người dùng đang đăng nhập
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    void loadFragment(Fragment fmNew){
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_frame, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option_home, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fmNew;
                if(item.getItemId() == R.id.bottomHome){
                    fmNew = new HomeFragment();
                    loadFragment(fmNew);
                    return true;
                }
                if(item.getItemId() == R.id.bottomSearch){
                    fmNew = new SearchFragment();
                    loadFragment(fmNew);
                    return true;
                }
                if(item.getItemId() == R.id.bottomLibrary){
                    fmNew = new LibraryFragment();
                    loadFragment(fmNew);
                    return true;
                }
                if(item.getItemId() == R.id.bottomItemChat){
                    fmNew = new GetAppFragment();
                    loadFragment(fmNew);
                    return true;
                }
                return true;
            }
        });
    }
    private void addControl() {
        bottomNavigationView = findViewById(R.id.bottomNav);
    }
}