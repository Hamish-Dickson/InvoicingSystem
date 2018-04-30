package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Controller class for the viewInformation.fxml file
 *
 * @author Hamish Dickson
 */
public class InformationScreenController {

    @FXML
    private VBox col1VBox;

    @FXML
    private VBox col2VBox;

    @FXML
    private VBox col3VBox;

    @FXML
    private Label lblCol1;

    @FXML
    private Label lblCol2;

    @FXML
    private Label lblCol3;

    @FXML
    private Button btnPrint;

    @FXML
    private Label lblBuyer;

    @FXML
    private ComboBox<String> informationChoice;

    @FXML
    private VBox col4VBox;

    @FXML
    private VBox col5VBox;

    @FXML
    private Label lblCol4;

    @FXML
    private Label lblCol5;

    private String informationType;

    /**
     * Array to store the available information types
     */
    private final String[] choices = {"Customers", "Products", "Suppliers", "Invoices", "Transaction logs"};

    /**
     * Initialise some of the screen features
     */
    @FXML
    public void initialize() {
        informationChoice.getItems().addAll(choices);
    }

    /**
     * Calls the UIRender method to set the home screen
     */
    @FXML
    void setHomeScreen() {
        UIRender.setHomeScreen(UIRender.getHomeScreenController().getSession().getUsername(), false);
    }

    /**
     * Updates the informationType variable and then displays information
     */
    @FXML
    void updateType() {
        informationType = informationChoice.getValue();
        displayInformation();
    }

    /**
     * Displays information about the system based on choice
     */
    private void displayInformation() {
        col1VBox.getChildren().clear();
        col2VBox.getChildren().clear();
        col3VBox.getChildren().clear();
        col4VBox.getChildren().clear();
        col5VBox.getChildren().clear();
        switch (informationType) {
            case "Customers":
                viewCustomers();
                break;
            case "Products":
                viewProducts();
                break;
            case "Suppliers":
                viewSuppliers();
                break;
            case "Invoices":
                viewInvoices();
                break;
            case "Transaction logs":
                viewTransactions();
                break;
            default:
                System.out.println("invalid information choice");
        }
    }

    private void viewCustomers() {
        DBAction dbAction = new DBAction();
        ArrayList<String> customers = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> fnames = new ArrayList<>();
        ArrayList<String> lnames = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<Integer> orders = new ArrayList<>();

        dbAction.getCustomers(customers);

        for (String customer : customers) {
            int firstSpace = customer.indexOf(" ");
            int secondSpace = customer.indexOf(" ", firstSpace + 1);

            ids.add(Integer.parseInt(customer.substring(0, firstSpace)));
            fnames.add(customer.substring(firstSpace, secondSpace));
            lnames.add(customer.substring(secondSpace, customer.length()));
        }

        for (Integer id : ids) {
            emails.add(dbAction.getEmail(id));
            orders.add(dbAction.getOrders(id));
        }

        lblCol1.setText("First name");
        lblCol2.setText("Last name");
        lblCol3.setText("ID");
        lblCol4.setText("Email");
        lblCol5.setText("No. of orders");

        //populate the data to the UI
        for (String fname : fnames) {
            generateLbl(fname, col1VBox);
        }
        for (String lname : lnames) {
            generateLbl(lname, col2VBox);
        }
        for (Integer id : ids) {
            generateLbl(String.valueOf(id), col3VBox);
        }
        for (String email : emails) {
            generateLbl(email, col4VBox);
        }
        for (Integer order : orders) {
            generateLbl(String.valueOf(order), col5VBox);
        }
    }

    private void viewProducts() {
        //todo
    }

    private void viewSuppliers() {
        //todo
    }

    private void viewInvoices() {
        //todo
    }

    private void viewTransactions() {
        //todo
    }

    /**
     * Generates a label using given information and adds it to the given VBox
     *
     * @param text the value to make a label for
     * @param VBox the VBox to add the label to
     */
    private void generateLbl(String text, VBox VBox) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-border-width:1px;-fx-border-color:darkgray;-fx-background-color:lightgray;-fx-padding: 3 3;" +
                "-fx-spacing: 10px;-fx-pref-width: 200px");
        lbl.setTooltip(new Tooltip(text));
        VBox.getChildren().add(lbl);
    }

    /**
     * Prints information
     */
    @FXML
    void printReport() {
        //todo
    }

}

