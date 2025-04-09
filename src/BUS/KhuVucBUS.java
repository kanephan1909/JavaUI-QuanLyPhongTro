package BUS;

import DAL.KhuVucDAL;
import DTO.KhuVucDTO;
import DTO.PhongDTO;

import java.util.List;

public class KhuVucBUS {

    private KhuVucDAL khuVucDAL;

    public KhuVucBUS() {
        khuVucDAL = new KhuVucDAL();
    }

    // Lấy danh sách tất cả Khu Vực
    public List<KhuVucDTO> getAllKhuVuc() {
        return khuVucDAL.getAllKhuVuc();
    }

    // Thêm Khu Vực
    public boolean addKhuVuc(KhuVucDTO khuVucDTO) {
        return khuVucDAL.addKhuVuc(khuVucDTO);
    }

    // Sửa Khu Vực
    public boolean updateKhuVuc(KhuVucDTO khuVucDTO) {
        return khuVucDAL.updateKhuVuc(khuVucDTO);
    }

    // Xóa Khu Vực
    public boolean deleteKhuVuc(int id) {
        return khuVucDAL.deleteKhuVuc(id);
    }
}
