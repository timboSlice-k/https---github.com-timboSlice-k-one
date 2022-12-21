import javax.swing.*;
//"one" reverse card. reverses order of play. 
class Reverse extends Card
{
	public Reverse (int col)
	{
		super(-1, col, Game.t.getImage("small/" + Game.colours[col] + "_reverse.png"));
		cardID = Game.colours[col] + " reverse";
		
	}

	public void special()
	{
		//switch the order and update frame and image
		Game.switchOrder();
		Game.currentWindow.updateNextPlayerDisplay();
		button.setIcon(new ImageIcon(cardImage));
		Game.currentWindow.nextButton();
	}

	public void printCard()
	{
		System.out.println(Game.colours[colour] + " reverse");
	}
}