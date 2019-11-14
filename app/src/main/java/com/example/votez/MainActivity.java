package com.example.votez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText signInEmail, password, userEmail, password_sign_up, forget_email;
    LinearLayout signIn, signUp, forget_;
    Button signInBtn, signUpBtn, forgetBtn;
    FirebaseAuth firebaseAuth;
    DatabaseReference mdatabase;
    FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private HashMap<String, Object> pushmap = new HashMap<>();
    private DatabaseReference pushinto = _firebase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        mdatabase=FirebaseDatabase.getInstance().getReference("Users");
        signInEmail= findViewById(R.id.signInEmail);
        password = findViewById(R.id.password);
        password_sign_up = findViewById(R.id.password_sign_up);
        userEmail = findViewById(R.id.user_email);
        forget_email = findViewById(R.id.forget_email);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        forget_ = findViewById(R.id.forget_);
        signInBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgetBtn = findViewById(R.id.forgetBtn);
        android.graphics.drawable.GradientDrawable g24=new android.graphics.drawable.GradientDrawable();
        g24.setCornerRadius(60);
        g24.setColor(Color.parseColor("#4FC3F7"));
        signInBtn.setBackground(g24);
        signUpBtn.setBackground(g24);
        forgetBtn.setBackground(g24);

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    finishAffinity();
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
            }
        };
    signUpBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user_register();
        }
    });

    signInBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user_login();
        }
    });
    }

    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void user_register() {
       final String email=userEmail.getText().toString();
      final String  pass=password_sign_up.getText().toString();
      final String username;
      String user="";
       int i=0;
       char ch[]=email.toCharArray();
       while (ch[i]!='@')
       {
           user+=ch[i];
           i++;
       }
       username=user;
       User.showUserName=username;
       User.showUserEmail=email;

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) ||TextUtils.isEmpty(username))
        {
            Toast.makeText(MainActivity.this,"Some Fields Are Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                  User newUser=new User(username,email,pass);
                  mdatabase.child(username).setValue(newUser);
                    Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void user_login() {
       String email=signInEmail.getText().toString();
       String pass=password.getText().toString();
        String user="";
        int i=0;
        char ch[]=email.toCharArray();
        while (ch[i]!='@')
        {
            user+=ch[i];
            i++;
        }
        User.showUserName=user;
        User.showUserEmail=email;
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(MainActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(MainActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Email or Password is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void signInFrag(View view) {
        signUp.setVisibility(View.GONE);
        signIn.setVisibility(View.VISIBLE);
        forget_.setVisibility(View.GONE);
    }

    public void SignUpFrag(View view) {
        signIn.setVisibility(View.GONE);
        signUp.setVisibility(View.VISIBLE);
        forget_.setVisibility(View.GONE);
    }

    public void ForgetFrag(View view) {
        signIn.setVisibility(View.GONE);
        signUp.setVisibility(View.GONE);
        forget_.setVisibility(View.VISIBLE);
    }

    public void mainFrag(View view) {
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }
}
