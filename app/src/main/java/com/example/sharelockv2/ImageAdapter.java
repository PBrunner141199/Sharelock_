package com.example.sharelockv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharelockv2.Helperclasses.Model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context mContext;
    List<Model> mUploads;

    public ImageAdapter(Class<Frag1> marketplaceActivity_class, List<Model> uploads) {
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_frag1, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Model currentUpload = mUploads.get(position);
        holder.button.setText( currentUpload.getTitle());
        Picasso.with(mContext).load(currentUpload.getImageUrl()).centerCrop().fit().into(holder.iButton);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        Button button;
        ImageButton iButton;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            iButton = itemView.findViewById(R.id.picbutton);
            button = itemView.findViewById(R.id.titlebutton);
        }
    }
    }

