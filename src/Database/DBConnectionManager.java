package Database;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages connections to the database
 *
 * @author Hamish Dickson
 */
class DBConnectionManager {

    /**
     * Creates a connection to the database
     *
     * @return Connection for connecting to database
     */
    Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gradedUnit?autoReconnect=true&useSSL=false&"
                    + "user=root&password=root");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error connecting to the database!", ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return null;
        }
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something else went wrong, please retry!"
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return null;
        }
        return connection;
    }
}
