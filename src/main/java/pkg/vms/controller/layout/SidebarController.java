package pkg.vms.controller.layout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class SidebarController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button requestsButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button vouchersButton;

    @FXML
    private Button branchesButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button reportsButton;

    // Simple callback used to inform the main application which view to show
    private Consumer<String> navigationHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Optional: set default active item or attach additional listeners
        setActive("dashboard");
    }

    // Called by the FXML buttons
    @FXML
    private void onDashboard(ActionEvent e) {
        navigate("dashboard");
    }

    @FXML
    private void onRequests(ActionEvent e) {
        navigate("requests");
    }

    @FXML
    private void onClients(ActionEvent e) {
        navigate("clients");
    }

    @FXML
    private void onVouchers(ActionEvent e) {
        navigate("vouchers");
    }

    @FXML
    private void onBranches(ActionEvent e) {
        navigate("branches");
    }

    @FXML
    private void onUsers(ActionEvent e) {
        navigate("users");
    }

    @FXML
    private void onReports(ActionEvent e) {
        navigate("reports");
    }

    // Public API for the host application to receive navigation events
    public void setNavigationHandler(Consumer<String> handler) {
        this.navigationHandler = handler;
    }

    private void navigate(String target) {
        if (navigationHandler != null) {
            navigationHandler.accept(target);
        } else {
            // fallback for testing without a host
            System.out.println("Sidebar navigate: " + target);
        }
        setActive(target);
    }

    // Visual feedback: mark the matching button as active
    public void setActive(String name) {
        // Reset "active" class from all buttons
        dashboardButton.getStyleClass().remove("active");
        requestsButton.getStyleClass().remove("active");
        clientsButton.getStyleClass().remove("active");
        vouchersButton.getStyleClass().remove("active");
        branchesButton.getStyleClass().remove("active");
        usersButton.getStyleClass().remove("active");
        reportsButton.getStyleClass().remove("active");


        // Add "active" class to the selected button
        switch (name) {
            case "dashboard": dashboardButton.getStyleClass().add("active"); break;
            case "requests":  requestsButton.getStyleClass().add("active"); break;
            case "clients":   clientsButton.getStyleClass().add("active"); break;
            case "vouchers":  vouchersButton.getStyleClass().add("active"); break;
            case "branches":  branchesButton.getStyleClass().add("active"); break;
            case "users":     usersButton.getStyleClass().add("active"); break;
            case "reports":   reportsButton.getStyleClass().add("active"); break;
        }
    }

    // Optional helper to access root if needed
    public VBox getRoot() {
        return root;
    }
}
