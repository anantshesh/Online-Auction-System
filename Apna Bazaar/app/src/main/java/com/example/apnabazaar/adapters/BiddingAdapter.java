package com.example.apnabazaar.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.apnabazaar.R;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Bids;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BiddingAdapter extends ArrayAdapter<Bids> {

    List<Bids> bid= new ArrayList<Bids>();
    private TextView username,bidValue, time;
    public BiddingAdapter(@NonNull Context context, List<Bids> bid) {
        super(context, R.layout.bidding_items, bid);
        this.bid=bid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView=convertView;
        if(customView==null){
            customView= LayoutInflater.from(getContext()).inflate(R.layout.bidding_items,parent,false);
        }
        Bids bid= getItem(position);
        username=(TextView) customView.findViewById(R.id.bidder_username);
        bidValue=(TextView) customView.findViewById(R.id.bid_value);
        time = customView.findViewById(R.id.Time);
        final String timestamp = bid.getTimestamp();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timestamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm:ss:mm aa", calendar);


        time.setText(pTime);
        bidValue.setText(bid.getBids());
        username.setText(bid.getBuName());
        return customView;
    }
}
