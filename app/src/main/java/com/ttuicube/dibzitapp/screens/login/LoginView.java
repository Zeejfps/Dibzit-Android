package com.ttuicube.dibzitapp.screens.login;

/**
 * Created by Zeejfps on 10/27/17.
 */

public interface LoginView {

    void displayInvalidCredentials();

    void closeView();

    void displayAuthenticationError(String error);

}
