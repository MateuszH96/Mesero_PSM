package Backend;

public class FormatOrderToActivity {
    public static String format(Order order){
        String output ="";
        Double totalPrice = 0.0;
        Dish dish;
        String[] orderString = order.toString().split(";");
        for (int i = 1 ; i<orderString.length; i++){
            dish = new Dish(orderString[i]);
            totalPrice += dish.getPrice();
            output+= dish.getName()+'\n'+"\t\t\t"+dish.getPrice().toString()+'\n';
        }
        output+="\nCena końcowa: "+totalPrice.toString();
        return output;
    }
}
