package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText txtName, txtNIC, txtEmail, txtPass, txtPhone;
    Button btnRegister;
    TextView login;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        txtName = findViewById(R.id.txtName);
        txtNIC = findViewById(R.id.txtNIC);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtPhone = findViewById(R.id.txtPhone);

        progressBar = findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login:
                    startActivity(new Intent(this, Login.class));
                    break;
                case R.id.btnRegister:
                    registerUser();
                    break;
            }
    }

    private void registerUser() {
        String name = txtName.getText().toString().trim();
        String NIC = txtNIC.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String Email = txtEmail.getText().toString().trim();
        String Password = txtPass.getText().toString().trim();

        if (name.isEmpty()){
            txtName.setError("Name is required!");
            txtName.requestFocus();
            return;
        }

        if (NIC.isEmpty()){
            txtNIC.setError("NIC is required!");
            txtNIC.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            txtPhone.setError("Phone number is required!");
            txtPhone.requestFocus();
            return;
        }

        if (Email.isEmpty()){
            txtEmail.setError("Email is required!");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            txtEmail.setError("Please provide valid email!");
            txtEmail.requestFocus();
            return;
        }

        if (Password.isEmpty()){
            txtPass.setError("Password is required!");
            txtPass.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            txtPass.setError("Password must contain 6 characters!");
            txtPass.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(name,NIC,phone,Email,Password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "User successfully registered!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        // redirect to Login layout
                                        startActivity(new Intent(Register.this,Login.class));

                                    }else {
                                        Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}