package com.tollbooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.ui.IconGenerator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tollbooth.CommonClasses.GeneralValues;
import com.tollbooth.CommonClasses.Validation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends BaseNavigation implements
        AdapterView.OnItemSelectedListener ,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener , OnMapReadyCallback, TaskLoadedCallback{
    Spinner spRoutes, spTolls;
    Button btnPay;
    FloatingActionButton btnZoom;
    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    String Status = "", Message = "", Value = "";
    String RouteId="";
    GeneralValues gv;
    SharedPreferences prefLogin;
    ArrayList<HashMap<String, String>> GetRoutelist;
    ArrayList<HashMap<String, String>> GetTolllist;
    TextView tvName;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    //Button getDirection;
    private Polyline currentPolyline;
    private MapFragment mapFragment;
    private boolean isFirstTime = true;

    private static final String TAG = "Dashboard";
    private Location mCurrentLocation;
    //private TextView tvLocation;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 1 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 1000; /* 2 sec */
    String []EndLatLon;
    String []StartLatLon;
    IconGenerator iconFactory;
    boolean MarkerClear=false, zoom=false;
    String mapData="";
    ArrayAdapter<String> adapterToll;
    int zoomvalue=18, zin=18, zout=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_dashboard, null, false);
        drawer.addView(contentView, 0);
        gv = ((GeneralValues) getApplicationContext());
        mRequestQueue = Volley.newRequestQueue(Dashboard.this);
        prefLogin = getApplicationContext().getSharedPreferences("Login", 0);
        GetRoutelist = new ArrayList<HashMap<String, String>>();
        GetTolllist = new ArrayList<HashMap<String, String>>();
        spRoutes = findViewById(R.id.spRoutes);
        spRoutes.setOnItemSelectedListener(this);
        spTolls = findViewById(R.id.spTolls);
        if (gv.isNetworkConnected(getApplicationContext())) {
            getRoutes();
        } else {
            Validation.Message(getApplicationContext(),"Start Internet");
        }

        View header=navigationView.getHeaderView(0);
        tvName=header.findViewById(R.id.tvName);
        tvName.setText(prefLogin.getString("regName",""));
        btnZoom=findViewById(R.id.btnZoom);
        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zoom) {
                    zoom = false;
                    zoomvalue = zout;
                }else {
                    zoom = true;
                    zoomvalue = zin;
                }
            }
        });
        btnPay=findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = spRoutes.getSelectedItem().toString();
                if(!name.equals("Select Route")){
                    name = spTolls.getSelectedItem().toString();
                    if(!name.equals("Select Toll to Pay")){
                        AlertDialog.Builder adb=new AlertDialog.Builder(Dashboard.this);
                        adb.setTitle("Make Payment");
                        adb.setMessage("Are You Sure to pay this toll?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok",new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                String rId="", tId="";

                                for (int j = 0; j <= GetRoutelist.size(); j++) {
                                    if(GetRoutelist.get(j).get("Route").equals(spRoutes.getSelectedItem().toString())){
                                        rId=GetRoutelist.get(j).get("RouteId");
                                        break;
                                    }
                                }
                                for (int j = 0; j <= GetTolllist.size(); j++) {
                                    if(GetTolllist.get(j).get("TollName").equals(spTolls.getSelectedItem().toString())){
                                        tId=GetTolllist.get(j).get("TollId");
                                        break;
                                    }
                                }
                                if (gv.isNetworkConnected(getApplicationContext())) {
                                    Intent i =new Intent(Dashboard.this,TollPayment.class);
                                    i.putExtra("RouteId",rId);
                                    i.putExtra("RouteName",spRoutes.getSelectedItem().toString());
                                    i.putExtra("TollId",tId);
                                    i.putExtra("TollName",spTolls.getSelectedItem().toString());
                                    startAnimatedActivity(i);
                                } else {
                                    Validation.Message(getApplicationContext(),"Start Internet");
                                }
                            }
                        });
                        adb.show();
                    }else {
                        Validation.Message(Dashboard.this,"Select Toll");
                    }
                }else {
                    Validation.Message(Dashboard.this,"Select Route");
                }
            }
        });



        //code for getting current location
        requestMultiplePermissions();

        //tvLocation = (TextView) findViewById((R.id.tv));
        iconFactory = new IconGenerator(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void getRoutes() {
        pDialog = new ProgressDialog(Dashboard.this);
        pDialog.setMessage("Loading Routes List");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = gv.IPAddress + "getRoutes.ashx";
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
                            JSONArray jsonArray = new JSONArray(Value);
                            GetRoutelist.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = (JSONObject) jsonArray.get(i);
                                HashMap<String, String> ProList = new HashMap<String, String>();
                                ProList.put("RouteId", jObj.getString("RouteId"));
                                ProList.put("Route", jObj.getString("Route"));
                                ProList.put("StartLatLon", jObj.getString("StartLatLon"));
                                ProList.put("EndLatLon", jObj.getString("EndLatLon"));
                                ProList.put("RoutePath", jObj.getString("RoutePath"));
                                GetRoutelist.add(ProList);
                            }
                            int size = GetRoutelist.size() + 1;
                            String[] spinnerArray = new String[size];
                            spinnerArray[0] = "Select Route";
                            for (int i = 1; i <= GetRoutelist.size(); i++) {
                                spinnerArray[i] = GetRoutelist.get(i - 1).get("Route");
                            }
                            if (spinnerArray.length > 0) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_spinner_item, spinnerArray);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRoutes.setAdapter(adapter);
                            }
                        } else {
                            Validation.Message(Dashboard.this, Message);
                        }
                    } else {
                        Validation.Message(Dashboard.this, "Response " + response);
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
                return Postpara;
            }
        };

        mStringRequest.setShouldCache(false);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(gv.VolleyTimeOut, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);
    }

    private void getTolls(String RouteId) {
        pDialog = new ProgressDialog(Dashboard.this);
        pDialog.setMessage("Loading Toll List");
        pDialog.setCancelable(false);
        pDialog.show();
        final String Route=RouteId;
        String url = gv.IPAddress + "getTollsByRoute.ashx";
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
                            JSONArray jsonArray = new JSONArray(Value);
                            GetTolllist.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jObj = (JSONObject) jsonArray.get(i);
                                HashMap<String, String> ProList = new HashMap<String, String>();
                                ProList.put("TollId", jObj.getString("TollId"));
                                ProList.put("TollName", jObj.getString("TollName"));
                                ProList.put("TollLatLon", jObj.getString("TollLatLon"));
                                ProList.put("SeqNo", jObj.getString("SeqNo"));
                                GetTolllist.add(ProList);
                            }
                            int size = GetTolllist.size() + 1;
                            String[] spinnerArray = new String[size];
                            spinnerArray[0] = "Select Toll to Pay";
                            for (int i = 1; i <= GetTolllist.size(); i++) {
                                spinnerArray[i] = GetTolllist.get(i - 1).get("TollName");
                            }
                            if (spinnerArray.length > 0) {
                                adapterToll = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_spinner_item, spinnerArray);
                                adapterToll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spTolls.setAdapter(adapterToll);
                            }
                        } else {
                            Validation.Message(Dashboard.this, Message);
                        }
                    } else {
                        Validation.Message(Dashboard.this, "Response " + response);
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
                Postpara.put("RouteId", Route );
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spRoutes:
                String name = spRoutes.getSelectedItem().toString();
                if(!name.equals("Select Route")){
                    for (int j = 0; j <= GetRoutelist.size(); j++) {
                        if(GetRoutelist.get(j).get("Route").equals(name)){
                            RouteId=GetRoutelist.get(j).get("RouteId");
                            mapData=GetRoutelist.get(j).get("RoutePath");
                            EndLatLon=GetRoutelist.get(j).get("EndLatLon").split(",");
                            StartLatLon=GetRoutelist.get(j).get("StartLatLon").split(",");
                            getTolls(RouteId);
                            MarkerClear=true;
                            isFirstTime=true;
                            zoom=false;
                            zoomvalue=zout;
                            break;
                        }
                    }
                }
                else{
                    RouteId="";
                    mapData="";
                    GetTolllist.clear();
                    if(adapterToll != null) {
                        adapterToll.notifyDataSetChanged();
                    }
                    MarkerClear=false;
                    isFirstTime=false;
                }
                break;
                default:
                    RouteId="";
                    mapData="";
                    GetTolllist.clear();
                    MarkerClear=false;
                    isFirstTime=false;
                    break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void addIcon(IconGenerator iconFactory, String text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        mMap.addMarker(markerOptions);
    }

    //------------ Google Map Code -----------------------
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        if (mCurrentLocation != null) {
            Log.d("mylog", "Added Markers");
            if (MarkerClear) {
                if (EndLatLon != null && isFirstTime) {
                    if (EndLatLon.length > 0) {
                        mMap.clear();
                        LatLng source = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                        LatLng destination = new LatLng(Double.parseDouble(EndLatLon[0]), Double.parseDouble(EndLatLon[1]));
                        //new FetchURL(Dashboard.this).execute(getUrl(source, destination, "driving"), "driving");
                        if(!mapData.equals("")) {
                            new PointsParser(Dashboard.this, "driving").execute(mapData);
                        }
                        //isFirstTime=false;
                    }
                }
                if (GetTolllist.size() > 0) {
                    for (int j = 0; j < GetTolllist.size(); j++) {
                        String[] LATLON = GetTolllist.get(j).get("TollLatLon").split(",");
                        LatLng marker = new LatLng(Double.parseDouble(LATLON[0]), Double.parseDouble(LATLON[1]));
                        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
                        addIcon(iconFactory, GetTolllist.get(j).get("TollName").toString(), marker);
                    }
                    //MarkerClear=false;
                }
                if (GetTolllist.size() > 0) {
                    String name = spRoutes.getSelectedItem().toString();
                    if (!name.equals("Select Route")) {
                        for (int j = 0; j < GetRoutelist.size(); j++) {
                            if (GetRoutelist.get(j).get("Route").equals(name)) {
                                String[] Source = GetRoutelist.get(j).get("StartLatLon").split(",");
                                String[] Destination = GetRoutelist.get(j).get("EndLatLon").split(",");
                                LatLng marker = new LatLng(Double.parseDouble(Source[0]), Double.parseDouble(Source[1]));
                                iconFactory.setStyle(IconGenerator.STYLE_RED);
                                //addIcon(iconFactory, "", marker);

                                marker = new LatLng(Double.parseDouble(Destination[0]), Double.parseDouble(Destination[1]));
                                iconFactory.setStyle(IconGenerator.STYLE_GREEN);
                                addIcon(iconFactory, "", marker);

                                if(!zoom) {
                                    SharedPreferences prefLocation = getApplicationContext().getSharedPreferences("LastLocation", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = prefLocation.edit();
                                    editor.putString("Latitude", String.valueOf(Source[0]));
                                    editor.putString("Longitude", String.valueOf(Source[1]));
                                    editor.commit();
                                    zoomvalue=zout;
                                }
                                break;
                            }
                        }
                    }
                }
                //Tollbooth Notification
                if (GetTolllist.size() > 1) {
                    for (int j = 0; j < GetTolllist.size(); j++) {
                        String[] LATLON = GetTolllist.get(j).get("TollLatLon").split(",");
                        LatLng point1 = new LatLng(Double.parseDouble(LATLON[0]), Double.parseDouble(LATLON[1]));
                        LatLng point2 = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                        Double distance = Math.ceil(SphericalUtil.computeDistanceBetween(point1, point2));

                        if(distance<=60.00) {
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), Dashboard.class), 0);
                            Resources r = getResources();
                            final Notification notification = new NotificationCompat.Builder(getApplicationContext())
                                    .setTicker("Toll Booth")
                                    .setSmallIcon(R.drawable.markernew)
                                    .setContentTitle("Toll Booth")
                                    .setContentText("Toll Booth a head")
                                    .setContentIntent(pi)
                                    .setAutoCancel(true)
                                    .build();

                            final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(0, notification);
                            final String tId=GetTolllist.get(j).get("TollId");
                            final String tollname = GetTolllist.get(j).get("TollName").toString();
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            String rId="";

                                            for (int j = 0; j < GetRoutelist.size(); j++) {
                                                if(GetRoutelist.get(j).get("Route").equals(spRoutes.getSelectedItem().toString())){
                                                    rId=GetRoutelist.get(j).get("RouteId");
                                                    break;
                                                }
                                            }
                                            Intent i =new Intent(Dashboard.this,TollPayment.class);
                                            i.putExtra("RouteId",rId);
                                            i.putExtra("RouteName",spRoutes.getSelectedItem().toString());
                                            i.putExtra("TollId",tId);
                                            i.putExtra("TollName",tollname);
                                            startAnimatedActivity(i);
                                            notificationManager.cancelAll();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            getApplicationContext();
                                            notificationManager.cancelAll();
                                            break;
                                    }
                                }
                            };
                            final AlertDialog builder = new AlertDialog.Builder(Dashboard.this).setTitle("Toll Booth").setMessage("message")
                                    .setPositiveButton("OK", dialogClickListener)
                                    .setNegativeButton("CANCEL", dialogClickListener).setCancelable(false).show();

                            TextView textView = (TextView) builder.findViewById(android.R.id.message);
                            //String tollname = GetTolllist.get(j).get("TollName").toString();
                            textView.setText("Pay For This Tollbooth " + tollname);
                            textView.setTextColor(Color.parseColor("#bc3d3d3d"));
                        }
                    }
                }
                //------------------------------------------
            }
        }
        SharedPreferences prefLocation = getApplicationContext().getSharedPreferences("LastLocation", 0);
        MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.markernew)).
                position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).
                title("");
        mMap.addMarker(marker);
        String Lat1=prefLocation.getString("Latitude","0");
        String Lat2=prefLocation.getString("Longitude","0");

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(Double.parseDouble(Lat1), Double.parseDouble(Lat2)))
                .zoom(zoomvalue)
                .bearing(0)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10, null);

        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(Lat1), Double.parseDouble(Lat2)), 18);
        mMap.animateCamera(cameraUpdate);*/

    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
    //runtime permission method
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    //methods for getting current location
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

        } else {
            //Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }
    @Override
    public void onLocationChanged(Location location) {
        this.mCurrentLocation = location;
        Log.e("***** Location ****", "*** " + mCurrentLocation.getLatitude() + " *** " + mCurrentLocation.getLongitude());
        SharedPreferences prefLocation = getApplicationContext().getSharedPreferences("LastLocation", 0); // 0 - for private mode
        SharedPreferences.Editor editor = prefLocation.edit();
        editor.putString("Latitude", String.valueOf(mCurrentLocation.getLatitude()));
        editor.putString("Longitude", String.valueOf(mCurrentLocation.getLongitude()));
        editor.commit();

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }
    //----------------------------------------------------
}
