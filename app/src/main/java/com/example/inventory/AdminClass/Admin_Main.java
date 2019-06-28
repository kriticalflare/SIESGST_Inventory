package com.example.inventory.AdminClass;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.inventory.R;
import com.example.inventory.SQLiteHelpers.DatabaseHelper;

public class Admin_Main extends AppCompatActivity  {

    private BottomNavigationView bottomnavigate ;
    private FloatingActionButton addComp;
//    DatabaseHelper dbHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__main);

//        dbHelp = new DatabaseHelper(this);
        addComp = (FloatingActionButton)findViewById(R.id.add_components);
        bottomnavigate = (BottomNavigationView)findViewById(R.id.admin_bottom_navigation);
        bottomnavigate.setOnNavigationItemSelectedListener(admin_bottom_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment, new AdminHomeFragment()).commit();

        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddComponentBottomSheet addCompSheet = new AddComponentBottomSheet();
                addCompSheet.show(getSupportFragmentManager(),"Add Component Sheet");
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener admin_bottom_listener  =
    new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.home_icon:
                    selectedFragment = new AdminHomeFragment();
                    break;
                case R.id.logs_icon:
                    selectedFragment = new AdminLogsFragments();
                    break;
                case R.id.request_icon:
                    selectedFragment = new AdminRequestsFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment,selectedFragment).commit();
            return true;

        }
    };
}
