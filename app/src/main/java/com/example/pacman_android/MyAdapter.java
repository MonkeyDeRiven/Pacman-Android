package com.example.pacman_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[];
    String data2[];
    String data3[];
    int images[];
    Context context;
   public MyAdapter(Context ct, String s1[], String s2[], String s3[], int img[])
   {
      context = ct;
       data1 = s1;
       images = img;
       data2 = s2;
       data3 = s3;
   }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
       View view = Inflater.inflate(R.layout.recycleviewrow,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.MyImage.setBackgroundResource(images[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
   }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView myText1;
        ImageView MyImage;
        TextView myText2;
        TextView myText3;

       public MyViewHolder(@NonNull View itemView) {


            super(itemView);
            myText1 = itemView.findViewById(R.id.Text);
            MyImage = itemView.findViewById(R.id.Bild);
            myText2 = itemView.findViewById(R.id.Text2);
            myText3 = itemView.findViewById(R.id.Text3);
        }
    }
}
