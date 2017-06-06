package com.dbeqiraj.guideapp.utilities;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.model.Spot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallWS {

    public static int respondStatus;
    public static List<Spot> listOfSpots;
    public static void getSpots(final Activity activity,  Call<List<Spot>> call) {

        respondStatus = 0;
        listOfSpots =   null;

        if ( Utils.isNetworkOK ) {
            try {
                call.enqueue(new Callback<List<Spot>>() {
                    @Override
                    public void onFailure(Call<List<Spot>> call, Throwable t) {
                        Log.e("APIPlug", "Error Occured: " + t.getMessage());
                        respondStatus = 2;
                    }

                    @Override
                    public void onResponse(Call<List<Spot>> call, Response<List<Spot>> response) {
                        Log.d("APIPlug", "Successfully response fetched!" + response.body() );
                        listOfSpots = response.body();
                        respondStatus = ( listOfSpots != null && listOfSpots.size() > 0 ) ? 1 : 2;
                    }
                });
            } catch ( Exception e ) {
                e.printStackTrace();
                Toast.makeText(activity, activity.getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.showSimpleDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.connection_error), null);
                }
            });
        }
    }
}