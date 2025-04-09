package DAL;

import DTO.PhongDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAL {

    // Lấy danh sách tất cả phòng
    // Phương thức lấy tất cả phòng
    public static List<PhongDTO> getAllPhong() {
        List<PhongDTO> dsPhong = new ArrayList<>();
        String sql = "SELECT * FROM Phong";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhongDTO phong = new PhongDTO(
                        rs.getInt("ID"),
                        rs.getString("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getInt("DienTich"),
                        rs.getFloat("GiaPhong"),
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
        if (phong == null || phong.getMaPhong() == null || phong.getTenPhong() == null) return false;

        String sql = "INSERT INTO Phong (MaPhong, TenPhong, LoaiPhong, DienTich, GiaPhong, MoTa, TinhTrang, KhuVucID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phong.getMaPhong());
            ps.setString(2, phong.getTenPhong());
            ps.setString(3, phong.getLoaiPhong());
            ps.setInt(4, phong.getDienTich());
            ps.setFloat(5, phong.getGiaPhong());
            ps.setString(6, phong.getMoTa());
            ps.setString(7, phong.getTinhTrang());
            ps.setInt(8, phong.getKhuVucID());

            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Lỗi: Mã phòng đã tồn tại!");
            return false;  // Trả về false nếu có lỗi trùng mã
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Cập nhật thông tin phòng (dựa trên ID)
    public static boolean updatePhong(PhongDTO phong) {
        if (phong == null || phong.getId() <= 0) return false;

        String sql = "UPDATE Phong SET MaPhong=?, TenPhong=?, LoaiPhong=?, DienTich=?, GiaPhong=?, MoTa=?, TinhTrang=?, KhuVucID=? WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phong.getMaPhong());
            ps.setString(2, phong.getTenPhong());
            ps.setString(3, phong.getLoaiPhong());
            ps.setInt(4, phong.getDienTich());
            ps.setFloat(5, phong.getGiaPhong());
            ps.setString(6, phong.getMoTa());
            ps.setString(7, phong.getTinhTrang());
            ps.setInt(8, phong.getKhuVucID());
            ps.setInt(9, phong.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm phòng theo mã phòng
    public static PhongDTO searchPhong(String maPhong) {
        if (maPhong == null) return null;

        String sql = "SELECT * FROM Phong WHERE MaPhong = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PhongDTO(
                            rs.getInt("ID"),
                            rs.getString("MaPhong"),
                            rs.getString("TenPhong"),
                            rs.getString("LoaiPhong"),
                            rs.getInt("DienTich"),
                            rs.getFloat("GiaPhong"),
                            rs.getString("MoTa"),
                            rs.getString("TinhTrang"),
                            rs.getInt("KhuVucID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa phòng theo ID
    public static boolean deletePhong(int id) {
        if (id <= 0) return false;

        String sql = "DELETE FROM Phong WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTinhTrangPhong(int phongID, String tinhTrang) {
        String sql = "UPDATE Phong SET TinhTrang = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tinhTrang);
            ps.setInt(2, phongID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
