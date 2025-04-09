package GUI;

import DAL.KhuVucDAL;
import DTO.KhuVucDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KhuVucPanel extends JPanel {
    private JTextField txtMaKhuVuc;
    private JTextField txtTenKhuVuc;
    private JTextField txtDiaChi;
    private JTable tblKhuVuc;
    private DefaultTableModel model;

    public KhuVucPanel() {
        setLayout(new BorderLayout(0,0));
        initializeUI();
    }

    private void initializeUI() {
        // Panel cho các thành phần nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin khu vực"));

        inputPanel.add(new JLabel("Mã Khu Vực:"));
        txtMaKhuVuc = new JTextField();
        inputPanel.add(txtMaKhuVuc);
        txtMaKhuVuc.setEditable(false);

        inputPanel.add(new JLabel("Tên Khu Vực:"));
        txtTenKhuVuc = new JTextField();
        inputPanel.add(txtTenKhuVuc);

        inputPanel.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        add(inputPanel, BorderLayout.NORTH);

        // Panel cho các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnThem = new JButton("Thêm Khu Vực");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        Color customColor = new Color(20, 25, 95);
        btnThem.setBackground(customColor);
        btnSua.setBackground(customColor);
        btnXoa.setBackground(customColor);

        // Đặt màu chữ cho các nút
        btnThem.setForeground(Color.WHITE);
        btnSua.setForeground(Color.WHITE);
        btnXoa.setForeground(Color.WHITE);

        // Đặt kích thước cho các nút
        Dimension buttonSize = new Dimension(120, 30);  // Thay đổi kích thước của nút
        btnThem.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);

        // Thêm các nút vào bottomPanel
        buttonPanel.add(btnThem);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Thêm khoảng cách giữa các nút
        buttonPanel.add(btnSua);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btnXoa);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        // Bảng để hiển thị danh sách khu vực
        model = new DefaultTableModel(new String[]{"ID", "Tên Khu Vực", "Địa Chỉ"}, 0);
        tblKhuVuc = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblKhuVuc);

        // Thêm các panel vào giao diện
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện cho nút thêm khu vực
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenKhuVuc = txtTenKhuVuc.getText();
                String diaChi = txtDiaChi.getText();

                if (tenKhuVuc.isEmpty() || diaChi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
                    return;
                }

                KhuVucDTO khuVuc = new KhuVucDTO("KV001", tenKhuVuc, diaChi);
                boolean result = KhuVucDAL.addKhuVuc(khuVuc);

                if (result) {
                    JOptionPane.showMessageDialog(null, "Thêm khu vực thành công.");
                    loadKhuVucData();
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thêm khu vực.");
                }
            }
        });

        // Sự kiện cho nút cập nhật khu vực
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblKhuVuc.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khu vực để cập nhật.");
                    return;
                }

                // Lấy thông tin khu vực từ bảng
                String id = (String) model.getValueAt(selectedRow, 0);
                String tenKhuVucOld = (String) model.getValueAt(selectedRow, 1);  // Tên khu vực cũ
                String diaChiOld = (String) model.getValueAt(selectedRow, 2);  // Địa chỉ khu vực cũ

                // Tạo các trường nhập liệu với giá trị mặc định
                JTextField txtTenKhuVuc = new JTextField(tenKhuVucOld);
                JTextField txtDiaChi = new JTextField(diaChiOld);

                // Tạo form nhập liệu cho người dùng sửa khu vực
                JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
                panel.add(new JLabel("Tên khu vực:"));
                panel.add(txtTenKhuVuc);
                panel.add(new JLabel("Địa chỉ:"));
                panel.add(txtDiaChi);

                int result = JOptionPane.showConfirmDialog(null, panel, "Cập nhật", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String tenKhuVuc = txtTenKhuVuc.getText().trim();
                    String diaChi = txtDiaChi.getText().trim();

                    // Kiểm tra nếu thông tin chưa được điền đầy đủ
                    if (tenKhuVuc.isEmpty() || diaChi.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
                        return;
                    }

                    // Tạo đối tượng khu vực mới để sửa
                    KhuVucDTO khuVuc = new KhuVucDTO(id, tenKhuVuc, diaChi);
                    boolean resultUpdate = KhuVucDAL.updateKhuVuc(khuVuc);  // Gọi phương thức cập nhật trong DAL

                    if (resultUpdate) {
                        JOptionPane.showMessageDialog(null, "Cập nhật khu vực thành công.");
                        loadKhuVucData();  // Tải lại danh sách khu vực trong bảng
                        clearInputFields();  // Xóa các trường nhập liệu
                    } else {
                        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật khu vực.");
                    }
                }
            }
        });

        // Sự kiện cho nút xóa khu vực
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblKhuVuc.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn khu vực để xóa.");
                    return;
                }

                String id = (String) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khu vực này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean result = KhuVucDAL.deleteKhuVuc(id);

                    if (result) {
                        JOptionPane.showMessageDialog(null, "Xóa khu vực thành công.");
                        loadKhuVucData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa khu vực.");
                    }
                }
            }
        });

        // Tải dữ liệu khu vực khi mở panel
        loadKhuVucData();
    }

    private void loadKhuVucData() {
        model.setRowCount(0);
        List<KhuVucDTO> khuVucList = KhuVucDAL.getAllKhuVuc();

        for (KhuVucDTO khuVuc : khuVucList) {
            model.addRow(new Object[]{khuVuc.getId(), khuVuc.getTenKhuVuc(), khuVuc.getDiaChi()});
        }
    }

    private void clearInputFields() {
        txtMaKhuVuc.setText("");
        txtTenKhuVuc.setText("");
        txtDiaChi.setText("");
    }
}
