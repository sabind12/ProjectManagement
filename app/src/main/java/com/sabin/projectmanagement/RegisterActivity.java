package com.sabin.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {           //Activitate pentru inregistrarea si autentificarea automata a utilizatorului

    EditText emailRegister;
    EditText userNameRegister;
    EditText passwordRegister;
    EditText passwordRepeatRegister;
    Button registerButton;
    SQLiteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);                                             //Accesul elementelor din activity_register.xml
        emailRegister = (EditText) findViewById(R.id.emailAddressRegisterEditText);
        userNameRegister = (EditText) findViewById(R.id.userNameRegisterEditText);
        passwordRegister = (EditText) findViewById(R.id.passwordRegisterEditText);
        passwordRepeatRegister = (EditText) findViewById(R.id.passwordRepeatRegisterEditText);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        db = new SQLiteDatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {                  //Buton pentru inregistrarea utilizatorului
            @Override
            public void onClick(View v) {
                String email = emailRegister.getText().toString();
                String userName = userNameRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                String passwordRepeat = passwordRepeatRegister.getText().toString();
                User user = new User(userName, email, password, "admin");       //Validarea informatiilor completate in  campuri
                if (email.equals("") || password.equals("") || passwordRepeat.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please fill all fields!", Toast.LENGTH_LONG).show();
                else
                    if (!password.equals(passwordRepeat))
                        Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                    else{
                        int checkuser = db.checkIsUserEmail(email);
                        if(checkuser > 0) {
                            Toast.makeText(RegisterActivity.this, "Email already in use!", Toast.LENGTH_LONG).show();
                        }else{
                            db.createUser(user);                                    //Inregistrarea utilizatorului in baza de date
                            Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);        //Autentificarea utilizatorului
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }

            }
        });
    }
}