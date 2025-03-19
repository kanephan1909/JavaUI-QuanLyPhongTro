package controllers;

import models.HoaDon;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonController {
    private Connection conn;

    public HoaDonController() {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            throw new RuntimeException("Không thể kết nối database.");
        }
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> danhSachHoaDon = new ArrayList<>();
        try {
            
            String sql = 
                "SELECT hd.maHoaDon, hd.maPhong, hd.ngayLap, hd.tienDien, hd.tienNuoc, hd.tienPhong, " +
                "       hd.tongTien, hd.trangThai, hd.thang, " +
                "       kt.ho_ten AS hoTen, " +
                "       p.ten_phong AS tenPhong " +
                "FROM dbo.HoaDon hd " +
                "JOIN dbo.KhachThue kt ON hd.maKhach = kt.id " +
                "JOIN dbo.Phong p ON hd.maPhong = p.ma_phong";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
               
                HoaDon hoaDon = new HoaDon(
                    rs.getInt("maHoaDon"),
                    rs.getInt("maPhong"),
                    rs.getString("ngayLap"),
                    rs.getDouble("tienDien"),
                    rs.getDouble("tienNuoc"),
                    rs.getDouble("tienPhong"),
                    rs.getDouble("tongTien"),
                    rs.getString("trangThai"),
                    rs.getString("hoTen"),    // alias hoTen (lấy từ ho_ten)
                    rs.getString("thang"),
                    rs.getString("tenPhong")  // alias tenPhong (lấy từ ten_phong)
                );
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHoaDon;
    }

    // Thêm hóa đơn vào database
    public boolean themHoaDon(HoaDon hoaDon) {
        String sql = 
            "INSERT INTO dbo.HoaDon (maPhong, ngayLap, tienDien, tienNuoc, tienPhong, " +
            "                       tongTien, trangThai, thang, maKhach) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, hoaDon.getMaPhong());
            pstmt.setString(2, hoaDon.getNgayLap());
            pstmt.setDouble(3, hoaDon.getTienDien());
            pstmt.setDouble(4, hoaDon.getTienNuoc());
            pstmt.setDouble(5, hoaDon.getTienPhong());
            pstmt.setDouble(6, hoaDon.getTongTien());
            pstmt.setString(7, hoaDon.getTrangThai());
            pstmt.setString(8, hoaDon.getThang());

            // Tìm id của KhachThue dựa theo ho_ten
            // Nếu không có, trả về 0 => cột maKhach = 0
            pstmt.setInt(9, timMaKhachTuTen(hoaDon.getKhachThue()));

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Tìm id của KhachThue dựa vào cột ho_ten
    private int timMaKhachTuTen(String hoTen) {
        String sql = "SELECT id FROM dbo.KhachThue WHERE ho_ten = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hoTen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Không tìm thấy
    }

    // Xóa hóa đơn theo maHoaDon
    public void xoaHoaDon(int id) {
        try {
            String sql = "DELETE FROM dbo.HoaDon WHERE maHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật trạng thái hóa đơn (nếu cần)
    public void capNhatTrangThaiHoaDon(int id, String trangThai) {
        try {
            String sql = "UPDATE dbo.HoaDon SET trangThai = ? WHERE maHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, trangThai);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
