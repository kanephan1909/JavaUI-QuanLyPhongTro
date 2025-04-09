package GUI;

import BUS.HopDongBUS;
import DTO.HopDongDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HopDongPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<HopDongDTO> danhSachHopDong;
    private JButton btnThem, btnSua, btnXoa,btnCapNhat;
    private JTextField txtMaNguoiThue, txtMaPhong, txtTienDatCoc;
    private JDateChooser txtNgayLap, txtNgayBatDau, txtNgayKetThuc;

    private HopDongBUS hopDongBUS = new HopDongBUS();

    public HopDongPanel() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin hợp đồng"));

        inputPanel.add(new JLabel("Mã Người Thuê:"));
        txtMaNguoiThue = new JTextField();
        inputPanel.add(txtMaNguoiThue);

        inputPanel.add(new JLabel("Mã Phòng:"));
        txtMaPhong = new JTextField();
        inputPanel.add(txtMaPhong);

        // Sử dụng JDateChooser cho Tháng/Năm
        inputPanel.add(new JLabel("Ngày Lập:"));
        txtNgayLap = new JDateChooser();
        txtNgayLap.setDateFormatString("dd/MM/yyyy");
        inputPanel.add(txtNgayLap);

        inputPanel.add(new JLabel("Ngày Bắt Đầu:"));
        txtNgayBatDau = new JDateChooser();
        txtNgayBatDau.setDateFormatString("dd/MM/yyyy");
        inputPanel.add(txtNgayBatDau);

        inputPanel.add(new JLabel("Ngày Kết Thúc:"));
        txtNgayKetThuc = new JDateChooser();
        txtNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        inputPanel.add(txtNgayKetThuc);

        inputPanel.add(new JLabel("Tiền Đặt Cọc:"));
        txtTienDatCoc = new JTextField();
        inputPanel.add(txtTienDatCoc);

        add(inputPanel, BorderLayout.NORTH);

        // Các cột của bảng
        String[] columns = {"ID", "Mã Người Thuê", "Mã Phòng", "Ngày Lập", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Tiền Đặt Cọc"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập Nhật");

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

        btnThem.addActionListener(e -> themHopDong());
        btnSua.addActionListener(e -> suaHopDong());
        btnXoa.addActionListener(e -> xoaHopDong());
        btnCapNhat.addActionListener(e -> loadData());
    }

    private void loadData() {
        danhSachHopDong = new ArrayList<>(hopDongBUS.getAllHopDong());
        capNhatTable();
    }

    private void capNhatTable() {
        tableModel.setRowCount(0);
        for (HopDongDTO hd : danhSachHopDong) {
            tableModel.addRow(new Object[]{
                    hd.getId(),
                    hd.getMaNguoiThue(),
                    hd.getMaPhong(),
                    hd.getNgayLap(),
                    hd.getNgayBatDau(),
                    hd.getNgayKetThuc(),
                    hd.getTienDatCoc(),
            });
        }
    }

    private void clearForm() {
        txtMaNguoiThue.setText("");
        txtMaPhong.setText("");
        txtNgayLap.setDate(null);
        txtNgayBatDau.setDate(null);
        txtNgayKetThuc.setDate(null);
        txtTienDatCoc.setText("");
    }

    private void themHopDong() {
        try {
            String maPhongText = txtMaPhong.getText();
            String maNguoiThueText = txtMaNguoiThue.getText();
            String tienDatCocText = txtTienDatCoc.getText();

            if (maPhongText.isEmpty() || maNguoiThueText.isEmpty() || tienDatCocText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }

            int maPhong = Integer.parseInt(maPhongText);
            int maNguoiThue = Integer.parseInt(maNguoiThueText);
            double tienDatCoc = Double.parseDouble(tienDatCocText);

            // Format dates
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ngayLap = (txtNgayLap.getDate() != null) ? sdf.format(txtNgayLap.getDate()) : null;
            String ngayBatDau = (txtNgayBatDau.getDate() != null) ? sdf.format(txtNgayBatDau.getDate()) : null;
            String ngayKetThuc = (txtNgayKetThuc.getDate() != null) ? sdf.format(txtNgayKetThuc.getDate()) : null;

            HopDongDTO hopdong = new HopDongDTO(0, maNguoiThue, maPhong, ngayLap, ngayBatDau, ngayKetThuc, tienDatCoc);

            if (hopDongBUS.addHopDong(hopdong)) {
                danhSachHopDong.add(hopdong);
                capNhatTable();
                JOptionPane.showMessageDialog(this, "Thêm Hợp Đồng Thành Công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm Hợp Đồng Thất Bại!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số!");
        }
    }

    private void suaHopDong() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Lấy thông tin hợp đồng hiện tại từ bảng
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int maNguoiThue = (int) tableModel.getValueAt(selectedRow, 1);
            int maPhong = (int) tableModel.getValueAt(selectedRow, 2);
            String ngayLap = (String) tableModel.getValueAt(selectedRow, 3);
            String ngayBatDau = (String) tableModel.getValueAt(selectedRow, 4);
            String ngayKetThuc = (String) tableModel.getValueAt(selectedRow, 5);
            double tienDatCoc = (double) tableModel.getValueAt(selectedRow, 6);

            // Điền thông tin vào các trường nhập liệu
            txtMaNguoiThue.setText(String.valueOf(maNguoiThue));
            txtMaPhong.setText(String.valueOf(maPhong));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Cập nhật ngày cho các trường JDateChooser
            txtNgayLap.setDate(sdf.parse(ngayLap));
            txtNgayBatDau.setDate(sdf.parse(ngayBatDau));
            txtNgayKetThuc.setDate(sdf.parse(ngayKetThuc));
            txtTienDatCoc.setText(String.valueOf(tienDatCoc));

            // Thực hiện cập nhật hợp đồng
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa hợp đồng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Cập nhật thông tin hợp đồng
                String maPhongText = txtMaPhong.getText();
                String maNguoiThueText = txtMaNguoiThue.getText();
                String tienDatCocText = txtTienDatCoc.getText();

                if (maPhongText.isEmpty() || maNguoiThueText.isEmpty() || tienDatCocText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                    return;
                }

                maPhong = Integer.parseInt(maPhongText);
                maNguoiThue = Integer.parseInt(maNguoiThueText);
                tienDatCoc = Double.parseDouble(tienDatCocText);

                // Format dates
                ngayLap = (txtNgayLap.getDate() != null) ? sdf.format(txtNgayLap.getDate()) : null;
                ngayBatDau = (txtNgayBatDau.getDate() != null) ? sdf.format(txtNgayBatDau.getDate()) : null;
                ngayKetThuc = (txtNgayKetThuc.getDate() != null) ? sdf.format(txtNgayKetThuc.getDate()) : null;

                HopDongDTO hopdong = new HopDongDTO(id, maNguoiThue, maPhong, ngayLap, ngayBatDau, ngayKetThuc, tienDatCoc);

                if (hopDongBUS.updateHopDong(hopdong)) {
                    // Cập nhật lại danh sách hợp đồng và bảng
                    danhSachHopDong.set(selectedRow, hopdong);
                    capNhatTable();
                    JOptionPane.showMessageDialog(this, "Sửa Hợp Đồng Thành Công!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa Hợp Đồng Thất Bại!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void xoaHopDong() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            HopDongDTO hopdong = danhSachHopDong.get(selectedRow);
            if (hopDongBUS.xoaHopDong(hopdong.getId())) {
                danhSachHopDong.remove(selectedRow);
                capNhatTable();
                JOptionPane.showMessageDialog(this, "Xóa Hợp Đồng Thành Công!");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}
