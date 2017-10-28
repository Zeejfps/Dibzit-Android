package com.ttuicube.dibzitapp.screens.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;
import android.widget.CheckBox;

import com.ttuicube.dibzitapp.DibzitApplication;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.utils.PresentedActivity;
import com.ttuicube.dibzitapp.utils.PresenterFactory;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class LoginActivity extends PresentedActivity<LoginPresenter, LoginView> implements LoginView {

    private TextInputEditText usernameField;
    private TextInputEditText passwordField;
    private Button loginButton;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        usernameField = (TextInputEditText) findViewById(R.id.usernameField);
        passwordField = (TextInputEditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        rememberMe = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        loginButton.setOnClickListener(
        v -> presenter.authenticate(
            usernameField.getText().toString(),
            passwordField.getText().toString(),
            rememberMe.isChecked())
        );
    }

    private void onAuthenticated(String email) {
        /*if (rememberMe.isChecked()) {
            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                .edit().putString(EMAIL_KEY, email).apply();
        }
        Intent result = new Intent();
        result.putExtra(EMAIL_KEY, email);
        setResult(1, result);
        finish();*/
    }

    @Override
    protected PresenterFactory<LoginPresenter> getPresenterFactory() {
        return () -> new LoginPresenter(DibzitApplication.instance().getRepository());
    }

    @Override
    protected LoginView getView() {
        return this;
    }

    @Override
    public void displayInvalidCredentials() {

    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    public void displayAuthenticationError(String error) {

    }

}
