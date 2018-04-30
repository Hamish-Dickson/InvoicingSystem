package ClientModule;

import Database.DBAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;


/**
 * Controller class for the viewTrends.fxml file
 *
 * @author Hamish Dickson
 */
public class TrendScreenController {

    @FXML
    private TableColumn<?, ?> colProductID;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colUnitsSold;

    @FXML
    private TableColumn<?, ?> colIncrease;

    @FXML
    private TableColumn<?, ?> colPopularity;

    @FXML
    private Button btnPrint;

    /**
     * Method called when page is loaded.
     */
    @FXML
    public void initialize() {
        populateTable();
    }

    /**
     * Populates the table with relevant trend statistics, from the database
     */
    private void populateTable() {
        DBAction dbAction = new DBAction();
        //todo
    }

    /**
     * Prints the trend selection
     */
    @FXML
    void print() {
        //todo
    }

    /**
     * Calls the UIRender method to set home screen
     */
    @FXML
    void setHomeScreen() {
        UIRender.setHomeScreen(UIRender.getHomeScreenController().getSession().getUsername(), false);
    }

}
