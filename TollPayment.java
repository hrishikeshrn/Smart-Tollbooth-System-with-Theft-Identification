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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tollbooth.CommonClasses.DateFormation;
import com.tollbooth.CommonClasses.GeneralValues;
import com.tollbooth.CommonClasses.Validation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TollPayment extends BaseNavigation {
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    String Status="", Message="", Value="" ;
    GeneralValues gv;
    String RouteId="", TollId="", Vtype="";
    SharedPreferences prefLogin;
    TextView tvRouteName,tvTollName,tvAmount,tvDate, tvTimeH, tvTime, tvHead, tvSecreteCode;
    EditText edSecreteCode;
    Button btnPayment;
    Double PaymentAmount=0.0;
    LinearLayout linpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_toll_payment, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(TollPayment.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);
        tvRouteName=findViewById(R.id.tvRouteName);
        tvTollName=findViewById(R.id.tvTollName);
        tvAmount=findViewById(R.id.tvAmount);
        tvDate=findViewById(R.id.tvDate);
        tvTime=findViewById(R.id.tvTime);
        tvTime.setVisibility(View.GONE);
        tvTimeH=findViewById(R.id.tvTimeH);
        tvTimeH.setVisibility(View.GONE);
        tvHead=findViewById(R.id.tvHead);
        tvSecreteCode=findViewById(R.id.tvSecreteCode);
        edSecreteCode=findViewById(R.id.edSecreteCode);
        btnPayment=findViewById(R.id.btnPayment);
        linpay=findViewById(R.id.linpay);
        linpay.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RouteId=extras.getString("RouteId");
            TollId=extras.getString("TollId");
            tvRouteName.setText(extras.getString("RouteName"));
            tvTollName.setText(extras.getString("TollName"));
            Vtype=prefLogin.getString("vtype","0");
            tvDate.setText(DateFormation.DisplayDate(""));

            getTollRateByToll();
        }

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tvAmount.getText().toString().equals("") &&
                        !tvAmount.getText().toString().equals("0")) {
                    if(PaymentAmount >= Double.parseDouble(tvAmount.getText().toString())) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(TollPayment.this);
                        adb.setTitle("Payment");
                        adb.setMessage("Are you sure to pay for toll?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String s = prefLogin.getString("secreteCode", "0").toString();
                                String s1 = edSecreteCode.getText().toString();
                                if (s.equals(s1)) {
                                    if (gv.isNetworkConnected(getApplicationContext())) {
                                        Toll_Payment();
                                    } else {
                                        Validation.Message(getApplicationContext(), "Start Internet");
                                    }
                                } else {
                                    Validation.Message(TollPayment.this, "Wrong Secrete Code");
                                }
                            }
                        });
                        adb.show();
                    }else {
                        Validation.Message(TollPayment.this, "Insufficient Balance In Wallet");
                    }
                }else {
                    Validation.Message(TollPayment.this, "Toll Amount is ZERO or BLANK");
                }
            }
        });
    }

    private void getTollRateByToll() {
        pDialog = new ProgressDialog(TollPayment.this);
        pDialog.setMessage("");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = gv.IPAddress + "getTollRateByToll.ashx";
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
                            tvAmount.setText(Value);
                        } else {
                            Validation.Message(TollPayment.this, Message);
                        }
                    } else {
                        Validation.Message(TollPayment.this, "Response " + response);
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
                Postpara.put("TollId", TollId );
                Postpara.put("Vtype", Vtype );
                return Postpara;
            }
        };

        mStringRequest.setShouldCache(false);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(gv.VolleyTimeOut, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);
    }

    private void Toll_Payment() {
        pDialog = new ProgressDialog(TollPayment.this);
        pDialog.setMessage("Payment In Progress..");
        pDialog.setCancelable(false);
        pDialog.show();
        //RequestQueue initialized

        String url=gv.IPAddress +"Toll_Payments.ashx";

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
//                            finish();
//                            startAnimatedActivity(new Intent(TollPayment.this, Dashboard.class));
                            tvHead.setVisibility(View.VISIBLE);
                            tvTime.setVisibility(View.VISIBLE);
                            tvTimeH.setVisibility(View.VISIBLE);
                            linpay.setVisibility(View.VISIBLE);
                            tvHead.setText("Receipt");
                            tvTime.setText(DateFormation.ValueTime());
                            tvSecreteCode.setVisibility(View.GONE);
                            edSecreteCode.setVisibility(View.GONE);
                            btnPayment.setVisibility(View.GONE);
                            Validation.Message(TollPayment.this, Message);
                        }else {
                            Validation.Message(TollPayment.this, Message);
                        }
                    }
                    else{
                        Validation.Message(TollPayment.this, "Response " + response);
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
                Postpara.put("RouteId", RouteId );
                Postpara.put("TollId", TollId );
                Postpara.put("PaymentAmt", tvAmount.getText().toString() );
                return Postpara;
            }
        };

        mStringRequest.setShouldCache(false);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(gv.VolleyTimeOut, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);

    }

    private void getBalance() {
        pDialog = new ProgressDialog(TollPayment.this);
        pDialog.setMessage("Checking Wallet");
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
                            if(!Value.equals("")){
                                if(Double.parseDouble(Value)>0){
                                    btnPayment.setVisibility(View.VISIBLE);
                                    PaymentAmount=Double.parseDouble(Value);
                                }else {
                                    btnPayment.setVisibility(View.GONE);
                                    Validation.Message(TollPayment.this, "Insufficient Balance In Wallet");
                                }
                            }else {
                                btnPayment.setVisibility(View.GONE);
                                Validation.Message(TollPayment.this, "Something went wrong");
                            }
                        } else {
                            btnPayment.setVisibility(View.GONE);
                            Validation.Message(TollPayment.this, Message);
                        }
                    } else {
                        Validation.Message(TollPayment.this, "Response " + response);
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

    @Override
    public void onBackPressed() {
        finish();
        startAnimatedActivity(new Intent(TollPayment.this, Dashboard.class));
    }
}
