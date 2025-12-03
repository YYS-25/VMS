package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

            // First check if user exists
            String checkUserQuery = "SELECT password FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // User exists, check password
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Login Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + username + "!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect password!");
                    alert.showAndWait();
                }
            } else {
                // User doesn't exist
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
