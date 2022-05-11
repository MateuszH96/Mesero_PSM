package com.example.masero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import Backend.Dish;
import Backend.Global;
import Backend.ListDish;
import Backend.Mail.SendMail;
import Database.Connect;
import Database.GetListDish;
import Database.SqlRequest;

public class MainActivity extends AppCompatActivity {
    Button newOrderButton;
    Button prevOrdersButton;
    Button currentOrdersBtn;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIsEmail();
        newOrderButton = findViewById(R.id.newOrder);
        prevOrdersButton = findViewById(R.id.previousOrders);
        currentOrdersBtn=findViewById(R.id.yourOrder);
        setButtonsClickable(true);
        makeConnect();
        getNumOfCurrentOrders();
    }
    private void getNumOfCurrentOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean skip = false;
                while(Global.connect==null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        skip = true;
                    }
                }
                if (!skip){
                    String text = "";
                    try {
                        text = getNumOrders();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    String finalText = text;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setTextToBtn(currentOrdersBtn, finalText);
                        }
                    });
                }
            }
        });
        thread.start();
    }
    private String getNumOrders() throws SQLException {
        String getEmail = getData();
        String findOrdersWithEmail=String.format(SqlRequest.findOrdersByEmail,getEmail);
        String finalQuery=String.format(String.format(SqlRequest.getCoutRequest,findOrdersWithEmail));
        Statement stmt = Global.connect.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(finalQuery);
        rs.next();
        String countOrders=rs.getString("count");
        String text = String.format("Aktualne zamówienia(%s)",countOrders);
        return text;
    }
    public void setTextToBtn(Button button,String toSet){
        button.setText(toSet);
    }
    private void makeConnect() {
        if (Global.connect!=null){
            return;
        }
        Thread thread = new Thread(() -> {
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
        currentOrdersBtn.setClickable(toSet);
    }
    private String getData(){
        String text;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        text = sharedPreferences.getString(SharPref.EMAIL_KEY,"");
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
        Intent intent = new Intent(MainActivity.this,ActivityMail.class);
        startActivity(intent);
    }

    public void newOrder(View view) throws InterruptedException {
        Intent intent= new Intent(MainActivity.this, NewOrder.class);
        startActivity(intent);
    }
}