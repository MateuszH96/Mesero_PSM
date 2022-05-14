package Database;

public class SqlRequest {
    public static final String sendOrderToDatabase=  "INSERT INTO roznosci.order VALUES(%d,%d,'%s',FALSE);";
    public static final String getIdRequest = "SELECT MAX(o.id_order) AS \"MAX\" FROM roznosci.order o;";
    public static final String getFullListRequest = "SELECT * FROM roznosci.dish_list dl WHERE dl.available IS TRUE;";
    public static final String getCoutRequest= "SELECT COUNT(*) FROM (%s) AS foo;";
    public static final String findOrdersByEmail="SELECT o.id_order FROM roznosci.order o WHERE o.email = '%s' AND o.ordered=FALSE GROUP BY o.id_order";
    public static final String getFullListOfCurrentOrders="SELECT * FROM roznosci.order o INNER JOIN roznosci.dish_list dl ON o.id_dish = dl.id WHERE o.email = '%s' AND o.ordered=%s ORDER BY o.id_order";
    public static final String getListOfCurrentOrdersById="SELECT * FROM roznosci.order o INNER JOIN roznosci.dish_list dl ON o.id_dish = dl.id WHERE o.email = '%s' AND o.id_order = %s AND o.ordered=%s ORDER BY o.id_order";
    public static final String getListIdCurretOrders ="SELECT foo.id_order FROM (%s) AS foo GROUP BY foo.id_order";
    public static final String setOrder="UPDATE roznosci.order SET ordered=TRUE WHERE id=%d;";
}
