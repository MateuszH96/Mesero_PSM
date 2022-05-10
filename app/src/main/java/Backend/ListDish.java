package Backend;

import java.util.LinkedList;
import java.util.List;

public class ListDish {
    List<Dish> dishList;
    public ListDish(){
        dishList=new LinkedList<>();
    }
    public ListDish(List<Dish> dishList){
        dishList=new LinkedList<>();
        this.dishList=dishList;
    }
    public ListDish(String input){
        dishList=new LinkedList<>();
        String[] tab=input.split(";");
        for (String i:tab) {
            dishList.add(new Dish(i));
        }
    }
    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public boolean isEmpty(){
        return dishList.isEmpty();
    }
    public void clear(){
        dishList.clear();
    }
    public void add(Dish input){
        dishList.add(input);
    }
    public int size(){
        return dishList.size();
    }
}
