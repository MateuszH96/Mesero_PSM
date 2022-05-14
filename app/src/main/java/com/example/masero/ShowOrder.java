package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowOrder extends AppCompatActivity {

    TextView textView;
    Button takeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        textView=findViewById(R.id.listOrder);
        takeOrder=findViewById(R.id.takeOrder);
        String visibleButton= getIntent().getStringExtra("visible");
        if (!visibleButton.equals("show")){
            takeOrder.setVisibility(View.INVISIBLE);
        }
        String listOrder=getIntent().getStringExtra("toPrint");
        textView.setText(listOrder);
    }

    public void onClickClose(View view) {
        finish();
    }
}