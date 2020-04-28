package com.example.apnabazaar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class Profile extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    //String storagePath = "Users/";

    ImageView m_dp;
    TextView m_email, m_name, m_phone, m_district, m_state, m_pincode;
    ProgressDialog pd;
    Button m_fab;


    //String uId;
    //String myUid;


    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    String profilePhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        storageReference = getInstance().getReference("User/");

        m_dp = findViewById(R.id.dp);
        m_name = findViewById(R.id.name);
        m_phone = findViewById(R.id.phone);
        m_district = findViewById(R.id.district);
        m_state = findViewById(R.id.state);
        m_pincode = findViewById(R.id.pincode);
        m_email = findViewById(R.id.email);
        m_fab = findViewById(R.id.fab);

       /* SharedPreferences pref = getSharedPreferences("apna_bazaar",MODE_PRIVATE);
        try{
            Picasso.get().load(pref.getString("image","")).into(m_dp);
        }
        catch (Exception e){
            Picasso.get().load(R.drawable.ic_add_image).into(m_dp);
        }
        m_email.setText(pref.getString("email",""));
        m_name.setText(pref.getString("name",""));
        m_phone.setText(pref.getString("phone",""));
        m_district.setText(pref.getString("district",""));
        m_state.setText(pref.getString("state",""));
        m_pincode.setText(pref.getString("pincode",""));*/





        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};



       // storageReference = getInstance().getReference();



        pd = new ProgressDialog(Profile.this);

       Query query = databaseReference.orderByChild("uid").equalTo(firebaseUser.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = "" + ds.child("email").getValue();
                    String name = "" + ds.child("name").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String district = "" + ds.child("district").getValue();
                    String state = "" + ds.child("state").getValue();
                    String pin_code = "" + ds.child("pincode").getValue();
                    String dp = "" + ds.child("image").getValue();


                    m_email.setText(email);
                    m_name.setText(name);
                    m_phone.setText(phone);
                    m_district.setText(district);
                    m_state.setText(state);
                    m_pincode.setText(pin_code);


                    try{
                        Picasso.get().load(dp).into(m_dp);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_add_image).into(m_dp);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        m_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });





    }

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){

       ActivityCompat.requestPermissions(Profile.this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){

        boolean result = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){

        ActivityCompat.requestPermissions(Profile.this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private void showEditProfileDialog() {

        String option[]= {"Edit Profile Image","Edit Name", "Edit Phone number", "Edit District", "Edit State" , "Edit Pin-code"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Choose option");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                      if(which == 0 ){
                        //change profile
                          pd.setMessage("Updating Profile Image");
                            profilePhoto = "image";
                          showImagePicDialog();

                      }
                      else if(which == 1){
                        //change name
                          pd.setMessage("Updating Name");
                          showNamePhoneUpdateDialog("name");

                      }

                      else if(which == 2){
                        //change phone number
                          pd.setMessage("Updating Phone Number");
                          showNamePhoneUpdateDialog("phone");

                      }

                      else if(which == 3){
                        //change district
                          pd.setMessage("Updating District");
                          showNamePhoneUpdateDialog("district");

                      }

                      else if(which == 4){
                        //change state
                          pd.setMessage("Updating State");
                          showNamePhoneUpdateDialog("state");

                      }

                      else if(which == 5){
                        //change pin code
                          pd.setMessage("Updating Pin-code");
                          showNamePhoneUpdateDialog("pincode");

                      }


            }
        });

        builder.create().show();

    }

    private void showNamePhoneUpdateDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Update "+ key);

        LinearLayout linearLayout = new LinearLayout(Profile.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);


        final EditText editText = new EditText(Profile.this);

        editText.setHint("Enter "+ key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String value = editText.getText().toString().trim();

                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(Profile.this,"Updated" , Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(Profile.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            });

                  if(key.equals("name")){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                        Query query = ref.orderByChild("uid").equalTo(firebaseUser.getUid());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String child = ds.getKey();
                                    dataSnapshot.getRef().child(child).child("uName").setValue(value);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for( DataSnapshot ds: dataSnapshot.getChildren()){
                                    String child = ds.getKey();
                                    if(dataSnapshot.child(child).hasChild("Bidders")){
                                        String child1 = "" + dataSnapshot.child(child).getKey();
                                        Query child2 = FirebaseDatabase.getInstance().getReference("Posts").child(child1).child("Bidders").orderByChild("uid").equalTo(firebaseUser.getUid());
                                        child2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                                    String child = ds.getKey();
                                                    dataSnapshot.getRef().child(child).child("uName").setValue(value);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                  //
                    if(key.equals("phone")){
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Posts");
                        Query query1 = ref1.orderByChild("uid").equalTo(firebaseUser.getUid());
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String child = ds.getKey();
                                    dataSnapshot.getRef().child(child).child("phone").setValue(value);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for( DataSnapshot ds: dataSnapshot.getChildren()){
                                    String child = ds.getKey();
                                    if(dataSnapshot.child(child).hasChild("Bidders")){
                                        String child1 = "" + dataSnapshot.child(child).getKey();
                                        Query child2 = FirebaseDatabase.getInstance().getReference("Posts").child(child1).child("Bidders").orderByChild("uid").equalTo(firebaseUser.getUid());
                                        child2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                                    String child = ds.getKey();
                                                    dataSnapshot.getRef().child(child).child("phone").setValue(value);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                }
                else{
                    Toast.makeText(Profile.this,"Please enter" +key, Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }

    private void showImagePicDialog() {
        String option[]= {"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Select Image From");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0 ){
                    //Camera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }

                }
                else if(which == 1){
                    //Gallery
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromGallery();
                    }


                }



            }
        });

        builder.create().show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {

                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(Profile.this, "Please enable Camera and Storage permission", Toast.LENGTH_SHORT).show();

                    }
                }
            } break;
            case STORAGE_REQUEST_CODE:{

                if(grantResults.length >0){

                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {

                        pickFromGallery();
                    }
                    else{
                        Toast.makeText(Profile.this, "Please enable Storage permission", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){

            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();

                uploadProfilePhoto(image_uri);

            }

            if(requestCode == IMAGE_PICK_CAMERA_CODE){

                uploadProfilePhoto(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfilePhoto(Uri uri) {

        pd.show();
        String filePathAndName = storageReference+ "" + profilePhoto+ "_" + firebaseUser.getUid();

        StorageReference storageReference1 = storageReference.child(filePathAndName);
        storageReference1.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        final Uri downloadUri = uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String, Object> result = new HashMap<>();
                            result.put(profilePhoto, downloadUri.toString());

                            databaseReference.child(firebaseUser.getUid()).updateChildren(result)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            pd.dismiss();
                                            Toast.makeText(Profile.this, "Image Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(Profile.this, "Error Updating Image", Toast.LENGTH_SHORT).show();

                                }
                            });

                            if(profilePhoto.equals("image")){
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                                Query query = ref.orderByChild("uid").equalTo(firebaseUser.getUid());
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       for(DataSnapshot ds: dataSnapshot.getChildren()){
                                           String child = ds.getKey();
                                           dataSnapshot.getRef().child(child).child("uDp").setValue(downloadUri.toString());
                                       }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for( DataSnapshot ds: dataSnapshot.getChildren()){
                                            String child = ds.getKey();
                                            if(dataSnapshot.child(child).hasChild("Bidders")){
                                                String child1 = ""+dataSnapshot.child(child).getKey();
                                                Query child2 = FirebaseDatabase.getInstance().getReference("Posts").child(child1).child("Bidders").orderByChild("uid").equalTo(firebaseUser.getUid());
                                                child2.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                                                            String child = ds.getKey();
                                                            dataSnapshot.getRef().child(child).child("uDp").setValue(downloadUri.toString());
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(Profile.this, "Some Error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Description");

        image_uri = Profile.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            m_email.setText(user.getEmail());


        }

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }


}
