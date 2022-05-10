package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

import Backend.Dish;
import Backend.Global;
import Backend.ListDish;
import Backend.MyAdapter.MyAdapter;
import Database.GetListDish;

public class NewOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    String menuList;
    ListDish listDish;
    List<Dish> orderedDishList=new LinkedList<Dish>();
    String orderedDishes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        try {
            getDataFromDataBase();
        } catch (InterruptedException e) {
            Toast.makeText(this,"coś poszło nie tak",Toast.LENGTH_LONG).show();
        }
        listDish = new ListDish(menuList);
        setRecycleView();
        getSharPref();
    }

    private String getSharPref() {
        String tmp;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        tmp = sharedPreferences.getString(SharPref.ORDER_KEY,"");
        return tmp;
    }

    private void setRecycleView() {
        recyclerView = findViewById(R.id.recycleView);
        List<Integer> images=new LinkedList<Integer>();
        for (int i=0;i<listDish.size();i++){
            images.add(R.drawable.img);
        }
        MyAdapter myAdapter = new MyAdapter(this,listDish.getDishList(),images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDataFromDataBase() throws InterruptedException {
        GetListDish getListDish=new GetListDish(Global.connect);
        ListDish listDish=getListDish.getListOfDish();
        while(!getListDish.getStatus()) {
            Thread.sleep(100);
        }
        menuList="";
        for (Dish i:listDish.getDishList()) {
            menuList+=i.toString();
        }
    }

    public void saveOrder(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        sendOrderToDataBase(sharedPreferences.getString(SharPref.ORDER_KEY,""));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharPref.ORDER_KEY,"");
        editor.apply();
    }

    private void sendOrderToDataBase(String toSend) {
        if (toSend==""){
            Toast.makeText(this,"Puste zamóœienie",Toast.LENGTH_LONG).show();
        }else{
            ListDish list = new ListDish(toSend);
        }
    }
}