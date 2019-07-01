package com.example.inventory.AdminClass;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inventory.Adapter.Admin_Home_Adapter;
import com.example.inventory.Adapter.Admin_Requests_Adapter;
import com.example.inventory.Models.ComponentModel;
import com.example.inventory.Models.FinalRequestModel;
import com.example.inventory.Models.RequestModel;
import com.example.inventory.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminRequestsFragment extends Fragment {

    RecyclerView request_recycler;
    DatabaseReference compref,requestref;
    ArrayList<ComponentModel>componentlist;
    ArrayList<RequestModel>requestlist;
    ArrayList<FinalRequestModel>finalRequestlist;
    boolean one,two;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_requests_fragment,container,false);
        request_recycler = (RecyclerView)view.findViewById(R.id.admin_request_recycler);
        request_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        compref = FirebaseDatabase.getInstance().getReference("Components").child("Admin");
        if(compref!=null){
            compref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        componentlist = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            componentlist.add(ds.getValue(ComponentModel.class)); } } }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }});
        }
        requestref = FirebaseDatabase.getInstance().getReference("Requests");
        if(requestref!=null){
            requestref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        requestlist = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            requestlist.add(ds.getValue(RequestModel.class));
                        }
                        finalRequestlist =new ArrayList<>();
                        if(componentlist!=null && requestlist!=null){

                            for(RequestModel request : requestlist){
                                for(ComponentModel component : componentlist){
                                    if(request.getComponent().equals(component.getComponent())) {
                                        finalRequestlist.add(new FinalRequestModel(
                                                request.getComponent(), request.getRequestcount(),
                                                request.getUemail(), request.getUname(), request.getDatetime(),
                                                component.getCount())); }}}}
                        Admin_Requests_Adapter requests_adapter = new Admin_Requests_Adapter(finalRequestlist,getContext());
                        request_recycler.setAdapter(requests_adapter);

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
