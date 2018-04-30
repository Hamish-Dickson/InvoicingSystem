package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Controller class for the generateReport.fxml file
 *
 * @author Hamish Dickson
 */
public class ReportScreenController {

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
    private ComboBox<String> reportChoice;

    @FXML
    private VBox col4VBox;

    @FXML
    private VBox col5VBox;

    @FXML
    private Label lblCol4;

    @FXML
    private Label lblCol5;

    private String reportType = "";

    private static final DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Preset string to store choices of reports available to user
     */
    private final String[] choices = {"Sales analysis",
            "VAT analysis",
            "Stock turnover",
            "Profitability",
            "Stock reorder"};

    /**
     * Method to initialise some of the screen features
     */
    @FXML
    public void initialize() {
        reportChoice.getItems().addAll(choices);
    }

    /**
     * Updates the value of reportType, then generates the report
     */
    @FXML
    void updateType() {
        reportType = reportChoice.getValue();
        generateReport();
    }

    /**
     * Generates a report based on the report type chosen
     */
    private void generateReport() {
        col1VBox.getChildren().clear();
        col2VBox.getChildren().clear();
        col3VBox.getChildren().clear();
        col4VBox.getChildren().clear();
        col5VBox.getChildren().clear();
        switch (reportType) {
            case "Sales analysis":
                salesReport();
                break;
            case "VAT analysis":
                vatReport();
                break;
            case "Stock turnover":
                stockReport();
                break;
            case "Profitability":
                profitReport();
                break;
            case "Stock reorder":
                reorderReport();
                break;
            default:
                System.out.println("invalid report choice");
        }
    }

    /**
     * Generates a sales report
     */
    private void salesReport() {
        DBAction dbAction = new DBAction();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> unitsSold = new ArrayList<>();
        ArrayList<Double> unitPrice = new ArrayList<>();
        ArrayList<Double> totalSales = new ArrayList<>();
        dbAction.getProductNames(names);

        for (String name : names) {
            ids.add(dbAction.getProductID(name));
        }
        for (Integer id : ids) {
            unitsSold.add(dbAction.getUnitsSold(id));
            unitPrice.add(dbAction.getProductPrice(id));
            totalSales.add(dbAction.getProductPrice(id) * dbAction.getUnitsSold(id));
        }

        lblCol1.setText("Product name");
        lblCol2.setText("Product ID");
        lblCol3.setText("Units sold");
        lblCol4.setText("Unit price");
        lblCol5.setText("Total Sales(£)");

        //populate the data to the UI
        for (String name : names) {
            generateLbl(name, col1VBox);
        }
        for (Integer id : ids) {
            generateLbl(String.valueOf(id), col2VBox);
        }
        for (Integer unitSold : unitsSold) {
            generateLbl(String.valueOf(unitSold), col3VBox);
        }
        for (Double price : unitPrice) {
            generateLbl(String.valueOf(df.format(price)), col4VBox);
        }
        for (Double total : totalSales) {
            generateLbl(String.valueOf(df.format(total)), col5VBox);
        }
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
     * Generates a VAT report
     */
    private void vatReport() {
        DBAction dbAction = new DBAction();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Double> totalSales = new ArrayList<>();
        ArrayList<Double> vat = new ArrayList<>();
        ArrayList<Double> grossSales = new ArrayList<>();
        dbAction.getProductNames(names);

        for (String name : names) {
            ids.add(dbAction.getProductID(name));
        }
        for (Integer id : ids) {
            totalSales.add(dbAction.getProductPrice(id) * dbAction.getUnitsSold(id));
            grossSales.add((dbAction.getProductPrice(id) * dbAction.getUnitsSold(id)) / 1.2);//prices stored in DB have VAT
        }
        for (Double sale : grossSales) {
            vat.add(sale * 0.20);//vat is 20% of gross sales
        }

        lblCol1.setText("Product name");
        lblCol2.setText("Product ID");
        lblCol3.setText("Gross Sales(£)");
        lblCol4.setText("VAT(£)");
        lblCol5.setText("Gross sales + VAT(£)");
        lblCol5.setStyle("-fx-pref-width: 200px");

        //populate the data to the UI
        for (String name : names) {
            generateLbl(name, col1VBox);
        }
        for (Integer id : ids) {
            generateLbl(String.valueOf(id), col2VBox);
        }
        for (Double gross : grossSales) {
            generateLbl(String.valueOf(df.format(gross)), col3VBox);
        }
        for (Double vatValue : vat) {
            generateLbl(String.valueOf(df.format(vatValue)), col4VBox);
        }
        for (Double total : totalSales) {
            generateLbl(String.valueOf(df.format(total)), col5VBox);
        }
    }

    /**
     * Generates a stock report
     */
    private void stockReport() {
        //todo implement this
    }

    /**
     * Generates a profit report
     */
    private void profitReport() {
        //todo implement this
    }

    /**
     * Generates a reorder report
     */
    private void reorderReport() {
        //todo implement this
    }

    /**
     * Calls the UIRender method to set the home screen
     */
    @FXML
    private void setHomeScreen() {
        UIRender.setHomeScreen(UIRender.getHomeScreenController().getSession().getUsername(), false);
    }

    /**
     * Prints the report
     */
    @FXML
    void printReport() {
        //todo, this currently makes the process hang and never complete, commenting out for now
        /*PrinterJob printerJob = PrinterJob.getPrinterJob();

        boolean doPrint = printerJob.printDialog();
        while (doPrint){
            try{
                printerJob.print();
            } catch (PrinterException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }
}
