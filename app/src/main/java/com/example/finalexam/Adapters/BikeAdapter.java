package com.example.finalexam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.Activities.BikeDetailsActivity;
import com.example.finalexam.Models.BikeModel;
import com.example.finalexam.R;
import com.example.finalexam.ServerLink.LinkToLocal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.ViewHolder> {


    private LinkToLocal linkToLocal=new LinkToLocal();
    private BikeModel bikeModel;


    Context context;

    public BikeAdapter(BikeModel bikeModel, Context context) {
        this.bikeModel = bikeModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bike,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        //holder.textView.setText(customerModels.get(position).getName());
        holder.name.setText(bikeModel.getBikeTypeID().get(position));
        holder.price.setText(bikeModel.getPrice().get(position));
        holder.loction.setText(bikeModel.getCityID().get(position));

        String url = "";

        url = linkToLocal.url  + bikeModel.getImgLink().get(position);


        Picasso.get()
                .load(url)
                .into(holder.img);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, BikeDetailsActivity.class);
                intent.putExtra("img",bikeModel.getImgLink().get(position));
                intent.putExtra("name",bikeModel.getBikeTypeID().get(position));
                    intent.putExtra("price",bikeModel.getPrice().get(position));
                    intent.putExtra("model",bikeModel.getModel().get(position));
                intent.putExtra("date",bikeModel.getDate().get(position));
                intent.putExtra("mobile",bikeModel.getMobile().get(position));
                intent.putExtra("des",bikeModel.getDes().get(position));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return bikeModel.getId().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,loction;

        ImageView img;
        Button btnDetail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            loction=itemView.findViewById(R.id.loction);
            btnDetail=itemView.findViewById(R.id.btnDetail);

        }
    }
}

