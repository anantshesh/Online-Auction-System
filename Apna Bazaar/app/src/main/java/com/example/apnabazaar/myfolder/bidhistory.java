package com.example.apnabazaar.myfolder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.bidhistoryAdapter;
import com.example.apnabazaar.models.bidhistoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bidhistory extends AppCompatActivity {


    RecyclerView recyclerView;
    List<bidhistoryModel> bidhistory1;
    bidhistoryAdapter bidhistoryAdapter;
    public String type;
    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    String myEmail, myUid;

    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_history);
        recyclerView = findViewById(R.id.bidHistory);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        checkCurrentUser();

       /* Intent i = getIntent();
        type = i.getStringExtra("type");
*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

       bidhistory1 = new ArrayList<>();



       loadbidhistory();
    }

    private void checkCurrentUser() {
         user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();



        }else{
            startActivity(new Intent(bidhistory.this, login.class));
            finish();
        }
    }

    private void loadbidhistory() {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(myUid).child("Bid History");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bidhistory1.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    bidhistoryModel history = ds.getValue(bidhistoryModel.class);
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(history.getUid())) {
                        bidhistory1.add(history);
                        bidhistoryAdapter = new bidhistoryAdapter(bidhistory.this, bidhistory1);
                        recyclerView.setAdapter(bidhistoryAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
