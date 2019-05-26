package com.tollbooth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class MyWallet extends BaseNavigation {
    String Status = "", Message = "", Value = "";
    GeneralValues gv;
    SharedPreferences prefLogin;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    CardView mywalletcardview,addmoneycardview;
    MaterialEditText edBalance,edinput;
    Button btnAdd,btnSubmit,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_my_wallet, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(MyWallet.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);

        mywalletcardview=findViewById(R.id.mywalletcardview);
        addmoneycardview=findViewById(R.id.addmoneycardview);
        edBalance=findViewById(R.id.edBalance);
        edBalance.setEnabled(false);
        edBalance.setTextColor(getResources().getColor(R.color.colorBlack));
        edinput=findViewById(R.id.edinput);
        btnAdd=findViewById(R.id.btnAdd);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnCancel=findViewById(R.id.btnCancel);

        mywalletcardview.setVisibility(View.VISIBLE);
        addmoneycardview.setVisibility(View.GONE);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mywalletcardview.setVisibility(View.GONE);
                addmoneycardview.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mywalletcardview.setVisibility(View.VISIBLE);
                addmoneycardview.setVisibility(View.GONE);
                edinput.setText("");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation.isValidate(edinput,"enter amount")) {
                    if (gv.isNetworkConnected(getApplicationContext())) {
                        addBalance();
                    } else {
                        Validation.Message(getApplicationContext(), "Start Internet");
                    }
                }
            }
        });
        if (gv.isNetworkConnected(getApplicationContext())) {
            getBalance();
        } else {
            Validation.Message(getApplicationContext(),"Start Internet");
        }
    }

    private void getBalance() {
        pDialog = new ProgressDialog(MyWallet.this);
        pDialog.setMessage("Loading Wallet");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = gv.IPAddress + "UserPayment.ashx";
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (!response.toString().equals("")) {
                        JSONArray jArray = new JSONArray(response);
                        JSONObject jsonObj = (JSONObject) jArray.get(0);
                        Status = jsonObj.getString("Status");
                        Message = jsonObj.getString("Message");
                        if (Status.equals("true")) {
                            Value = jsonObj.getString("Value");
                            edBalance.setText(Value);
                        } else {
                            Validation.Message(MyWallet.this, Message);
                        }
                    } else {
                        Validation.Message(MyWallet.this, "Response " + response);
                    }
                } catch (Exception ex) {
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
                Log.i("", "Error :" + error.toString());
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Postpara = new HashMap<>();
                Postpara.put("opflag", "view" );
                Postpara.put("regid", prefLogin.getString("regId","0").toString() );
                return Postpara;
            }
        };

        mStringRequest.setShouldCache(false);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(gv.VolleyTimeOut, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);
    }

    private void addBalance() {
        pDialog = new ProgressDialog(MyWallet.this);
        pDialog.setMessage("Loading Wallet");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = gv.IPAddress + "UserPayment.ashx";
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (!response.toString().equals("")) {
                        JSONArray jArray = new JSONArray(response);
                        JSONObject jsonObj = (JSONObject) jArray.get(0);
                        Status = jsonObj.getString("Status");
                        Message = jsonObj.getString("Message");
                        if (Status.equals("true")) {
                            mywalletcardview.setVisibility(View.VISIBLE);
                            addmoneycardview.setVisibility(View.GONE);
                            edinput.setText("");
                            Validation.Message(MyWallet.this, Message);
                        } else {
                            Validation.Message(MyWallet.this, Message);
                        }
                    } else {
                        Validation.Message(MyWallet.this, "Response " + response);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                } finally {
                    mRequestQueue.getCache().clear();
                    if (pDialog.isShowing())
                        pDialog.dismiss();

                    getBalance();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("", "Error :" + error.toString());
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Postpara = new HashMap<>();
                Postpara.put("opflag", "pay" );
                Postpara.put("regid", prefLogin.getString("regId","0").toString());
                Postpara.put("paidamount", edinput.getText().toString() );
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
        startAnimatedActivity(new Intent(getApplicationContext(), Dashboard.class));
    }
}
