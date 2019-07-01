package com.example.inventory.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;


import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;

import java.util.ArrayList;

public class Admin_Home_Adapter extends RecyclerView.Adapter<Admin_Home_Adapter.Admin_Home_ViewHolder>{

    ArrayList<ComponentModel> componentModel;
    private Context context;

    Dialog homeDialog;
    public Admin_Home_Adapter(ArrayList<ComponentModel> componentModel, Context context) {
        this.componentModel = componentModel;
        this.context = context;
    }

    @NonNull
    @Override
    public Admin_Home_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_home_recycler_card,viewGroup,false);
        Admin_Home_ViewHolder adminViewHold = new Admin_Home_ViewHolder(view);

        homeDialog = new Dialog(context);
        homeDialog.setContentView(R.layout.admin_component_dialog);

        adminViewHold.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView admin_dialog_component = (TextView)homeDialog.findViewById(R.id.admin_dialog_component);
                TextView admin_dialog_quantity = (TextView)homeDialog.findViewById(R.id.admin_dialog_quantity);
                TextView admin_dialog_admin = (TextView)homeDialog.findViewById(R.id.admin_dialog_admin);
                TextView admin_dialog_count = (TextView)homeDialog.findViewById(R.id.admin_count);

                admin_dialog_component.setText(componentModel.get(i).getComponent());
                admin_dialog_quantity.setText(String.valueOf(componentModel.get(i).getCount()));
                admin_dialog_admin.setText(componentModel.get(i).getAdder());
                admin_dialog_count.setText(String.valueOf(componentModel.get(i).getCount()));

                homeDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.WHITE)));
                homeDialog.show();
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
