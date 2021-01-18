package com.example.finalexam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalexam.R;
import com.example.finalexam.ServerLink.LinkToLocal;
import com.squareup.picasso.Picasso;

public class BikeDetailsActivity extends AppCompatActivity {

    String img,name,price,model,date,mobile,des;
    private ImageView img1;
    private TextView name1,price1,model1,date1,mobile1,des1;
    private LinkToLocal linkToLocal=new LinkToLocal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bike_details);

        img=getIntent().getStringExtra("img");
        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        model=getIntent().getStringExtra("model");
        date=getIntent().getStringExtra("date");
        mobile=getIntent().getStringExtra("mobile");
        des=getIntent().getStringExtra("des");

        img1=findViewById(R.id.img);
        name1=findViewById(R.id.name);
        price1=findViewById(R.id.price);
        model1=findViewById(R.id.model);
        date1=findViewById(R.id.date);
        mobile1=findViewById(R.id.mobile);
        des1=findViewById(R.id.des);



        String url = "";

        url = linkToLocal.url  + img;


        Picasso.get()
                .load(url)
                .into(img1);

        name1.setText("Name: "+name);
        price1.setText("Price: "+price);
        model1.setText("Model: "+model);
        date1.setText("Date: "+date);
        mobile1.setText("Mobile: "+mobile);
        des1.setText("Description: "+des);








    }


}