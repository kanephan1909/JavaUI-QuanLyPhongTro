package DTO;

public class DichVuDTO {
    private int id;            // ID của dịch vụ
    private String tenDichVu;  // Tên dịch vụ
    private double giaDichVu;  // Giá của dịch vụ
    private int phongID;       // ID phòng liên kết với dịch vụ
    private int khachThueID;   // ID khách thuê liên kết với dịch vụ

    // Constructor
    public DichVuDTO(int id, String tenDichVu, double giaDichVu, int phongID, int khachThueID) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.giaDichVu = giaDichVu;
        this.phongID = phongID;
        this.khachThueID = khachThueID;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public double getGiaDichVu() {
        return giaDichVu;
    }

    public void setGiaDichVu(double giaDichVu) {
        this.giaDichVu = giaDichVu;
    }

    public int getPhongID() {
        return phongID;
    }

    public void setPhongID(int phongID) {
        this.phongID = phongID;
    }

    public int getKhachThueID() {
        return khachThueID;
    }

    public void setKhachThueID(int khachThueID) {
        this.khachThueID = khachThueID;
    }

    @Override
    public String toString() {
        return "DichVuDTO{" +
                "id=" + id +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", giaDichVu=" + giaDichVu +
                ", phongID=" + phongID +
                ", khachThueID=" + khachThueID +
                '}';
    }
}

