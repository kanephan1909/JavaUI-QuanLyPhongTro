package DTO;

public class KhachThueDTO {
    private int id;
    private String hoTen;
    private String soDienThoai;
    private String cccd;
    private int phongID;
    private String ngayThue;
    private String ngayTra;

    // Constructor
    public KhachThueDTO(int id, String hoTen, String soDienThoai, String cccd, int phongID, String ngayThue, String ngayTra) {
        this.id = id;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.cccd = cccd;
        this.phongID = phongID;
        this.ngayThue = ngayThue;
        this.ngayTra = ngayTra;
    }

    // Constructor dùng khi thêm khách mới
    public KhachThueDTO(String hoTen, String soDienThoai, String cccd, int phongID) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.cccd = cccd;
        this.phongID = phongID;
        this.ngayThue = java.time.LocalDate.now().toString(); // Ngày thuê là hôm nay
        this.ngayTra = null;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public int getPhongID() {
        return phongID;
    }

    public void setPhongID(int phongID) {
        this.phongID = phongID;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }
}

