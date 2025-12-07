package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pkg.vms.DAO.BranchDAO;
import pkg.vms.DAO.CompanyDAO;
import pkg.vms.model.Company;

public class AddBranchController {

    @FXML private TextField nameField;
    @FXML private ComboBox<Company> companyCombo;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField managerField;

    private BranchDAO branchDAO = new BranchDAO();
    private CompanyDAO companyDAO = new CompanyDAO();

    @FXML
    public void initialize() {
        companyCombo.getItems().addAll(companyDAO.getAll());
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String manager = managerField.getText();
        Company selectedCompany = companyCombo.getValue();

        if (selectedCompany != null && !name.isEmpty()) {
            branchDAO.insertFull(name, manager, selectedCompany.getId(), address, phone);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }
    }
}
