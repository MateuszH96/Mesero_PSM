package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL_KEY",input);
        editor.apply();
        Toast.makeText(this,"ustawiono mail: \n"+input,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ActivityMail.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private String getData(){
        String text;
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        text = sharedPreferences.getString("EMAIL_KEY","");
        return text;
    }
    private void checkIsEmail() {
        String mail=getData();
        if (mail!=""){
            editText.setText(mail);
        }
    }
}