package pkg.vms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Clients;
import pkg.vms.model.Requests;
import pkg.vms.model.Vouchers;

import java.sql.*;

public class ClientController {

    @FXML private TableView<Clients> clientTable;
    @FXML private TableColumn<Clients, Integer> idColumn;
    @FXML private TableColumn<Clients, String> nameColumn;

    @FXML private TableView<Requests> requestsTable;
    @FXML private TableView<Vouchers> vouchersTable;

    @FXML private Button addButton, editButton, deleteButton;

    private ObservableList<Clients> clientList = FXCollections.observableArrayList();
    private ObservableList<Requests> requestList = FXCollections.observableArrayList();
    private ObservableList<Vouchers> voucherList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cell -> cell.getValue().refClientProperty().asObject());
        nameColumn.setCellValueFactory(cell -> cell.getValue().nomClientProperty());

        clientTable.setItems(clientList);
        requestsTable.setItems(requestList);
        vouchersTable.setItems(voucherList);

        loadClients();

        clientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                loadClientRequests(newSel);
                loadClientVouchers(newSel);
            }
        });
    }

    private void loadClients() {
        clientList.clear();
        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {

            while (rs.next()) {
                Clients c = new Clients(
                        rs.getInt("ref_client"),
                        rs.getString("nom_client"),
                        rs.getString("email_client"),
                        rs.getString("address_client"),
                        rs.getString("phone_client")
                );
                clientList.add(c);
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadClientRequests(Clients client) {
        requestList.clear();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM requests WHERE ref_client=?")) {
            ps.setInt(1, client.getRef_client());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Requests r = new Requests(
                        rs.getInt("ref_request"),
                        rs.getDate("creation_date"),
                        rs.getInt("num_voucher"),
                        rs.getString("status"),
                        rs.getString("payment"),
                        rs.getDate("date_payment"),
                        rs.getInt("ref_payment"),
                        rs.getDate("date_approval"),
                        rs.getInt("duration_voucher"),
                        rs.getInt("ref_client"),
                        rs.getString("processed_by"),
                        rs.getString("approved_by"),
                        rs.getString("validated_by")
                );
                requestList.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void loadClientVouchers(Clients client) {
        voucherList.clear();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT v.* FROM vouchers v JOIN requests r ON v.ref_request=r.ref_request WHERE r.ref_client=?")) {
            ps.setInt(1, client.getRef_client());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vouchers v = new Vouchers(
                        rs.getInt("ref_voucher"),
                        rs.getInt("val_voucher"),
                        rs.getDate("init_date"),
                        rs.getDate("expiry_date"),
                        rs.getString("status_voucher"),
                        rs.getDate("date_redeemed"),
                        rs.getString("bearer"),
                        rs.getInt("ref_request"),
                        rs.getString("redeemed_by"),
                        rs.getString("redeemed_branch")
                );
                voucherList.add(v);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleAddClient() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Client");
        dialog.setHeaderText("Enter client name:");
        dialog.showAndWait().ifPresent(name -> {
            try (Connection conn = DBconnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO clients(nom_client) VALUES(?)")) {
                ps.setString(1, name);
                ps.executeUpdate();
                loadClients();
            } catch (SQLException e) { e.printStackTrace(); }
        });
    }

    @FXML
    private void handleEditClient() {
        Clients selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        TextInputDialog dialog = new TextInputDialog(selected.getNom_client());
        dialog.setTitle("Edit Client");
        dialog.setHeaderText("Edit client name:");
        dialog.showAndWait().ifPresent(name -> {
            try (Connection conn = DBconnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE clients SET nom_client=? WHERE ref_client=?")) {
                ps.setString(1, name);
                ps.setInt(2, selected.getRef_client());
                ps.executeUpdate();
                loadClients();
            } catch (SQLException e) { e.printStackTrace(); }
        });
    }

    @FXML
    private void handleDeleteClient() {
        Clients selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM clients WHERE ref_client=?")) {
            ps.setInt(1, selected.getRef_client());
            ps.executeUpdate();
            loadClients();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
