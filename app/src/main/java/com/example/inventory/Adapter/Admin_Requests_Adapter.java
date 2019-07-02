package com.example.inventory.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.Models.FinalRequestModel;
import com.example.inventory.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.logging.Handler;

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
    public void onBindViewHolder(@NonNull final Admin_Requests_ViewHolder holder, final int position) {
        holder.component.setText(requestModel.get(position).getComponent());
        holder.uname.setText(requestModel.get(position).getUname());
        holder.uemail.setText(requestModel.get(position).getUemail());
        holder.datetime.setText(requestModel.get(position).getDatetime());
        holder.requestcount.setText(requestModel.get(position).getRequestcount());
        holder.count.setText(String.valueOf(requestModel.get(position).getCount()));
        if(requestModel.get(position).getRequesttype().equals("return")){
            holder.requesttype.setText(requestModel.get(position).getRequesttype().toUpperCase());
            holder.requesttype.setTextColor(Color.GREEN);
        }
        if(requestModel.get(position).getRequesttype().equals("request")){
            holder.requesttype.setText(requestModel.get(position).getRequesttype().toUpperCase());
            holder.requesttype.setTextColor(Color.BLUE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String componentval = (String) holder.component.getText();
                final String unameval = (String) holder.uname.getText();
                String uemailval = (String) holder.uemail.getText();
                final String datetimeval = (String) holder.datetime.getText();
                final String requestcountval = (String) holder.requestcount.getText();
                final String availcointval = (String) holder.count.getText();

                final Dialog dialog = new Dialog(holder.context);
                dialog.setContentView(R.layout.admin_requests_dialog);
                dialog.setTitle("Request");
                final TextView components = dialog.findViewById(R.id.request_dialog_component);
                TextView user_name = dialog.findViewById(R.id.request_dialog_uname);
                TextView user_email = dialog.findViewById(R.id.request_dialog_uemail);
                TextView datelog = dialog.findViewById(R.id.request_dialog_datetime);
                final TextView request_count = dialog.findViewById(R.id.request_dialog_requestcount);
                final TextView avail_count = dialog.findViewById(R.id.request_dialog_availcount);
                TextView dialogheading = dialog.findViewById(R.id.dialog_heading);
                Button positivebut = dialog.findViewById(R.id.request_dialog_accept_request);
                Button negativebut = dialog.findViewById(R.id.request_dialog_reject_request);
                TextView returncountheading = dialog.findViewById(R.id.request_dialog_requestcount_heading);

                components.setText(componentval);
                user_name.setText(unameval);
                user_email.setText(uemailval);
                datelog.setText(datetimeval);
                request_count.setText(requestcountval);
                avail_count.setText(availcointval);


                if(requestModel.get(position).getRequesttype().equals("return")){
                    dialogheading.setText("Return");
                    positivebut.setText("ACCEPT RETURN");
                    negativebut.setText("REJECT RETURN");
                    returncountheading.setText("Return Count : ");
                    positivebut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference myref;
                                    myref =  FirebaseDatabase.getInstance().getReference("Components");



                                    myref.child(unameval).child(componentval).child("component").setValue(null);
                                    myref.child(unameval).child(componentval).child("adder").setValue(null);
                                    myref.child(unameval).child(componentval).child("count").setValue(null);
                                    myref.child("Admin").child(componentval).child("count").setValue(
                                            Integer.parseInt(availcointval) + Integer.parseInt(requestcountval));

                                    DatabaseReference Logsref;
                                    Logsref = FirebaseDatabase.getInstance().getReference("Logs");
                                    Logsref.child(datetimeval).child("component").setValue(componentval);
                                    Logsref.child(datetimeval).child("count").setValue(Integer.parseInt(requestcountval));
                                    Logsref.child(datetimeval).child("datetime").setValue(datetimeval);
                                    Logsref.child(datetimeval).child("logtype").setValue(3);
                                    Logsref.child(datetimeval).child("uname").setValue(unameval);
                                    Toast.makeText(context,"return accepted",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //TODO Remove the entry from requests firebase

                                    removeitem(position);
                                }
                            });
                }
                if (requestModel.get(position).getRequesttype().equals("request")){
                    positivebut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    DatabaseReference myref;
                                    myref =  FirebaseDatabase.getInstance().getReference("Components");

                                    if(Integer.parseInt(availcointval) > Integer.parseInt(requestcountval)){
                                    Toast.makeText(context,"request accepted",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    DatabaseReference Logsref;
                                    Logsref = FirebaseDatabase.getInstance().getReference("Logs");
                                    Logsref.child(datetimeval).child("component").setValue(componentval);
                                    Logsref.child(datetimeval).child("count").setValue(Integer.parseInt(requestcountval));
                                    Logsref.child(datetimeval).child("datetime").setValue(datetimeval);
                                    Logsref.child(datetimeval).child("logtype").setValue(1);
                                    Logsref.child(datetimeval).child("uname").setValue(unameval);


                                    myref.child(unameval).child(componentval).child("component").setValue(componentval);
                                    myref.child(unameval).child(componentval).child("adder").setValue(unameval);
                                    myref.child(unameval).child(componentval).child("count").setValue(Integer.parseInt(requestcountval));
                                    myref.child("Admin").child(componentval).child("count").setValue(
                                            Integer.parseInt(availcointval) - Integer.parseInt(requestcountval));

                                    //TODO Remove the entry from Requests firebase

                                    removeitem(position);}else{
                                        Toast.makeText(context,unameval+" has requested for more items than currently available. Add components before approving request",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                }
                negativebut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        deleteRequest(datetimeval);
                        Toast.makeText(context,"reject",Toast.LENGTH_SHORT).show();
                        removeitem(position);
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }
    public void deleteRequest(String datetime){
        DatabaseReference myref;
        myref =  FirebaseDatabase.getInstance().getReference("Requests");
        myref.child(datetime).child("component").setValue(null);
        myref.child(datetime).child("datetime").setValue(null);
        myref.child(datetime).child("requestcount").setValue(null);
        myref.child(datetime).child("requesttype").setValue(null);
        myref.child(datetime).child("uemail").setValue(null);
        myref.child(datetime).child("uname").setValue(null);
    }
    public void removeitem(int position){
        requestModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, requestModel.size());
    }

    @Override
    public int getItemCount() {
        return requestModel.size();
    }

    class Admin_Requests_ViewHolder extends RecyclerView.ViewHolder{
        TextView component,uname,uemail,datetime,requestcount,count,requesttype;
        CardView cardView;
        Context context;

        public Admin_Requests_ViewHolder(@NonNull View itemView) {
            super(itemView);
            component = itemView.findViewById(R.id.admin_request_card_component);
            uname = itemView.findViewById(R.id.admin_request_card_uname);
            uemail = itemView.findViewById(R.id.admin_request_card_uemail);
            datetime = itemView.findViewById(R.id.admin_request_card_datetime);
            requestcount = itemView.findViewById(R.id.admin_request_card_requestcount);
            count = itemView.findViewById(R.id.admin_request_card_count);
            cardView = itemView.findViewById(R.id.admin_request_card);
            requesttype = itemView.findViewById(R.id.admin_request_card_requesttype);
            context = itemView.getContext();
        }
    }
}
