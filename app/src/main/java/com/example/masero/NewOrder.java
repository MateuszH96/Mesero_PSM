package com.example.masero;

import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import Backend.Dish;
import Backend.Global;
import Backend.ListDish;
import Backend.MyAdapter.MyAdapter;
import Database.GetListDish;
import Database.SqlRequest;

public class NewOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    String menuList;
    ListDish listDish;
    Toast toast;
    //List<Dish> orderedDishList = new LinkedList<Dish>();
    //String orderedDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        try {
            getDataFromDataBase();
        } catch (InterruptedException e) {
            makeText(this, "coś poszło nie tak", LENGTH_LONG).show();
        }
        listDish = new ListDish(menuList);
        setRecycleView();
        getSharPref();
    }

    private String getSharPref() {
        String tmp;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF, MODE_PRIVATE);
        tmp = sharedPreferences.getString(SharPref.ORDER_KEY, "");
        return tmp;
    }

    private void setRecycleView() {
        recyclerView = findViewById(R.id.recycleView);
        List<Integer> images = new LinkedList<Integer>();
        for (int i = 0; i < listDish.size(); i++) {
            images.add(R.drawable.img);
        }
        MyAdapter myAdapter = new MyAdapter(this, listDish.getDishList(), images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDataFromDataBase() throws InterruptedException {
        GetListDish getListDish = new GetListDish(Global.connect);
        ListDish listDish = getListDish.getListOfDish();
        while (!getListDish.getStatus()) {
            Thread.sleep(100);
        }
        menuList = "";
        for (Dish i : listDish.getDishList()) {
            menuList += i.toString();
        }
    }

    public void saveOrder(View view) throws SQLException {
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF, MODE_PRIVATE);
        sendOrderToDataBase(sharedPreferences.getString(SharPref.ORDER_KEY, ""));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharPref.ORDER_KEY, "");
        editor.apply();
        //finish();
    }

    private void sendOrderToDataBase(String toSend) throws SQLException {
        if (toSend == "") {
            makeText(this, "Puste zamówienie", LENGTH_LONG).show();
        } else {
            ListDish list = new ListDish(toSend);
            SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF, MODE_PRIVATE);
            String email = sharedPreferences.getString(SharPref.EMAIL_KEY, "");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Integer id=0;
                        //ring sql =
                        Statement stmt = Global.connect.getConnection().createStatement();
                        ResultSet rs = stmt.executeQuery(SqlRequest.getIdRequest);
                        rs.next();
                        String id_string = rs.getString("MAX");
                        id = Integer.parseInt(id_string) + 1;
                        for (Dish i : list.getDishList()) {
                            String sqlRequest = String.format(SqlRequest.sendOrderToDatabase, id, i.getId().intValue(), email);
                            stmt.executeUpdate(sqlRequest);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}