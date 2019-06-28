package com.example.inventory.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventory.R;
import com.example.inventory.SQLiteHelpers.DatabaseContract;

public class Admin_Home_Adapter extends RecyclerView.Adapter<Admin_Home_Adapter.Admin_Home_View_Holder> {
    private Context context;
    private Cursor cursor;
    public Admin_Home_Adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public Admin_Home_View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        LayoutInflater  inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.admin_home_recycler_card,viewGroup,false);
        return new Admin_Home_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Home_View_Holder admin_home_view_holder, final int i) {

        admin_home_view_holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView comp_text = (TextView)v.findViewById(R.id.admin_home_card_component);
                Toast.makeText(context,"Clicked :"+comp_text.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        if(!cursor.moveToPosition(i)){
            return;
        }

        final String components = cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COMPONENTS_COMP));
        String categorys  = cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COMPONENTS_CAT));
        final String dates = cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COMPONENTS_DATE));
        Integer counts  = cursor.getInt(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COMPONENTS_COUNT));
        String admins = cursor.getString(cursor.getColumnIndex(DatabaseContract.DatabaseEntry.COMPONENTS_ADMIN));

        admin_home_view_holder.component.setText(components);
        admin_home_view_holder.category.setText(categorys);
        admin_home_view_holder.date.setText(dates);
        admin_home_view_holder.count.setText(counts.toString());
        admin_home_view_holder.admin.setText(admins);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapcursor(Cursor newCursor){
        if(cursor !=null ){
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }

    }

    public class Admin_Home_View_Holder extends RecyclerView.ViewHolder {

        public CardView cardView;

        public TextView component,category,date,count,admin;

        public Admin_Home_View_Holder(@NonNull View itemView) {
            super(itemView);

            component = itemView.findViewById(R.id.admin_home_card_component);
            category = itemView.findViewById(R.id.admin_home_card_category);
            date = itemView.findViewById(R.id.admin_home_card_date);
            count = itemView.findViewById(R.id.admin_home_card_count);
            admin= itemView.findViewById(R.id.admin_home_card_admin);
            cardView = itemView.findViewById(R.id.admin_home_card);


        }
    }
}
