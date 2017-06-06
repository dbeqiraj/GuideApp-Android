package com.dbeqiraj.guideapp.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {

    private Spot spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_detail);

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spot = (Spot) getIntent().getSerializableExtra("spot");
        Utils.setActionBarTitle(getSupportActionBar(), spot.getName());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView shortDesc = (TextView) findViewById(R.id.short_dsc);
        TextView address = (TextView) findViewById(R.id.address);
        TextView distance = (TextView) findViewById(R.id.distance);
        TextView web = (TextView) findViewById(R.id.web);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView hours = (TextView) findViewById(R.id.hours);
        TextView navigate = (TextView) findViewById(R.id.navigate);
        TextView longDesc = (TextView) findViewById(R.id.long_desc);

        shortDesc.setText(spot.getShort_dsc());
        address.setText(spot.getAddress());
        distance.setText(spot.getDistance());
        web.setText(spot.getSite());
        phone.setText(spot.getPhone());
        email.setText(spot.getEmail());
        hours.setText(spot.getHours());
        longDesc.setText(spot.getLong_desc());
        navigate.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(spot.getLatitude(), spot.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(marker).title(spot.getName()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 9));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() ) {
            case R.id.navigate:
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", spot.getLatitude(),spot.getLongitude(),spot.getLatitude(),spot.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;
        }
    }
}
