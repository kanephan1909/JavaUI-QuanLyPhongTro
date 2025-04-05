package DTO;

public class PhongDTO {
    private int id;
    private String maPhong;
    private String tenPhong;
    private String loaiPhong;
    private int dienTich;
    private float giaPhong;
    private String moTa;
    private String tinhTrang;
    private int khuVucID;

    // Constructor
    public PhongDTO(int id, String maPhong, String tenPhong, String loaiPhong, int dienTich, float giaPhong, String moTa, String tinhTrang, int khuVucID) {
        this.id = id;
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.moTa = moTa;
        this.tinhTrang = tinhTrang;
        this.khuVucID = khuVucID;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public float getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(float giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getKhuVucID() {
        return khuVucID;
    }

    public void setKhuVucID(int khuVucID) {
        this.khuVucID = khuVucID;
    }
}

