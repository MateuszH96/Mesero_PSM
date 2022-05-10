package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        try {
            getDataFromDataBase();
        } catch (InterruptedException e) {
            Toast.makeText(this,"coś poszło nie tak",Toast.LENGTH_LONG).show();
        }
        ListDish listDish = new ListDish(menuList);
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
}