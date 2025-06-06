package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import BUS.KhachThueBUS;
import BUS.PhongBUS;
import DTO.KhachThueDTO;
import DTO.KhuVucDTO;
import DTO.PhongDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class KhachPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private KhachThueBUS khachbus;
    private PhongBUS phongBUS;
    private List<PhongDTO> phongList = new ArrayList<>();


    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;

    public KhachPanel() {
        khachbus = new KhachThueBUS();
        phongBUS = new PhongBUS();
        setLayout(new BorderLayout(0, 0)); // Dùng BorderLayout cho toàn bộ panel
        setBackground(Color.WHITE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Tạo panel tìm kiếm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");

        topPanel.add(new JLabel("Nhập tên khách:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);



//        JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        topPanel2.setPreferredSize(new Dimension(200, 100));
//        topPanel2.setBackground(new Color(22, 26, 83));
//        JLabel lblKhachThue = new JLabel("<html><div style='text-align: center;margin-top: 18px; color: #fff;'>Khách Thuê</div></html>");
//        lblKhachThue.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        lblKhachThue.setForeground(Color.WHITE); // Màu chữ trắng cho label
//
//        topPanel2.add(lblKhachThue, BorderLayout.CENTER);
//        add(topPanel2, BorderLayout.NORTH);


        // Center panel: bảng hiển thị
        String[] columns = {"ID", "Họ tên", "SĐT", "CCCD", "Mã Phòng", "Ngày thuê", "Ngày Trả"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Ẩn cột ID
//        table.getColumnModel().getColumn(0).setMinWidth(0);
//        table.getColumnModel().getColumn(0).setMaxWidth(0);
//        table.getColumnModel().getColumn(0).setWidth(0);

        // Bottom panel: các nút chức năng
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Căn giữa panel

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");

        // Đặt màu nền cho các nút
        Color customColor = new Color(20, 25, 95);
        btnAdd.setBackground(customColor);
        btnEdit.setBackground(customColor);
        btnDelete.setBackground(customColor);
        btnSearch.setBackground(customColor);

        // Đặt màu chữ cho các nút
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setForeground(Color.WHITE);
        btnDelete.setForeground(Color.WHITE);
        btnSearch.setForeground(Color.WHITE);

        // Đặt kích thước cho các nút
        Dimension buttonSize = new Dimension(80, 30);  // Thay đổi kích thước của nút
        btnAdd.setPreferredSize(buttonSize);
        btnEdit.setPreferredSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);

        // Thêm các nút vào bottomPanel
        bottomPanel.add(btnAdd);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 0))); // Thêm khoảng cách giữa các nút
        bottomPanel.add(btnEdit);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        bottomPanel.add(btnDelete);

        // Thêm bottomPanel vào phần Nam của BorderLayout
        add(bottomPanel, BorderLayout.SOUTH);

        // Các action listener
        btnSearch.addActionListener(e -> timKhachThue());
        btnAdd.addActionListener(e -> AddKhachThue());
        btnEdit.addActionListener(e -> showEditForm());
        btnDelete.addActionListener(e -> deleteCustomer());
    }

    private void loadData() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Lấy lại dữ liệu từ cơ sở dữ liệu
        List<KhachThueDTO> list = khachbus.getAllKhachThue();
        for (KhachThueDTO khach : list) {
            // Lấy MaPhong từ PhongID
            String maPhong = khach.getPhongID();

            // Thêm dữ liệu vào bảng
            tableModel.addRow(new Object[]{
                    khach.getId(),
                    khach.getHoTen(),
                    khach.getSoDienThoai(),
                    khach.getCccd(),
                    maPhong, // Thay PhongID bằng MaPhong
                    khach.getNgayThue(),
                    khach.getNgayTra()
            });
        }
    }



    private void timKhachThue() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        List<KhachThueDTO> list = khachbus.getAllKhachThue();
        for (KhachThueDTO k : list) {
            if (k.getHoTen().toLowerCase().contains(keyword)
                    || k.getSoDienThoai().contains(keyword)
                    || k.getCccd().contains(keyword)) {
                tableModel.addRow(new Object[] {
                        k.getId(), k.getHoTen(), k.getSoDienThoai(),
                        k.getCccd(), k.getPhongID(), k.getNgayThue()
                });
            }
        }
    }

    private void AddKhachThue() {
        List<PhongDTO> dsPhongTrong = phongBUS.getPhongTrong();

        JTextField txtHoTen = new JTextField(20);
        JTextField txtSdt = new JTextField(20);
        JTextField txtCccd = new JTextField(20);

        DefaultComboBoxModel<String> modelPhong = new DefaultComboBoxModel<>();
        for (PhongDTO p : dsPhongTrong) {
            modelPhong.addElement(p.getMaPhong() + " - " + p.getTenPhong());
        }
        JComboBox<String> cboPhong = new JComboBox<>(modelPhong);
        if (dsPhongTrong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có phòng trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSdt);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCccd);
        panel.add(new JLabel("Chọn phòng:"));
        panel.add(cboPhong);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (txtHoTen.getText().trim().isEmpty() || txtSdt.getText().trim().isEmpty() || txtCccd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String hoTen = txtHoTen.getText().trim();
                String sdt = txtSdt.getText().trim();
                String cccd = txtCccd.getText().trim();
                String selected = (String) cboPhong.getSelectedItem();
                String phongId = selected.split(" - ")[0];

                KhachThueDTO khachMoi = new KhachThueDTO(hoTen, sdt, cccd, phongId);
                if (khachbus.addKhachThue(khachMoi)) {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditForm() {
        List<PhongDTO> dsPhongTrong = phongBUS.getPhongTrong();

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
            return;
        }

        // Lấy dữ liệu từ hàng được chọn
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String hoTen = (String) tableModel.getValueAt(selectedRow, 1);
        String sdt = (String) tableModel.getValueAt(selectedRow, 2);
        String cccd = (String) tableModel.getValueAt(selectedRow, 3);
        String phongId = (String) tableModel.getValueAt(selectedRow, 4);  // Lấy phòng hiện tại
        String ngayThue = (String) tableModel.getValueAt(selectedRow, 5);
        String ngayTra = (String) tableModel.getValueAt(selectedRow, 6);

        // Các trường nhập liệu
        JTextField txtHoTen = new JTextField(hoTen, 20);
        JTextField txtSdt = new JTextField(sdt, 20);
        JTextField txtCccd = new JTextField(cccd, 20);

        DefaultComboBoxModel<String> modelPhong = new DefaultComboBoxModel<>();
        for (PhongDTO p : dsPhongTrong) {
            modelPhong.addElement(p.getMaPhong() + " - " + p.getTenPhong());
        }
        JComboBox<String> cboPhong = new JComboBox<>(modelPhong);

        // Chọn phòng trong JComboBox dựa trên `phongId`
        for (int i = 0; i < dsPhongTrong.size(); i++) {
            if (dsPhongTrong.get(i).getMaPhong().equals(phongId)) {
                cboPhong.setSelectedIndex(i); // Chọn phòng đúng từ danh sách
                break;
            }
        }

        // Tạo JDateChooser cho ngày thuê
        JDateChooser dateChooserNgayThue = new JDateChooser();
        JDateChooser dateChooserNgayTra = new JDateChooser();

        try {
            // Nếu ngày thuê có sẵn, set giá trị cho JDateChooser
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNgayThue = sdf.parse(ngayThue);
            dateChooserNgayThue.setDate(dateNgayThue);

            // Nếu có ngày trả, set giá trị cho JDateChooser ngày trả
            if (ngayTra != null && !ngayTra.isEmpty()) {
                Date dateNgayTra = sdf.parse(ngayTra);
                dateChooserNgayTra.setDate(dateNgayTra);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Tạo panel với GridLayout
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSdt);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCccd);
        panel.add(new JLabel("Phòng:"));
        panel.add(cboPhong);
        panel.add(new JLabel("Ngày Thuê:"));
        panel.add(dateChooserNgayThue);
        panel.add(new JLabel("Ngày Trả:"));
        panel.add(dateChooserNgayTra);

        int result = JOptionPane.showConfirmDialog(this, panel, "Chỉnh sửa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Lấy ngày thuê từ JDateChooser
                Date selectedNgayThue = dateChooserNgayThue.getDate();
                if (selectedNgayThue == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày thuê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String ngayThueMoi = sdf.format(selectedNgayThue);

                // Kiểm tra và xử lý ngày trả
                Date selectedNgayTra = dateChooserNgayTra.getDate();
                String ngayTraMoi = null;
                if (selectedNgayTra != null) {
                    ngayTraMoi = sdf.format(selectedNgayTra); // Ngày trả có thể là null
                }

                // Lấy phòng mới từ JComboBox
                String phongIdMoi = ((String) cboPhong.getSelectedItem()).split(" - ")[0]; // Lấy MaPhong mới

                // Cập nhật thông tin khách hàng
                KhachThueDTO updated = new KhachThueDTO(
                        id,
                        txtHoTen.getText().trim(),
                        txtSdt.getText().trim(),
                        txtCccd.getText().trim(),
                        phongIdMoi, // Cập nhật PhongID
                        ngayThueMoi,
                        ngayTraMoi
                );

                if (khachbus.updateKhachThue(updated)) {
                    // Cập nhật dữ liệu trong bảng mà không cần load lại từ cơ sở dữ liệu
                    tableModel.setValueAt(txtHoTen.getText().trim(), selectedRow, 1);
                    tableModel.setValueAt(txtSdt.getText().trim(), selectedRow, 2);
                    tableModel.setValueAt(txtCccd.getText().trim(), selectedRow, 3);
                    tableModel.setValueAt(phongIdMoi, selectedRow, 4); // Cập nhật PhongID
                    tableModel.setValueAt(ngayThueMoi, selectedRow, 5);
                    tableModel.setValueAt(ngayTraMoi, selectedRow, 6);

                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            if (khachbus.deleteKhachThue(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}