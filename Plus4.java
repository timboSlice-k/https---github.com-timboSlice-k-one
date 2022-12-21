import javax.swing.*;
//plus4 card class
class Plus4 extends Card
{
	
	public Plus4()
	{
		super (-5, -1, Game.t.getImage("small/plus4.png"));
		cardID = "Plus 4";
	}
	public void special()
	{
		//call the colourselect method when this card is placed.
		Game.currentWindow.colourSelect(this);
		//make next player draw 4
		for (int i = 0; i < 4; i++)
		{
			Game.drawCard(Game.nextPlayer());
		}

		//update card count display
		Game.currentWindow.updateCounts();
	}
	public void printCard()
	{
		if (this.colour == -1)
		{
			System.out.println("plus4");
		}
		else
			System.out.println(Game.colours[colour] + " plus4");
		
	}
	public void changeColour(int col)
	{
		colour = col;
		//update card icon images
		cardImage = Game.t.getImage("small/" + Game.colours[colour] + "_plus4.png");
		button.setIcon(new ImageIcon(cardImage));
		cardID = Game.colours[col] + " Plus 4";
	}
	//reset the colour & image when going back into the discard pile.
	public void refresh()
	{
		colour = -1;
		cardImage = Game.t.getImage("small/plus4.png");
		button.setIcon(new ImageIcon(cardImage));
		cardID = "Plus 4";
	}
}