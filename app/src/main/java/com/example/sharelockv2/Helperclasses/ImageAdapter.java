package com.example.sharelockv2.Helperclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharelockv2.Helperclasses.Model;
import com.example.sharelockv2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{   Context nContext;
    List<Model> uploads;



    public ImageAdapter(Context context, List<Model> uploads ){
        nContext = context;
        this.uploads = uploads;

    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(nContext).inflate(R.layout.anzeige,parent,false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Model currentUpload = uploads.get(position);
        holder.title.setText(currentUpload.getTitle());
        holder.username.setText(currentUpload.getUsername());
        holder.date.setText(currentUpload.getDate());
        Picasso.with(nContext).load(currentUpload.getImageUrl()).fit().centerCrop().into(holder.imageView);
        if (currentUpload.getType() == 1){
            holder.angebotOderNachfrage.setText("Helfen!");
        }
        else if (currentUpload.getType() == 2 ){
            holder.angebotOderNachfrage.setText("Annhemen!");
        }
        else{
            holder.angebotOderNachfrage.setText("Stefan hat einen Fehler gemacht");
        }
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder  {
        public TextView title,username,date;
        public ImageView imageView;
        public Button angebotOderNachfrage;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.bild);
            username = itemView.findViewById(R.id.ersteller);
            date = itemView.findViewById(R.id.date);
            angebotOderNachfrage = itemView.findViewById(R.id.AngebotNachfragePost);



        }


    }

}