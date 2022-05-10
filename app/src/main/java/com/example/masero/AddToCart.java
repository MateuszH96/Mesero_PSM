package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddToCart extends AppCompatActivity {

    TextView quantity;
    TextView price;
    ImageView image;
    Integer count =1;
    Double dishPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        quantity=findViewById(R.id.quantity);
        price=findViewById(R.id.priceTotal);
        image=findViewById(R.id.myImage);
        dishPrice=getIntent().getDoubleExtra("price",0.0);
        price.setText(dishPrice.toString()+"zł");
        image.setImageResource(Integer.parseInt(getIntent().getStringExtra("photo")));
    }

    public void onClickDecrement(View view) {
        if(count>1){
            count--;
        }
        Double result=count*dishPrice;
        price.setText(result.toString().substring(0,result.toString().indexOf(".")+3)+"zł");
        quantity.setText(count.toString());
    }

    public void onClickIncrement(View view) {
        if(count<10){
            count++;
        }
        Double result=count*dishPrice;
        price.setText(result.toString().substring(0,result.toString().indexOf(".")+3)+"zł");
        quantity.setText(count.toString());
    }

    public void add(View view) {
    }
}