package com.tollbooth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class BaseNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            startAnimatedActivity(new Intent(getApplicationContext(), Dashboard.class));
        } else if (id == R.id.nav_profile) {
            startAnimatedActivity(new Intent(getApplicationContext(), MyProfile.class));
        }else if (id == R.id.nav_wallet) {
            startAnimatedActivity(new Intent(getApplicationContext(), MyWallet.class));
        }else if (id == R.id.nav_payHis) {
            startAnimatedActivity(new Intent(getApplicationContext(), PaymentHistory.class));
        } else if (id == R.id.nav_secreteCode) {
            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Login", 0);
            AlertDialog.Builder adb=new AlertDialog.Builder(BaseNavigation.this);
            adb.setTitle("Secret Code");
            adb.setMessage(prefLogin.getString("secreteCode","0"));
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            adb.show();
        } else if (id == R.id.nav_Complain) {
            startAnimatedActivity(new Intent(getApplicationContext(), ComplainRegister.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder adb=new AlertDialog.Builder(BaseNavigation.this);
            adb.setTitle("Logout");
            adb.setMessage("Are You Sure Do You Want To Logout");
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                    startAnimatedActivity(new Intent(getApplicationContext(), Login.class));
                }
            });
            adb.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void startAnimatedActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
