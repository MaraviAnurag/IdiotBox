package com.merakiphi.idiotbox.other;

import android.content.Context;

import com.merakiphi.idiotbox.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuragmaravi on 28/01/17.
 */

public class ParseJSON {
    private Context context;
    static List<Movie> movieModelList = new ArrayList<>();

    public ParseJSON(Context context){
        this.context = context;
    }

    public  List<Movie> parseMovieResponse(String response) throws JSONException {
        JSONObject parentObject= new JSONObject(response);
        parentObject.getInt("page");
        JSONArray parentArray = parentObject.getJSONArray("results");
        for(int i=0;i<parentArray.length();i++){
            JSONObject finalObject = parentArray.getJSONObject(i);
            Movie movieModel = new Movie();
//            movieModel.setAdult(finalObject.getBoolean("adult"));
//            movieModel.setOverview(finalObject.getString("overview"));
//            movieModel.setReleaseDate(finalObject.getString("release_date"));
//            movieModel.setGenreIds(finalObject.getJSONArray("genre_ids"));
            movieModel.setId(finalObject.getString("id"));
            movieModel.setTitle(finalObject.getString("original_title"));
            movieModel.setPosterPath(Contract.API_IMAGE_URL + finalObject.getString("poster_path"));
//            movieModel.setLanguage(finalObject.getString("original_language"));
//            movieModel.setBackdropPath(Contract.API_IMAGE_URL + finalObject.getString("backdrop_path"));
//            movieModel.setPopularity(finalObject.getString("popularity"));
//            movieModel.setVoteCount(finalObject.getString("vote_count"));
            movieModel.setVoteAverage(finalObject.getString("vote_average"));

            movieModelList.add(movieModel);
        }
        return movieModelList;
    }



}
