import java.util.ArrayList;
import java.util.Random;
/*
 * Description: TODO: this class contain 3 methods, Deck ,printDeck and getAllCards.
 * The method, Deck, puts N decks of poker into "ArrayList<Card>"
 * And,the next one, printDeck, which is called by Line.21,"deck.printDeck()", can ...
 * ...print card in ArrayList<Card> sequentially.
 * 
 */

public class Deck{
	private ArrayList<Card> cards;
	private ArrayList<Card> usedCard;
	private ArrayList<Card> openCard;
	public int nUsed;
	//TODO: Please implement the constructor (30 points)
	public Deck(int nDeck){
		cards=new ArrayList<Card>();
		usedCard=new ArrayList<Card>();
		openCard=new ArrayList<Card>();
		for (int i = 1 ;i<=nDeck;i++) {
			for (Card.Suit s : Card.Suit.values()) {
				for(int j =1;j<=13;j++) {
					Card card=new Card(s,j);
					cards.add(card);
				}	
			}
		}
		shuffle();
	}	 
	
	public void printDeck(){
		//Hint: print all items in ArrayList<Card> cards, 
		//TODO: please implement and reuse printCard method in Card class (5 points)
		
		for (int index = 0; index < cards.size(); index++){
			Card account = cards.get(index);
			account.printCard(account.getSuit(),account.getRank());
		}
	}
	
	public void shuffle() {
		Random rnd = new Random();
		//Getting the rest of UsedCard
		for (int  i=0; i< usedCard.size();i++){
			cards.add(usedCard.get(i));	
		}
		while(usedCard.size()>0){
			usedCard.remove(0);nUsed=usedCard.size();
		}
		//wiping all elements in openedCard method.
		while(openCard.size()>0){
			openCard.remove(0);
		}
		for (int times=0; times <2*cards.size();times++) {
			int j = rnd.nextInt(cards.size());
			Card change=cards.get(j);
			cards.remove(j);
			int i = rnd.nextInt(cards.size());
			cards.add(i,change);		
		}

		
	}
	
	public Card getOneCard(boolean isOpened) {
		if (cards.size()==0) {
			shuffle();			
		}
		Card TheOneCard =cards.get(0);
		if(isOpened) {
		usedCard.add(TheOneCard);
		openCard.add(TheOneCard);
		}else {	
		usedCard.add(TheOneCard);
		}
		nUsed=usedCard.size();
		cards.remove(0);
		return TheOneCard;
	}
	public ArrayList<Card> getAllCards(){
		return cards;
	}
	//return the openCard
	public ArrayList<Card> getOpenedCard(){
		return openCard;
	}
}