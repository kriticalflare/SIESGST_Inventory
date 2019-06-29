package com.example.inventory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;



import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.Adapter.Slider_Adapter;
import com.example.inventory.AdminClass.Admin_Main;
import com.example.inventory.Models.LoginModel;
import com.example.inventory.UserClass.User_Main;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private ViewPager viewPager;
    private LinearLayout bottomlayout;
    private Slider_Adapter slider_adapter;
    private TextView[] mDots ;
    private Button nextButton,backButton;
    private int CurrentPage ;
    private TextView GbutText;

    private SignInButton GsignIn;
    private GoogleApiClient googleApiClient ;
    private static final int REQ_CODE = 9001;
    DatabaseReference firerefLogin;
    ArrayList<LoginModel> loginlist;


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


        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions ).build();


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
        switch (v.getId()){

            case R.id.google_sign:
                signIn();
                break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }
    private void handleresult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String email = account.getEmail();

            firerefLogin= FirebaseDatabase.getInstance().getReference("Login");
            if(firerefLogin!=null){
                firerefLogin.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            loginlist = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                loginlist.add(ds.getValue(LoginModel.class));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            ArrayList<LoginModel>login = new ArrayList<>();
            for (LoginModel object : loginlist){
                if(object.getEmail() == email){
                    if(object.getStatus() == "admin"){
                        Intent adminIntent = new Intent(this,Admin_Main.class);
                        startActivity(adminIntent);
                        finish();
                    }
                    else if (object.getStatus()=="user"){
                        Intent userIntent = new Intent(this,User_Main.class);
                        startActivity(userIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"NOT FOUND",Toast.LENGTH_SHORT).show();
                    }



                }
            }









        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }

    }
}
