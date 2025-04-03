package DTO;
import java.math.BigDecimal;
import java.util.Date;

public class ChiSoDienDTO {
    private int id;
    private int khachThueID;
    private Date thangNam;
    private BigDecimal chiSoCu;
    private BigDecimal chiSoMoi;

    // Constructor
    public ChiSoDienDTO(int id, int khachThueID, Date thangNam, BigDecimal chiSoCu, BigDecimal chiSoMoi) {
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

    public Date getThangNam() {
        return thangNam;
    }

    public void setThangNam(Date thangNam) {
        this.thangNam = thangNam;
    }

    public BigDecimal getChiSoCu() {
        return chiSoCu;
    }

    public void setChiSoCu(BigDecimal chiSoCu) {
        this.chiSoCu = chiSoCu;
    }

    public BigDecimal getChiSoMoi() {
        return chiSoMoi;
    }

    public void setChiSoMoi(BigDecimal chiSoMoi) {
        this.chiSoMoi = chiSoMoi;
    }
}

