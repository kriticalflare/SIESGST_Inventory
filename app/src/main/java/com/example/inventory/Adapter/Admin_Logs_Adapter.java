package com.example.inventory.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.Models.LogsModel;
import com.example.inventory.R;

import java.util.ArrayList;

public class Admin_Logs_Adapter extends RecyclerView.Adapter<Admin_Logs_Adapter.Admin_Logs_ViewHolder> {

    ArrayList<LogsModel> logModel;

    public Admin_Logs_Adapter(ArrayList<LogsModel> logModel){
        this.logModel = logModel;
    }

    @NonNull
    @Override
    public Admin_Logs_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_logs_recycler_card,viewGroup,false);
        Admin_Logs_ViewHolder adminLViewHold = new Admin_Logs_ViewHolder(view);
        return adminLViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Logs_Adapter.Admin_Logs_ViewHolder admin_logs_viewHolder, int i) {
        admin_logs_viewHolder.Component.setText(logModel.get(i).getComponent());
        admin_logs_viewHolder.Count.setText(String.valueOf(logModel.get(i).getCount()));
        admin_logs_viewHolder.Uname.setText(logModel.get(i).getUname());
        admin_logs_viewHolder.Time.setText(logModel.get(i).getDatetime());
        //admin_logs_viewHolder.LogType.setText(String.valueOf(logModel.get(i).getLogtype()));
        switch (logModel.get(i).getLogtype()){
            case 0:
                 admin_logs_viewHolder.LogType.setText("Added");
                Log.d("LogScreen", "onBindViewHolder: Logtype0");
                admin_logs_viewHolder.LogType.setTextColor(Color.MAGENTA);
                 break;
            case 1:
                 admin_logs_viewHolder.LogType.setText("REQUEST ACCEPTED");
                 Log.d("LogScreen", "onBindViewHolder: Logtype1");
                 admin_logs_viewHolder.LogType.setTextColor(Color.BLUE);
                 break;
            case 2:
                 admin_logs_viewHolder.LogType.setText("REJECTED");
                 Log.d("LogScreen", "onBindViewHolder: Logtype2");
                 admin_logs_viewHolder.LogType.setTextColor(Color.RED);
                 break;
            case 3:
                admin_logs_viewHolder.LogType.setText("RETURN ACCEPTED");
                Log.e("LogScreen", "onBindViewHolder: Logtype3");
                admin_logs_viewHolder.LogType.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return logModel.size();
    }

    class Admin_Logs_ViewHolder extends RecyclerView.ViewHolder{
        TextView Component,Count,Uname,Time,LogType;


        public Admin_Logs_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Component = itemView.findViewById(R.id.admin_logs_card_component);
            Count = itemView.findViewById(R.id.admin_logs_card_count);
            Uname = itemView.findViewById(R.id.admin_logs_card_uname);
            Time = itemView.findViewById(R.id.admin_logs_time);
            LogType = itemView.findViewById(R.id.admin_logs_card_type);
        }
    }

}
