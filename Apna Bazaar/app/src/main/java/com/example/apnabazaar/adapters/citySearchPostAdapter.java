package com.example.apnabazaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.auctionfolder.BiddingScreen;
import com.example.apnabazaar.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class citySearchPostAdapter extends RecyclerView.Adapter<citySearchPostAdapter.Holder> {
    Context context;
    List<Post> postList;
    private final int limit = 10;

    public citySearchPostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public citySearchPostAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_item_layout, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull citySearchPostAdapter.Holder holder, int position) {
        final String  postId = postList.get(position).getpId();
        final String pTitle = postList.get(position).getpTitle();

        final String pMinPrice = postList.get(position).getpMinPrice();
        final String pImage = postList.get(position).getpImage();
        final String pPlace = postList.get(position).getpCity();



        holder.title.setText(pTitle);
        holder.amt.setText(pMinPrice);
        holder.place.setText(pPlace);

        try {
            Picasso.get().load(pImage).into(holder.imageView);

        } catch (Exception e) {

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BiddingScreen.class);
                intent.putExtra("all", postId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (postList.size() > limit){
            return limit;
        }
        else {
            return postList.size();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, amt, place, time;

        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSimilar);
            title = itemView.findViewById(R.id.titleSimilarProduct);
            amt = itemView.findViewById(R.id.amountSimilarProduct);
            place = itemView.findViewById(R.id.placeSimilarProduct);
        }
    }
}
