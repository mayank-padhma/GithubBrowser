package com.dev.githubbrowser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.githubbrowser.model.Repo;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    Context context;
    List<Repo> repos;

    public AdapterHome(Context context, List<Repo> repos){
        this.context = context;
        this.repos = repos;
    }

    @NonNull
    @Override
    public AdapterHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repolistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.ViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.rname.setText(repo.getRepoName());
        holder.rdesc.setText(repo.getRepoDesc());

        holder.rshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, repo.getRepoName() + "\n" + repo.getRepoDesc() + "\n" + repo.getRepoUrl());
                shareIntent.setType("text/plain");
                context.startActivity(shareIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rname, rdesc;
        public ImageView rshare;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            rname = itemView.findViewById(R.id.rname);
            rdesc = itemView.findViewById(R.id.rdesc);
            rshare = itemView.findViewById(R.id.rshare);
        }
        @Override
        public void onClick(View view){
            int position = this.getAdapterPosition();
            Intent intent = new Intent(context, RepoDetails.class);
            intent.putExtra("rid", position);
            context.startActivity(intent);
        }
    }

}
