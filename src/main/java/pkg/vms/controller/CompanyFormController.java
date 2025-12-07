package pkg.vms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pkg.vms.DAO.CompanyDAO;
import pkg.vms.model.Company;

public class CompanyFormController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField industryField;

    private Company company;
    private CompanyDAO dao = new CompanyDAO();
    private CompanyController parent;

    public void setController(CompanyController controller) {
        this.parent = controller;
    }

    public void setCompany(Company c) {
        this.company = c;

        if (c != null) {
            nameField.setText(c.getNameCompany());
            emailField.setText(c.getEmailCompany());
            industryField.setText(c.getIndustryType());
        }
    }

    @FXML
    private void saveCompany() {
        if (company == null) {
            dao.insert(
                    nameField.getText(),
                    emailField.getText(),
                    industryField.getText()
            );
        } else {
            dao.update(
                    company.getCompanyId(),
                    nameField.getText(),
                    emailField.getText(),
                    industryField.getText()
            );
        }

        parent.refresh();
        close();
    }

    @FXML
    private void close() {
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
