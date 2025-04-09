package DAL;

import DTO.KhuVucDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuVucDAL {

    // Lấy danh sách tất cả khu vực
    public static List<KhuVucDTO> getAllKhuVuc() {
        List<KhuVucDTO> dsKhuVuc = new ArrayList<>();
        String sql = "SELECT * FROM KhuVuc";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuVucDTO khuVuc = new KhuVucDTO(
                        rs.getInt("ID"),
                        rs.getString("TenKhuVuc"),
                        rs.getString("DiaChi")
                );
                dsKhuVuc.add(khuVuc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhuVuc;
    }

    // Thêm khu vực mới
    public static boolean addKhuVuc(KhuVucDTO khuVuc) {
        if (khuVuc == null || khuVuc.getTenKhuVuc() == null || khuVuc.getDiaChi() == null) return false;

        String sql = "INSERT INTO KhuVuc (TenKhuVuc, DiaChi) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khuVuc.getTenKhuVuc());
            ps.setString(2, khuVuc.getDiaChi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin khu vực (dựa trên ID)
    public static boolean updateKhuVuc(KhuVucDTO khuVuc) {
        if (khuVuc == null || khuVuc.getId() <= 0) return false;

        String sql = "UPDATE KhuVuc SET TenKhuVuc=?, DiaChi=? WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khuVuc.getTenKhuVuc());
            ps.setString(2, khuVuc.getDiaChi());
            ps.setInt(3, khuVuc.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm khu vực theo mã khu vực
    public static KhuVucDTO searchKhuVuc(String tenKhuVuc) {
        if (tenKhuVuc == null) return null;

        String sql = "SELECT * FROM KhuVuc WHERE TenKhuVuc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenKhuVuc);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhuVucDTO(
                            rs.getInt("ID"),
                            rs.getString("TenKhuVuc"),
                            rs.getString("DiaChi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa khu vực theo ID
    public static boolean deleteKhuVuc(int id) {
        if (id <= 0) return false;

        String sql = "DELETE FROM KhuVuc WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
