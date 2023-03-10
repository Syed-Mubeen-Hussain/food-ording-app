package com.example.foodorderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodorderapp.Models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBNAME = "mydatabase.db";
    final static int DBVERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table orders(" +
                        "id integer primary key autoincrement," +
                        "name text," +
                        "phone text," +
                        "price int," +
                        "image int," +
                        "description text," +
                        "foodname text," +
                        "quantity int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists orders");
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(String name, String phone, int price, int image, String description, String foodName, int quantity){
        SQLiteDatabase database = getReadableDatabase();
        /*
            id = 0
            name = 1
            phone = 2
            price = 3
            image = 4
            description = 5
            foodname = 6
         */
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",description);
        values.put("foodName",foodName);
        values.put("quantity",quantity);
        long id = database.insert("orders",null,values);
        if(id <= 0){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<OrderModel> getOrders(){
        ArrayList<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor  = database.rawQuery("Select id,foodname,image,price,quantity from orders", null);
        if(cursor.moveToFirst()){
            do{
                OrderModel model = new OrderModel();
                model.setOrderNumber(cursor.getInt(0));
                model.setOrderName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setOrderPrice(cursor.getInt(3));
                model.setOrderQuantity(cursor.getInt(4));
                orders.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return orders;
    }

    public Cursor getOrderById(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor  = database.rawQuery("Select * from orders where id = "+id, null);
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public boolean updateOrder(String name, String phone, int price, int image, String description, String foodName, int quantity, int id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",description);
        values.put("foodName",foodName);
        values.put("quantity",quantity);
        long row = database.update("orders",values,"id="+id,null);
        if(row <= 0){
            return false;
        }else{
            return true;
        }
    }

    public int orderDelete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("orders","id="+id,null);
    }

    public int orderCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from orders", null);
        return cursor.getCount();
    }
}
