package com.tollbooth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tollbooth.CommonClasses.GeneralValues;
import com.tollbooth.CommonClasses.Validation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends BaseNavigation {
    MaterialEditText edregName, edMH, edTen, edBB, edNo,
            edMobileNo, edEmailId, edvtype, edRFIDcode;
    Button btnRegister;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    String Status="", Message="", Value="" ;
    GeneralValues gv;
    String vehicleNo="";
    SharedPreferences prefLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_my_profile, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(MyProfile.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);

        edregName=findViewById(R.id.edregName);
        edMH=findViewById(R.id.edMH);
        edTen=findViewById(R.id.edTen);
        edBB=findViewById(R.id.edBB);
        edNo=findViewById(R.id.edNo);
        edMobileNo=findViewById(R.id.edMobileNo);
        edEmailId=findViewById(R.id.edEmailId);
        edvtype=findViewById(R.id.edvtype);
        edRFIDcode=findViewById(R.id.edRFIDcode);
        btnRegister=findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation.isValidate(edregName,"enter name") &&
                        Validation.isValidContact(edMobileNo,"enter mobile") &&
                        Validation.isValidateEmailAddress(edEmailId,"enter email id")) {
                    if (gv.isNetworkConnected(getApplicationContext())) {
                        UpdateProfile();
                    } else {
                        Validation.Message(getApplicationContext(),"Start Internet");
                    }
                }
            }
        });

        // Display Profile
        edregName.setText(prefLogin.getString("regName","0"));
        edEmailId.setText(prefLogin.getString("emailId","0"));
        edMobileNo.setText(prefLogin.getString("mobileNo","0"));
        edvtype.setText(prefLogin.getString("vtype","0"));
        edRFIDcode.setText(prefLogin.getString("RFIDcode","0"));
        vehicleNo = prefLogin.getString("vehicleNo","0");
        String [] VhNo=vehicleNo.split(" ");
        if(VhNo.length>1){
            edMH.setText(VhNo[0]);
            edTen.setText(VhNo[1]);
            edBB.setText(VhNo[2]);
            edNo.setText(VhNo[3]);
        }

        edMobileNo.setEnabled(false);
        edMobileNo.setTextColor(getResources().getColor(R.color.colorBlack));
        edvtype.setEnabled(false);
        edvtype.setTextColor(getResources().getColor(R.color.colorBlack));
        edMH.setEnabled(false);
        edMH.setTextColor(getResources().getColor(R.color.colorBlack));
        edTen.setEnabled(false);
        edTen.setTextColor(getResources().getColor(R.color.colorBlack));
        edBB.setEnabled(false);
        edBB.setTextColor(getResources().getColor(R.color.colorBlack));
        edNo.setEnabled(false);
        edNo.setTextColor(getResources().getColor(R.color.colorBlack));
        edRFIDcode.setEnabled(false);
        edRFIDcode.setTextColor(getResources().getColor(R.color.colorBlack));
        //----------------------------------
    }
    private void UpdateProfile() {
        pDialog = new ProgressDialog(MyProfile.this);
        pDialog.setMessage("Profile Update In Progress..");
        pDialog.setCancelable(false);
        pDialog.show();
        //RequestQueue initialized

        String url=gv.IPAddress +"UpdateProfile.ashx";

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try { Log.e("Voley----",response);
                    if (!response.toString().equals("")) {
                        JSONArray jArray = new JSONArray(response);
                        JSONObject jsonObj = (JSONObject) jArray.get(0);
                        Status = jsonObj.getString("Status");
                        Message = jsonObj.getString("Message");
                        if (Status.equals("true")) {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("regName", edregName.getText().toString());
                            editor.putString("emailId", edEmailId.getText().toString());
                            editor.commit();

                            finish();
                            startAnimatedActivity(new Intent(MyProfile.this, Dashboard.class));

                            Validation.Message(MyProfile.this, Message);
                        }else {
                            Validation.Message(MyProfile.this, Message);
                        }
                    }
                    else{
                        Validation.Message(MyProfile.this, "Response " + response);
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                } finally {
                    mRequestQueue.getCache().clear();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("","Error :" + error.toString());
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String, String> Postpara = new HashMap<>();
                Postpara.put("regName", edregName.getText().toString());
                Postpara.put("emailId", edEmailId.getText().toString());
                Postpara.put("mobileNo", edMobileNo.getText().toString() );
                return Postpara;
            }
        };

        mStringRequest.setShouldCache(false);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(gv.VolleyTimeOut, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);

    }
    @Override
    public void onBackPressed() {
        finish();
        startAnimatedActivity(new Intent(MyProfile.this, Dashboard.class));
    }
}
