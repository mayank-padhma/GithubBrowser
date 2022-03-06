package com.dev.githubbrowser;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.githubbrowser.data.MyDbHandler;
import com.dev.githubbrowser.model.Repo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView addRepo;
    Button addButton;
    RecyclerView recyclerView;
    AdapterHome adapter;
    LinearLayout ll1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        addRepo = findViewById(R.id.addRepo);
        addButton = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.listofrepos);
        ll1 = findViewById(R.id.ll1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        setSupportActionBar(myToolbar);



        //mydbhandler

        MyDbHandler db = new MyDbHandler(MainActivity.this);
        List<Repo> allRepos = db.getAllRepos();

        adapter = new AdapterHome(this, allRepos);
        recyclerView.setAdapter(adapter);


        if (adapter.getItemCount()!=0){
            recyclerView.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.INVISIBLE);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
            ll1.setVisibility(View.VISIBLE);
        }



        addRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRepo.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRepo.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}