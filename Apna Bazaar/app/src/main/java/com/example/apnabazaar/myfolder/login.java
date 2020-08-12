package com.example.apnabazaar.myfolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apnabazaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity {


    EditText m_email, password, m_name, m_phone, m_district, m_state, m_pincode;
    TextView register_b, forgot_password;
    Button login_b;
    ImageView m_dp;
    ProgressBar progressBar;



    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
//            LOGS USER IN ONCE IT FINDS HE HAD LOGGED IN!
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }







        m_email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        progressBar = findViewById(R.id.progress_login);
        m_dp = findViewById(R.id.dp);
        register_b = findViewById(R.id.register_login);
        forgot_password= findViewById(R.id.forgot_password);
        m_name = findViewById(R.id.name);
        m_phone = findViewById(R.id.phone);
        m_district = findViewById(R.id.district);
        m_state = findViewById(R.id.state);
        m_pincode = findViewById(R.id.pincode);




        login_b = findViewById(R.id.login_button);

       /*SharedPreferences pref = getSharedPreferences("apna_bazaar",MODE_PRIVATE);
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


        login_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = m_email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(memail)){
                    m_email.setError("Invalid Email");
                    return;
                }

                if(TextUtils.isEmpty(mpassword)){
                    password.setError("Invalid Password");
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                String email = user.getEmail();
                                String uid = user.getUid();

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("email", email);
                                hashMap.put("uid", uid);
                                hashMap.put("name", "");
                                hashMap.put("phone", "");
                                hashMap.put("district", "");
                                hashMap.put("state", "");
                                hashMap.put("pincode", "");
                                hashMap.put("Type","");
                                hashMap.put("image", "");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                DatabaseReference reference = database.getReference("User");

                                reference.child(uid).setValue(hashMap);

                            }

                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT ).show();
                            startActivity(new Intent(login.this, MainActivity.class));
                        } else {
                            Toast.makeText(login.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });



            }
        });


        register_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });


    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText memail = new EditText(this);
        memail.setHint("Email");
        memail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        memail.setMinEms(16);

        linearLayout.addView(memail);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_email = memail.getText().toString().trim();
                beginRecovery(m_email);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void beginRecovery(String m_email) {
        firebaseAuth.sendPasswordResetEmail(m_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(login.this, "Email Sent!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(login.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
