package com.example.inventory.UserClass;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.inventory.Adapter.Admin_Home_Adapter;
import com.example.inventory.Adapter.User_Search_Adapter;
import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserSearchFragment extends Fragment {
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    RecyclerView search_recycler;
    DatabaseReference firerefComp;
    ArrayList<ComponentModel> componentList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_fragment,container,false);
        search_recycler = (RecyclerView)view.findViewById(R.id.user_search_recycler);
        search_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firerefComp = FirebaseDatabase.getInstance().getReference("Components").child("Admin");
        if(firerefComp!=null){
            firerefComp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        componentList = new ArrayList<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            componentList.add(ds.getValue(ComponentModel.class));
                        }
                        User_Search_Adapter user_search_adapter = new User_Search_Adapter(componentList);
                        search_recycler.setAdapter(user_search_adapter);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.components_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    searhComponets(newText);
                    Log.i("onQueryTextChange", newText);

                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searhComponets(query);
                    Log.i("onQueryTextSubmit", query);

                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searhComponets (String keyword){
        ArrayList<ComponentModel> searchlist = new ArrayList<>();
        if (componentList != null){
            for (ComponentModel object : componentList){
            if (object.getComponent().toLowerCase().contains(keyword.toLowerCase())) {
                searchlist.add(object);
                }
            }
        }
        User_Search_Adapter search_adapter =  new User_Search_Adapter(searchlist);
        search_recycler.setAdapter(search_adapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
