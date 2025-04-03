package DAL;

import DTO.PhongDTO;
import DTO.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAL {
    private static Connection conn = DatabaseConnection.getConnection();

    // Lấy danh sách tất cả phòng
    public static List<PhongDTO> getAllPhong() {
        List<PhongDTO> dsPhong = new ArrayList<>();
        String sql = "SELECT * FROM Phong";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhongDTO phong = new PhongDTO(
                        rs.getInt("ID"),
                        rs.getString("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getInt("DienTich"),
                        rs.getBigDecimal("GiaPhong"),
                        rs.getString("MoTa"),
                        rs.getString("TinhTrang"),
                        rs.getInt("KhuVucID")
                );
                dsPhong.add(phong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }

    // Thêm phòng mới
    public static boolean addPhong(PhongDTO phong) {
        String sql = "INSERT INTO Phong (MaPhong, TenPhong, LoaiPhong, DienTich, GiaPhong, MoTa, TinhTrang, KhuVucID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phong.getMaPhong());
            ps.setString(2, phong.getTenPhong());
            ps.setString(3, phong.getLoaiPhong());
            ps.setInt(4, phong.getDienTich());
            ps.setBigDecimal(5, phong.getGiaPhong());
            ps.setString(6, phong.getMoTa());
            ps.setString(7, phong.getTinhTrang());
            ps.setInt(8, phong.getKhuVucID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin phòng
    public static boolean updatePhong(PhongDTO phong) {
        String sql = "UPDATE Phong SET TenPhong=?, LoaiPhong=?, DienTich=?, GiaPhong=?, MoTa=?, TinhTrang=?, KhuVucID=? WHERE MaPhong=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phong.getTenPhong());
            ps.setString(2, phong.getLoaiPhong());
            ps.setInt(3, phong.getDienTich());
            ps.setBigDecimal(4, phong.getGiaPhong());
            ps.setString(5, phong.getMoTa());
            ps.setString(6, phong.getTinhTrang());
            ps.setInt(7, phong.getKhuVucID());
            ps.setString(8, phong.getMaPhong());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa phòng theo ID
    public static boolean deletePhong(int id) {
        String sql = "DELETE FROM Phong WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
