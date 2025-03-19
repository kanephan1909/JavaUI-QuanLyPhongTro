package models;

public class Phong {
    private int maPhong;
    private String tenPhong;
    private int giaThue;
    private String trangThai;

    // Constructor mặc định
    public Phong() {
    }

    // Constructor đầy đủ
    public Phong(int maPhong, String tenPhong, int giaThue, String trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.trangThai = trangThai;
    }

    // Getter
    public int  getMaPhong() {
        return maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public String getTrangThai() {
        return trangThai;
    }

    // Setter
    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "maPhong=" + maPhong +
                ", tenPhong='" + tenPhong + '\'' +
                ", giaThue=" + giaThue +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
