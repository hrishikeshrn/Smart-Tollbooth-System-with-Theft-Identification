package com.tollbooth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tollbooth.CommonClasses.GeneralValues;
import com.tollbooth.CommonClasses.Validation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextInputEditText etUserName, etUserPassword;
    Button btnLogin;
    TextView tvsingup;
    SharedPreferences prefLogin;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    String Status="", Message="", Value="" ;
    GeneralValues gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("");
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(Login.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);
        tvsingup=findViewById(R.id.tvsingup);
        tvsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
        etUserName=findViewById(R.id.etUserName);
        etUserPassword=findViewById(R.id.etUserPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation.isValidate(etUserName,"enter mobile no") &&
                        Validation.isValidate(etUserPassword,"enter password")) {
                    if (gv.isNetworkConnected(getApplicationContext())) {
                        getLogin();
                    } else {
                        Validation.Message(getApplicationContext(),"Start Internet");
                    }
                }
            }
        });
    }

    private void getLogin(){
        try {

            String username="", upass="";
            username=prefLogin.getString("mobileNo","0");
            upass=prefLogin.getString("upass","0");
            if(username.equals("0") || upass.equals("0") ||
                    username.equals("") || upass.equals("")){
                getLogindtl();
            }else if(username.equals(etUserName.getText().toString()) &&
                    upass.equals(etUserPassword.getText().toString())){
                finish();
                startActivity(new Intent(Login.this, Dashboard.class));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {

        }
    }
    private void getLogindtl() {
        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Login In Progress..");
        pDialog.setCancelable(false);
        pDialog.show();

        String url=gv.IPAddress +"UserLogin.ashx";

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
                            Value = jsonObj.getString("Value");
                            JSONArray jsonArray = new JSONArray(Value);
                            if(jsonArray.length()>0) {
                                JSONObject jObj = (JSONObject) jsonArray.get(0);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("regId", jObj.getString("regId"));
                                editor.putString("regName", jObj.getString("regName"));
                                editor.putString("emailId", jObj.getString("emailId"));
                                editor.putString("mobileNo", jObj.getString("mobileNo"));
                                editor.putString("vehicleNo", jObj.getString("vehicleNo"));
                                editor.putString("secreteCode", jObj.getString("secreteCode"));
                                editor.putString("vtype", jObj.getString("vtype"));
                                editor.putString("upass", jObj.getString("upass"));
                                editor.putString("RFIDcode", jObj.getString("RFIDcode"));
                                editor.commit();

                                finish();
                                startActivity(new Intent(Login.this, Dashboard.class));
                                Validation.Message(Login.this, Message);
                            }

                        }else {
                            Validation.Message(Login.this, Message);
                        }
                    }
                    else{
                        Validation.Message(Login.this, "Response " + response);
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
                Postpara.put("mobileNo", etUserName.getText().toString() );
                Postpara.put("upass", etUserPassword.getText().toString() );
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
        finishAffinity();
    }
}
