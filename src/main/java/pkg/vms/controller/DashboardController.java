package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import pkg.vms.controller.layout.SidebarController;

import java.io.IOException;
import java.util.Locale;

public class DashboardController {

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;

    @FXML private ImageView iconRequests;
    @FXML private ImageView iconClients;
    @FXML private ImageView iconVouchers;
    @FXML private ImageView iconBranches;
    @FXML private ImageView iconUsers;
    @FXML private ImageView iconReports;

    @FXML private BorderPane rootPane;  // The main BorderPane from Dashboard.fxml

    @FXML
    public void initialize() {

        // Load Sidebar and connect navigation system
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/pkg/vms/fxml/layout/Sidebar.fxml")
            );

            Parent sidebar = loader.load();
            SidebarController sidebarController = loader.getController();

            // Connect sidebar navigation to dashboard navigation
            sidebarController.setNavigationHandler(this::navigateTo);

            // Insert sidebar inside the left of the dashboard
            rootPane.setLeft(sidebar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CLICK ON CLIENT ICON IN DASHBOARD
    @FXML
    private void handleClientsClick() {
        navigateTo("clients");
    }

    // CLICK ON USERS ICON IN DASHBOARD
    @FXML
    private void handleUsersClick() {
        navigateTo("users");
    }

    // CLICK ON BRANCH ICON IN DASHBOARD
    @FXML
    private void handleBranchClick() {
        navigateTo("branch");
    }

    /**
     * Central navigation for dashboard (sidebar + dashboard icons)
     */
    private void navigateTo(String target) {
        if (rootPane == null) {
            System.out.println("navigateTo called but rootPane is null");
            return;
        }

        String t = target == null ? "" : target.toLowerCase(Locale.ROOT).trim();
        System.out.println("Navigating to: " + target + " (normalized: " + t + ")");

        try {
            Parent view = null;

            switch (t) {

                case "users":
                    view = FXMLLoader.load(
                            getClass().getResource("/pkg/vms/fxml/Users.fxml")
                    );
                    break;

                case "clients":
                    view = FXMLLoader.load(
                            getClass().getResource("/pkg/vms/fxml/clients.fxml")
                    );
                    break;

                case "branch":
                case "branches": // accept both names from sidebar
                    view = FXMLLoader.load(
                            getClass().getResource("/pkg/vms/fxml/Branch.fxml")
                    );
                    break;

                case "dashboard":
                    view = FXMLLoader.load(
                            getClass().getResource("/pkg/vms/fxml/Dashboard.fxml")
                    );
                    break;

                default:
                    System.out.println("Unknown navigation target: " + target);
                    break;
            }

            if (view != null) {
                rootPane.setCenter(view);   // Swap the center content
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserInfo(String username, String role) {
        usernameLabel.setText("Logged in as: " + username);
        roleLabel.setText("Role: " + role);
    }
}
