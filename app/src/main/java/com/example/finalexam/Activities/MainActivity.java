package com.example.finalexam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalexam.R;
import com.example.finalexam.ServerLink.LinkToLocal;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout et_email,et_pass;
    private Button btnLogin,btnReg;
    private LottieAnimationView lot_amin;
    private String email,pass;

    private SharedPreferences sharedPreferences;

    private LinkToLocal linkToLocal=new LinkToLocal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectViews();


    }

    private void connectViews()
    {

        et_email=findViewById(R.id.et_email);
        et_pass=findViewById(R.id.et_pass);
        btnLogin=findViewById(R.id.btnLogin);
        btnReg=findViewById(R.id.btnReg);
        lot_amin=findViewById(R.id.lot_amin);
        lot_amin.setVisibility(View.GONE);


        sharedPreferences = getSharedPreferences("exam", Context.MODE_PRIVATE);
        bnClick();




    }

    private void bnClick()
    {
        et_pass.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_pass.setError(null);

            }
        });

        et_email.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setError(null);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm())
                {
                    submitData();
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterUser.class));
            }
        });
    }

    private boolean validForm()
    {
        email=et_email.getEditText().getText().toString();
        pass=et_pass.getEditText().getText().toString();

        if (email.equals(""))
        {
            et_email.setError("Required!");
            return false;

        }
        else if(pass.equals(""))
        {
            et_pass.setError("Required!");
            return false;
        }
        else
        {
            return true;
        }

    }


    private void submitData()
    {
        lot_amin.setVisibility(View.VISIBLE);
        btnReg.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, linkToLocal.login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lot_amin.setVisibility(View.GONE);
                        btnReg.setVisibility(View.VISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            if (jsonArray.length()>0)
                            {
                                JSONObject object=jsonArray.getJSONObject(0);
                                String id=object.getString("id");
                                String name=object.getString("name");
                                String cnic=object.getString("cnic");
                                String email=object.getString("email");
                                String mobile=object.getString("mobile");
                                String address=object.getString("address");
                                String password=object.getString("password");
                                String image=object.getString("image");




                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id",id);
                                editor.putString("name",name);
                                editor.putString("cnic",cnic);
                                editor.putString("email",email);
                                editor.putString("mobile",mobile);
                                editor.putString("address",address);
                                editor.putString("password",password);
                                editor.putString("image",image);


                                editor.apply();
                                editor.commit();

                                startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                                finish();


                            }
                            else
                            {
                                et_email.setError("Invalid Email or Password!");
                                et_pass.setError("Invalid Email or Password!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            lot_amin.setVisibility(View.GONE);
                            btnReg.setVisibility(View.VISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Connection Error!", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                lot_amin.setVisibility(View.GONE);
                btnReg.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Connection Error!", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                map.put("email",email);
                map.put("pass",pass);

                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);


    }
}