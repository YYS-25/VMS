package pkg.vms.DAO;

import pkg.vms.DBconnection.DBconnection;
import pkg.vms.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {

    public List<Company> getAll() {
        List<Company> list = new ArrayList<>();

        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM company")) {

            while (rs.next()) {
                list.add(new Company(
                        rs.getInt("company_id"),
                        rs.getString("name_company"),
                        rs.getString("email_company"),
                        rs.getString("industry_type")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(String name, String email, String industry) {
        String sql = "INSERT INTO company(name_company, email_company, industry_type) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, industry);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Company c) {
        String sql = "UPDATE company SET name_company=?, email_company=?, industry_type=? WHERE company_id=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getIndustry());
            ps.setInt(4, c.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM company WHERE company_id=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Object companyId, String text, String text1, String text2) {
        String sql = "UPDATE company SET name_company=?, email_company=?, industry_type=? WHERE company_id=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, text);
            ps.setString(2, text1);
            ps.setString(3, text2);
            ps.setObject(4, companyId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
