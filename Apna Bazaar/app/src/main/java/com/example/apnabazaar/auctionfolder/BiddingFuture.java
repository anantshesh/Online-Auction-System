package com.example.apnabazaar.auctionfolder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.BiddingAdapter;
import com.example.apnabazaar.adapters.horizontalProductAdapter;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.Ratings;
import com.example.apnabazaar.models.Won;
import com.example.apnabazaar.myfolder.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BiddingFuture extends AppCompatActivity {

    private Post setPost;


    RecyclerView recyclerView;
    private TextView title, description, hours, mins, sec, initialBid, quantity, place,uname ;
    private ImageView postImage, udp;
    private ListView myListView;
    private java.text.SimpleDateFormat mdformat;
    private Date startDate, endDate;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference = null;
    private long elapsedMinutes, elapsedHours, elapsedSeconds;
    private BiddingAdapter myadapter;

    //private AdapterBid adapterBid;
    private List<Bids> bids = new ArrayList<Bids>();
    private List<Post> posts= new ArrayList<Post>();

    private LinearLayout linearLayout;
    private DatabaseReference bidReference;
    private ChildEventListener childEventListener;

    private Button bidButton;
    private EditText bidEt;
    ProgressDialog pd;
    String myUid, postId, myEmail, myName, minAmt ,myPhone, myDp;
    String hisName, bidamount,  hisUid, hisEmail, pduration, startdatetime, hisdp,stitle,desc, amt, location,squantity,pimage;

    TextView  ratingText, totalCustomers ,count1;
    RatingBar ratingBar;

    private Boolean isImageFitToScreen = false;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    // private RecyclerView recyclerView;
    Bundle bundle;
    long diff = 0;
    private boolean onDestroyFlag = false;
    String postID;
    List<Bid> checkWinnerBids = new ArrayList<Bid>();

    private horizontalProductAdapter horizontalProductAdapter;
    private TextView layoutTitle, layoutTitle1;
    RecyclerView  mostViewRecycler;
    Date currentDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding_future);

        Intent i = getIntent();
        setPost = (Post) getIntent().getSerializableExtra("send post");

        Intent intent = getIntent();
        postId = intent.getStringExtra("future");

        title = findViewById(R.id.pTitleTv);
        description = findViewById(R.id.pDescriptionTv);
        quantity = findViewById(R.id.pQuantityTv);
        place = findViewById(R.id.pLocationTv);
        uname = findViewById(R.id.uNameTv);
        udp = findViewById(R.id.uPictureIv);
        initialBid = findViewById(R.id.pPriceTv);
        hours = findViewById(R.id.hours_bidding_screen);
        mins = findViewById(R.id.mins_bidding_screen);
        sec  = findViewById(R.id.sec_bidding_screen);
        postImage = findViewById(R.id.pImageIv1);

        ratingBar = findViewById(R.id.sellerRating);
        ratingText = findViewById(R.id.ratingText);
        totalCustomers = findViewById(R.id.total);
        //recyclerView = findViewById(R.id.bidderRecycler);
       // myListView = findViewById(R.id.listview_bidding_screen);



//        linearLayout.setVisibility(View.GONE);

        myadapter = new BiddingAdapter(BiddingFuture.this, bids);
//        myListView.setAdapter(myadapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
       // bidReference = FirebaseDatabase.getInstance().getReference().child("Bids");



        mdformat = new java.text.SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        bidReference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = bidReference.orderByChild("pId").equalTo(postId);

        //Post details

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    hisUid = "" +ds.child("uid").getValue();
                    hisEmail = "" +ds.child("uEmail").getValue();
                    hisName = ""+ ds.child("uName").getValue();
                    hisdp = ""+ ds.child("uDp").getValue();
                    stitle = ""+ ds.child("pTitle").getValue();
                    desc = ""+ ds.child("pDesc").getValue();
                    amt = ""+ ds.child("pMinPrice").getValue();
                    location = ""+ ds.child("pCity").getValue();
                    squantity = ""+ ds.child("pQuantity").getValue();
                    pimage = ""+ ds.child("pImage").getValue();
                    pduration = "" + ds.child("pduration").getValue();
                    startdatetime = "" + ds.child("aDateTime").getValue();

                    uname.setText(hisName);
                    title.setText(stitle);
                    description.setText(desc);
                    initialBid.setText(amt);
                    place.setText(location);
                    quantity.setText(squantity);


                    try {
                        startDate = mdformat.parse(startdatetime);
                        endDate = mdformat.parse(pduration);
                    }
                    catch (Exception c) {
                        c.printStackTrace();
                    }
                    System.out.println("checkff end=" + endDate);
                    System.out.println("checkff start=" + startDate);
                    showTimer(startDate, endDate);

                    try{
                        Picasso.get().load(hisdp).into(udp);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(udp);
                    }

                    try{
                        Picasso.get().load(pimage).into(postImage);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(postImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        layoutTitle1 = findViewById(R.id.MostViewedTitle);
        mostViewRecycler = findViewById(R.id.mostViewed);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(BiddingFuture.this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager1.setStackFromEnd(false);
        layoutManager1.setReverseLayout(false);
        mostViewRecycler.setLayoutManager(layoutManager1);
        DatabaseReference postRef1 = FirebaseDatabase.getInstance().getReference("Posts");
        postRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Post check1 = dataSnapshot1.getValue(Post.class);


                    if (Integer.valueOf(check1.getCount())>5 ){
                        Calendar calendar = Calendar.getInstance();
                        Date enddate = null, startDate = null;
                        try{
                            currentDate = mdformat.parse(mdformat.format(calendar.getTime()));
                            enddate = mdformat.parse(check1.getPduration());
                            startDate = mdformat.parse(check1.getaDateTime());

                        }catch (Exception e ){
                            e.printStackTrace();
                        }


                        if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(enddate) == -1){

                            // posts.clear();
                            posts.add(check1);
                            horizontalProductAdapter = new horizontalProductAdapter(BiddingFuture.this, posts);
                            mostViewRecycler.setAdapter(horizontalProductAdapter);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("Ratings");
        ratingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int ratingSum = 0;
                float ratingtotal = 0;
                float ratingAvg = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Ratings ratings = ds.getValue(Ratings.class);
                    if (ratings.getpUid().equals(hisUid)) {
                        ratingSum = ratingSum + Integer.valueOf(ratings.getRating());
                        ratingtotal++;
                    }
                }
                if (ratingtotal != 0){
                    ratingAvg = ratingSum/ratingtotal;
                    ratingBar.setRating(ratingAvg);
                    ratingText.setText(Float.toString(ratingAvg));
                    totalCustomers.setText(Float.toString(ratingtotal));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen){
                    isImageFitToScreen = false;
                    postImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    postImage.setAdjustViewBounds(true);
                    isImageFitToScreen = false;
                }
                else {
                    isImageFitToScreen=true;
                    postImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    postImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    isImageFitToScreen = true;
                }

            }
        });

    }

    private void showTimer(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        Date currentdate = null;
        try {
            currentdate = mdformat.parse(mdformat.format(cal.getTime()));
        } catch (Exception c) {
            c.printStackTrace();
        }
        System.out.println("checkff end= " + startDate + " current= " + currentdate);
        diff = startDate.getTime() - currentdate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        System.out.println("Checkff difference" + diff);

        long elapsedDays = diff / daysInMilli;
        diff = diff % daysInMilli;
        System.out.println("Checkff difference=" + diff + " days=" + elapsedDays);
        elapsedHours = diff / hoursInMilli;
        diff = diff % hoursInMilli;
        System.out.println("Checkff difference=" + diff + " hours=" + elapsedHours);
        elapsedMinutes = diff / minutesInMilli;
        diff = diff % minutesInMilli;
        System.out.println("Checkff difference=" + diff + " mins=" + elapsedMinutes);
        elapsedSeconds = diff / secondsInMilli;
        System.out.println("Checkff difference=" + diff + " sec=" + elapsedSeconds);

        hours.setText(String.valueOf(elapsedHours));
        mins.setText(String.valueOf(elapsedMinutes));
        final CountDownTimer mCountDown = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long l) {
                sec.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                sec.setText("0");
                if (elapsedMinutes == 0) {
                    if (elapsedHours == 0) {
                        Toast.makeText(BiddingFuture.this, "Bidding time ends", Toast.LENGTH_SHORT).show();
                        Query query = databaseReference;
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                checkWinnerBids.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Bid winnerBid = snapshot.getValue(Bid.class);
                                    if (winnerBid.getpId().equals(setPost.getpId())) {
                                        checkWinnerBids.add(winnerBid);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        if (checkWinnerBids.size() > 0) {
                            Bid max = checkWinnerBids.get(0);
                            for (int i = 0; i < checkWinnerBids.size(); i++) {
                                if (Long.parseLong(max.getBids()) < Long.parseLong(checkWinnerBids.get(i).getBids())) {
                                    max = checkWinnerBids.get(i);
                                }
                            }
                            if(onDestroyFlag==false) {
                                DatabaseReference wonAuctionsRef = FirebaseDatabase.getInstance().getReference("Posts").child(setPost.getpId()).child("Bidders");
                                Won wonAuction = new Won(max.getuName(), setPost.getpId());
                                wonAuctionsRef.push().setValue(wonAuction);
                                System.out.println("checkff setValue(Won) bidding screen");
                            }
                        }
                        Intent i = new Intent(BiddingFuture.this, MainActivity.class);
                        i.putExtra("type", "Bidder");
                        startActivity(i);
                        finish();
                        return;
                    } else {
                        --elapsedHours;
                        elapsedMinutes = 59;
                    }
                } else
                    --elapsedMinutes;
                hours.setText(String.valueOf(elapsedHours));
                mins.setText(String.valueOf(elapsedMinutes));
                show2ndTimer();
            }
        }.start();
    }

    private void show2ndTimer() {
        final CountDownTimer mCountDown = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                sec.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                sec.setText("0");
                if (elapsedMinutes == 0) {
                    if (elapsedHours == 0) {
                        Toast.makeText(BiddingFuture.this, "Bidding time ends", Toast.LENGTH_SHORT).show();
                       /* Query query = databaseReference;
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<Bid> checkWinnerBids = new ArrayList<Bid>();
                                int count = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Bid winnerBid = snapshot.getValue(Bid.class);
                                    if (winnerBid.getpId().equals(setPost.getpId())) {
                                        checkWinnerBids.add(winnerBid);
                                    }
                                }
                                if (checkWinnerBids.size() > 0) {
                                    Bid max = checkWinnerBids.get(0);
                                    for (int i = 0; i < checkWinnerBids.size(); i++) {
                                        if (Long.parseLong(max.getBids()) < Long.parseLong(checkWinnerBids.get(i).getBids())) {
                                            max = checkWinnerBids.get(i);
                                        }
                                    }
                                    if (onDestroyFlag == false) {
                                        DatabaseReference wonAuctionsRef = FirebaseDatabase.getInstance().getReference().child("Won");
                                        Won wonAuction = new Won(max.getuName(), setPost.getpId());
                                        wonAuctionsRef.push().setValue(wonAuction);
                                        System.out.println("checkff setValue(Won) bidding screen");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/
                        Intent i = new Intent(BiddingFuture.this, MainActivity.class);
                        i.putExtra("type", "Bidder");
                        startActivity(i);
                        finish();
                        this.cancel();
                        return;
                    } else {
                        --elapsedHours;
                        elapsedMinutes = 59;
                    }
                } else
                    --elapsedMinutes;
                hours.setText(String.valueOf(elapsedHours));
                mins.setText(String.valueOf(elapsedMinutes));
                this.start();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
