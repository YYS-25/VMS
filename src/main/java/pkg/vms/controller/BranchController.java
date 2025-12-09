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
import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Branch;

import java.sql.*;

public class BranchController {

    @FXML private TableView<Branch> branchTable;
    @FXML private TableColumn<Branch, Integer> idColumn;
    @FXML private TableColumn<Branch, String> locationColumn;
    @FXML private TableColumn<Branch, String> responsibleColumn;

    @FXML private Label detailBranchId;
    @FXML private Label detailBranchName;
    @FXML private Label detailCompany;
    @FXML private Label detailCity;
    @FXML private Label detailAddress;
    @FXML private Label detailContact;
    @FXML private Label detailEmail;
    @FXML private Label detailManager;
    @FXML private Label detailIndustry;
    @FXML private Label detailsTitle;



    @FXML private Button addButton, editButton, deleteButton;

    private ObservableList<Branch> branchList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cell -> cell.getValue().branchIdProperty().asObject());
        locationColumn.setCellValueFactory(cell -> cell.getValue().locationProperty());
        responsibleColumn.setCellValueFactory(cell -> cell.getValue().responsibleUserProperty());

        branchTable.setItems(branchList);
        loadBranches();
        loadCompaniesIntoFilter();

        branchTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                detailsTitle.setText("Branch Details (Selected: " + selected.getBranchId() + ")");

                detailBranchId.setText("Branch ID: " + selected.getBranchId());
                detailBranchName.setText("Branch Name: " + selected.getLocation());
                detailCompany.setText("Company ID (FK): " + selected.getRefCompany());
                detailCity.setText("City: " + selected.getLocation());
                detailAddress.setText("Address: " + selected.getAddress());
                detailContact.setText("Contact: " + selected.getPhone());
                detailEmail.setText("Email: -");
                detailManager.setText("Manager: " + selected.getResponsibleUser());
                detailIndustry.setText("Industry: -");
            }
        });

    }

    private void loadBranches() {
        branchList.clear();
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM branch")) {

            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("location"),
                        rs.getString("responsible_user")
                );
                branchList.add(branch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddBranch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pkg/vms/view/add-branch.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Branch");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Refresh the table after closing window
            loadBranches();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleEditBranch() {
        Branch selected = branchTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog(selected.getLocation());
        dialog.setTitle("Edit Branch Location");
        dialog.setHeaderText("Enter new location:");
        dialog.showAndWait().ifPresent(location -> {
            try (Connection conn = DBconnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE branch SET location=? WHERE branch_id=?")) {
                ps.setString(1, location);
                ps.setInt(2, selected.getBranchId());
                ps.executeUpdate();
                loadBranches();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleDeleteBranch() {
        Branch selected = branchTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM branch WHERE branch_id=?")) {
            ps.setInt(1, selected.getBranchId());
            ps.executeUpdate();
            loadBranches();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCompanyPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pkg/vms/view/company.fxml"));
            Parent root = loader.load();

            // Show the new page (replace entire window content)
            branchTable.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handles search functionality
    @FXML
    private TextField searchField;

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim();

        branchList.clear();

        String sql = keyword.isEmpty()
                ? "SELECT * FROM branch"
                : "SELECT * FROM branch WHERE location ILIKE ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("location"),
                        rs.getString("responsible_user")
                );
                branchList.add(branch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //    Handles filter functionality
    @FXML
    private ComboBox<String> filterCompanyCombo;

    @FXML
    private void handleFilter() {
        String selected = filterCompanyCombo.getValue();

        branchList.clear();

        String sql;

        if (selected == null || selected.equals("All Companies")) {
            sql = "SELECT * FROM branch";
        } else {
            int companyId = Integer.parseInt(selected.split(" - ")[0]);
            sql = "SELECT * FROM branch WHERE ref_company = ?";
        }

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!(selected == null || selected.equals("All Companies"))) {
                ps.setInt(1, Integer.parseInt(selected.split(" - ")[0]));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("location"),
                        rs.getString("responsible_user")
                );
                branchList.add(branch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCompaniesIntoFilter() {
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT company_id, name_company FROM company ORDER BY name_company")) {

            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("All Companies");

            while (rs.next()) {
                int id = rs.getInt("company_id");
                String name = rs.getString("name_company");
                list.add(id + " - " + name);  // Display "3 - Toyota"
            }

            filterCompanyCombo.setItems(list);
            filterCompanyCombo.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
