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
import com.example.apnabazaar.models.bidhistoryModel;
import com.example.apnabazaar.myfolder.bidHistoryDetail;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class bidhistoryAdapter extends RecyclerView.Adapter<bidhistoryAdapter.MyHolder>{

    Context context;
    List<bidhistoryModel> bidhistoryModels;

    public bidhistoryAdapter(Context context, List<bidhistoryModel> bidhistoryModels) {
        this.context = context;
        this.bidhistoryModels = bidhistoryModels;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final String postID = bidhistoryModels.get(position).getBhpId();

        final String uid = bidhistoryModels.get(position).getBhuid();
        final String uEmail = bidhistoryModels.get(position).getBhuEmail();
        final String uName = bidhistoryModels.get(position).getBhuName();
        final String uDp = bidhistoryModels.get(position).getBhuDp();

        final String pTitle = bidhistoryModels.get(position).getBhpTitle();
        final String pCity = bidhistoryModels.get(position).getBhpCity();
        //final String pDescription = bidhistoryModels.get(position).getpDesc();
        final String pQuantity = bidhistoryModels.get(position).getBhpQuantity();
        final String pMinPrice = bidhistoryModels.get(position).getBhpMinPrice();
        final String pImage = bidhistoryModels.get(position).getBhpImage();
        final String pDuration = bidhistoryModels.get(position).getBhpduration();
        final String pTimeStamp = bidhistoryModels.get(position).getBhpTime();


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);

        //String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
        //holder.uNameTv.setText(uName);
        holder.pTimeTv.setText(pTime);
        holder.pTitleTv.setText(pTitle);
        //holder.pDescriptionTv.setText(pDescription);
        holder.pQuantityTv.setText(pQuantity);
        holder.pPriceTv.setText(pMinPrice);


       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, bidHistoryDetail.class);
                intent.putExtra("posthis", postID);
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
        return bidhistoryModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
