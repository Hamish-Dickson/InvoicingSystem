package Database;

import ClientModule.Product;
import ClientModule.UIRender;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Manages the execution of actions on the database
 *
 * @author Hamish Dickson
 */
public class DBAction {
    private static final DBConnectionManager dbConnectionManager = new DBConnectionManager();

    /**
     * Logs the user into the system
     *
     * @param username the username of the user logging in
     * @param password the provided password of the user logging in
     */
    public void login(String username, String password) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet rs = null;

            rs = statement.executeQuery(("SELECT password FROM users WHERE username='" + username + "';"));

            if (!rs.next()) {//if the username was invalid
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username invalid, please try again!"
                        , ButtonType.CLOSE);
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
            } else {//if the username was valid
                if (rs.getString("password").equals(password)) {
                    UIRender.setHomeScreen(username, true);
                    UIRender.getHomeScreenController().createSession(username, getAdmin(username));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password error, please try again! "
                            , ButtonType.CLOSE);
                    alert.setHeaderText("Error");
                    alert.setTitle("Error");
                    alert.show();
                }
            }
            conn.close();
            rs.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Gets the password for the provided username
     *
     * @param username
     * @return
     */
    public String getPassword(String username) {
        String password = "";
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet rs = null;

            rs = statement.executeQuery(("SELECT password FROM users WHERE username='" + username + "';"));

            if (!rs.next()) {//if the username was invalid
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username error, please try again!"
                        , ButtonType.CLOSE);
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
            } else {//if the username was valid
                password = rs.getString("password");
            }
            conn.close();
            rs.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return password;
    }

    /**
     * Finds out if the user logged in is an admin
     *
     * @param username the username to get admin for
     * @return true if the user is an admin, else false
     */
    private boolean getAdmin(String username) {
        boolean admin = false;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = statement.executeQuery("SELECT admin FROM users where username= '" + username + "';");

            rs.next();

            admin = rs.getBoolean("Admin");

            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return admin;
    }

    /**
     * Populates the passed in ArrayList with the product names stored in database
     *
     * @param choices ArrayList of choices to be updated with values from the database
     */
    public void getProductNames(ArrayList<String> choices) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = statement.executeQuery("SELECT name FROM products;");

            while (rs.next()) {//effectively: for each product
                choices.add(rs.getString("name"));
            }

            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Populates an ArrayList with all of the customers stored in the database
     *
     * @param choices ArrayList to be populated
     */
    public void getCustomers(ArrayList<String> choices) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = statement.executeQuery("SELECT id, fname, lname FROM customers;");

            while (rs.next()) {//while there is another customer
                choices.add(rs.getInt("id") + " " + rs.getString("fname") + " " + rs.getString("lname"));
            }

            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Gets the product ID for a selected product name
     *
     * @param productName product to get ID for
     * @return Product ID
     */
    public int getProductID(String productName) {
        int productID = 0;

        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = statement.executeQuery("SELECT id FROM products WHERE name='" + productName + "';");

            while (rs.next()) {//while there are more product IDs (there should only be 1)
                productID = rs.getInt("id");
            }

            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return productID;
    }

    /**
     * Returns the current order number
     *
     * @return current order number
     */
    private int getOrderNo() {
        int orderNo = 0;

        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            //find what the next order number should be,
            // based on the current maximum order number.
            ResultSet rs = statement.executeQuery("SELECT MAX(id) FROM orders");

            rs.next();

            orderNo = rs.getInt("MAX(id)");

            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return orderNo;
    }

    /**
     * Stores the sales within the database
     *
     * @param products ArrayList of products to be added
     */
    public void recordSale(ArrayList<Product> products) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            //creates an order for this order
            statement.executeUpdate("INSERT INTO orders(ctr_id) VALUES (" + products.get(0).getSellerID() + ");");

            int orderNo = getOrderNo();

            for (Product product : products) {
                statement.executeUpdate("INSERT INTO ordered_items(pdt_id, odr_id, quantity_ordered) VALUES ("
                        + product.getId() + "," + orderNo + "," + product.getQuantity() + ");");
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Updates the stock level of the product
     *
     * @param productID ID of the product to update stock for
     * @param quantity  quantity of product purchased
     */
    public void updateStock(int productID, int quantity) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            statement.executeUpdate("UPDATE products SET quantity = quantity - " + quantity + " WHERE id = "
                    + productID + ";");

            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Gets the price of the selected product
     *
     * @param productID the ID of the product to get the price for
     * @return price of the product
     */
    public double getProductPrice(int productID) {
        double price = 0;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs;
            rs = statement.executeQuery("SELECT price FROM products WHERE id=" + productID + ";");

            while (rs.next()) {//while there are more prices(should only be 1)
                price = rs.getDouble("price");
            }
            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return price;
    }

    /**
     * Gets the discount for the specified product
     *
     * @param productID the product ID to get the discount of
     * @return product's discount
     */
    public double getDiscount(int productID) {
        double discount = 0;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs;
            rs = statement.executeQuery("SELECT discount FROM products WHERE id=" + productID + ";");

            while (rs.next()) {
                discount = rs.getDouble("discount");
            }
            conn.close();
            statement.close();
            rs.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
        return discount;
    }

    /**
     * Sets the discount of the selected product
     *
     * @param productID product to set discount for
     * @param discount  discount to be applied
     */
    public void setDiscount(int productID, double discount) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            statement.executeUpdate("UPDATE products SET discount=" + discount + " WHERE id=" + productID + ";");

            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Gets the amount of units sold for a selected product
     *
     * @param productID the product ID of the product to get the units sold for
     * @return the units sold for selected product
     */
    public Integer getUnitsSold(int productID) {
        Integer units = 0;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT SUM(quantity_ordered) AS \"Units sold\" FROM ordered_items" +
                    " WHERE pdt_id=" + productID + ";");

            while (rs.next()) {
                units = rs.getInt("Units sold");
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return units;
        }
        return units;
    }

    /**
     * Gets the selected customer's email address
     *
     * @param customerID the customer to get the email address for
     * @return the customer's email address
     */
    public String getEmail(Integer customerID) {
        String email = "";
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT email FROM customers WHERE id=" + customerID + ";");

            while (rs.next()) {
                email = rs.getString("email");
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return "";
        }
        return email;
    }

    /**
     * Returns the amount of orders that the selected customer has placed
     *
     * @param customerID the customer to get the orders of
     * @return number of orders the customer has placed
     */
    public Integer getOrders(Integer customerID) {
        Integer orders = 0;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT COUNT(*) AS \"ordered\" FROM orders WHERE ctr_id="
                    + customerID + ";");

            while (rs.next()) {
                orders = rs.getInt("ordered");
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return orders;
        }
        return orders;
    }

    /**
     * Returns the stock level of the selected product
     *
     * @param productID the id of the product to get stock for
     * @return the stock level of selected product
     */
    public int getStock(int productID) {
        int stock = 0;
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT quantity FROM products WHERE id=" + productID + ";");

            while (rs.next()) {
                stock = rs.getInt("quantity");
            }
            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return 0;
        }
        return stock;
    }

    /**
     * Sets the stock level of a selected product to the stock level provided
     *
     * @param productID the product to change the stock for
     * @param stock     the stock level to set for the product
     */
    public void setStock(int productID, Integer stock) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            statement.executeUpdate("UPDATE products SET quantity=" + stock + " WHERE id=" + productID + ";");

            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }

    }

    /**
     * Adds a customer to the database
     *
     * @param fname first name for the customer to add
     * @param lname last name for the customer to add
     * @param email email address for the customer to add
     */
    public void addCustomer(String fname, String lname, String email) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            statement.executeUpdate("INSERT INTO customers(fname, lname, email)" +
                    " VALUES( '" + fname + "', '" + lname + "', '" + email + "');");

            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }

    }

    /**
     * Updates the password for the given user
     *
     * @param username the user to update's username
     * @param password the new password for this user
     */
    public void updatePassword(String username, String password) {
        Connection conn = dbConnectionManager.connect();
        Statement statement = null;
        try {
            statement = conn.createStatement();

            statement.executeQuery("USE gradedunit");

            statement.executeUpdate("UPDATE users SET password = '" + password + "' WHERE username = '"
                    + username + "';");

            conn.close();
            statement.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }
}
