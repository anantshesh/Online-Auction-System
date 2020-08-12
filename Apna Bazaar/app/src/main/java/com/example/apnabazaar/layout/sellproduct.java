package com.example.apnabazaar.layout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.apnabazaar.R;
import com.example.apnabazaar.myfolder.MainActivity;
import com.example.apnabazaar.myfolder.login;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class sellproduct extends AppCompatActivity {

    private int current_year;
    private int current_month;
    private int current_date;
    private Button setTimeButton;
    private TextView year;
    private TextView month;
    private TextView date;
    private Spinner hours;
    private Spinner mins;
    private Spinner duration;
    private Spinner category;
    private Spinner place;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String mYear;
    private String mMonth;
    private String mDate;
    private String mHours;
    private String mMins;
    private String mCategory;
    private String mplace;
    private String mDuration;
    private SimpleDateFormat mdformat;
    private ProgressDialog progressDialog;
    Date currentdate = null;
    public Date enteredStartDate = null;
    public Date enteredEndDate = null;
    private int count = 0;



    EditText title, quantity, description, min_amt, upi;
    TextView datePicked, timePicked;
   TextView durationEt;



    ImageView image;
    Button post, time;
    Uri image_rui = null;
    Context context;









    LocalDate currentDate;

   private int mDay, mHour, mMinute, mSecond;


    ProgressDialog pd;

    String name, email, uid, dp, phone,city, state, pincode, highest_bid;

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;

    private DatePicker datePicker;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;

    String[] cameraPermission;
    String[] storagePremission;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellproduct);


        mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        year =  findViewById(R.id.start_time_year);
        month =  findViewById(R.id.start_time_month);
        date =  findViewById(R.id.start_time_date);
        hours = findViewById(R.id.start_time_hours);
        mins =  findViewById(R.id.start_time_mins);
        duration = findViewById(R.id.duration);
        category = (Spinner) findViewById(R.id.categoryOfItem);
        place = (Spinner) findViewById(R.id.CityOfItem);
        setTimeButton = findViewById(R.id.set_time_button);
        ArrayAdapter<CharSequence> adapterhours = ArrayAdapter.createFromResource(sellproduct.this, R.array.hours, android.R.layout.simple_spinner_item);
        adapterhours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adaptermins = ArrayAdapter.createFromResource(sellproduct.this, R.array.mins, android.R.layout.simple_spinner_item);
        adaptermins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterduration = ArrayAdapter.createFromResource(sellproduct.this, R.array.duration, android.R.layout.simple_spinner_item);
        adapterduration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterCategoryOfItem = ArrayAdapter.createFromResource(sellproduct.this, R.array.category_of_item, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapterCategoryOfItem);

        ArrayAdapter<CharSequence> adapterCityOfItem = ArrayAdapter.createFromResource(sellproduct.this, R.array.city_of_item, android.R.layout.simple_spinner_item);
        adapterCityOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place.setAdapter(adapterCityOfItem);

        hours.setAdapter(adapterhours);
        mins.setAdapter(adaptermins);
        duration.setAdapter(adapterduration);
        mHours = hours.getSelectedItem().toString();
        mMins = mins.getSelectedItem().toString();
        mCategory = category.getSelectedItem().toString();
        mplace = place.getSelectedItem().toString();

        hours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mHours = hours.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMins = mins.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDuration = duration.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCategory = category.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mplace = place.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



       // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        //final String currentDateTime = sdf.format(new Date());



        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePremission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        title = findViewById(R.id.product_name);
        description = findViewById(R.id.description);
        min_amt = findViewById(R.id.min_price);

        quantity = findViewById(R.id.quantity);
        quantity = findViewById(R.id.quantity);
        //place = findViewById(R.id.city);
        upi = findViewById(R.id.upiid);

      //date time and duration

        //final Spinner spinner = (Spinner) findViewById(R.id.duration);








       currentDate = LocalDate.now(ZoneId.systemDefault());





       // date = findViewById(R.id.startDate);
      // time = findViewById(R.id.startTime);

      // datePicked = findViewById(R.id.date);
       // timePicked = findViewById(R.id.time);

      //date.setOnClickListener(this);

      //time.setOnClickListener(this);




        image = findViewById(R.id.image);

        post = findViewById(R.id.postbutton);

        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        userDbRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    name = ""+ ds.child("name").getValue();
                    email = ""+ ds.child("email").getValue();
                    dp = ""+ ds.child("image").getValue();
                    phone = ""+ds.child("phone").getValue();
                    city = "" +ds.child("district").getValue();
                    state = ""+ds.child("state").getValue();
                    pincode = ""+ds.child("pincode").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                try {
                    currentdate = mdformat.parse(mdformat.format(c.getTime()));
                } catch (Exception c1) {
                    c1.printStackTrace();
                }
                current_date = c.get(Calendar.DAY_OF_MONTH);
                current_month = c.get(Calendar.MONTH);
                current_year = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(sellproduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        year.setText(String.valueOf(i));
                        month.setText(String.valueOf(i1 + 1));
                        date.setText(String.valueOf(i2));
                    }
                }, current_year, current_month, current_date);
                datePickerDialog.show();

            }
        });



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredDateString = year.getText().toString() + "/" + month.getText().toString() + "/" + date.getText().toString()
                        + " " + mHours + ":" + mMins;
                try {
                    enteredStartDate = mdformat.parse(enteredDateString);
                    Calendar cal = Calendar.getInstance(); // creates calendar
                    cal.setTime(enteredStartDate); // sets calendar time/date
                    cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + Integer.parseInt(mDuration));
                    System.out.println("checkff" + mdformat.format(cal.getTime()));
                    enteredEndDate = cal.getTime();
                } catch (Exception c) {
                    c.printStackTrace();
                }






                final String m_title = title.getText().toString().trim();
                final String m_price = min_amt.getText().toString().trim();
                final String m_quantity = quantity.getText().toString().trim();
                final String upiid = upi.getText().toString().trim();
                final String m_description = description.getText().toString().trim();
                final int Count = 0;


                //String duration_s = duration.getSelectedItem().toString();
                //String m_date = datePicked.getText().toString().trim();
               // String m_time = timePicked.getText().toString().trim();


                //String enteredDateTime = datePicked.getText().toString();
                //String m_spinner = spinner.getSelectedItem().toString();



                //String m_date_end = end_date.getText().toString().trim();
                //String m_time_end = end_time.getText().toString().trim();


                if (TextUtils.isEmpty(m_title)){
                    Toast.makeText(sellproduct.this, "Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(m_price)){
                    Toast.makeText(sellproduct.this, "Enter Minimum Bidding Amount", Toast.LENGTH_SHORT).show();
                    return;
                    }

                if (TextUtils.isEmpty(m_description)){
                    Toast.makeText(sellproduct.this, "Description is necessary", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(m_quantity)){
                    Toast.makeText(sellproduct.this, "Enter the number of Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(upiid)){
                    Toast.makeText(sellproduct.this, "Enter your UPI ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(image_rui == null){
                    //uploadData(m_title, m_description, m_quantity,  m_price, m_date, m_time, m_place, "noImage");
                    Toast.makeText(sellproduct.this, "You need to Post an image of your Product/s ", Toast.LENGTH_SHORT).show();
                }


                /*if (m_date == null ){
                    Toast.makeText(sellproduct.this, "Enter Auction Start Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (m_time == null){
                    Toast.makeText(sellproduct.this, "Enter Auction Start Time", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (enteredStartDate == null) {
                    Toast.makeText(sellproduct.this, "Please enter start bidding date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentdate.compareTo(enteredStartDate) == 1) {
                    Toast.makeText(sellproduct.this, "Entered date is less than current date!", Toast.LENGTH_SHORT).show();
                    year.setText("0000");
                    month.setText("00");
                    date.setText("00");
                    return;
                }

                /*if(currentDateTime.compareTo(m_date) == 1){
                    Toast.makeText(sellproduct.this , "Entered date is less than current date!", Toast.LENGTH_SHORT).show();
                    datePicked.setText("");
                    return;
                }

                if(currentDateTime.compareTo(m_time) == 1){
                    Toast.makeText(sellproduct.this , "Entered time is less than current date!", Toast.LENGTH_SHORT).show();
                    timePicked.setText("");
                    return;
                }*/




              /* if(duration_s == null){
                    Toast.makeText(sellproduct.this, "Choose the duration of the auction ", Toast.LENGTH_SHORT).show();
                    return;
                }

             /* if (TextUtils.isEmpty(m_date)){
                    Toast.makeText(sellproduct.this, "Enter Auction Start Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(m_date_end)){
                    Toast.makeText(sellproduct.this, "Enter Auction End Date", Toast.LENGTH_SHORT).show();
                    return;
                }*/


                else{

                    // pass the m_quantity parameter in the next line , m_date, m_time
                    //, m_date, m_time,m_date_end, m_time_end
                    uploadData(m_title, m_description, m_quantity, m_price,upiid, Count ,String.valueOf(enteredStartDate), String.valueOf(enteredEndDate),mCategory, mplace,  String.valueOf(image_rui));
              }


            }
        });







    }



    //Need to add final Sting m_quantity in the uploadData method below

    //, final String m_date, final String m_time, final String m_date_end, final String m_time_end

   private void uploadData(final String m_title, final String m_description, final String m_quantity, final String m_price,final String upiid,final int Count, final String enteredStartDate, final String enteredEndDate,final String mCategory, final String mplace, final String uri) {
        pd.setMessage("Posting Your Product...");
        pd.show();



        final String timeStamp = String.valueOf(System.currentTimeMillis());

        String filePathAndName = "Posts/" + "post_" + timeStamp;

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
                                hashMap.put("uid", uid);
                                hashMap.put("uName", name);
                                hashMap.put("uEmail", email);
                                hashMap.put("uDp", dp );
                                hashMap.put("pId", timeStamp );
                                hashMap.put("pTitle", m_title );
                                hashMap.put("aDateTime",(enteredStartDate));
                                hashMap.put("pQuantity", m_quantity );
                                hashMap.put("pDesc", m_description );
                                hashMap.put("pMinPrice", m_price );
                                hashMap.put("pCity", mplace );
                                hashMap.put("pCatogry", mCategory );
                                hashMap.put("pduration", String.valueOf(enteredEndDate));
                                hashMap.put("pImage", downloadUri );
                                hashMap.put("upiid", upiid);
                                hashMap.put("pTime", timeStamp);
                                hashMap.put("puphone", phone);
                                hashMap.put("pucity", city);
                                hashMap.put("pustate", state);
                                hashMap.put("pupincode", pincode);
                                hashMap.put("Count", String.valueOf(Count));


                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(sellproduct.this,"Post Published",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(sellproduct.this,"" +e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                    Toast.makeText(sellproduct.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{

            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("uName", name);
            hashMap.put("uEmail", email);
            hashMap.put("uDp", dp );
            hashMap.put("pId", timeStamp );
            hashMap.put("pTitle", m_title );
            hashMap.put("pQuantity", m_quantity);
            hashMap.put("pDesc", m_description );
            hashMap.put("Count", String.valueOf(Count));
            hashMap.put("pMinPrice", m_price );
            hashMap.put("aDateTime", String.valueOf(enteredStartDate));
            hashMap.put("pCity", mplace );
            hashMap.put("pCatogry", mCategory );
            hashMap.put("pduration", String.valueOf(enteredEndDate));
            hashMap.put("pImage", "noImage" );
            hashMap.put("pTime", timeStamp);
            hashMap.put("upiid", upiid);
            hashMap.put("puphone", phone);
            hashMap.put("pucity", city);
            hashMap.put("pustate", state);
            hashMap.put("pupincode", pincode);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(sellproduct.this,"Post Published",Toast.LENGTH_SHORT).show();
                            title.setText("");
                            description.setText("");
                            min_amt.setText("");
                            quantity.setText("");
                            year.setText("0000");
                            month.setText("00");
                            date.setText("00");


                            image.setImageURI(null);
                            image_rui = null;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(sellproduct.this,"" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }



    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            email = user.getEmail();
            uid = user.getUid();
        }
        else{
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
        image_rui = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_rui);
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
                image_rui = data.getData();

                image.setImageURI(image_rui);

            }

            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                image.setImageURI(image_rui);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    /*@Override
    public void onClick(View v) {
        if(v == date) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(sellproduct.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                   datePicked.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            },mYear, mMonth, mDay);
            datePickerDialog.show();

        }

        if (v == time) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            timePicked.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }*/
}
