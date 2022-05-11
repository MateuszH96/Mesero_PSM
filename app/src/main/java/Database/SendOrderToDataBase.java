package Database;

import android.app.Application;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Backend.Dish;
import Backend.Global;

public class SendOrderToDataBase{
    private static final String sql ="INSERT INTO roznosci.order VALUES(%d,%d,'%s',5);";
    public static void sendOrder( Integer id,String email,Dish dish) throws SQLException {
        String sqlRequest = String.format(sql,id,dish.getId(),email);
        Statement stmt = Global.connect.getConnection().createStatement();
        stmt.executeUpdate("INSERT INTO roznosci.order VALUES(0,1,'mateuszhamera3@gmail.com',1);");
    }
}
