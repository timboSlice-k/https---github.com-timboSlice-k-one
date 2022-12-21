import javax.swing.*;
//skip turn card
class Skip extends Card
{
	public Skip (int col)
	{
		super(-2, col, Game.t.getImage("small/" + Game.colours[col] + "_skip.png"));
		cardID = Game.colours[col] + " skip";
	}

	public void special()
	{
		//increases amount to cycle. adds up when skip cards are stacked.
		Game.amountToCycle++;
		Game.currentWindow.updateNextPlayerDisplay();
		button.setIcon(new ImageIcon(cardImage));
		Game.currentWindow.nextButton();
	}

	public void printCard()
	{
		System.out.println(Game.colours[colour] + " skip");
	}
}