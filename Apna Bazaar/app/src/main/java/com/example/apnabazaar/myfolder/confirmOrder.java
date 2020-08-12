package com.example.apnabazaar.myfolder;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.apnabazaar.R;
import com.example.apnabazaar.models.Bids;
import com.example.apnabazaar.models.Post;
import com.example.apnabazaar.models.Ratings;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.apnabazaar.App.CHANNEL_1_ID;

public class confirmOrder extends AppCompatActivity {

    private Bids bidpost;
    private Post setpost;
    ImageView pImage, sdp;
    LinearLayout linearLayout, orderDetail;
    TextView title, amt11, amt1, qunatity, wname, sname, phone, address, email, upi;
    TextView title1, amount1, name1, upi2;
    Button payment;
    private String winnerUsername="No Winner";
    private DatabaseReference wonRef, postref, rateref;
    String postId;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    String myEmail, myUid, ratingId;
    String  myName, minAmt ,myPhone, myDp;
    String hisName, bidamount,  hisUid, hisEmail, pduration, startdatetime, hisdp,stitle,desc, amt, location,squantity,pimage, upi1;

    DatabaseReference postRef;
    private RatingBar ratingBar;

    private SimpleDateFormat mdformat;
    Date currentDate;

    private LinearLayout rateNowContainer, layoutRating;



    final int UPI_PAYMENT = 0;


    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Intent intent = getIntent();
        postId = intent.getStringExtra("order");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();




          setpost = (Post) getIntent().getSerializableExtra("send post");


        pImage = findViewById(R.id.pImageIv);
        sdp = findViewById(R.id.uPictureIv);


        //layoutRating = findViewById(R.id.ratinglayout);
        title = findViewById(R.id.pTitleTv);
        amt11 = findViewById(R.id.conPriceTv);
        qunatity = findViewById(R.id.pQuantityTv);
        wname = findViewById(R.id.winnername);
        sname = findViewById(R.id.uNameTv);
        payment = findViewById(R.id.payment);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        upi = findViewById(R.id.upi);
        linearLayout = findViewById(R.id.details);


        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");


        ratingBar = findViewById(R.id.ratingBar);

        wonRef = FirebaseDatabase.getInstance().getReference("Bids");
        rateref = FirebaseDatabase.getInstance().getReference("Ratings");


        postRef = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = postRef.orderByChild("pId").equalTo(postId);

       // orderDetail.setVisibility(View.GONE);





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
                    String phone1 = ""+ ds.child("puphone").getValue();
                    upi1 = ""+ ds.child("upiid").getValue();
                    String state = ""+ ds.child("pustate").getValue();
                    String pincode = ""+ ds.child("pupincode").getValue();
                    desc = ""+ ds.child("pDesc").getValue();
                    amt = ""+ ds.child("pMinPrice").getValue();
                    location = ""+ ds.child("pCity").getValue();
                    squantity = ""+ ds.child("pQuantity").getValue();
                    pimage = ""+ ds.child("pImage").getValue();
                    pduration = "" + ds.child("pduration").getValue();
                    startdatetime = "" + ds.child("aDateTime").getValue();

                    sname.setText(hisName);
                    title.setText(stitle);
                    amt11.setText(amt);


                    qunatity.setText(squantity);

                    phone.setText(phone1);
                    email.setText(hisEmail);
                    address.setText(location + " " + state + " " + pincode);
                    upi.setText(upi1);








                    try{
                        Picasso.get().load(hisdp).into(sdp);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(sdp);
                    }

                    try{
                        Picasso.get().load(pimage).into(pImage);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(pImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        checkUserStatus();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Ratings");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Ratings rate = ds.getValue(Ratings.class);
                    if (rate.getUserIdRated().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        if (rate.getPid().equals(postId)) {
                            ratingBar.setRating(rate.getRating());
                            ratingBar.setIsIndicator(true);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //rateNow();

        wonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    final Bids checkWon = snapshot.getValue(Bids.class);
                    if (postId.equals(checkWon.getBpId())) {
                        winnerUsername = checkWon.getBuName() + " Rs. " + checkWon.getBids();
                        if (checkWon.getBuid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            linearLayout.setVisibility(View.VISIBLE);
                            payment.setVisibility(View.VISIBLE);
                            ratingBar.setVisibility(View.VISIBLE);
                            showWinnerRelatedObject();
                            payment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(confirmOrder.this, paymentorder.class);
                                    startActivity(i);
                                }
                            });
                        } else {
                            payment.setVisibility(View.GONE);
                            ratingBar.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                }
                wname.setText(winnerUsername);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* rateNowContainer = findViewById(R.id.stars);
        for(int x =0; x< rateNowContainer.getChildCount(); x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starRating(starPosition);
                }
            });
        }*/

    }

    private void sendNotification() {
        notificationManagerCompat = NotificationManagerCompat.from(this);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Apna Bazaar")
                .setContentText("Congratulations, You have won an auction Product that you bid on!")
                .build();

        notificationManagerCompat.notify(1, notification);
    }


    private void showWinnerRelatedObject() {


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
                String timeStamp = String.valueOf(System.currentTimeMillis());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ratings");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("ratingId", timeStamp);
                hashMap.put("pid", postId);
                hashMap.put("pUid",hisUid);
                hashMap.put("pUname",hisName);
                hashMap.put("UserIdRated", myUid);
                hashMap.put("Rating", rating);

                reference.child(timeStamp).setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(confirmOrder.this, "Recorded Ratings", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(confirmOrder.this, "Unsuccessful Rating", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }





    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();
        }else{
            startActivity(new Intent(confirmOrder.this, login.class));
            finish();
        }
    }

    private void starRating(final int starPosition) {

                        for (int x = 0; x < rateNowContainer.getChildCount(); x++){
                            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
                            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                            if (x <= starPosition){
                                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                            }

                        }


                        String timeStamp = String.valueOf(System.currentTimeMillis());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ratings");
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("ratingId", timeStamp);
                        hashMap.put("pid", setpost.getpId());
                        hashMap.put("pUid", setpost.getUid());
                        hashMap.put("pUname", setpost.getuName());
                        hashMap.put("UserIdRated", myUid );
                        hashMap.put("Rating", starPosition + 1 );

                        reference.child(timeStamp).setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(confirmOrder.this, "Ratings Recorded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(confirmOrder.this, "Unsuccessful Rating", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

    private void doPayment(TextView title, TextView amount, TextView name, TextView upiid) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", String.valueOf(upiid)).
                appendQueryParameter("tn", String.valueOf(name)).
                appendQueryParameter("tn", String.valueOf(title)).
                appendQueryParameter("am", String.valueOf(amount)).
                appendQueryParameter("cu","INR")
                .build();

        Intent upi_payment = new Intent(Intent.ACTION_VIEW);
        upi_payment.setData(uri);

        Intent chooser = Intent.createChooser(upi_payment, "Pay With");

        if (null != chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser, UPI_PAYMENT );
        }

        else {
            Toast.makeText(this, "No UPI APP Found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(confirmOrder.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(confirmOrder.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                Intent i = new Intent(this, confirmOrder.class);
                startActivity(i);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(confirmOrder.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, confirmOrder.class);
                startActivity(i);
            }
            else {
                Toast.makeText(confirmOrder.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, confirmOrder.class);
                startActivity(i);
            }
        } else {
            Toast.makeText(confirmOrder.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, confirmOrder.class);
            startActivity(i);
        }
    }



    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
