package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.List;

import BUS.KhachThueBUS;
import BUS.PhongBUS;
import DTO.KhachThueDTO;
import DTO.PhongDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;



public class KhachPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private KhachThueBUS khachbus;
    private PhongBUS phongBUS;

    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete;

    public KhachPanel() {
        khachbus = new KhachThueBUS();
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

        setLayout(new BorderLayout(10, 10)); // Sử dụng BorderLayout và thêm khoảng cách giữa các phần tử
        JPanel topPanelUP = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        // Thêm chiều rộng cho topPanel
        topPanelUP.setPreferredSize(new Dimension(200, 100));
        topPanelUP.setBackground(new Color(221, 156, 43));

        JLabel lblHome = new JLabel("<html><div style='text-align: center;margin-top: 18px; color: #fff;'>Trang Chủ</div></html>");
        lblHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHome.setForeground(Color.WHITE); // Màu chữ trắng cho label

        topPanel.add(lblHome, BorderLayout.CENTER);
        add(topPanelUP, BorderLayout.NORTH);

        topPanel.add(lblSearch, BorderLayout.WEST);
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng khách thuê
        String[] columns = {"ID", "Họ tên", "SĐT", "CCCD", "Phòng", "Ngày thuê"};
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
        List<KhachThueDTO> list = khachbus.getAllKhachThue();
        for (KhachThueDTO k : list) {
            model.addRow(new Object[] {
                    k.getId(), k.getHoTen(), k.getSoDienThoai(),
                    k.getCccd(), k.getPhongID(), k.getNgayThue()
            });
        }
    }

    private void searchCustomer() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        model.setRowCount(0);
        List<KhachThueDTO> list = khachbus.getAllKhachThue();
        for (KhachThueDTO k : list) {
            if (k.getHoTen().toLowerCase().contains(keyword)
                    || k.getSoDienThoai().contains(keyword)
                    || k.getCccd().contains(keyword)) {
                model.addRow(new Object[] {
                        k.getId(), k.getHoTen(), k.getSoDienThoai(),
                        k.getCccd(), k.getPhongID(), k.getNgayThue()
                });
            }
        }
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
            if (txtHoTen.getText().trim().isEmpty() || txtSdt.getText().trim().isEmpty() || txtCccd.getText().trim().isEmpty()) {
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
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
            return;
        }

        // Lấy dữ liệu từ hàng được chọn
        int id = (int) model.getValueAt(selectedRow, 0);
        String hoTen = (String) model.getValueAt(selectedRow, 1);
        String sdt = (String) model.getValueAt(selectedRow, 2);
        String cccd = (String) model.getValueAt(selectedRow, 3);
        int phongId = (int) model.getValueAt(selectedRow, 4);
        String ngayThue = (String) model.getValueAt(selectedRow, 5);

        // Các trường nhập liệu
        JTextField txtHoTen = new JTextField(hoTen, 20);
        JTextField txtSdt = new JTextField(sdt, 20);
        JTextField txtCccd = new JTextField(cccd, 20);
        JTextField txtPhong = new JTextField(String.valueOf(phongId));
        txtPhong.setEnabled(false);

        // Tạo JDateChooser cho ngày thuê
        JDateChooser dateChooser = new JDateChooser();
        try {
            // Nếu ngày thuê có sẵn, set giá trị cho JDateChooser
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(ngayThue); // Chuyển đổi từ String thành Date
            dateChooser.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Tạo panel với GridLayout
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Họ tên:"));
        panel.add(txtHoTen);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSdt);
        panel.add(new JLabel("CCCD:"));
        panel.add(txtCccd);
        panel.add(new JLabel("Phòng:"));
        panel.add(txtPhong);
        panel.add(new JLabel("Ngày Thuê:"));
        panel.add(dateChooser);

        int result = JOptionPane.showConfirmDialog(this, panel, "Chỉnh sửa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Lấy ngày thuê từ JDateChooser
                Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày thuê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Nếu ngày thuê không được chọn, thoát ra
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String ngayThueMoi = sdf.format(selectedDate); // Chuyển lại thành String

                // Kiểm tra và xử lý ngày trả (ngày trả có thể là null)
                String ngayTra = ""; // Nếu không có ngày trả, set giá trị rỗng hoặc null
                if (ngayTra.isEmpty()) {
                    ngayTra = null; // Nếu ngày trả không có, để nó là null
                }

                // Cập nhật thông tin khách hàng
                KhachThueDTO updated = new KhachThueDTO(
                        id,
                        txtHoTen.getText().trim(),
                        txtSdt.getText().trim(),
                        txtCccd.getText().trim(),
                        phongId,
                        ngayThueMoi,
                        ngayTra // Ngày trả có thể null nếu không có giá trị
                );
                if (khachbus.updateKhachThue(updated)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadData();
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
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(selectedRow, 0);
            if(khachbus.deleteKhachThue(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
}
