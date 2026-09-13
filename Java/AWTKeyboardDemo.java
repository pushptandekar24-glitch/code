import java.awt.*;
import java.awt.event.*;

public class AWTKeyboardDemo extends Frame implements KeyListener {
    Label label;
    AWTKeyboardDemo() {
        label = new Label("Press any key");
        label.setBounds(100, 100, 250, 30);

        add(label);
        addKeyListener(this);

        setSize(400, 300);
        setLayout(null);
        setVisible(true);

        requestFocus();
    }

    public void keyTyped(KeyEvent e) {
        label.setText("Key Typed: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
        label.setText("Key Pressed: " + e.getKeyChar());
    }

    public void keyReleased(KeyEvent e) {
        label.setText("Key Released");
    }

    public static void main(String[] args) {
        new AWTKeyboardDemo();
    }
}
