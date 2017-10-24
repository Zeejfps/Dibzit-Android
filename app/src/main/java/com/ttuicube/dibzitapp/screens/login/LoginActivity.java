package com.ttuicube.dibzitapp.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.ttuicube.dibzitapp.R;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "Email";

    private TextInputEditText usernameField;
    private TextInputEditText passwordField;
    private Button loginButton;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (TextInputEditText) findViewById(R.id.usernameField);
        passwordField = (TextInputEditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        rememberMe = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAuthenticated(usernameField.getText().toString());
            }
        });
    }

    private void onAuthenticated(String email) {
        if (rememberMe.isChecked()) {
            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                .edit().putString(EMAIL_KEY, email).apply();
        }
        Intent result = new Intent();
        result.putExtra(EMAIL_KEY, email);
        setResult(1, result);
        finish();
    }

}
