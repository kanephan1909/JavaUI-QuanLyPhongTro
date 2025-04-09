package BUS;

import DAL.HopDongDAL;
import DTO.HopDongDTO;

import java.util.List;

public class HopDongBUS {
    private HopDongDAL hopDongDAL;

    public HopDongBUS(){
        hopDongDAL = new HopDongDAL();
    }

    // Lấy Tất Cả Danh Saách
    public List<HopDongDTO> getAllHopDong(){
        return hopDongDAL.getAllHopDong();
    }

    // Thêm Hợp Đồng
    public boolean addHopDong(HopDongDTO hopdong) {
        return hopDongDAL.addHopDong(hopdong);
    }

    // Sửa Hợp Đồng

    public boolean updateHopDong(HopDongDTO updatedHopdong) {
        return hopDongDAL.updateHopDong(updatedHopdong);
    }

    // XÓA HỢP ĐỒNG
    public boolean xoaHopDong(int id){
        return hopDongDAL.deleteHopDong(id);
    }
}
