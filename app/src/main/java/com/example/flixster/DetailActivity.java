package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
//    TextView tvTitle;
//    TextView tvOverview;
//    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    Movie movie;
    ActivityDetailBinding detailBinding;

    private   static final  String YOUTUBE_API_KEY = "AIzaSyAZzPV9XiJHsyE6fQZoyxMoYnC_WNZaV7o";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//        setContentView(R.layout.activity_detail);

//        tvTitle = findViewById(R.id.tvTitle);
//        tvOverview = findViewById(R.id.tvOverview);
//        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = detailBinding.player;

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        detailBinding.setMovie(movie);
        detailBinding.executePendingBindings();
//        tvTitle.setText(movie.getTitle());
//        tvOverview.setText(movie.getOverview());
//        ratingBar.setRating((float) movie.getRating());



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL,movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.d("DetailActivity","failed");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onInitializationSuccess");
                if (movie.getRating() > 5){
                    youTubePlayer.loadVideo(youtubeKey);
                } else{
                    youTubePlayer.cueVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });
    }
}