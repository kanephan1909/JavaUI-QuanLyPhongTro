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

        setTitle("Quản lý phòng trọ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Cập nhật contentPanel Flat Design
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE); // Nền trắng sáng

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
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(245, 245, 245)); // Màu xám nhạt
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel titleLabel = new JLabel("Quản Lý Phòng Trọ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titlePanel.setBackground(new Color(245, 245, 245));
        titlePanel.add(titleLabel);
        sidebar.add(titlePanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(245, 245, 245));
        menuPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weighty = 1;

        JLabel sectionLabel = new JLabel("Menu");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(100, 100, 100));
        menuPanel.add(sectionLabel, gbc);
        gbc.gridy++;

        addSidebarButton(menuPanel, "Quản lý Hóa đơn", new GUI.HoaDonPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Khách hàng", new GUI.KhachPanel(), gbc);
        addSidebarButton(menuPanel, "Quản lý Phòng", new GUI.PhongPanel(), gbc);

        sidebar.add(menuPanel, BorderLayout.NORTH);
        return sidebar;
    }

    private JLabel createMenuSection(String text) {
        JLabel sectionLabel = new JLabel(text);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        sectionLabel.setForeground(new Color(100, 100, 100));
        return sectionLabel;
    }

    // Button
    private void addSidebarButton(JPanel sidebar, String text, JPanel panel, GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setPreferredSize(new Dimension(180, 40));
        buttonPanel.setBackground(new Color(230, 230, 230)); // Xám nhẹ

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonPanel.add(label);

        buttonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        buttonPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanel(panel);
                highlightButton(buttonPanel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setBackground(new Color(180, 200, 255)); // Xanh nhạt khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (activePanel != buttonPanel) {
                    buttonPanel.setBackground(new Color(230, 230, 230));
                }
            }
        });

        sidebar.add(buttonPanel, gbc);
        gbc.gridy++;
    }

    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void highlightButton(JPanel buttonPanel) {
        if (activePanel != null) {
            activePanel.setBackground(Color.WHITE);
            activePanel.getComponent(0).setForeground(Color.BLACK);
        }
        buttonPanel.setBackground(new Color(100, 149, 237));
        buttonPanel.getComponent(0).setForeground(Color.WHITE);
        activePanel = buttonPanel;
    }
}
