package ClientModule;

import Database.DBManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that performs the rendering of UI elements
 *
 * @author Hamish Dickson
 */
public class UIRender extends Application {
    //stores the controllers for all of the xml files. currently only the home screen one is used
    //but further development may require the other screen's controllers.
    private static HomeScreenController homeScreenController;
    private static SaleScreenController saleScreenController;
    private static LoginScreenController loginScreenController;
    private static ReportScreenController reportScreenController;
    private static InformationScreenController informationScreenController;
    private static DiscountScreenController discountScreenController;
    private static TrendScreenController trendScreenController;

    private static Stage screen;
    private DBManager dbManager;

    /**
     * Entry point for JavaFX Application
     *
     * @param primaryStage the JavaFX Stage
     */
    @Override
    public void start(Stage primaryStage) {
        screen = primaryStage;
        setLoginScreen();
        dbManager = new DBManager();
        dbManager.startup();
        screen.requestFocus();
        screen.getIcons().add(new Image("file:./media/icon.png"));
    }

    /**
     * Renders the UI elements for the home screen
     *
     * @param username username String of user logging in
     * @param newLogin Boolean declaring if this is a new user logging in
     */
    public static void setHomeScreen(String username, boolean newLogin) {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            Session tempSession = null;

            if (!newLogin) {
                tempSession = homeScreenController.getSession();
            }

            homeScreenController = loader.getController();

            if (!newLogin) {
                //re create session using values from temp session
                homeScreenController.createSession(tempSession.getUsername(), tempSession.isAdmin());
            }

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
            homeScreenController.setUsername(username);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the login screen
     */
    static void setLoginScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            loginScreenController = loader.getController();

            screen.setTitle("Invoicing System");
            screen.setScene(new Scene(root, 1280, 720));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the sale screen
     */
    static void setSaleScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("enterSale.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            saleScreenController = loader.getController();

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the report screen
     */
    static void setReportScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("generateReport.fxml"));
        Parent root = null;
        try {
            root = loader.load();

            reportScreenController = loader.getController();

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the information screen
     */
    static void setInformationScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("viewInformation.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            informationScreenController = loader.getController();

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the discount screen
     */
    static void setDiscountScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("addDiscount.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            discountScreenController = loader.getController();

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Renders the UI elements for the trend screen
     */
    static void setTrendScreen() {
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("viewTrends.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            trendScreenController = loader.getController();

            screen.setScene(new Scene(root));

            screen.show();
            screen.setResizable(false);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * Shuts down the database when the application is exited
     */
    @Override
    public void stop() {
        dbManager.shutdown();
    }

    /**
     * Returns the instance of HomeScreenController
     *
     * @return the HomeScreenController
     */
    public static HomeScreenController getHomeScreenController() {
        return homeScreenController;
    }

    /**
     * Entry point for the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
