import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//transition window between player turns. only has a very big button that loads the next player's window.
public class NextWindow extends JFrame implements ActionListener, WindowListener
{
    //the button
    JButton nextB;
    public NextWindow()
    {
        //add a windowlistener
        addWindowListener(this);

        nextB = new JButton("NEXT PLAYER (" + Game.names[Game.currentPlayer] + ")");
        //add an actionlistener
        nextB.addActionListener(this);
        nextB.setFont(new Font("Courier", Font.BOLD, 50));
        setSize(1000, 1000);
        add(nextB);
        setVisible(true);
    }
    //load next player's window
    @Override
    public void actionPerformed(ActionEvent e) {
        Game.nextWindow();
    }
   //load next players window even if they do close it.
    @Override
    public void windowClosing(WindowEvent e) {
        Game.nextWindow();
    }

    //windowlistener methods i dont need
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
