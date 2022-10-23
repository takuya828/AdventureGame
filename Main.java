import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Scanner;

class Main{
  public static void main(String[] args) {
    Hero hero = new Hero(200, 200, 50, 50, 30, 80);
    System.out.println("ステージを選んでください[1:草原　2:葛飾野高校　3:城]");
    System.out.print("=> ");
    int userSelect = new Scanner(System.in).nextInt();
    if (userSelect == 1) {
      System.out.println("草原…始まりの大地。始めの一歩。雑魚どもを蹴散らしていけ");
      sleepMilliSecond(2500);
      FirstStage.playFirstStage(hero);
      SecondStage.playSecondStage(hero);
    } else if (userSelect == 2) {
      System.out.println("葛飾野高校…OB達が洗脳されてしまった！？彼らを救え！");
      sleepMilliSecond(2500);
      SecondStage.playSecondStage(hero);
    }
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
}



  class FirstStage {
    public static void playFirstStage(Hero hero) {
      int walkCount = 0;
    while(hero.isAlive() && walkCount < 10) {
      if (isHit(10)) {
        Enemy kuribo = selectEnemy("クリボー");
        walkCount++;
        fight(hero, kuribo);
      } else if (isHit(10)) {
        Enemy nokonoko = selectEnemy("ノコノコ");
        walkCount++;
        fight(hero, nokonoko);
      } else if (isHit(3)) {
        Enemy rareSlime = selectEnemy("クリボー隊長");
        walkCount++;
        fight(hero, rareSlime);
      } else if (isHit(1)) {
        Enemy superRareSlime = selectEnemy("スーパーレアスライム");
        walkCount++;
        fight(hero, superRareSlime);
      } else {
        System.out.println("Walk");
        sleepMilliSecond(500);
        walkCount++;
      }
    }
    if (hero.isAlive() && walkCount >= 10) {
      Enemy koopa = selectEnemy("クッパ");
      fight(hero, koopa);
    }
    if (!hero.isAlive()) {
      System.out.println("貴様は負けた。ゲームは終了だ");
    }
  }


  public static Enemy selectEnemy(String enemyName) {
    if (enemyName == "クリボー") {
      Enemy kuribo = new Enemy(40, 10, "クリボー", 50);
      return kuribo;
    } else if (enemyName == "クッパ") {
      Enemy koopa = new Enemy(1, 60, "クッパ", 9999);
      return koopa;
    } else if (enemyName == "ノコノコ") {
      Enemy nokonoko = new Enemy(60, 25, "ノコノコ", 70);
      return nokonoko;
    } else if(enemyName == "クリボー隊長") {
      Enemy rareSlime = new Enemy(300, 30, "クリボー隊長", 1000);
      return rareSlime;
    } else if(enemyName == "スーパーレアスライム") {
      Enemy superRareSlime = new Enemy(100, 40, "スーパーレアスライム", 3000);
      return superRareSlime;
    } else {
      return new Enemy(0, 0, "敵", 0);
    }
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
    System.out.println("|貴様のLevel:" + hero.level + "                      |");
    System.out.println("|貴様のHP:" + hero.hp + "/" + hero.hpMax + "                   |");
    System.out.println("|貴様のMP:" + hero.mp + "/" + hero.mpMax + "                     |");
    System.out.println("------------------------------------");
    sleepMilliSecond(500);
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
      System.out.println("貴様の勝利！「我、最強…」");
      sleepMilliSecond(1000);
      hero.getExp(enemy);
      System.out.println("貴様は経験値として" + enemy.exp + "を獲得した！");
      sleepMilliSecond(1000);
      if (hero.canLevelUp()) {
        hero.levelUp();
        System.out.println(hero.level + "にレベルが上がった！");
        sleepMilliSecond(1500);
        displayHeroStatus(hero);
        sleepMilliSecond(500);
      }
      return true;
    }
  }
}


class SecondStage {
    public static void playSecondStage(Hero hero) {
      int walkCount = 0;
    while(hero.isAlive() && walkCount < 30) {
      if (isHit(10)) {
        Enemy kenta = selectEnemy("筋肉野郎");
        walkCount++;
        fight(hero, kenta);
      } else if (isHit(10)) {
        Enemy hiroshi = selectEnemy("じゃがいも小僧");
        walkCount++;
        fight(hero, hiroshi);
      } else if (isHit(10)) {
        Enemy yuki = selectEnemy("地獄のメガネ");
        walkCount++;
        fight(hero, yuki);
      } else if (isHit(10)){
        Enemy ryo = selectEnemy("神奈川県民");
        walkCount++;
        fight(hero, ryo);
      } else {
        System.out.println("Walk");
        sleepMilliSecond(500);
        walkCount++;
      }
    }
    if (hero.isAlive() && walkCount >= 30) {
      System.out.println("けんた&ひろし&ゆうき&りょう「合体…!!!");
      sleepMilliSecond(2000);
      Enemy muslePotetoGlass = selectEnemy("神奈川の地獄のマッスルポテトメガネくん");
      fight(hero, muslePotetoGlass);
    }
    if (!hero.isAlive()) {
      System.out.println("貴様は負けた。ゲームは終了だ");
    }
  }


  public static Enemy selectEnemy(String enemyName) {
    if (enemyName == "筋肉野郎") {
      Enemy kenta = new Enemy(40, 10, "筋肉野郎", 50);
      return kenta;
    } else if (enemyName == "神奈川の地獄のマッスルポテトメガネくん") {
      Enemy muslePotetoGlass = new Enemy(1000, 60, "神奈川の地獄のマッスルポテトメガネくん", 9999);
      return muslePotetoGlass;
    } else if (enemyName == "じゃがいも小僧") {
      Enemy hiroshi = new Enemy(60, 25, "じゃがいも小僧", 70);
      return hiroshi;
    } else if(enemyName == "地獄のメガネ") {
      Enemy yuki = new Enemy(300, 30, "地獄のメガネ", 1000);
      return yuki;
    } else if(enemyName == "神奈川県民") {
      Enemy ryo = new Enemy(100, 30, "神奈川県民", 1);
      return ryo;
    } else {
      return new Enemy(0, 0, "敵", 0);
    }
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
    System.out.println("|貴様のLevel:" + hero.level + "                      |");
    System.out.println("|貴様のHP:" + hero.hp + "/" + hero.hpMax + "                   |");
    System.out.println("|貴様のMP:" + hero.mp + "/" + hero.mpMax + "                     |");
    System.out.println("------------------------------------");
    sleepMilliSecond(500);
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
      System.out.println("貴様の勝利！「我、最強…」");
      sleepMilliSecond(1000);
      hero.getExp(enemy);
      System.out.println("貴様は経験値として" + enemy.exp + "を獲得した！");
      sleepMilliSecond(1000);
      if (hero.canLevelUp()) {
        hero.levelUp();
        System.out.println(hero.level + "にレベルが上がった！");
        sleepMilliSecond(1500);
        displayHeroStatus(hero);
        sleepMilliSecond(500);
      }
      return true;
    }
  }
}



class Hero {
  public int hp;
  public int hpMax;
  public int mp;
  public int mpMax;
  public int power;
  public int healPower;
  public int exp;
  public int level;

  public Hero(int hp, int hpMax, int mp, int mpMax, int power, int healPower) {
    this.hp = hp;
    this.hpMax = hpMax;
    this.mp = mp;
    this.mpMax = mpMax;
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
      System.out.println("「限界突破…！ハァアッ！」");
      Main.sleepMilliSecond(2000);
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
    System.out.println("「我に極上の癒しを…！」");
    Main.sleepMilliSecond(2000);
    int randomHeal = 0;
    if (hero.mp >= 5){
      randomHeal = (new java.util.Random().nextInt(30) + 1) * (int)Math.pow(1.1, this.level - 1);
      hero.hp += (this.healPower * (int)Math.pow(1.1, this.level - 1)) + randomHeal;
      System.out.println("貴様は" + (int)(((this.healPower * (int)Math.pow(1.1, this.level - 1)) + randomHeal)) + "回復した！");
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
    int diffLevel = (int)Math.floor(Math.log((float)this.exp / (float)500.0) / Math.log(1.2) + 1) - this.level;
    this.level = (int)Math.floor(Math.log((float)this.exp / (float)500.0) / Math.log(1.2) + 1);
    this.mpMax = (int)(this.mpMax * Math.pow(1.2, diffLevel));
    this.mp = this.mpMax;
    this.hpMax = (int)(this.hpMax * Math.pow(1.2, diffLevel));
    this.hp = this.hpMax;
  }

  public boolean canLevelUp() {
    int futureLevel = (int)Math.floor(Math.log((float)this.exp / (float)500.0) / Math.log(1.2) + 1);
    if(this.level < futureLevel) {
      return true;
    } else {
      return false;
    }
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