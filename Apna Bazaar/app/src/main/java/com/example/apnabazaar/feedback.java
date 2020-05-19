package com.example.apnabazaar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class feedback extends AppCompatActivity {

    EditText name, email, subject, message;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.feedname);
        email = findViewById(R.id.feedemail);
        subject = findViewById(R.id.feedsubject);
        message = findViewById(R.id.feedmessage);

        submit = findViewById(R.id.submit);



    }

    public void send_click(View v){
        if (name.getText().toString().equals("")){
            name.setError("Name Required");
        }
        else if (email.getText().toString().equals("")){
            email.setError("Email address Required");
        }
        else if (subject.getText().toString().equals("")){
            subject.setError("Subject Required");
        }
        else if (message.getText().toString().equals("")){
            message.setError("Message Required");
        }
        else{
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {"anantsheshk@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT, "Hello Apna Bazaar \n" + message.getText().toString() + "\n Regards," + email.getText().toString());

            try {
                startActivity(Intent.createChooser(i, "send mail"));
            }
            catch (android.content.ActivityNotFoundException e){
                Toast.makeText(this, "No Mail app Found", Toast.LENGTH_SHORT).show();

            }
            catch (Exception e){
                Toast.makeText(this, "Unexpected error" +e.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }

}
