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
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Bids;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterBid1 extends RecyclerView.Adapter<AdapterBid1.MyHolder> {

    Context context;
    List<Bids> bidList;

    public AdapterBid1(Context context, List<Bids> bidList) {
        this.context = context;
        this.bidList = bidList;
    }

    @NonNull
    @Override
    public AdapterBid1.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bidders_for_auctioneers, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBid1.MyHolder holder, int position) {
        final  String uName = bidList.get(position).getBuName();

        final  String Bids = bidList.get(position).getBids();
        final  String uEmail = bidList.get(position).getBuEmail();
        final  String  uid = bidList.get(position).getBuid();
        final  String timestamp = bidList.get(position).getTimestamp();
        final  String uphone = bidList.get(position).getBphone();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm:ss:mm aa", calendar);


        holder.bNameTv.setText(uName);
        holder.bTimeTv.setText(pTime);
        holder.bPriceTv.setText(Bids);
        holder.bPhone.setText(uphone);
        holder.bEmail.setText(uEmail);


    }

    @Override
    public int getItemCount() {
        return bidList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView bNameTv,bPriceTv, bTimeTv, bEmail, bPhone;
        ImageView bPictureIv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            bNameTv = itemView.findViewById(R.id.bNameTv);
            bPriceTv = itemView.findViewById(R.id.bPriceTv);
            bTimeTv = itemView.findViewById(R.id.bTimeTv);
            bEmail = itemView.findViewById(R.id.email);
            bPhone = itemView.findViewById(R.id.phone);
        }
    }
}
