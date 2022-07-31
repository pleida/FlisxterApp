package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> movies;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView rvMovies = mainBinding.rvMovies;
        movies = new ArrayList<>();
        // Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this,movies);

        // Set a Layout on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONArray results = jsonObject.getJSONArray("results");
//                    Log.i(TAG,"Results" + results.toString());
//                    movies.addAll(Movie.fromJsonArray(results));
//                    movieAdapter.notifyDataSetChanged();
//                    Log.i(TAG,"Movies: " + movies.size());
//                }catch (JSONException e){
//                    Log.e(TAG,"Hit json exception",e);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG, "onFailure");
//            }
//        });

        StringBuilder responseStrBuilder = new StringBuilder();;
        try {
            InputStream inputStream = getAssets().open("movies.txt");
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            movies.addAll( Movie.fromJsonArray(results));
            movieAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "onCreate: " + responseStrBuilder.toString());
        }

    }
}