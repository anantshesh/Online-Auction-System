package com.example.apnabazaar.auctionfolder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdapterBid;
import com.example.apnabazaar.adapters.AdapterBid1;
import com.example.apnabazaar.adapters.BiddingAdapter;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuctioneerBiddingScreen extends AppCompatActivity {



    private RequestQueue requestQueue;

    private boolean notify = false;

    private ListView myListView;
    private TextView title,description,initialBid,winner, quantity, place, uname;
    private BiddingAdapter myAdapter;
    private AdapterBid adapterBid;
    private AdapterBid1 adapterBid1;
    ImageView postImage, udp;
    RecyclerView recyclerView;
    private List<Bids> bids= new ArrayList<Bids>();
    private DatabaseReference wonRef,bidReference;
    private Post setPost;
    private long last = 0;
    private boolean onDestroyFlag = false;
    Date currentDate = null;
    String postId;
    private SimpleDateFormat mdformat;
    private List<Post> posts = new ArrayList<Post>();



    String myUid,  myEmail, myName, minAmt , myDp;
    String hisName, bidId,  hisUid, hisEmail, pduration, startdatetime;




    private String winnerUsername="No Winner";
    private ChildEventListener childEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctioneer_bidding_screen);


        Intent intent = getIntent();
        postId = intent.getStringExtra("my");

        mdformat = new java.text.SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
       // setPost = (Post) getIntent().getSerializableExtra("send post");
        // myListView=(ListView) findViewById(R.id.listview_auc_bidding_screen);
        //title=(TextView) findViewById(R.id.title__auc_bidding_screen);
        //description=(TextView) findViewById(R.id.description_auc_bidding_screen);
        initialBid=(TextView) findViewById(R.id.pPriceTv);
        winner=(TextView) findViewById(R.id.winner);


        wonRef= FirebaseDatabase.getInstance().getReference("Posts");
        bidReference=FirebaseDatabase.getInstance().getReference("Bids");
        //myAdapter= new BiddingAdapter(AuctioneerBiddingScreen.this,bids);
        //myListView.setAdapter(myAdapter);

        title = findViewById(R.id.pTitleTv);
        description = findViewById(R.id.pDescriptionTv);
        quantity = findViewById(R.id.pQuantityTv);
        //place = findViewById(R.id.pLocationTv);
       // uname = findViewById(R.id.uNameTv);
        //udp = findViewById(R.id.uPictureIv);
        postImage = findViewById(R.id.pImageIv);

        recyclerView = findViewById(R.id.recyclerForbidders);



        Query query = wonRef.orderByChild("pId").equalTo(postId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    hisUid = "" +ds.child("uid").getValue();
                    hisEmail = "" +ds.child("uEmail").getValue();
                    hisName = ""+ ds.child("uName").getValue();
                    String dp = ""+ ds.child("uDp").getValue();
                    String title1 = ""+ ds.child("pTitle").getValue();
                    String desc = ""+ ds.child("pDesc").getValue();
                    String amt = ""+ ds.child("pMinPrice").getValue();
                    String location = ""+ ds.child("pCity").getValue();
                    String quantity1 = ""+ ds.child("pQuantity").getValue();
                    String pimage = ""+ ds.child("pImage").getValue();
                    pduration = "" + ds.child("pduration").getValue();
                    startdatetime = "" + ds.child("aDateTime").getValue();



                   // uNameTv.setText(hisName);
                    title.setText(title1);
                    description.setText(desc);
                    initialBid.setText(amt);
                    //pLocationTv.setText(location);
                   // quantity.setText(quantity1);
                    //date.setText(startdatetime);

                    //duration.setText(pduration);



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

        /*title.setText(setPost.getpTitle());
        description.setText(setPost.getpDesc());
        initialBid.setText(setPost.getpMinPrice());
//        quantity.setText(setPost.getpQuantity());
        //place.setText(setPost.getpCity());
     //   uname.setText(setPost.getuName());
        String pImage = setPost.getpImage();
        String uImage = setPost.getuDp();
        try{
            Picasso.get().load(pImage).into(postImage);
        }catch (Exception e){

        }*/
        //checkWinner();
        bidReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                       Bids checkWon = snapshot.getValue(Bids.class);
                       if (postId.equals(checkWon.getBpId())) {
                           winnerUsername = checkWon.getBuName() + " Rs. " + checkWon.getBids();
                       }
                   }
                   winner.setText(winnerUsername);
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        /*childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Bids checkBid= dataSnapshot.getValue(Bids.class);
                if(checkBid.getBpId().equals(setPost.getpId())){
                    bids.add(checkBid);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        bidReference.addChildEventListener(childEventListener);
*/
        loadBidders();


    }

    private void checkWinner() {
        Calendar c = Calendar.getInstance();
        try {
            currentDate = mdformat.parse(mdformat.format(c.getTime()));
        } catch (Exception c1) {
            c1.printStackTrace();
        }
        Date postEndDate = null;

        for (int i = 0; i < posts.size(); i++) {
            try {
                postEndDate = mdformat.parse(posts.get(i).getPduration());
            } catch (Exception c1) {
                c1.printStackTrace();
            }
            if (currentDate.compareTo(postEndDate) >= 0) {
                System.out.println("checkff setValue compare to coming");

                wonRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Bid checkWon = snapshot.getValue(Bid.class);
                            winnerUsername = checkWon.getuName() + " Rs. " + checkWon.getBids();


                        }
                        winner.setText(winnerUsername);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private void loadBidders() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AuctioneerBiddingScreen.this);
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
                          adapterBid1 = new AdapterBid1(AuctioneerBiddingScreen.this, bids);
                          recyclerView.setAdapter(adapterBid1);

                    }


                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AuctioneerBiddingScreen.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
