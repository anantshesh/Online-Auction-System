package com.example.apnabazaar.myfolder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.apnabazaar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class update_profile extends AppCompatActivity {

    EditText name, phone, city, state, pincode;
    ImageView image;
    Button post;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    Uri image_riu = null;

    ProgressDialog pd;

    String name1, email, uid, dp;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    String[] cameraPermission;
    String[] storagePremission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        //preferences =      getSharedPreferences("apna_bazaar",MODE_PRIVATE);
        //editor  = preferences.edit();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePremission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.district);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pincode);

        image = findViewById(R.id.m_dp);

        post = findViewById(R.id.update);

        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name = name.getText().toString().trim();
                String u_phone = phone.getText().toString().trim();
                String u_city = city.getText().toString().trim();
                String u_state = state.getText().toString().trim();
                String u_pin = pincode.getText().toString().trim();


                if(TextUtils.isEmpty(u_name)){
                    Toast.makeText(update_profile.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(u_phone)){
                    Toast.makeText(update_profile.this, "Enter your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(u_city)){
                    Toast.makeText(update_profile.this, "Enter your CIty/District", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(u_state)){
                    Toast.makeText(update_profile.this, "Enter your State", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(u_pin)){
                    Toast.makeText(update_profile.this, "Enter your Pin-code", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(image_riu == null) {
                    //uploadData(m_title, m_description, m_quantity,  m_price, m_date, m_time, m_place, "noImage");
                    Toast.makeText(update_profile.this, "You need to upload your Image ", Toast.LENGTH_SHORT).show();
                }

                else{

                    // pass the m_quantity parameter in the next line
                    uploaddata(u_name, u_phone, u_city, u_state, u_pin, String.valueOf(image_riu));
                }



            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

    }

    private void uploaddata(final String u_name, final String u_phone, final String u_city, final String u_state, final String u_pin, final String uri) {
        pd.setMessage("Uploading your Details...");
        pd.show();

        //editor.putString("uid", uid);
        //editor.putString("email", email);
        //editor.putString("name", u_name);
        //editor.putString("phone", u_phone);
        //editor.putString("district", u_city);
        //editor.putString("state", u_state);
        //editor.putString("pincode", u_pin);




        String filePathAndName = "User/";

        if(!uri.equals("noImage")){
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if(uriTask.isSuccessful()){
                                    HashMap<Object, String> hashMap = new HashMap<>();
                                //editor.putString("image", downloadUri );
                                //editor.apply();
                                //editor.commit();
                                    hashMap.put("uid", uid);
                                    hashMap.put("email", email);
                                    hashMap.put("name", u_name);
                                    hashMap.put("phone", u_phone);
                                    hashMap.put("district", u_city);
                                    hashMap.put("state", u_state);
                                    hashMap.put("pincode", u_pin);
                                    hashMap.put("image", downloadUri );


                                databaseReference = FirebaseDatabase.getInstance().getReference("User");
                                databaseReference.setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(update_profile.this,"Profile details uploaded",Toast.LENGTH_SHORT).show();
                                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(update_profile.this,"" +e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                    Toast.makeText(update_profile.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        else{
            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("email", email);
            hashMap.put("name", u_name);
            hashMap.put("phone", u_phone);
            hashMap.put("district", u_city);
            hashMap.put("state", u_state);
            hashMap.put("pincode", u_pin);
            hashMap.put("image", "noImage" );


            databaseReference = FirebaseDatabase.getInstance().getReference("User");
            databaseReference.setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(update_profile.this,"Profile details uploaded",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(update_profile.this,"" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });







        }



    }


    private void checkUserStatus() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        } else {
            startActivity(new Intent(this, login.class));
            finish();
        }
    }


    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallary"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if(!checkCameraPermission()) {
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if(which==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromGallary();
                    }

                }
            }
        });
        builder.create().show();
    }

    private void pickFromGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Desc");
        image_riu = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_riu);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;

    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePremission, STORAGE_REQUEST_CODE);
    }



    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Camera & Storage Permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallary();
                    }
                    else{
                        Toast.makeText(this, "Storage Permissions required", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image_riu = data.getData();

                image.setImageURI(image_riu);

            }

            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                image.setImageURI(image_riu);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}












