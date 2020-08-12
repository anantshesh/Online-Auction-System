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

public class horizontalProductAdapter extends RecyclerView.Adapter<horizontalProductAdapter.Holder> {

    Context context;
    private List<Post> posts;
    private final int limit = 10;

    public horizontalProductAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }



    @NonNull
    @Override
    public horizontalProductAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull horizontalProductAdapter.Holder holder, int position) {


        final String postId = posts.get(position).getpId();
        final String pTitle = posts.get(position).getpTitle();
        final String pMinPrice = posts.get(position).getpMinPrice();
        final String pPlace = posts.get(position).getpCity();
        final String pImage = posts.get(position).getpImage();

        //holder.setIsRecyclable(false);
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
        if (posts.size() > limit){
            return limit;
        }
        else {
            return posts.size();
        }

    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, amt, place;
        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSimilar);
            title = itemView.findViewById(R.id.titleSimilarProduct);
            amt = itemView.findViewById(R.id.amountSimilarProduct);
            place = itemView.findViewById(R.id.placeSimilarProduct);
        }



    }
}
