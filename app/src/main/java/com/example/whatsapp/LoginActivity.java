package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    //<---------------------------Others Variable--------------------------------------->


    private ProgressDialog loadinBar;





    //<-------------------------Firebase Variable-------------------------------->

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;







    private Button loginButton,phoneLoginButton;
    private EditText userEmail,userPassword;
    private TextView needNewAccount,forgotPasswordLInk;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Initialize();
        mAuth=FirebaseAuth.getInstance();
        needNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        sendUserToRegisterActivity();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogin();
            }
        });










    }

    private void allowUserToLogin() {

        String email=userEmail.getText().toString();
        String password=userPassword.getText().toString();

        if(email.isEmpty()){
            userEmail.setError("Please Enter An email  .");
            userEmail.requestFocus();
            return;
        }else if(password.isEmpty()){
            userPassword.setError("Please Enter  A Password  .");
            userPassword.requestFocus();
            return;
        }else{

            loadinBar.setTitle("Please Wait");
            loadinBar.setMessage("Logging in");
            loadinBar.setCanceledOnTouchOutside(true);
            loadinBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendUserToMainActivity();
                      Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        loadinBar.dismiss();
                    }else{
                        loadinBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Something Problem : "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }







    }

    private void sendUserToMainActivity() {

        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void sendUserToRegisterActivity() {



        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);



    }


    private void Initialize() {


        loadinBar=new ProgressDialog(LoginActivity.this);

        loginButton=findViewById(R.id.login_Button);
        userEmail=findViewById(R.id.login_email);
        userPassword=findViewById(R.id.login_password);

        phoneLoginButton=findViewById(R.id.phone_login_Button);
        forgotPasswordLInk=findViewById(R.id.forget_PasswordLink);
        needNewAccount=findViewById(R.id.need_New_Account);




    }






}