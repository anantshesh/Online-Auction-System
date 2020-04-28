package com.example.apnabazaar.adapters;


import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.auctionfolder.AuctioneerBiddingScreen;
import com.example.apnabazaar.models.Post;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyHolder>  {




    //View mView;
    Context context;
    List<Post> postList;
    String mType;



//, View itemView
    public AdapterPost(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;

    }
    @NonNull
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

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

        /*holder.bBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, induidual_posts.class);
                intent1.putExtra("postId", postID);
                context.startActivity(intent1);
            }
        });
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AuctioneerBiddingScreen.class);
                intent.putExtra("my", postID);
                context.startActivity(intent);

            }
        });





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

    public class MyHolder extends RecyclerView.ViewHolder {

        Button bBid;
        ImageView uPictureIv, m_dp;
        String email, uid;
        TextView uNameTv, pTimeTv, pTitleTv, pPriceTv, pDescriptionTv, pQuantityTv;
        ImageButton moreButton;
        Button placeBidButton, shareButton;
        LinearLayout postLayout;
        RelativeLayout myLinearLayout;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            myLinearLayout =  itemView.findViewById(R.id.relative_layout_item);
            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            m_dp = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pQuantityTv = itemView.findViewById(R.id.pQuantityTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            pPriceTv = itemView.findViewById(R.id.pPriceTv);
            //moreButton = itemView.findViewById(R.id.moreButton);
            //placeBidButton = itemView.findViewById(R.id.placeBidButton);
            //shareButton = itemView.findViewById(R.id.shareButton);
            //postLayout = itemView.findViewById(R.id.postLayout);
            bBid = itemView.findViewById(R.id.bBid);
        }
    }


}
