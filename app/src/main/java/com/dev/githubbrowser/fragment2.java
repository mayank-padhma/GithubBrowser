package com.dev.githubbrowser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dev.githubbrowser.model.Issue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment2 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Issue> issueList;
    RequestQueue rQueue;
    TextView noIssue, loadText;
    String owner, reponame;

    public fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        issueList = new ArrayList<>();
        noIssue = view.findViewById(R.id.noIssue);
        loadText = view.findViewById(R.id.loadText);

        //retreive data from shared preferneces
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        owner = sharedPreferences.getString("owner","");
        reponame = sharedPreferences.getString("reponame","");




        //accessing data from json

        rQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.github.com/repos/"+owner+"/"+reponame+"/issues";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() == 0){
                                noIssue.setVisibility(View.VISIBLE);
                                loadText.setVisibility(View.GONE);
                            }else{

                                for (int i = 0; i < response.length(); i++){
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String title = jsonObject.getString("title");
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("user");
                                    String login = jsonObject1.getString("login");
                                    String aurl = jsonObject1.getString("avatar_url");
                                    issueList.add(new Issue(title, login, aurl));

                                }

                                IAdapter adapter;
                                adapter = new IAdapter(issueList);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                loadText.setVisibility(View.GONE);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        rQueue.add(request);


        return view;
    }

}