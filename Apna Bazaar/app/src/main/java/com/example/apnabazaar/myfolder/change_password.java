package com.example.apnabazaar.myfolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apnabazaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class change_password extends AppCompatActivity {

    private static final String TAG = "change_password";
    EditText editText;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        dialog = new ProgressDialog(this);
        button = findViewById(R.id.buttonchangepassword);
        editText = findViewById(R.id.password_change);
        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePassword();
            }
        });

    }

    public void updatePassword(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String password = editText.getText().toString().trim();

        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Log.d(TAG, "User password updated");
                    Toast.makeText(change_password.this, "Password Changed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }









}
