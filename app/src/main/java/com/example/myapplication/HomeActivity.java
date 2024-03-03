package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.classes.Song;
import com.example.myapplication.fragment.GetAppFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.LibraryFragment;
import com.example.myapplication.fragment.SearchFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout myDrawer;
    NavigationView myNavigation;
    MaterialToolbar materialToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav);
        //Method
        addControl();
        addEvents();

        //Lấy ID
        myDrawer = findViewById(R.id.drawerLayout);
        myNavigation = findViewById(R.id.navigationViewHome);
        materialToolbar= findViewById(R.id.materialToolbar);

        //Click vào materialToolbar hiện ra DrawerLayout
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

                if(item.getItemId() == R.id.mnDrawerHomeChat) {
                    // Do Something
                }

                if(item.getItemId() == R.id.mnDrawerHomeLogout) {
                    finish();
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