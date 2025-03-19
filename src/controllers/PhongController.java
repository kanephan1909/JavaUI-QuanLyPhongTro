package controllers;

import models.Phong;
import java.sql.*;
import java.util.ArrayList;
import database.DatabaseConnection;

public class PhongController {
    private Connection conn;
    
    public PhongController() {
        conn = DatabaseConnection.getConnection();
        if (conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // Lấy tất cả phòng
    public ArrayList<Phong> getAllPhong() {
        ArrayList<Phong> danhSachPhong = new ArrayList<>();
        String sql = "SELECT * FROM Phong";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Phong p = new Phong(
                    rs.getInt("ma_phong"),
                    rs.getString("ten_phong"),
                    rs.getInt("gia_thue"),
                    rs.getString("trang_thai")
                );
                danhSachPhong.add(p);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return danhSachPhong;
    }

    // Lấy phòng theo ID
    public Phong getPhongById(int id) {
        String sql = "SELECT * FROM Phong WHERE ma_phong = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Phong(
                    rs.getInt("ma_phong"),
                    rs.getString("ten_phong"),
                    rs.getInt("gia_thue"),
                    rs.getString("trang_thai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách phòng trống
    public ArrayList<Phong> getPhongTrong() {
        ArrayList<Phong> danhSachPhong = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Phong WHERE trang_thai = N'Trống'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Phong p = new Phong(
                        rs.getInt("ma_phong"),
                        rs.getString("ten_phong"),
                        rs.getInt("gia_thue"),
                        rs.getString("trang_thai")
                );
                danhSachPhong.add(p);
                System.out.println("Phòng tìm thấy: " + p);
            }

            System.out.println("Tổng số phòng trống: " + danhSachPhong.size());


        } catch(SQLException e) {
            e.printStackTrace();
        }
        return danhSachPhong;
    }

    // Cập nhật trạng thái phòng
    public boolean capNhatTrangThaiPhong(int phongId, String trangThai) {
        String sql = "UPDATE Phong SET trang_thai = ? WHERE ma_phong = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, trangThai);
            pstmt.setInt(2, phongId);
            return pstmt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Thêm phòng mới
    public boolean themPhong(Phong p) {
        String sql = "INSERT INTO Phong (ten_phong, gia_thue, trang_thai) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getTenPhong());
            pstmt.setInt(2, p.getGiaThue());
            pstmt.setString(3, p.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa phòng 
    public boolean suaPhong(Phong p) {
        String sql = "UPDATE Phong SET ten_phong = ?, gia_thue = ?, trang_thai = ? WHERE ma_phong = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getTenPhong());
            stmt.setInt(2, p.getGiaThue());
            stmt.setString(3, p.getTrangThai());
            stmt.setInt(4, p.getMaPhong());
            return stmt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa phòng
    public boolean xoaPhong(int id) {
        String sql = "DELETE FROM Phong WHERE ma_phong = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
