package com.gogrocersm.storemanager.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.franmontiel.localechanger.LocaleChanger;
import com.gogrocersm.storemanager.AppController;
import com.gogrocersm.storemanager.Config.BaseURL;
import com.gogrocersm.storemanager.MainActivity;
import com.gogrocersm.storemanager.R;
import com.gogrocersm.storemanager.util.CustomVolleyJsonRequest;
import com.gogrocersm.storemanager.util.Session_management;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private static String TAG = EditProfile.class.getSimpleName();

    private TextView et_phone, et_name, et_email, et_house ,admin,earn;
    private RelativeLayout btn_update;
    private TextView tv_phone, tv_name, tv_email, tv_house, tv_socity, btn_socity;
    private ImageView iv_profile;
    SharedPreferences myPrefrence;
    private String getsocity = "";
    private String filePath = "";
    private static final int GALLERY_REQUEST_CODE1 = 201;
    private Bitmap bitmap;
    private Uri imageuri;
    String image , user_email,store_name,paid,user_phone,address,store_earning;
    String userid;
    private Session_management sessionManagement;
    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManagement = new Session_management(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_profilee));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        et_phone = (TextView) findViewById(R.id.et_pro_phone);
        et_name = (TextView) findViewById(R.id.et_pro_name);
        tv_phone = (TextView) findViewById(R.id.tv_pro_phone);
        tv_name = (TextView) findViewById(R.id.tv_pro_name);
        tv_email = (TextView) findViewById(R.id.tv_pro_email);
        et_email = (TextView) findViewById(R.id.et_pro_email);
        et_house = (TextView) findViewById(R.id.et_pro_aaddress);
        admin = (TextView) findViewById(R.id.admin);
        earn = (TextView) findViewById(R.id.earn);

//        iv_profile = (ImageView) findViewById(R.id.iv_pro_img);
//        btn_update = (RelativeLayout) findViewById(R.id.btn_pro_edit);
        SharedPreferences prefs = getSharedPreferences("logindata", MODE_PRIVATE);
        userid=prefs.getString("id","");
//        String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
//        String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
//        String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
//        String getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
//        String getpin = sessionManagement.getUserDetails().get(BaseURL.KEY_PINCODE);
//        String gethouse = sessionManagement.getUserDetails().get(BaseURL.KEY_HOUSE);
//        getsocity = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_ID);
//        String getsocity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_NAME);



//        if (!TextUtils.isEmpty(getimage)) {
//            Glide.with(this)
//                    .load(BaseURL.IMG_PROFILE_URL + getimage)
//                    .centerCrop()
//                    .placeholder(R.drawable.icons)
//                    .crossFade()
//                    .into(iv_profile);
//        }
//        if (!TextUtils.isEmpty(getemail)) {
//            et_email.setText(getemail);
//        }
//        btn_update.setOnClickListener(this);
//        iv_profile.setOnClickListener(this);

attemptEditProfile();
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.btn_pro_edit) {
//           attemptEditProfile(et_email.getText().toString(),et_name.getText().toString(),et_phone.getText().toString(), userid);
//           // storeImage(bitmap);
//        } else if (id == R.id.iv_pro_img) {
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            // Start the Intent
//            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE1);
//        }
//    }
    private void attemptEditProfile() {
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("store_id", "1");

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.Update_user, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    String status = response.getString("data");
                    String message = response.getString("message");
                    if (status.contains("1")) {
                        JSONObject obj = response.getJSONObject("data");
                         store_name = obj.getString("store_name");
                         user_email = obj.getString("email");
                         user_phone = obj.getString("phone_number");
                         address = obj.getString("address");
                         paid = obj.getString("paid");
                         store_earning = obj.getString("store_earning");


                        et_name.setText(store_name);
                        et_phone.setText(user_phone);
                        et_email.setText(user_email);
                        et_house.setText(address);
                        admin.setText(paid);
                        earn.setText(store_earning);

//                        Toast.makeText(EditProfile.this, ""+message, Toast.LENGTH_SHORT).show();

//                        Session_management sessionManagement = new Session_management(EditProfile.this);
//                        sessionManagement.createLoginSession(user_id, user_email, user_fullname, user_phone, "", "", "", "", "");
//
//                        Intent i = new Intent(EditProfile.this, MainActivity.class);
//                        startActivity(i);
//                        finish();
//                        btn_update.setEnabled(false);

                    } else {
                        Toast.makeText(EditProfile.this, "" + message, Toast.LENGTH_SHORT).show();
//                        btn_update.setEnabled(true);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(EditProfile.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}


