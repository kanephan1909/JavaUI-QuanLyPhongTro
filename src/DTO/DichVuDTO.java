package DTO;

public class DichVuDTO {
    private int id;            // ID của dịch vụ
    private String tenDichVu;  // Tên dịch vụ
    private double giaDichVu;  // Giá của dịch vụ
    private String phongID;    // ID phòng liên kết với dịch vụ (thay đổi từ int sang String)
    private String khachThueID;   // ID khách thuê liên kết với dịch vụ (thay đổi từ int sang String)

    // Constructor
    public DichVuDTO(int id, String tenDichVu, double giaDichVu, String phongID, String khachThueID) {
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

    public String getPhongID() {
        return phongID;
    }

    public void setPhongID(String phongID) {
        this.phongID = phongID;
    }

    public String getKhachThueID() {
        return khachThueID;
    }

    public void setKhachThueID(String khachThueID) {
        this.khachThueID = khachThueID;
    }

    @Override
    public String toString() {
        return "DichVuDTO{" +
                "id=" + id +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", giaDichVu=" + giaDichVu +
                ", phongID='" + phongID + '\'' +
                ", khachThueID='" + khachThueID + '\'' +
                '}';
    }
}
