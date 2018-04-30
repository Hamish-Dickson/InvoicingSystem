package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Controller class for the enterSale.fxml file
 *
 * @author Hamish Dickson
 */
public class SaleScreenController {
    @FXML
    private Label lblBuyer;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> productChoice;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> buyerChoice;

    @FXML
    private VBox namesListVBox;

    @FXML
    private VBox quantityListVBox;

    @FXML
    private VBox priceListVBox;

    private ArrayList<Product> products = new ArrayList<>();

    private static final DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Initialise some of the screen features
     */
    @FXML
    public void initialize() {
        populateCustomers();
        populateProducts();
        productChoice.setPromptText("Select Product");
        buyerChoice.setPromptText("Select Customer");
        priceField.setText("0");
    }

    /**
     * Populates an ArrayList with the customers in the database and updates the buyerChoice ComboBox with the values
     */
    private void populateCustomers() {
        DBAction dbAction = new DBAction();
        ArrayList<String> choices = new ArrayList<>();
        dbAction.getCustomers(choices);
        buyerChoice.getItems().removeAll(productChoice.getItems());
        buyerChoice.getItems().addAll(choices);
        buyerChoice.setVisibleRowCount(5);
    }

    /**
     * Populates an ArrayList with the products in the database and updates the productChoice ComboBox with the values
     */
    private void populateProducts() {
        DBAction dbAction = new DBAction();
        ArrayList<String> choices = new ArrayList<>();
        dbAction.getProductNames(choices);
        productChoice.getItems().removeAll(productChoice.getItems());
        productChoice.getItems().addAll(choices);
        productChoice.setVisibleRowCount(5);
    }

    /**
     * Records a sale in the database
     */
    @FXML
    private void sale() {
        lblBuyer.setVisible(false);
        buyerChoice.setVisible(true);

        DBAction dbAction = new DBAction();

        dbAction.recordSale(products);

        for (Product product : products) {
            dbAction.updateStock(product.getId(), product.getQuantity());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully recorded sale to customer: "
                + buyerChoice.getValue(), ButtonType.OK);
        alert.setHeaderText("Sale recorded");
        alert.setTitle("Success");
        alert.show();

        products.clear();
        priceListVBox.getChildren().clear();
        quantityListVBox.getChildren().clear();
        namesListVBox.getChildren().clear();
        quantityField.setText("");

    }

    /**
     * Adds products to the sold products
     */
    @FXML
    private void addProducts() {
        //probably a better way to handle this.
        if (productChoice.getValue() == null || priceField.getText().equals("") ||
                buyerChoice.getValue() == null || quantityField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are mandatory!", ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return;
        }

        try {
            int x = Integer.parseInt(quantityField.getText());
            double i = Double.parseDouble(priceField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please put the correct data types in the fields"
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return;
        }

        if (Integer.parseInt(quantityField.getText()) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot have less than 0 quantity", ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
            return;
        }

        for (Product product : products) {
            if (product.getName().equals(productChoice.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You tried to add the same product twice," +
                        " please instead change the quantity of the previous product selection!", ButtonType.CLOSE);
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
                return;
            }
        }

        buyerChoice.setVisible(false);
        lblBuyer.setText(buyerChoice.getValue());
        lblBuyer.setVisible(true);

        //last parameter of product is buyer ID.
        //I use substring to find the value before the first space in the ComboBox this is the buyer ID.
        products.add(new Product(productChoice.getValue(),
                Double.parseDouble(priceField.getText()),
                Integer.parseInt(quantityField.getText()),
                Integer.parseInt(buyerChoice.getValue().substring(0, buyerChoice.getValue().indexOf(" ")))));
        updateBasket();

    }

    /**
     * Updates the basket with current products
     */
    private void updateBasket() {
        namesListVBox.getChildren().clear();
        quantityListVBox.getChildren().clear();
        priceListVBox.getChildren().clear();

        for (Product product : products) {
            Label name = new Label(product.getName());
            Label quantity = new Label("" + product.getQuantity());
            Label price = new Label("" + df.format(product.getPrice() * product.getQuantity()));

            name.setTooltip(new Tooltip(product.getName()));
            quantity.setTooltip(new Tooltip("" + product.getQuantity()));
            price.setTooltip(new Tooltip("" + product.getPrice() * product.getQuantity()));

            namesListVBox.getChildren().add(name);
            quantityListVBox.getChildren().add(quantity);
            priceListVBox.getChildren().add(price);
        }
    }

    /**
     * Updates the price displayed in the Price field
     */
    @FXML
    private void updatePrice() {
        DBAction dbAction = new DBAction();
        String productName = productChoice.getValue();
        int productID = dbAction.getProductID(productName);
        priceField.setText(String.valueOf(df.format(dbAction.getProductPrice(productID))));
    }

    /**
     * Calls the UIRender method to set the home screen
     */
    @FXML
    private void setHomeScreen() {
        UIRender.setHomeScreen(UIRender.getHomeScreenController().getSession().getUsername(), false);
    }

}
