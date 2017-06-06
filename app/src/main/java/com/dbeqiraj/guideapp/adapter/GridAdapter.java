package com.dbeqiraj.guideapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.activity.MainActivity;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.ImageHandler;
import com.dbeqiraj.guideapp.utilities.Utils;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private MainActivity activity;
    private List<Spot> spots;

    public GridAdapter(MainActivity activity, List<Spot> spots) {
        this.activity = activity;
        this.spots = spots;
    }

    @Override
    public int getCount() {
        return spots.size();
    }

    @Override
    public Object getItem(int i) {
        return spots.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if ( view == null )
            view = activity.getLayoutInflater().inflate(R.layout.spot_grid, viewGroup, false);
        LinearLayout    spotImg     = (LinearLayout) view.findViewById(R.id.spot_image);
        TextView        spotName    = (TextView) view.findViewById(R.id.spot_name);
        TextView        spotDist    = (TextView) view.findViewById(R.id.spot_distance);

        Glide.with(activity).load(spots.get(i).getImg_url())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageHandler(spotImg));
        spotName.setText(spots.get(i).getName());
        spots.get(i).setDistance(Utils.calculateDistance(spots.get(i).getLatitude(), spots.get(i).getLongitude(), activity.latitude, activity.longitude));
        spotDist.setText(spots.get(i).getDistance());

        return view;
    }
}