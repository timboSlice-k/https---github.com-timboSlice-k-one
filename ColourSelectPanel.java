import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//panel to select plus4 and wild colour
class ColourSelectPanel extends JPanel
{
    
    int colourToReturn;
    //array of buttons
    private ArrayList<JButton> colourButtons = new ArrayList<JButton>(4);
    

    public ColourSelectPanel(int x, int y, int w, int h, Card cardToChange)
    {
        //prevent stacking until colour has been selected
        setBounds(x, y, w, h);
        Game.currentWindow.removeNextButton();
        //make it a 2x2 layout.
        setLayout(new GridLayout(2,2));
        
        //add the colourbuttons and add an actionlistener to each.
        for (int i = 0; i < 4; i++)
        {
            colourButtons.add(new JButton(Game.colours[i].toUpperCase()));
            colourButtons.get(i).setFont(new Font("Courier", Font.BOLD, 20));
            add(colourButtons.get(i));
            
            colourButtons.get(i).addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //re allow stacking
                    //make frame add end turn button
                    Game.currentWindow.nextButton();

                    //change the card colour. image updates already included in the changecolour class for plus4 and wild.
                    colourToReturn = colourButtons.indexOf(e.getSource());
                    cardToChange.changeColour(colourToReturn);
                }
            });
        }
        
        
    }
    
}