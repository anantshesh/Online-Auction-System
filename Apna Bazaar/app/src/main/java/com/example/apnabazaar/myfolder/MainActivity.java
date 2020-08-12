package com.example.apnabazaar.myfolder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.apnabazaar.Profile;
import com.example.apnabazaar.R;
import com.example.apnabazaar.about_us;
import com.example.apnabazaar.feedback;
import com.example.apnabazaar.layout.FutureAuctions;
import com.example.apnabazaar.layout.LiveAuctions;
import com.example.apnabazaar.layout.YourProduct;
import com.example.apnabazaar.layout.order_history;
import com.example.apnabazaar.layout.sellproduct;
import com.example.apnabazaar.models.Bid;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    public String type;

    private DatabaseReference postRef, bidRef, wonRef;
    Calendar calendar;
    private SimpleDateFormat mdformat;
    Date currentDate = null;
    List<String> pId = new ArrayList<String>();
    private boolean onDestroyFlag = false;
    private int listenerCounter = 0;

    View navView;

    ImageView m_dp, dp;
    TextView u_name, name, email, Utype;
    String myUid, myEmail, userTpye;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView ;
    BottomNavigationView bottomNavigationView;



    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;


    String myName,myDp, mType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        calendar = Calendar.getInstance();
        onDestroyFlag = false;
        postRef = FirebaseDatabase.getInstance().getReference("Posts");
        bidRef = FirebaseDatabase.getInstance().getReference("Posts").child("Bidders");
        Intent i = getIntent();
        type = i.getStringExtra("type");
        Utype = findViewById(R.id.type);


        checkcurrentUser();

        /*postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onDestroyFlag == false && listenerCounter == 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post checkPost = snapshot.getValue(Post.class);
                        Date endDatePost = null;
                        try {
                            currentDate = mdformat.parse(mdformat.format(calendar.getTime()));
                            String dd = checkPost.getaDateTime();
                            endDatePost = mdformat.parse(checkPost.getPduration());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                            if (currentDate.compareTo(endDatePost) == 1) {
                                pId.add(checkPost.getpId());
                            }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        bidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onDestroyFlag == false && listenerCounter == 0) {
                    List<Bid> bids = new ArrayList<Bid>();
                    if (pId.size() > 0) {
                        for (int i = 0; i < pId.size(); i++) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Bid checkBid = snapshot.getValue(Bid.class);
                                if (pId.get(i).equals(checkBid.getpId())) {
                                    bids.add(checkBid);
                                }
                            }

                            bids.clear();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // actionBar = getSupportActionBar();
        m_dp = findViewById(R.id.m_dp);
        u_name = findViewById(R.id.name);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        bottomNavigationView = findViewById(R.id.bottom_navagation);

        storageReference = getInstance().getReference();


        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query query1 = databaseReference.orderByChild("uid").equalTo(myUid);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    myName = "" + ds.child("name").getValue();
                    myDp = "" + ds.child("image").getValue();
                    userTpye = "" + ds.child("Type").getValue();
                    Utype.setText(userTpye);


                    if (userTpye.equals("Buyer")){
                        hideItem();


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        navigationView = findViewById(R.id.navigation_view);
        navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        navView = navigationView.getHeaderView(0);
        name = navView.findViewById(R.id.n_name);
        email = navView.findViewById(R.id.n_email);
        dp =  navView.findViewById(R.id.n_dp);
        name.setText(myName);
        email.setText(myEmail);
        try {
            Picasso.get().load(myDp).into(dp);
        }
        catch (Exception e){
                 Picasso.get().load(R.drawable.ic_add_image).into(dp);

        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelectced(menuItem);
                return false;
            }
        });


        BottomNavigationView navigationView1 = findViewById(R.id.bottom_navagation);
        navigationView1.setOnNavigationItemSelectedListener(selectedListener);

       // actionBar.setTitle("Live Auction");
        LiveAuctions fragment1 = new LiveAuctions();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.container, fragment1, "");
        ft1.commit();

        //upDateToken(FirebaseInstanceId.getInstance().getToken());

    }

    private void hideItem() {
        navigationView = findViewById(R.id.navigation_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_product).setVisible(false);
        nav_Menu.findItem(R.id.your_product).setVisible(false);
    }


    private void checkcurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            myEmail = user.getEmail();
            myUid = user.getUid();


            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", myUid);
            editor.apply();


        }else{
            startActivity(new Intent(MainActivity.this, login.class));
            finish();
        }
    }



    @Override
    protected void onResume() {
        checkcurrentUser();
        super.onResume();
    }

   /* public void upDateToken(String token){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(myUid).setValue(mToken);
    }
*/
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.live:
                           // actionBar.setTitle("Live Auction");
                            LiveAuctions fragment1 = new LiveAuctions();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.container, fragment1, "");
                            ft1.commit();
                            return true;

                        case R.id.upcoming:
                           // actionBar.setTitle("Future Auction");
                            FutureAuctions fragment2 = new FutureAuctions();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.container, fragment2, "");
                            ft2.commit();
                            return true;
                    }
                    return false;
                }
            };



    protected void onStart() {

        super.onStart();


    }

    //Load Post method

    /*    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Post post = ds.getValue(Post.class);

                    postList.add(post);

                    adapterPost = new AdapterPost(MainActivity.this, postList);
                    recyclerView.setAdapter(adapterPost);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/




    //Search Post method

 /*   private void searchPosts(final String searchQuery){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Post post = ds.getValue(Post.class);


                    //if search by words from description then use this in if statement,
                    // || post.getpDesc().toLowerCase().contains(searchQuery.toLowerCase() )
                    if(post.getpTitle().toLowerCase().contains(searchQuery.toLowerCase())){
                        postList.add(post);
                    }


                    adapterPost = new AdapterPost(MainActivity.this, postList);
                    recyclerView.setAdapter(adapterPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
*/

    //search post handler method
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s)){
                    searchPosts(s);
                }
                else {
                    loadPosts();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!TextUtils.isEmpty(s)){
                    searchPosts(s);
                }
                else {
                    loadPosts();

                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }*/

//navigation menu name and image

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        try {

            Picasso.get().load(String.valueOf(m_dp)).into(m_dp);

        } catch (Exception e) {
            Picasso.get().load(R.drawable.ic_add_image).into(m_dp);

        }



        return super.onOptionsItemSelected(item);
    }


    //navigation menu item handler

    public void UserMenuSelectced(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                break;

            case R.id.nav_order:
                startActivity(new Intent(getApplicationContext(), order_history.class));
                break;

            case R.id.nav_bid:
                startActivity(new Intent(getApplicationContext(), bidhistory.class));
                break;

            case R.id.nav_product:
                startActivity(new Intent(getApplicationContext(), sellproduct.class));
                break;

                case R.id.your_product:
                startActivity(new Intent(getApplicationContext(), YourProduct.class));
                break;

            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), feedback.class));

                break;


            case R.id.about_us:
                startActivity(new Intent(getApplicationContext(), about_us.class));
                break;

            case R.id.change_password:
                startActivity(new Intent(getApplicationContext(), change_password.class));

                break;

            //case R.id.change_language:
              //  break;

            case R.id.delete_account:
                startActivity(new Intent(getApplicationContext(), delete_user.class));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
                break;


        }
    }


    private class SectionsPagerAdapter extends PagerAdapter {
        public SectionsPagerAdapter(FragmentManager supportFragmentManager) {
            super();

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyFlag = true;
    }
}
