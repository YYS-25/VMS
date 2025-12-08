package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pkg.vms.DBconnection.DBconnection;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        cancelButton.setOnAction(e -> handleCancel());
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username and Password cannot be empty!");
            alert.showAndWait();
            return;
        }

        try {
            Connection conn = DBconnection.getConnection();

            String checkUserQuery = "SELECT password, role FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (storedPassword.equals(password)) {
                    // ========== LOGIN SUCCESS → LOAD DASHBOARD ==========
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/pkg/vms/fxml/Dashboard.fxml")
                        );
                        Parent root = loader.load();

                        // Store user session
                        String userRole = rs.getString("role");
                        UserSession.getInstance().setUser(username, userRole);

                        // Pass username & role to Dashboard
                        DashboardController controller = loader.getController();
                        controller.setUserInfo(username, userRole);

                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect password!");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("No user with username '" + username + "' was found!");
                alert.showAndWait();
            }

            rs.close();
            checkStmt.close();

        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Database connection error: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void handleCancel() {
        usernameField.clear();
        passwordField.clear();
    }
}
