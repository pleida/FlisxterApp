package com.example.flixster.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.databinding.PopularMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.time.Instant;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static Context context;
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
            ItemMovieBinding movieView = DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(movieView);
        }else {
            PopularMovieBinding movieView1 = DataBindingUtil.inflate(inflater, R.layout.popular_movie, parent, false);
            viewHolder = new OtherViewHolder(movieView1);
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder

    @Override
    public  void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {

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
        if ( movies.get(position).getRating() > 5){
            type_movie = 2;
        }else{
            type_movie = 1;
        }
        return type_movie;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
//        RelativeLayout container;
//        TextView tvTitle;
//        TextView tvOverview;
//        ImageView ivPoster;
        ItemMovieBinding movieBinding;


        public ViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvOverview = itemView.findViewById(R.id.tvOverview);
//            ivPoster = itemView.findViewById(R.id.ivposter);
//            container = itemView.findViewById(R.id.container);

            this.movieBinding = binding;
        }

        public void bind(Movie movie) {
//            tvTitle.setText(movie.getTitle());
//            tvOverview.setText(movie.getOverview());
//            String imageUrl;
//            // if landscape
//            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//                //imageurl = back drop image
//                imageUrl = movie.getBackdropPath();
//            }else{
//                //esle imageurl = poster image
//                imageUrl = movie.getPosterPath();
//            }
//
//            Glide.with(context).load(imageUrl).placeholder(R.drawable.dd).transform(new RoundedCorners(200)).into(ivPoster);

            movieBinding.setMovie(movie);
            movieBinding.executePendingBindings();

            // 1.Register click listener on the whole row
            movieBinding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));


                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, movieBinding.ivposter,"transitionTv");
                    context.startActivity(i,options.toBundle());                }
            });

        }

    }


    public class OtherViewHolder extends RecyclerView.ViewHolder{
//        ImageView popularImage;
//      RelativeLayout container2;
        PopularMovieBinding popularBinding;

        public OtherViewHolder(@NonNull PopularMovieBinding binding) {
            super(binding.getRoot());
//            popularImage = itemView.findViewById(R.id.popular_movie);
//            container2 = itemView.findViewById(R.id.container2);

            this.popularBinding = binding;
        }
        public void bind(Movie movie) {
//            String imageurl;
//            imageurl = movie.getBackdropPath();
//            Glide.with(context).load(imageurl).placeholder(R.drawable.dd).transform(new RoundedCorners(200)).into(popularImage);

            popularBinding.setMovie(movie);
            popularBinding.executePendingBindings();

            popularBinding.container2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, popularBinding.popularMovie,"transitionTv");
                    context.startActivity(i,options.toBundle());

                }
            });


        }


    }


    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrlPopular"})
        public static void loadImagePopular(ImageView view, String url) {
            Glide.with(context).load(url)
                    .placeholder(R.drawable.dd)
                    .transform(new RoundedCorners(200))
                    .into(view);
        }


        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(context).load(url)
                    .placeholder(R.drawable.dd)
                    .transform(new RoundedCorners(200))
                    .into(view);
        }
    }

}
