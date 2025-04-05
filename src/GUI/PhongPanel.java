package GUI;

import BUS.PhongBUS;
import DTO.PhongDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class PhongPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;
    private ArrayList<PhongDTO> danhSachPhong;
    private PhongBUS phongBUS;

    public PhongPanel() {
        phongBUS = new PhongBUS();
        danhSachPhong = new ArrayList<>(phongBUS.getAllPhong());
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadData(danhSachPhong);
    }

    private void initComponents() {
        // Top panel: thanh tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        txtSearch = new JTextField();
        btnSearch = new JButton("Tìm kiếm");
        topPanel.add(new JLabel("Nhập tên phòng:"), BorderLayout.WEST);
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center panel: bảng hiển thị
        String[] columns = { "ID", "Mã Phòng", "Tên phòng","Loại Phòng", "Giá thuê", "Trạng thái" };
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
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Bottom panel: các nút chức năng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnSearch.addActionListener(e -> searchRoom());
        btnAdd.addActionListener(e -> addRoom());
        btnEdit.addActionListener(e -> editRoom());
        btnDelete.addActionListener(e -> deleteRoom());
    }

    private void loadData(List<PhongDTO> ds) {
        tableModel.setRowCount(0);
        for (PhongDTO p : ds) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getMaPhong(),
                    p.getTenPhong(),
                    p.getLoaiPhong(),
                    p.getGiaPhong(),
                    p.getTinhTrang()
            });
        }
    }

    private void searchRoom() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadData(phongBUS.getAllPhong());
            return;
        }
        ArrayList<PhongDTO> filtered = new ArrayList<>();
        for (PhongDTO p : phongBUS.getAllPhong()) {
            if (p.getTenPhong().toLowerCase().contains(keyword)) {
                filtered.add(p);
            }
        }
        loadData(filtered);
    }

    private void addRoom() {
        JTextField txtMaPhong = new JTextField();
        JTextField txtTenPhong = new JTextField();
        JTextField txtGiaThue = new JTextField();
        JTextField txtLoaiPhong = new JTextField();
        JComboBox<String> cmbTrangThai = new JComboBox<>(new String[]{"Trống", "Đã thuê"});

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Mã phòng:"));
        panel.add(txtMaPhong);
        panel.add(new JLabel("Tên phòng:"));
        panel.add(txtTenPhong);
        panel.add(new JLabel("Loại phòng:"));
        panel.add(txtLoaiPhong);
        panel.add(new JLabel("Giá thuê:"));
        panel.add(txtGiaThue);
        panel.add(new JLabel("Trạng thái:"));
        panel.add(cmbTrangThai);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm phòng mới",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String maPhong = txtMaPhong.getText().trim();
            String tenPhong = txtTenPhong.getText().trim();
            String giaThueStr = txtGiaThue.getText().trim();

            if (maPhong.isEmpty() || tenPhong.isEmpty() || giaThueStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            try {
                float giaThue = Float.parseFloat(giaThueStr);
                String trangThai = (String) cmbTrangThai.getSelectedItem();
                PhongDTO newPhong = new PhongDTO(0, maPhong, tenPhong, "", 0, giaThue, "", trangThai, 1); // giả sử KhuVucID = 1
                if (phongBUS.addPhong(newPhong)) {
                    JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");
                    danhSachPhong = new ArrayList<>(phongBUS.getAllPhong());
                    loadData(danhSachPhong);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm phòng thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá thuê phải là số!");
            }
        }
    }

    private void editRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần sửa!");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String maPhong = tableModel.getValueAt(selectedRow, 1).toString();
        String tenPhongOld = tableModel.getValueAt(selectedRow, 2).toString();
        float giaThueOld = Float.parseFloat(tableModel.getValueAt(selectedRow, 3).toString());
        String trangThaiOld = tableModel.getValueAt(selectedRow, 4).toString();

        JTextField txtTenPhong = new JTextField(tenPhongOld);
        JTextField txtGiaThue = new JTextField(String.valueOf(giaThueOld));
        JComboBox<String> cmbTrangThai = new JComboBox<>(new String[]{"Trống", "Đã thuê"});
        cmbTrangThai.setSelectedItem(trangThaiOld);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Tên phòng:"));
        panel.add(txtTenPhong);
        panel.add(new JLabel("Giá thuê:"));
        panel.add(txtGiaThue);
        panel.add(new JLabel("Trạng thái:"));
        panel.add(cmbTrangThai);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa thông tin phòng",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String tenPhong = txtTenPhong.getText().trim();
            String giaThueStr = txtGiaThue.getText().trim();
            if (tenPhong.isEmpty() || giaThueStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Thông tin không được để trống!");
                return;
            }
            try {
                float giaThue = Float.parseFloat(giaThueStr);
                String trangThai = (String) cmbTrangThai.getSelectedItem();
                PhongDTO editedPhong = new PhongDTO(id, maPhong, tenPhong, "", 0, giaThue, "", trangThai, 1); // giữ nguyên KhuVucID
                if (phongBUS.updatePhong(editedPhong)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thành công!");
                    danhSachPhong = new ArrayList<>(phongBUS.getAllPhong());
                    loadData(danhSachPhong);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá thuê phải là số!");
            }
        }
    }

    private void deleteRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa!");
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (phongBUS.deletePhong(id)) {
                JOptionPane.showMessageDialog(this, "Xóa phòng thành công!");
                danhSachPhong = new ArrayList<>(phongBUS.getAllPhong());
                loadData(danhSachPhong);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phòng thất bại!");
            }
        }
    }
}
