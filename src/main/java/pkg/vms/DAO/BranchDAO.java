package pkg.vms.DAO;

import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Branch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    public List<Branch> getAll() {
        List<Branch> list = new ArrayList<>();

        String sql = "SELECT branch_id, location, responsible_user, ref_company, address_branch, phone_branch FROM branch";

        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("location"),
                        rs.getString("responsible_user"),
                        rs.getInt("ref_company"),
                        rs.getString("address_branch"),
                        rs.getString("phone_branch")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(String location, int companyId,
                       String address, String phone) {

        String sql = "INSERT INTO branch(location, responsible_user, ref_company, address_branch, phone_branch) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location);
            ps.setString(2, "ResponsibleUser"); // hardcoded for now
            ps.setInt(3, companyId);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Branch b) {
        String sql = "UPDATE branch SET location=?, responsible_user=?, ref_company=?, " +
                "address_branch=?, phone_branch=? WHERE branch_id=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getLocation());
            ps.setString(2, b.getResponsibleUser());
            ps.setInt(3, b.getRefCompany());
            ps.setString(4, b.getAddress());
            ps.setString(5, b.getPhone());
            ps.setInt(6, b.getBranchId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM branch WHERE branch_id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String name) {

    }

    public void insertFull(String location, String responsibleUser,
                           int companyId, String address, String phone) {

        String sql = "INSERT INTO branch(location, responsible_user, ref_company, " +
                "address_branch, phone_branch) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location);
            ps.setString(2, responsibleUser);
            ps.setInt(3, companyId);
            ps.setString(4, address);
            ps.setString(5, phone);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
