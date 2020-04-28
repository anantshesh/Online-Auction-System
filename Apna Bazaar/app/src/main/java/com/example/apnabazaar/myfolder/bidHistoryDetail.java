package com.example.apnabazaar.myfolder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdapterBid;
import com.example.apnabazaar.adapters.BiddingAdapter;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.winner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class bidHistoryDetail extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Bids> bidList;

   // private ListView myListView;
    private BiddingAdapter myAdapter;

    private ChildEventListener childEventListener;
    AdapterBid adapterBid;

    ImageButton close, share;
    TextView pTitleTv, pDescriptionTv,highest, pPriceTv, pQuantityTv, pLocationTv, pTimeTv, uNameTv, date, duration;
    ImageView pImageIv, uPictureIv;

    String myUid, postId, myEmail, myName, minAmt , myDp;
    String hisName, bidId,  hisUid, hisEmail, pduration, startdatetime;

    private SimpleDateFormat mdformat;
    Date currentDate;


    private Post setPost;
    private winner win;
    private DatabaseReference postRef;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog pd;

    private DatabaseReference wonRef;

    private String winnerUsername="No Winner";
    private String winnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_history_detail);

        highest = findViewById(R.id.winner);
        uNameTv = findViewById(R.id.bhuname);
        close = findViewById(R.id.close);
        share = findViewById(R.id.share);
        pTitleTv = findViewById(R.id.bhpTitleTv);
        pDescriptionTv = findViewById(R.id.bhpDescriptionTv);
        pPriceTv = findViewById(R.id.bhpPriceTv);
        pQuantityTv = findViewById(R.id.bhpQuantityTv);
        pLocationTv = findViewById(R.id.bhpLocationTv);
        //pTimeTv = findViewById(R.id.pTimeTv);
        pImageIv = findViewById(R.id.bhpImageIv);
        uPictureIv = findViewById(R.id.bhuimage);

        recyclerView = findViewById(R.id.recyclerForbidHistory);
        //myListView=(ListView) findViewById(R.id.listBidders1);
       // myAdapter= new BiddingAdapter(bidHistoryDetail.this,bidList);
        //myListView.setAdapter(myAdapter);


        Intent intent = getIntent();
        postId = intent.getStringExtra("posthis");


        wonRef= FirebaseDatabase.getInstance().getReference("Bids");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        storageReference = getInstance().getReference();
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        postRef = FirebaseDatabase.getInstance().getReference("Bids");

        Query query = databaseReference.orderByChild("pId").equalTo(postId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    hisUid = "" +ds.child("uid").getValue();
                    hisEmail = "" +ds.child("uEmail").getValue();
                    hisName = ""+ ds.child("uName").getValue();
                    String dp = ""+ ds.child("uDp").getValue();
                    String title = ""+ ds.child("pTitle").getValue();
                    String desc = ""+ ds.child("pDesc").getValue();
                    String amt = ""+ ds.child("pMinPrice").getValue();
                    String location = ""+ ds.child("pCity").getValue();
                    String quantity = ""+ ds.child("pQuantity").getValue();
                    String pimage = ""+ ds.child("pImage").getValue();
                    pduration = "" + ds.child("pduration").getValue();
                    startdatetime = "" + ds.child("aDateTime").getValue();



                    uNameTv.setText(hisName);
                    pTitleTv.setText(title);
                    pDescriptionTv.setText(desc);
                    pPriceTv.setText(amt);
                    pLocationTv.setText(location);
                    pQuantityTv.setText(quantity);
//                    date.setText(startdatetime);

                   // duration.setText(pduration);


                    try{
                        Picasso.get().load(dp).into(uPictureIv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(uPictureIv);
                    }

                    try{
                        Picasso.get().load(pimage).into(pImageIv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(pImageIv);
                    }







                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        wonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Bids checkWon = snapshot.getValue(Bids.class);
                    if (postId.equals(checkWon.getBpId())) {
                        winnerUsername = checkWon.getBuName() + " Rs. " + checkWon.getBids();
                        winnerId = checkWon.getBuid();

                    }
                }


                highest.setText(winnerUsername);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/*
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                bidhistoryModel checkBid= dataSnapshot.getValue(bidhistoryModel.class);
                if(checkBid.getBhpId().equals(setPost.getpId())){
                    Bids bids = dataSnapshot.getValue(Bids.class);
                    bidList.add(bids);
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
        postRef.addChildEventListener(childEventListener);*/


/*       if (winnerId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
           highest.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i = new Intent(bidHistoryDetail.this, order_history.class);
                   startActivity(i);
               }
           });
       }
*/

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bidHistoryDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(bidHistoryDetail.this, "Share Event", Toast.LENGTH_SHORT).show();
            }
        });

        checkUserStatus();

        Query ref = FirebaseDatabase.getInstance().getReference("User");
        ref.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    myName = ""+ ds.child("name").getValue();
                    myDp = ""+ds.child("image").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadbidders();
    }

    private void loadbidders() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(bidHistoryDetail.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        bidList = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bids");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bidList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Bids modelBid = ds.getValue(Bids.class);
                    if (modelBid.getBpId().equals(postId)) {

                        bidList.add(modelBid);

                        adapterBid = new AdapterBid(bidHistoryDetail.this, bidList);
                        recyclerView.setAdapter(adapterBid);


                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(bidHistoryDetail.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();



        }else{
            startActivity(new Intent(bidHistoryDetail.this, login.class));
            finish();
        }
    }
}
