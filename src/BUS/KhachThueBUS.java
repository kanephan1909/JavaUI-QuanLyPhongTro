package BUS;

import DAL.KhachThueDAL;
import DTO.KhachThueDTO;

import java.util.List;

public class KhachThueBUS {
    private KhachThueDAL khachThueDAL;

    public KhachThueBUS() {
        khachThueDAL = new KhachThueDAL();
    }

    // Lấy tất cả Khách Thuê
    public List<KhachThueDTO> getAllKhachThue() {
        return khachThueDAL.getAllKhachThue();
    }

    // Thêm Khách THuê
    public boolean addKhachThue(KhachThueDTO khachThue) { return khachThueDAL.insertKhachThue(khachThue); }

    // Tìm Kiếm Khách Bằng ID
    public KhachThueDTO getKhachThueById(int id) { return khachThueDAL.getKhachThueById(id); }

    // Chỉnh Sửa Khách Thuê
    public  boolean updateKhachThue(KhachThueDTO khachThue) { return khachThueDAL.updateKhachThue(khachThue); }

    // Xóa Khách Thuê
    public boolean deleteKhachThue(int id) {
        return khachThueDAL.deleteKhachThue(id);
    }


}
