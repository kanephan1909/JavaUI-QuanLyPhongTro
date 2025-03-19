package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import controllers.KhachThueController;
import controllers.PhongController;
import models.KhachThue;
import models.Phong;

public class KhachPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachThueController khachController;
    private PhongController phongController;
    
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    
    public KhachPanel() {
        khachController = new KhachThueController();
        phongController = new PhongController();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        // Top panel: thanh tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblSearch = new JLabel("Tìm kiếm khách hàng:");
        txtSearch = new JTextField();
        btnSearch = new JButton("Tìm kiếm");
        topPanel.add(lblSearch, BorderLayout.WEST);
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel: bảng hiển thị dữ liệu khách hàng
        String[] columns = {"ID", "Họ tên", "SĐT", "CCCD", "Phòng"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
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
        btnAdd.addActionListener(e -> showAddForm());
        btnEdit.addActionListener(e -> showEditForm());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnSearch.addActionListener(e -> searchCustomer());
    }
    
    private void loadData() {
        model.setRowCount(0);
        ArrayList<KhachThue> list = khachController.getDanhSachKhach();
        for (KhachThue k : list) {
            model.addRow(new Object[] { k.getId(), k.getHoTen(), k.getSdt(), k.getCccd(), k.getPhongId() });
        }
    }
    
    private void searchCustomer() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if(keyword.isEmpty()){
            loadData();
            return;
        }
        model.setRowCount(0);
        ArrayList<KhachThue> list = khachController.getDanhSachKhach();
        for (KhachThue k : list) {
            if(k.getHoTen().toLowerCase().contains(keyword) ||
               k.getSdt().contains(keyword) ||
               k.getCccd().contains(keyword)) {
                model.addRow(new Object[] { k.getId(), k.getHoTen(), k.getSdt(), k.getCccd(), k.getPhongId() });
            }
        }
    }
    
    // Hiển thị form thêm khách (có thể mở một dialog riêng hoặc reuse code hiện có)
    private void showAddForm() {
        // Lấy danh sách phòng trống
        ArrayList<Phong> dsPhongTrong = phongController.getPhongTrong();
        System.out.println("Số lượng phòng trống: " + dsPhongTrong.size());
        for (Phong p : dsPhongTrong) {
            System.out.println("Phòng trống: " + p.getMaPhong() + " - " + p.getTenPhong());
        }
//        if (dsPhongTrong.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không có phòng trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        // Tạo các trường nhập liệu
        JTextField txtHoTen = new JTextField(20);
        JTextField txtSdt = new JTextField(20);
        JTextField txtCccd = new JTextField(20);
        
        DefaultComboBoxModel<String> modelPhong = new DefaultComboBoxModel<>();
        for (Phong p : dsPhongTrong) {
            modelPhong.addElement(p.getMaPhong() + " - " + p.getTenPhong() + " - " + p.getGiaThue() + "đ");
        }
        JComboBox<String> cboPhong = new JComboBox<>(modelPhong);
        
        // Xây dựng panel form
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
            // Kiểm tra thông tin
            if (txtHoTen.getText().trim().isEmpty() ||
                txtSdt.getText().trim().isEmpty() ||
                txtCccd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String hoTen = txtHoTen.getText().trim();
                String sdt = txtSdt.getText().trim();
                String Cccd = txtCccd.getText().trim();
                String selected = (String) cboPhong.getSelectedItem();
                int phongId = Integer.parseInt(selected.split(" - ")[0]);
                
                KhachThue khachMoi = new KhachThue(hoTen, sdt, Cccd, phongId);
                if (khachController.themKhach(khachMoi)) {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Hiển thị form sửa khách (nếu khách chọn một dòng)
    private void showEditForm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Lấy thông tin khách hàng được chọn
        int id = (int) model.getValueAt(selectedRow, 0);
        String hoTenOld = (String) model.getValueAt(selectedRow, 1);
        String sdtOld = (String) model.getValueAt(selectedRow, 2);
        String CccdOld = (String) model.getValueAt(selectedRow, 3);
        int currentPhongId = (int) model.getValueAt(selectedRow, 4);
        
        // Tạo các trường nhập liệu với giá trị mặc định
        JTextField txtHoTen = new JTextField(hoTenOld, 20);
        JTextField txtSdt = new JTextField(sdtOld, 20);
        JTextField txtCccd = new JTextField(CccdOld, 20);
        
        ArrayList<Phong> dsPhongTrong = phongController.getPhongTrong();
        DefaultComboBoxModel<String> modelPhong = new DefaultComboBoxModel<>();
        String currentRoomStr = "";
        boolean found = false;
        for (Phong p : dsPhongTrong) {
            String roomText = p.getMaPhong() + " - " + p.getTenPhong() + " - " + p.getGiaThue() + "đ";
            if (p.getMaPhong() == currentPhongId) {
                currentRoomStr = roomText;
                found = true;
            }
            modelPhong.addElement(roomText);
        }
        if (!found) { 
            currentRoomStr = currentPhongId + " - [Không tìm thấy thông tin phòng]";
            modelPhong.insertElementAt(currentRoomStr, 0);
        }
        JComboBox<String> cboPhong = new JComboBox<>(modelPhong);
        // Đặt giá trị mặc định cho combobox thành phòng hiện tại
        cboPhong.setSelectedItem(currentRoomStr);
        
        // Xây dựng panel form
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSdt);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCccd);
        panel.add(new JLabel("Chọn phòng:"));
        panel.add(cboPhong);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa thông tin khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (txtHoTen.getText().trim().isEmpty() ||
                txtSdt.getText().trim().isEmpty() ||
                txtCccd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String hoTen = txtHoTen.getText().trim();
                String sdt = txtSdt.getText().trim();
                String Cccd = txtCccd.getText().trim();
                String selected = (String) cboPhong.getSelectedItem();
                int newPhongId = Integer.parseInt(selected.split(" - ")[0]);
                
                KhachThue edited = new KhachThue(hoTen, sdt, Cccd, newPhongId);
                edited.setId(id); // Giữ nguyên id khách cũ
                if (khachController.suaKhach(edited, currentPhongId)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Xử lý xóa khách hàng
    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(selectedRow, 0);
            int phongId = (int) model.getValueAt(selectedRow, 4);
            if(khachController.xoaKhach(id, phongId)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }
        }
    }
}
