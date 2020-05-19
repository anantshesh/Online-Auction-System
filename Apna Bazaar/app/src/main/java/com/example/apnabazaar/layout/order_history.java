package com.example.apnabazaar.layout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.allPostAdapter;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.winner;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class order_history extends AppCompatActivity {
    //private ListView myListView;
   // private AdsAdapter myAdapter;

    private List<Post> posts = new ArrayList<Post>();
    private List<Bid> bids = new ArrayList<Bid>();
    private List<winner> wins = new ArrayList<winner>();
    private DatabaseReference postRef;
    private ChildEventListener childEventListener;
    private SimpleDateFormat mdformat;
    Date currentDate;
    RecyclerView recyclerView;
    private allPostAdapter allPostAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        //myListView =  findViewById(R.id.winnerList);
        //myAdapter = new AdsAdapter(order_history.this, posts, "won");
        postRef = FirebaseDatabase.getInstance().getReference("Posts");
        recyclerView = findViewById(R.id.orderRecycler);
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        //myListView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(order_history.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        posts.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post addPost = dataSnapshot.getValue(Post.class);
                Calendar calendar = Calendar.getInstance();
                Date enddate = null;
                try {
                    currentDate = mdformat.parse(mdformat.format(calendar.getTime()));
                    enddate = mdformat.parse(addPost.getPduration());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (currentDate.compareTo(enddate) >= 0) {


                    posts.add(addPost);
                    allPostAdapter = new allPostAdapter(order_history.this, "order", posts);
                    recyclerView.setAdapter(allPostAdapter);

                    //myAdapter.notifyDataSetChanged();
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
        postRef.addChildEventListener(childEventListener);


    }



}


