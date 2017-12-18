import java.util.ArrayList;

public class Table {
	public final static int  MAXPLAYER = 4;
	private Deck AllCardsOnTable;
	private Player[] PlayersOnTable;
	private Dealer dealer;
	private int[] pos_betArray;
	public Table(int nDeck) {
		AllCardsOnTable=new Deck(nDeck);
		PlayersOnTable=new Player[nDeck];
		pos_betArray=new int[4];
	} 
	//將Player放到牌桌上 (意即放到型別為Player[]的變數(instance field)中，為setter)，
	//pos為牌桌位置，最多MAXPLAYER人，p則為Player instance。
	public void set_player(int pos, Player p) {
		PlayersOnTable[pos] = p;
	}
	//回傳所有在牌桌上的player，意即回傳型別為Player[]的變數(instance field)變數，為getter
	public Player[] get_player() {
		return PlayersOnTable;	
	}
	//將Dealer放到牌桌上 (意即將Dealer放到 型別為Dealer 的變數(instance field) 中，為變數之setter)。
	public void set_dealer(Dealer d) {
		dealer=d;
	}
	//回傳dealer打開的那張牌，也就是第二張牌
	public Card get_face_up_card_of_dealer() {
		Card dealercard=dealer.getOneRoundCard().get(1);
		return dealercard;
	}
	//請每個玩家打招呼 (提示 say_hello())
	//請每個玩家下注 (提示 make_bet())
	//每個玩家下的注要存在pos_betArray供之後使用
	private void ask_each_player_about_bets() {
		for(int i=0;i < PlayersOnTable.length;i++) {
			PlayersOnTable[i].sayHello();
			int bet=PlayersOnTable[i].makeBet();
			System.out.println(PlayersOnTable[i].getName()+" bet "+bet+" dollars.");
			pos_betArray[i]=bet;
		}
	}
//	發牌給玩家跟莊家，先發兩張打開的牌給玩家
//	再一張蓋著的牌，以及一張打開的牌給莊家。(提示: setOneRoundCard())
//	發牌給莊家後，在畫面上印出莊家打開的牌"Dealer's face up card is " (提示: printCard())
	private void distribute_cards_to_dealer_and_players() {
		for(int i=0;i < PlayersOnTable.length;i++) {
			ArrayList<Card> playerCard=new ArrayList<Card>();
			playerCard.add(AllCardsOnTable.getOneCard(true));
			playerCard.add(AllCardsOnTable.getOneCard(true));
			PlayersOnTable[i].setOneRoundCard(playerCard);
		}
		ArrayList<Card> dealerCard=new ArrayList<Card>();
		dealerCard.add(AllCardsOnTable.getOneCard(false));
		dealerCard.add(AllCardsOnTable.getOneCard(true));
		dealer.setOneRoundCard(dealerCard);
		System.out.print("Dealer's face up card is ");
		get_face_up_card_of_dealer().printCard(get_face_up_card_of_dealer().getSuit(),get_face_up_card_of_dealer().getRank());
	}
//	問每個玩家要不要牌 (提示: hit_me(Table tbl))
//	請參照HW4.java，使用do while迴圈詢問各個玩家
//	詢問順序: 按照加入牌局的順序而定 (位置)
//	如果玩家要牌，請在畫面上印出"Hit! "+ 玩家的名字+ "’s cards now: "，並把玩家要牌後的完整牌印出。最後將新的牌用setOneRoundCard()設定回玩家物件。
//	如果爆了，請不要再問玩家是否要牌
//	如果玩家不要牌了，請在畫面上印出 玩家的名字+"Pass hit!"
	private void ask_each_player_about_hits(Table tbl) {
		for(Player c:PlayersOnTable) {
			ArrayList<Card> playerCard=c.getOneRoundCard();
			System.out.println(c.getName()+"'s Cards now:");
			for(Card eachCard : playerCard){
				eachCard.printCard(eachCard.getSuit(),eachCard.getRank());
			}
			boolean hit=false;
			do{
				hit=c.hit_me(tbl);
				if(hit){
					playerCard.add(AllCardsOnTable.getOneCard(true));
					c.setOneRoundCard(playerCard);
					System.out.print("Hit! ");
					System.out.println(c.getName()+"'s Cards now:");
					c.printAllCard();
				}
				else{
					System.out.println(c.getName()+"'s hit is over!");
//					}
				}
			}while(hit);
		}
	}
	//詢問莊家是否要牌，完成後，印出"Dealer's hit is over!"
	private void ask_dealer_about_hits() {
		boolean hit=false;
		ArrayList<Card> dealerCard=dealer.getOneRoundCard();
		Table tbl=new Table(4);
		do{
			hit=dealer.hit_me(tbl); //this
			if(hit){
				dealerCard.add(AllCardsOnTable.getOneCard(true));
				dealer.setOneRoundCard(dealerCard);
				System.out.print("Hit! ");
				System.out.println("Dealer's Cards now:");
				for(Card eachCard : dealerCard){
					eachCard.printCard(eachCard.getSuit(),eachCard.getRank());
				}
			}
			else{
				System.out.println("Dealer, Pass hit!");
				System.out.println("Dealer, Final Card:");
				for(Card eachCard : dealerCard){
					eachCard.printCard(eachCard.getSuit(),eachCard.getRank());
				}
			}
		}while(hit);
	}
//	印出莊家的點數和牌"Dealer's card value is "+總點數+" ,Cards:"+牌們 (提示: printAllCard())
//	莊家跟每一個玩家的牌比
//	針對每個玩家，先印出 玩家姓名+" card value is "+玩家總點數
//	看誰贏，要是莊家贏，把玩家籌碼沒收，印出", Loss "+下注籌碼數+" Chips, the Chips now is: "+玩家最新籌碼數(提示: get_current_chips())
//	要是莊家輸，則賠玩家與下注籌碼相符之籌碼，印出",Get "+下注籌碼數+" Chips, the Chips now is: "+玩家最新籌碼數(提示: get_current_chips())
//	不輸不贏，印出",chips have no change! The Chips now is: "+玩家最新籌碼數(提示: get_current_chips())
	private void calculate_chips() {
		System.out.println("Dealer's card value is "+dealer.getTotalValue()+" ,Cards:");
		dealer.printAllCard();
		for(int i=0; i<PlayersOnTable.length;i++) {
			System.out.println(PlayersOnTable[i].getName()+"'s Cards: ");
			PlayersOnTable[i].printAllCard();
			System.out.print(PlayersOnTable[i].getName()+" card value is "+PlayersOnTable[i].getTotalValue());
			if(PlayersOnTable[i].getTotalValue()>21 && dealer.getTotalValue()>21){
				System.out.println(",chips have no change! The Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}else if(PlayersOnTable[i].getTotalValue()<=21&&dealer.getTotalValue()>21){
				PlayersOnTable[i].increaseChips(pos_betArray[i]);
				System.out.println(",Get "+pos_betArray[i]+" Chips, the Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}else if(PlayersOnTable[i].getTotalValue()>21&&dealer.getTotalValue()<=21){
				PlayersOnTable[i].increaseChips(-pos_betArray[i]);
				System.out.println(", Loss "+pos_betArray[i]+" Chips, the Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}else if(PlayersOnTable[i].getTotalValue()>dealer.getTotalValue()&&PlayersOnTable[i].getTotalValue()<=21){
				PlayersOnTable[i].increaseChips(pos_betArray[i]);
				System.out.println(",Get "+pos_betArray[i]+" Chips, the Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}else if(PlayersOnTable[i].getTotalValue()<dealer.getTotalValue()&&dealer.getTotalValue()<=21){
				PlayersOnTable[i].increaseChips(-pos_betArray[i]);
				System.out.println(", Loss "+pos_betArray[i]+" Chips, the Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}else{
				System.out.println(",chips have no change! The Chips now is: "+PlayersOnTable[i].getCurrentChips());
			}
		}
	}
	
	public int[] get_players_bet() {
		
		return pos_betArray;
	}
	public void play(Table tbl){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits(tbl);
		ask_dealer_about_hits();
		calculate_chips();
	}
	
}
