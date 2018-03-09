/*
* Rita Sachechelashvili
* 1928162
* sache100@mail.chapman.edu
* CPSC408
* Purpose of this file is to serve as database configuration file
 */
import java.sql.DriverManager;
import java.sql.Connection;
public class DbConfig {

    public static Connection getMySqlConnection() {
        Connection mysqlConnection = null;
        try {
            //returns the Class object associated with the class or interface with the given string name
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost:3306/StudentDB"; //could be ip address or hostname
            mysqlConnection = DriverManager.getConnection(connectionUrl, "root", ""); //I purposefully did not set the password for easier access

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mysqlConnection;
    }
}

