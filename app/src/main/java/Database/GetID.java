package Database;

import android.content.Context;
import android.widget.Toast;

import com.example.masero.NewOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Backend.Global;

public class GetID {
    public static Integer getIDFromBase(Context context) throws SQLException {
        String toReturn = "";
        Statement stmt = Global.connect.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(o.id_order) FROM roznosci.order o;");
        while (rs.next()){
            toReturn=rs.getString("id_order");
        }
        Toast.makeText(context,"aaaaaaaaa",Toast.LENGTH_LONG).show();
        return Integer.parseInt(toReturn)+1;
    }
}
