package BUS;

import DAL.HoaDonDAL;
import DTO.HoaDonDTO;
import java.util.List;

public class HoaDonBUS {
    private HoaDonDAL hoaDonDAL;

    public HoaDonBUS() {
        hoaDonDAL = new HoaDonDAL();
    }

    // Lấy tất cả hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAL.getAllHoaDon();
    }

    // Lấy hóa đơn theo ID
    public HoaDonDTO getHoaDonById(int id) {
        return hoaDonDAL.getHoaDonById(id);

    }

    // Thêm hóa đơn mới
    public boolean addHoaDon(HoaDonDTO hoaDon) {
        float tongTinhLai = hoaDon.getTienPhong() + hoaDon.getTienDien() + hoaDon.getTienNuoc();
        if (hoaDon.getTongTien() != tongTinhLai) {
            System.out.println("❌ Tổng tiền không khớp với chi tiết!");
            return false;
        }
        return hoaDonDAL.addHoaDon(hoaDon);
    }

    // Cập nhật hóa đơn
    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        return hoaDonDAL.updateHoaDon(hoaDon);
    }

    // Xóa hóa đơn
    public boolean deleteHoaDon(int id) {
        return hoaDonDAL.deleteHoaDon(id);
    }


}
