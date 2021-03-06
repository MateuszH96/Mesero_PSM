
package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import Backend.Dish;
import Backend.Global;
import Backend.MyAdapter.MyAdapterOrders;
import Backend.Order;
import Database.SqlRequest;

public class PreviousOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Order> orders = new LinkedList<>();
    boolean repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);
        recyclerView = findViewById(R.id.recycleViewYourOrders);
        set();
        repeat = true;
        while (repeat) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setRecycleView();
    }

    private void setRecycleView() {
        MyAdapterOrders myAdapterOrders = new MyAdapterOrders(this, orders,"show-");
        recyclerView.setAdapter(myAdapterOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private String getData() {
        String toReturn;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF, MODE_PRIVATE);
        toReturn = sharedPreferences.getString(SharPref.EMAIL_KEY, "");
        return toReturn;
    }

    void set() {
        Thread thread = new Thread(() -> {
            List<Dish> dishList;
            String mailAddres = getData();
            String sqlRequestFullCurrentOrders = String.format(SqlRequest.getFullListOfCurrentOrders, mailAddres, "TRUE");
            String sqlRequestIdList = String.format(SqlRequest.getListIdCurretOrders, sqlRequestFullCurrentOrders);
            try {
                Statement stmt = Global.connect.getConnection().createStatement();
                Statement statement = Global.connect.getConnection().createStatement();
                ResultSet rsId = stmt.executeQuery(sqlRequestIdList);
                while (rsId.next()) {
                    String id = rsId.getString("id_order");
                    String sqlRequestById = String.format(SqlRequest.getListOfCurrentOrdersById, mailAddres, id, "TRUE");
                    dishList = null;
                    dishList = new LinkedList<>();
                    ResultSet rsCurrentOrders = statement.executeQuery(sqlRequestById);
                    while (rsCurrentOrders.next()) {
                        dishList.add(new Dish(
                                rsCurrentOrders.getString("name"),
                                rsCurrentOrders.getInt("weight"),
                                rsCurrentOrders.getDouble("price"),
                                rsCurrentOrders.getInt("id")
                        ));
                    }
                    orders.add(new Order(Integer.parseInt(id), dishList));
                }
                repeat = false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        thread.start();
    }
}