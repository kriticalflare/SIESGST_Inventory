package com.example.inventory.UserClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.Adapter.Admin_Logs_Adapter;
import com.example.inventory.MainActivity;
import com.example.inventory.Models.LogsModel;
import com.example.inventory.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserLogsFragment extends Fragment {
    RecyclerView logs_recycler;
    ArrayList<LogsModel> logList;
    DatabaseReference firereflog;
    GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_logs_fragment,container,false);
        logs_recycler = (RecyclerView)view.findViewById(R.id.user_logs_recycler);
        setHasOptionsMenu(true);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());




        firereflog = FirebaseDatabase.getInstance().getReference("Logs");
        if(firereflog!=null){
            firereflog.orderByChild("uname").equalTo(account.getDisplayName()).addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    if(dataSnapshot.exists()){
                        logList = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            logList.add(ds.getValue(LogsModel.class));
                        }
                        Admin_Logs_Adapter admin_logs_adapter = new Admin_Logs_Adapter(logList);
                        logs_recycler.setAdapter(admin_logs_adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError){
                    Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }

            });
        }

        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout_menu,menu);
        MenuItem logoutBut = menu.findItem(R.id.action_logout);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())){
            case R.id.action_logout:
                Toast.makeText(getContext(),"signout",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
