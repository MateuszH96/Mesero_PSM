package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

import Backend.ListDish;
import Backend.MyAdapter.MyAdapter;
import Database.GetListDish;

public class NewOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        Intent intent = getIntent();
        String result = intent.getStringExtra("MenuList");
        ListDish listDish = new ListDish(result);
        recyclerView = findViewById(R.id.recycleView);
        List<Integer> images=new LinkedList<Integer>();
        for (int i=0;i<listDish.size();i++){
            images.add(R.drawable.img);
        }
        MyAdapter myAdapter = new MyAdapter(this,listDish.getDishList(),images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}