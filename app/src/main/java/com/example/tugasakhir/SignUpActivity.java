package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView backlogin, signup;
    private EditText nama, email, telfon, pass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();

        backlogin = (TextView) findViewById(R.id.backlogin);
        backlogin.setOnClickListener(this);

        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        nama = (EditText) findViewById(R.id.nama);
        email = (EditText) findViewById(R.id.email);
        telfon = (EditText) findViewById(R.id.telfon);
        pass = (EditText) findViewById(R.id.pass);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backlogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.signup:
                signups();
                break;
        }
    }

    private void signups(){
        String name = nama.getText().toString().trim();
        String gmail = email.getText().toString().trim();
        String notelp = telfon.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if(name.isEmpty()){
            nama.setError("Name is Required!");
            nama.requestFocus();
            return;
        }

        if(gmail.isEmpty()){
            email.setError("Email is Required!");
            email.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
            email.setError("Your Email is not Valid!");
            email.requestFocus();
            return;
        }

        if(notelp.isEmpty()){
            telfon.setError("Mobile Number is Required!");
            telfon.requestFocus();
            return;
        }

        if(password.isEmpty()){
            pass.setError("Password is Required!");
            pass.requestFocus();
            return;
        }

        if(password.length()<6){
            pass.setError("Password Must Be 6 Characters Long!");
            pass.requestFocus();
            return;
        }
    }
}