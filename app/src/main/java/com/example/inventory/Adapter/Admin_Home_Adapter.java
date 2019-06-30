package com.example.inventory.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;

import java.util.ArrayList;

public class Admin_Home_Adapter extends RecyclerView.Adapter<Admin_Home_Adapter.Admin_Home_ViewHolder>{

    ArrayList<ComponentModel> componentModel;

    public Admin_Home_Adapter(ArrayList<ComponentModel> componentModel) {
        this.componentModel = componentModel;
    }

    @NonNull
    @Override
    public Admin_Home_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_home_recycler_card,viewGroup,false);
        Admin_Home_ViewHolder adminViewHold = new Admin_Home_ViewHolder(view);

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


        public Admin_Home_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Component = itemView.findViewById(R.id.admin_home_card_component);
            Count = itemView.findViewById(R.id.admin_home_card_count);
        }
    }
}
