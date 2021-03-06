package Backend.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masero.R;

import java.util.LinkedList;
import java.util.List;

import Backend.Dish;
import Backend.FormatOrderToActivity;
import Backend.Order;
import com.example.masero.ShowOrder;

public class MyAdapterOrders extends RecyclerView.Adapter<MyAdapterOrders.MyViewHolder> {
    List<Order> order = new LinkedList<>();
    Context context;
    String buttonVisible;

    public MyAdapterOrders(Context context, List<Order> order,String buttonVisible) {
        this.order = order;
        this.context = context;
        this.buttonVisible=buttonVisible;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String listNames = "";
        boolean refractorString = false;
        int lenString = 25;
        int iterator = 1;
        for (Dish i : order.get(position).getListDish()) {
            listNames += i.getName();
            if (listNames.length() >= lenString) {
                refractorString = true;
                break;
            }

            if (iterator != order.get(position).getListDish().size()) {
                listNames += ", ";
            }
            iterator++;
        }
        //String strNames=listNames.substring(0,listNames.length()-1);
        if (refractorString) {
            listNames += "...";
        }
        holder.orderId.setText("#" + order.get(position).getIdOrder().toString());
        holder.shortlist.setText(listNames);
        Double sum = 0.0;
        for (Dish i : order.get(position).getListDish()) {
            sum += i.getPrice();
        }
        holder.priceOrder.setText(sum.toString() + "z??");
        int pos = position;
        Integer idOrderToSend = order.get(pos).getIdOrder();
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSend = FormatOrderToActivity.format(order.get(pos));
                Intent intent = new Intent(context, ShowOrder.class);
                intent.putExtra("toPrint",toSend);
                intent.putExtra("visible",buttonVisible);
                intent.putExtra("idOrder",idOrderToSend.toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView priceOrder, shortlist, orderId;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            priceOrder = itemView.findViewById(R.id.priceOrder);
            shortlist = itemView.findViewById(R.id.shortlist);
            orderId = itemView.findViewById(R.id.orderId);
            constraintLayout = itemView.findViewById(R.id.constLayoutOrderRow);

        }
    }
}

/*
public  class MyAdapterOrders extends RecyclerView.Adapter<MyAdapterOrders.MyViewHolder>{
    Context context;
    List<Order> orders = new LinkedList<Order>();
    public MyAdapterOrders(Context context, List<Order> orders){
        this.context=context;
        this.orders=orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}*/