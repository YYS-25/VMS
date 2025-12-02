package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pkg.vms.DAO.BranchDAO;

public class AddBranchController {

    @FXML private TextField branchNameField;

    private BranchDAO branchDAO = new BranchDAO();

    @FXML
    private void initialize() {
        // nothing special yet
    }

    @FXML
    private void handleSave() {
        String name = branchNameField.getText().trim();

        if (!name.isEmpty()) {
            branchDAO.insert(name);

            // Close window
            Stage stage = (Stage) branchNameField.getScene().getWindow();
            stage.close();
        }
    }
}
