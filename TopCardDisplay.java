import javax.swing.*;
import java.awt.*;

//panel that shows the current top card
class TopCardDisplay extends JPanel
{
    
    public TopCardDisplay(int x, int y, int w)
    {
        setBounds(x, y, w, (int)Math.round(w*1.4));;
        setMaximumSize(new Dimension(w, (int)Math.round(w*1.4)));;
    }
    //paints the image of the topcard
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(Game.topCard.cardImage, 0, 0, this);
        repaint();
    }
}