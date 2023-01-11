package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodorderapp.databinding.ActivityDetailBinding;
import com.example.foodorderapp.databinding.ActivityMainBinding;
import com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final DBHelper helper = new DBHelper(this);
        if (getIntent().getIntExtra("type", 0) == 1) {
            getSupportActionBar().setTitle("Add Order");
            final int image = getIntent().getIntExtra("image", 0);
            final String name = getIntent().getStringExtra("name");
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String description = getIntent().getStringExtra("description");
            Integer totalQuantity = price * Integer.parseInt(binding.detailQuantity.getText().toString());

            binding.detailImage.setImageResource(image);
            binding.detailFoodname.setText(name);
            binding.detailPrice.setText(String.format("%d", price));
            binding.detailDescription.setText(description);
            binding.detailTotalPrice.setText(totalQuantity.toString());

            // increment and decrement order quantity code
            binding.detailAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(binding.detailQuantity.getText().toString()) > 0 && Integer.parseInt(binding.detailQuantity.getText().toString()) < 5) {
                        Integer operation = Integer.parseInt(binding.detailQuantity.getText().toString()) + 1;
                        binding.detailQuantity.setText(operation.toString());
                        Integer quantityResult = Integer.parseInt(binding.detailQuantity.getText().toString()) * price;
                        binding.detailTotalPrice.setText(quantityResult.toString());
                    }
                }
            });

            binding.detailMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(binding.detailQuantity.getText().toString()) > 1) {
                        Integer operation = Integer.parseInt(binding.detailQuantity.getText().toString()) - 1;
                        binding.detailQuantity.setText(operation.toString());
                        Integer quantityResult = Integer.parseInt(binding.detailQuantity.getText().toString()) * price;
                        binding.detailTotalPrice.setText(quantityResult.toString());
                    }
                }
            });

            // order button code
            binding.detailOrdernow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usernameCheck = binding.detailUsername.getText().toString();
                    String phoneNumberCheck = binding.detailUrerphone.getText().toString();
                    if (usernameCheck.equals("")) {
                        binding.detailUsername.setError("Username is required");
                        if (binding.detailUsername.requestFocus()) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    } else if (phoneNumberCheck.equals("")) {
                        binding.detailUrerphone.setError("Phone number is required");
                        if (binding.detailUrerphone.requestFocus()) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    } else {
                        boolean isInserted = helper.insertOrder(
                                binding.detailUsername.getText().toString(),
                                binding.detailUrerphone.getText().toString(),
                                price,
                                image,
                                description,
                                name,
                                Integer.parseInt(binding.detailQuantity.getText().toString())
                        );
                        if (isInserted) {
                            Toast.makeText(DetailActivity.this, "Order Added Successfully...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailActivity.this, OrderActivity.class));
                            finish();
                        }
                        else
                            Toast.makeText(DetailActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            getSupportActionBar().setTitle("Update Order");
            int id = getIntent().getIntExtra("id", 0);
            Cursor cursor = helper.getOrderById(id);
            binding.detailImage.setImageResource(cursor.getInt(4));
            binding.detailFoodname.setText(cursor.getString(6));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(3)));
            binding.detailDescription.setText(cursor.getString(5));
            binding.detailUsername.setText(cursor.getString(1));
            binding.detailUrerphone.setText(cursor.getString(2));
            binding.detailQuantity.setText(String.format("%d", cursor.getInt(7)));
            binding.detailOrdernow.setText("Update now");
            int price = Integer.parseInt(cursor.getString(3));
            Integer quantityResult = Integer.parseInt(binding.detailQuantity.getText().toString()) * price;
            binding.detailTotalPrice.setText(quantityResult.toString());

            // increment and decrement order quantity code
            binding.detailAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(binding.detailQuantity.getText().toString()) > 0 && Integer.parseInt(binding.detailQuantity.getText().toString()) < 5) {
                        Integer operation = Integer.parseInt(binding.detailQuantity.getText().toString()) + 1;
                        binding.detailQuantity.setText(operation.toString());
                        Integer price = Integer.parseInt(cursor.getString(3));
                        Integer quantityResult = Integer.parseInt(binding.detailQuantity.getText().toString()) * price;
                        binding.detailTotalPrice.setText(quantityResult.toString());
                    }
                }
            });

            binding.detailMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(binding.detailQuantity.getText().toString()) > 1) {
                        Integer operation = Integer.parseInt(binding.detailQuantity.getText().toString()) - 1;
                        binding.detailQuantity.setText(operation.toString());
                        Integer price = Integer.parseInt(cursor.getString(3));
                        Integer quantityResult = Integer.parseInt(binding.detailQuantity.getText().toString()) * price;
                        binding.detailTotalPrice.setText(quantityResult.toString());
                    }
                }
            });


            binding.detailOrdernow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usernameCheck = binding.detailUsername.getText().toString();
                    String phoneNumberCheck = binding.detailUrerphone.getText().toString();
                    if (usernameCheck.equals("")) {
                        binding.detailUsername.setError("Username is required");
                        if (binding.detailUsername.requestFocus()) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    } else if (phoneNumberCheck.equals("")) {
                        binding.detailUrerphone.setError("Phone number is required");
                        if (binding.detailUrerphone.requestFocus()) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    } else {
                        boolean isUpdated = helper.updateOrder(
                                binding.detailUsername.getText().toString(),
                                binding.detailUrerphone.getText().toString(),
                                Integer.parseInt(binding.detailPrice.getText().toString()),
                                cursor.getInt(4),
                                binding.detailDescription.getText().toString(),
                                binding.detailFoodname.getText().toString(),
                                Integer.parseInt(binding.detailQuantity.getText().toString()),
                                id
                        );
                        if (isUpdated) {
                            Toast.makeText(DetailActivity.this, "Order Updated Successfully..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DetailActivity.this, OrderActivity.class));
                            finish();
                        } else
                            Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}