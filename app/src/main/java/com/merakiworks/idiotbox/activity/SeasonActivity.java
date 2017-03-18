package com.merakiworks.idiotbox.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.merakiworks.idiotbox.R;
import com.merakiworks.idiotbox.adapter.EpisodesAdapter;
import com.merakiworks.idiotbox.model.TvShow;
import com.merakiworks.idiotbox.other.Contract;
import com.merakiworks.idiotbox.other.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.merakiworks.idiotbox.other.Contract.API_IMAGE_BASE_URL;
import static com.merakiworks.idiotbox.other.Contract.API_IMAGE_SIZE_XXL;
import static com.merakiworks.idiotbox.other.Contract.API_URL;

/**
 * Created by anuragmaravi on 04/02/17.
 */

public class SeasonActivity extends AppCompatActivity {
    String TAG;
    String episodeId, seasonNumber, tvShowId, tvShowName;

    //to measure the width
    private LinearLayout linearLayout;
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

    //Episodes
    private RecyclerView recyclerViewEpisodes;
    private List<TvShow> listEpisodes= new ArrayList<>();
    private  RecyclerView.Adapter adapterEpisodes;
    private RecyclerView.LayoutManager layoutManagerEpisodes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        TAG = getClass().getSimpleName();
        tvShowName = getIntent().getStringExtra("tvshow_name");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Name");
        seasonNumber = getIntent().getStringExtra("season_number");
        tvShowId = getIntent().getStringExtra("tvshow_id");


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
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);


        //Method to show TVshow details
        displayTvShowEpisodes();

    }


    /**
     * Parse data for TV Show Details and show it.
     */
    private void displayTvShowEpisodes(){
        String seasonUrl = API_URL + Contract.API_TV + "/" + tvShowId + "/season/" + seasonNumber + "?api_key=" + Contract.API_KEY;
        StringRequest stringRequestEpisodes = new StringRequest(Request.Method.GET, seasonUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse(TvShow Season Details): " + response);
                        try {
                            JSONObject parentObject= new JSONObject(response);
                            Glide.with(getApplicationContext()).load(API_IMAGE_BASE_URL + API_IMAGE_SIZE_XXL + "/" + parentObject.getString("poster_path")).into((ImageView) findViewById(R.id.imageViewPoster));
                            textViewOverview.setText(parentObject.getString("overview"));
                            textViewTitle.setText(parentObject.getString("name"));
                            textViewMovieOrTvShow.setText(parentObject.getString("air_date"));
                            JSONArray parentArray = parentObject.getJSONArray("episodes");
                            for(int i=0;i<parentArray.length();i++) {
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                TvShow tvShow = new TvShow();
                                tvShow.setEpisodeId(finalObject.getString("id"));
                                tvShow.setEpisodeAirDate(finalObject.getString("air_date"));
                                tvShow.setEpisodeNumber(finalObject.getString("episode_number"));
                                tvShow.setEpisodeName(finalObject.getString("name"));
                                tvShow.setEpisodeOverview(finalObject.getString("overview"));
                                tvShow.seteSeasonNumber(finalObject.getString("season_number"));
                                tvShow.seteTvShowId(tvShowId);
                                tvShow.setEpisodeStillPath(Contract.API_IMAGE_URL + finalObject.getString("still_path"));
                                listEpisodes.add(tvShow);
                            }
                            layoutManagerEpisodes = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerViewEpisodes = (RecyclerView) findViewById(R.id.recyclerViewEpisodes);
                            recyclerViewEpisodes.setLayoutManager(layoutManagerEpisodes);
                            recyclerViewEpisodes.setItemAnimator(new DefaultItemAnimator());
                            adapterEpisodes = new EpisodesAdapter(getApplicationContext(), listEpisodes);
                            recyclerViewEpisodes.setNestedScrollingEnabled(false);
                            recyclerViewEpisodes.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                            recyclerViewEpisodes.setAdapter(adapterEpisodes);

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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestEpisodes);

    }
}
