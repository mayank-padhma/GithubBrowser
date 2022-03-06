package com.dev.githubbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.githubbrowser.data.MyDbHandler;
import com.dev.githubbrowser.model.Repo;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class RepoDetails extends AppCompatActivity {

    TabLayout tabLayout;
    ImageView delete, eye;
    TabItem issueClick, branchClick;
    ViewPager viewPager;

    TextView dRepoName, dRepoDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        tabLayout = findViewById(R.id.tabLayout);
        delete = findViewById(R.id.delte);
        eye = findViewById(R.id.eye);
        dRepoDesc = findViewById(R.id.dRepoDesc);
        dRepoName = findViewById(R.id.dRepoName);
        issueClick = findViewById(R.id.issueClick);
        branchClick = findViewById(R.id.branchClick);
        viewPager = findViewById(R.id.viewpager);


        //view pager
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

       tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());

               if (tab.getPosition()==0 || tab.getPosition()==1){
                   pagerAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

       viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       //listen for scroll on pages


        //getintentextra

        Intent intent = getIntent();
        int ex = intent.getIntExtra("rid", -1);


        //getting repository details
        MyDbHandler db = new MyDbHandler(this);
        Repo repo = new Repo();
        repo = db.getFullRepo(ex);
        final int id = repo.getId();
        final String url = repo.getRepoUrl();
        final String owner = repo.getRepoOwner();
        final String reponame = repo.getRepoName();


        //shared preferences
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("owner", owner);
        editor.putString("reponame", reponame);
        editor.apply();

//        Toast.makeText(this, owner+" "+reponame, Toast.LENGTH_SHORT).show();


        //setting values before hand

        dRepoDesc.setText(repo.getRepoDesc());
        dRepoName.setText(repo.getRepoName());

        //fragments
//        fragment2 newFragment = new fragment2();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.viewpager, newFragment).commit();


        //action bar
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteRepoById(id);
                Intent intent = new Intent(RepoDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



    }
}