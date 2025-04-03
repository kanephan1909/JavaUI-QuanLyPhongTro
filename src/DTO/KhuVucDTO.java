package DTO;

public class KhuVucDTO {
    private int id;
    private String tenKhuVuc;
    private String diaChi;

    // Constructor
    public KhuVucDTO(int id, String tenKhuVuc, String diaChi) {
        this.id = id;
        this.tenKhuVuc = tenKhuVuc;
        this.diaChi = diaChi;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}

