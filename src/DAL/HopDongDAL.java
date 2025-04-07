package DAL;

import DTO.HopDongDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAL {

    // Lấy Danh Sách Hợp Đồng
    public List<HopDongDTO> getAllHopDong() {
        List<HopDongDTO> hopDongList = new ArrayList<>();
        String query = "SELECT * FROM HopDong";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HopDongDTO hopDongDTO = new HopDongDTO(
                        rs.getInt("id"),
                        rs.getInt("maNguoiThue"),
                        rs.getInt("maPhong"),
                        rs.getString("ngayLap"),
                        rs.getString("ngayBatDau"),
                        rs.getString("ngayKetThuc"),
                        rs.getDouble("tienDatCoc")
                );
                hopDongList.add(hopDongDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hopDongList;
    }

    // Thêm Hợp Đồng
    public boolean addHopDong(HopDongDTO hopDongDTO){
        String query = "INSERT INTO HopDong (MaNguoiThue, MaPhong, NgayLap, NgayBatDau, NgayKetThuc, TienDatCoc) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, hopDongDTO.getMaNguoiThue());
            ps.setInt(2, hopDongDTO.getMaPhong());
            ps.setString(3, hopDongDTO.getNgayLap());
            ps.setString(4, hopDongDTO.getNgayBatDau());
            ps.setString(5, hopDongDTO.getNgayKetThuc());
            ps.setDouble(6, hopDongDTO.getTienDatCoc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHopDong(int id) {
        String query = "DELETE FROM HopDong WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
