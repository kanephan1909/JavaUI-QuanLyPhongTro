package controllers;

import models.KhachThue;
import models.Phong;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class KhachThueController {
    private Connection conn;
    private PhongController phongController;

    public KhachThueController() {
        conn = DatabaseConnection.getConnection();
        phongController = new PhongController();
    }
    
    public ArrayList<KhachThue> getDanhSachKhach() {
        ArrayList<KhachThue> danhSachKhach = new ArrayList<>();
        String sql = "SELECT * FROM KhachThue";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()) {
                KhachThue k = new KhachThue(
                    rs.getInt("id"),
                    rs.getString("ho_ten"),
                    rs.getString("sdt"),
                    rs.getString("cccd"),
                    rs.getInt("phong_id"),
                    rs.getDate("ngay_thue"),
                    rs.getDate("ngay_tra")
                );
                danhSachKhach.add(k);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return danhSachKhach;
    }
    
    public boolean themKhach(KhachThue k) {
        try {
            // Kiểm tra phòng có trống không
            String checkPhong = "SELECT trang_thai FROM Phong WHERE ma_phong = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkPhong);
            checkStmt.setInt(1, k.getPhongId());
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Phòng không tồn tại!");
            }
            
            String trangThai = rs.getString("trang_thai");
            if (!trangThai.equals("Trống")) {
                throw new SQLException("Phòng đã có người thuê!");
            }

            // Thêm khách thuê
            String sql = "INSERT INTO KhachThue (ho_ten, sdt, cccd, phong_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, k.getHoTen());
            pstmt.setString(2, k.getSdt());
            pstmt.setString(3, k.getCccd());
            pstmt.setInt(4, k.getPhongId());
            
            boolean themThanhCong = pstmt.executeUpdate() > 0;
            if (themThanhCong) {
                // Cập nhật trạng thái phòng
                phongController.capNhatTrangThaiPhong(k.getPhongId(), "Đã thuê");
            }
            return themThanhCong;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            return false;
        }
    }

    public boolean xoaKhach(int id, int phongId) {
        try {
            String sql = "DELETE FROM KhachThue WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            boolean xoaThanhCong = pstmt.executeUpdate() > 0;
            if (xoaThanhCong) {
                // Cập nhật trạng thái phòng thành trống
                phongController.capNhatTrangThaiPhong(phongId, "Trống");
            }
            return xoaThanhCong;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaKhach(KhachThue k, int phongIdCu) {
        try {
            // Nếu đổi phòng, kiểm tra phòng mới có trống không
            if (k.getPhongId() != phongIdCu) {
                String checkPhong = "SELECT trang_thai FROM Phong WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkPhong);
                checkStmt.setInt(1, k.getPhongId());
                ResultSet rs = checkStmt.executeQuery();
                
                if (!rs.next() || !rs.getString("trang_thai").equals("Trống")) {
                    throw new SQLException("Phòng mới không khả dụng!");
                }
            }

            String sql = "UPDATE KhachThue SET ho_ten = ?, sdt = ?, cccd = ?, phong_id = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, k.getHoTen());
            pstmt.setString(2, k.getSdt());
            pstmt.setString(3, k.getCccd());
            pstmt.setInt(4, k.getPhongId());
            pstmt.setInt(5, k.getId());
            
            boolean suaThanhCong = pstmt.executeUpdate() > 0;
            if (suaThanhCong && k.getPhongId() != phongIdCu) {
                // Cập nhật trạng thái phòng cũ và mới
                phongController.capNhatTrangThaiPhong(phongIdCu, "Trống");
                phongController.capNhatTrangThaiPhong(k.getPhongId(), "Đã thuê");
            }
            return suaThanhCong;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            return false;
        }
    }

    // Thêm phương thức getKhachThueByPhong
    public KhachThue getKhachThueByPhong(int maPhong) {
        String sql = "SELECT * FROM KhachThue WHERE phong_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maPhong);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new KhachThue(
                    rs.getInt("id"),
                    rs.getString("ho_ten"),
                    rs.getString("sdt"),
                    rs.getString("cccd"),
                    rs.getInt("phong_id"),
                    rs.getDate("ngay_thue"),
                    rs.getDate("ngay_tra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
