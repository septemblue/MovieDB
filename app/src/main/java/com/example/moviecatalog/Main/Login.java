package com.example.moviecatalog.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalog.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://moviedb-a8d94-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(it -> {
            final String phoneTxt = phone.getText().toString();
            final String passwordTxt = password.getText().toString();

            if (phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(Login.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
            } else {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phoneTxt)) {
                            final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                            if (getPassword.equals(passwordTxt)) {
                                Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                            } else {
                                Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        registerNowBtn.setOnClickListener(it -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }
}