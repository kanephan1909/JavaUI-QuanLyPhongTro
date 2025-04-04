package DTO;
import java.math.BigDecimal;
import java.util.Date;

public class ChiSoNuocDTO {
    private int id;
    private int khachThueID;
    private String thangNam;
    private float chiSoCu;
    private float chiSoMoi;

    // Constructor

    public ChiSoNuocDTO(int id, int khachThueID, String thangNam, float chiSoCu, float chiSoMoi) {
        this.id = id;
        this.khachThueID = khachThueID;
        this.thangNam = thangNam;
        this.chiSoCu = chiSoCu;
        this.chiSoMoi = chiSoMoi;
    }


    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKhachThueID() {
        return khachThueID;
    }

    public void setKhachThueID(int khachThueID) {
        this.khachThueID = khachThueID;
    }

    public String getThangNam() {
        return thangNam;
    }

    public void setThangNam(String thangNam) {
        this.thangNam = thangNam;
    }

    public float getChiSoCu() {
        return chiSoCu;
    }

    public void setChiSoCu(float chiSoCu) {
        this.chiSoCu = chiSoCu;
    }

    public float getChiSoMoi() {
        return chiSoMoi;
    }

    public void setChiSoMoi(float chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
    }

}

