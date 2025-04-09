package GUI;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private JPanel contentPanel;

    public HomePanel() {
        setLayout(new BorderLayout(0, 0)); // Sử dụng BorderLayout và thêm khoảng cách giữa các phần tử
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        // Thêm chiều rộng cho topPanel
        topPanel.setPreferredSize(new Dimension(200, 100));
        topPanel.setBackground(new Color(22, 26, 83));

        JLabel lblHome = new JLabel("<html><div style='text-align: center;margin-top: 18px; color: #fff;'>Trang Chủ</div></html>");
        lblHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHome.setForeground(Color.WHITE); // Màu chữ trắng cho label

        topPanel.add(lblHome, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Content Panel (Nội dung chính)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(235, 174, 71)); // Màu nền của contentPanel

        // Thông báo chào mừng
        JLabel welcomeLabel = new JLabel(
                "<html><div style='text-align: center; color: #fff;'>Xin chào!<br>Vui lòng chọn một chức năng để bắt đầu quản lý phòng trọ.</div></html>");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePanel().setVisible(true));
    }
}
