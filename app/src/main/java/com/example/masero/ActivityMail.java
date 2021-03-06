package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Backend.Global;
import Database.SqlRequest;

public class ActivityMail extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        editText=findViewById(R.id.mailToSet);
        checkIsEmail();
    }

    public void clickSaveMail(View view) {
        saveData(editText.getText().toString());
    }

    private void saveData(String input) {
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharPref.EMAIL_KEY,input);
        editor.apply();
        Toast.makeText(this,"ustawiono mail: \n"+input,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ActivityMail.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private String getData(){
        String text;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        text = sharedPreferences.getString(SharPref.EMAIL_KEY,"");
        return text;
    }
    private void checkIsEmail() {
        String mail=getData();
        if (mail!=""){
            editText.setText(mail);
        }
    }
}