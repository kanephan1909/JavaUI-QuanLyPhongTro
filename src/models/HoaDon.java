package models;

public class HoaDon {
    private int maHoaDon;
    private int maPhong;
    private String ngayLap;
    private double tienDien;
    private double tienNuoc;
    private double tienPhong;
    private double tongTien;
    private String trangThai;
    private String khachThue; // Lấy từ bảng KhachThue (cột ho_ten)
    private String thang;     // Tháng của hóa đơn
    private String tenPhong;  // Lấy từ bảng Phong (cột ten_phong)

    // Constructor đầy đủ (thêm tham số tenPhong ở cuối)
    public HoaDon(int maHoaDon, int maPhong, String ngayLap,
                  double tienDien, double tienNuoc, double tienPhong,
                  double tongTien, String trangThai, String khachThue,
                  String thang, String tenPhong) {
        this.maHoaDon = maHoaDon;
        this.maPhong = maPhong;
        this.ngayLap = ngayLap;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienPhong = tienPhong;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.khachThue = khachThue;
        this.thang = thang;
        this.tenPhong = tenPhong;
    }

    // Getters và Setters
    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTienDien() {
        return tienDien;
    }

    public void setTienDien(double tienDien) {
        this.tienDien = tienDien;
    }

    public double getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(double tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public double getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(double tienPhong) {
        this.tienPhong = tienPhong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getKhachThue() {
        return khachThue;
    }

    public void setKhachThue(String khachThue) {
        this.khachThue = khachThue;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
}
