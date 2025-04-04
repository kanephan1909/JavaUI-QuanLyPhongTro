package GUI;

import com.sun.tools.javac.Main;

import javax.swing.*;

public class HomePanel extends JPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
