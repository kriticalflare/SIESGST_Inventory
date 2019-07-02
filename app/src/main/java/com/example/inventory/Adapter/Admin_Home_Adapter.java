package com.example.inventory.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;


import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.Change;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Admin_Home_Adapter extends RecyclerView.Adapter<Admin_Home_Adapter.Admin_Home_ViewHolder>{

    ArrayList<ComponentModel> componentModel;
    private Context context;
    Button button;

    Dialog homeDialog;
    public Admin_Home_Adapter(ArrayList<ComponentModel> componentModel, Context context) {
            this.componentModel = componentModel;
            this.context = context;
    }

    @NonNull
    @Override
    public Admin_Home_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_home_recycler_card,viewGroup,false);
        final Admin_Home_ViewHolder adminViewHold = new Admin_Home_ViewHolder(view);

        homeDialog = new Dialog(context);
        homeDialog.setContentView(R.layout.admin_component_dialog);

        adminViewHold.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView admin_dialog_component = (TextView)homeDialog.findViewById(R.id.admin_dialog_component);
                TextView admin_dialog_quantity = (TextView)homeDialog.findViewById(R.id.admin_dialog_quantity);
                TextView admin_dialog_admin = (TextView)homeDialog.findViewById(R.id.admin_dialog_admin);
                EditText admin_dialog_count = (EditText) homeDialog.findViewById(R.id.admin_count);

                //String  number = admin_dialog_count.getText().toString();
                //int number2 = Integer.parseInt(number);

                admin_dialog_component.setText(componentModel.get(adminViewHold.getLayoutPosition()).getComponent());
                admin_dialog_quantity.setText(String.valueOf(componentModel.get(adminViewHold.getLayoutPosition()).getCount()));
                admin_dialog_admin.setText(componentModel.get(adminViewHold.getLayoutPosition()).getAdder());
                admin_dialog_count.setHint(String.valueOf(componentModel.get(adminViewHold.getLayoutPosition()).getCount()));

                homeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                homeDialog.show();
            }
        });

        button = homeDialog.findViewById(R.id.admin_dialog_change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText admin_dialog_count = (EditText) homeDialog.findViewById(R.id.admin_count);
                TextView admin_dialog_component = (TextView)homeDialog.findViewById(R.id.admin_dialog_component);
                if(admin_dialog_count.getText().toString() == null){
                    admin_dialog_count.setError("Enter a value");
                }
                if(TextUtils.isEmpty(admin_dialog_count.getText().toString())){
                    admin_dialog_count.setError("Enter new count");
                }else {
                    String item = admin_dialog_component.getText().toString();
                    String countString = admin_dialog_count.getText().toString();

                    if(countString.equals("0")){
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Components").child("Admin");
                        database.child(item).child("adder").setValue(null);
                        database.child(item).child("component").setValue(null);
                        database.child(item).child("count").setValue(null);
                    }
                    else {
                        int count = Integer.parseInt(countString);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("Components");
                        databaseReference.child("Admin").child(item).child("count").setValue(count);

                    }




                    //int ActualCount = Integer.parseInt(count);
                    //String test = admin_dialog_component.getText().toString();
                    Toast toast = new Toast(context).makeText(context,"Data Updated" +
                            "",Toast.LENGTH_SHORT);
                    //toast.show();
                    homeDialog.dismiss();

                }


            }
        });


        return adminViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Home_ViewHolder admin_home_viewHolder, int i) {
        admin_home_viewHolder.Component.setText(componentModel.get(i).getComponent());
        admin_home_viewHolder.Count.setText(String.valueOf(componentModel.get(i).getCount()));

    }


    @Override
    public int getItemCount() {
        return componentModel.size();
    }

    class Admin_Home_ViewHolder extends RecyclerView.ViewHolder{
        TextView Component,Count;
        public CardView cardView;


        public Admin_Home_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Component = itemView.findViewById(R.id.admin_home_card_component);
            Count = itemView.findViewById(R.id.admin_home_card_count);
            cardView = itemView.findViewById(R.id.admin_home_card);
        }
    }

}
