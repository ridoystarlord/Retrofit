package com.ridoy.retrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ridoy.retrofit.Model.SharedPrefManager;
import com.ridoy.retrofit.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentDashboardBinding fragmentDashboardBinding;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding=FragmentDashboardBinding.inflate(inflater,container,false);

        sharedPrefManager=new SharedPrefManager(getContext());
        fragmentDashboardBinding.usernameTV.setText("Name: "+sharedPrefManager.getUser().getUsername());
        fragmentDashboardBinding.emailTV.setText("Email: "+sharedPrefManager.getUser().getEmail());

        return fragmentDashboardBinding.getRoot();
    }
}