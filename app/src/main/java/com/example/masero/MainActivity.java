package com.example.masero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

import Backend.Dish;
import Backend.ListDish;
import Backend.Mail.SendMail;
import Database.Connect;
import Database.GetListDish;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Connect connect;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thread = new Thread(() -> {
            connect = new Connect();
            if (connect.getConnection()==null){
                textView1.setText("jest problem");
            }
        });
        thread.start();
    }
    public void ButtonClick(View view) throws InterruptedException {
        /*GetListDish getListDish=new GetListDish(connect);
        //Toast.makeText(this,"Pobieranie danych...",Toast.LENGTH_SHORT).show();
        while(!getListDish.getStatus()) {
            Thread.sleep(100);
        }
        //System.out.println(getListDish.getListOfDish().get(0).getName());

        setTextView(textView1,getListDish.getListOfDish().get(0));
        setTextView(textView2,getListDish.getListOfDish().get(1));
        setTextView(textView3,getListDish.getListOfDish().get(2));*/
    }
    void setTextView(TextView textView,Dish dish){
        textView.setText(MessageFormat.format("{0} {1}g {2}zł", dish.getName(), dish.getWeight().toString(), dish.getPrice().toString()));
    }

    public void previousOrder(View view) {
        Intent intent = new Intent(MainActivity.this,PreviousOrders.class);
        startActivity(intent);
    }

    public void yourOrders(View view) {
        Intent intent = new Intent(MainActivity.this,YourOrders.class);
        startActivity(intent);
    }

    public void setMail(View view) {
        SendMail.sendMailMessage(this,
                                "mateuszhamera3@gmail.com",
                                "hello",
                                "hello there");
        Intent intent = new Intent(MainActivity.this,ActivityMail.class);
        startActivity(intent);
    }

    public void newOrder(View view) throws InterruptedException {
        GetListDish getListDish=new GetListDish(connect);
        ListDish listDish=getListDish.getListOfDish();
        while(!getListDish.getStatus()) {
            Thread.sleep(100);
        }
        String menuList="";
        for (Dish i:listDish.getDishList()) {
            menuList+=i.toString();
        }
        Intent intent= new Intent(MainActivity.this, NewOrder.class);
        intent.putExtra("MenuList",menuList);
        startActivity(intent);
    }
}