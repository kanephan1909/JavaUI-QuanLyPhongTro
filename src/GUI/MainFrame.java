package GUI;

import javax.swing.*;

import DAL.DatabaseConnection;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private JPanel activePanel;

    public MainFrame() {
        // Thêm vào đầu constructor
        try {
            Connection conn = DatabaseConnection.getConnection();
            if(conn != null) {
                System.out.println("✅ Kết nối database thành công!!");
            }
        } catch(Exception e) {
            System.out.println("Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
        }

        setTitle("QUẢN LÝ PHÒNG TRỌ - NHÀ TRỌ SẠCH SẼ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLayout(new BorderLayout());

        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        // Thêm chiều rộng cho topPanel
//        topPanel.setPreferredSize(new Dimension(0, 100));
//        topPanel.setBackground(new Color(22, 26, 83));
//
//        JLabel lblHome = new JLabel("<html><div style='text-align: center;margin-top: 18px; color: #fff;'>Trang Chủ</div></html>");
//        lblHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        lblHome.setForeground(Color.WHITE); // Màu chữ trắng cho label
//
//        topPanel.add(lblHome, BorderLayout.CENTER);
//        add(topPanel, BorderLayout.NORTH);


        // Cập nhật contentPanel Flat Design
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(235, 174, 71)); // Nền trắng sáng

        JLabel welcomeLabel = new JLabel(
                "<html><div style='text-align: center; color: #555;'>Xin chào!<br>Vui lòng chọn một chức năng để bắt đầu quản lý phòng trọ.</div></html>");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Menu
    private JPanel createSidebar() {
        // Sidebar chính
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(20, 25, 95)); // Màu nền sidebar tối (đậm hơn)
        sidebar.setPreferredSize(new Dimension(260, 0)); // Kích thước của sidebar

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 50));
        titlePanel.setBackground(new Color(20, 25, 95)); // Giữ màu nền của title


        // Tải icon từ file và thay đổi kích thước
        Icon icon = new ImageIcon("src/images/logo.png");  // Đảm bảo đường dẫn chính xác
        Image img = ((ImageIcon) icon).getImage();  // Lấy hình ảnh từ icon
        Image scaledImg = img.getScaledInstance(220, 80, Image.SCALE_SMOOTH);  // Thay đổi kích thước icon (100x100 pixels)

        // Tạo lại ImageIcon với kích thước mới
        icon = new ImageIcon(scaledImg);

        // Thêm icon vào titlePanel
        JLabel iconLabel = new JLabel(icon);
        titlePanel.add(iconLabel);  // Đảm bảo icon có kích thước đã thay đổi

        sidebar.add(titlePanel, BorderLayout.NORTH);


        // Menu Panel (Chứa các mục menu)
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(20, 25, 95)); // Giữ màu nền của menu

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 0;

        // Nút menu
        addSidebarButton(menuPanel, "Trang Chủ", new HomePanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Khu Vực", new GUI.KhuVucPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Phòng", new GUI.PhongPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Khách Thuê", new GUI.KhachPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Hợp Đồng", new GUI.HopDongPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Hóa đơn", new GUI.HoaDonPanel(), gbc);
        addSidebarButton(menuPanel, "Thoát", null, gbc); // Thêm mục "Logout"

        // Thêm panel rỗng để đẩy các nút lên đầu
        gbc.weighty = 1;
        gbc.gridy++;
        menuPanel.add(Box.createVerticalGlue(), gbc);

        sidebar.add(menuPanel, BorderLayout.CENTER);
        return sidebar;
    }

    private JLabel createMenuSection(String text) {
        JLabel sectionLabel = new JLabel(text);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(20, 25, 95));
        return sectionLabel;
    }

    // Button
    private void addSidebarButton(JPanel sidebar, String text, JPanel panel, GridBagConstraints gbc) {
        // Tạo nút với FlowLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());  // Dùng BorderLayout để dễ căn chỉnh các phần tử
        buttonPanel.setPreferredSize(new Dimension(240, 40)); // Chiều rộng cố định cho nút, vừa đủ chiếm chiều rộng sidebar
        buttonPanel.setBackground(new Color(235, 174, 71)); // Màu nền của nút

        // Tạo và thêm văn bản vào nút
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Cỡ chữ dễ đọc
        label.setForeground(Color.WHITE); // Màu chữ trắng
        label.setHorizontalAlignment(SwingConstants.CENTER); // Căn chữ vào giữa
        buttonPanel.add(label, BorderLayout.CENTER);  // Đặt chữ vào giữa

        // Thêm icon vào nút (nếu cần)
        Icon icon = new ImageIcon("src/images/component/file-solid.svg");  // Đảm bảo đường dẫn chính xác
        JLabel iconLabel = new JLabel(icon);
        buttonPanel.add(iconLabel, BorderLayout.WEST);  // Đặt icon vào bên trái nút

        buttonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thêm hiệu ứng khi hover

        // Hiệu ứng hover cho nút
        buttonPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (text.equals("Thoát")) {
                    System.exit(0);  // Đóng ứng dụng
                } else if (panel != null) {
                    switchPanel(panel); // Chuyển panel khi click
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setBackground(new Color(214, 148, 17)); // Màu khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (activePanel != buttonPanel) {
                    buttonPanel.setBackground(new Color(235, 174, 71)); // Màu gốc khi không hover
                }
            }
        });

        sidebar.add(buttonPanel, gbc); // Thêm nút vào sidebar
        gbc.gridy++; // Di chuyển xuống dòng tiếp theo trong GridBagLayout
    }



    // Hàm chuyển đổi giữa các panel
    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Hàm làm sáng nút khi nhấn
    private void highlightButton(JPanel buttonPanel) {
        if (activePanel != null) {
            activePanel.setBackground(new Color(186, 132, 40)); // Màu gốc của nút
            activePanel.getComponent(0).setForeground(Color.WHITE); // Đổi màu chữ
        }
        buttonPanel.setBackground(new Color(100, 149, 237)); // Màu xanh dương khi chọn
        buttonPanel.getComponent(0).setForeground(Color.WHITE); // Đổi màu chữ thành trắng khi chọn
        activePanel = buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }

}
