package com.example.finalexam.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    private TextInputLayout et_name,et_email,et_cnic,et_mobile,et_address,et_pass;
    private String name,email,cnic,mobile,address,pass,img="";

    private Button btnReg,btnImage;
    private LottieAnimationView lot_amin;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        connectViews();
    }

    private void connectViews()
    {
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_cnic=findViewById(R.id.et_cnic);
        et_mobile=findViewById(R.id.et_mobile);
        et_address=findViewById(R.id.et_address);
        et_pass=findViewById(R.id.et_pass);
        btnImage=findViewById(R.id.btnImage);
        btnReg=findViewById(R.id.btnReg);
        lot_amin=findViewById(R.id.lot_amin);

        lot_amin.setVisibility(View.GONE);
        btnClicks();
    }

    private void btnClicks()
    {
        et_name.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setError(null);
            }
        });



        et_email.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setError(null);
            }
        });


        et_cnic.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_cnic.setError(null);
            }
        });


        et_mobile.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_mobile.setError(null);
            }
        });


        et_address.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_address.setError(null);
            }
        });


        et_pass.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_pass.setError(null);
            }
        });

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

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validForm())
                {
                    sendData();
                }
            }
        });


    }



    private boolean validForm()
    {
        name=et_name.getEditText().getText().toString();
        email=et_email.getEditText().getText().toString();
        cnic=et_cnic.getEditText().getText().toString();
        mobile=et_mobile.getEditText().getText().toString();
        address=et_address.getEditText().getText().toString();
        pass=et_pass.getEditText().getText().toString();

        if (name.equals(""))
        {
            et_name.setError("Required!");
            return false;
        }
        else if(email.equals(""))
        {
            et_email.setError("Required!");
            return false;
        }

        else if(cnic.equals(""))
        {
            et_cnic.setError("Required!");
            return false;
        }
        else if(mobile.equals(""))
        {
            et_mobile.setError("Required!");
            return false;
        }
        else if(address.equals(""))
        {
            et_address.setError("Required!");
            return false;
        }
        else if(pass.equals(""))
        {
            et_pass.setError("Required!");
            return false;
        }
        else if(img1.equals(""))
        {
            Toast.makeText(this, "Select an Image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return  true;
        }



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



    private void sendData()
    {
        lot_amin.setVisibility(View.VISIBLE);
        btnReg.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, linkToLocal.Register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        lot_amin.setVisibility(View.GONE);
                        btnReg.setVisibility(View.VISIBLE);
                        Toast.makeText(RegisterUser.this, ""+response, Toast.LENGTH_SHORT).show();
                        if (!(response.equals("Error!")))
                        {
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lot_amin.setVisibility(View.GONE);
                btnReg.setVisibility(View.VISIBLE);
                Toast.makeText(RegisterUser.this, "Connection Error!", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("name",name);
                map.put("email",email);
                map.put("cnic",cnic);
                map.put("mobile",mobile);
                map.put("address",address);
                map.put("pass",pass);
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
        RequestQueue requestQueue= Volley.newRequestQueue(RegisterUser.this);
        requestQueue.add(stringRequest);

    }





}