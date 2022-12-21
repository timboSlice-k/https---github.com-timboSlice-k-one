import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
//generic card class, the mother of all card types.
class Card implements ActionListener
{
	//number is more like symbol. all reverses will have a common number value, all skips will have a common number value, etc. this is for stacking.
	protected int number;
	//colour variable
	protected int colour;
	//string variable that holds card name. for logs.
	protected String cardID;
	public Image cardImage;
	public JButton button;
	public Card (int num, int col, Image im)
	{
		//constructor setting things
		number = num;
		colour = col;
		cardImage = im;
		button = new JButton(new ImageIcon(cardImage));
		button.addActionListener(this);
		//button = new JButton();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!Game.placed && validPlace(Game.topCard)) //prevent illegal placements
		{	
			Game.placeCard(Game.playerCards[Game.currentPlayer].indexOf(this), Game.currentPlayer);
			//System.out.println("card placed");
			//Game.printCards(Game.currentPlayer);
			Game.currentWindow.updateTCD();
			Game.placed = true;
			Game.currentWindow.updateCounts();
		}
		else if (validStack(Game.topCard)) //stack the card on top of the other topcard
		{
			//log old topcard here if its a wild or plus4. colourselectpanel prevents it from being logged earlier, but log method in cycle will only log the last stacked card
			//only for stacking plus4s or wilds
			if(Game.topCard instanceof Wild || Game.topCard instanceof Plus4)
			{
				Game.logAction(Game.topCard);
			}
			//place the card
			Game.placeCard(Game.playerCards[Game.currentPlayer].indexOf(this), Game.currentPlayer);
			//update frame
			Game.currentWindow.updateTCD();
			Game.currentWindow.updateCounts();
		}
	}
	//sets card size (card height is 1.4 times width)
	public void setCardSize(int width)
	{
		//scale the image to new width
		Image newImage = cardImage.getScaledInstance(width, (int)Math.round(width * 1.4), Image.SCALE_SMOOTH);
		//update button image and size
		button.setIcon(new ImageIcon(newImage));
		button.setBounds(0, 0, width, (int)Math.round(width * 1.4));
		button.setMaximumSize(new Dimension(width, (int)Math.round(width * 1.4)));
		button.setPreferredSize(new Dimension(width, (int)Math.round(width * 1.4)));
		button.setMinimumSize(new Dimension(width, (int)Math.round(width * 1.4)));
	}
	//returns colour
	public int getColour()
	{
		return colour;
	}
	//printcard method used for testing, meant to be overwritten
	public void printCard() {}
	//special class. meant to be overwritten by special cards. runs when card is placed.
	public void special()
	{
		//default code only used in numbercard
		button.setIcon(new ImageIcon(cardImage));
		Game.currentWindow.nextButton();
	}
	//changes colour
	public void changeColour(int col)//oh shiet mikey YAKson
	{
		colour = col;
	} 
	//refresh method meant to be overwritten by plus4 and wild.
	public void refresh()
	{
		button.setIcon(new ImageIcon(cardImage));
	}
	//updates the button
	public void updateButton()
	{
		button.setIcon(new ImageIcon(cardImage));
	}
	//checks if card can be placed on top of passed topcard (same colour or number/symbol)
	public boolean validPlace(Card topcard)
	{
		if (topcard.colour == this.colour || topcard.number == this.number || this.colour == -1)
			return true;
		else
			return false;
	}
	//checks if card can be stacked on top of passed topcard (same number/symbol)
	public boolean validStack(Card topcard)
	{
		if (topcard.number == this.number)
			return true;
		else
			return false;

	}
	
}