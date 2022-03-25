package com.sg2d.modules.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.sg2d.app.CleanArchApp;
import com.sg2d.modules.MainActivity;
import com.sg2d.modules.R;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AuthActivity.class.getSimpleName();
    private ContentLoadingProgressBar progressBar;
    private AuthEntryViewModel authEntryViewModel;
    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    private EditText inputUserName, inputPassword;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AuthEntry feature component scope start here
        ((CleanArchApp) getApplication()).buildAuthEntryComponent();

        setContentView(R.layout.activity_login);
        initViews();
        authEntryViewModel.loadAuthEntry(null, null);
    }

    void initViews() {
        progressBar = findViewById(R.id.progressBar);
        inputUserName = findViewById(R.id.user_name);
        inputPassword = findViewById(R.id.user_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this::onClick);
        authEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(AuthEntryViewModel.class);
        addAuthenticationObserver();
    }

    void addAuthenticationObserver() {
        authEntryViewModel.getAuthEntry().observe(this, authEntry -> {
            Log.d(TAG, "received update for authEntry");
            hideProgress();
            //Error Response From Server
            if (authEntry.getError() != null && !authEntry.getError().equalsIgnoreCase("Success")) {
                inputUserName.setError(authEntry.getError());
                inputPassword.setError(authEntry.getError());
            } else if (authEntry.getMyId() != 0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(AuthActivity.this,
                                MainActivity.class);
                        //Intent is used to switch from one activity to another.
                        i.putExtra("auth_user", new Gson().toJson(authEntry));
                        startActivity(i);
                        //invoke the SecondActivity.
                        finish();
                    }
                }, 1000);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.splash_view).setVisibility(View.GONE);
                    }
                }, SPLASH_SCREEN_TIME_OUT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //AuthEntry feature component scope ends here
        ((CleanArchApp) getApplication()).releaseAuthEntryComponent();
    }

    void loginUser() {
        if (TextUtils.isEmpty(inputUserName.getText())) {
            inputUserName.setError("Please Enter user name");
        } else if (TextUtils.isEmpty(inputPassword.getText())) {
            inputPassword.setError("Please Enter password");
        } else {
            showProgress();
            authEntryViewModel.loadAuthEntry(inputUserName.getText().toString(), inputPassword.getText().toString());
        }
    }

    public void showProgress() {
        if (progressBar != null && !progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.show();
        }
    }

    public void hideProgress() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.hide();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            loginUser();
        }
    }
}
