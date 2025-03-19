package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.HoaDon;
import controllers.HoaDonController;
import controllers.KhachThueController;
import models.KhachThue;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa;
    private ArrayList<HoaDon> danhSachHoaDon;
    private HoaDonController hoaDonController;
    private KhachThueController khachThueController;
    private JComboBox<String> cboTrangThai;
    private JTextField txtMaPhong, txtTienDien, txtTienNuoc, txtTienPhong, txtThang;

    public HoaDonPanel() {
        hoaDonController = new HoaDonController();
        khachThueController = new KhachThueController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel nhập liệu (phía trên)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin hóa đơn"));

        inputPanel.add(new JLabel("Mã phòng:"));
        txtMaPhong = new JTextField();
        inputPanel.add(txtMaPhong);

        inputPanel.add(new JLabel("Tiền điện:"));
        txtTienDien = new JTextField();
        inputPanel.add(txtTienDien);

        inputPanel.add(new JLabel("Tiền nước:"));
        txtTienNuoc = new JTextField();
        inputPanel.add(txtTienNuoc);

        inputPanel.add(new JLabel("Tiền phòng:"));
        txtTienPhong = new JTextField();
        inputPanel.add(txtTienPhong);

        inputPanel.add(new JLabel("Tháng (MM/YYYY):"));
        txtThang = new JTextField();
        inputPanel.add(txtThang);

        inputPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        inputPanel.add(cboTrangThai);

        add(inputPanel, BorderLayout.NORTH);

        // Tạo bảng hiển thị danh sách hóa đơn (ở giữa)
        String[] columns = {"ID", "Khách thuê", "Tên phòng", "Tháng", "Tổng tiền", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa nút (ở dưới)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        btnThem.addActionListener(e -> themHoaDon());
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
    }

    // Lấy ngày hiện tại theo định dạng yyyy-MM-dd
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    // Load dữ liệu từ database và cập nhật bảng
    private void loadData() {
        danhSachHoaDon = hoaDonController.getAllHoaDon();
        capNhatTable();
    }

    private void capNhatTable() {
        tableModel.setRowCount(0);
        for (HoaDon hd : danhSachHoaDon) {
            tableModel.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getKhachThue(),
                hd.getTenPhong(),
                hd.getThang(),
                hd.getTongTien(),
                hd.getTrangThai()
            });
        }
    }

    // Xử lý thêm hóa đơn
    private void themHoaDon() {
        try {
            int maPhong = Integer.parseInt(txtMaPhong.getText().trim());
            double tienDien = Double.parseDouble(txtTienDien.getText().trim());
            double tienNuoc = Double.parseDouble(txtTienNuoc.getText().trim());
            double tienPhong = Double.parseDouble(txtTienPhong.getText().trim());
            double tongTien = tienDien + tienNuoc + tienPhong;
            String thang = txtThang.getText().trim();
            String trangThai = cboTrangThai.getSelectedItem().toString();

            // Kiểm tra sự ràng buộc với khách hàng
            KhachThue khachThue = khachThueController.getKhachThueByPhong(maPhong);
            if (khachThue == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách thuê cho phòng này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            HoaDon hoaDon = new HoaDon(0, maPhong, getCurrentDate(), tienDien, tienNuoc, tienPhong, tongTien, trangThai, khachThue.getHoTen(), thang, khachThue.getPhong().getTenPhong());
            if (hoaDonController.themHoaDon(hoaDon)) {
                // Thêm hóa đơn vào danh sách và cập nhật bảng
                danhSachHoaDon.add(hoaDon);
                capNhatTable();

                // Hiển thị thông tin hóa đơn vừa thêm
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!\n" +
                        "Mã Hóa Đơn: " + hoaDon.getMaHoaDon() + "\n" +
                        "Mã Phòng: " + hoaDon.getMaPhong() + "\n" +
                        "Ngày Lập: " + hoaDon.getNgayLap() + "\n" +
                        "Tiền Điện: " + hoaDon.getTienDien() + "\n" +
                        "Tiền Nước: " + hoaDon.getTienNuoc() + "\n" +
                        "Tiền Phòng: " + hoaDon.getTienPhong() + "\n" +
                        "Tổng Tiền: " + hoaDon.getTongTien() + "\n" +
                        "Trạng Thái: " + hoaDon.getTrangThai() + "\n" +
                        "Tháng: " + hoaDon.getThang() + "\n" +
                        "Tên Phòng: " + hoaDon.getTenPhong());

                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void clearForm() {
        txtMaPhong.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        txtTienPhong.setText("");
        txtThang.setText("");
        cboTrangThai.setSelectedIndex(0);
    }

    // Xử lý sửa hóa đơn (chỉ sửa trong danh sách và giao diện, chưa cập nhật DB)
    private void suaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        HoaDon hd = danhSachHoaDon.get(selectedRow);

        // Cho người dùng nhập lại thông tin
        String khachThue = JOptionPane.showInputDialog(this, "Nhập tên khách thuê:", hd.getKhachThue());
        String thang = JOptionPane.showInputDialog(this, "Nhập tháng (MM/YYYY):", hd.getThang());
        try {
            int maPhong = Integer.parseInt(
                JOptionPane.showInputDialog(this, "Nhập mã phòng:", hd.getMaPhong())
            );
            double tienDien = Double.parseDouble(
                JOptionPane.showInputDialog(this, "Nhập tiền điện:", hd.getTienDien())
            );
            double tienNuoc = Double.parseDouble(
                JOptionPane.showInputDialog(this, "Nhập tiền nước:", hd.getTienNuoc())
            );
            double tienPhong = Double.parseDouble(
                JOptionPane.showInputDialog(this, "Nhập tiền phòng:", hd.getTienPhong())
            );
            double tongTien = tienDien + tienNuoc + tienPhong;
            String trangThai = JOptionPane.showInputDialog(this, "Nhập trạng thái:", hd.getTrangThai());

            // Cập nhật đối tượng (chỉ trong ArrayList, chưa update DB)
            hd.setMaPhong(maPhong);
            hd.setTienDien(tienDien);
            hd.setTienNuoc(tienNuoc);
            hd.setTienPhong(tienPhong);
            hd.setTongTien(tongTien);
            hd.setTrangThai(trangThai);
            hd.setKhachThue(khachThue);
            hd.setThang(thang);

            // Cập nhật lại giao diện bảng
            capNhatTable();

            // TODO: Nếu muốn cập nhật DB, bạn cần viết hàm updateHoaDon(hd) trong HoaDonController
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    // Xử lý xóa hóa đơn
    private void xoaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
            this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            HoaDon hd = danhSachHoaDon.get(selectedRow);
            hoaDonController.xoaHoaDon(hd.getMaHoaDon()); // Xóa trong DB
            danhSachHoaDon.remove(selectedRow);          // Xóa khỏi danh sách
            capNhatTable();
        }
    }
}
