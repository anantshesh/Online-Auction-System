package com.example.apnabazaar.layout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdapterPost;
import com.example.apnabazaar.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YourProduct extends AppCompatActivity {
   // private ListView myListView;
    //private AdsAdapter myadapter;
    private AdapterPost adapterPost;
    private List<Post> posts= new ArrayList<Post>();
    //private List<Bid> bids= new ArrayList<Bid>();
    //private List<winner> wins= new ArrayList<winner>();
    private FirebaseDatabase database;

    private DatabaseReference postsreference;
    //private ChildEventListener childEventListener;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_product);
        database=FirebaseDatabase.getInstance();
        postsreference=database.getReference().child("Posts");
        System.out.println("checkff coming My Ads");
        //myListView = findViewById( R.id.myAds_listview);

        recyclerView = findViewById(R.id.recyclerMypost);
        //here
        //myadapter=new AdsAdapter(YourProduct.this,posts,"my ads");
        //myListView.setAdapter(myadapter);
        posts.clear();

        LinearLayoutManager layoutManager = new LinearLayoutManager(YourProduct.this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        postsreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post check = ds.getValue(Post.class);
                    if (check.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        posts.add(check);
                        adapterPost = new AdapterPost(YourProduct.this, posts);
                        recyclerView.setAdapter(adapterPost);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(YourProduct.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
