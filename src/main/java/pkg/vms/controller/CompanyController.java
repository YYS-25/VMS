package pkg.vms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pkg.vms.DAO.CompanyDAO;
import pkg.vms.model.Company;

public class CompanyController {

    @FXML private TableView<Company> companyTable;
    @FXML private TableColumn<Company, Integer> idColumn;
    @FXML private TableColumn<Company, String> nameColumn;
    @FXML private TableColumn<Company, String> emailColumn;
    @FXML private TableColumn<Company, String> industryColumn;

    @FXML private Label detailId, detailName, detailEmail, detailIndustry, detailsTitle;

    private final CompanyDAO dao = new CompanyDAO();
    private final ObservableList<Company> list = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().companyIdProperty().asObject());
        nameColumn.setCellValueFactory(c -> c.getValue().nameCompanyProperty());
        emailColumn.setCellValueFactory(c -> c.getValue().emailCompanyProperty());
        industryColumn.setCellValueFactory(c -> c.getValue().industryTypeProperty());

        loadCompanies();

        companyTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> showDetails(newV)
        );
    }

    private void loadCompanies() {
        list.setAll(dao.getAll());
        companyTable.setItems(list);
    }

    private void showDetails(Company c) {
        if (c == null) {
            detailsTitle.setText("Company Details (Selected: None)");
            detailId.setText("ID: -");
            detailName.setText("Name: -");
            detailEmail.setText("Email: -");
            detailIndustry.setText("Industry: -");
            return;
        }

        detailsTitle.setText("Company Details (Selected: " + c.getNameCompany() + ")");
        detailId.setText("ID: " + c.getCompanyId());
        detailName.setText("Name: " + c.getNameCompany());
        detailEmail.setText("Email: " + c.getEmailCompany());
        detailIndustry.setText("Industry: " + c.getIndustryType());
    }

    // -------------------------------
    // OPEN ADD COMPANY FORM
    // -------------------------------
    @FXML
    private void openAddCompany() {
        openCompanyForm(null);
    }

    // -------------------------------
    // OPEN EDIT COMPANY FORM
    // -------------------------------
    @FXML
    private void openEditCompany() {
        Company selected = companyTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        openCompanyForm(selected);
    }

    // -------------------------------
    // REUSABLE MODAL FORM
    // -------------------------------
    private void openCompanyForm(Company company) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pkg/vms/view/company_form.fxml"));
            Parent root = loader.load();

            CompanyFormController controller = loader.getController();
            controller.setController(this);
            controller.setCompany(company); // null = add, otherwise edit

            Stage stage = new Stage();
            stage.setTitle(company == null ? "Add Company" : "Edit Company");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // DELETE
    // -------------------------------
    @FXML
    private void deleteCompany() {
        Company selected = companyTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        dao.delete(selected.getCompanyId());
        loadCompanies();
    }

    // Called after add/edit
    public void refresh() {
        loadCompanies();
    }
}
