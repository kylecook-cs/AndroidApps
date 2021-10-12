package edu.sjsu.android.raidersdirectory;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.MyViewHolder>
{
    //new variables to hold our values which we will pass inside main activity
    String d1[], d2[], d3[];
    int images[];
    Context c;
    Dialog myDialogLastAnimal;

    public AdapterActivity(Context ct, String s1[], String s2[], String s3[], int img[]){
        c = ct;
        d1 = s1;
        d2 = s2;
        d3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.activity_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //Dynamically add values into recyclerview
        holder.title.setText(d1[position]);
        holder.position.setText(d2[position]);
        holder.myImage.setImageResource(images[position]);
        //Set onclick listener for the main layout
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, NavigateActivity.class);
                //Pass in 3 values, title, description, image
                intent.putExtra("data1", d1[position]);
                intent.putExtra("data2", d2[position]);
                intent.putExtra("data3", d3[position]);
                intent.putExtra("myImage", images[position]);
                c.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return d1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView title, position, details;
        ImageView myImage;
        ConstraintLayout mainLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Find ids
            title = itemView.findViewById(R.id.title_player);
            position = itemView.findViewById(R.id.description_player);
            details = itemView.findViewById(R.id.description_navigation);
            myImage = itemView.findViewById(R.id.icon);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
