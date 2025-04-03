package DAL;

import DTO.KhachThueDTO;
import DTO.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachThueDAL {
    private static Connection conn = DatabaseConnection.getConnection();

    // Thêm khách thuê mới
    public boolean insertKhachThue(KhachThueDTO khachThue) {
        String sql = "INSERT INTO KhachThue (HoTen, SoDienThoai, CCCD, PhongID, NgayThue, NgayTra) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, khachThue.getHoTen());
            statement.setString(2, khachThue.getSoDienThoai());
            statement.setString(3, khachThue.getCccd());
            statement.setInt(4, khachThue.getPhongID());
            statement.setString(5, khachThue.getNgayThue());
            statement.setString(6, khachThue.getNgayTra());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách khách thuê
    public List<KhachThueDTO> getAllKhachThue() {
        List<KhachThueDTO> khachThueList = new ArrayList<>();
        String sql = "SELECT * FROM KhachThue";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                KhachThueDTO khachThue = new KhachThueDTO(
                        resultSet.getInt("ID"),
                        resultSet.getString("HoTen"),
                        resultSet.getString("SoDienThoai"),
                        resultSet.getString("CCCD"),
                        resultSet.getInt("PhongID"),
                        resultSet.getString("NgayThue"),
                        resultSet.getString("NgayTra")
                );
                khachThueList.add(khachThue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khachThueList;
    }

    // Cập nhật khách thuê
    public boolean updateKhachThue(KhachThueDTO khachThue) {
        String sql = "UPDATE KhachThue SET HoTen = ?, SoDienThoai = ?, CCCD = ?, PhongID = ?, NgayThue = ?, NgayTra = ? WHERE ID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, khachThue.getHoTen());
            statement.setString(2, khachThue.getSoDienThoai());
            statement.setString(3, khachThue.getCccd());
            statement.setInt(4, khachThue.getPhongID());
            statement.setString(5, khachThue.getNgayThue());
            statement.setString(6, khachThue.getNgayTra());
            statement.setInt(7, khachThue.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa khách thuê
    public boolean deleteKhachThue(int id) {
        String sql = "DELETE FROM KhachThue WHERE ID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
