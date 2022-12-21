import java.util.*;

class Main {
	public static void main(String[] args) {
		
		//print out  title
		System.out.println("\"one\"\n");
		//get number of players
		Game.playerNumber = Game.intInput("How many players? (2 - 6)", "Please enter a valid number of players. (2 - 6)", 2, 6);
		//initialize names and decks
		Game.playerCards = new ArrayList[Game.playerNumber];
		Game.names = new String[Game.playerNumber];
		//get the names of the players
		Game.getNames();
		//initialize the player arraylists
		for (int i = 0; i < Game.playerNumber; i++)
		{
			Game.playerCards[i] = new ArrayList();
		}
		//intialize the cards
		Game.initCards();
		//fill the deck
		Game.deck = Game.fullCards;
		//distribute cards
		Game.dealCards();
		//set the topcard to something random
		Game.randomTop();
		//draw cards if first player doesnt have playable cards
		Game.drawAmount = Game.drawUntilValid();
		//create the player window
		Game.currentWindow = new PlayerWindow(0, Game.drawAmount);
	}
}