package com.example.lab_project.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lab_project.R;
import com.example.lab_project.models.Image;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class slideradapter extends SliderViewAdapter<slideradapter.Holder> {

    ArrayList<Image> images = new ArrayList<>();

    public slideradapter(ArrayList<Image> images){
        this.images=images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slideritem,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.imageView.setImageBitmap(images.get(position).getImage());
    }

    @Override
    public int getCount() {
        return  images.size();
    }


    public class Holder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public Holder(View itemview){
            super(itemview);
            imageView= itemview.findViewById(R.id.home_property_imageView);// we need to make this dynamic ...
        }
    }
}


//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.smarteist.autoimageslider.SliderViewAdapter;
//
//public class slideradapter extends SliderViewAdapter<slideradapter.Holder> {
//
//    int[] images;
//
//    public slideradapter(int[] images){
//        this.images=images;
//    }
//
//    @Override
//    public Holder onCreateViewHolder(ViewGroup parent) {
//        View view= LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.slideritem,parent,false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(Holder viewHolder, int position) {
//
//        viewHolder.imageView.setImageResource(images[position]);
//    }
//
//    @Override
//    public int getCount() {
//        return  images.length;
//    }
//
//
//    public class Holder extends SliderViewAdapter.ViewHolder {
//        ImageView imageView;
//
//        public Holder(View itemview){
//            super(itemview);
//
//            imageView= itemview.findViewById(R.id.home_property_imageView);
//        }
//    }
//}
