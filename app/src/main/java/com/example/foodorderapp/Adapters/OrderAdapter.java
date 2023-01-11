package com.example.foodorderapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DBHelper;
import com.example.foodorderapp.DetailActivity;
import com.example.foodorderapp.MainActivity;
import com.example.foodorderapp.Models.OrderModel;
import com.example.foodorderapp.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {

    Context context;
    ArrayList<OrderModel> list;
    OrderAdapter adapter;

    public OrderAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_sample_layout, parent, false);
        return new OrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final OrderModel model = list.get(position);
        holder.order_image.setImageResource(model.getOrderImage());
        holder.order_name.setText(model.getOrderName());
        holder.order_price.setText(String.valueOf(model.getOrderPrice()));
        holder.order_number.setText(String.valueOf(model.getOrderNumber()));
        holder.order_quantity.setText(String.valueOf(model.getOrderQuantity()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", model.getOrderNumber());
                intent.putExtra("type", 2);
                context.startActivity(intent);
            }
        });
        Vibrator vibe = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                vibe.vibrate(100);
                new AlertDialog.Builder(context)
                        .setTitle("Delete Order")
                        .setMessage("Are you sure you went to delete this order")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBHelper helper = new DBHelper(context);
                                if (helper.orderDelete(model.getOrderNumber()) > 0) {
                                    Toast.makeText(context, "Order Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, MainActivity.class));
                                } else {
                                    Toast.makeText(context, "Order Not Deleted!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView order_image;
        TextView order_name, order_price, order_number, order_quantity;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            order_image = itemView.findViewById(R.id.order_image);
            order_name = itemView.findViewById(R.id.order_name);
            order_price = itemView.findViewById(R.id.order_price);
            order_number = itemView.findViewById(R.id.order_number);
            order_quantity = itemView.findViewById(R.id.order_quantity);
        }
    }
}
