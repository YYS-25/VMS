package pkg.vms.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    // This method is called automatically after FXML is loaded
    @FXML
    private void initialize() {
        // Set button actions
        loginButton.setOnAction(e -> handleLogin());
        cancelButton.setOnAction(e -> handleCancel());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Example validation: username and password not empty
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username and Password cannot be empty!");
            alert.showAndWait();
        } else {
            // Here you could check credentials with your database/DAO
            System.out.println("Login attempted: " + username + " / " + password);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Success");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, " + username + "!");
            alert.showAndWait();
        }
    }

    private void handleCancel() {
        // Clear both fields
        usernameField.clear();
        passwordField.clear();
    }
}
