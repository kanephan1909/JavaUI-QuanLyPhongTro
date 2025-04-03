package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import DTO.KhachThueDTO;
import DTO.PhongDTO;

public class KhachPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachThueBUS khachController;
    private PhongBUS phongBUS;

    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;

    public KhachPanel() {
        khachController = new KhachThueBUS();
        phongBUS = new PhongBUS();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblSearch = new JLabel("Tìm kiếm khách:");
        txtSearch = new JTextField();
        btnSearch = new JButton("Tìm kiếm");

        topPanel.add(lblSearch, BorderLayout.WEST);
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng khách thuê
        String[] columns = {"ID", "Họ tên", "SĐT", "CCCD", "Phòng"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> showAddForm());
        btnEdit.addActionListener(e -> showEditForm());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnSearch.addActionListener(e -> searchCustomer());
    }

    private void loadData() {
        model.setRowCount(0);
        List<KhachThueDTO> list = khachController.getAllKhachThue();
        for (KhachThueDTO k : list) {
            model.addRow(new Object[] { k.getId(), k.getHoTen(), k.getSoDienThoai(), k.getCccd(), k.getPhongID() });
        }
    }

    private void searchCustomer() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if(keyword.isEmpty()){
            loadData();
            return;
        }
        model.setRowCount(0);
        List<KhachThueDTO> list = khachController.getAllKhachThue();
        for (KhachThueDTO k : list) {
            if(k.getHoTen().toLowerCase().contains(keyword) ||
                    k.getSoDienThoai().contains(keyword) ||
                    k.getCccd().contains(keyword)) {
                model.addRow(new Object[] { k.getId(), k.getHoTen(), k.getSoDienThoai(), k.getCccd(), k.getPhongID() });
            }
        }
    }

        private void showEditForm() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
        }


        private void showAddForm() {
        List<PhongDTO> dsPhongTrong = phongBUS.getPhongTrong();

        JTextField txtHoTen = new JTextField(20);
        JTextField txtSdt = new JTextField(20);
        JTextField txtCccd = new JTextField(20);

        DefaultComboBoxModel<String> modelPhong = new DefaultComboBoxModel<>();
        for (PhongDTO p : dsPhongTrong) {
            modelPhong.addElement(p.getMaPhong() + " - " + p.getTenPhong());
        }
        JComboBox<String> cboPhong = new JComboBox<>(modelPhong);

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
            if (txtHoTen.getText().trim().isEmpty() ||
                    txtSdt.getText().trim().isEmpty() ||
                    txtCccd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String hoTen = txtHoTen.getText().trim();
                String sdt = txtSdt.getText().trim();
                String cccd = txtCccd.getText().trim();
                String selected = (String) cboPhong.getSelectedItem();
                int phongId = Integer.parseInt(selected.split(" - ")[0]);

                KhachThueDTO khachMoi = new KhachThueDTO(hoTen, sdt, cccd, phongId);
                if (khachController.addKhachThue(khachMoi)) {
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

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(selectedRow, 0);
            if(khachController.deleteKhachThue(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}
