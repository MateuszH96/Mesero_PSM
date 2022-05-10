package Backend.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masero.AddToCart;
import com.example.masero.R;

import java.util.LinkedList;
import java.util.List;

import Backend.Dish;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Dish> listOfDish = new LinkedList<Dish>();
    List<Integer> images = new LinkedList<Integer>();
    Context context;
    public MyAdapter(Context context, List<Dish> listOfDish,List<Integer>images){
        this.context=context;
        this.listOfDish=listOfDish;
        this.images=images;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dish_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(listOfDish.get(position).getName());
        holder.weight.setText(listOfDish.get(position).getWeight().toString()+" gram");
        holder.price.setText(listOfDish.get(position).getPrice().toString()+" zł");
        holder.imageView.setImageResource(images.get(position).intValue());
        int pos = position;
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddToCart.class);
                intent.putExtra("price",listOfDish.get(pos).getPrice());
                intent.putExtra("photo",images.get(pos).toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfDish.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,weight,price;
        ImageView imageView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            weight=itemView.findViewById(R.id.weight);
            price=itemView.findViewById(R.id.price);
            imageView=itemView.findViewById(R.id.imageView);
            constraintLayout=itemView.findViewById(R.id.constLayout);

        }
    }
}
