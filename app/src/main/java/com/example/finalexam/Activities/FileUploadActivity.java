package com.example.finalexam.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
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
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUploadActivity extends AppCompatActivity {

    private LottieAnimationView lot_amin;
    private MaterialSpinner et_cond,et_city,et_bikeComp,et_bike_type;
    private TextInputLayout et_model,et_bike_number,et_price,et_mobile,et_des;

    private String  cond,city,bikeComp,bike_type;
    private String model,bike_number,price,mobile,des;

    private static final int REQUEST_EXTERNAL_STORAGE =5 ;
    private static final int REQUEST_EXTERNAL_STORAGE1 =6 ;

    private String img1="",img2="";
    private String displayName;
    private LinkToLocal linkToLocal=new LinkToLocal();

    private static final int permission_code = 1001;
    private ContentValues values;
    private static  final int REQUEST_LOCATION=1;

    private Uri imageUri;
    private Bitmap thumbnail;
    private List<Bitmap> bitmaps = new ArrayList<>();

    private List<Bitmap> bitmaps1 = new ArrayList<>();


    private ArrayList<String> bikeCondId,bikecondName;
    private ArrayList<String> cityId,cityName;
    private ArrayList<String> bikeCompId,bikeCompName;
    private ArrayList<String> bikeTypeId,bikeTypeName;


    private Button btnImage,btnSub;
    private SharedPreferences sharedPreferences;
    private String oID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_file_upload);
        connectViews();
    }

    private void connectViews()
    {
        lot_amin=findViewById(R.id.lot_amin);
        et_cond=findViewById(R.id.et_cond);
        et_city=findViewById(R.id.et_city);
        et_bikeComp=findViewById(R.id.et_bikeComp);
        et_bike_type=findViewById(R.id.et_bike_type);
        et_model=findViewById(R.id.et_model);
        et_bike_number=findViewById(R.id.et_bike_number);
        et_price=findViewById(R.id.et_price);
        et_mobile=findViewById(R.id.et_mobile);
        et_des=findViewById(R.id.et_des);
        btnImage=findViewById(R.id.btnImage);
        btnSub=findViewById(R.id.btnSub);


        sharedPreferences = getSharedPreferences("exam", Context.MODE_PRIVATE);
        oID=sharedPreferences.getString("id","");

        lot_amin.setVisibility(View.GONE);
        btnClicks();



    }

    private void btnClicks()
    {
        bikeCondId=new ArrayList<String>();
        bikecondName=new ArrayList<String>();
        cityId=new ArrayList<String>();
        cityName=new ArrayList<String>();
        bikeCompId=new ArrayList<String>();
        bikeCompName=new ArrayList<String>();
        bikeTypeId=new ArrayList<String>();
        bikeTypeName=new ArrayList<String>();

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

                            ArrayAdapter adapter = new ArrayAdapter(FileUploadActivity.this,android.R.layout.simple_spinner_dropdown_item, bikecondName);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_cond.setAdapter(adapter);


                            ArrayAdapter adapter1 = new ArrayAdapter(FileUploadActivity.this,android.R.layout.simple_spinner_dropdown_item, cityName);

                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_city.setAdapter(adapter1);


                            ArrayAdapter adapter2 = new ArrayAdapter(FileUploadActivity.this,android.R.layout.simple_spinner_dropdown_item, bikeCompName);

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            et_bikeComp.setAdapter(adapter2);

                            ArrayAdapter adapter3 = new ArrayAdapter(FileUploadActivity.this,android.R.layout.simple_spinner_dropdown_item, bikeTypeName);
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
        RequestQueue requestQueue= Volley.newRequestQueue(FileUploadActivity.this);
        requestQueue.add(stringRequest);


        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, permission_code);
                    } else {
                        launchGalleryIntent();
                    }

                } else {
                    launchGalleryIntent();
                }
            }

        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm())
                {
                    sendData();
                }

            }
        });




    }


    public void launchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    launchGalleryIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }




    // Get Extension
    public String GetFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

        // Return file Extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == RESULT_OK) {

            //final ImageView imageView = findViewById(R.id.displayPic);
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                //multiple images selecetd
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();

                    //user_img.setImageURI(imageUri);
                    Log.d("URI", imageUri.toString());
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmaps.add(bitmap);
                        img1="Attachment Added";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //single image selected
                Uri imageUri = data.getData();
                // user_img.setImageURI(imageUri);
                Log.d("URI", imageUri.toString());
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                    img1="Attachment Added";

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }


        }




    }


    private boolean validForm()
    {

        cond=et_cond.getText().toString();
        city=et_city.getText().toString();
        bikeComp=et_bikeComp.getText().toString();
        bike_type=et_bike_type.getText().toString();
         model=et_model.getEditText().getText().toString();
         bike_number=et_bike_number.getEditText().getText().toString();
         price=et_price.getEditText().getText().toString();
         mobile=et_mobile.getEditText().getText().toString();
         des=et_des.getEditText().getText().toString();

         if (et_cond.getSelectedIndex()==0)
         {
             Toast.makeText(this, "Bike Condition Required!", Toast.LENGTH_SHORT).show();
             return false;

         }
         else if(et_city.getSelectedIndex()==0)
         {
             Toast.makeText(this, "City Required!", Toast.LENGTH_SHORT).show();
             return false;
         }

         else if(et_bikeComp.getSelectedIndex()==0)
         {
             Toast.makeText(this, "Company Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(et_bike_type.getSelectedIndex()==0)
         {
             Toast.makeText(this, "Bike Type Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(model.equals(""))
         {
             Toast.makeText(this, "Model Required!", Toast.LENGTH_SHORT).show();
             return false;
         }

         else if(bike_number.equals(""))
         {
             Toast.makeText(this, "Bike Number Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(price.equals(""))
         {
             Toast.makeText(this, "Price Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(mobile.equals(""))
         {
             Toast.makeText(this, "Mobile Number Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(des.equals(""))
         {
             Toast.makeText(this, "Description Required!", Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(img1.equals(""))
         {
             Toast.makeText(this, "Image Required!", Toast.LENGTH_SHORT).show();
             return false;
         }

         else
         {


             return  true;
         }


    }

    private void sendData()
    {

        lot_amin.setVisibility(View.VISIBLE);
        btnSub.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, linkToLocal.postAd,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lot_amin.setVisibility(View.GONE);
                        btnSub.setVisibility(View.VISIBLE);
                        Toast.makeText(FileUploadActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if (!(response.equals("Error!")))
                        {
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lot_amin.setVisibility(View.GONE);
                btnSub.setVisibility(View.VISIBLE);
                Toast.makeText(FileUploadActivity.this, "Connection Error!", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("cond",cond);
                map.put("city",city);
                map.put("bikeComp",bikeComp);
                map.put("bike_type",bike_type);
                map.put("model",model);
                map.put("bike_number",bike_number);
                map.put("price",price);
                map.put("mobile",mobile);
                map.put("des",des);
                map.put("oID",oID);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final String[] imagesString = new String[0];
                String imageString;
                int num = 0;
                for (Bitmap b:bitmaps){
                    b.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    map.put("ImgFile",imageString);
                    num += 1;
                }



                return map;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS));
        RequestQueue requestQueue= Volley.newRequestQueue(FileUploadActivity.this);
        requestQueue.add(stringRequest);

    }


}