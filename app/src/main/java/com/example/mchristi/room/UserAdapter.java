package com.example.mchristi.room;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> users = new ArrayList<>();


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = users.get(position);
        holder.firstName.setText(currentUser.firstName);
        holder.lastName.setText(currentUser.lastName);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> Users) {
        this.users = Users;
        notifyDataSetChanged();
    }

    public User getUserAt(int adapterPosition) {
        return users.get(adapterPosition);
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private TextView firstName;
        private TextView lastName;

        public UserHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
        }
    }
}