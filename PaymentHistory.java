package com.tollbooth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class PaymentHistory extends BaseNavigation {
    String Status = "", Message = "", Value = "";
    GeneralValues gv;
    SharedPreferences prefLogin;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    TableLayout table_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_payment_history);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_payment_history, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(PaymentHistory.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);
        table_main=findViewById(R.id.table_main);
        getPaymentHistory();
    }

    private void getPaymentHistory() {
        pDialog = new ProgressDialog(PaymentHistory.this);
        pDialog.setMessage("Loading Wallet");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = gv.IPAddress + "UserPayment.ashx";
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    table_main.removeAllViews();
                    table_main = findViewById(R.id.table_main);

                    int col_dt=300;
                    int col_ti=250;
                    int col_ro=700;
                    int col_to=600;
                    int col_rs=200;
                    int col_height=130;

                    TableRow tbrow0 = new TableRow(getApplicationContext());
                    TextView tv0 = new TextView(getApplicationContext());
                    tv0.setText("Id");
                    tv0.setTextSize(16.0f);
                    tv0.setTextColor(Color.BLACK);
                    tv0.setTypeface(null, Typeface.BOLD);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setPadding(5,5,5,5);
                    tv0.setBackgroundResource(R.drawable.row_borders);
                    tv0.setVisibility(View.GONE);
                    tbrow0.addView(tv0);

                    TextView tv1 = new TextView(getApplicationContext());
                    tv1.setText("Date");
                    tv1.setTextSize(16.0f);
                    tv1.setTextColor(Color.BLACK);
                    tv1.setTypeface(null, Typeface.BOLD);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(5,5,5,5);
                    tv1.setBackgroundResource(R.drawable.row_borders);
                    tv1.setWidth(col_dt);
                    tv1.setHeight(col_height);
                    tbrow0.addView(tv1);

                    TextView tv2 = new TextView(getApplicationContext());
                    tv2.setText("Time");
                    tv2.setTextSize(16.0f);
                    tv2.setTextColor(Color.BLACK);
                    tv2.setTypeface(null, Typeface.BOLD);
                    tv2.setGravity(Gravity.CENTER);
                    tv2.setPadding(5,5,15,5);
                    tv2.setBackgroundResource(R.drawable.row_borders);
                    tv2.setWidth(col_ti);
                    tv2.setHeight(col_height);
                    tbrow0.addView(tv2);

                    TextView tv3 = new TextView(getApplicationContext());
                    tv3.setText("Route");
                    tv3.setTextSize(16.0f);
                    tv3.setTextColor(Color.BLACK);
                    tv3.setTypeface(null, Typeface.BOLD);
                    tv3.setGravity(Gravity.CENTER);
                    tv3.setPadding(5,5,15,5);
                    tv3.setBackgroundResource(R.drawable.row_borders);
                    tv3.setWidth(col_ro);
                    tv3.setHeight(col_height);
                    tbrow0.addView(tv3);

                    TextView tv4 = new TextView(getApplicationContext());
                    tv4.setText("Toll");
                    tv4.setTextSize(16.0f);
                    tv4.setTextColor(Color.BLACK);
                    tv4.setTypeface(null, Typeface.BOLD);
                    tv4.setGravity(Gravity.CENTER);
                    tv4.setPadding(5,5,15,5);
                    tv4.setBackgroundResource(R.drawable.row_borders);
                    tv4.setWidth(col_to);
                    tv4.setHeight(col_height);
                    tbrow0.addView(tv4);

                    TextView tv5 = new TextView(getApplicationContext());
                    tv5.setText("Amount");
                    tv5.setTextSize(16.0f);
                    tv5.setTextColor(Color.BLACK);
                    tv5.setTypeface(null, Typeface.BOLD);
                    tv5.setGravity(Gravity.CENTER);
                    tv5.setPadding(5,5,15,5);
                    tv5.setBackgroundResource(R.drawable.row_borders);
                    tv5.setWidth(col_rs);
                    tv5.setHeight(col_height);
                    tbrow0.addView(tv5);

                    table_main.addView(tbrow0, new TableLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    if (!response.toString().equals("")) {
                        JSONArray jArray = new JSONArray(response);
                        JSONObject jsonObj = (JSONObject) jArray.get(0);
                        Status = jsonObj.getString("Status");
                        Message = jsonObj.getString("Message");
                        if (Status.equals("true")) {
                            Value = jsonObj.getString("Value");
                            JSONArray jsonArray = new JSONArray(Value);
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jObj = (JSONObject) jsonArray.get(i);

                                TableRow tbrow = new TableRow(getApplicationContext());
                                TextView tvId = new TextView(getApplicationContext());
                                tvId.setText(jObj.getString("PayId"));
                                tvId.setTextSize(16.0f);
                                tvId.setTextColor(Color.BLACK);
                                tvId.setTypeface(null, Typeface.BOLD);
                                tvId.setGravity(Gravity.CENTER);
                                tvId.setPadding(5,5,5,5);
                                tvId.setBackgroundResource(R.drawable.row_borders);
                                tvId.setVisibility(View.GONE);
                                tbrow.addView(tvId);

                                TextView tvDate = new TextView(getApplicationContext());
                                tvDate.setText(jObj.getString("PayDate"));
                                tvDate.setTextSize(16.0f);
                                tvDate.setTextColor(Color.BLACK);
                                tvDate.setTypeface(null, Typeface.BOLD);
                                tvDate.setGravity(Gravity.CENTER);
                                tvDate.setPadding(5,5,5,5);
                                tvDate.setBackgroundResource(R.drawable.row_borders);
                                tvDate.setWidth(col_dt);
                                tvDate.setHeight(col_height);
                                tbrow.addView(tvDate);

                                TextView tv2Time = new TextView(getApplicationContext());
                                tv2Time.setText(jObj.getString("PayTime"));
                                tv2Time.setTextSize(16.0f);
                                tv2Time.setTextColor(Color.BLACK);
                                tv2Time.setTypeface(null, Typeface.BOLD);
                                tv2Time.setGravity(Gravity.CENTER);
                                tv2Time.setPadding(5,5,15,5);
                                tv2Time.setBackgroundResource(R.drawable.row_borders);
                                tv2Time.setWidth(col_ti);
                                tv2Time.setHeight(col_height);
                                tbrow.addView(tv2Time);

                                TextView tv3Route = new TextView(getApplicationContext());
                                tv3Route.setText(jObj.getString("Route"));
                                tv3Route.setTextSize(16.0f);
                                tv3Route.setTextColor(Color.BLACK);
                                tv3Route.setTypeface(null, Typeface.BOLD);
                                tv3Route.setGravity(Gravity.CENTER);
                                tv3Route.setPadding(5,5,15,5);
                                tv3Route.setBackgroundResource(R.drawable.row_borders);
                                tv3Route.setWidth(col_ro);
                                tv3Route.setHeight(col_height);
                                tbrow.addView(tv3Route);

                                TextView tv4Toll = new TextView(getApplicationContext());
                                tv4Toll.setText(jObj.getString("TollName"));
                                tv4Toll.setTextSize(16.0f);
                                tv4Toll.setTextColor(Color.BLACK);
                                tv4Toll.setTypeface(null, Typeface.BOLD);
                                tv4Toll.setGravity(Gravity.CENTER);
                                tv4Toll.setPadding(5,5,15,5);
                                tv4Toll.setBackgroundResource(R.drawable.row_borders);
                                tv4Toll.setWidth(col_to);
                                tv4Toll.setHeight(col_height);
                                tbrow.addView(tv4Toll);

                                TextView tv5Amount = new TextView(getApplicationContext());
                                tv5Amount.setText(jObj.getString("PaymentAmt"));
                                tv5Amount.setTextSize(16.0f);
                                tv5Amount.setTextColor(Color.BLACK);
                                tv5Amount.setTypeface(null, Typeface.BOLD);
                                tv5Amount.setGravity(Gravity.CENTER);
                                tv5Amount.setPadding(5,5,15,5);
                                tv5Amount.setBackgroundResource(R.drawable.row_borders);
                                tv5Amount.setWidth(col_rs);
                                tv5Amount.setHeight(col_height);
                                tbrow.addView(tv5Amount);

                                table_main.addView(tbrow, new TableLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                            }
                        } else {
                            Validation.Message(PaymentHistory.this, Message);
                        }
                    } else {
                        Validation.Message(PaymentHistory.this, "Response " + response);
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
                Postpara.put("opflag", "History" );
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
        startAnimatedActivity(new Intent(getApplicationContext(), Dashboard.class));
    }
}
