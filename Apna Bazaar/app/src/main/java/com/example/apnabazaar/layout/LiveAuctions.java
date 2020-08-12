package com.example.apnabazaar.layout;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.allPostAdapter;
import com.example.apnabazaar.adapters.citySearchPostAdapter;
import com.example.apnabazaar.allCityOption;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.Won;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveAuctions extends Fragment {


    ImageButton imageButton;

   //city live pos
    private List<Post> posts = new ArrayList<Post>();
    //RecyclerView recyclerView;
    private citySearchPostAdapter citySearchPostAdapter;
    Button cityAll;
    LinearLayout citySearch;

    //upcoming post
    private citySearchPostAdapter upcomingPost;
    RecyclerView upcomingRecycler;
    Button upcomingall;

    //perishable
    private citySearchPostAdapter perishablePost;
    RecyclerView perishableRecycler;
    Button perishableall;

    //all Post recycler
    RecyclerView allpostrecycler;
    private allPostAdapter allPostAdapter;


    //all recycler in one
    RecyclerView allRecycler, mostViewRecycler;


    //private List<Post> posts = new ArrayList<Post>();


    private Spinner category;
    private String mCategory;

    private TextView layoutTitle, layoutTitle1;

    private Spinner city;
    private String mcity;

    private DatabaseReference postreference;
    private DatabaseReference bidRef;

    private ChildEventListener childEventListener;

    private SimpleDateFormat mdformat;

    private boolean isCountDownRunning = false;

    Date currentDate = null;

    private boolean onDestroyFlag = false, returnType;

    public LiveAuctions() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_auctions2, container, false);
        onDestroyFlag = false;
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

        //all post recycler
        allpostrecycler = view.findViewById(R.id.AllPostRecycler);



       //city post
       // recyclerView = view.findViewById(R.id.citySearchRecycler);
        //cityAll = view.findViewById(R.id.cityViewAll);
        //citySearch = view.findViewById(R.id.citySearchLayout);



        imageButton = view.findViewById(R.id.cityImage);

        imageButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent i = new Intent(getActivity(), allCityOption.class);
                                               startActivity(i);
                                           }
                                       }
        );

/*
        //upcoming post
        upcomingRecycler = view.findViewById(R.id.upcomingRecycler);
        upcomingall = view.findViewById(R.id.upcomingViewAll);

        //perishable posts
        perishableRecycler = view.findViewById(R.id.perishableRecycler);
        perishableall = view.findViewById(R.id.perishableViewAll);
*/
        //myadapter = new AdsAdapter(getActivity(), posts, "live");
        category = (Spinner) view.findViewById(R.id.categoryOfItem_liveAuctions);
        // city = view.findViewById(R.id.cityOfItem_liveAuctions);
        //mylistview = (ListView) view.findViewById(R.id.live_auctions_listview);
        postreference = FirebaseDatabase.getInstance().getReference("Posts");
        bidRef = FirebaseDatabase.getInstance().getReference("Bids");
        ArrayAdapter<CharSequence> adapterCategoryOfItem = ArrayAdapter.createFromResource(getActivity(), R.array.category_search, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapterCategoryOfItem);
       /* ArrayAdapter<CharSequence> adapterCityOfItem = ArrayAdapter.createFromResource(getActivity(), R.array.search_of_city, android.R.layout.simple_spinner_item);
        adapterCityOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapterCityOfItem);*/
        posts.clear();
        /*mcity = city.getSelectedItem().toString();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //recyclerView.setLayoutManager(layoutManager);



        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                mcity = city.getItemAtPosition(position).toString();
                Query query1 = postreference;
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    posts.clear();
                        for(DataSnapshot ds: dataSnapshot.getChildren()){


                            Post post = ds.getValue(Post.class);
                            Calendar c = Calendar.getInstance();
                            try {
                                currentDate = mdformat.parse(mdformat.format(c.getTime()));
                            } catch (Exception c1) {
                                c1.printStackTrace();
                            }
                            Date postStartDate = null, postEndDate = null;
                            try {
                                postStartDate = mdformat.parse(post.getaDateTime());
                                postEndDate = mdformat.parse(post.getPduration());
                            } catch (Exception c1) {
                                c1.printStackTrace();
                            }
                            if (currentDate.compareTo(postStartDate) >= 0 && currentDate.compareTo(postEndDate) == -1) {

                                if (mcity.equals("All")){
                                    posts.add(post);
                                }
                                 else if(post.getpCity().equals(mcity)){

                                    posts.add(post);


                                }




                                //citySearchPostAdapter = new citySearchPostAdapter(getActivity(), posts);
                               // recyclerView.setAdapter(citySearchPostAdapter);
                            }

                     }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

      //  mylistview.setAdapter(myadapter);
        mCategory = category.getSelectedItem().toString();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCategory = category.getItemAtPosition(i).toString();
                System.out.println("checkff category=" + mCategory);
                Query query = postreference;
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        posts.clear();

                        //System.out.println("checkff post.clear runs");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post checkPost = snapshot.getValue(Post.class);
                            Calendar c = Calendar.getInstance();
                                try {
                                    currentDate = mdformat.parse(mdformat.format(c.getTime()));
                                } catch (Exception c1) {
                                    c1.printStackTrace();
                                }
                                Date postStartDate = null, postEndDate = null;
                                try {
                                   // System.out.println("checkaa start=" + checkPost.getaDateTime() + " end=" + checkPost.getPduration());
                                    postStartDate = mdformat.parse(checkPost.getaDateTime());
                                    postEndDate = mdformat.parse(checkPost.getPduration());
                                } catch (Exception c1) {
                                    c1.printStackTrace();
                                }
                               // System.out.println("checkff all current=" + currentDate + " start=" + postStartDate + " end=" + postEndDate);
                                if (currentDate.compareTo(postStartDate) >= 0 && currentDate.compareTo(postEndDate)  == -1 ) {

                                    if (mCategory.equals("All")) {
                                        posts.add(checkPost);

                                    }
                                    else if(checkPost.getpCatogry().equals(mCategory)){
                                        posts.add(checkPost);

                                    }
                                    allPostAdapter = new allPostAdapter(getActivity(),"all", posts);
                                    allpostrecycler.setAdapter(allPostAdapter);


                            }


                             /*if(checkPost.getpCatogry().equals(mCategory)) {
                                Calendar cal = Calendar.getInstance();
                                 try {
                                     currentDate = mdformat.parse(mdformat.format(cal.getTime()));
                                 } catch (Exception c1) {
                                     c1.printStackTrace();
                                 }
                                 Date postStartDate = null, postEndDate = null;
                                 try {
                                     postStartDate = mdformat.parse(checkPost.getaDateTime());
                                     postEndDate = mdformat.parse(checkPost.getPduration());
                                 } catch (Exception c1) {
                                     c1.printStackTrace();
                                 }
                                 if (currentDate.compareTo(postStartDate) >= 0 && currentDate.compareTo(postEndDate) == -1) {
                                     posts.add(checkPost);



                                 }
                            }*/

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //loadUpcomingPost();

        //testing all recycler in one
       // allRecycler = view.findViewById(R.id.allRecycler);
       // LinearLayoutManager alllayoutManager = new LinearLayoutManager(getContext());
       // alllayoutManager.setOrientation(RecyclerView.VERTICAL);
        //allRecycler.setLayoutManager(alllayoutManager);


        //loadPerishablePost();





        checkTimeOfPosts();


        /*final CountDownTimer mCountDown = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                this.start();
            }
        }.start();*/
        return view;
    }



    private void loadPerishablePost() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        perishableRecycler.setLayoutManager(layoutManager);
        posts.clear();
         DatabaseReference perishable = FirebaseDatabase.getInstance().getReference("Posts");
         perishable.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 posts.clear();
                 for (DataSnapshot ds : dataSnapshot.getChildren()){
                     Post post = ds.getValue(Post.class);
                     Calendar c = Calendar.getInstance();
                     try {
                         currentDate = mdformat.parse(mdformat.format(c.getTime()));
                     } catch (Exception c1) {
                         c1.printStackTrace();
                     }
                     Date postStartDate = null, postEndDate = null;
                     try {
                         postStartDate = mdformat.parse(post.getaDateTime());
                         postEndDate = mdformat.parse(post.getPduration());
                     } catch (Exception c1) {
                         c1.printStackTrace();
                     }
                     if (currentDate.compareTo(postStartDate) >= 0) {
                         if (post.getpCatogry().equals("Perishable")) {
                             posts.add(post);
                         }

                     }
                     perishablePost = new citySearchPostAdapter(getActivity(), posts);
                     perishableRecycler.setAdapter(perishablePost);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }

    private void loadUpcomingPost() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        upcomingRecycler.setLayoutManager(layoutManager);
        posts.clear();

        DatabaseReference upcoming = FirebaseDatabase.getInstance().getReference("Posts");
        upcoming.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Post checkPost = ds.getValue(Post.class);
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
                        upcomingPost = new citySearchPostAdapter(getActivity(), posts);
                        upcomingRecycler.setAdapter(upcomingPost);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void checkTimeOfPosts() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        allpostrecycler.setLayoutManager(layoutManager);

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
                posts.remove(i);
                allPostAdapter = new allPostAdapter(getActivity(),"all", posts);
                allpostrecycler.setAdapter(allPostAdapter);

            }
        }

        Query query = postreference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post checkPost = snapshot.getValue(Post.class);
                    Calendar c = Calendar.getInstance();
                    Date startDate = null, endDate = null;
                    String sdate,edate;
                    try {
                        currentDate = mdformat.parse(mdformat.format(c.getTime()));
                        String dd = checkPost.getaDateTime();
                        startDate = mdformat.parse(checkPost.getaDateTime());
                        endDate = mdformat.parse(checkPost.getPduration());
                    } catch (Exception c1) {
                        c1.printStackTrace();
                    }
                    if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) == -1) {
                        posts.add(checkPost);
                        allPostAdapter = new allPostAdapter(getActivity(), "all", posts);
                        allpostrecycler.setAdapter(allPostAdapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void checkWinner(final String postkey) {
        Query query = bidRef;
        final List<Bids> allBids = new ArrayList<Bids>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Bids checkbid = snapshot.getValue(Bids.class);
                        if (checkbid.getBpId().equals(postkey)) {
                            allBids.add(checkbid);
                        }
                    }
                    //maxBid(allBids);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void maxBid(List<Bids> bids) {
        if (bids.size() > 0) {
            Bids max = bids.get(0);
            for (int i = 0; i < bids.size(); i++) {
                if (Long.parseLong(max.getBids()) < Long.parseLong(bids.get(i).getBids())) {
                    max = bids.get(i);
                }
            }
            if (onDestroyFlag == false) {
                DatabaseReference wonAuctionsRef = FirebaseDatabase.getInstance().getReference("Winner");
                Won wonAuction = new Won(max.getBuName(), max.getBidId());
                wonAuctionsRef.push().setValue(wonAuction);
                System.out.println("checkff setValue(Won) live screen");
            }
        }
    }

}
