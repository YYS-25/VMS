package pkg.vms.DAO;

import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Branch;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    public List<Branch> getAll() {
        List<Branch> list = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM branch")) {

            while (rs.next()) {
                list.add(new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("location"),
                        rs.getString("responsible_user")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(String location) {
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO branch(location, responsible_user) VALUES (?, ?)")) {

            ps.setString(1, location);
            ps.setString(2, "ResponsibleUser");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String newLocation) {
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE branch SET location=? WHERE branch_id=?")) {

            ps.setString(1, newLocation);
            ps.setInt(2, id);
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
}
