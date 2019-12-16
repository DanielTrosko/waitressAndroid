
package it.danieltrosko.gastro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {


    Button login;
    EditText loginText;
    EditText passwordText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginButton);
        loginText = findViewById(R.id.loginText);
        passwordText = findViewById(R.id.passwordText);
        textView = findViewById(R.id.textView);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncTask.execute(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        String login = loginText.getText().toString();
                        String password = passwordText.getText().toString();


                        try {
                            URL url = null;
                            url = new URL("http://10.0.2.2:8080/waitress/login?username=" + login + "&password=" + password);

                            HttpURLConnection myConnection = null;
                            myConnection = (HttpURLConnection) url.openConnection();
                            myConnection.setRequestMethod("POST");


                            if (myConnection.getResponseCode() == 200) {
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Wrong username or password",LENGTH_LONG).show();
                                    }
                                });

                            }
                            myConnection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                });

            }
        });

    }
}
