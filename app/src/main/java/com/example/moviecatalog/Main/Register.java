package com.example.moviecatalog.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://moviedb-a8d94-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final EditText comPassword = findViewById(R.id.comPassword);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        registerBtn.setOnClickListener(it -> {
            final String fullnameTxt = fullname.getText().toString();
            final String emailTxt = email.getText().toString();
            final String phoneTxt = phone.getText().toString();
            final String passwordTxt = password.getText().toString();
            final String comPasswordTxt = comPassword.getText().toString();


            if (fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!passwordTxt.equals(comPasswordTxt)) {
                Toast.makeText(Register.this, "Password does not match", Toast.LENGTH_SHORT).show();
            } else {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(phoneTxt)) {
                            Toast.makeText(Register.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                            databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);

                            Toast.makeText(Register.this, "User Registered  successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        loginNowBtn.setOnClickListener(it -> {
            finish();
        });

    }


}