package com.dev.githubbrowser;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dev.githubbrowser.data.MyDbHandler;
import com.dev.githubbrowser.model.Repo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AddRepo extends AppCompatActivity {

    EditText organization, reponame;
    Button addNew;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        organization = findViewById(R.id.organization);
        reponame = findViewById(R.id.repoName);
        addNew = findViewById(R.id.addNew);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(myToolbar);
        MyDbHandler db = new MyDbHandler(AddRepo.this);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vis();
                //*********************************************//



                if (organization.getText().toString().matches("")){
                    vis();
                    Toast.makeText(AddRepo.this, "Please enter an organization name", Toast.LENGTH_SHORT).show();
                }
                else if (reponame.getText().toString().matches("")){
                    vis();
                    Toast.makeText(AddRepo.this, "Please enter a Repository name", Toast.LENGTH_SHORT).show();
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(AddRepo.this);
                    String url = "https://api.github.com/repos/" + organization.getText() + "/" + reponame.getText();

// Request a string response from the provided URL.
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    String nameId = "";
                                    String owner = "";
                                    String desc = "";
                                    String html_url = "";
                                    try {
                                        nameId = response.getString("name");
                                        desc = response.getString("description");
                                        html_url = response.getString("html_url");
                                        JSONObject owner1 = response.getJSONObject("owner");
                                        owner = owner1.getString("login");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    boolean res = true;

                                    List<Repo> allRepos = db.getAllRepos();
                                    for (Repo repo : allRepos) {
                                        if (repo.getRepoUrl().matches(html_url)) {
                                            res = false;
                                            break;
                                        }
                                    }

                                    if (res){
                                            Repo newRepo = new Repo();
                                            newRepo.setRepoOwner(owner);
                                            newRepo.setRepoName(nameId);
                                            newRepo.setRepoDesc(desc);
                                            newRepo.setRepoUrl(html_url);
                                            db.addRepo(newRepo);
                                            Toast.makeText(AddRepo.this, "Item added", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddRepo.this, MainActivity.class);
                                            startActivity(intent);
                                    }else{
                                            Toast.makeText(AddRepo.this, "This repository already exists in your database", Toast.LENGTH_SHORT).show();

                                    }

                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            vis();
                            Toast.makeText(AddRepo.this, "Please enter correct details", Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(request);
                }


                //*********************************************//





            }
        });

    }
    public void vis(){
        if (addNew.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.VISIBLE);
            addNew.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            addNew.setVisibility(View.VISIBLE);
        }
    }


}
