package Database;

public class SqlRequest {
    public static final String sendOrderToDatabase=  "INSERT INTO roznosci.order VALUES(%d,%d,'%s');";
    public static final String getIdRequest = "SELECT MAX(o.id_order) AS \"MAX\" FROM roznosci.order o;";
    public static final String getFullListRequest = "SELECT * FROM roznosci.dish_list dl WHERE dl.available IS TRUE;";
    public static final String getCoutRequest= "SELECT COUNT(*) FROM (%s) AS foo;";
    public static final String findOrdersByEmail="SELECT o.id_order FROM roznosci.order o WHERE o.email = '%s' GROUP BY o.id_order";
}
