package DTO;
import java.util.Date;

public class HopDongDTO {
    private int id;
    private int maNguoiThue;
    private int maPhong;
    private Date ngayLap;


    private Date ngayBatDau;
    private Date ngayKetThuc;
    private double tienDatCoc;

    // Constructor
    public HopDongDTO(int id, int maNguoiThue, int maPhong, Date ngayLap, Date ngayBatDau, Date ngayKetThuc, double tienDatCoc) {
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

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public double getTienDatCoc() {
        return tienDatCoc;
    }

    public void setTienDatCoc(double tienDatCoc) {
        this.tienDatCoc = tienDatCoc;
    }
}

