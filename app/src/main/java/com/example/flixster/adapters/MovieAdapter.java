package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.time.Instant;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<Movie> movies;
    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }
    // Usually involves inflating a layout from XML and returning the Holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        Log.d("MovieAdapter", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        if ( viewType == 1) {
            View movieView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(movieView);
        }else {
            View movieView1 =inflater.inflate(R.layout.popular_movie, parent, false);
            viewHolder = new OtherViewHolder(movieView1);
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder

    @Override
    public  void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        if (Holder.getItemViewType() == 1){
            ViewHolder var1 = (ViewHolder) Holder;
            var1.bind(movie);
        }else{
            OtherViewHolder var2 = (OtherViewHolder) Holder;
            var2.bind(movie);
        }


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // Return the type of the movie(popular or not)
    @Override
    public int getItemViewType(int position) {
        int type_movie;
        if ( movies.get(position).getVoteAverage() >= 5){
            type_movie = 2;
        }else{
            type_movie = 1;
        }
        return type_movie;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivposter);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //imageurl = back drop image
                imageUrl = movie.getBackdropPath();
            }else{
                //esle imageurl = poster image
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).placeholder(R.drawable.dd).into(ivPoster);

        }

    }
    class OtherViewHolder extends RecyclerView.ViewHolder{
        ImageView popularImage;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            popularImage = itemView.findViewById(R.id.popular_movie);

        }
        public void bind(Movie movie) {
        String imageurl;
        imageurl = movie.getBackdropPath();
        Glide.with(context).load(imageurl).placeholder(R.drawable.dd).into(popularImage);

        }


    }



}
