package com.merakiphi.idiotbox.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.merakiphi.idiotbox.R;
import com.merakiphi.idiotbox.adapter.CastImageAdapter;
import com.merakiphi.idiotbox.model.Movie;
import com.merakiphi.idiotbox.other.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.merakiphi.idiotbox.other.Contract.API_CASTING;
import static com.merakiphi.idiotbox.other.Contract.API_KEY;
import static com.merakiphi.idiotbox.other.Contract.API_URL;

public class CastImageActivity extends AppCompatActivity {
    private List<Movie> castingList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Request Cast Images
        String castingImagesRequest = API_URL + API_CASTING + "/" + getIntent().getStringExtra("profileId") + "/images?api_key=" + API_KEY;
        StringRequest stringRequestCastingImages = new StringRequest(Request.Method.GET, castingImagesRequest,
                new Response.Listener<String>() {
                    JSONObject parentObject;
                    @Override
                    public void onResponse(String response) {
                        try {
                            parentObject= new JSONObject(response);
                            JSONArray parentArray = parentObject.getJSONArray("profiles");
                            for(int i=0;i<parentArray.length();i++){
                                JSONObject finalObject = parentArray.getJSONObject(i);
                                Movie movieModel = new Movie();
                                movieModel.setCastingProfilePath(finalObject.getString("file_path"));
                                castingList.add(movieModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CastImageAdapter adapter = new CastImageAdapter(getApplicationContext(), castingList);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequestCastingImages);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
