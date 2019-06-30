package com.example.inventory;

import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.Adapter.Slider_Adapter;
import com.example.inventory.AdminClass.Admin_Main;
import com.example.inventory.Models.ComponentModel;
import com.example.inventory.Models.LoginModel;
import com.example.inventory.UserClass.User_Main;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private LinearLayout bottomlayout;
    private Slider_Adapter slider_adapter;
    private TextView[] mDots ;
    private Button nextButton,backButton;
    private int CurrentPage ;
    private TextView GbutText;
    private SignInButton GsignIn;
    private static final int RC_SIGN_IN = 2;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference refLogin;
    ArrayList<LoginModel> loginlist;

    @Override
    protected void onResume() {
        super.onResume();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }


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
        TextView  GbutText  = (TextView) GsignIn.getChildAt(0);
        GbutText.setText("Sign In With GST ID");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refLogin = FirebaseDatabase.getInstance().getReference("Login");
        refLogin.keepSynced(true);
        if(refLogin!=null){
            refLogin.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        loginlist = new ArrayList<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            loginlist.add(ds.getValue(LoginModel.class));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"CANCELLED",Toast.LENGTH_SHORT).show();

                }
            });
        }
        GsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
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

    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            if (loginlist != null) {
                for (LoginModel object : loginlist) {
                    if (object.getEmail().contains(account.getEmail())) {
                        if (object.getStatus().equals("admin")) {
                            Toast.makeText(getApplicationContext(), account.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent adminIntent = new Intent(this, Admin_Main.class);
                            startActivity(adminIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), account.getEmail(), Toast.LENGTH_SHORT).show();
                            Intent userIntent = new Intent(this, User_Main.class);
                            userIntent.putExtra("ACCOUNT", account);
                            startActivity(userIntent);
                            finish();

                        }
                    }
                }
            }
        }
    }
    private void signIn() {Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
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


}
