package Database;

public class SqlRequest {
    public static final String sendOrderToDatabase=  "INSERT INTO roznosci.order VALUES(%d,%d,'%s');";
    public static final String getIdRequest = "SELECT MAX(o.id_order) AS \"MAX\" FROM roznosci.order o;";
}
