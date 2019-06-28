package com.example.inventory.AdminClass;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.example.inventory.Adapter.Admin_Home_Adapter;
import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.example.inventory.SQLiteHelpers.DatabaseHelper;

import java.util.List;

public class AdminHomeFragment extends Fragment {
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    SQLiteDatabase componentDatabase;
    Admin_Home_Adapter admin_home_adapter;
    RecyclerView home_recycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_home_fragment,container,false);
        home_recycler = (RecyclerView)view.findViewById(R.id.admin_home_recycler);


        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        componentDatabase =dbHelp.getWritableDatabase();
        home_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        admin_home_adapter = new Admin_Home_Adapter(getContext(),dbHelp.getAllComponents());
        home_recycler.setAdapter(admin_home_adapter);

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
        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        Cursor componentsCursor = dbHelp.searchComponents(keyword);
        if(componentsCursor != null){
            home_recycler.setAdapter( new Admin_Home_Adapter(getContext(),componentsCursor));
        }
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
