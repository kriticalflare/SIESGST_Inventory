package com.example.inventory.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.Models.ComponentModel;
import com.example.inventory.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.components.Component;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class User_Home_Adapter extends RecyclerView.Adapter<User_Home_Adapter.User_Home_ViewHolder> {

    ArrayList<ComponentModel>componentModel;
    GoogleSignInClient mGoogleSignInClient;
    Dialog dialog;

    public User_Home_Adapter(ArrayList<ComponentModel> componentModel) {
        this.componentModel = componentModel;
    }

    @NonNull
    @Override
    public User_Home_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_recycler_card,parent,false);
       final User_Home_ViewHolder userViewHolder = new User_Home_ViewHolder(view);

        dialog = new Dialog(userViewHolder.context);
        dialog.setContentView(R.layout.user_return_dialog);


        userViewHolder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(userViewHolder.context, gso);
                final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(userViewHolder.context);

                TextView user_dialog_component = (TextView)dialog.findViewById(R.id.return_dialog_component);
                TextView user_dialog_count = (TextView)dialog.findViewById(R.id.return_dialog_quantity);

                user_dialog_component.setText(componentModel.get(userViewHolder.getLayoutPosition()).getComponent());
                user_dialog_count.setText(String.valueOf(componentModel.get(userViewHolder.getLayoutPosition()).getCount()));

                dialog.setTitle("Request");
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialog.findViewById(R.id.return_dialog_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long date = System.currentTimeMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy , h:mm:ss a");
                        final String dateString = sdf.format(date);
                        DatabaseReference myref;
                        myref =  FirebaseDatabase.getInstance().getReference("Components").child("Admin")
                                .child(userViewHolder.Component.getText().toString()).child("count");
                        myref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Integer availcount = dataSnapshot.getValue(Integer.class);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        DatabaseReference submitref;
                        submitref = FirebaseDatabase.getInstance().getReference("Requests");
                        submitref.child(dateString).child("component").setValue(userViewHolder.Component.getText().toString());
                        submitref.child(dateString).child("datetime").setValue(dateString);
                        submitref.child(dateString).child("requestcount").setValue(userViewHolder.Count.getText().toString());
                        submitref.child(dateString).child("requesttype").setValue("return");
                        submitref.child(dateString).child("uname").setValue(account.getDisplayName());
                        submitref.child(dateString).child("uemail").setValue(account.getEmail());
                        dialog.dismiss();
                        Toast.makeText(userViewHolder.context,"Request Sent",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User_Home_ViewHolder userViewHolder, int position) {

        userViewHolder.Component.setText(componentModel.get(position).getComponent());
        userViewHolder.Count.setText(String.valueOf(componentModel.get(position).getCount()));

    }

    @Override
    public int getItemCount() {
        return componentModel.size();
    }

    public class User_Home_ViewHolder extends RecyclerView.ViewHolder {
        TextView Component,Count;
        CardView Card;
        Context context;

        public User_Home_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Component = itemView.findViewById(R.id.admin_home_card_component);
            Count = itemView.findViewById(R.id.admin_home_card_count);
            Card = itemView.findViewById(R.id.admin_home_card);
            context = itemView.getContext();

        }
    }
}
