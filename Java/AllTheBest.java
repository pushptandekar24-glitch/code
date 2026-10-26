import java.awt.*;
import java.awt.event.*;
public class AllTheBest extends Frame{
    AllTheBest(){
        setTitle("All the best !");
        setSize(500,400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                dispose();
            }
        });
    }
    public void paint(Graphics g){
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.setColor(Color.RED);
        g.drawString("All the best ",150,100);

        g.setColor(Color.BLUE);
        g.drawString("All the best ",150,140);

        g.setColor(Color.GREEN);
        g.drawString("All the best ",150,180);

        g.setColor(Color.ORANGE);
        g.drawString("All the best ",150,220);

        g.setColor(Color.PINK);
        g.drawString("All the best ",150,260);
    }
    public static void main(String[] args) {
        AllTheBest obj = new AllTheBest();
    }
}
