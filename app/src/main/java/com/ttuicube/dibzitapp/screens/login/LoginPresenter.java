package com.ttuicube.dibzitapp.screens.login;

import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.Presenter;

/**
 * Created by Zeejfps on 10/27/17.
 */

public class LoginPresenter implements Presenter<LoginView> {

    private final Repository repo;
    private LoginView view;

    public LoginPresenter(Repository repo) {
        this.repo = repo;
    }

    @Override
    public void onViewAttached(LoginView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroyed() {}

    public void authenticate(String username, String password, boolean save) {
        view.closeView();
    }

}
