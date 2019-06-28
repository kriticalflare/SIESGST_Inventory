package com.example.inventory.AdminClass;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.example.inventory.SQLiteHelpers.DatabaseHelper;

import java.text.SimpleDateFormat;

public class AddComponentBottomSheet extends BottomSheetDialogFragment {

    private SQLiteDatabase mDatabase;
    private Button addComp;
    private Spinner category_spinner;
    private EditText components,count,admin_name;
    private DatabaseHelper dbHelp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.add_component_bottom_fragment,container,false);

        components = (EditText)v.findViewById(R.id.add_components);
        count = (EditText) v.findViewById(R.id.count_edit);
        admin_name = (EditText)v.findViewById(R.id.admin_name);
        addComp = (Button)v.findViewById(R.id.submit_components);
        category_spinner =(Spinner)v.findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category,android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(spinner_adapter);

        dbHelp = new DatabaseHelper(getContext());



        addComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy , h:mm a");
                String dateString = sdf.format(date);
                String category_selected = category_spinner.getSelectedItem().toString();
                ComponentModel newcomp = new ComponentModel(
                        components.getText().toString().trim(),
                        dateString,
                        category_selected.trim(),
                        Integer.parseInt(count.getText().toString().trim()),
                        admin_name.getText().toString().trim());
                boolean comp_inserted = dbHelp.insertComponent(newcomp);
                if(comp_inserted ==true)
                    Toast.makeText(getContext()," Data Inserted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"Data Not Inserted",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return  v;

    }
}
