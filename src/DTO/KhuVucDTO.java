package DTO;

public class KhuVucDTO {
    private String id;
    private String tenKhuVuc;
    private String diaChi;

    // Constructor
    public KhuVucDTO(String id, String tenKhuVuc, String diaChi) {
        this.id = id;
        this.tenKhuVuc = tenKhuVuc;
        this.diaChi = diaChi;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

