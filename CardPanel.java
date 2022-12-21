import javax.swing.*;
//cardpanel that shows the player's hand
public class CardPanel extends JPanel
{
	int player;
	public CardPanel(int x, int y, int w, int h, int player)
	{
		this.player = player;

		setBounds(0, 700, 1000, 200);
		//x axis boxlayout sets components in a horizontal line
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		for (int i = 0; i < Game.playerCards[player].size(); i++)
		{
			//add cards
			add(Game.playerCards[player].get(i).button);
			//set their card size to something fill up a 900 wide space, but with a max width of 100
			Game.playerCards[player].get(i).setCardSize(900 / Game.playerCards[player].size() > 100?100:(int)800 / Game.playerCards[player].size());

		}
	}

	public void updatePanel ()
	{
		//remove all buttons
		removeAll();
		for (int i = 0; i < Game.playerCards[player].size(); i++)
		{
			//update buttons
			Game.playerCards[player].get(i).refresh();
			//same as before
			add(Game.playerCards[player].get(i).button);	
			Game.playerCards[player].get(i).setCardSize(900 / Game.playerCards[player].size() > 100?100:(int)800 / Game.playerCards[player].size());
		}
	}
}