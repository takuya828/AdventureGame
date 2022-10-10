import java.util.concurrent.TimeUnit;
import java.util.Random;

class Main{
  public static void main(String[] args) {
    Hero hero = new Hero(200, 50, 30, 80);
    int walkCount = 0;
    while(walkCount < 100) {
      if (isHit(20)) {
        Enemy kuribo = new Enemy(40, 10, "クリボー", 50);
        walkCount++;
        fight(hero, kuribo);
      } else {
        System.out.println("Walk");
        sleepMilliSecond(500);
        walkCount++;
      }
    }
    Enemy koopa = new Enemy(200, 60, "クッパ", 999);
    fight(hero, koopa);
  }

  public static void sleepMilliSecond(int time) {
    try {
      TimeUnit.MILLISECONDS.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static boolean isHit(int percent) {
    int num = new Random().nextInt(100) + 1;
    boolean box;
    if (num < percent) {
      box = true;
    } else {
      box = false;
    }
    return box;
  }

  public static void displayMenu() {
    System.out.println();
    System.out.println("====================================");
    System.out.println("| 1:攻撃                            |");
    System.out.println("| 2:回復                            |");
    System.out.println("| 3:逃げる                          |");
    System.out.println("------------------------------------");
    System.out.print("=> ");
  }


  public static void displayHeroStatus(Hero hero) {
    System.out.println();
    System.out.println("====================================");
    System.out.println("|貴様のHP" + hero.hp + "                       |");
    System.out.println("|貴様のMP" + hero.mp + "                        |");
    System.out.println("------------------------------------");
  }

  public static void displayEnemyStatus(Enemy enemy) {
    System.out.println();
    System.out.println("====================================");
    System.out.println("|" + enemy.name + "のHP" + enemy.hp + "                       |");
    System.out.println("------------------------------------");
  }

  public static boolean fight(Hero hero, Enemy enemy) {
    System.out.println(enemy.name + "が現れた！");
    while(enemy.isAlive() && hero.isAlive()) {
      sleepMilliSecond(1000);
      displayHeroStatus(hero);
      displayMenu();
      int command = new java.util.Scanner(System.in).nextInt();
      System.out.println("");
      if (command == 1) {
        enemy = hero.attack(enemy);
        if (enemy.isAlive()){
          hero = enemy.attack(hero);
        }
      } else if (command == 2) {
        hero = hero.heal(hero);
        hero = enemy.attack(hero);
      } else if (command == 3) {
        System.out.println("貴様は『逃げる』を選択した！この臆病者！");
        sleepMilliSecond(1000);
        if (hero.runAway() == true) {
          System.out.println("成功した！貴様は『逃げた』！このチキン野郎！");
          return true;
        } else {
          System.out.println("逃げられると思ったか！さっさと戦え！");
          sleepMilliSecond(2000);
          System.out.println("ほれ！" + enemy.name + "の攻撃がきたぞ！くらえ！");
          sleepMilliSecond(1000);
          hero = enemy.attack(hero);
        }
      }
    }
    if(!hero.isAlive()) {
      Main.sleepMilliSecond(2000);
      System.out.println("貴様は" + enemy.name + "に負けた！「死ぬには良い日だ…」");
      sleepMilliSecond(1000);
      System.out.println("この『敗北者』め！");
      return false;
    } else {
      System.out.println(enemy.name + "をやっつけた！");
      sleepMilliSecond(1000);
      System.out.println("貴様の勝利！よくやった！「また今回も私を死なせてくれないようだ・・・」");
      sleepMilliSecond(1000);
      hero.getExp(enemy);
      System.out.println("貴様は経験値として" + enemy.exp + "を獲得した！");
      hero.levelUp();
      System.out.println(hero.level + "にレベルが上がった！");
      return true;
    }
  }

}


class Hero {
  public int hp;
  public int mp;
  public int power;
  public int healPower;
  public int exp;
  public int level;

  public Hero(int hp, int mp, int power, int healPower) {
    this.hp = hp;
    this.mp = mp;
    this.power = power;
    this.healPower = healPower;
    this.exp = 500;
    this.level = 1;
  }

  public Enemy attack(Enemy enemy) {
    System.out.println("貴様の攻撃！");
    Main.sleepMilliSecond(1000);
    int randomPower = 0;
    if(Main.isHit(20)) {
      System.out.println("「そろそろ本気を出すか・・・ハァアッ！」");
      Main.sleepMilliSecond(1000);
      randomPower = (new java.util.Random().nextInt(40) + 1) * (int)Math.pow(1.1, this.level - 1);
      enemy.hp -= (int)((this.power * (int)Math.pow(1.1, this.level - 1)) * 1.5 + randomPower);
      System.out.println("貴様の会心の一撃により" + (int)((this.power * (int)Math.pow(1.1, this.level - 1)) * 1.5 + randomPower) + "のダメージを与えた");
    } else {
      randomPower = (new java.util.Random().nextInt(40) + 1) * (int)Math.pow(1.1, this.level - 1);
      enemy.hp -= (this.power * (int)Math.pow(1.1, this.level - 1)) + randomPower;
      System.out.println((int)(((this.power * (int)Math.pow(1.1, this.level - 1)) + randomPower)) + "のダメージを与えた");
    }
    Main.sleepMilliSecond(2000);
    if (enemy.hp < 0) {
      enemy.hp = 0;
    }
    return enemy;
  }

  public Hero heal(Hero hero) {
    System.out.println("回復だと！？情けない奴め！");
    Main.sleepMilliSecond(2000);
    if (hero.mp >= 5){
      int randomHeal = new java.util.Random().nextInt(30) + 1;
      hero.hp += this.healPower + randomHeal;
      System.out.println("貴様は" + (this.healPower + randomHeal) + "回復した！");
      Main.sleepMilliSecond(1000);
      hero.mp -= 5;
      System.out.println("貴様のMPは残り" + hero.mp);
      Main.sleepMilliSecond(1000);
    } else {
      System.out.println("MPが足りない！貴様はもう回復できない！");
      Main.sleepMilliSecond(2000);
    }
    return hero;
  }

  public boolean runAway() {
    int judgement = new java.util.Random().nextInt(4);
    if(judgement == 0) {
      return true;
    } else {
      return false;
    }
  }

  public void getExp(Enemy enemy) {
    this.exp += enemy.exp;
  }

  public void levelUp() {
    this.level = (int)Math.floor(Math.log((float)this.exp / (float)500.0) / Math.log(1.2) + 1);
  }

  public boolean isAlive() {
    if(this.hp <= 0) {
      boolean box;
      box = false;
      return box;
    } else {
      boolean box;
      box = true;
      return box;
    }
  }
}

class Enemy {
  public int hp;
  public int power;
  public String name;
  public int exp;

  public Enemy(int hp, int power, String name, int exp) {
    this.hp = hp;
    this.power = power;
    this.name = name;
    this.exp = exp;
  }

  public Hero attack(Hero hero) {
    System.out.println(this.name + "の攻撃！");
    int randomPower = new java.util.Random().nextInt(20) + 1;
    hero.hp -= this.power + randomPower;
    System.out.println((this.power + randomPower) + "のダメージを受けた");
    if (hero.hp < 0) {
      hero.hp = 0;
    }
    return hero;
  }

  public boolean isAlive() {
    if(this.hp <= 0) {
      boolean box;
      box = false;
      return box;
    } else {
      boolean box;
      box = true;
      return box;
    }
  }
}