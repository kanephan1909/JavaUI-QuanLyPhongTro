package DTO;


public class HoaDonDTO {
    private int id;
    private int maHopDong;
    private String thangNam;
    private float tienPhong;
    private float tienDien;
    private float tienNuoc;
    private float tongTien;
    private String trangThai;

    // Constructor


    public HoaDonDTO(int id, int maHopDong, String thangNam, float tienPhong, float tienDien, float tienNuoc, float tongTien, String trangThai) {
        this.id = id;
        this.maHopDong = maHopDong;
        this.thangNam = thangNam;
        this.tienPhong = tienPhong;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(int maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getThangNam() {
        return thangNam;
    }

    public void setThangNam(String thangNam) {
        this.thangNam = thangNam;
    }

    public float getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(float tienPhong) {
        this.tienPhong = tienPhong;
    }

    public float getTienDien() {
        return tienDien;
    }

    public void setTienDien(float tienDien) {
        this.tienDien = tienDien;
    }

    public float getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(float tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}

