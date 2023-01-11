package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodorderapp.Adapters.MainAdapter;
import com.example.foodorderapp.Adapters.OrderAdapter;
import com.example.foodorderapp.Models.MainModel;
import com.example.foodorderapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<MainModel> mainList = new ArrayList<>();
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainList.add(new MainModel(R.drawable.burgur,"Burgur","5","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.pizza,"Pizza","0","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.burgur2,"Burgur Large","6","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.pizza3,"Small Pizza","3","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.burgur3,"Small Burgur","2","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.pizza4,"Large Pizza","8","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.burgur5,"Large Burgur","7","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.pizza5,"Special Pizza","12","food, substance consisting essentially of protein"));
        mainList.add(new MainModel(R.drawable.burgur6,"Special Burgur","13","food, substance consisting essentially of protein"));
        mainAdapter = new MainAdapter(this,mainList);
        binding.mainRecyclerView.setAdapter(mainAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.orders:
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Are you sure you went to exit this")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }
}