package BUS;

import DAL.PhongDAL;
import DTO.PhongDTO;

import java.util.List;
import java.util.ArrayList;

public class PhongBUS {

    private final PhongDAL phongDAL;

    public PhongBUS() {
        phongDAL = new PhongDAL();
    }

    // Lấy danh sách tất cả phòng
    public List<PhongDTO> getAllPhong() {
        return phongDAL.getAllPhong();
    }

    // Thêm phòng
    public boolean addPhong(PhongDTO phong) {
        return phongDAL.addPhong(phong);
    }

    // Cập nhật phòng
    public boolean updatePhong(PhongDTO phong) {
        return phongDAL.updatePhong(phong);
    }

    // Tìm phòng theo mã
    public PhongDTO searchPhong(String maPhong) {
        return phongDAL.searchPhong(maPhong);
    }

    // Xóa phòng theo ID
    public boolean deletePhong(int id) {
        return phongDAL.deletePhong(id);
    }

    public List<PhongDTO> getPhongTrong() {
        List<PhongDTO> allPhong = phongDAL.getAllPhong();
        List<PhongDTO> phongTrong = new ArrayList<>();

        for (PhongDTO p : allPhong) {
            if ("Trống".equalsIgnoreCase(p.getTinhTrang())) {
                phongTrong.add(p);
            }
        }
        return phongTrong;
    }

}
