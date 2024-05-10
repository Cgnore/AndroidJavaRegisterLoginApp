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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPassword, editUpdatedName;
    private String txtEmail, txtPassword, txtUpdatedName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRefererence;
    private FirebaseFirestore mFirestore;
    private HashMap<String, Object> mData;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.login_editEmail);
        editPassword = (EditText)findViewById(R.id.login_editPassword);
        editUpdatedName = (EditText)findViewById(R.id.login_editUpdatedName);


        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        Intent gelenIntent = getIntent();
    }

    public void Login(View view){
        txtEmail = editEmail.getText().toString();
        txtPassword = editPassword.getText().toString();

        if (!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPassword)){
            mAuth.signInWithEmailAndPassword(txtEmail,txtPassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();

                            assert mUser !=null;
                            verileriGetir(mUser.getUid());
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void verileriGetir (String uid) {
        docRef = mFirestore.collection("Users").document(uid);
        docRef.get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            System.out.println(documentSnapshot.getData());
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        /*mRefererence = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        mRefererence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snp : snapshot.getChildren())
                    System.out.println(snp.getKey() + " = " + snp.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void veriyiGuncelle(HashMap<String,Object> hashMap, final String uid){
        mFirestore.collection("Users").document(uid)
                .update(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Value Updated Succesfully!", Toast.LENGTH_SHORT).show();
                            System.out.println("---- Updated value ----");
                            verileriGetir(uid);
                        }
                    }
                });


        /*mRefererence = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        mRefererence.updateChildren(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Value Updated Succesfully!", Toast.LENGTH_SHORT).show();
                            System.out.println("---- Updated value ----");
                            verileriGetir(uid);
                        }
                    }
                });*/
    }

    public void updateName(View v){
        txtUpdatedName = editUpdatedName.getText().toString();

        if (!TextUtils.isEmpty(txtUpdatedName)){
            mData= new HashMap<>();
            mData.put("userName", txtUpdatedName);

            assert mUser !=null;
            veriyiGuncelle(mData, mUser.getUid());
        }
        else
            Toast.makeText(this, "Updating value cannot be empty!", Toast.LENGTH_SHORT).show();
    }
    public void deleteValue(View v){
        mFirestore.collection("Users").document(mUser.getUid())
                .delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(LoginActivity.this, "Data deleted succesfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        /*
        mRefererence = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        mRefererence.removeValue()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(LoginActivity.this, "Data deleted succesfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });*/
    }


}