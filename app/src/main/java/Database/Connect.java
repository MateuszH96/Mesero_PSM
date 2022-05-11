package Database;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import Backend.Utils;

public class Connect {
    Connection connection;
    public Connect(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://195.150.230.210:5436/2022_hamera_mateusz",
                    Utils.USER_DB,
                    Utils.PASSWORD_DB);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {

        return connection;
    }

    protected void closeConnection() throws Exception {
        connection.close();
    }
}
