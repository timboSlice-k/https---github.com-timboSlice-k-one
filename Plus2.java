import javax.swing.*;

class Plus2 extends Card
{
	public Plus2(int col)
	{
		super (-3, col, Game.t.getImage("small/" + Game.colours[col] + "_plus2.png"));
		cardID = Game.colours[col] + " plus 2";
	}

	public void special()
	{
		button.setIcon(new ImageIcon(cardImage));
		//make next player draw 2 cards
		Game.drawCard(Game.nextPlayer());
		Game.drawCard(Game.nextPlayer());
		//update counts and display endturn button
		Game.currentWindow.updateCounts();
		Game.currentWindow.nextButton();
	}
	public void printCard()
	{
		System.out.println(Game.colours[colour] + " plus2");
	}
}