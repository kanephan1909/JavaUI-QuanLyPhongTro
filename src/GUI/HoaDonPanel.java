package GUI;

import BUS.HoaDonBUS;
import BUS.KhachThueBUS;
import DTO.HoaDonDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.toedter.calendar.JDateChooser;


public class HoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<HoaDonDTO> danhSachHoaDon;
    private JButton btnThem, btnSua, btnXoa;
    private JComboBox<String> cboTrangThai;
    private JTextField txtMaPhong, txtTienDien, txtTienNuoc, txtTienPhong, txtThangNam;

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private KhachThueBUS khachThueBUS = new KhachThueBUS();

    public HoaDonPanel() {
        initComponents();
        loadData();

    }

    private void initComponents() {
        setLayout(new BorderLayout());

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

        // Sử dụng JDateChooser cho Tháng/Năm
        inputPanel.add(new JLabel("Tháng/Năm (MM/YYYY):"));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("MM/yyyy"); // Đặt định dạng hiển thị là MM/yyyy
        inputPanel.add(dateChooser);

        inputPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        inputPanel.add(cboTrangThai);

        add(inputPanel, BorderLayout.NORTH);

        // Các cột của bảng
        String[] columns = {"ID", "Mã Hợp Đồng", "Tháng/Năm", "Tiền Phòng", "Tiền Điện", "Tiền Nước", "Tổng Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themHoaDon(dateChooser)); // Truyền dateChooser vào
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
    }

    private void loadData() {
        danhSachHoaDon = new ArrayList<>(hoaDonBUS.getAllHoaDon());
        capNhatTable();
    }

    private void capNhatTable() {
        tableModel.setRowCount(0);
        for (HoaDonDTO hd : danhSachHoaDon) {
            tableModel.addRow(new Object[]{
                    hd.getId(),
                    hd.getMaHopDong(),
                    hd.getThangNam(),
                    hd.getTienPhong(),
                    hd.getTienDien(),
                    hd.getTienNuoc(),
                    hd.getTongTien(),
                    hd.getTrangThai()
            });
        }
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private void themHoaDon(JDateChooser dateChooser) {
        try {
            int maPhong = Integer.parseInt(txtMaPhong.getText().trim());
            float tienDien = Float.parseFloat(txtTienDien.getText().trim());
            float tienNuoc = Float.parseFloat(txtTienNuoc.getText().trim());
            float tienPhong = Float.parseFloat(txtTienPhong.getText().trim());
            float tongTien = tienDien + tienNuoc + tienPhong;

            String thangNam = "";
            if (dateChooser.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                thangNam = sdf.format(dateChooser.getDate());
            }

            if (thangNam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng/năm");
                return;
            }

            // Kiểm tra định dạng tháng/năm
            if (!thangNam.matches("\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Tháng/Năm phải theo định dạng MM/YYYY");
                return;
            }

            String trangThai = cboTrangThai.getSelectedItem().toString();

            // Giả sử tìm được mã hợp đồng qua mã phòng (giả lập)
            int maHopDong = maPhong; // Giả sử map 1:1 trong trường hợp này

            HoaDonDTO hoaDon = new HoaDonDTO(0, maHopDong, thangNam, tienPhong, tienDien, tienNuoc, tongTien, trangThai);

            // Thêm hóa đơn vào cơ sở dữ liệu
            if (hoaDonBUS.addHoaDon(hoaDon)) {
                danhSachHoaDon.add(hoaDon);
                capNhatTable();
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại! Kiểm tra tổng tiền.");
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
        txtThangNam.setText("");
        cboTrangThai.setSelectedIndex(0);
    }

    private void suaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HoaDonDTO hd = danhSachHoaDon.get(selectedRow);

        // Tạo form trong JDialog
        Window window = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) window, "Sửa Hóa Đơn", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(8, 2, 10, 10)); // Cập nhật số hàng cho GridLayout
        dialog.setLocationRelativeTo(this);

        // Tạo các trường nhập dữ liệu
        JTextField tfTienDien = new JTextField(String.valueOf(hd.getTienDien()));
        JTextField tfTienNuoc = new JTextField(String.valueOf(hd.getTienNuoc()));
        JTextField tfTienPhong = new JTextField(String.valueOf(hd.getTienPhong()));

        // Tạo JDateChooser cho Tháng/Năm
        JDateChooser dateChooser = new JDateChooser();
        try {
            // Định dạng ngày là yyyy-MM-dd
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
            Date thangNamDate = sdfInput.parse(hd.getThangNam()); // Phân tích chuỗi ngày đầy đủ
            dateChooser.setDate(thangNamDate);
        } catch (Exception e) {
            e.printStackTrace(); // Nếu có lỗi, ngày mặc định sẽ là null
        }

        String[] trangThaiOptions = {"Chưa thanh toán", "Đã thanh toán", "Quá hạn"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setSelectedItem(hd.getTrangThai());

        // Add các thành phần vào dialog
        dialog.add(new JLabel("Tiền điện:"));
        dialog.add(tfTienDien);
        dialog.add(new JLabel("Tiền nước:"));
        dialog.add(tfTienNuoc);
        dialog.add(new JLabel("Tiền phòng:"));
        dialog.add(tfTienPhong);
        dialog.add(new JLabel("Tháng (MM/YYYY):"));
        dialog.add(dateChooser);
        dialog.add(new JLabel("Trạng thái:"));
        dialog.add(cbTrangThai);

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        dialog.add(btnLuu);
        dialog.add(btnHuy);

        btnLuu.addActionListener(e -> {
            try {
                float tienDien = Float.parseFloat(tfTienDien.getText());
                float tienNuoc = Float.parseFloat(tfTienNuoc.getText());
                float tienPhong = Float.parseFloat(tfTienPhong.getText());

                // Lấy ngày từ JDateChooser và định dạng lại thành MM/YYYY
                Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn tháng/năm!");
                    return;
                }

                // Định dạng ngày thành yyyy-MM-01 (ngày đầu tiên của tháng)
                SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-01"); // Lấy ngày đầu tiên của tháng
                String thangNam = sdfOutput.format(selectedDate);

                String trangThai = (String) cbTrangThai.getSelectedItem();

                // Tính tổng tiền
                float tongTien = tienDien + tienNuoc + tienPhong;

                // Cập nhật thông tin hóa đơn
                hd.setTienDien(tienDien);
                hd.setTienNuoc(tienNuoc);
                hd.setTienPhong(tienPhong);
                hd.setTongTien(tongTien);
                hd.setThangNam(thangNam);
                hd.setTrangThai(trangThai);

                // Cập nhật hóa đơn vào cơ sở dữ liệu
                if (hoaDonBUS.updateHoaDon(hd)) {
                    loadData(); // Gọi lại loadData() để làm mới bảng
                    capNhatTable();
                    JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại! Kiểm tra dữ liệu.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đúng định dạng số!");
            }
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void xoaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            HoaDonDTO hd = danhSachHoaDon.get(selectedRow);
            if (hoaDonBUS.deleteHoaDon(hd.getId())) {
                danhSachHoaDon.remove(selectedRow);
                capNhatTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}