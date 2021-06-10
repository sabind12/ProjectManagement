package com.sabin.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailLogin;
    EditText passwordLogin;
    Button loginButton;
    Button registerButton;
    SQLiteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = (EditText) findViewById(R.id.emailAddressLoginEditText);
        passwordLogin = (EditText) findViewById(R.id.passwordLoginEditText) ;
        loginButton = (Button) findViewById(R.id.buttonLogin);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        db = new SQLiteDatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if (email.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_LONG).show();
                else {
                    int checkuser = db.checkIsUserEmail(email);
                    if(checkuser < 1){
                        Toast.makeText(LoginActivity.this, "Email address not registered!", Toast.LENGTH_LONG).show();
                    }else {
                        int checkUserPass = db.checkUserEmailPassCombo(email, password);
                        if (checkUserPass > -1){
                            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("email", email);
                            //finish();
                            startActivity(intent);
                        }else
                            Toast.makeText(LoginActivity.this, "Email password combination incorrect", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
    }



}