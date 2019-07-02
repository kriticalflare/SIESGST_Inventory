package com.example.inventory.UserClass;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.inventory.Adapter.User_Home_Adapter;
import com.example.inventory.Adapter.User_Search_Adapter;
import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {
    RecyclerView home_recycler;
    DatabaseReference firerefComp;
    ArrayList<ComponentModel> componentList;
    GoogleSignInClient mGoogleSignInClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_home_fragment,container,false);
        home_recycler = (RecyclerView)view.findViewById(R.id.user_home_recycler);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        home_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firerefComp = FirebaseDatabase.getInstance().getReference("Components").child(account.getDisplayName());
        if(firerefComp!=null){
            firerefComp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        componentList = new ArrayList<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            componentList.add(ds.getValue(ComponentModel.class));
                        }
                        User_Home_Adapter user_home_adapter = new User_Home_Adapter(componentList);
                        home_recycler.setAdapter(user_home_adapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
