package com.example.apnabazaar.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.models.Post;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class citySearchPostAdapter extends RecyclerView.Adapter<citySearchPostAdapter.Holder> {
    Context context;
    List<Post> postList;
    private final int limit = 0;

    public citySearchPostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public citySearchPostAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_search_item, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull citySearchPostAdapter.Holder holder, int position) {
        final String  postID = postList.get(position).getpId();
        final String pTitle = postList.get(position).getpTitle();
        final String pCity = postList.get(position).getpCity();
        final String pMinPrice = postList.get(position).getpMinPrice();
        final String pImage = postList.get(position).getpImage();
        final String pTimeStamp = postList.get(position).getpTime();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);

        holder.time.setText(pTime);
        holder.title.setText(pTitle);
        holder.amt.setText(pMinPrice);
        holder.place.setText(pCity);


        try {
            Picasso.get().load(pImage).into(holder.imageView);

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {

            return postList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, amt, place, time;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.CImageIv);
            title = itemView.findViewById(R.id.CTitleTv);
            amt = itemView.findViewById(R.id.CPriceTv);
            place = itemView.findViewById(R.id.CCity);
            time = itemView.findViewById(R.id.CTimeTv);
        }
    }
}
