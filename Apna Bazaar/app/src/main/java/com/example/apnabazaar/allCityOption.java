package com.example.apnabazaar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnabazaar.adapters.allPostAdapter;
import com.example.apnabazaar.adapters.citySearchPostAdapter;
import com.example.apnabazaar.models.Post;
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

public class allCityOption extends AppCompatActivity {

    private List<Post> posts = new ArrayList<Post>();
    RecyclerView recyclerView;
    private citySearchPostAdapter citySearchPostAdapter;
    private allPostAdapter adapterPost;
    Button cityAll;
    LinearLayout citySearch;

    private Spinner city;
    private String mcity;

    private DatabaseReference postreference;
    Date currentDate = null;
    private SimpleDateFormat mdformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_city_option);

        city = findViewById(R.id.cityOfItem_liveAuctions);
        recyclerView = findViewById(R.id.citySearchRecycler);
        mdformat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        postreference = FirebaseDatabase.getInstance().getReference("Posts");
        ArrayAdapter<CharSequence> adapterCityOfItem = ArrayAdapter.createFromResource(this, R.array.search_of_city, android.R.layout.simple_spinner_item);
        adapterCityOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapterCityOfItem);
        posts.clear();
        mcity = city.getSelectedItem().toString();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

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
                                adapterPost = new allPostAdapter(allCityOption.this, "all", posts);
                                 recyclerView.setAdapter(adapterPost);
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
        });
    }
}
