package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.sql.Statement;

import Backend.Global;
import Backend.Mail.SendMail;
import Database.SqlRequest;

public class ShowOrder extends AppCompatActivity {

    TextView textView;
    Button takeOrder;
    String id;
    String listOrder;
    String mail;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        textView=findViewById(R.id.listOrder);
        takeOrder=findViewById(R.id.takeOrder);
        String visibleButton= getIntent().getStringExtra("visible");
        id=getIntent().getStringExtra("idOrder");
        if (!visibleButton.equals("show")){
            takeOrder.setVisibility(View.INVISIBLE);
        }
        listOrder=getIntent().getStringExtra("toPrint");
        textView.setText(listOrder);
        mail=getData();
    }

    private String getData() {
        String text;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        text = sharedPreferences.getString(SharPref.EMAIL_KEY,"");
        return text;
    }

    public void onClickClose(View view) {
        finish();
    }

    public void onClickTakeOrder(View view) {
        Context context = this;
        String mess = listOrder;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String sqlRequest=String.format(SqlRequest.setOrder,id);
                try {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            SendMail.sendMailMessage(context,mail,"Twoje zamówienie",mess);
                        }
                    });
                    Statement stmt = Global.connect.getConnection().createStatement();
                    stmt.executeUpdate(sqlRequest);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                finish();
            }
        });
        thread.start();
    }
}