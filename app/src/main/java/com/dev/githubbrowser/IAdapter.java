package com.dev.githubbrowser;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dev.githubbrowser.model.Issue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IAdapter extends RecyclerView.Adapter<IAdapter.myviewholder>{
    ArrayList<Issue> issues;
    Context context;
    public IAdapter(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issuelayout,parent, false);
        context = parent.getContext();
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.issueTitle.setText(issues.get(position).getTitle());
        holder.issueCreator.setText(issues.get(position).getCname());
        String urlLink = issues.get(position).getImgUrl();
        Glide.with(context).load(urlLink).into(holder.issueAvatar);
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView issueTitle, issueCreator;
        CircleImageView issueAvatar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            issueAvatar = itemView.findViewById(R.id.issueAvatar);
            issueCreator = itemView.findViewById(R.id.issueCreator);
            issueTitle = itemView.findViewById(R.id.issueTitle);
            issueAvatar = itemView.findViewById(R.id.issueAvatar);
        }
    }
}
