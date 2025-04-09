package GUI;

import BUS.PhongBUS;
import DTO.PhongDTO;
import DAL.KhuVucDAL;
import DTO.KhuVucDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PhongPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnSearch, btnThem, btnSua, btnXoa, btnCapNhat;
    private List<PhongDTO> danhSachPhong;
    private PhongBUS phongBUS;
    private List<KhuVucDTO> khuVucList;  // Danh sách khu vực

    public PhongPanel() {
        phongBUS = new PhongBUS();
        danhSachPhong = phongBUS.getAllPhong();
        khuVucList = KhuVucDAL.getAllKhuVuc();  // Lấy danh sách khu vực
        setLayout(new BorderLayout(0, 0));
        initComponents();
        loadData(danhSachPhong);
    }

    private void initComponents() {
        // Top panel: thanh tìm kiếm
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Sử dụng BoxLayout cho phần tìm kiếm
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");

        topPanel.add(new JLabel("Nhập tên phòng:"));
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Thêm khoảng cách giữa label và trường nhập liệu
        topPanel.add(txtSearch);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: bảng hiển thị
        String[] columns = {"Mã Phòng", "Tên phòng", "Loại Phòng", "Giá thuê", "Trạng thái", "Khu Vực" };
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
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Dùng BoxLayout cho các nút
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnCapNhat = new JButton("Cập Nhật");

        // Cải thiện màu sắc cho các nút
        Color customColor = new Color(20, 25, 95);
        btnThem.setBackground(customColor);
        btnSua.setBackground(customColor);
        btnXoa.setBackground(customColor);
        btnSearch.setBackground(customColor);
        btnCapNhat.setBackground(customColor);

        // Đặt màu chữ cho các nút
        btnThem.setForeground(Color.WHITE);
        btnSua.setForeground(Color.WHITE);
        btnXoa.setForeground(Color.WHITE);
        btnSearch.setForeground(Color.WHITE);
        btnCapNhat.setForeground(Color.WHITE);

        // Đặt kích thước cho các nút
        Dimension buttonSize = new Dimension(100, 30);  // Thay đổi kích thước của nút
        btnThem.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnSearch.setPreferredSize(buttonSize);
        btnCapNhat.setPreferredSize(buttonSize);

        // Thêm các nút vào bottomPanel
        bottomPanel.add(btnThem);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 0))); // Thêm khoảng cách giữa các nút
        bottomPanel.add(btnSua);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        bottomPanel.add(btnXoa);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        bottomPanel.add(btnCapNhat);

        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnSearch.addActionListener(e -> searchRoom());
        btnThem.addActionListener(e -> addRoom());
        btnSua.addActionListener(e -> editRoom());
        btnXoa.addActionListener(e -> deleteRoom());
        btnCapNhat.addActionListener(e -> loadData(danhSachPhong));
    }

    private void loadData(List<PhongDTO> ds) {
        tableModel.setRowCount(0);  // Xóa tất cả các dòng hiện có trong bảng
        for (PhongDTO p : ds) {
            String khuVucTen = "";
            for (KhuVucDTO khuVuc : khuVucList) {
                if (khuVuc.getId().equals(p.getKhuVucID())) {  // Sửa từ '==' thành '.equals()' để so sánh giá trị chuỗi
                    khuVucTen = khuVuc.getTenKhuVuc();
                    break;
                }
            }

            // Thêm dòng vào bảng với tên khu vực thay vì KhuVucID
            tableModel.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getTenPhong(),
                    p.getLoaiPhong(),
                    p.getGiaPhong(),
                    p.getTinhTrang(),
                    khuVucTen  // Hiển thị tên khu vực thay vì ID
            });
        }
    }



    private void updateTableData() {
        danhSachPhong = phongBUS.getAllPhong();
        loadData(danhSachPhong);
    }

    private void searchRoom() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            loadData(danhSachPhong);  // Dùng danh sách phòng hiện tại thay vì gọi lại phongBUS.getAllPhong()
            return;
        }

        List<PhongDTO> filtered = new ArrayList<>();
        for (PhongDTO p : danhSachPhong) {
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


        updateTableData();
        // JComboBox để chọn khu vực
        JComboBox<String> cmbKhuVuc = new JComboBox<>();
        for (KhuVucDTO khuVuc : khuVucList) {
            cmbKhuVuc.addItem(khuVuc.getTenKhuVuc());
        }

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));  // Tăng số dòng lên 7
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
        panel.add(new JLabel("Khu vực:"));  // Label cho khu vực
        panel.add(cmbKhuVuc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm phòng mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String maPhong = txtMaPhong.getText().trim();
            String tenPhong = txtTenPhong.getText().trim();
            String loaiPhong = txtLoaiPhong.getText().trim();
            String giaThueStr = txtGiaThue.getText().trim();
            String khuVucSelected = (String) cmbKhuVuc.getSelectedItem();  // Khu vực đã chọn

            if (maPhong.isEmpty() || tenPhong.isEmpty() || loaiPhong.isEmpty() || giaThueStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            try {
                // Chuyển đổi giaThueStr sang float
                float giaThue = Float.parseFloat(giaThueStr);
                String trangThai = (String) cmbTrangThai.getSelectedItem();
                String khuVucID = null;  // Chuyển khuVucID thành String

                for (KhuVucDTO khuVuc : khuVucList) {
                    if (khuVuc.getTenKhuVuc().equals(khuVucSelected)) {
                        khuVucID = khuVuc.getId();  // Gán id từ khuVuc
                        break;
                    }
                }

                // Tạo đối tượng PhongDTO với khuVucID kiểu String
                PhongDTO newPhong = new PhongDTO(maPhong, tenPhong, loaiPhong, 0, giaThue, "", trangThai, khuVucID);
                if (phongBUS.addPhong(newPhong)) {
                    JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");
                    updateTableData();  // Cập nhật bảng
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

        // Lấy thông tin phòng từ bảng
        String maPhong = tableModel.getValueAt(selectedRow, 0).toString();  // Mã phòng
        String tenPhongOld = tableModel.getValueAt(selectedRow, 1).toString();
        String loaiPhongOld = tableModel.getValueAt(selectedRow, 2).toString();
        String giaThueOldStr = tableModel.getValueAt(selectedRow, 3).toString();
        String trangThaiOld = tableModel.getValueAt(selectedRow, 4).toString();

        // Lấy khu vực (Tên khu vực) từ bảng (Cột 6 là tên khu vực, không phải ID)
        String khuVucTenOld = tableModel.getValueAt(selectedRow, 5).toString();

        // Kiểm tra giá thuê có hợp lệ không
        float giaThueOld = 0;
        try {
            giaThueOld = Float.parseFloat(giaThueOldStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá thuê không hợp lệ!");
            return;
        }

        // Lấy danh sách khu vực
        List<KhuVucDTO> khuVucList = KhuVucDAL.getAllKhuVuc();  // Lấy danh sách khu vực từ database

        // Tạo các trường nhập liệu với giá trị mặc định
        JTextField txtMaPhong = new JTextField(maPhong);  // Mã phòng (không cho chỉnh sửa)
        txtMaPhong.setEditable(false);
        JTextField txtTenPhong = new JTextField(tenPhongOld);
        JTextField txtGiaThue = new JTextField(String.valueOf(giaThueOld));
        JTextField txtLoaiPhong = new JTextField(loaiPhongOld);
        JComboBox<String> cmbTrangThai = new JComboBox<>(new String[]{"Trống", "Đã thuê"});
        cmbTrangThai.setSelectedItem(trangThaiOld);

        JComboBox<String> cmbKhuVuc = new JComboBox<>();
        for (KhuVucDTO khuVuc : khuVucList) {
            cmbKhuVuc.addItem(khuVuc.getTenKhuVuc());
        }

        // Đặt khu vực đã chọn trong combobox
        for (int i = 0; i < khuVucList.size(); i++) {
            if (khuVucList.get(i).getTenKhuVuc().equals(khuVucTenOld)) {
                cmbKhuVuc.setSelectedIndex(i);
                break;
            }
        }

        // Tạo form nhập liệu cho người dùng sửa phòng
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.add(new JLabel("Mã phòng:"));
        panel.add(txtMaPhong);  // Hiển thị Mã Phòng cho người dùng
        panel.add(new JLabel("Tên phòng:"));
        panel.add(txtTenPhong);
        panel.add(new JLabel("Giá thuê:"));
        panel.add(txtGiaThue);
        panel.add(new JLabel("Loại Phòng:"));
        panel.add(txtLoaiPhong);
        panel.add(new JLabel("Trạng thái:"));
        panel.add(cmbTrangThai);
        panel.add(new JLabel("Khu vực:"));
        panel.add(cmbKhuVuc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa thông tin phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String tenPhong = txtTenPhong.getText().trim();
            String giaThueStr = txtGiaThue.getText().trim();

            if (tenPhong.isEmpty() || giaThueStr.isEmpty() || txtLoaiPhong.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Thông tin không được để trống!");
                return;
            }

            try {
                float giaThue = Float.parseFloat(giaThueStr);  // Kiểm tra giá thuê nhập vào

                String loaiPhong = txtLoaiPhong.getText().trim();
                String trangThai = (String) cmbTrangThai.getSelectedItem();
                String khuVucSelected = (String) cmbKhuVuc.getSelectedItem(); // Khu vực đã chọn
                String khuVucID = null;
                for (KhuVucDTO khuVuc : khuVucList) {
                    if (khuVuc.getTenKhuVuc().equals(khuVucSelected)) {
                        khuVucID = khuVuc.getId();  // Lưu id là String
                        break;
                    }
                }

                // Tạo đối tượng phòng mới để sửa thông tin
                PhongDTO editedPhong = new PhongDTO(maPhong, tenPhong, loaiPhong, 0, giaThue, "", trangThai, khuVucID);

                // Gọi PhongBUS để cập nhật phòng
                if (phongBUS.updatePhong(editedPhong)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thành công!");
                    updateTableData();  // Cập nhật lại bảng phòng sau khi sửa
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật phòng thất bại!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá thuê phải là số hợp lệ!");
            }
        }
    }

    private void deleteRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa!");
            return;
        }

        String maPhong = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (phongBUS.deletePhong(maPhong)) {
                JOptionPane.showMessageDialog(this, "Xóa phòng thành công!");
                danhSachPhong.removeIf(p -> p.getMaPhong().equals(maPhong));
                loadData(danhSachPhong);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phòng thất bại!");
            }
        }
    }

}
