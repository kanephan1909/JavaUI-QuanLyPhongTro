
package GUI;
import javax.swing.*;

import DAL.DatabaseConnection;
import images.component.RoundedButton;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnTogglePassword;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Right;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private RoundedButton btnLogin;

    public LoginFrame() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Khởi tạo các thành phần
        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        btnLogin = new RoundedButton("ĐĂNG NHẬP");
        btnTogglePassword = new javax.swing.JButton();

        // Cấu hình JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HỆ THỐNG ĐĂNG NHẬP");
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setResizable(false);

        // Cấu hình jPanel1
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        // Cấu hình panel Right (bên trái - chứa logo)
        Right.setBackground(new java.awt.Color(20, 25, 95));
        Right.setBounds(0, 0, 640, 720);

        // Logo
        jLabel5.setIcon(new javax.swing.ImageIcon("src/images/logo.png")); // Giả sử có logo
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Tiêu đề "NHÀ TRỌ SẠCH SẼ"
        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 36)); // Tăng kích thước font
        jLabel6.setForeground(Color.WHITE);
        jLabel6.setText("NHÀ TRỌ SẠCH SẼ");
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Mô tả phụ (nếu cần)
        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 16));
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Hệ thống quản lý nhà trọ chuyên nghiệp");
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Căn chỉnh các thành phần trong panel Right
        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
                RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGap(150) // Tăng khoảng cách từ trên xuống để logo nằm cao hơn
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20) // Khoảng cách giữa logo và tiêu đề
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10) // Khoảng cách giữa tiêu đề và mô tả
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Cấu hình panel Left (bên phải - chứa form đăng nhập)
        Left.setBackground(new java.awt.Color(235, 174, 71));
        Left.setBounds(640, 0, 640, 720);

        // Tiêu đề "ĐĂNG NHẬP"
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36));
        jLabel1.setForeground(new java.awt.Color(20, 25, 95));
        jLabel1.setText("ĐĂNG NHẬP");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Label "Username"
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Tên đăng nhập");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        // TextField cho Username
        textField.setFont(new java.awt.Font("Segoe UI", 1, 14));
        textField.setForeground(new java.awt.Color(20, 25, 95));
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1));

        // Label "Password"
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mật khẩu");

        // PasswordField
        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 14));
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1));

        // Nút Login
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackgroundColor(new java.awt.Color(20, 25, 95));
        btnLogin.setHoverColor(new java.awt.Color(30, 40, 120));

        btnTogglePassword.setText("👁");
        btnTogglePassword.setFont(new java.awt.Font("Roboto", 0, 14));
        btnTogglePassword.setBounds(450, 360, 40, 40);
        btnTogglePassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(224, 224, 224), 1));
        btnTogglePassword.setContentAreaFilled(false);
        btnTogglePassword.addActionListener(e -> {
            if (passwordField.getEchoChar() == 0) {
                passwordField.setEchoChar('•'); // Ẩn mật khẩu
            } else {
                passwordField.setEchoChar((char) 0); // Hiện mật khẩu
            }
        });
        Left.add(btnTogglePassword);



        // Căn chỉnh các thành phần trong panel Left
        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(150) // Căn giữa form trong panel (640 - 340 - 2*150 = 0)
                                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(150))
        );
        LeftLayout.setVerticalGroup(
                LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(150) // Đẩy form xuống để căn giữa theo chiều dọc
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40) // Khoảng cách giữa tiêu đề và label Username
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10) // Khoảng cách giữa label và text field
                                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20) // Khoảng cách giữa text field và label Password
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10) // Khoảng cách giữa label và password field
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30) // Khoảng cách giữa password field và nút Login
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Thêm panels vào jPanel1
        jPanel1.add(Right);
        jPanel1.add(Left);

        // Cấu hình layout chính
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        // Thêm sự kiện
        btnLogin.addActionListener(e -> {
            if (authenticateUser()) {
                new MainFrame().setVisible(true);
                dispose();
            }
        });

        // Cấu hình phím Enter và focus
        getRootPane().setDefaultButton(btnLogin);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                textField.requestFocusInWindow();
            }
        });

        pack();
        setLocationRelativeTo(null); // Căn giữa màn hình
    }

    // Hàm xử lý thông tin đăng nhập
    private boolean authenticateUser() {
        String userName = textField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        String sql = "SELECT MatKhau FROM TaiKhoan WHERE TenDangNhap=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, userName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("MatKhau");
                    if (storedPassword != null) {
                        storedPassword = storedPassword.trim();
                    }
                    if (storedPassword != null && password.equals(storedPassword)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Sai mật khẩu!");
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại!");
                    return false;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + sqlException.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

