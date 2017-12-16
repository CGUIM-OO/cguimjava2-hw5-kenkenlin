# 物件導向程式設計HW5說明

1. 點作業[連結](https://classroom.github.com/a/oW9npdaP)，接受作業，並用github desktop將作業
	Clone到本機端資料夾CGUIMJava2_HW5 (自行新增此資夾)
2. 新增一個HW5專案
3. 將HW4中，自己撰寫的Card.java，Deck.java，Player.java複製一份到本機端資料夾CGUIMJava2_HW5，
	並拖拉到新的HW5專案內（選link）
4. 將本機端資料夾CGUIMJava2_HW5中的主程式 (HW5.java, Person.java, Dealer.java) ，拖拉到HW5專
	案內（選link）
5. Player.java
  - 修改Player.java，使其繼承Person
  - 刪掉Player.java內重複宣告的變數與方法 (提示: Person.java內非abstract method的內
	容都不應在Player.java內出現)
  - override Person.java內的abscract method
    - 注意: hit_me method的輸入參數有改，請對應新增
    - 注意: Person.java中，有一setOneRoundCard()，為oneRoundCard之setter，供後續程式使用
    - 注意: Person.java中，有一printAllCard()，供後續程式使用
      - 提示: Dealer.java也是繼承Person，可參考使用

6. Table.java

  - 可依需求增加field和method，但新增加的field和method一定為private層級
  - Table.java所需field (除static field外全為private)與method，若無寫明變數名稱，表示可自
	行取名
  - 新增Class field, final, int, MAXPLAYER = 4
    - 最多一張牌桌能坐幾個人
  - 新增 一型別為Deck的變數(instance field)，存放所有的牌
  - 新增 一型別為Player[] 的變數(instance field)，存放所有的玩家
  - 新增 一型別為Dealer 的變數(instance field)，存放一個莊家
  - 新增 int[] pos_betArray，存放每個玩家在一局下的注(instance field)
  - 新增 Constructor, 輸入參數為 int nDeck，在HW5.java內有實體化Table Class，可參考。
    - 在此Constructor中，請將Deck class實體化，並存入上述新增的Deck變數(instance field)
  - 新增 請初始化上述新增型別為Player[]的變數(instance field)，宣告一個長度是MAXPLAYER
	的Player array
  - public void set_player(int pos, Player p)
     - 將Player放到牌桌上 (意即放到型別為Player[]的變數(instance field)中，為setter)，pos為
	牌桌位置，最多MAXPLAYER人，p則為Player instance。
  - public Player[] get_player()
     - 回傳所有在牌桌上的player，意即回傳型別為Player[]的變數(instance field)變數，為getter
  - public void set_dealer(Dealer d)
    - 將Dealer放到牌桌上 (意即將Dealer放到 型別為Dealer 的變數(instance field) 中，為變數之
	setter)。
  - public Card get_face_up_card_of_dealer()
    - 回傳dealer打開的那張牌，也就是第二張牌
  - private void ask_each_player_about_bets()
    - 請每個玩家打招呼 (提示 say_hello())
    - 請每個玩家下注 (提示 make_bet())
    - 每個玩家下的注要存在pos_betArray供之後使用
  - private void distribute_cards_to_dealer_and_players()
    - 發牌給玩家跟莊家，先發兩張打開的牌給玩家，再一張蓋著的牌，以及一張打開的牌給莊家。
	(提示: setOneRoundCard())
    - 發牌給莊家後，在畫面上印出莊家打開的牌"Dealer's face up card is " (提示: printCard())
  - private void ask_each_player_about_hits()
    - 問每個玩家要不要牌 (提示: hit_me(Table tbl))
    - 請參照HW4.java，使用do while迴圈詢問各個玩家
    - 詢問順序: 按照加入牌局的順序而定 (位置)
    - 如果玩家要牌，請在畫面上印出"Hit! "+ 玩家的名字+ "’s cards now: "，並把玩家要牌後的
	完整牌印出。最後將新的牌用setOneRoundCard()設定回玩家物件。
    - 如果爆了，請不要再問玩家是否要牌
    - 如果玩家不要牌了，請在畫面上印出 玩家的名字+"Pass hit!"
  - private void ask_dealer_about_hits()
    - 詢問莊家是否要牌，完成後，印出"Dealer's hit is over!"
  - private void calculate_chips()
    - 印出莊家的點數和牌"Dealer's card value is "+總點數+" ,Cards:"+牌們 
	(提示: printAllCard())
    - 莊家跟每一個玩家的牌比
    - 針對每個玩家，先印出 玩家姓名+" card value is "+玩家總點數
    - 看誰贏，要是莊家贏，把玩家籌碼沒收，印出", Loss "+下注籌碼數+" Chips, the Chips now 
	is: "+玩家最新籌碼數(提示: get_current_chips())
    - 要是莊家輸，則賠玩家與下注籌碼相符之籌碼，印出",Get "+下注籌碼數+" Chips, the Chips 
	now is: "+玩家最新籌碼數(提示: get_current_chips())
    - 不輸不贏，印出",chips have no change! The Chips now is: "+玩家最新籌碼數
	(提示: get_current_chips())
  - public int[] get_palyers_bet()
    - 新增一method play()如下，使用方法見HW5.java

```
public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
```

輸出範例:

Hello, I am Palyer 1.

I have 300 chips.

Hello, I am Palyer 2.

I have 300 chips.

Hello, I am Palyer 3.

I have 300 chips.

Hello, I am Palyer 4.

I have 300 chips.

Dealer's face up card is Spade,8

Palyer 1's Cards now:Club,12

Diamond,8

Pass hit!

Palyer 1's hit is over!

Palyer 2's Cards now:Club,5

Diamond,9

Hit! Palyer 2's Cards now:Club,5

Diamond,9

Spade,8

Palyer 2's hit is over!

Palyer 3's Cards now:Heart,11

Heart,3

Hit! Palyer 3's Cards now:Heart,11

Heart,3

Club,12

Palyer 3's hit is over!

Palyer 4's Cards now:Spade,10

Diamond,6

Hit! Palyer 4's Cards now:Spade,10

Diamond,6

Club,3

Pass hit!

Palyer 4's hit is over!

Dealer's hit is over!

Dealer's card value is 23 ,Cards:Diamond,8

Spade,8

Club,2

Heart,1

Club,4

Palyer 1's Cards: Diamond,8

Spade,8

Club,2

Heart,1

Club,4

Palyer 1 card value is 18, Get 1 Chips, the Chips now is: 301

Palyer 2's Cards: Diamond,8

Spade,8

Club,2

Heart,1

Club,4

Palyer 2 card value is 22, chips have no change!, the Chips now is: 300

Palyer 3's Cards: Diamond,8

Spade,8

Club,2

Heart,1

Club,4

Palyer 3 card value is 23, chips have no change!, the Chips now is: 300

Palyer 4's Cards: Diamond,8

Spade,8

Club,2

Heart,1

Club,4

Palyer 4 card value is 19, Get 1 Chips, the Chips now is: 301
