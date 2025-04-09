package DTO;
import java.util.Date;

public class HopDongDTO {
    private int id;
    private int maNguoiThue;
    private String maPhong;
    private String ngayLap;
    private String ngayBatDau;
    private String ngayKetThuc;
    private double tienDatCoc;

    // Constructor


    public HopDongDTO(int id, int maNguoiThue, String maPhong, String ngayLap, String ngayBatDau, String ngayKetThuc, double tienDatCoc) {
        this.id = id;
        this.maNguoiThue = maNguoiThue;
        this.maPhong = maPhong;
        this.ngayLap = ngayLap;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tienDatCoc = tienDatCoc;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaNguoiThue() {
        return maNguoiThue;
    }

    public void setMaNguoiThue(int maNguoiThue) {
        this.maNguoiThue = maNguoiThue;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getTienDatCoc() {
        return tienDatCoc;
    }

    public void setTienDatCoc(double tienDatCoc) {
        this.tienDatCoc = tienDatCoc;
    }
}

