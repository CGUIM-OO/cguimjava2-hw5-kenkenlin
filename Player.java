public class Player extends Person{
	private String name;
	private int chips;
	private int bet;
//	private ArrayList<Card> oneRoundCard;
	public Player(String inputname, int inputchips) {
		name=inputname;
		chips=inputchips;
	}

	public String getName() {
		return name;
	}
	//- �U�`�A�^�ǹw�p�U�`���w�X
    //- �򥻤U�`�G�@��1��
	public int makeBet() {
		if(getCurrentChips()>1) {
			bet=1;
			return bet;
		}else {
			bet=0;
			return bet;
		}
	}
	//- �O�_�n�P�A�O�^��true�A���A�n�P�h�^��false
    //- �򥻰Ѧұ���G16�I�H�U�n�P�A17�I�H�W���n�P
    //- ���ܡG��oneRoundCard�Ӻ�
	public boolean hit_me(Table table) {
		boolean hithop =false;
		if(getTotalValue()>17) {
			return hithop;
		}else {
			hithop=true;
			return hithop;
		}
	}
	//�^�Ǫ��a�{���w�X
	public int getCurrentChips() {
		return chips;
	}
	//���a�w�X�ܰʡAsetter
	public void increaseChips(int diff){
		chips+=diff;
	}
	
	public void sayHello() {
		System.out.println("Hello, I am " + name + ". ");
	    System.out.println("I have " + chips + " chips.");
	}
	
}
