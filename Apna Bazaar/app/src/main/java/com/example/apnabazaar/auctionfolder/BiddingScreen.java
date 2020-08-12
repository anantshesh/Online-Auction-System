package com.example.apnabazaar.auctionfolder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdapterBid;
import com.example.apnabazaar.adapters.AdapterPost;
import com.example.apnabazaar.adapters.BiddingAdapter;
import com.example.apnabazaar.adapters.allPostAdapter;
import com.example.apnabazaar.adapters.citySearchPostAdapter;
import com.example.apnabazaar.adapters.horizontalProductAdapter;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.Ratings;
import com.example.apnabazaar.models.Won;
import com.example.apnabazaar.myfolder.MainActivity;
import com.example.apnabazaar.myfolder.login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiddingScreen extends AppCompatActivity {
    private Post setPost;
   // private winner setWin;
   // private Bid bidPost;
    //private bidhistoryModel history;

    private horizontalProductAdapter horizontalProductAdapter, horizontalProductAdapter1;
    private allPostAdapter allPostAdapter;
    private citySearchPostAdapter citySearchPostAdapter;
    private List<Post> posts= new ArrayList<Post>();
    private AdapterPost adapterPost;

    private AdapterBid adapterBid1;
    boolean ViewCount = false;



    RecyclerView recyclerView1, recyclerView, mostViewRecycler;
    private TextView title, description, hours, mins, sec, initialBid, quantity, place,uname, ratingText, totalCustomers,count1, category;
    private ImageView postImage, udp;

    private java.text.SimpleDateFormat mdformat;
    private Date startDate = null, endDate = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference = null;
    private long elapsedMinutes, elapsedHours, elapsedSeconds;
    private BiddingAdapter myadapter;

    String bidsupdate;

    //private AdapterBid adapterBid;
    private List<Bids> bids = new ArrayList<Bids>();

    private DatabaseReference bidReference, bidRef, postRef, postRef1;
    private ChildEventListener childEventListener;

    private Button bidButton;
    private EditText bidEt;
    ProgressDialog pd;
    String myUid, postId, postId1, myEmail, myName, minAmt ,myPhone, myDp;
    String hisName, bidamount,  hisUid, hisEmail, pduration, startdatetime, hisdp,stitle,posttime, desc, amt, location,squantity,pimage, pcatogry, pcount;

    Date currentDate = null;
    //private SimpleDateFormat mdformat;



   // private LinearLayout linearLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    StorageReference storageReference;
   // private RecyclerView recyclerView;
    Bundle bundle;
    long diff = 0;
    private boolean onDestroyFlag = false;
    RatingBar ratingBar;
    String BidId;
    long count;

    //Horizontal Variables
    private TextView layoutTitle, layoutTitle1;
    private Button viewAll;
    private RecyclerView similarRecycler;
    private Boolean isImageFitToScreen = false;

    List<Bids> checkWinnerBids = new ArrayList<Bids>();
    private String winnerUsername="No Winner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding_screen);

        Intent intent = getIntent();
        postId = intent.getStringExtra("all");

        title = findViewById(R.id.pTitleTv);
        description = findViewById(R.id.pDescriptionTv);
        quantity = findViewById(R.id.pQuantityTv);
        place = findViewById(R.id.pLocationTv);
        uname = findViewById(R.id.uNameTv);
        udp = findViewById(R.id.uPictureIv);
        hours = findViewById(R.id.hours_bidding_screen);
        mins = findViewById(R.id.mins_bidding_screen);
        sec  = findViewById(R.id.sec_bidding_screen);
        initialBid = findViewById(R.id.pPriceTv);
        postImage = findViewById(R.id.pImageIv);
        count1 = findViewById(R.id.count);
        category = findViewById(R.id.category);

        ratingBar = findViewById(R.id.sellerRating);
        ratingText = findViewById(R.id.ratingText);
        totalCustomers = findViewById(R.id.total);
        //myListView = findViewById(R.id.bidList);
        bidButton = findViewById(R.id.bBid);
        bidEt = findViewById(R.id.bidEt);

        pd = new ProgressDialog(this);




        recyclerView = findViewById(R.id.bidderRecycler);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        bidRef = FirebaseDatabase.getInstance().getReference("Bids");

        postRef = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = postRef.orderByChild("pId").equalTo(postId);

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
                    pcatogry = "" + ds.child("pCatogry").getValue();
                    posttime = "" +ds.child("pTime").getValue();
                    pcount = "" +ds.child("Count").getValue();

                    uname.setText(hisName);
                    title.setText(stitle);
                    description.setText(desc);
                    initialBid.setText(amt);
                    place.setText(location);
                    quantity.setText(squantity);
                    count1.setText(pcount);
                    category.setText(pcatogry);


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




        //updateViewCount();



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
                }
                else {
                    isImageFitToScreen=true;
                    postImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    postImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }

            }
        });
        //logged in user details
        checkCurrentUser();
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        loadUser();
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myUid.equals(hisUid)){
                    Toast.makeText(BiddingScreen.this, "You are the seller of this Product", Toast.LENGTH_SHORT).show();
                }
                else {
                    postBid();
                }
            }
        });


         loadBidders();



       /* layoutTitle1 = findViewById(R.id.MostViewedTitle);
        mostViewRecycler = findViewById(R.id.mostViewed);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(BiddingScreen.this);
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
                          horizontalProductAdapter1 = new horizontalProductAdapter(BiddingScreen.this, posts);
                          mostViewRecycler.setAdapter(horizontalProductAdapter1);
                      }
                  }
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/

         //Similar product code
        layoutTitle = findViewById(R.id.similarTitle);
        recyclerView1 = findViewById(R.id.similarRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(BiddingScreen.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView1.setLayoutManager(layoutManager);
        postRef = FirebaseDatabase.getInstance().getReference("Posts");
        //posts.clear();
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Post check = ds.getValue(Post.class);

                    if (check.getpCatogry().equals(pcatogry)) {
                        Calendar calendar = Calendar.getInstance();
                        Date enddate = null, startDate = null;
                        try{
                            currentDate = mdformat.parse(mdformat.format(calendar.getTime()));
                            enddate = mdformat.parse(check.getPduration());
                            startDate = mdformat.parse(check.getaDateTime());

                        }catch (Exception e ){
                            e.printStackTrace();
                        }


                        if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(enddate) == -1){

                           // posts.clear();
                            posts.add(check);
                            horizontalProductAdapter = new horizontalProductAdapter(BiddingScreen.this, posts);
                            recyclerView1.setAdapter(horizontalProductAdapter);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void updateViewCount() {

    }

    //Notification

    private void prepareNotification(String pId, String title, String bName, String notificationType, String notificationTopic){

        String NOTIFICATION_TOPIC = "/topics/" + notificationTopic;
        String NOTIFICATION_TITLE = title;
        String NOTIFICATION_MESSAGE = bName;
        String NOTIFICATION_TYPE = notificationType;

        JSONObject notificationJo = new JSONObject();
        JSONObject notificationBodyJo = new JSONObject();

        try {
            notificationBodyJo.put("notificationType", NOTIFICATION_TYPE);
            notificationBodyJo.put("sender", myUid);
            notificationBodyJo.put("pid", pId);
            notificationBodyJo.put("pTitle", NOTIFICATION_TITLE);
            notificationBodyJo.put("buName", NOTIFICATION_MESSAGE);

            notificationJo.put("to", NOTIFICATION_TOPIC);
            notificationJo.put("data", notificationBodyJo);

        } catch (JSONException e) {
            Toast.makeText(this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendBidNotification(notificationJo);

    }



    private void sendBidNotification(JSONObject notificationJo) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https:/fcm.googleapis.com/fcm/send", notificationJo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("FCM_RESPONSE", "onResponse:" +response.toString());

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(BiddingScreen.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorizartion", "key=AAAAZnFJ-fw:APA91bEVc0HEpDZBpjznMmQPlL2h5atHmA2WVc-TyELdkF7FulbyLbzPYQNTQe5F3n-S2uGZL37SqGJpfsmPEnFT52tihADhoLMg5VPBT0ms4VOgwBgEs7JLGY3tzyZhTKQ3GIYqlvIy");
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }







    private void addToBidHistory() {



        String timeStamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(myUid).child("Bid History");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("bidHistoryId", timeStamp);
        hashMap.put("uid", myUid);
        hashMap.put("bhuid", hisUid);
        hashMap.put("bhuName", hisName);
        hashMap.put("bhuEmail", hisEmail);
        hashMap.put("bhuDp", hisdp);
        hashMap.put("bhpId", postId );
        hashMap.put("bhpTitle", stitle );
        hashMap.put("bhaDateTime", startdatetime);
        hashMap.put("bhpQuantity", squantity);
        hashMap.put("bhpDesc", desc );
        hashMap.put("bhpMinPrice", amt );
        hashMap.put("bhpCity", location);
        hashMap.put("bhpduration", pduration);
        hashMap.put("bhpImage", pimage);
        hashMap.put("bhpTime", posttime);

        reference.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(BiddingScreen.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void loadUser() {
        Query ref = FirebaseDatabase.getInstance().getReference("User");
        ref.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    myName = ""+ ds.child("name").getValue();
                    myDp = ""+ds.child("image").getValue();
                    myPhone = ""+ds.child("phone").getValue();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadBidders() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(BiddingScreen.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        bids = new ArrayList<>();
        bidReference = FirebaseDatabase.getInstance().getReference("Bids");
        bidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bids.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Bids check = ds.getValue(Bids.class);
                    if (check.getBpId().equals(postId)) {
                        bids.add(check);
                        adapterBid1 = new AdapterBid(BiddingScreen.this, bids);
                        recyclerView.setAdapter(adapterBid1);

                    }


                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(BiddingScreen.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void checkCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();



        }else{
            startActivity(new Intent(BiddingScreen.this, login.class));
            finish();
        }
    }

    private void postBid() {


        //bidReference = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Bidders").child(bidId).child("Bids");
        pd.setMessage("Placing Your Bid...");
        String bid = bidEt.getText().toString().trim();
        if(TextUtils.isEmpty(bid)){
            Toast.makeText(this, "Place your Bid!", Toast.LENGTH_SHORT).show();
            return;
        }


        for (int i = 0; i < bids.size(); i++) {
            if (Long.parseLong(bids.get(i).getBids()) >= Long.parseLong(bidEt.getText().toString())) {
                bidEt.setText("");
                Toast.makeText(BiddingScreen.this, "Entered bid is less than or equal to current greatest bid", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(Long.parseLong(initialBid.getText().toString())>= Long.parseLong(bidEt.getText().toString())){
            bidEt.setText("");
            Toast.makeText(BiddingScreen.this, "Entered bid is less than or equal to initial bid", Toast.LENGTH_SHORT).show();
            return;
        }


        else {
            String timeStamp = String.valueOf(System.currentTimeMillis());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bids");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("bidId", timeStamp);
            hashMap.put("Bids", bid);
            hashMap.put("timestamp", timeStamp);
            hashMap.put("buid", myUid);
            hashMap.put("buEmail", myEmail);
            hashMap.put("buName", myName);
            hashMap.put("bphone", myPhone);
            hashMap.put("bpId", postId);

            reference.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(BiddingScreen.this, "Bid Placed", Toast.LENGTH_SHORT).show();
                            bidEt.setText("");
                            addToBidHistory();
                            /*prepareNotification(""+setPost.getpId(),
                                    "" +setPost.getpTitle(),
                                    ""+myName+ "Bid on your Product",
                                    "BidNotification",
                                     "Bid");*/

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(BiddingScreen.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void showTimer(Date startDate, Date endDate) {

        Calendar cal = Calendar.getInstance();
        Date currentdate = null;
        try {
            currentdate = mdformat.parse(mdformat.format(cal.getTime()));

        } catch (Exception c) {
            c.printStackTrace();
        }
        System.out.println("checkff end= " + endDate + " current= " + currentdate);
        diff = endDate.getTime() - currentdate.getTime();




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
                        Toast.makeText(BiddingScreen.this, "Bidding time ends", Toast.LENGTH_SHORT).show();

                        DatabaseReference databaseReference;
                        databaseReference = FirebaseDatabase.getInstance().getReference("Bids");
                        Query query = databaseReference;
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                checkWinnerBids.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Bids winnerBid = snapshot.getValue(Bids.class);
                                    if (winnerBid.getBpId().equals(postId)) {
                                        checkWinnerBids.add(winnerBid);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        if (checkWinnerBids.size() > 0) {
                            Bids max = checkWinnerBids.get(0);
                            for (int i = 0; i < checkWinnerBids.size(); i++) {
                                if (Long.parseLong(max.getBids()) < Long.parseLong(checkWinnerBids.get(i).getBids())) {
                                    max = checkWinnerBids.get(i);
                                }
                            }
                            if(onDestroyFlag==false) {


                                DatabaseReference wonAuctionsRef = FirebaseDatabase.getInstance().getReference("Winner");
                                Won wonAuction = new Won(max.getBuName(),postId);
                                wonAuctionsRef.push().setValue(wonAuction);
                                System.out.println("checkff setValue(Won) bidding screen");
                            }
                        }
                        Intent i = new Intent(BiddingScreen.this, MainActivity.class);
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
                        Toast.makeText(BiddingScreen.this, "Bidding time ends", Toast.LENGTH_SHORT).show();
                        Query query = bidReference;
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<Bids> checkWinnerBids = new ArrayList<Bids>();
                                int count = 0;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Bids winnerBid = snapshot.getValue(Bids.class);
                                    if (winnerBid.getBpId().equals(postId)) {
                                        checkWinnerBids.add(winnerBid);
                                    }
                                }
                                if (checkWinnerBids.size() > 0) {
                                    Bids max = checkWinnerBids.get(0);
                                    for (int i = 0; i < checkWinnerBids.size(); i++) {
                                        if (Long.parseLong(max.getBids()) < Long.parseLong(checkWinnerBids.get(i).getBids())) {
                                            max = checkWinnerBids.get(i);
                                        }
                                    }
                                    if (onDestroyFlag == false) {
                                        DatabaseReference wonAuctionsRef = FirebaseDatabase.getInstance().getReference().child("Won");
                                        Won wonAuction = new Won(max.getBuName(), postId);
                                        wonAuctionsRef.push().setValue(wonAuction);
                                        System.out.println("checkff setValue(Won) bidding screen");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Intent i = new Intent(BiddingScreen.this, MainActivity.class);
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
         onDestroyFlag = true;
        Intent i = new Intent(BiddingScreen.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("type", "Bidder");
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewCount = true;
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String views = ""+ dataSnapshot.child("Count").getValue();
                int newViewVal = Integer.parseInt(views) + 1;
                ref.child("Count").setValue(String.valueOf(newViewVal));
                ViewCount = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
