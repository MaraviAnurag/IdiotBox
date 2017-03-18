package com.merakiworks.idiotbox.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.merakiworks.idiotbox.R;
import com.merakiworks.idiotbox.adapter.CastingAdapter;
import com.merakiworks.idiotbox.adapter.SimilarAdapter;
import com.merakiworks.idiotbox.adapter.TrailerAdapter;
import com.merakiworks.idiotbox.model.Movie;
import com.merakiworks.idiotbox.other.Contract;
import com.merakiworks.idiotbox.other.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.merakiworks.idiotbox.other.Contract.API_IMAGE_BASE_URL;
import static com.merakiworks.idiotbox.other.Contract.API_IMAGE_SIZE_XXL;
import static com.merakiworks.idiotbox.other.Contract.API_KEY;
import static com.merakiworks.idiotbox.other.Contract.API_MOVIE;
import static com.merakiworks.idiotbox.other.Contract.API_URL;
import static com.merakiworks.idiotbox.other.Contract.OMDB_BASE_URL;

/**
 * Created by anuragmaravi on 29/01/17.
 */

public class MovieDetailsActivity  extends AppCompatActivity {

    public static String TAG;
    String movieId;
    static String movieDetailsRequest;
    private TextView textViewDirector,
            textViewTitle,
            textViewVoteAverage,
            textViewReleaseDateRuntime,
            textViewOverview,
            textViewMovieOrTvShow,
            textViewYear,
            textViewTmdbVote,
            textViewMovieTagline,
            textViewCountry;

    //Similar Movies
    private RecyclerView recyclerViewSimilar;
    private List<Movie> similarMovieList= new ArrayList<>();
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager similarLayoutManager;

    //Trailers
    private List<Movie> trailersList= new ArrayList<>();
    private RecyclerView recyclerViewTrailers;
    private  RecyclerView.Adapter adapterTrailers;
    private RecyclerView.LayoutManager layoutManagerTrailers;

    //Casting
    private List<Movie> castingList= new ArrayList<>();
    private RecyclerView recyclerViewCasting;
    private  RecyclerView.Adapter adapterCasting;
    private RecyclerView.LayoutManager layoutManagerCasting;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        TAG = getClass().getSimpleName();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");
        movieId = getIntent().getStringExtra("movie_id");

        //Views Initialisation
        textViewOverview = (TextView) findViewById(R.id.textViewOverview);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewMovieOrTvShow = (TextView) findViewById(R.id.textViewMovieOrTvShow);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewReleaseDateRuntime = (TextView) findViewById(R.id.textViewReleaseDateRuntime);
        textViewDirector = (TextView) findViewById(R.id.textViewDirector);
        textViewCountry = (TextView) findViewById(R.id.textViewCountry);
        textViewVoteAverage = (TextView) findViewById(R.id.textViewVoteAverage);
        textViewMovieTagline = (TextView) findViewById(R.id.textViewMovieTagline);
//        textViewTmdbVote = (TextView) findViewById(R.id.textViewTmdbVote);


        /**
         * Movie Details
         */
        //Movie Details Request
        movieDetailsRequest = API_URL + Contract.API_MOVIE + "/" + movieId + "?api_key=" + Contract.API_KEY;

        //request movie details
        StringRequest stringRequestTmdb = new StringRequest(Request.Method.GET, movieDetailsRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(TMDb): " + response);
                        try {
                            parseAndDisplayData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestTmdb);

        /**
         * Trailers
         */
        //RecyclerView Trailers
        layoutManagerTrailers = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrailers = (RecyclerView) findViewById(R.id.recyclerViewTrailers);
        recyclerViewTrailers.setLayoutManager(layoutManagerTrailers);
        recyclerViewTrailers.setItemAnimator(new DefaultItemAnimator());


        //Request Trailers
        String trailersRequest = API_URL + API_MOVIE + "/" + movieId + "/videos?api_key=" + API_KEY;
        StringRequest stringRequestTrailers = new StringRequest(Request.Method.GET, trailersRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(Trailers): " + response);
                        try {
                            JSONObject parentObject= new JSONObject(response);
                            JSONArray parentArray = parentObject.getJSONArray("results");
                            for(int i=0;i<parentArray.length();i++){
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                Movie movieModel = new Movie();
                                movieModel.setVideoKey(finalObject.getString("key"));
                                movieModel.setVideoName(finalObject.getString("name"));
                                movieModel.setVideoType(finalObject.getString("type"));
                                trailersList.add(movieModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterTrailers = new TrailerAdapter(getApplicationContext(), trailersList);
                        recyclerViewTrailers.setAdapter(adapterTrailers);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestTrailers);

        /**
         * Similar Movies
         */
        //RecyclerView Similar Movies
        similarLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSimilar = (RecyclerView) findViewById(R.id.recyclerViewSimilar);
        recyclerViewSimilar.setLayoutManager(similarLayoutManager);
        recyclerViewSimilar.setItemAnimator(new DefaultItemAnimator());
        //Request Similar movies
        String similarMovieRequest = API_URL + API_MOVIE + "/" + movieId + "/similar?api_key=" + API_KEY;
        StringRequest stringRequestSimilar = new StringRequest(Request.Method.GET, similarMovieRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(Similar): " + response);
                        try {
                            JSONObject parentObject= new JSONObject(response);
                            JSONArray parentArray = parentObject.getJSONArray("results");
                            for(int i=0;i<parentArray.length();i++){
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                Movie movieModel = new Movie();
                                movieModel.setSimilarId(finalObject.getString("id"));
                                movieModel.setSimilarPosterPath(Contract.API_IMAGE_URL + finalObject.getString("poster_path"));
                                similarMovieList.add(movieModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new SimilarAdapter(getApplicationContext(), similarMovieList);
                        recyclerViewSimilar.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestSimilar);


        /**
         * Casting
         */
        //RecyclerView Casting
        layoutManagerCasting = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCasting = (RecyclerView) findViewById(R.id.recyclerViewCasting);
        recyclerViewCasting.setLayoutManager(layoutManagerCasting);
        recyclerViewCasting.setItemAnimator(new DefaultItemAnimator());
        //Request Casting
        String castingMovieRequest = API_URL + API_MOVIE + "/" + movieId + "/credits?api_key=" + API_KEY;
        StringRequest stringRequestCasting = new StringRequest(Request.Method.GET, castingMovieRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(Credits): " + response);
                        try {
                            JSONObject parentObject= new JSONObject(response);
                            JSONArray parentArray = parentObject.getJSONArray("cast");
                            for(int i=0;i<parentArray.length();i++){
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                Movie movieModel = new Movie();
                                movieModel.setCastingId(finalObject.getString("id"));
                                movieModel.setCastingCharacter(finalObject.getString("character"));
                                movieModel.setCastingName(finalObject.getString("name"));
                                movieModel.setCastingProfilePath(Contract.API_IMAGE_URL + finalObject.getString("profile_path"));
                                castingList.add(movieModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterCasting = new CastingAdapter(getApplicationContext(), castingList);
                        recyclerViewCasting.setAdapter(adapterCasting);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestCasting);

    }

    /**
     * Parse and display the data from tmdb
     */
    private void parseAndDisplayData(String response) throws JSONException {
        //ToDo: Add this data for on persistent storage
        JSONObject parentObject= new JSONObject(response);
        Glide.with(getApplicationContext()).load(API_IMAGE_BASE_URL + API_IMAGE_SIZE_XXL + "/" + parentObject.getString("poster_path")).into((ImageView) findViewById(R.id.imageViewPoster));
        textViewOverview.setText(parentObject.getString("overview"));
        textViewTitle.setText(parentObject.getString("original_title"));
        textViewMovieTagline.setText(parentObject.getString("tagline"));
//        textViewTmdbVote.setText(parentObject.getString("vote_average"));

        //Send Request to imdb database using imdb Id
        StringRequest stringRequestImdb = new StringRequest(Request.Method.GET, OMDB_BASE_URL + parentObject.getString("imdb_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(IMDb): " + response);
                        try {
                            parseAndDisplayDataImdb(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestImdb);
    }

    /**
     * Parse and display the data from imdb
     */
    private void parseAndDisplayDataImdb(String response) throws JSONException {
        //ToDo: Add this data for on persistent storage
        JSONObject parentObject = new JSONObject(response);
        textViewMovieOrTvShow.setText(parentObject.getString("Type"));
        textViewYear.setText(parentObject.getString("Year"));
        textViewReleaseDateRuntime.setText("• " + parentObject.getString("Runtime") + " • " + parentObject.getString("Released") + " • " + parentObject.getString("Rated") + "\n\n• " +parentObject.getString("Genre"));
        textViewDirector.setText("Director: " + parentObject.getString("Director"));
        textViewCountry.setText( parentObject.getString("Country"));
        textViewVoteAverage.setText( parentObject.getString("imdbRating"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
