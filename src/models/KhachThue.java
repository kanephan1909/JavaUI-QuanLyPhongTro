package models;

import java.sql.Date;

import controllers.PhongController;

public class KhachThue {
    private int id;
    private String hoTen;
    private String sdt;
    private String cccd;
    private int phongId;
    private Date ngayThue;
    private Date ngayTra;

    // Constructor mặc định
    public KhachThue() {
        this.ngayThue = new Date(System.currentTimeMillis());
    }

    // Constructor đầy đủ với ngày thuê và ngày trả
    public KhachThue(int id, String hoTen, String sdt, String cccd, int phongId, Date ngayThue, Date ngayTra) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.cccd = cccd;
        this.phongId = phongId;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
    }

    // Constructor không có ID (dùng khi thêm mới)
    public KhachThue(String hoTen, String sdt, String cccd, int phongId) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.cccd = cccd;
        this.phongId = phongId;
        this.ngayThue = new Date(System.currentTimeMillis());
    }

    // Thêm phương thức getPhong
    public Phong getPhong() {
        return new PhongController().getPhongById(phongId);
    }

    // Getters
    public int getId() { return id; }
    public String getHoTen() { return hoTen; }
    public String getSdt() { return sdt; }
    public String getCccd() { return cccd; }
    public int getPhongId() { return phongId; }
    public Date getNgayThue() { return ngayThue; }
    public Date getNgayTra() { return ngayTra; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public void setcccd(String cccd) { this.cccd = cccd; }
    public void setPhongId(int phongId) { this.phongId = phongId; }
    public void setNgayThue(Date ngayThue) { this.ngayThue = ngayThue; }
    public void setNgayTra(Date ngayTra) { this.ngayTra = ngayTra; }

    @Override
    public String toString() {
        return String.format("Khách: %s - Cccd: %s - SDT: %s - Phòng: %d",
            hoTen, cccd, sdt, phongId);
    }

    // Phương thức kiểm tra thông tin hợp lệ
    public boolean isValid() {
        return hoTen != null && !hoTen.trim().isEmpty() &&
               sdt != null && sdt.matches("\\d{10}") &&
               cccd != null && cccd.matches("\\d{9}|\\d{12}") &&
               phongId > 0;
    }
}
