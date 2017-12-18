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
	//�NPlayer���P��W (�N�Y��쫬�O��Player[]���ܼ�(instance field)���A��setter)�A
	//pos���P���m�A�̦hMAXPLAYER�H�Ap�h��Player instance�C
	public void set_player(int pos, Player p) {
		PlayersOnTable[pos] = p;
	}
	//�^�ǩҦ��b�P��W��player�A�N�Y�^�ǫ��O��Player[]���ܼ�(instance field)�ܼơA��getter
	public Player[] get_player() {
		return PlayersOnTable;	
	}
	//�NDealer���P��W (�N�Y�NDealer��� ���O��Dealer ���ܼ�(instance field) ���A���ܼƤ�setter)�C
	public void set_dealer(Dealer d) {
		dealer=d;
	}
	//�^��dealer���}�����i�P�A�]�N�O�ĤG�i�P
	public Card get_face_up_card_of_dealer() {
		Card dealercard=dealer.getOneRoundCard().get(1);
		return dealercard;
	}
	//�ШC�Ӫ��a���۩I (���� say_hello())
	//�ШC�Ӫ��a�U�` (���� make_bet())
	//�C�Ӫ��a�U���`�n�s�bpos_betArray�Ѥ���ϥ�
	private void ask_each_player_about_bets() {
		for(int i=0;i < PlayersOnTable.length;i++) {
			PlayersOnTable[i].sayHello();
			int bet=PlayersOnTable[i].makeBet();
			System.out.println(PlayersOnTable[i].getName()+" bet "+bet+" dollars.");
			pos_betArray[i]=bet;
		}
	}
//	�o�P�����a����a�A���o��i���}���P�����a
//	�A�@�i�\�۪��P�A�H�Τ@�i���}���P�����a�C(����: setOneRoundCard())
//	�o�P�����a��A�b�e���W�L�X���a���}���P"Dealer's face up card is " (����: printCard())
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
//	�ݨC�Ӫ��a�n���n�P (����: hit_me(Table tbl))
//	�аѷ�HW4.java�A�ϥ�do while�j��߰ݦU�Ӫ��a
//	�߰ݶ���: ���ӥ[�J�P�������Ǧөw (��m)
//	�p�G���a�n�P�A�Цb�e���W�L�X"Hit! "+ ���a���W�r+ "��s cards now: "�A�ç⪱�a�n�P�᪺����P�L�X�C�̫�N�s���P��setOneRoundCard()�]�w�^���a����C
//	�p�G�z�F�A�Ф��n�A�ݪ��a�O�_�n�P
//	�p�G���a���n�P�F�A�Цb�e���W�L�X ���a���W�r+"Pass hit!"
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
	//�߰ݲ��a�O�_�n�P�A������A�L�X"Dealer's hit is over!"
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
//	�L�X���a���I�ƩM�P"Dealer's card value is "+�`�I��+" ,Cards:"+�P�� (����: printAllCard())
//	���a��C�@�Ӫ��a���P��
//	�w��C�Ӫ��a�A���L�X ���a�m�W+" card value is "+���a�`�I��
//	�ݽ�Ĺ�A�n�O���aĹ�A�⪱�a�w�X�S���A�L�X", Loss "+�U�`�w�X��+" Chips, the Chips now is: "+���a�̷s�w�X��(����: get_current_chips())
//	�n�O���a��A�h�ߪ��a�P�U�`�w�X�۲Ť��w�X�A�L�X",Get "+�U�`�w�X��+" Chips, the Chips now is: "+���a�̷s�w�X��(����: get_current_chips())
//	���餣Ĺ�A�L�X",chips have no change! The Chips now is: "+���a�̷s�w�X��(����: get_current_chips())
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
