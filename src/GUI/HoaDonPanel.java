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

        inputPanel.add(new JLabel("Tháng/Năm (MM/YYYY):"));
        txtThangNam = new JTextField();
        inputPanel.add(txtThangNam);

        inputPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        inputPanel.add(cboTrangThai);

        add(inputPanel, BorderLayout.NORTH);

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

        btnThem.addActionListener(e -> themHoaDon());
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

    private void themHoaDon() {
        try {
            int maPhong = Integer.parseInt(txtMaPhong.getText().trim());
            float tienDien = Float.parseFloat(txtTienDien.getText().trim());
            float tienNuoc = Float.parseFloat(txtTienNuoc.getText().trim());
            float tienPhong = Float.parseFloat(txtTienPhong.getText().trim());
            float tongTien = tienDien + tienNuoc + tienPhong;
            String thangNam = txtThangNam.getText().trim();

            if (!thangNam.matches("\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Tháng/Năm phải theo định dạng MM/YYYY");
                return;
            }

            String trangThai = cboTrangThai.getSelectedItem().toString();

            // Giả sử tìm được mã hợp đồng qua mã phòng (giả lập)
            int maHopDong = maPhong; // Giả sử map 1:1 trong trường hợp này

            HoaDonDTO hoaDon = new HoaDonDTO(0, maHopDong, thangNam, tienPhong, tienDien, tienNuoc, tongTien, trangThai);

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
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        // Tạo các trường nhập dữ liệu
        JTextField tfTienDien = new JTextField(String.valueOf(hd.getTienDien()));
        JTextField tfTienNuoc = new JTextField(String.valueOf(hd.getTienNuoc()));
        JTextField tfTienPhong = new JTextField(String.valueOf(hd.getTienPhong()));
        JTextField tfThangNam = new JTextField(hd.getThangNam());
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
        dialog.add(tfThangNam);
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
                String thangNam = tfThangNam.getText();
                String trangThai = (String) cbTrangThai.getSelectedItem();

                float tongTien = tienDien + tienNuoc + tienPhong;

                hd.setTienDien(tienDien);
                hd.setTienNuoc(tienNuoc);
                hd.setTienPhong(tienPhong);
                hd.setTongTien(tongTien);
                hd.setThangNam(thangNam);
                hd.setTrangThai(trangThai);

                if (hoaDonBUS.updateHoaDon(hd)) {
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