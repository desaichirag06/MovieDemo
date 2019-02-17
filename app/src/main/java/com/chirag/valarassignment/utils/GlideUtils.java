package com.chirag.valarassignment.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chirag.valarassignment.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideUtils {

    private Context context;

    public GlideUtils(Context context) {
        this.context = context;
    }

    public void loadImageSimple(String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.shape_circle_grey)
                        .error(R.drawable.shape_circle_grey)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop())
                .transition(withCrossFade(300))
                .into(view);
    }
}
