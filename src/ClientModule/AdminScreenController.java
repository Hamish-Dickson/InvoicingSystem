package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Controller class for the admin.fxml file
 *
 * @author Hamish Dickson
 */

public class AdminScreenController {

    @FXML
    private TextField fnameField;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private TextField currentStockField;

    @FXML
    private TextField newStockField;

    @FXML
    private Button btnAddCustomer1;

    @FXML
    private ComboBox<String> productChoice;

    private int productID;

    /**
     * Initializes some of the UI elements
     */
    @FXML
    public void initialize() {
        productChoice.getItems().removeAll();
        DBAction dbAction = new DBAction();
        ArrayList<String> choices = new ArrayList<>();
        dbAction.getProductNames(choices);
        productChoice.getItems().addAll(choices);
        productChoice.setVisibleRowCount(5);
    }

    /**
     * Adds customers to the database, after checking values entered are valid
     */
    @FXML
    void addCustomer() {
        DBAction dbAction = new DBAction();
        if (fnameField.getText().contains("'") || lnameField.getText().contains("'") ||
                emailField.getText().contains("'")) {//cannot contain ' as it breaks DB statements
            Alert alert = new Alert(Alert.AlertType.ERROR, "values cannot contain ' ", ButtonType.CLOSE);
            alert.setHeaderText("Input error");
            alert.setTitle("Error");
            alert.show();
        } else {
            dbAction.addCustomer(fnameField.getText(), lnameField.getText(), emailField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully created customer "
                    + fnameField.getText() + " " + lnameField.getText() + " with email: "
                    + emailField.getText(), ButtonType.OK);
            alert.setHeaderText("Customer created");
            alert.setTitle("Success");
            alert.show();
        }
    }

    /**
     * Updates the number in the currentStockField field to the current stock level of the selected item
     */
    @FXML
    void updateCurrentStock() {
        DBAction dbAction = new DBAction();
        productID = dbAction.getProductID(productChoice.getValue());
        currentStockField.setText(String.valueOf(dbAction.getStock(productID)));
    }

    /**
     * Updates the stock in the database with the stock level provided
     */
    @FXML
    void updateStock() {
        DBAction dbAction = new DBAction();
        try {
            if (Integer.parseInt(newStockField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Error! cannot have stock less than 0", ButtonType.CLOSE);
                alert.setHeaderText("Quantity error");
                alert.setTitle("Quantity error");
                alert.show();
                return;
            }
            dbAction.setStock(productID, Integer.parseInt(newStockField.getText()));
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Error! check that quantity is an integer value!" +
                    e.getMessage(), ButtonType.CLOSE);
            alert.setHeaderText("Quantity error");
            alert.setTitle("Quantity error");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Set stock for product: " + productChoice.getValue() +
                " to: " + newStockField.getText(), ButtonType.OK);
        alert.setHeaderText("Stock Adjusted");
        alert.setTitle("Success");
        alert.show();
        updateCurrentStock();
    }

}

