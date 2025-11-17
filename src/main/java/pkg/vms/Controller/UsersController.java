package pkg.vms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Users;

import java.sql.*;

public class UsersController {

    @FXML
    private TableView<Users> usersTable;

    @FXML private TableColumn<Users, String> usernameColumn;
    @FXML private TableColumn<Users, String> firstNameColumn;
    @FXML private TableColumn<Users, String> lastNameColumn;
    @FXML private TableColumn<Users, String> emailColumn;
    @FXML private TableColumn<Users, String> roleColumn;
    @FXML private TableColumn<Users, String> statusColumn;

    @FXML private Button addButton, editButton, deleteButton;

    private ObservableList<Users> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(cell -> cell.getValue().usernameProperty());
        firstNameColumn.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cell -> cell.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cell -> cell.getValue().emailProperty());
        roleColumn.setCellValueFactory(cell -> cell.getValue().roleProperty());
        statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());

        usersTable.setItems(userList);
        loadUsers();
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
        }
    }

    @FXML
    private void handleAddUser() {
        // You can use a dialog to ask for details
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Enter username:");
        dialog.showAndWait().ifPresent(username -> {
            try (Connection conn = DBconnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO users(username, first_name_user, last_name_user, email_user, role, password, ddl, titre, status) VALUES(?,?,?,?,?,?,?,?,?)")) {

                ps.setString(1, username);
                ps.setString(2, "FirstName");
                ps.setString(3, "LastName");
                ps.setString(4, "email@example.com");
                ps.setString(5, "role");
                ps.setString(6, "password");
                ps.setString(7, "ddl");
                ps.setString(8, "titre");
                ps.setString(9, "active");
                ps.executeUpdate();
                loadUsers();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleEditUser() {
        Users selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog(selected.getStatus());
        dialog.setTitle("Edit User Status");
        dialog.setHeaderText("Enter new status:");
        dialog.showAndWait().ifPresent(status -> {
            try (Connection conn = DBconnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE users SET status=? WHERE username=?")) {
                ps.setString(1, status);
                ps.setString(2, selected.getUsername());
                ps.executeUpdate();
                loadUsers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleDeleteUser() {
        Users selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username=?")) {
            ps.setString(1, selected.getUsername());
            ps.executeUpdate();
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
