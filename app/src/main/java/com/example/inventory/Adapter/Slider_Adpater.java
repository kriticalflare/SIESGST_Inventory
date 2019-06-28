package com.example.inventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inventory.R;

public class Slider_Adpater extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public Slider_Adpater(Context context){
        this.context = context;
    }

    public  int[] slider_images ={
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3
    };
    public  String[] slider_headings ={
            "Store",
            "Logs",
            "Save Time!"
    };
    public  String[] slider_texts ={
            "Manage Database of All Components",
            "Manage Detailed Logs of Request of Components",
            "Save Time in Organizing the Inventory "
    };

    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (LinearLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  =layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slider_image = (ImageView) view.findViewById(R.id.slider_image);
        TextView slider_heading  = (TextView) view.findViewById(R.id.slider_heading);
        TextView slider_text  =(TextView)view.findViewById(R.id.slider_text);
         slider_image.setImageResource(slider_images[position]);
         slider_heading.setText(slider_headings[position]);
         slider_text.setText(slider_texts[position] );
         container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
