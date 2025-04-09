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
                        rs.getString("ID"),
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
    public static String generateKhuVucID() {
        List<KhuVucDTO> khuVucList = getAllKhuVuc();  // Lấy danh sách khu vực hiện có
        int newIdNumber = khuVucList.size() + 1;  // Tính số lượng khu vực và tăng lên 1
        return "KV" + String.format("%03d", newIdNumber);  // Tạo ID với định dạng "KV001", "KV002", ...
    }


    // Thêm khu vực mới
    public static boolean addKhuVuc(KhuVucDTO khuVuc) {
        if (khuVuc == null || khuVuc.getTenKhuVuc() == null || khuVuc.getDiaChi() == null) {
            return false;  // Kiểm tra thông tin không hợp lệ
        }

        // Tạo ID tự động cho khu vực mới
        String newKhuVucID = generateKhuVucID();
        khuVuc.setId(newKhuVucID);  // Gán ID mới cho đối tượng khu vực

        String sql = "INSERT INTO KhuVuc (ID, TenKhuVuc, DiaChi) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu lệnh SQL
            ps.setString(1, khuVuc.getId());  // Gán ID tự động cho khu vực
            ps.setString(2, khuVuc.getTenKhuVuc());
            ps.setString(3, khuVuc.getDiaChi());

            return ps.executeUpdate() > 0;  // Thêm thành công, trả về true
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return false;  // Trả về false nếu có lỗi
    }

    // Cập nhật thông tin khu vực (dựa trên ID)
    public static boolean updateKhuVuc(KhuVucDTO khuVuc) {
        if (khuVuc == null || khuVuc.getId().isEmpty()) return false;

        String sql = "UPDATE KhuVuc SET TenKhuVuc=?, DiaChi=? WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, khuVuc.getTenKhuVuc());
            ps.setString(2, khuVuc.getDiaChi());
            ps.setString(3, khuVuc.getId());

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
                            rs.getString("ID"),
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
    public static boolean deleteKhuVuc(String id) {
        if (id.isEmpty()) return false;

        String sql = "DELETE FROM KhuVuc WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
