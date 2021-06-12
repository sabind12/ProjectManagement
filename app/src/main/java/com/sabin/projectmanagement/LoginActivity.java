package com.sabin.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {          //Activitate pentru autentificare sau acesul activitatii de inregistrare

    EditText emailLogin;
    EditText passwordLogin;
    Button loginButton;
    Button registerButton;
    SQLiteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);            //Accesarea elementelor desenate din activity_login.xml
        emailLogin = (EditText) findViewById(R.id.emailAddressLoginEditText);
        passwordLogin = (EditText) findViewById(R.id.passwordLoginEditText) ;
        loginButton = (Button) findViewById(R.id.buttonLogin);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        db = new SQLiteDatabaseHelper(this);
        loginButton.setOnClickListener(new View.OnClickListener() {     //Buton pentru autentificarea utilizatorului
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if (email.equals("") || password.equals(""))    //Verificarea prezentei textului in ambele campuri
                    Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_LONG).show();
                else {
                    int checkuser = db.checkIsUserEmail(email);
                    if(checkuser < 1){                          //Verificarea existentei adresei mail
                        Toast.makeText(LoginActivity.this, "Email address not registered!", Toast.LENGTH_LONG).show();
                    }else {
                        int checkUserPass = db.checkUserEmailPassCombo(email, password);
                        if (checkUserPass > -1){                 //Verificarea combinatiei user + parola
                            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("email", email);
                            //finish();                           //Intoarcerea in activitatea principala
                            startActivity(intent);
                        }else
                            Toast.makeText(LoginActivity.this, "Email password combination incorrect", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {               //Buton pentru accesul activitatii de creare user
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
    }



}