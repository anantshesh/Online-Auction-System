package com.example.apnabazaar.layout;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdsAdapter;
import com.example.apnabazaar.adapters.allPostAdapter;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.winner;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureAuctions extends Fragment {



    private ListView myListView;
    private AdsAdapter myAdapter;

    private List<Post> posts = new ArrayList<Post>();
    private List<Bid> bids = new ArrayList<Bid>();
    private List<winner> wins = new ArrayList<winner>();


    private DatabaseReference postRef;

    private ChildEventListener childEventListener;
    private SimpleDateFormat mdformat;
    Date currentDate;

    private allPostAdapter allPostAdapter;
    RecyclerView recyclerView;




    public FutureAuctions() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future_auctions, container, false);

        //myListView =  view.findViewById(R.id.future_auctions_listView);

        //here
        //myAdapter = new AdsAdapter(getActivity(), posts, "future");
        recyclerView = view.findViewById(R.id.futureAllPost);

        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        //myListView.setAdapter(myAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        posts.clear();
       childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post checkPost = dataSnapshot.getValue(Post.class);

                Calendar calendar = Calendar.getInstance();
                Date startDate = null, endDate = null;
                String sdate,edate;
                try {
                    currentDate = mdformat.parse(mdformat.format(calendar.getTime()));
                    String dd = checkPost.getaDateTime();
                    startDate = mdformat.parse(checkPost.getaDateTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                    if (currentDate.compareTo(startDate) == -1) {
                        posts.add(checkPost);
                        allPostAdapter = new allPostAdapter(getActivity(), "future", posts);
                        recyclerView.setAdapter(allPostAdapter);
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
        return view;

    }

}
