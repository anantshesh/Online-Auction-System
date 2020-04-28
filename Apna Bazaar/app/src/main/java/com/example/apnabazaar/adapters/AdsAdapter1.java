package com.example.apnabazaar.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apnabazaar.R;
import com.example.apnabazaar.auctionfolder.AuctioneerBiddingScreen;
import com.example.apnabazaar.auctionfolder.BiddingFuture;
import com.example.apnabazaar.auctionfolder.BiddingScreen;
import com.example.apnabazaar.layout.order_history;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.winner;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdsAdapter1 extends ArrayAdapter{
    private TextView title;
    private TextView amount;
    private TextView description;
    private TextView time;
    private TextView quantity;
    private ImageView image;
    private List<winner> winners;
    private Context mContext;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference = null;
    private String mType;
    private RelativeLayout myLinearLayout;

    public AdsAdapter1(@NonNull Context context, List<winner> winners, String type) {
        super(context, R.layout.row_posts, winners);
        this.winners = winners;
        mContext = context;
        mType = type;
    }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View customView = convertView;
        if (customView == null) {
            customView = LayoutInflater.from(getContext()).inflate(R.layout.row_posts, parent, false);
        }

        winner winners = (winner) getItem(position);
        final String postId = winners.getWpId();

        title = (TextView) customView.findViewById(R.id.pTitleTv);
        amount = (TextView) customView.findViewById(R.id.pPriceTv);
        quantity = (TextView) customView.findViewById(R.id.pQuantityTv);
        time = (TextView) customView.findViewById(R.id.pTimeTv);
        //description = (TextView) customView.findViewById(R.id.description_of_ad);
        image = (ImageView) customView.findViewById(R.id.pImageIv);
        myLinearLayout = (RelativeLayout) customView.findViewById(R.id.relative_layout_item);


        myLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("checkff coming onClick, type="+mType);
                if (mType.equals("won")) {
                    Post sendPost = (Post) getItem(position);
                    Intent i = new Intent(mContext, order_history.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("send post", sendPost);
                    //check again later - i.putExtra("send post", sendPost);
                    mContext.startActivity(i);
                    ((Activity)mContext).finish();


                    return;
                }

            }
        });



        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType.equals("won")) {
                    Post sendPost = (Post) getItem(position);
                    // String id = sendPost.getpId();
                    Intent i = new Intent(mContext, order_history.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("send post", sendPost);

                    mContext.startActivity(i);
                    ((Activity)mContext).finish();
                    return;
                }


            }
        });


        title.setText(winners.getWpTitle());
        //reference = storage.getReferenceFromUrl(post.getpImage());
        amount.setText(winners.getWpMinPrice());
        quantity.setText(winners.getWpQuantity());


        String pTimeStamp = winners.getWpTime();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));

        String pTime = (String) DateFormat.format("dd/MM/yyyy hh:mm aa", calendar);
        time.setText(pTime);
        String pImage = winners.getWpImage();
        try{
            Picasso.get().load(pImage).into(image);
        }catch (Exception e){

        }
        // Glide.with(mContext).using(new FirebaseImageLoader()).load(reference).into(image);
        return customView;
    }
}
