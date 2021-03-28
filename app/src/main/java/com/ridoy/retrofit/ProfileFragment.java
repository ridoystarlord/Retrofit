package com.ridoy.retrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ridoy.retrofit.Model.LoginResponse;
import com.ridoy.retrofit.Model.PasswordResponse;
import com.ridoy.retrofit.Model.SharedPrefManager;
import com.ridoy.retrofit.databinding.FragmentProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentProfileBinding fragmentProfileBinding;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding=FragmentProfileBinding.inflate(inflater,container,false);
        sharedPrefManager=new SharedPrefManager(getActivity());

        fragmentProfileBinding.changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=fragmentProfileBinding.nameET.getText().toString();
                int id=sharedPrefManager.getUser().getId();

                Call<LoginResponse> call=RetrofitClient.getInstance().getApi().updateuser(id,name);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse updateresponse=response.body();
                        if (response.isSuccessful()){
                            if (updateresponse.getError().equals("200")){
                                sharedPrefManager.SaveUser(updateresponse.getUser());
                                Toast.makeText(getActivity(), updateresponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), updateresponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(),"Failed" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fragmentProfileBinding.changepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentpass=fragmentProfileBinding.currentpassET.getText().toString();
                String newpass=fragmentProfileBinding.newpassET.getText().toString();
                String email=sharedPrefManager.getUser().getEmail();

                Call<PasswordResponse> call=RetrofitClient.getInstance().getApi().updatepass(email,currentpass,newpass);
                call.enqueue(new Callback<PasswordResponse>() {
                    @Override
                    public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                        PasswordResponse passwordResponse=response.body();
                        if (response.isSuccessful()){
                            if (passwordResponse.getError().equals("200")){
                                Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PasswordResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return fragmentProfileBinding.getRoot();
    }
}