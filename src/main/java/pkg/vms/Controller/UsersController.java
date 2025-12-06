package pkg.vms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Users;
import java.sql.*;

public class UsersController {

    // TABLE & COLUMNS
    @FXML private TableView<Users> usersTable;
    @FXML private TableColumn<Users, String> usernameColumn;
    @FXML private TableColumn<Users, String> firstNameColumn;
    @FXML private TableColumn<Users, String> lastNameColumn;
    @FXML private TableColumn<Users, String> emailColumn;
    @FXML private TableColumn<Users, String> roleColumn;
    @FXML private TableColumn<Users, String> statusColumn;
    @FXML private TableColumn<Users, String> titreColumn;

    // ADD/EDIT FORM
    @FXML private VBox addForm;
    @FXML private Label formTitle;
    @FXML private TextField addUsername, addFirstName, addLastName, addEmail, addRole, addStatus, addPassword, addDdl, addTitre;
    @FXML private Label formError;
    @FXML private TextField searchHeaderField;

    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private Users editingUser = null;

    @FXML
    public void initialize() {
        // SETUP TABLE COLUMNS
        usernameColumn.setCellValueFactory(cell -> cell.getValue().usernameProperty());
        firstNameColumn.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cell -> cell.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cell -> cell.getValue().emailProperty());
        roleColumn.setCellValueFactory(cell -> cell.getValue().roleProperty());
        statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());
        titreColumn.setCellValueFactory(cell -> cell.getValue().titreProperty());
        usersTable.setItems(userList);
        loadUsers();

        // HIDE FORM INITIALLY
        addForm.setVisible(false);
        addForm.setManaged(false);
    }

    private void loadUsers() {
        userList.clear();
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                Users user = new Users(
                        rs.getString("username"),
                        rs.getString("first_name_user"),
                        rs.getString("last_name_user"),
                        rs.getString("email_user"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getString("ddl"),
                        rs.getString("titre"),
                        rs.getString("status")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error loading users: " + e.getMessage());
        }
    }

    /** SEARCH USERS **/
    @FXML
    private void handleSearch() {
        String searchText = searchHeaderField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            usersTable.setItems(userList);
            return;
        }
        ObservableList<Users> filteredList = FXCollections.observableArrayList();
        for (Users user : userList) {
            if (user.getUsername().toLowerCase().contains(searchText)
                    || user.getFirstName().toLowerCase().contains(searchText)
                    || user.getLastName().toLowerCase().contains(searchText)
                    || user.getEmail().toLowerCase().contains(searchText)
                    || user.getRole().toLowerCase().contains(searchText)
                    || user.getStatus().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }
        usersTable.setItems(filteredList);
    }

    /** SHOW ADD FORM **/
    @FXML
    private void handleAddUser() {
        editingUser = null;
        formTitle.setText("Add New User");
        clearFormFields();
        formError.setText("");
        addForm.setVisible(true);
        addForm.setManaged(true);
    }

    /** SHOW EDIT FORM **/
    @FXML
    private void handleEditUser() {
        Users selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a user to edit.");
            return;
        }
        editingUser = selected;
        formTitle.setText("Edit User");
        addUsername.setText(selected.getUsername());
        addFirstName.setText(selected.getFirstName());
        addLastName.setText(selected.getLastName());
        addEmail.setText(selected.getEmail());
        addRole.setText(selected.getRole());
        addStatus.setText(selected.getStatus());
        addPassword.setText(selected.getPassword());
        addDdl.setText(selected.getDdl());
        addTitre.setText(selected.getTitre());
        formError.setText("");
        addForm.setVisible(true);
        addForm.setManaged(true);
    }

    /** DELETE USER **/
    @FXML
    private void handleDeleteUser() {
        Users selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a user to delete.");
            return;
        }
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username=?")) {
            ps.setString(1, selected.getUsername());
            ps.executeUpdate();
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error deleting user: " + e.getMessage());
        }
    }

    /** SAVE FORM **/
    @FXML
    private void handleFormSave() {
        String username = addUsername.getText().trim();
        String firstName = addFirstName.getText().trim();
        String lastName = addLastName.getText().trim();
        String email = addEmail.getText().trim();
        String role = addRole.getText().trim();
        String status = addStatus.getText().trim();
        String password = addPassword.getText().trim();
        String ddl = addDdl.getText().trim();
        String titre = addTitre.getText().trim();

        if (username.isEmpty()) {
            formError.setText("Username cannot be empty.");
            return;
        }

        try (Connection conn = DBconnection.getConnection()) {
            if (editingUser == null) {
                // INSERT NEW USER
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO users(username, first_name_user, last_name_user, email_user, role, password, ddl, titre, status) VALUES(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, email);
                ps.setString(5, role);
                ps.setString(6, password);
                ps.setString(7, ddl);
                ps.setString(8, titre);
                ps.setString(9, status);
                ps.executeUpdate();
            } else {
                // UPDATE EXISTING USER
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE users SET username=?, first_name_user=?, last_name_user=?, email_user=?, role=?, password=?, ddl=?, titre=?, status=? WHERE username=?");
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, email);
                ps.setString(5, role);
                ps.setString(6, password);
                ps.setString(7, ddl);
                ps.setString(8, titre);
                ps.setString(9, status);
                ps.setString(10, editingUser.getUsername());
                ps.executeUpdate();
            }
            loadUsers();
            handleAddCancel();
        } catch (SQLException e) {
            e.printStackTrace();
            formError.setText("Database error: " + e.getMessage());
        }
    }

    /** CANCEL ADD/EDIT **/
    @FXML
    private void handleAddCancel() {
        addForm.setVisible(false);
        addForm.setManaged(false);
    }

    private void clearFormFields() {
        addUsername.setText("");
        addFirstName.setText("");
        addLastName.setText("");
        addEmail.setText("");
        addRole.setText("");
        addStatus.setText("");
        addPassword.setText("");
        addDdl.setText("");
        addTitre.setText("");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}