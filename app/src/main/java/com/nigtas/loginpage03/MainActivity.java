package com.nigtas.loginpage03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editPassword, editUsername;
    private String txtEmail, txtPassword, txtUsername;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReferance;
    private HashMap<String, Object> mData;
    private FirebaseFirestore mFirestore;
    Button loginPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        editEmail = (EditText) findViewById(R.id.register_editEmail);
        editPassword = (EditText)findViewById(R.id.register_editPassword);
        editUsername = (EditText)findViewById(R.id.register_editUsername);
        tanimlama();

        mAuth = FirebaseAuth.getInstance();
        mReferance = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();

        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Register (View v){

        txtUsername = editUsername.getText().toString();
        txtEmail = editEmail.getText().toString();
        txtPassword = editPassword.getText().toString();

        if(!TextUtils.isEmpty(txtUsername) && !TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPassword)){
            mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                mUser = mAuth.getCurrentUser();

                                mData = new HashMap<>();
                                mData.put("userName", txtUsername);
                                mData.put("userEmail", txtEmail);
                                mData.put("userPassword", txtPassword);
                                mData.put("userUID", mUser.getUid());

                                mFirestore.collection("Users").document(mUser.getUid())
                                        .set(mData)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                 if(task.isSuccessful())
                                                     Toast.makeText(MainActivity.this, "User registiration is successful",Toast.LENGTH_SHORT).show();
                                                 else
                                                     Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
        }
        else {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void tanimlama(){
        loginPageButton=findViewById(R.id.register_loginPageButton);
    }


}