class Main{
  public static void main(String[] args) {
    int heroHP = 100;
    int heroHeal = new java.util.Random().nextInt(10) + 1;
    int enemyHP = 100;
    System.out.println("ゴミムシが現れた！");
    while(enemyHP >= 0) {
      System.out.println("１～３を選んでください　1:攻撃　2:回復　3:逃げる");
      int command = new java.util.Scanner(System.in).nextInt();
      if (command == 1) {
        int heroPower = new java.util.Random().nextInt(30) + 10;
        System.out.println("貴様の攻撃!　" + heroPower + "のダメージを与える");
        enemyHP -= heroPower;
        int enemyPower = new java.util.Random().nextInt(20)+1;
        System.out.println("ゴミムシの攻撃！　" + enemyPower + "のダメージを受けた");
        heroHP -= enemyPower;
        if((enemyHP <= 0) || (heroHP <= 0)) {
          System.out.println("--------------貴様のHP:" + heroHP + "--------------");
          System.out.println("--------------ゴミムシのHP:0--------------");
        } else {
          System.out.println("--------------貴様のHP:" + heroHP + "--------------");
          System.out.println("--------------ゴミムシのHP:" + enemyHP + "--------------");
        }
      } else if (command == 2) {
        int healHero = heroHeal;
        heroHP += healHero;
        System.out.println("回復だと！？情けない奴め！");
        System.out.println("貴様は" + healHero + "回復した！");
        System.out.println("--------------貴様のHP:" + heroHP + "--------------");
      }
    }
    if(enemyHP <= 0) {
      System.out.println("ゴミムシをやっつけた！");
      System.out.println("貴様の勝利！よくやった！「また今回も私を死なせてくれないようだ・・・」");
      
    }
  }
}