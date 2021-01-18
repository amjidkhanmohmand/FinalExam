package com.example.finalexam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalexam.Adapters.BikeAdapter;
import com.example.finalexam.Models.BikeModel;
import com.example.finalexam.R;
import com.example.finalexam.ServerLink.LinkToLocal;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private ImageView head_img;
    private TextView head_name,head_user;
    private SharedPreferences sharedPreferences;
    private String user_img;
    private String user_name;
    private String user_email;
    private LinkToLocal linkToLocal=new LinkToLocal();
    private ImageButton btnmen;

    private ArrayList<String> bikeCondId,bikecondName;
    private ArrayList<String> cityId,cityName;
    private ArrayList<String> bikeCompId,bikeCompName;
    private ArrayList<String> bikeTypeId,bikeTypeName;

    private RecyclerView recycler;

    private ArrayList<String> id,cityID,companyID,bikeTypeID,model,regcityID,
            bikeNumber,price,conditions,mobile,date,imgLink,des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);

        connectViews();



    }
    private void connectViews()
    {
        btnmen=findViewById(R.id.btnmen);
        recycler=findViewById(R.id.recycler);
        sharedPreferences = getSharedPreferences("exam", Context.MODE_PRIVATE);
        user_img=sharedPreferences.getString("image","");
        user_name=sharedPreferences.getString("name","");
        user_email=sharedPreferences.getString("name","");

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setItemIconTintList(null);

        head_img=nv.getHeaderView(0).findViewById(R.id.head_img);
        head_name=nv.getHeaderView(0).findViewById(R.id.head_name);
        head_user=nv.getHeaderView(0).findViewById(R.id.head_email);

        head_name.setText(user_name);
        head_user.setText(user_email);


        String url = "";

        url = linkToLocal.url  + user_img;


        Picasso.get()
                .load(url)
                .into(head_img);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.logout) {



                    dl.closeDrawers();
                }
                else if (id==R.id.item1)
                {
                    startActivity(new Intent(DashboardActivity.this,FileUploadActivity.class));
                    dl.closeDrawers();

                }
                else
                {

                    dl.closeDrawers();
                }

                // ReadJson(item.getTitle().toString());



                return true;
            }
        });

        SetData();
    }


    public void openMenu(View view) {
        dl.openDrawer(nv);
    }

    public void menuCreate(View view) {
        PopupMenu popup = new PopupMenu(DashboardActivity.this, btnmen);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menux, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(DashboardActivity.this, "Signing Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                finish();
                return true;
            }
        });
        popup.show(); //showing popup menu
    }


    private void SetData()
    {
        id=new ArrayList<String>();
        cityID=new ArrayList<String>();
        companyID=new ArrayList<String>();
        bikeTypeID=new ArrayList<String>();
        model=new ArrayList<String>();
        regcityID=new ArrayList<String>();
                bikeNumber=new ArrayList<String>();
                price=new ArrayList<String>();
                conditions=new ArrayList<String>();
                mobile=new ArrayList<String>();
                date=new ArrayList<String>();
                imgLink=new ArrayList<String>();
                des=new ArrayList<String>();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, linkToLocal.getAll,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray=new JSONArray(response);
                                    for (int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject object=jsonArray.getJSONObject(i);
                                        id.add(object.getString("id").toString());
                                        cityID.add(object.getString("cityID").toString());
                                        companyID.add(object.getString("companyID").toString());
                                        bikeTypeID.add(object.getString("bikeTypeID").toString());
                                        model.add(object.getString("model").toString());
                                        regcityID.add(object.getString("regcityID").toString());
                                        bikeNumber.add(object.getString("bikeNumber").toString());
                                        price.add(object.getString("price").toString());
                                        conditions.add(object.getString("conditions").toString());
                                        mobile.add(object.getString("mobile").toString());
                                        des.add(object.getString("des").toString());
                                        imgLink.add(object.getString("imgLink").toString());
                                        date.add(object.getString("date").toString());
                                    }
                                    if (jsonArray.length()>0)
                                    {
                                        BikeModel bikeModel=new BikeModel(id,cityID,companyID,bikeTypeID,model,regcityID,bikeNumber,price,conditions,mobile,date,imgLink,des);

                                        BikeAdapter adapter=new BikeAdapter(bikeModel,DashboardActivity.this);
                                        recycler.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
                                        // recycler_jobs.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                                        recycler.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DashboardActivity.this, "Connection Error!", Toast.LENGTH_SHORT).show();

                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        RequestQueue requestQueue= Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);

    }

    public void searchBike(View view) {


        final Dialog dialog=new Dialog(DashboardActivity.this);//,android.R.style.Theme_Light);

        //  dialog.setTitle("ECG Leads Placement");
        dialog.setTitle( Html.fromHtml("<font color='#448AFF'>ECG Leads Placement</font>"));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_search);


        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);




      bikeCondId=new ArrayList<String>();
      bikecondName=new ArrayList<String>();
        cityId=new ArrayList<String>();
        cityName=new ArrayList<String>();
      bikeCompId=new ArrayList<String>();
      bikeCompName=new ArrayList<String>();
         bikeTypeId=new ArrayList<String>();
         bikeTypeName=new ArrayList<String>();

        //final TextInputLayout et_amount=dialog.findViewById(R.id.et_amount);
        final MaterialSpinner et_cond=dialog.findViewById(R.id.et_cond);
        final MaterialSpinner et_city=dialog.findViewById(R.id.et_city);
        final MaterialSpinner et_bikeComp=dialog.findViewById(R.id.et_bikeComp);
        final MaterialSpinner et_bike_type=dialog.findViewById(R.id.et_bike_type);
        final MaterialSpinner et_price_range=dialog.findViewById(R.id.et_price_range);
        final TextInputLayout et_model=dialog.findViewById(R.id.et_model);
        final Button btnClose=dialog.findViewById(R.id.btnClose);
        final Button btnSearch=dialog.findViewById(R.id.btnSearch);

        StringRequest stringRequest =new StringRequest(Request.Method.POST, linkToLocal.getBikeTable,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObj=new JSONObject(response);
                            JSONArray bikeCond=mainObj.getJSONArray("bikeCond");
                            JSONArray city_details=mainObj.getJSONArray("city_details");
                            JSONArray company_details=mainObj.getJSONArray("company_details");
                            JSONArray type_details=mainObj.getJSONArray("type_details");

                            for (int i=0;i<bikeCond.length();i++)
                            {
                                JSONObject object1=bikeCond.getJSONObject(i);

                                if (i==0)
                                {
                                    bikeCondId.add("0");
                                    bikecondName.add("Select Bike Condition");
                                }

                                bikeCondId.add(object1.getString("id").toString());
                                bikecondName.add(object1.getString("name").toString());


                            }

                            for (int i=0;i<city_details.length();i++)
                            {
                                JSONObject object2=city_details.getJSONObject(i);
                                if (i==0)
                                {
                                    cityId.add("0");
                                    cityName.add("Select City");
                                }
                                cityId.add(object2.getString("id").toString());
                                cityName.add(object2.getString("name").toString());

                            }

                            for (int i=0;i<company_details.length();i++)
                            {
                                JSONObject object3=company_details.getJSONObject(i);
                                if (i==0)
                                {
                                    bikeCompId.add("0");
                                    bikeCompName.add("Select Company");
                                }
                                bikeCompId.add(object3.getString("id").toString());
                                bikeCompName.add(object3.getString("name").toString());




                            }

                            for (int i=0;i<type_details.length();i++)
                            {
                                JSONObject object4=type_details.getJSONObject(i);
                                if (i==0)
                                {
                                    bikeTypeId.add("0");
                                    bikeTypeName.add("Select Type");
                                }
                                bikeTypeId.add(object4.getString("id").toString());
                                bikeTypeName.add(object4.getString("name").toString());

                            }

                            ArrayAdapter adapter = new ArrayAdapter(DashboardActivity.this,android.R.layout.simple_spinner_dropdown_item, bikecondName);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_cond.setAdapter(adapter);


                            ArrayAdapter adapter1 = new ArrayAdapter(DashboardActivity.this,android.R.layout.simple_spinner_dropdown_item, cityName);

                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_city.setAdapter(adapter1);


                            ArrayAdapter adapter2 = new ArrayAdapter(DashboardActivity.this,android.R.layout.simple_spinner_dropdown_item, bikeCompName);

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_bikeComp.setAdapter(adapter2);

                            ArrayAdapter adapter3 = new ArrayAdapter(DashboardActivity.this,android.R.layout.simple_spinner_dropdown_item, bikeTypeName);
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_bike_type.setAdapter(adapter3);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        RequestQueue requestQueue= Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);









        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        //dialog.getWindow().setBackgroundDrawableResource(R.color.whiteshade);
        dialog.show();


    }
}

