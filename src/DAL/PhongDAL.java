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
                        rs.getString("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getInt("DienTich"),
                        rs.getFloat("GiaPhong"),
                        rs.getString("MoTa"),
                        rs.getString("TinhTrang"),
                        rs.getString("KhuVucID")
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
            ps.setString(8, phong.getKhuVucID());

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
        String sql = "UPDATE Phong SET TenPhong=?, LoaiPhong=?, DienTich=?, GiaPhong=?, MoTa=?, TinhTrang=?, KhuVucID=? WHERE MaPhong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phong.getTenPhong());
            ps.setString(2, phong.getLoaiPhong());
            ps.setInt(3, phong.getDienTich());
            ps.setFloat(4, phong.getGiaPhong());
            ps.setString(5, phong.getMoTa());
            ps.setString(6, phong.getTinhTrang());
            ps.setString(7, phong.getKhuVucID());
            ps.setString(8, phong.getMaPhong());


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
                            rs.getString("MaPhong"),
                            rs.getString("TenPhong"),
                            rs.getString("LoaiPhong"),
                            rs.getInt("DienTich"),
                            rs.getFloat("GiaPhong"),
                            rs.getString("MoTa"),
                            rs.getString("TinhTrang"),
                            rs.getString("KhuVucID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa phòng theo ID
    public static boolean deletePhong(String maPhong) {
        if (maPhong == null || maPhong.isEmpty()) return false;  // Kiểm tra nếu id không hợp lệ

        String sql = "DELETE FROM Phong WHERE MaPhong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);  // Sử dụng setString để truyền id kiểu String
            return ps.executeUpdate() > 0;  // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean capNhatTinhTrangPhong(int phongID, String tinhTrang) {
        String sql = "UPDATE Phong SET TinhTrang = ? WHERE MaPhong = ?";
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
