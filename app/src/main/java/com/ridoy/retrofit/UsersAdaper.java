package com.ridoy.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridoy.retrofit.Model.User;
import com.ridoy.retrofit.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

public class UsersAdaper extends RecyclerView.Adapter<UsersAdaper.UserViewHolder> {

    Context context;
    List<User> users;

    public UsersAdaper(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userItemBinding.usernameTV.setText(users.get(position).getUsername());
        holder.userItemBinding.emailTV.setText(users.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        UserItemBinding userItemBinding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userItemBinding=UserItemBinding.bind(itemView);
        }
    }
}
