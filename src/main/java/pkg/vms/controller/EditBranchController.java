package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pkg.vms.DAO.BranchDAO;
import pkg.vms.DAO.CompanyDAO;
import pkg.vms.model.Branch;
import pkg.vms.model.Company;

public class EditBranchController {

    @FXML private TextField nameField;
    @FXML private ComboBox<Company> companyCombo;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField managerField;

    private BranchDAO branchDAO = new BranchDAO();
    private CompanyDAO companyDAO = new CompanyDAO();
    private Branch branch;

    public void setBranch(Branch branch) {
        this.branch = branch;

        nameField.setText(branch.getLocation());
        addressField.setText(branch.getAddress());
        phoneField.setText(branch.getPhone());
        managerField.setText(branch.getResponsibleUser());

        companyCombo.getItems().addAll(companyDAO.getAll());

        // auto-select company
        for (Company c : companyCombo.getItems()) {
            if (c.getId() == branch.getRefCompany()) {
                companyCombo.setValue(c);
            }
        }
    }

    @FXML
    private void handleUpdate() {
        branch.setLocation(nameField.getText());
        branch.setAddress(addressField.getText());
        branch.setPhone(phoneField.getText());
        branch.setResponsibleUser(managerField.getText());
        branch.setRefCompany(companyCombo.getValue().getId());

        branchDAO.update(branch);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
