import javax.swing.*;
//wild card
class Wild extends Card
{
	
	public Wild()
	{
		super (-4, -1, Game.t.getImage("small/" + "wild.png"));
		cardID = "Wild";
		
	}
	public void special()
	{
		//create a colour select panel on the current window
		Game.currentWindow.colourSelect(this);
	}
	public void printCard()
	{
		if (this.colour == -1)
		{
			System.out.println("wild");
		}
		else
			System.out.println(Game.colours[colour] + " wild");
	}
	//change colour and update image.
	public void changeColour(int col)
	{
		colour = col;
		cardImage = Game.t.getImage("small/" + Game.colours[colour] + "_wild.png");
		button.setIcon(new ImageIcon(cardImage));
		cardID = Game.colours[colour] + " Wild";
	}
	//reset colour and image
	public void refresh()
	{
		colour = -1;
		cardImage = Game.t.getImage("small/wild.png");
		button.setIcon(new ImageIcon(cardImage));
		cardID = "Wild";
	}
}