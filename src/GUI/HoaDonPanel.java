package GUI;

import BUS.HoaDonBUS;
import BUS.KhachThueBUS;
import DAL.HoaDonDAL;
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
    private JButton btnThem, btnSua, btnXoa, btnCapNhat;
    private JComboBox<String> cboTrangThai;
    private JTextField txtMaPhong, txtTienDien, txtTienNuoc, txtTienPhong, txtMaHopDong;
    private JDateChooser dateThangNam;

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private KhachThueBUS khachThueBUS = new KhachThueBUS();

    public HoaDonPanel() {
        initComponents();
        loadData();
    }

    private void initComponents() {

        setLayout(new BorderLayout(5,5));

//        JPanel topPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        topPanel3.setPreferredSize(new Dimension(200, 100));
//        topPanel3.setBackground(new Color(22, 26, 83));
//        JLabel lblHoaDon = new JLabel("<html><div style='text-align: center;margin-top: 18px; color: #fff;'>Hóa Đơn</div></html>");
//        lblHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        lblHoaDon.setForeground(Color.WHITE); // Màu chữ trắng cho label
//        topPanel3.add(lblHoaDon, BorderLayout.CENTER);
//        add(topPanel3, BorderLayout.NORTH);


        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
//        inputPanel.setBounds(0, 100, 1005, 200);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin hóa đơn"));

        inputPanel.add(new JLabel("Mã Hợp Đồng:"));
        txtMaHopDong = new JTextField();
        inputPanel.add(txtMaHopDong);

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
        dateThangNam = new JDateChooser();
        dateThangNam.setDateFormatString("MM/yyyy"); // Đặt định dạng hiển thị là MM/yyyy
        inputPanel.add(dateThangNam);

        inputPanel.add(new JLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Đã thanh toán", "Chưa thanh toán"});
        inputPanel.add(cboTrangThai);

        add(inputPanel, BorderLayout.NORTH);

        // Các cột của bảng
        String[] columns = {"ID", "Mã Hợp Đồng", "Tháng/Năm", "Tiền Phòng", "Tiền Điện", "Tiền Nước", "Tổng Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.setBounds(200, 0, 600, 400);
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cap Nhat");

        Color customColor = new Color(20, 25, 95);
        btnThem.setBackground(customColor);
        btnSua.setBackground(customColor);
        btnXoa.setBackground(customColor);
        btnCapNhat.setBackground(customColor);

        // Đặt màu chữ cho các nút
        btnThem.setForeground(Color.WHITE);
        btnSua.setForeground(Color.WHITE);
        btnXoa.setForeground(Color.WHITE);
        btnCapNhat.setForeground(Color.WHITE);

        // Đặt kích thước cho các nút
        Dimension buttonSize = new Dimension(100, 30);  // Thay đổi kích thước của nút
        btnThem.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnCapNhat.setPreferredSize(buttonSize);

        // Thêm các nút vào bottomPanel
        buttonPanel.add(btnThem);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Thêm khoảng cách giữa các nút
        buttonPanel.add(btnSua);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btnXoa);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btnCapNhat);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnCapNhat);

        add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themHoaDon());
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnCapNhat.addActionListener(e -> loadData());
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

    private void themHoaDon() {
        try {
            String maPhongText = txtMaPhong.getText().trim();
            String tienDienText = txtTienDien.getText().trim();
            String tienNuocText = txtTienNuoc.getText().trim();
            String tienPhongText = txtTienPhong.getText().trim();

            // Kiểm tra các trường nhập liệu
            if (maPhongText.isEmpty() || tienDienText.isEmpty() || tienNuocText.isEmpty() || tienPhongText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // Chuyển đổi các giá trị nhập vào
            String maPhong = maPhongText;
            float tienDien = Float.parseFloat(tienDienText);
            float tienNuoc = Float.parseFloat(tienNuocText);
            float tienPhong = Float.parseFloat(tienPhongText);

            // Kiểm tra các giá trị số hợp lệ
            if (tienDien < 0 || tienNuoc < 0 || tienPhong < 0) {
                JOptionPane.showMessageDialog(this, "Tiền điện, tiền nước và tiền phòng phải là số dương!");
                return;
            }

            // Lấy mã hợp đồng từ mã phòng
            int maHopDong = HoaDonDAL.getMaHopDongFromMaPhong(maPhong);
            if (maHopDong == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã hợp đồng cho mã phòng này!");
                return;
            }

            // Tính tổng tiền
            float tongTien = tienDien + tienNuoc + tienPhong;

            // Lấy tháng/năm
            String thangNam = "";
            if (dateThangNam.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                thangNam = sdf.format(dateThangNam.getDate());
            }

            if (thangNam.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng/năm");
                return;
            }

            // Tạo đối tượng hóa đơn
            HoaDonDTO hoaDon = new HoaDonDTO(0, maHopDong, thangNam, tienPhong, tienDien, tienNuoc, tongTien, cboTrangThai.getSelectedItem().toString());

            // Thêm hóa đơn vào cơ sở dữ liệu
            if (hoaDonBUS.addHoaDon(hoaDon)) {
                danhSachHoaDon.add(hoaDon);  // Cập nhật danh sách hóa đơn
                capNhatTable();              // Cập nhật lại bảng
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                clearForm();                 // Xóa các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại! Kiểm tra tổng tiền.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearForm() {
        txtMaPhong.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        txtTienPhong.setText("");
        dateThangNam.setDate(null); // Clear JDateChooser
        cboTrangThai.setSelectedIndex(0);
    }

    private void suaHoaDon() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HoaDonDTO hd = danhSachHoaDon.get(selectedRow);
        Window window = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) window, "Sửa Hóa Đơn", true);
        dialog.setSize(400, 350);
        dialog.setLayout(new GridLayout(8, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        JTextField tfTienDien = new JTextField(String.valueOf(hd.getTienDien()));
        JTextField tfTienNuoc = new JTextField(String.valueOf(hd.getTienNuoc()));
        JTextField tfTienPhong = new JTextField(String.valueOf(hd.getTienPhong()));

        JDateChooser dateChooser = new JDateChooser();
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
            Date thangNamDate = sdfInput.parse(hd.getThangNam());
            dateChooser.setDate(thangNamDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] trangThaiOptions = {"Chưa thanh toán", "Đã thanh toán", "Quá hạn"};
        JComboBox<String> cbTrangThai = new JComboBox<>(trangThaiOptions);
        cbTrangThai.setSelectedItem(hd.getTrangThai());

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

                Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn tháng/năm!");
                    return;
                }

                SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-01");
                String thangNam = sdfOutput.format(selectedDate);

                String trangThai = (String) cbTrangThai.getSelectedItem();

                float tongTien = tienDien + tienNuoc + tienPhong;

                hd.setTienDien(tienDien);
                hd.setTienNuoc(tienNuoc);
                hd.setTienPhong(tienPhong);
                hd.setTongTien(tongTien);
                hd.setThangNam(thangNam);
                hd.setTrangThai(trangThai);

                if (hoaDonBUS.updateHoaDon(hd)) {
                    loadData();
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
