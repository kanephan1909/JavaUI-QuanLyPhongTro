package images.component;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int radius = 40; // Độ bo góc (border radius)
    private Color backgroundColor;
    private Color hoverColor;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Tắt nền mặc định của JButton
        setFocusPainted(false); // Tắt viền focus
        setBorderPainted(false); // Tắt viền mặc định
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Con trỏ tay
        this.backgroundColor = new Color(20, 25, 95); // Màu nền mặc định
        this.hoverColor = new Color(30, 40, 120); // Màu khi hover
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tạo hình chữ nhật bo góc
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        // Kiểm tra trạng thái hover
        if (getModel().isRollover()) {
            g2.setColor(hoverColor); // Màu khi hover
        } else {
            g2.setColor(backgroundColor); // Màu nền mặc định
        }

        // Vẽ nền bo góc
        g2.fill(roundedRectangle);

        // Vẽ nội dung của button (text, icon, v.v.)
        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ viền mặc định
    }

    @Override
    public boolean contains(int x, int y) {
        // Đảm bảo chỉ nhận click trong vùng bo góc
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        return roundedRectangle.contains(x, y);
    }
}
