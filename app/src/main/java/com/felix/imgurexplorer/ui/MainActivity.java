package com.felix.imgurexplorer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.adapter.PhotoAdapter;
import com.felix.imgurexplorer.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE = "MOVIE";

    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Photo> mPhotoList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPhotoList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(1, "cats");
    }

    public void parseJSON(int pageNumber, String queryString) {
        String url = "https://api.imgur.com/3/gallery/search/time/" + pageNumber + "?q=" + queryString;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d(TAG, "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String title = data.getString("title");
                                String id;
                                if (data.getBoolean("is_album")) {
                                    id = data.getString("cover");
                                } else {
                                    id = data.getString("id");
                                }

                                mPhotoList.add(new Photo(title, id));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPhotoAdapter = new PhotoAdapter(MainActivity.this, mPhotoList);
                                    mRecyclerView.setAdapter(mPhotoAdapter);
                                }
                            });
                            mPhotoAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Client-ID 126701cd8332f32");
                return headers;
            }
        };

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        Photo clickedPhoto = mPhotoList.get(position);
        intent.putExtra(MOVIE, clickedPhoto);
        startActivity(intent);
    }
}
