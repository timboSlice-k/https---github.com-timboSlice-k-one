//number card class. least interesting card class.
class NumberCard extends Card
{
	public NumberCard (int num, int col)
	{
		super(num, col, Game.t.getImage("small/" + Game.colours[col] + "_" + num + ".png"));
		cardID = Game.colours[col] + " " + num;
		
	}
	public void printCard()
	{
		System.out.println(Game.colours[colour] + " " + number);
	}
}