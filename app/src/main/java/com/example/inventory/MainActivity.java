package com.example.inventory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;


import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventory.Adapter.Slider_Adpater;
import com.example.inventory.AdminClass.Admin_Main;
import com.example.inventory.UserClass.User_Main;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private ViewPager viewPager;
    private LinearLayout bottomlayout;
    private Slider_Adpater slider_adpater ;
    private TextView[] mDots ;
    private Button nextButton,backButton;
    private int CurrentPage ;
    private TextView GbutText;

    private SignInButton GsignIn;
    private GoogleApiClient googleApiClient ;
    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        bottomlayout  = (LinearLayout) findViewById(R.id.bottomlayout);
        nextButton = (Button)findViewById(R.id.nextbutton);
        backButton = (Button) findViewById(R.id.backbutton);
        GsignIn = (SignInButton)findViewById(R.id.google_sign);
        GsignIn.setVisibility(View.INVISIBLE);
        GsignIn.setOnClickListener(this);
        TextView  GbutText  = (TextView) GsignIn.getChildAt(0);
        GbutText.setText("Sign In With GST ID");

        slider_adpater = new Slider_Adpater(this);
        viewPager.setAdapter(slider_adpater);
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
                 GsignIn.setVisibility(View.INVISIBLE);
                 nextButton.setText("Next");
                 backButton.setText("");
             }else if ( i == mDots.length -1){
                 nextButton.setEnabled(false);
                 backButton.setEnabled(false);
                 backButton.setVisibility(View.INVISIBLE);
                 nextButton.setVisibility(View.INVISIBLE);
                 GsignIn.setVisibility(View.VISIBLE);
             }else {
                 nextButton.setEnabled(true);
                 backButton.setEnabled(true);
                 backButton.setVisibility(View.VISIBLE);
                 nextButton.setVisibility(View.VISIBLE);
                 nextButton.setText("Next");
                 backButton.setText("Back");
                 GsignIn.setVisibility(View.INVISIBLE);
             }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
