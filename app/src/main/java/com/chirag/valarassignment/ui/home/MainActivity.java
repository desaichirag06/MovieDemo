package com.chirag.valarassignment.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.adapter.MovieListAdapter;
import com.chirag.valarassignment.databinding.ActivityMainBinding;
import com.chirag.valarassignment.model.SearchListModel;
import com.chirag.valarassignment.ui.base.BaseActivity;
import com.chirag.valarassignment.utils.Logger;
import com.chirag.valarassignment.webservices.APIs;
import com.chirag.valarassignment.webservices.JSONCallback;
import com.chirag.valarassignment.webservices.Retrofit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.chirag.valarassignment.utils.AppConstants.API_KEY;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;
    Gson gson = new Gson();
    ArrayList<SearchListModel> searchList;
    MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar.toolbar);

        //API Calling
        API_GetMovieList();
    }

    public void API_GetMovieList() {

        HashMap<String, String> params = new HashMap<>();
        params.put("s", "star wars");
        params.put("apikey", API_KEY);
        try {
            Retrofit.with(this)
                    .setAPI(APIs.BASE_URL)
                    .setGetParameters(params)
                    .setCallBackListener(new JSONCallback(MainActivity.this, showProgressBar()) {
                        @Override
                        protected void onSuccess(int statusCode, JSONObject jsonObject) {
                            hideProgressBar();
                            if (jsonObject.optJSONArray("Search") != null && jsonObject.optJSONArray("Search").length() > 0) {

                                Type modelType = new TypeToken<List<SearchListModel>>() {
                                }.getType();

                                searchList = gson.fromJson(jsonObject.optJSONArray("Search").toString(), modelType);
                                if (searchList.size() > 0) {
                                    mBinding.rvMovieList.setVisibility(View.VISIBLE);
                                    mAdapter = new MovieListAdapter(MainActivity.this, searchList);
                                    mBinding.rvMovieList.setAdapter(mAdapter);
                                    mBinding.tvEmpty.setVisibility(View.GONE);
                                } else {
                                    mBinding.rvMovieList.setVisibility(View.GONE);
                                    mBinding.tvEmpty.setVisibility(View.VISIBLE);
                                    mBinding.tvEmpty.setText(getString(R.string.no_movies_found));
                                }

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
