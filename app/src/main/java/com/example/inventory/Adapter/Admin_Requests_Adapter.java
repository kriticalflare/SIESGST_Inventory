package com.example.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.Models.FinalRequestModel;
import com.example.inventory.R;

import java.util.ArrayList;

public class Admin_Requests_Adapter extends RecyclerView.Adapter<Admin_Requests_Adapter.Admin_Requests_ViewHolder> {

    ArrayList<FinalRequestModel> requestModel;
    Context context;


    public Admin_Requests_Adapter(ArrayList<FinalRequestModel> requestModel,Context context){
        this.requestModel=requestModel;
        this.context=context;
    }

    @NonNull
    @Override
    public Admin_Requests_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_request_card,parent,false);
        final Admin_Requests_ViewHolder adminViewHold = new Admin_Requests_ViewHolder(view);

        return adminViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Requests_ViewHolder holder, int position) {
        holder.component.setText(requestModel.get(position).getComponent());
        holder.uname.setText(requestModel.get(position).getUname());
        holder.uemail.setText(requestModel.get(position).getUemail());
        holder.datetime.setText(requestModel.get(position).getDatetime());
        holder.requestcount.setText(requestModel.get(position).getRequestcount());
        holder.count.setText(String.valueOf(requestModel.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return requestModel.size();
    }

    class Admin_Requests_ViewHolder extends RecyclerView.ViewHolder{
        TextView component,uname,uemail,datetime,requestcount,count;
        CardView cardView;

        public Admin_Requests_ViewHolder(@NonNull View itemView) {
            super(itemView);
            component = itemView.findViewById(R.id.admin_request_card_component);
            uname = itemView.findViewById(R.id.admin_request_card_uname);
            uemail = itemView.findViewById(R.id.admin_request_card_uemail);
            datetime = itemView.findViewById(R.id.admin_request_card_datetime);
            requestcount = itemView.findViewById(R.id.admin_request_card_requestcount);
            count = itemView.findViewById(R.id.admin_request_card_count);
            cardView = itemView.findViewById(R.id.admin_request_card);
        }
    }




}
