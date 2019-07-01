package com.example.inventory.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class User_Search_Adapter extends RecyclerView.Adapter<User_Search_Adapter.User_Search_ViewHolder> {
    ArrayList<ComponentModel> componentModel;
    GoogleSignInClient mGoogleSignInClient;



    public User_Search_Adapter(ArrayList<ComponentModel> componentModel){
        this.componentModel = componentModel;
    }

    @NonNull
    @Override
    public User_Search_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_home_recycler_card,viewGroup,false);
        User_Search_ViewHolder adminViewHold = new User_Search_ViewHolder(view);

        return adminViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull final User_Search_ViewHolder user_search_viewHolder, final int i) {
        user_search_viewHolder.Component.setText(componentModel.get(i).getComponent());
        user_search_viewHolder.Count.setText(String.valueOf(componentModel.get(i).getCount()));
        user_search_viewHolder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(user_search_viewHolder.context, gso);
                final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(user_search_viewHolder.context);
                final Dialog dialog = new Dialog(user_search_viewHolder.context);
                dialog.setContentView(R.layout.user_component_request_dialog);
                dialog.setTitle("Request");
                final TextView component = dialog.findViewById(R.id.user_request_dialog_component);
                component.setText(componentModel.get(i).getComponent());
                TextView quantity = dialog.findViewById(R.id.user_request_dialog_quantity);
                quantity.setText(String.valueOf(componentModel.get(i).getCount()));
                final TextView counter = dialog.findViewById(R.id.request_count);
                counter.setText(String.valueOf(componentModel.get(i).getCount()));
                Button increment =  dialog.findViewById(R.id.increment_button);
                Button decrement = dialog.findViewById(R.id.decrement_button);
                increment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count  = Integer.parseInt(counter.getText().toString());
                        if(count < componentModel.get(i).getCount()){
                            count= count + 1;
                            counter.setText(String.valueOf(count));
                        }
                    }
                });
                decrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count  = Integer.parseInt(counter.getText().toString());
                        if(count >0){
                            count= count - 1;
                            counter.setText(String.valueOf(count));
                        }
                    }
                });
                Button submitRequest = dialog.findViewById(R.id.user_request_dialog_submit);
                submitRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long date = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy , h:mm:ss a");
                        final String dateString = sdf.format(date);
                        DatabaseReference myref;
                        myref =  FirebaseDatabase.getInstance().getReference("Requests");
                        myref.child(dateString).child("component").setValue(component.getText());
                        myref.child(dateString).child("uemail").setValue(account.getEmail());
                        myref.child(dateString).child("requestcount").setValue(counter.getText());
                        myref.child(dateString).child("uname").setValue(account.getDisplayName());
                        myref.child(dateString).child("datetime").setValue(dateString);
                        dialog.dismiss();
                        Toast.makeText(user_search_viewHolder.context,"Request Sent",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }
    @Override
    public int getItemCount() {
        return componentModel.size();
    }
    class User_Search_ViewHolder extends RecyclerView.ViewHolder{
        TextView Component,Count;
        CardView Card;
        Context context;
        public User_Search_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Component = itemView.findViewById(R.id.admin_home_card_component);
            Count = itemView.findViewById(R.id.admin_home_card_count);
            Card = itemView.findViewById(R.id.admin_home_card);
            context  = itemView.getContext();
        }
    }


}
