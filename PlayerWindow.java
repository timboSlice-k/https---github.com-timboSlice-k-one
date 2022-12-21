import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PlayerWindow extends JFrame implements ActionListener, WindowListener
{
	//player associated with the window
	int player;
	//panel that displays the topcard
	TopCardDisplay tcd;
	//shows the players hands
	CardPanel carp;
	//colour select panel for wilds and plus4s.
	ColourSelectPanel csp;
	//shows how many cards the other players have
	JLabel cardCounts;
	//shows how many cards current player has.
	JLabel playerCardCount;
	//log jlabel
	JLabel logs;
	//jscrollpanes that add jlabels to account for possible strings that are too big
	JScrollPane logPane;
	JScrollPane countPane;
	//shows whos playing next
	JLabel nextPlayer;
	//draw card button
	JButton drawButton = new JButton("Draw Card");

	//jbutton to end turn and move to next player
	JButton nextjb = new JButton("End turn");

	boolean nextjbAdded = false;
	
	//constructor takes player, and number of cards they had to draw.
	public PlayerWindow (int pl, int drawAmount)
	{
		//whole bunch of jlabel stuff
		this.player = pl;

		//button to draw cards
		drawButton.setFont(new Font("Courier", Font.BOLD, 20));
		drawButton.setBounds(698, 0, 300, 150);
		drawButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (Game.playerCards[player].size() < 30)
				{
					Game.drawCard(player);
					carp.updatePanel();
					playerCardCount.setText(Game.names[player] + ": " + Game.playerCards[player].size() + " cards");
				}
					

			}
		});
		add(drawButton);


		nextPlayer = new JLabel("Next Player: " + Game.names[Game.returnNextCycle()]);
		nextPlayer.setFont(new Font("Courier", Font.BOLD, 18));
		nextPlayer.setBounds(0, 80, 400, 100);
		add(nextPlayer);

		logs = new JLabel(setLog());
		logPane = new JScrollPane(logs);
		logPane.setBounds(600, 400, 400, 300);
		logs.setFont(new Font("Courier", Font.PLAIN, 20));
		//logs.setBounds(600, 500, 500, 300);
		add(logPane);

		updatePlayerCardCount(drawAmount);

		playerCardCount.setFont(new Font("Courier", Font.BOLD, 30));
		playerCardCount.setBounds(0, 600, 600, 200);
		add(playerCardCount);

		setTitle(Game.names[player]);
		
		cardCounts = new JLabel(Game.cardCounts());
		cardCounts.setFont(new Font("Courier", Font.BOLD, 24));
		countPane = new JScrollPane(cardCounts);
		countPane.setBounds(600, 150, 400, 250);
		add(countPane);

		
		setSize(1000, 1000);
		setResizable(false);
		addWindowListener(this);

		nextjb.setFont(new Font ("Courier", Font.BOLD, 20));
		nextjb.setBounds(0, 0, 200, 100);
		nextjb.addActionListener(this);
		
		//null layout so i can put anything wherever i want
		getContentPane().setLayout(null);
		
		//card panel
		carp = new CardPanel(100, 700, 800, 200, player);
		add(carp);
		//top card display
		tcd = new TopCardDisplay(400, 200, 200);
		add(tcd);
		
		setVisible(true);
	}
	//update topcard display, cardpanel and cardcount text.
	public void updateTCD()
	{
		tcd = new TopCardDisplay(400, 200, 200);
		carp.updatePanel();

		playerCardCount.setText(Game.names[player] + ": " + Game.playerCards[player].size() + " cards");
	}
	//creates a colour select panel, called by wilds and plus 4.
	public void colourSelect(Card ctc)
	{
		//try to remove old colour select panel. try catch there incase csp doesnt exist yet. if exception happens, just move on.
		try{
			remove(csp);
		}
		catch(Exception e){}
		//new colour select panel
		csp = new ColourSelectPanel(50, 300, 300, 300, ctc);
		// add it and update the panel.
		add (csp);
		repaint();
	}
	//adds the end turn button
	public void nextButton()
	{
		if (!nextjbAdded)
		{
			add(nextjb);
			repaint();
			nextjbAdded = true;
		}
	}
	public void removeNextButton()
	{
		try{
			remove(nextjb);
			repaint();
		}
		
		catch(Exception e){}
		nextjbAdded = false;
	}
	//updates other player's card counts in case of plus 4 and plus 2
	public void updateCounts()
	{
		cardCounts.setText(Game.cardCounts());
	}
	//updates player card count
	public void updatePlayerCardCount(int drawNumber)
	{
		String labeltext = Game.playerCards[player].size() + " cards";
		//dont show draw number if they didnt draw anything
		if (drawNumber != 0)
		{
			labeltext += ", drew " + drawNumber;
		}
		playerCardCount = new JLabel(Game.names[player] + ": " + labeltext);
	}
	
	//convert the log arraylist to html text useable by jlabels
	public String setLog ()
	{
		String logText = "<html>";
		for (int i = 0; i < Game.log.size(); i++)
		{
			logText += Game.log.get(i) + "<br>";
		}
		logText += "</html>";
		return logText;
	}

	//updates whos playing next. in case of skip and reverse
	public void updateNextPlayerDisplay ()
	{
		nextPlayer.setText(("Next Player: " + Game.names[Game.returnNextCycle()]));
	}

	//for when end turn button is pressed, run the game's pausewindow method
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.pauseWindow();
	}

	

	//just end the game and program if they close it.
    @Override
    public void windowClosing(WindowEvent e) {

        System.out.println("Game ended prematurely by " + Game.names[Game.currentPlayer] + ". Program ended.");
        dispose();
        System.exit(0);
    }
	//extra methods that come with the windowlistener interface that i do not need
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