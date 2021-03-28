package com.ridoy.retrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ridoy.retrofit.Model.FetchUsersResponse;
import com.ridoy.retrofit.Model.User;
import com.ridoy.retrofit.databinding.FragmentUsersBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersFragment extends Fragment {



    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentUsersBinding fragmentUsersBinding;
    List<User> userslist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentUsersBinding=FragmentUsersBinding.inflate(inflater,container,false);

        //UsersAdaper usersAdaper=new UsersAdaper(getActivity(),userslist);

        Call<FetchUsersResponse> call=RetrofitClient.getInstance().getApi().fetchusers();
        call.enqueue(new Callback<FetchUsersResponse>() {
            @Override
            public void onResponse(Call<FetchUsersResponse> call, Response<FetchUsersResponse> response) {

                if (response.isSuccessful()){
                    userslist=response.body().getUsers();
                    fragmentUsersBinding.rv.setAdapter(new UsersAdaper(getActivity(),userslist));
                }else {
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FetchUsersResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        fragmentUsersBinding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return fragmentUsersBinding.getRoot();
    }
}