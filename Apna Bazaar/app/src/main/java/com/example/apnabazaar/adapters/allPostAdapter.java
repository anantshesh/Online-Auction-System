package com.example.apnabazaar.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.auctionfolder.BiddingFuture;
import com.example.apnabazaar.auctionfolder.BiddingScreen;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.myfolder.confirmOrder;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class allPostAdapter extends RecyclerView.Adapter<allPostAdapter.Holder> {
    Context context;
    String mType;
    List<Post> postList;


    public allPostAdapter(Context context, String mType, List<Post> postList) {
        this.context = context;
        this.mType = mType;
        this.postList = postList;
    }

    @NonNull
    @Override
    public allPostAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allPostAdapter.Holder holder, int position) {

        final String postID = postList.get(position).getpId();

        final String uid = postList.get(position).getUid();
        final String uEmail = postList.get(position).getuEmail();
        final String uName = postList.get(position).getuName();
        final String uDp = postList.get(position).getuDp();

        final String pTitle = postList.get(position).getpTitle();
        final String pCity = postList.get(position).getpCity();
        //final String pDescription = postList.get(position).getpDesc();
        final String pQuantity = postList.get(position).getpQuantity();
        final String pMinPrice = postList.get(position).getpMinPrice();
        final String pImage = postList.get(position).getpImage();
        final String pTimeStamp = postList.get(position).getpTime();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);

        holder.pTimeTv.setText(pTime);
        holder.pTitleTv.setText(pTitle);
        //holder.pDescriptionTv.setText(pDescription);
        holder.pQuantityTv.setText(pQuantity);
        holder.pPriceTv.setText(pMinPrice);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mType.equals("future")){
                    Intent intent = new Intent(context, BiddingFuture.class);
                    intent.putExtra("future", postID);
                    context.startActivity(intent);

                }

                else if (mType.equals("all")){
                    Intent intent = new Intent(context, BiddingScreen.class);
                    intent.putExtra("all", postID);
                    context.startActivity(intent);

                }
                               else if (mType.equals("order")){
                    Intent intent = new Intent(context, confirmOrder.class);
                    intent.putExtra("order", postID);
                    context.startActivity(intent);
                }



            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BiddingFuture.class);
                intent.putExtra("future", postID);
                context.startActivity(intent);
            }
        });*/


        try {
            Picasso.get().load(pImage).into(holder.m_dp);

        } catch (Exception e) {

        }

        try {
            Picasso.get().load(uDp).into(holder.uPictureIv);

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView uPictureIv, m_dp;
        String email, uid;
        TextView uNameTv, pTimeTv, pTitleTv, pPriceTv, pDescriptionTv, pQuantityTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            m_dp = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pQuantityTv = itemView.findViewById(R.id.pQuantityTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            pPriceTv = itemView.findViewById(R.id.pPriceTv);
        }
    }
}
