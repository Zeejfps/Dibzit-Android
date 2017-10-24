package com.ttuicube.dibzitapp.modules.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.ttuicube.dibzitapp.R;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        rememberMe = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
