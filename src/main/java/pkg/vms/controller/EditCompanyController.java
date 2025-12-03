package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pkg.vms.DAO.CompanyDAO;
import pkg.vms.model.Company;

public class EditCompanyController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField industryField;

    private final CompanyDAO companyDAO = new CompanyDAO();
    private Company company;

    public void setCompany(Company company) {
        this.company = company;
        nameField.setText(company.getName());
        emailField.setText(company.getEmail());
        industryField.setText(company.getIndustry());
    }

    @FXML
    private void handleSave() {
        companyDAO.update(new Company(
                company.getId(),
                nameField.getText(),
                emailField.getText(),
                industryField.getText()
        ));

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
