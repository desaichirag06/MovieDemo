package com.chirag.valarassignment.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.databinding.ActivityMovieDetailsBinding;
import com.chirag.valarassignment.model.MovieDetailModel;
import com.chirag.valarassignment.ui.base.BaseActivity;
import com.chirag.valarassignment.utils.GlideUtils;
import com.chirag.valarassignment.utils.Logger;
import com.chirag.valarassignment.webservices.APIs;
import com.chirag.valarassignment.webservices.JSONCallback;
import com.chirag.valarassignment.webservices.Retrofit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import static com.chirag.valarassignment.utils.AppConstants.API_KEY;

public class MovieDetailsActivity extends BaseActivity {

    private static final String EXTRA_IMAGE = "extraImage";
    ActivityMovieDetailsBinding mBinding;
    Gson gson = new Gson();
    MovieDetailModel movieDetailModel;
    GlideUtils glideUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        glideUtils = new GlideUtils(this);

        //Getting Movie ID
        String id = getIntent().getStringExtra("movieId");

        //API Calling
        API_GetMovieDetails(id);
    }

    public void API_GetMovieDetails(String id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("i", id);
        params.put("apikey", API_KEY);
        try {
            Retrofit.with(this)
                    .setAPI(APIs.BASE_URL)
                    .setGetParameters(params)
                    .setCallBackListener(new JSONCallback(MovieDetailsActivity.this, showProgressBar()) {
                        @Override
                        protected void onSuccess(int statusCode, JSONObject jsonObject) {
                            hideProgressBar();
                            if (jsonObject.optBoolean("Response")) {

                                Type modelType = new TypeToken<MovieDetailModel>() {
                                }.getType();

                                movieDetailModel = gson.fromJson(jsonObject.toString(), modelType);

                                //Collapsing Toolbar
                                ViewCompat.setTransitionName(findViewById(R.id.appBar), EXTRA_IMAGE);
                                mBinding.tbCollapsing.setTitle(movieDetailModel.getTitle());
                                mBinding.tbCollapsing.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
                                glideUtils.loadImageSimple(movieDetailModel.getPoster(), mBinding.ivMoviePoster);

                                //Movie Details
                                mBinding.tvTitle.setText(movieDetailModel.getTitle());
                                mBinding.tvGenre.setText(movieDetailModel.getGenre());
                                mBinding.tvDirector.setText(movieDetailModel.getDirector());
                                mBinding.tvWriter.setText(movieDetailModel.getWriter());
                                mBinding.tvActor.setText(movieDetailModel.getActors());
                                mBinding.tvPlot.setText(movieDetailModel.getPlot());
                            }
                        }

                        @Override
                        protected void onFailed(int statusCode, String message) {
                            Logger.e(message);
                            hideProgressBar();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }
}
