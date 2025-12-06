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

    @FXML private Button dashboardButton;
    @FXML private Button requestsButton;
    @FXML private Button clientsButton;
    @FXML private Button vouchersButton;
    @FXML private Button branchesButton;
    @FXML private Button usersButton;
    @FXML private Button reportsButton;

    /**
     * Callback used by the main controller (DashboardController)
     * so it can load the matching view when a sidebar item is clicked.
     */
    private Consumer<String> navigationHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Default highlight
        setActive("dashboard");
    }

    // Sidebar button handlers =========================

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

    // ================================================

    /**
     * Allows DashboardController to receive navigation events.
     */
    public void setNavigationHandler(Consumer<String> handler) {
        this.navigationHandler = handler;
    }

    /**
     * Called internally when a sidebar button is pressed.
     */
    private void navigate(String target) {
        if (navigationHandler != null) {
            navigationHandler.accept(target);   // send event to DashboardController
        } else {
            System.out.println("[Sidebar] navigate: " + target);
        }
        setActive(target);
    }

    /**
     * Highlights whichever button is active.
     */
    public void setActive(String name) {

        // remove from all
        dashboardButton.getStyleClass().remove("active");
        requestsButton.getStyleClass().remove("active");
        clientsButton.getStyleClass().remove("active");
        vouchersButton.getStyleClass().remove("active");
        branchesButton.getStyleClass().remove("active");
        usersButton.getStyleClass().remove("active");
        reportsButton.getStyleClass().remove("active");

        // add to one
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

    public VBox getRoot() {
        return root;
    }
}
