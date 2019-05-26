package com.tollbooth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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

public class ComplainRegister extends BaseNavigation {
    MaterialEditText edregName;
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
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_complain_register, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(ComplainRegister.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);

        edregName=findViewById(R.id.edregName);
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb=new AlertDialog.Builder(ComplainRegister.this);
                adb.setTitle("Complain");
                adb.setMessage("Are you sure to register complain?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String s=prefLogin.getString("vehicleNo","0").toString();
                        String s1=edregName.getText().toString();
                        String s2=prefLogin.getString("regId","0").toString();

                        if(s.toLowerCase().equals(s1.toLowerCase()) && !s2.equals("0")){
                            if (gv.isNetworkConnected(getApplicationContext())) {
                                RegisterComplain();
                            } else {
                                Validation.Message(getApplicationContext(),"Start Internet");
                            }
                        }else {
                            Validation.Message(ComplainRegister.this, "Wrong Vehicle No");
                        }
                    }
                });
                adb.show();
            }
        });
    }
    private void RegisterComplain() {
        pDialog = new ProgressDialog(ComplainRegister.this);
        pDialog.setMessage("Complain Register In Progress..");
        pDialog.setCancelable(false);
        pDialog.show();
        //RequestQueue initialized

        String url=gv.IPAddress +"ComplainRegister.ashx";

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
                            finish();
                            startAnimatedActivity(new Intent(ComplainRegister.this, Dashboard.class));
                            Validation.Message(ComplainRegister.this, Message);
                        }else {
                            Validation.Message(ComplainRegister.this, Message);
                        }
                    }
                    else{
                        Validation.Message(ComplainRegister.this, "Response " + response);
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
                Postpara.put("regId", prefLogin.getString("regId","0").toString());
                Postpara.put("vehicleno", edregName.getText().toString());
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
        startAnimatedActivity(new Intent(ComplainRegister.this, Dashboard.class));
    }
}
