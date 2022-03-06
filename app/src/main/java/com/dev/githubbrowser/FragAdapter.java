package com.dev.githubbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.githubbrowser.model.Commit;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragAdapter extends RecyclerView.Adapter<FragAdapter.myviewholder>{
    ArrayList<Commit> Commits;
    Context context;

    public FragAdapter(ArrayList<Commit> Commits) {
        this.Commits = Commits;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commitslayout,parent, false);
        context = parent.getContext();
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.date.setText(Commits.get(position).getDate());
        holder.username.setText(Commits.get(position).getUsername());
        holder.commitMsg.setText(Commits.get(position).getMessage());
        holder.id.setText(Commits.get(position).getId());
        String urlLink = Commits.get(position).getAurl();
        Glide.with(context).load(urlLink).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return Commits.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView date, id, commitMsg, username;
        CircleImageView avatar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            commitMsg = itemView.findViewById(R.id.commitMsg);
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
