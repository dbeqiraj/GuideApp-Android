package com.dbeqiraj.guideapp.utilities;

import android.os.Build;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class ImageHandler extends SimpleTarget<GlideDrawable> {

    private LinearLayout linearLayout;

    public ImageHandler(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            linearLayout.setBackgroundDrawable(resource);
        else
            linearLayout.setBackground(resource);
    }
}
