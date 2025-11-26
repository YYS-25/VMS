package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardController {

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;

    @FXML private ImageView iconRequests;
    @FXML private ImageView iconClients;
    @FXML private ImageView iconVouchers;
    @FXML private ImageView iconBranches;
    @FXML private ImageView iconUsers;
    @FXML private ImageView iconReports;

    public void initialize() {

        // Load icons (replace these paths with your own)
//        iconRequests.setImage(new Image("/icons/requests.png"));
//        iconClients.setImage(new Image("/icons/clients.png"));
//        iconVouchers.setImage(new Image("/icons/vouchers.png"));
//        iconBranches.setImage(new Image("/icons/branches.png"));
//        iconUsers.setImage(new Image("/icons/users.png"));
//        iconReports.setImage(new Image("/icons/reports.png"));

        // Example logged-in user (later populate dynamically)
        usernameLabel.setText("Logged in as: Bobby");
        roleLabel.setText("Role: Superuser");
    }
}
