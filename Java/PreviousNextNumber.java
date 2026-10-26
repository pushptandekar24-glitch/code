import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PreviousNextNumber extends JFrame implements ActionListener{
    JTextField t1,t2,t3;
    JButton b;
    PreviousNextNumber(){
        // create frame
        setTitle("Previous and Next Number");
        setSize(400,200);
        setLayout(new FlowLayout());

        //create text fields
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        t3 = new JTextField(10);

        //create button
        b = new JButton("Calculate");

        // add components to frame
        add(new JLabel("Enter a number:"));
        add(t1);

        add(new JLabel("Previous number:"));
        add(t2);

        add(new JLabel("Next number:"));
        add(t3);
        add(b);

        // add action listener
        b.addActionListener(this);

        //make frame visible
        setVisible(true);

        //close window 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override 
    public void actionPerformed(ActionEvent e ){
        //get number from first text field 
        int n = Integer.parseInt(t1.getText());

        //display previous number
        t2.setText(String.valueOf(n-1));

        //display next number
        t3.setText(String.valueOf(n+1));
    }
    public static void main(String[] args) {
        new PreviousNextNumber();
    }
}
//awt is platform dependent and swing is platform independent.
//customization is easy in swing and difficult in awt.