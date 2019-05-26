package com.tollbooth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class Registration extends AppCompatActivity {
    MaterialEditText edregName, edMH, edTen, edBB, edNo,
            edMobileNo, edEmailId, edPassword, edPasswordR, edRFIDcode;
    Button btnRegister;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    String Status="", Message="", Value="" ;
    GeneralValues gv;
    String vehicleNo="";
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(Registration.this);

        edregName=findViewById(R.id.edregName);
        edMH=findViewById(R.id.edMH);
        edTen=findViewById(R.id.edTen);
        edBB=findViewById(R.id.edBB);
        edNo=findViewById(R.id.edNo);
        edMobileNo=findViewById(R.id.edMobileNo);
        edEmailId=findViewById(R.id.edEmailId);
        edPassword=findViewById(R.id.edPassword);
        edPasswordR=findViewById(R.id.edPasswordR);
        edRFIDcode=findViewById(R.id.edRFIDcode);
        btnRegister=findViewById(R.id.btnRegister);
        spinner = findViewById(R.id.spVehicleType);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation.isValidate(edregName,"enter name") &&
                        Validation.isValidate(edMH,"*") &&
                        Validation.isValidate(edTen,"*") &&
                        Validation.isValidate(edBB,"*") &&
                        Validation.isValidate(edNo,"*") &&
                        Validation.isValidate(edRFIDcode,"enter RFID code") &&
                        Validation.isValidContact(edMobileNo,"enter mobile") &&
                        Validation.isValidateEmailAddress(edEmailId,"enter email id") &&
                        Validation.isValidate(edPassword,"enter password") &&
                        Validation.isPassword(edPassword,"password length 6 to 12 characters") &&
                        Validation.isValidate(edPasswordR,"retype password")){
                    if(edPassword.getText().toString().equals(edPasswordR.getText().toString())){
                        vehicleNo=edMH.getText().toString().toUpperCase() + " " + edTen.getText().toString() + " " +
                                edBB.getText().toString().toUpperCase() + " " + edNo.getText().toString();
                        if(!spinner.getSelectedItem().toString().equals("Vehicle Type")){
                            if (gv.isNetworkConnected(getApplicationContext())) {
                                RegisterUser();
                            } else {
                                Validation.Message(getApplicationContext(),"Start Internet");
                            }
                        }else {
                            Validation.Message(Registration.this,"Select Vehicle Type");
                        }

                    }else {
                        Validation.Message(Registration.this,"password not match");
                    }
                }
            }
        });

        String[] plants = new String[]{
                "Vehicle Type",
                "Car",
                "Tempo",
                "Truck",
                "Bus"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plants
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void RegisterUser() {
        pDialog = new ProgressDialog(Registration.this);
        pDialog.setMessage("Registration In Progress..");
        pDialog.setCancelable(false);
        pDialog.show();
        //RequestQueue initialized

        String url=gv.IPAddress +"Registration.ashx";

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try { Log.e("Voley----",response);
                    if (!response.toString().equals("")) {
                        response=response.replace("send","");
                        JSONArray jArray = new JSONArray(response);
                        JSONObject jsonObj = (JSONObject) jArray.get(0);
                        Status = jsonObj.getString("Status");
                        Message = jsonObj.getString("Message");
                        if (Status.equals("true")) {
                            Value = jsonObj.getString("Value");
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("regId", Message);
                            editor.putString("regName", edregName.getText().toString());
                            editor.putString("emailId", edEmailId.getText().toString());
                            editor.putString("mobileNo", edMobileNo.getText().toString());
                            editor.putString("vehicleNo", vehicleNo);
                            editor.putString("secreteCode", Value);
                            editor.putString("vtype", spinner.getSelectedItem().toString());
                            editor.putString("upass", edPassword.getText().toString());
                            editor.putString("RFIDcode", edRFIDcode.getText().toString());
                            editor.commit();

                            finish();
                            startActivity(new Intent(Registration.this, Login.class));

                            Validation.Message(Registration.this, "Registeration Successful");
                        }else {
                            Validation.Message(Registration.this, Message);
                        }
                    }
                    else{
                        Validation.Message(Registration.this, "Response " + response);
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
                Postpara.put("vehicleNo", vehicleNo);
                Postpara.put("vtype", spinner.getSelectedItem().toString());
                Postpara.put("upass", edPassword.getText().toString());
                Postpara.put("RFIDcode", edRFIDcode.getText().toString());
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
        startActivity(new Intent(Registration.this,Login.class));
    }
}
