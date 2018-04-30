package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Controller class for the addDiscount.fxml file
 *
 * @author Hamish Dickson
 */
public class DiscountScreenController {

    @FXML
    private ComboBox<String> productChoice;

    @FXML
    private TextField currentDiscountField;

    @FXML
    private TextField discountField;

    @FXML
    private Button btnApply;

    @FXML
    private Label lblBuyer;

    /**
     * Initializes some screen features
     */
    @FXML
    public void initialize() {
        populateChoices();
    }

    /**
     * Populates an ArrayList with the products in the database and updates the productChoice ComboBox with the values
     */
    private void populateChoices() {
        DBAction dbAction = new DBAction();
        ArrayList<String> choices = new ArrayList<>();
        dbAction.getProductNames(choices);
        productChoice.getItems().removeAll(productChoice.getItems());
        productChoice.getItems().addAll(choices);
    }

    /**
     * Applies the discount to the selected product
     */
    @FXML
    void applyDiscount() {
        try {
            Double.parseDouble(discountField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid double value to set discount to!",
                    ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return;
        }
        if (Double.parseDouble(discountField.getText()) < 0 || Double.parseDouble(discountField.getText()) >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid discount between 0 and 99.99%",
                    ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return;
        }
        DBAction dbAction = new DBAction();
        dbAction.setDiscount(dbAction.getProductID(productChoice.getValue()),
                Double.parseDouble(discountField.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Set discount for product: " + productChoice.getValue()
                + " to: " + discountField.getText() + "%", ButtonType.OK);
        alert.setHeaderText("Discount set");
        alert.setTitle("Success");
        alert.show();
    }

    /**
     * Calls the UIRender method to set the home screen
     */
    @FXML
    void setHomeScreen() {
        UIRender.setHomeScreen(UIRender.getHomeScreenController().getSession().getUsername(), false);
    }

    /**
     * Changes the TextField currentDiscountField to the current discount for the selected product
     */
    @FXML
    void updateDiscount() {
        DBAction dbAction = new DBAction();
        double discount = dbAction.getDiscount(dbAction.getProductID(productChoice.getValue()));
        currentDiscountField.setText("" + discount);
    }


}
