package com.example.inventory.UserClass;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.SearchView;

import com.example.inventory.Adapter.Admin_Home_Adapter;
import com.example.inventory.R;
import com.example.inventory.SQLiteHelpers.DatabaseHelper;

public class UserSearchFragment extends Fragment {private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    SQLiteDatabase componentDatabase;
    Admin_Home_Adapter user_search_adapter;
    RecyclerView search_recycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_search_fragment,container,false);
        search_recycler = (RecyclerView)view.findViewById(R.id.user_search_recycler);


        DatabaseHelper dbHelp = new DatabaseHelper(getContext());
        componentDatabase =dbHelp.getWritableDatabase();
        search_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        user_search_adapter = new Admin_Home_Adapter(getContext(),dbHelp.getAllComponents());
        search_recycler.setAdapter(user_search_adapter);

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
            search_recycler.setAdapter( new Admin_Home_Adapter(getContext(),componentsCursor));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
