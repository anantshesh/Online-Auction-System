package com.example.apnabazaar.myfolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apnabazaar.R;
import com.example.apnabazaar.adapters.AdapterBid;
import com.example.apnabazaar.adapters.AdapterPost;
import com.example.apnabazaar.models.Bid;
import com.example.apnabazaar.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.HashMap;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class induidual_posts extends AppCompatActivity {



    RecyclerView recyclerView , recyclerView1;
    List<Bid> bidList;
    AdapterBid adapterBid;
    List<Post> postList = new ArrayList<Post>() ;

    AdapterPost adapterPost;

    Date currentDateTime, startDate;

    ImageButton close, share;
    TextView  pTitleTv, pDescriptionTv, pPriceTv, pQuantityTv, pLocationTv, pTimeTv, uNameTv, date, duration;
    ImageView pImageIv, uPictureIv;
    Button bBid;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog pd;



    private DatabaseReference bidReference;
  //  private ChildEventListener childEventListener;
    SimpleDateFormat sdf;

    //LinearLayout bidlayout;


    String myUid, postId, myEmail, myName, minAmt , myDp;
    String hisName, bidId,  hisUid, hisEmail, pduration, startdatetime;


    EditText bidEt;


 //   String name, email, uid, dp;


   // Context context;




    //DatabaseReference userDbRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_induidual_posts);

        //bBid = findViewById(R.id.bBid);
        //bidEt = findViewById(R.id.bidEt);
        uNameTv = findViewById(R.id.uNameTv);
        close = findViewById(R.id.close);
        share = findViewById(R.id.share);
        pTitleTv = findViewById(R.id.pTitleTv);
        pDescriptionTv = findViewById(R.id.pDescriptionTv);
        pPriceTv = findViewById(R.id.pPriceTv);
        pQuantityTv = findViewById(R.id.pQuantityTv);
        pLocationTv = findViewById(R.id.pLocationTv);
        //pTimeTv = findViewById(R.id.pTimeTv);
        pImageIv = findViewById(R.id.pImageIv);
        //date = findViewById(R.id.date);
        //time = findViewById(R.id.time);
        //duration = findViewById(R.id.pCountDownTv);
        pd = new ProgressDialog(this);







        Intent intent = getIntent();
       postId = intent.getStringExtra("send Post");

        uPictureIv = findViewById(R.id.uPictureIv);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        storageReference = getInstance().getReference();



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
                    date.setText(startdatetime);

                    duration.setText(pduration);


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


       // final String currentDate = sdf.format(new Date());
//Long.parseLong(pPriceTv.getText().toString())>= Long.parseLong(bidEt.getText().toString())


      /*  sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

        Calendar c = Calendar.getInstance();
        try{
            currentDateTime = sdf.parse(sdf.format(c.getTime()));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        Date postStartDate = null;

        for(int i = 0 ; i< postList.size(); i++){
            try{
                postStartDate = sdf.parse(postList.get(i).getaDateTime());

            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(currentDateTime.compareTo(postStartDate)>=0){
                bBid.setVisibility(View.VISIBLE);
            }
            else{
                bBid.setVisibility(View.GONE);
            }

        }
*/















        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(induidual_posts.this, MainActivity.class);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(induidual_posts.this, "Share Event", Toast.LENGTH_SHORT).show();
            }
        });

       // recyclerView = findViewById(R.id.bidderRecycler);

       // loadPostInfo();

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





       /* bBid.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bBid.setVisibility(View.VISIBLE);


            }
        }, 20000);*/


       bBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBid();
            }
        });

        //loadBidders();

        //bidReference = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Bidders").child(bidId).child("Bids");
        //bidReference.addChildEventListener(childEventListener);




    }



    private void postBid() {


       // loadUserInfo();



      bidReference = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Bidders").child(bidId).child("Bids");
        pd.setMessage("Placing Your Bid...");

        String bid = bidEt.getText().toString().trim();

        if(TextUtils.isEmpty(bid)){
            Toast.makeText(this, "Place your Bid!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < bidList.size(); i++) {
            if (Long.parseLong(bidList.get(i).getBids()) >= Long.parseLong(bidEt.getText().toString())) {
                bidEt.setText("");
                Toast.makeText(induidual_posts.this, "Entered bid is less than or equal to current greatest bid", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(Long.parseLong(pPriceTv.getText().toString())>= Long.parseLong(bidEt.getText().toString())){
            bidEt.setText("");
            Toast.makeText(induidual_posts.this, "Entered bid is less than or equal to initial bid", Toast.LENGTH_SHORT).show();
            return;
        }
        else {


            String timeStamp = String.valueOf(System.currentTimeMillis());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Bidders");


            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("bidId", timeStamp);
            hashMap.put("Bids", bid);
            hashMap.put("timestamp", timeStamp);
            hashMap.put("uid", myUid);
            hashMap.put("uEmail", myEmail);
            hashMap.put("uName", myName);

            reference.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(induidual_posts.this, "Bid Placed", Toast.LENGTH_SHORT).show();
                            bidEt.setText("");

                            // updateBidCount();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(induidual_posts.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }





    }

    /*boolean mProcessBid = false;
    private void updateBidCount() {
        mProcessBid = true;
        final DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Posts").child(postId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mProcessBid){
                    String bids = "" + dataSnapshot.child("pBids").getValue();
                    int newBidBal = Integer.parseInt(bids) + 1;
                    ref.child("pBids").setValue("" + newBidBal);
                    mProcessBid = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
  private void loadUserInfo() {

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
    }

   private void loadPostInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

        Query query = ref.orderByChild("pId").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    hisName = ""+ ds.child("uName").getValue();
                    hisUid = "" +ds.child("uid").getValue();
                    hisEmail = "" +ds.child("uEmail").getValue();
                    String udp = ""+ ds.child("uDp").getValue();
                    String title = ""+ ds.child("pTitle").getValue();
                    String desc = ""+ ds.child("pDesc").getValue();
                    String amt = ""+ ds.child("pMinPrice").getValue();
                    String location = ""+ ds.child("pCity").getValue();
                    String quantity = ""+ ds.child("pQuantity").getValue();
                    String pimage = ""+ ds.child("pImage").getValue();
                    //hisName = "" + ds.child("uName").getValue();


                    uNameTv.setText(hisName);
                    pTitleTv.setText(title);
                    pDescriptionTv.setText(desc);
                    pPriceTv.setText(amt);
                    pLocationTv.setText(location);
                    pQuantityTv.setText(quantity);

                    try{
                        Picasso.get().load(udp).into(uPictureIv);
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
    }

   private void loadBidders() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(induidual_posts.this);
       layoutManager.setStackFromEnd(true);
       layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        bidList = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Bidders");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bidList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                     Bid modelBid = ds.getValue(Bid.class);

                     bidList.add(modelBid);

                    //adapterBid = new AdapterBid(induidual_posts.this, bidList );
                    recyclerView.setAdapter(adapterBid);



                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(induidual_posts.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkUserStatus(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();



        }else{
            startActivity(new Intent(induidual_posts.this, login.class));
            finish();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
