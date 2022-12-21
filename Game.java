import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class Game
{
	//hardcoded constant that sets how many cards each player starts with.
	private static final int initialCardCount = 7;

	//holds the window currently being displayed
	public static PlayerWindow currentWindow;
	//int that holds how many cards the player had to draw to be able to play.
	public static int drawAmount;
	//the NEXT PLAYER frame.
	private static NextWindow pauseFrame;
	//prevents users from placing multiple cards.
	public static boolean placed = false;
	//full cards list. gets initialized, then used after every game to reset the deck.
	static ArrayList<Card> fullCards = new ArrayList();
	static ArrayList<Card> deck = new ArrayList();
	//old cards go here. gets added to the deck if deck is empty.
	private static ArrayList<Card> discardPile = new ArrayList();
	//stores hands. each arraylist within the array is a seperate player hand. counts as a 2d array maybe
	public static ArrayList<Card>[] playerCards;
	//variable that holds the top card.
	public static Card topCard;
	//int that stores the currentplayer
	public static int currentPlayer = 0;
	//amount to cycle, used only for the skip card
	public static int amountToCycle = 1;
	//logs that store what happened in previous turns
	public static ArrayList<String> log = new ArrayList<String>();
	//amount of players
	public static int playerNumber;
	//toolkit to get images
	public static Toolkit t = Toolkit.getDefaultToolkit();
	//holds post game frame so it can be disposed remotely.
	private static PostGameFrame pgf;

	//array to store player names
	public static String[] names;

	//1 is cw, -1 is ccw
	private static int order = 1;

	//constant array so i can associate each colour with a number with ease.
	public static final String[] colours = {"red", "yellow", "green", "blue"};



	//gets names
	public static void getNames()
	{
		Scanner nameGetter = new Scanner(System.in);
		for (int i = 0; i < names.length; i++)
		{
			System.out.print("Enter player " + (i+1) + "'s name\n\t> ");
			names[i] = nameGetter.nextLine();
		}
	}

	//returns all cardcounts in html text
	public static String cardCounts()
	{
		String countDisplay = "<html>";
		for (int i = 0; i < playerCards.length; i++)
		{
			if (i != currentPlayer)
			{
				countDisplay += names[i] + ": " + playerCards[i].size() + " cards<br>";
			}
			else 
			{
				countDisplay += names[i]+ " (You): " + playerCards[i].size() + " cards<br>";
			}
			
		}
		countDisplay += "</html>";
		return countDisplay;
	}

	//disposes post game frame and resets game
	public static void resetGame()
	{
		//reset relevant variables
		pgf.dispose();
		currentPlayer = 0;
		deck = fullCards;
		discardPile = new ArrayList();
		amountToCycle = 1;
		playerCards = null;
		topCard = null;
		
		log = new ArrayList<String>();
		
		//everything is just the same as the main at this point
		playerNumber = intInput("How many players? (2 - 6)", "Please enter a valid number of players. (2 - 6)", 2, 6);
		playerCards = new ArrayList[playerNumber];
		names = new String [playerNumber];
		getNames();
		for (int i = 0; i < Game.playerNumber; i++)
		{
			Game.playerCards[i] = new ArrayList();
		}
		dealCards();
		randomTop();
		drawAmount = drawUntilValid();
		currentWindow = new PlayerWindow(currentPlayer, drawAmount);
	}

	//draws cards until current player has a playble one
	public static int drawUntilValid()
	{
		int i;
		for (i = 0; !hasPlayableCard(currentPlayer); i++)
		{
			drawCard(currentPlayer);
		}
		return i;
	}

	//returns player directly nextt to you in the order (not affected by skips)
	public static int nextPlayer()
	{
		int next = currentPlayer + order;
		if (next >= playerNumber)
		{
			next = next % playerNumber;
		}
		else if (next < 0)
		{
			next = playerNumber + next;
		}
		return next;
		
	}
	
	//method to create a pause/nextplayer window. checks player who just placed won.
	public static void pauseWindow()
	{
		//get rid of current player window
		currentWindow.dispose();
		//log plus4/wild cards since theyre special and cannot be logged in the placeCard method without not accounting for the colour changed to
		if (topCard instanceof Wild || topCard instanceof Plus4)
			logAction(topCard);
		//reset placed boolean
		placed = false;
		//run post game frame if someone wins
		if (checkGame())
		{
			pgf = new PostGameFrame(currentPlayer);
		}
		else //no one has won one yet
		{
			//set currentplayer to the next player
			cycle();
			//draw until playable
			drawAmount = drawUntilValid();
			//load transition window
			pauseFrame = new NextWindow();
		}
		
	}
	//adds string to log
	public static void logAction(Card placedCard)
	{
		log.add(names[currentPlayer] + " placed " + placedCard.cardID);
	}
	//run transition window
	public static void nextWindow()
	{
		pauseFrame.dispose();
		currentWindow = new PlayerWindow(currentPlayer, drawAmount);

	}
	//check whether or not player has a playable card. used in drawUntilValid method.
	public static boolean hasPlayableCard(int player)
	{
		for (int i = 0; i < playerCards[player].size(); i++)
		{
			if (playerCards[player].get(i).validPlace(topCard))
			{
				return true;
			}
		}
		return false;
	}
	//array of colours to associate each colour with an array index (0 is red, 1 is yellow, etc.) so that i do not have to use switch statements.
	
	//set currentplayer to next player
	public static void cycle()
	{
		currentPlayer += amountToCycle * order;
		if (currentPlayer >= playerNumber)
		{
			currentPlayer = currentPlayer % playerNumber;
		}
		else if (currentPlayer < 0)
		{
			currentPlayer = playerNumber + currentPlayer;
		}
		amountToCycle = 1;
	}
	//returns player that plays next. (affected by skip cards)
	public static int returnNextCycle()
	{
		int nextCycle = currentPlayer + amountToCycle * order;
		if (nextCycle >= playerNumber)
		{
			nextCycle = nextCycle % playerNumber;
		}
		else if (nextCycle < 0)
		{
			nextCycle = playerNumber + nextCycle;
		}
		return nextCycle;
	}

	//gets the order 
	public static int getOrder()
	{
		return order;
	}


	/* code that was used for the console version of "one"
	public static void play (int player)
	{
		System.out.println("Player " + player + " turn");
		System.out.print("Top card right now is ");
		topCard.printCard();
		int i;
		for (i = 0; !hasPlayableCard(player); i++)
		{
			drawCard(player);
		}
		if (i != 0)
		{
			System.out.println("Drew " + i + " cards");
		}
		
		printCards(player);
		int cardToPlace = intInput("What card would you like to place?", "Please enter a valid card index.", 0, playerCards[player].size() - 1);
		while (!playerCards[player].get(cardToPlace).validPlace(topCard))
		{
			cardToPlace = intInput("That card cannot be placed.", "Please enter a valid card index.", 0, playerCards[player].size() - 1);
		}
		placeCard(cardToPlace, player);
		System.out.print("Top card right now is ");
		topCard.printCard();
		pause("Enter anything to continue");
	}
	
	public static void pause (String prompt)
	{ //change for gui
		Scanner pauser = new Scanner(System.in);
		System.out.println(prompt);
		String poop = pauser.nextLine();
		System.out.print("\033[H\033[2J");
        System.out.flush();
	}
	*/

	//makes the player draw a card
	public static void drawCard(int player)
	{
		playerCards[player].add(randomCard());
	}
	//place card method
	public static void placeCard(int card, int player)
	{
		//get rid of old card and put in discard pile
		Card oldCard = topCard;
		//reset the button (and colour for plus4 and wild cards)
		oldCard.refresh();
		discardPile.add(oldCard);
		//set new topcard
		topCard = playerCards[player].get(card);
		//activate special card properties (like plus2, reverse, etc);
		playerCards[player].get(card).special();
		//remove card from player hand
		playerCards[player].remove(card);

		//log non wild/plus4 cards here because they do not need to have a colour selected
		if (!(topCard instanceof Wild || topCard instanceof Plus4))
		{
			logAction(topCard);
		}
	}
	//print cards method. used for testing
	public static void printCards(int player)
	{
		for (int i = 0; i < playerCards[player].size(); i++)
		{
			System.out.print(i + ") ");
			playerCards[player].get(i).printCard();
		}
	}

	//check whether someone has won (their arraylist for their hand is empty)
	public static boolean checkGame()
	{
		if (playerCards[currentPlayer].isEmpty())
		{
			return true;
		}
		return false;
	}

	//generate a random topcard at the start of the game
	public static void randomTop()
	{
		topCard = randomCard();
		//if the randomlygenerated card is plus4 or wild, set it to a random colour.
		if (topCard instanceof Wild || topCard instanceof Plus4)
		{
			topCard.changeColour((int)(Math.random() * 4));
		}
		//log it
		log.add(0, "Starting card is " + topCard.cardID);
	}
	//gets a random card from the deck and removes it from the deck.
	private static Card randomCard()
	{
		//get a random card index
		int cardIndex = (int)(Math.random() * deck.size());
		Card cardToGet = deck.get(cardIndex);
		//remove it
		deck.remove(cardIndex);
		//merge with discard pile if its empty.
		if (deck.isEmpty())
		{
			deck.addAll(discardPile);
		}
		//return the card
		return cardToGet;
	}
	//give each player a set amount of cards at the beginning
	public static void dealCards()
	{
		for (int i = 0; i < playerCards.length; i++)
		{
			for (int j = 0; j < initialCardCount; j++)
			{
				playerCards[i].add(randomCard());
			}
		}
	}
	//switches the order of play
	public static void switchOrder()
	{
		order = order==1?-1:1;
	}
	//method only used once. Creates full list of cards.
	public static void initCards()
	{
		for (int col = 0; col < 4; col++)
		{
			for (int num = 1; num <= 9; num++)
			{
				fullCards.add(new NumberCard(num, col));
				fullCards.add(new NumberCard(num, col));
			}
			fullCards.add(new NumberCard(0, col));
			fullCards.add(new Reverse(col));
			fullCards.add(new Reverse(col));
			fullCards.add(new Skip(col));
			fullCards.add(new Plus2(col));
			fullCards.add(new Skip(col));
			fullCards.add(new Plus2(col));
		}

		for (int i = 0; i < 4; i++)
		{
			fullCards.add(new Plus4());
			fullCards.add(new Wild());
		}
	}
	//int input method i made in the bookingsystem project. Very versatile.
	public static int intInput (String prompt, String errorPrompt, int lowBound, int upBound)
	{
		//scanner and variable to store input
		Scanner intreader = new Scanner (System.in);
		int intIn;
		//prints prompt and > to indicate user input
		System.out.print(prompt + "\n\t> ");
		//try to use nextInt. will throw exception if something stupid is entered, like a String or double.
		try
		{
			intIn = intreader.nextInt();
		}
		catch(Exception e)
		{ //if exception happens, set intIn as an integer outside of the bounds, so it wont throw an exception but still sees it as invalid.
			intIn = lowBound - 1;
		}
		finally
		{ //clear scanner
			intreader = new Scanner (System.in);
		}
		//ask for input while it is invalid (not between bounds.)
		while (intIn < lowBound || intIn > upBound)
		{//same as before, but with the errorprompt this time.
			System.out.print(errorPrompt + "\n\t> ");
			try
			{
				intIn = intreader.nextInt();
			}
			catch(Exception e)
			{
				intIn = lowBound - 1;
			}
			finally
			{
				intreader = new Scanner (System.in);
			}
			
		}
		System.out.println("");
		//return the input once everything is okay.
		return intIn;
	}
}