package com.example.apnabazaar.myfolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apnabazaar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class delete_user extends AppCompatActivity {

    Button deleteaccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        deleteaccount = findViewById(R.id.delete);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(delete_user.this);
                dialog.setTitle("Are You Sure?");
                dialog.setMessage("Deleting your account will result in completely removing your account from the system and you won't be able to access this account");
                 dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(delete_user.this, "Account Deleted", Toast.LENGTH_LONG ).show();
                                    Intent intent = new Intent(delete_user.this, login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else{
                                    Toast.makeText(delete_user.this, task.getException().getMessage()   , Toast.LENGTH_LONG ).show();


                                }
                             }
                         });
                     }
                 });
                 dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                     }
                 });

                 AlertDialog alertDialog = dialog.create();
                 alertDialog.show();

            }
        });
    }

}
