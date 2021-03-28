package com.ridoy.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.ridoy.retrofit.Model.LoginResponse;
import com.ridoy.retrofit.Model.SharedPrefManager;
import com.ridoy.retrofit.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activityLoginBinding.useremailET.getText().toString();
                String password = activityLoginBinding.userpasswordET.getText().toString();

                if (email.isEmpty()) {
                    activityLoginBinding.useremailET.setError("Email Required");
                    activityLoginBinding.useremailET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    activityLoginBinding.useremailET.setError("Enter Correct Email");
                    activityLoginBinding.useremailET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    activityLoginBinding.userpasswordET.setError("Password Required");
                    activityLoginBinding.userpasswordET.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    activityLoginBinding.userpasswordET.setError("Minimum 6 digit");
                    activityLoginBinding.userpasswordET.requestFocus();
                    return;
                }
                Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if (response.isSuccessful()) {
                            if (loginResponse.getError().equals("200")) {
                                sharedPrefManager.SaveUser(loginResponse.getUser());
                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        activityLoginBinding.signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManager.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }
}