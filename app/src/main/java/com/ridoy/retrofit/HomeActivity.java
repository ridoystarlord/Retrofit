package com.ridoy.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ridoy.retrofit.Model.PasswordResponse;
import com.ridoy.retrofit.Model.SharedPrefManager;
import com.ridoy.retrofit.databinding.ActivityHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity{

    ActivityHomeBinding activityHomeBinding;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        activityHomeBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.bottom_dashboard:
                        transaction.replace(R.id.container,new DashboardFragment());
                        break;
                    case R.id.bottom_profile:
                        transaction.replace(R.id.container,new ProfileFragment());
                        break;
                    case R.id.bottom_users:
                        transaction.replace(R.id.container,new UsersFragment());
                        break;
                }
                transaction.commit();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.top_logout:
                sharedPrefManager.logout();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finishAffinity();
                break;
            case R.id.top_delete:
                Call<PasswordResponse> call=RetrofitClient.getInstance().getApi().delete(sharedPrefManager.getUser().getId());
                call.enqueue(new Callback<PasswordResponse>() {
                    @Override
                    public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                        PasswordResponse passwordResponse=response.body();
                        if (response.isSuccessful()){
                            if (passwordResponse.getError().equals("200")){
                                sharedPrefManager.logout();
                                Toast.makeText(HomeActivity.this, passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(HomeActivity.this, passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PasswordResponse> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}