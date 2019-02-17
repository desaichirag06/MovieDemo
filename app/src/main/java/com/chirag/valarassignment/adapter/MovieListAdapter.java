package com.chirag.valarassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.databinding.ItemMovieBinding;
import com.chirag.valarassignment.model.SearchListModel;
import com.chirag.valarassignment.ui.home.MovieDetailsActivity;
import com.chirag.valarassignment.utils.GlideUtils;

import java.util.ArrayList;

/**
 * Created by Chirag Desai on 17-02-2019.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchListModel> searchList;

    public MovieListAdapter(Context context, ArrayList<SearchListModel> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_movie, parent, false);
        return new ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtils glideUtils = new GlideUtils(context);
        SearchListModel searchListModel = searchList.get(position);

        glideUtils.loadImageSimple(searchListModel.getPoster(), holder.mBinder.ivPoster);
        holder.mBinder.tvMovieName.setText(searchListModel.getTitle());
        holder.mBinder.tvReleaseDate.setText(searchListModel.getYear());
        holder.mBinder.cvOrder.setOnClickListener(view -> context.startActivity(new Intent(context, MovieDetailsActivity.class)
                .putExtra("movieId", searchListModel.getImdbid())));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding mBinder;

        ViewHolder(ItemMovieBinding mBinder) {
            super(mBinder.getRoot());
            this.mBinder = mBinder;

        }
    }
}
