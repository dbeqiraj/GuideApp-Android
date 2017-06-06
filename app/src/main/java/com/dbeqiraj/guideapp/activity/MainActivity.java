package com.dbeqiraj.guideapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.adapter.GridAdapter;
import com.dbeqiraj.guideapp.http_rest_utils.ApiClient;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.GPSTracker;
import com.dbeqiraj.guideapp.utilities.Utils;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gridView;
    public double longitude = 0 ;
    public double latitude = 0;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.grid_view);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.all_spots));
        Utils.createConnectivityListener(this);
        getLocationFillList(ApiClient.getClient().getAll());
    }

    public void populateWithSpots(final List<Spot> spots) {
        gridView.setAdapter(null);
        GridAdapter adapter = new GridAdapter(this, spots);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("spot", spots.get(i));
                startActivity(intent);
            }
        });
    }

    public void getLocationFillList(final Call<List<Spot>> call) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GPSTracker gps = new GPSTracker(MainActivity.this);
                if ( gps.canGetLocation() ) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    gps.stopUsingGPS();
                    new Utils.GetData(MainActivity.this, call).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.cannot_continue), Toast.LENGTH_SHORT).show();

                }
            }
        }, 1000);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all_spots) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.all_spots));
            getLocationFillList(ApiClient.getClient().getAll());
        } else if (id == R.id.nav_food) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.food_and_drinks));
            getLocationFillList(ApiClient.getClient().getFoodAndDrinks());
        } else if (id == R.id.nav_hotel) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.hotel));
            getLocationFillList(ApiClient.getClient().getHotels());
        } else if (id == R.id.nav_museums) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.museums));
            getLocationFillList(ApiClient.getClient().getMuseums());
        } else if (id == R.id.nav_attractions) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.attractions));
            getLocationFillList(ApiClient.getClient().getAttractions());
        } else if (id == R.id.nav_nightlife) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.nightlife));
            getLocationFillList(ApiClient.getClient().getNightlife());
        } else if (id == R.id.nav_shopping) {
            Utils.setActionBarTitle(getSupportActionBar(), getString(R.string.shopping));
            getLocationFillList(ApiClient.getClient().getShopping());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}