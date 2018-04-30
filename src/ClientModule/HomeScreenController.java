package ClientModule;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Controller class for the home.fxml file
 *
 * @author Hamish Dickson
 */
public class HomeScreenController {
    @FXML
    private Label usernameLbl;

    @FXML
    private Button btnAdmin;

    private static AdminScreenController adminScreenController;

    private static PasswordScreenController passwordScreenController;

    private Session session;

    /**
     * Sets the username label value to the current user's username
     *
     * @param uName the current user's username
     */
    public void setUsername(String uName) {
        usernameLbl.setText(uName.toUpperCase());
    }

    /**
     * Creates a session for the current user
     *
     * @param username the username of the current user
     * @param admin    the admin status of the current user
     */
    public void createSession(String username, boolean admin) {
        session = new Session(username, admin);
        if (!admin) {
            btnAdmin.setVisible(false);
        }
    }

    /**
     * Returns the user to the login screen and removes the current session
     */
    public void logOut() {
        UIRender.setLoginScreen();
        session = null;
    }

    /**
     * Calls the UIRender method to set the sale screen
     */
    public void setSaleScreen() {
        UIRender.setSaleScreen();
    }

    /**
     * Calls the UIRender method to set the report screen
     */
    public void setReportScreen() {
        UIRender.setReportScreen();
    }

    /**
     * Calls the UIRender method to set the information screen
     */
    public void setInformationScreen() {
        UIRender.setInformationScreen();
    }

    /**
     * Calls the UIRender method to set the discount screen
     */
    public void setDiscountScreen() {
        UIRender.setDiscountScreen();
    }

    /**
     * Calls the UIRender method to set the trend screen
     */
    public void setTrendScreen() {
        UIRender.setTrendScreen();
    }

    /**
     * Sets a new stage for the admin functions
     */
    public void adminFunctions() {
        Stage adminStage = new Stage();
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("admin.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            adminScreenController = loader.getController();
            Scene admin = new Scene(root, 600, 600);
            adminStage.setScene(admin);
            adminStage.show();
            adminStage.setResizable(false);
            adminStage.getIcons().add(new Image("file:./media/admin.png"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    public void changePassword() {
        Stage passwordStage = new Stage();
        FXMLLoader loader = new FXMLLoader(UIRender.class.getResource("password.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            passwordScreenController = loader.getController();
            Scene pw = new Scene(root, 600, 600);
            passwordStage.setScene(pw);
            passwordStage.show();
            passwordStage.setResizable(false);
            passwordStage.getIcons().add(new Image("file:./media/settings.png"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong! " + e.getMessage()
                    , ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.setTitle("Error");
            alert.show();
        }
    }

    /**
     * @return the current session
     */
    public Session getSession() {
        return session;
    }
}
