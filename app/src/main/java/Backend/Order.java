package Backend;

import java.util.LinkedList;
import java.util.List;

public class Order {
    List<Dish> listDish = new LinkedList<Dish>();
    Integer idOrder;
    public Order(Integer idOrder,List<Dish>list){
        this.idOrder = idOrder;
        listDish=list;
    }
    public Order(String input){
        String[] tab = input.split(";");
        idOrder=Integer.parseInt(tab[0]);
        for (int i=1;i< tab.length;i++){
            listDish.add(new Dish(tab[i]));
        }
    }

    public List<Dish> getListDish() {
        return listDish;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    @Override
    public String toString() {
        String toReturn= idOrder.toString()+";";
        for (Dish i:listDish) {
            toReturn+=i.toString();
        }
        return toReturn;
    }
}
