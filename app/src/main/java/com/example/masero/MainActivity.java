package com.example.masero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;

import Backend.Dish;
import Backend.Global;
import Backend.ListDish;
import Backend.Mail.SendMail;
import Database.Connect;
import Database.GetListDish;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    Thread thread;
    Button newOrderButton;
    Button prevOrdersButton;
    Button yourOrdersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIsEmail();
        newOrderButton = findViewById(R.id.newOrder);
        prevOrdersButton = findViewById(R.id.previousOrders);
        yourOrdersButton = findViewById(R.id.yourOrder);
        setButtonsClickable(true);
        makeConnect();
    }

    private void makeConnect() {
        if (Global.connect!=null){
            return;
        }
        thread = new Thread(() -> {
            Global.connect = new Connect();
            if (Global.connect.getConnection()==null){
                Toast.makeText(this,"Nie można połączyć z bazą",Toast.LENGTH_LONG).show();
                setButtonsClickable(false);
            }
        });
        thread.start();
    }

    private void setButtonsClickable(boolean toSet) {
        newOrderButton.setClickable(toSet);
        prevOrdersButton.setClickable(toSet);
        yourOrdersButton.setClickable(toSet);
    }
    private String getData(){
        String text;
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        text = sharedPreferences.getString("EMAIL_KEY","");
        return text;
    }
    private void checkIsEmail() {
        if (getData()==""){
            Intent intent = new Intent(MainActivity.this,ActivityMail.class);
            startActivity(intent);
            finish();
        }
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
        /*SendMail.sendMailMessage(this,
                                "mateuszhamera3@gmail.com",
                                "hello",
                                "hello there");*/
        Intent intent = new Intent(MainActivity.this,ActivityMail.class);
        startActivity(intent);
    }

    public void newOrder(View view) throws InterruptedException {
        Intent intent= new Intent(MainActivity.this, NewOrder.class);
        //intent.putExtra("MenuList",menuList);
        startActivity(intent);
    }
}