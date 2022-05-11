package Database;

import android.content.SharedPreferences;

import Backend.Dish;
import Backend.ListDish;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class GetListDish {
    private ListDish listOfDish=new ListDish();
    private boolean status =false;
    public GetListDish(Connect connect){
        if (!listOfDish.isEmpty()){
            listOfDish.clear();
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                status=false;
                try{
                    Statement stmt = connect.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(
                            "SELECT * " +
                                    "FROM roznosci.dish_list dl " +
                                    "WHERE dl.available IS TRUE;");
                    while (rs.next()){
                        listOfDish.add(new Dish(rs.getString("name"),
                                                rs.getInt("weight"),
                                                rs.getDouble("price"),
                                                rs.getInt("id")));
                    }
                    status = true;
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        });
        thread.start();
    }
    public ListDish getListOfDish() {
        return listOfDish;
    }
    public boolean getStatus(){
        return status;
    }

    @Override
    public String toString() {
        String fullList="";
        for (Dish i: listOfDish.getDishList()) {
            fullList+=i.toString();
        }
        return fullList;
    }
}
