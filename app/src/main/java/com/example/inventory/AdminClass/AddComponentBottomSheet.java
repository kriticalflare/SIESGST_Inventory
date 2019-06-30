package com.example.inventory.AdminClass;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inventory.Models.ComponentModel;
import com.example.inventory.Models.LogsModel;
import com.example.inventory.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class AddComponentBottomSheet extends BottomSheetDialogFragment {

    private SQLiteDatabase mDatabase;
    private Button addComp;
    private Spinner category_spinner;
    private EditText components,count,admin_name;

    FirebaseDatabase firedata;
    DatabaseReference firerefComponets;
    DatabaseReference firerefLogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.add_component_bottom_fragment,container,false);

        components = (EditText)v.findViewById(R.id.add_components);
        count = (EditText) v.findViewById(R.id.count_edit);
        addComp = (Button)v.findViewById(R.id.submit_components);

        firedata = FirebaseDatabase.getInstance();
        firerefComponets =firedata.getReference("Components");
        firerefLogs = firedata.getReference("Logs");

        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(components.getText().toString())){
                    components.setError("Enter Components");
                }
                else if (TextUtils.isEmpty(count.getText().toString())){
                    count.setError("Enter Count");
                }
                else {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy , h:mm:ss a");
                    final String dateString = sdf.format(date);
                    final ComponentModel newcomponent = new ComponentModel(components.getText().toString(),
                            "Admin",Integer.parseInt(count.getText().toString()));
                    final LogsModel newLog = new LogsModel("Admin",components.getText().toString(),
                            dateString,Integer.parseInt(count.getText().toString()),0);
                    firerefComponets.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            firerefComponets.child(newcomponent.getAdder()).child(newcomponent.getComponent())
                                    .child("count").setValue(newcomponent.getCount());
                            firerefComponets.child(newcomponent.getAdder()).child(newcomponent.getComponent())
                                    .child("component").setValue(newcomponent.getComponent());
                            firerefComponets.child(newcomponent.getAdder()).child(newcomponent.getComponent())
                                    .child("adder").setValue(newcomponent.getAdder());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    firerefLogs.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            firerefLogs.child(String.valueOf(dateString))
                                    .child("uname").setValue(newLog.getUname());
                            firerefLogs.child(String.valueOf(dateString))
                                    .child("component").setValue(newLog.getComponent());
                            firerefLogs.child(String.valueOf(dateString))
                                    .child("count").setValue(newLog.getCount());
                            firerefLogs.child(String.valueOf(dateString))
                                    .child("logtype").setValue(newLog.getLogtype());
                            firerefLogs.child(String.valueOf(dateString))
                                    .child("datetime").setValue(newLog.getDatetime());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(getContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                    dismiss();
                }
            }
        });

        return  v;

    }
}
