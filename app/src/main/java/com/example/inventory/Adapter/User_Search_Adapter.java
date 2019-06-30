package com.example.inventory.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.R;

public class User_Search_Adapter extends RecyclerView.Adapter<User_Search_Adapter.
        User_Search_View_Holder> {
    private Context context;

    Dialog requestDialog;
    public User_Search_Adapter(Context context){
        this.context = context ;
    }
    @NonNull
    @Override
    public User_Search_View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater  inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.user_search_recycler_card,viewGroup,false);
        final User_Search_View_Holder user_search_view_holder= new User_Search_View_Holder(view);


        requestDialog = new Dialog(context);
        requestDialog.setContentView(R.layout.user_component_request_dialog);

        user_search_view_holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_component = (TextView)requestDialog.findViewById(R.id.user_request_dialog_component);
                TextView dialog_category = (TextView)requestDialog.findViewById(R.id.user_request_dialog_category);
                TextView dialog_quantity = (TextView)requestDialog.findViewById(R.id.user_request_dialog_quantity);
                TextView dialog_admin  = (TextView)requestDialog.findViewById(R.id.user_request_dialog_admin);
                TextView dialog_count = (TextView)requestDialog.findViewById(R.id.request_count);


                TextView comp_text = (TextView)v.findViewById(R.id.user_search_card_component);


                dialog_component.setText(comp_text.getText());
                Toast.makeText(context,"Clicked ; "+comp_text.getText(),Toast.LENGTH_SHORT).show();
                requestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                requestDialog.show();
            }
        });

        return user_search_view_holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull User_Search_View_Holder user_search_view_holder, int i) {



    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class User_Search_View_Holder extends RecyclerView.ViewHolder{

        public TextView component,category,date,count,admin;
        public CardView cardView;

        public User_Search_View_Holder(@NonNull View itemView) {
            super(itemView);
            component = itemView.findViewById(R.id.user_search_card_component);
            category = itemView.findViewById(R.id.user_search_card_category);
            date = itemView.findViewById(R.id.user_search_card_date);
            count = itemView.findViewById(R.id.user_search_card_count);
            admin= itemView.findViewById(R.id.user_search_card_admin);
            cardView = itemView.findViewById(R.id.user_search_card);
        }
    }
}
