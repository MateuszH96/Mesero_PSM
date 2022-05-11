package com.example.masero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Backend.Dish;

public class AddToCart extends AppCompatActivity {

    TextView quantity;
    TextView price;
    ImageView image;
    Integer count =1;
    Dish dish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        quantity=findViewById(R.id.quantity);
        price=findViewById(R.id.priceTotal);
        image=findViewById(R.id.myImage);
        dish=new Dish(getIntent().getStringExtra("dish"));
        price.setText(dish.getPrice().toString()+"zł");
        image.setImageResource(Integer.parseInt(getIntent().getStringExtra("photo")));
    }

    public void onClickDecrement(View view) {
        if(count>1){
            count--;
        }
        Double result=count*dish.getPrice();
        price.setText(result.toString().substring(0,result.toString().indexOf(".")+3)+"zł");
        quantity.setText(count.toString());
    }

    public void onClickIncrement(View view) {
        if(count<10){
            count++;
        }
        Double result=count*dish.getPrice();
        price.setText(result.toString().substring(0,result.toString().indexOf(".")+3)+"zł");
        quantity.setText(count.toString());
    }

    public void add(View view) {
        String currentSp;
        String newSp;
        SharedPreferences sharedPreferences = getSharedPreferences(SharPref.SHAR_PREF,MODE_PRIVATE);
        currentSp=sharedPreferences.getString(SharPref.ORDER_KEY,"");
        newSp=currentSp;
        for (int i = 0;i<count;i++){
            newSp+=dish.toString();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharPref.ORDER_KEY,newSp);
        editor.apply();
        Toast.makeText(this,String.format("Dodano %dx %s",count,dish.getName()),Toast.LENGTH_LONG).show();
        finish();
    }
}