package com.merakiphi.idiotbox.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.merakiphi.idiotbox.R;
import com.merakiphi.idiotbox.adapter.TvShowAdapter;
import com.merakiphi.idiotbox.model.TvShow;
import com.merakiphi.idiotbox.other.Contract;
import com.merakiphi.idiotbox.other.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.merakiphi.idiotbox.other.Contract.API_IMAGE_URL;
import static com.merakiphi.idiotbox.other.Contract.API_URL;

/**
 * Created by anuragmaravi on 02/02/17.
 */

public class TvShowFragmentPopular extends Fragment {
    View rootView;
    private String tvShowDetailsRequest;
    private ProgressBar progressBar;

    //Tv Shows
    private RecyclerView recyclerViewTvShows;
    private List<TvShow> tvShowsListPopular= new ArrayList<>();
    private  RecyclerView.Adapter adapterTvShows;
    private RecyclerView.LayoutManager layoutManagerTvShows;


    public TvShowFragmentPopular() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tvshow_popular, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.tv_show_accent), android.graphics.PorterDuff.Mode.MULTIPLY);


        /**
         * Popular Tv shows
         */
//        layoutManagerTvShows = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerViewTvShows = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShows);
//        recyclerViewTvShows.setLayoutManager(layoutManagerTvShows);
//
//        recyclerViewTvShows.setItemAnimator(new DefaultItemAnimator());
//        tvShowDetailsRequest = API_URL + Contract.API_TV + "/popular?api_key=" + Contract.API_KEY;
//        StringRequest stringRequestTvShowDetails = new StringRequest(Request.Method.GET, tvShowDetailsRequest,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i(TAG, "onResponse(TvShow Popular): " + response);
//                        try {
//                            JSONObject parentObject = new JSONObject(response);
//                            JSONArray parentArray = parentObject.getJSONArray("results");
//                            for (int i = 0; i < parentArray.length(); i++) {
//                                JSONObject finalObject = parentArray.getJSONObject(i);
//                                TvShow tvShow = new TvShow();
//                                tvShow.setTvShowName(finalObject.getString("original_name"));
//                                tvShow.setTvShowId(finalObject.getString("id"));
////                                tvShow.setTvShowFirstAirDate(finalObject.getString("first_air_date"));
////                                tvShow.setTvShowBackdropPath(finalObject.getString("backdrop_path"));
//                                tvShow.setTvShowPosterPath(API_IMAGE_URL + finalObject.getString("poster_path"));
//                                tvShowsListPopular.add(tvShow);
//
//                            }
//                            adapterTvShows = new TvShowAdapter(getContext(), tvShowsListPopular);
//                            recyclerViewTvShows.setAdapter(adapterTvShows);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
//            }
//        });
//        // Add the request to the RequestQueue.
//        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequestTvShowDetails);
//        // Inflate the layout for this fragment


        layoutManagerTvShows = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTvShows = (RecyclerView) rootView.findViewById(R.id.recyclerViewTvShows);
        recyclerViewTvShows.setLayoutManager(layoutManagerTvShows);

        recyclerViewTvShows.setItemAnimator(new DefaultItemAnimator());
        tvShowDetailsRequest = API_URL + Contract.API_TV + "/popular?api_key=" + Contract.API_KEY;
        if(tvShowsListPopular.size() == 0){
            StringRequest stringRequestTvShowDetails = new StringRequest(Request.Method.GET, tvShowDetailsRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Volley", "onResponse(TvShow Popular): " + response);
                        try {
                            JSONObject parentObject = new JSONObject(response);
                            JSONArray parentArray = parentObject.getJSONArray("results");
                            for (int i = 0; i < parentArray.length(); i++) {
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                TvShow tvShow = new TvShow();
                                tvShow.setTvShowName(finalObject.getString("original_name"));
                                tvShow.setTvShowId(finalObject.getString("id"));
//                                tvShow.setTvShowFirstAirDate(finalObject.getString("first_air_date"));
//                                tvShow.setTvShowBackdropPath(finalObject.getString("backdrop_path"));
                                tvShow.setTvShowPosterPath(API_IMAGE_URL + finalObject.getString("poster_path"));
                                tvShowsListPopular.add(tvShow);

                            }
                            adapterTvShows = new TvShowAdapter(getActivity(), tvShowsListPopular);
                            recyclerViewTvShows.setAdapter(adapterTvShows);
                            recyclerViewTvShows.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequestTvShowDetails);
        // Inflate the layout for this fragment
        }

        else{
            adapterTvShows = new TvShowAdapter(getActivity(), tvShowsListPopular);
            recyclerViewTvShows.setAdapter(adapterTvShows);
        }

        return rootView;
    }

}
