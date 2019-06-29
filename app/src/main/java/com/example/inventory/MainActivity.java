package com.example.inventory;

import android.content.Intent;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventory.Adapter.Slider_Adapter;
import com.example.inventory.AdminClass.Admin_Main;
import com.example.inventory.UserClass.User_Main;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout bottomlayout;
    private Slider_Adapter slider_adapter;
    private TextView[] mDots ;
    private Button nextButton,backButton,userlogin,adminlogin;
    private int CurrentPage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        bottomlayout  = (LinearLayout) findViewById(R.id.bottomlayout);
        nextButton = (Button)findViewById(R.id.nextbutton);
        backButton = (Button) findViewById(R.id.backbutton);
        userlogin = (Button)findViewById(R.id.userlogin);
        adminlogin =(Button)findViewById(R.id.adminlogin);

        slider_adapter = new Slider_Adapter(this);
        viewPager.setAdapter(slider_adapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewPagelistener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(CurrentPage +1);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(CurrentPage -1);
            }
        });
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminintent = new Intent(getApplicationContext(), Admin_Main.class);
                startActivity(adminintent);
                finish();
            }
        });
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userintent = new Intent(getApplicationContext(), User_Main.class);
                startActivity(userintent);
                finish();
            }
        });


    }





    public void addDotsIndicator (int position ) {
        mDots = new TextView[3];
        bottomlayout.removeAllViews();
        for (int i = 0 ; i < mDots.length ; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor( getResources().getColor( R.color.colorPrimaryDark));
            bottomlayout.setGravity(Gravity.CENTER_HORIZONTAL);
            bottomlayout.addView(mDots[i]);
        }

        if (mDots.length > 0 ){
            mDots[position].setTextColor( getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewPagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
             CurrentPage = i;

             if(i ==0 ){
                 nextButton.setEnabled(true);
                 backButton.setEnabled(false);
                 backButton.setVisibility(View.INVISIBLE);
                 userlogin.setVisibility(View.INVISIBLE);
                 adminlogin.setVisibility(View.INVISIBLE);
                 nextButton.setText("Next");
                 backButton.setText("");
             }else if ( i == mDots.length -1){
                 nextButton.setEnabled(false);
                 backButton.setEnabled(false);
                 backButton.setVisibility(View.INVISIBLE);
                 nextButton.setVisibility(View.INVISIBLE);
                 userlogin.setVisibility(View.VISIBLE);
                 adminlogin.setVisibility(View.VISIBLE);
             }else {
                 nextButton.setEnabled(true);
                 backButton.setEnabled(true);
                 backButton.setVisibility(View.VISIBLE);
                 nextButton.setVisibility(View.VISIBLE);
                 nextButton.setText("Next");
                 backButton.setText("Back");
                 userlogin.setVisibility(View.INVISIBLE);
                 adminlogin.setVisibility(View.INVISIBLE);
             }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
