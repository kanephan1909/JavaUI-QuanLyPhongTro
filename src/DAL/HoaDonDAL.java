package DAL;

import DTO.HoaDonDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAL {

    // Lấy tất cả hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        List<HoaDonDTO> hoaDonList = new ArrayList<>();
        String query = "SELECT * FROM HoaDon";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO(
                        rs.getInt("id"),
                        rs.getInt("maHopDong"),
                        rs.getString("thangNam"),
                        rs.getFloat("tienPhong"),
                        rs.getFloat("tienDien"),
                        rs.getFloat("tienNuoc"),
                        rs.getFloat("tongTien"),
                        rs.getString("trangThai")
                );
                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }

    // Lấy hóa đơn theo ID
    public HoaDonDTO getHoaDonById(int id) {
        String query = "SELECT * FROM HoaDon WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HoaDonDTO(
                        rs.getInt("id"),
                        rs.getInt("maHopDong"),
                        rs.getString("thangNam"),
                        rs.getFloat("tienPhong"),
                        rs.getFloat("tienDien"),
                        rs.getFloat("tienNuoc"),
                        rs.getFloat("tongTien"),
                        rs.getString("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm hóa đơn
    public boolean addHoaDon(HoaDonDTO hoaDon) {
        String query = "INSERT INTO HoaDon (maHopDong, thangNam, tienPhong, tienDien, tienNuoc, tongTien, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (!checkHopDongExist(hoaDon.getMaHopDong())) {
            JOptionPane.showMessageDialog(null, "Mã hợp đồng không tồn tại trong cơ sở dữ liệu.");
            return false;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hoaDon.getMaHopDong());
            stmt.setString(2, hoaDon.getThangNam());
            stmt.setDouble(3, hoaDon.getTienPhong());
            stmt.setDouble(4, hoaDon.getTienDien());
            stmt.setDouble(5, hoaDon.getTienNuoc());
            stmt.setDouble(6, hoaDon.getTongTien());
            stmt.setString(7, hoaDon.getTrangThai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }


    // Cập nhật hóa đơn
    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        String query = "UPDATE HoaDon SET maHopDong = ?, thangNam = ?, tienPhong = ?, tienDien = ?, tienNuoc = ?, tongTien = ?, trangThai = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, hoaDon.getMaHopDong());
            stmt.setString(2, hoaDon.getThangNam());
            stmt.setFloat(3, hoaDon.getTienPhong());
            stmt.setFloat(4, hoaDon.getTienDien());
            stmt.setFloat(5, hoaDon.getTienNuoc());
            stmt.setFloat(6, hoaDon.getTongTien());
            stmt.setString(7, hoaDon.getTrangThai());
            stmt.setInt(8, hoaDon.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa hóa đơn
    public boolean deleteHoaDon(int id) {
        String query = "DELETE FROM HoaDon WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra mã hợp đồng có tồn tại chưa
    private boolean checkHopDongExist(int maHopDong) {
        String query = "SELECT COUNT(*) FROM hopdong WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, maHopDong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
