package com.dev.githubbrowser;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dev.githubbrowser.model.Commit;
import com.dev.githubbrowser.model.Issue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Commits extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Commit> commitList;
    RequestQueue rQueue;
    TextView loadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commitList = new ArrayList<>();
        loadText = findViewById(R.id.loadText);


        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //getting extras
        String branchName = getIntent().getStringExtra("branchName");

        myToolbar.setSubtitle(branchName);


        //retreive data from shared preferneces
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String owner = sharedPreferences.getString("owner","");
        String reponame = sharedPreferences.getString("reponame","");




        rQueue = Volley.newRequestQueue(this);
         String url = "https://api.github.com/repos/"+owner+"/"+reponame+"/commits?sha="+branchName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0 ; i< response.length() ; i++){

                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONObject jsonObjectS = jsonObject.getJSONObject("commit");
                                JSONObject jsonObject1 = jsonObjectS.getJSONObject("author");
                                String date = jsonObject1.getString("date");
                                date = date.substring(0,10);
                                String message = jsonObjectS.getString("message");
                                JSONObject jsonObject2 = jsonObject.getJSONObject("author");
                                String username = jsonObject2.getString("login");
                                String aurl = jsonObject2.getString("avatar_url");
                                int rawId = jsonObject2.getInt("id");
                                String id = String.valueOf(rawId);


                            commitList.add(new Commit(date, aurl, username, message, id));
                            }


                                        FragAdapter adapter;
                                        adapter = new FragAdapter(commitList);
                                        recyclerView.setAdapter(adapter);
                                        vis();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Commits.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        rQueue.add(request);
    }

    public void vis(){
        if (recyclerView.getVisibility() == View.GONE){
            recyclerView.setVisibility(View.VISIBLE);
            loadText.setVisibility(View.INVISIBLE);
        }
    }


}