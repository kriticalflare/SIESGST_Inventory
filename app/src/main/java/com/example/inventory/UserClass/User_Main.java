package com.example.inventory.UserClass;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.inventory.R;

public class User_Main extends AppCompatActivity {

    private BottomNavigationView userBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);

        userBottomNav = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        userBottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new UserHomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener  =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment  =null;

                    switch ( menuItem.getItemId()){
                        case R.id.home_icon:
                            selectedFragment = new UserHomeFragment();
                            break;
                        case R.id.search_icon:
                            selectedFragment = new UserSearchFragment();
                            break;
                        case R.id.pallet_icon:
                            selectedFragment = new UserPalletFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;



                }
            };




}
