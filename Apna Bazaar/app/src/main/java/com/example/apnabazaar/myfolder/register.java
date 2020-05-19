package com.example.apnabazaar.myfolder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnabazaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class register extends AppCompatActivity {


    EditText  email, password, m_email, m_name, m_phone, m_district, m_state, m_pincode;;
    Button register_b;
    TextView login_b;
    ImageView m_dp;
    Spinner spinner;
    String type;
    private FirebaseAuth firebaseAuth;

    ProgressBar progressBar;

    protected DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);

        progressBar = findViewById(R.id.progressregister);
        register_b = findViewById(R.id.register_button);
        login_b = findViewById(R.id.login_register);
        spinner = findViewById(R.id.sellorbuy);
        ArrayAdapter<CharSequence> adapterCategoryOfItem = ArrayAdapter.createFromResource(register.this, R.array.type, android.R.layout.simple_spinner_item);
        adapterCategoryOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoryOfItem);

        firebaseAuth = FirebaseAuth.getInstance();


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
        m_pincode.setText(pref.getString("pincode",""));
*/

        /*if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        } */


        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });

        register_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memail = email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();
                type = spinner.getSelectedItem().toString();


                if (TextUtils.isEmpty(memail)) {
                    email.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(mpassword)) {
                    password.setError("Invalid Password");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                //Register user to firebase

                firebaseAuth.createUserWithEmailAndPassword(memail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", "");
                            hashMap.put("phone", "");
                            hashMap.put("district", "");
                            hashMap.put("Type",type);
                            hashMap.put("state", "");
                            hashMap.put("pincode", "");
                            hashMap.put("image", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("User");

                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(register.this, MainActivity.class));

                        } else {
                            Toast.makeText(register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }


}



