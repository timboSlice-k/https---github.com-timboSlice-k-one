import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//jframe that appears if game finishes, asks whether or not player wants to play again.
public class PostGameFrame extends JFrame implements ActionListener, WindowListener
{
    //content pane is just the jpanel that the jframe puts stuff on.
    Container contentpane;
    JButton yes;
    JButton no;
    public PostGameFrame (int player)
    {
        setSize(1000, 1000);
        setResizable(false);
        //make windowlistener, can run code when actions are performed w/ window.
        addWindowListener(this);
        contentpane = this.getContentPane();
        //null layout lets me place anything anywhere.
        contentpane.setLayout(null);
        //display who won last game
        JLabel text = new JLabel(Game.names[player] + " won! Would you like to play \"One\" again?");
        text.setBounds(10, 10, 1000, 300);
        text.setFont(new Font("Courier", Font.PLAIN, 30));
        add(text);
        
       //yes or no buttons
        yes = new JButton("Yes");
        no = new JButton("No");
        yes.addActionListener(this);
        yes.setFont(new Font("Courier", Font.PLAIN, 40));
        no.addActionListener(this);
        no.setFont(new Font("Courier", Font.PLAIN, 40));
        no.setBounds(500, 300, 500, 700);
        yes.setBounds(0, 300, 500, 700);
        
        add(yes);
        add(no);
       
        
        setVisible(true);
        //pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yes) //pressing yes, calls game reset method
        {
            Game.resetGame();
        }
        else //pressing no, program ends.
        {
            System.out.println("Program ended.");
            dispose();
            System.exit(0);
        }
    }
    //make closing the window the same as pressing no
    @Override
    public void windowClosing(WindowEvent e) {

        System.out.println("Program ended.");
        dispose();
        System.exit(0);
    }
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e){}
    
}
