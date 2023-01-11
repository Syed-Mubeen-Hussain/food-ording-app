package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.foodorderapp.Adapters.OrderAdapter;
import com.example.foodorderapp.Models.MainModel;
import com.example.foodorderapp.Models.OrderModel;
import com.example.foodorderapp.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    ActivityOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DBHelper db = new DBHelper(this);

        getSupportActionBar().setTitle("Orders ("+db.orderCount() + ")");

        ArrayList<OrderModel> orderList = db.getOrders();

        if(orderList.size() > 0){
            binding.orderTextView.setVisibility(View.GONE);
            OrderAdapter orderAdapter = new OrderAdapter(this, orderList);
            binding.orderRecyclerView.setAdapter(orderAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.orderRecyclerView.setLayoutManager(layoutManager);
        }else{
         binding.orderTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OrderActivity.this, MainActivity.class));
        finish();
    }
}