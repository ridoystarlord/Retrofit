package com.ridoy.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.ridoy.retrofit.Model.RegisterResponse;
import com.ridoy.retrofit.Model.SharedPrefManager;
import com.ridoy.retrofit.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        activityMainBinding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=activityMainBinding.usernameET.getText().toString();
                String email=activityMainBinding.useremailET.getText().toString();
                String password=activityMainBinding.userpasswordET.getText().toString();
                if (name.isEmpty()){
                    activityMainBinding.usernameET.setError("Name Required");
                    activityMainBinding.usernameET.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    activityMainBinding.useremailET.setError("Email Required");
                    activityMainBinding.useremailET.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    activityMainBinding.useremailET.setError("Enter Correct Email");
                    activityMainBinding.useremailET.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    activityMainBinding.userpasswordET.setError("Password Required");
                    activityMainBinding.userpasswordET.requestFocus();
                    return;
                }
                if (password.length()<6){
                    activityMainBinding.userpasswordET.setError("Minimum 6 digit");
                    activityMainBinding.userpasswordET.requestFocus();
                    return;
                }
                Call<RegisterResponse> call=RetrofitClient.getInstance().getApi().register(name,email,password);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse=response.body();
                        if (response.isSuccessful()){
                            Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        Toast.makeText(MainActivity.this, "Error: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        activityMainBinding.signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManager.isLoggedIn()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
    }
}