import java.awt.*;
import java.awt.event.*;
public class AWTMouseDemo extends Frame implements MouseListener {
    Label label;
    AWTMouseDemo() {
        label = new Label("Perform a mouse action");
        label.setBounds(100, 100, 250, 30);
        add(label);
        addMouseListener(this);
        setSize(400, 300);
        setLayout(null);
        setVisible(true);
    }
    public void mouseClicked(MouseEvent e) {
        label.setText("Mouse Clicked");
    }
    public void mousePressed(MouseEvent e) {
        label.setText("Mouse Pressed");
    }
    public void mouseReleased(MouseEvent e) {
        label.setText("Mouse Released");
    }
    public void mouseEntered(MouseEvent e) {
        label.setText("Mouse Entered");
    }
    public void mouseExited(MouseEvent e) {
        label.setText("Mouse Exited");
    }
    public static void main(String[] args) {
        new AWTMouseDemo();
    }
}
//main java program for the practical related to the mouse awt