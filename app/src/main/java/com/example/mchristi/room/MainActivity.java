package com.example.mchristi.room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TAG","BLA");

        // on récupère le recyclerview
        RecyclerView recyclerView = findViewById(R.id.myusers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // on créée une instance d'adapter (qui contient une copie de la donnée,
        // et la déclaration des ViewHolder
        final UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        // on crée une instance de notre ViewModel
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        // et on ajoute un observer sur les utilisateurs...
        viewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.setUsers(users);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    public void addUser(View v) {
        Log.i("TAG","Adding a view...");
        viewModel.insert(new User("Charles","Babbage"));
    }
}
