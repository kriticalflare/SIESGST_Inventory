package com.example.inventory.AdminClass;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inventory.Adapter.Admin_Logs_Adapter;
import com.example.inventory.Models.LogsModel;
import com.example.inventory.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

public class AdminLogsFragments extends Fragment {
    RecyclerView logs_recycler;
    ArrayList<LogsModel> logList;
    DatabaseReference firereflog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_logs_fragment,container,false);
        logs_recycler = (RecyclerView)view.findViewById(R.id.admin_logs_recycler);
        firereflog = FirebaseDatabase.getInstance().getReference("Logs");
        if(firereflog!=null){
            firereflog.addValueEventListener(new ValueEventListener(){
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


}
