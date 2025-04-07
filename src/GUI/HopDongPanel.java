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
    private JButton btnThem, btnSua, btnXoa;
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themHopDong());
        btnXoa.addActionListener(e -> xoaHopDong());
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
