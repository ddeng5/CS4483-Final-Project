import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;



public class GamePanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener{

 //We are planning on implementing an achievement system by giving the player different kinds of medals
 //Image[]medalsSm=new Image[4];
 //Image[]medalsBg=new Image[4];

 //set up all our images
 Image upArrow = new ImageIcon("images/upArrow.png").getImage();
 Image announce = new ImageIcon("images/announcement.png").getImage();

 Image greenBarNormal = new ImageIcon("images/greenBar1.png").getImage();
 Image greenBarHover = new ImageIcon("images/greenBar2.png").getImage();
 Image greenBarPressed = new ImageIcon("images/greenBar3.png").getImage();
 Image yellowBarNormal = new ImageIcon("images/yellowBarNormal.png").getImage();
 Image yellowBarHover = new ImageIcon("images/yellowBarHover.png").getImage();
 Image yellowBarPressed = new ImageIcon("images/yellowBarPressed.png").getImage();

 //set up the speed up button
 Image fastMove = new ImageIcon ("images/fastMove2.png").getImage();
 Rectangle fastMoveRect = new Rectangle(1078,648,fastMove.getHeight(null),fastMove.getWidth(null));




 Button greenBarBut;
 Button yellowBarBut;

 boolean isButtonPressed = false;


 //A list of hints that will be randomly shown to the user
 String [] hints = new String[]{"A random hint pops up here everytime!","Upgrade the selected plane in the Information Box!","Upgrades make a plane stronger against enemies!","Boss comes in at Wave 10!",
 "Have good planes for the boss!","There are subwaves in waves!","You look great today!",
 "Keep an eye on the announcement box on top!","Press right arrow to speed up game on the bottom!"};


 //Initialize stage settings
 int mx=0; int my = 0;
 int checkx=0; int checky=0;
 int mapCount = 0;
 int coins=150;
 int lives=1;
 int nukes=3;
 int jetTimer=0;
 int spawnCounter=0;
 int bomberTimer=0;
 int heliTimer=0;
 int nukeTimer=0;
 int nukePlanePos=-300;
 int explosionGrowth=0;
 int balloonKilled = 0;


 boolean jetBuyable=false;
 boolean bomberBuyable=false;
 boolean heliBuyable=false;
 boolean nukeBuyable=true;
 boolean nuked=false;
 boolean paused=false;
 boolean quit = false;
 boolean gameOver=false;

 Image imgRedFlag,blueGlow,jetBt1,jetBt2,bomberBt1,bomberBt2,heliBt1,heliBt2,nukeBt1,nukeBt2,nukePlaneShadow,nukeExplosion,imgCursor,imgNormalCur,coinImg,sellButton,sellButtonHover,sellButtonPushed,pauseButtonPushed,pauseMenu,priceTag,winMenu,pauseButton,FFButton,brnzMedSm,silvMedSm,gldMedSm,platMedSm, brnzMedBg,silvMedBg,gldMedBg,platMedBg;
 Image i1,i2;

 String hint = "";

 ArrayList<Point>Path1;
 ArrayList<Point>Path2;
 ArrayList<Point>Path3;

 int frameTimer = 0;
 int wave=1;
 int subWave=0;
 int totalCoins=0;

 Cursor curRedFlag,defCursor,normalCur;

 ArrayList<Balloon> balloons = new ArrayList<Balloon>();
 ArrayList<Plane> planes = new ArrayList<Plane>();
 ArrayList<Bullet> bullets=new ArrayList<Bullet>();

 Font coinFont,coinFontBig;
 Plane selectedPlane = null;
 Balloon selectedBalloon=null;
 boolean balSpawn=false;
 String planeButton = "released2";
 private boolean []keys;
 Image[]explosions=new Image[28];

 int [][]waveStructure= new int[9][5];
 String infoBuyable = "";
 Rectangle bottomBarRect = new Rectangle(75,625,1125,150);


 String announceString = "";
 Point announcePoint = new Point(405,-announce.getHeight(null));
 int announceD = 0;
 int sitCounter=0;
 private float alpha = 1f;

 //Method that restarts the game to its initial state
 public void resetEverything(ArrayList<Point> fileA,ArrayList<Point> fileB,ArrayList<Point> fileC,Image map1,Image map2){


  Path1=fileA;
  Path2=fileB;
  Path3=fileC;

  i1=map1;
  i2=map2;

  announceString = "";
  announcePoint = new Point(405,-announce.getHeight(null));
  announceD = 0;
  sitCounter=0;

  balSpawn=false;

  quit=false;

  frameTimer = 0;
  wave=1;
  subWave=0;

  hint="";

  mx=0; my = 0;
  checkx=0; checky=0;
  mapCount = 0;
  coins=200;

  nukes=3;
  jetTimer=0;
  spawnCounter=0;
  bomberTimer=0;
  heliTimer=0;
  nukeTimer=0;
  nukePlanePos=-300;
  explosionGrowth=0;

  jetBuyable=false;
  bomberBuyable=false;
  heliBuyable=false;
  nukeBuyable=true;
  nuked=false;
  paused=false;
  quit = false;
  gameOver=false;
  lives=12;
  balloonKilled = 0;
  totalCoins=0;

  selectedPlane=null;
  selectedBalloon=null;

  planes.clear();
  bullets.clear();
  balloons.clear();


  planes.add(new Plane("bomber",this,new Point(500,600),250));
  //planes.add(new Plane("heli",this,new Point(500,600),100));
  //planes.add(new Plane("heli",this,new Point(500,600),200));


 }
 public void makeAnnouncement(Graphics g){
  g.setColor(Color.WHITE);
     g.drawImage(announce,(int)announcePoint.x,(int)announcePoint.y,this);
     g.drawString(announceString,((int)announcePoint.x+236)-announceString.length()*5,(int)announcePoint.y+15);
     announcePoint.translate(0,announceD);
     sitCounter++;
     if (announcePoint.y<=-announce.getHeight(null)){
      sitCounter=0;
     }
     if (announcePoint.y>0&&sitCounter>announce.getHeight(null)+100){
      announceD = -1;


     }else if(announcePoint.y>0){
      announceD=0;
     }
     if (announcePoint.y<-announce.getHeight(null)){
      announceD = 0;
     }
    }

    public void makeAnnouncement(String n){
     announceD=1;
     announceString=n;
    }


    //Game Panel Constructor
    public GamePanel(ArrayList<Point> fileA,ArrayList<Point> fileB,Image map1,Image map2){
     super();
     Path1 = fileA;
     Path2 = fileB;
     i1 = map1;
     i2 = map2;
     addMouseListener(this);
  addMouseMotionListener(this);
  addKeyListener(this);
  //planes.add(new Plane("heli",this,new Point(500,600),250));
  for(int i=0;i<9;i++){
   int []temp={6,8,8,8,10};
   waveStructure[i]=temp;
  }
  try{
   coinFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,12);
   coinFontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,25);
  }
  catch(IOException ioe){
   System.out.println("error loading AgentOrange.tff");
  }
  catch(FontFormatException e){
  }
    // Get UI set up for drawing
  imgRedFlag=new ImageIcon("images/redflag.png").getImage();
  imgNormalCur=new ImageIcon("images/cursor.png").getImage();
  blueGlow=new ImageIcon("images/blueGlow.png").getImage();
  jetBt1=new ImageIcon("images/jetbt1.png").getImage();
  jetBt2=new ImageIcon("images/jetbt2.png").getImage();
  bomberBt1=new ImageIcon("images/bomberbt1.png").getImage();
  bomberBt2=new ImageIcon("images/bomberbt2.png").getImage();
  heliBt1=new ImageIcon("images/helibt1.png").getImage();
  heliBt2=new ImageIcon("images/helibt2.png").getImage();
  nukeBt1=new ImageIcon("images/nukebt1.png").getImage();
  nukeBt2=new ImageIcon("images/nukebt2.png").getImage();
  nukePlaneShadow=new ImageIcon("images/nukePlaneShadow.png").getImage();
  sellButton=new ImageIcon("images/sellButton1.png").getImage();
  sellButtonHover=new ImageIcon("images/sellButton2.png").getImage();
  sellButtonPushed=new ImageIcon("images/sellButton3.png").getImage();
  nukeExplosion=new ImageIcon("images/exp/11.png").getImage();
  coinImg=new ImageIcon("images/coin.png").getImage();
  pauseButtonPushed=new ImageIcon("images/pause2.png").getImage();
  pauseMenu=new ImageIcon("images/pauseMenu.png").getImage();
  priceTag=new ImageIcon("images/priceTag.png").getImage();
  winMenu=new ImageIcon("images/winMenu.png").getImage();
  pauseButton=new ImageIcon("images/pauseButton.png").getImage();
  FFButton=new ImageIcon("images/FFButton.png").getImage();
  //brnzMedSm=new ImageIcon("images/bronzeMedalSmall.png").getImage();
  //silvMedSm=new ImageIcon("images/silverMedalSmall.png").getImage();
  //gldMedSm=new ImageIcon("images/goldMedalSmall.png").getImage();
  //platMedSm=new ImageIcon("images/platinumMedalSmall.png").getImage();

  //brnzMedBg=new ImageIcon("images/bronzeMedalBig.png").getImage();
  //silvMedBg=new ImageIcon("images/silverMedalBig.png").getImage();
  //gldMedBg=new ImageIcon("images/goldMedalBig.png").getImage();
  //platMedBg=new ImageIcon("images/platinumMetalBig.png").getImage();



  //medalsSm[0]=brnzMedSm;
  //medalsSm[1]=silvMedSm;
  //medalsSm[2]=gldMedSm;
  //medalsSm[3]=platMedSm;

  //medalsBg[0]=brnzMedBg;
  //medalsBg[1]=silvMedBg;
  //medalsBg[2]=gldMedBg;
  //medalsBg[3]=platMedBg;


  Toolkit tk = Toolkit.getDefaultToolkit();
  curRedFlag = tk.createCustomCursor( imgRedFlag, new java.awt.Point( 5, 5 ), "red flag" );
  normalCur = tk.createCustomCursor( imgNormalCur, new java.awt.Point( 5, 5 ), "cursor" );
  keys=new boolean[KeyEvent.KEY_LAST+1];
  for(int i=0;i<28;i++){
   explosions[i]=new ImageIcon("images/exp/"+(i+1)+".png").getImage();
  }

  greenBarBut=new Button(0,0,greenBarNormal,greenBarHover,greenBarPressed,this);
  yellowBarBut=new Button(0,0,yellowBarNormal,yellowBarHover,yellowBarPressed,this);
    }



    public void addNotify(){
  super.addNotify();
  requestFocus();
 }
 public void keyTyped(KeyEvent e){}
 //if a key gets pressed set that index of the list to true
 public void keyPressed(KeyEvent e){
  if (e.getKeyCode()<keys.length){
   keys[e.getKeyCode()]=true;
  }

 }
 //if the key is released set it to false
 public void keyReleased(KeyEvent e){
  if(e.getKeyCode()==KeyEvent.VK_RIGHT && selectedPlane!=null){
   selectedPlane=planes.get((planes.indexOf(selectedPlane)+1)%planes.size());
  }
  if(e.getKeyCode()==KeyEvent.VK_LEFT && selectedPlane!=null){
   selectedPlane=planes.get((planes.indexOf(selectedPlane)+planes.size()+1)%planes.size());
  }
  if(e.getKeyCode()==KeyEvent.VK_ESCAPE && selectedPlane==null){
   paused=!paused;
   Random rnd = new Random();
        hint = hints[rnd.nextInt(hints.length)];
  }
  if (e.getKeyCode()<keys.length){
   keys[e.getKeyCode()]=false;
  }
  if(e.getKeyCode()==KeyEvent.VK_ESCAPE && selectedPlane!=null){
   selectedPlane=null;
   planeButton="released2";
  }

 }

 public void checkKeys(){
  if(keys[KeyEvent.VK_C]&&selectedPlane!=null){
   selectedPlane.clearPath();
  }
 }
 //Method used to control movement of balloons, bullets and planes
 public void moveStuff(Graphics g){
  Balloon removeBall = null;
  for(Balloon b:balloons){
  //if a balloon isn't moving(aka destroyed), remove it
   if(b.move()==false){
    if(gameOver==false){
     lives-=1;
    }
    if(lives==0){
     gameOver=true;
    }
    removeBall = b;

   }
   if(b.getRect().contains(mx,my) && isButtonPressed && selectedPlane==null){
    selectedBalloon=b;

   }
  }
  if (removeBall!=null){
   balloons.remove(removeBall);
  }
  //update bullet positions
  for(Bullet y:bullets){
   y.move();
  }
  //update plane position and check if they venture off screen
  for (int i=0;i<planes.size();i++){
   Plane current = planes.get(i);
   current.move(g,selectedPlane,this);
   if(isOffScreen(current.getX(), current.getY()) == true){
   planes.remove(current);
   selectedPlane = null;
   }
  }
 }

 //Method responsible for painting game objects
 public void paintComponent(Graphics g){
  //System.out.println(balloonKilled);
  g.setColor(Color.RED);
  Balloon removeBall = null;
  g.drawImage(i1,0,0,this);

   //paint all balloons
  for(Balloon b:balloons){
   if(b.getZ()==0){
    b.draw(g);
   }
  }
  //draw current bullets
  for(Bullet y:bullets){
   y.draw(g);
  }


  g.drawImage(i2,0,0,this);
  for(Balloon b:balloons){
   if(b.getZ()==1){
    b.draw(g);
   }
  }
  if(paused==false){
   tickTimers();
   moveStuff(g);
  }
  bottomBar(g);
  topBar(g);

  //draw purchased planes
  for (int i=0;i<planes.size();i++){
   if(planes.get(i).getRect()!=null){
    planes.get(i).draw(g);
    //Sillhoutte of attack radius is shown when mouse hovers over plane
    if(planes.get(i).getRect().contains(mx,my)){
     g.drawOval(planes.get(i).getX()-planes.get(i).getRange()/2,planes.get(i).getY()-planes.get(i).getRange()/2,planes.get(i).getRange(),planes.get(i).getRange());
    }
    //Highlight selected plane
    if(planes.get(i)==selectedPlane){
     g.drawImage(blueGlow,selectedPlane.getX2()+selectedPlane.getGlowX(),selectedPlane.getY2()+selectedPlane.getGlowY(),this);
    }
   }

  }
  if (paused==false){
   setPlanePath(g);//DONT MOVE THIS, WILL BREAK
  }

  if(nuked&&!gameOver){
   nuke(g);
  }

  //Draw pause Menu
  if(paused){
   Button surrenderBut=yellowBarBut.clone(645-greenBarNormal.getWidth(null)/2-5,465-greenBarNormal.getHeight(null)-10);
   Button resumeBut=greenBarBut.clone(535,465);
   g.setColor(Color.DARK_GRAY);
   g.drawRect(300, 100, 700, 450);
   g.fillRect(300,100,700,450);
   g.setColor(Color.WHITE);
   resumeBut.draw(g);
   surrenderBut.draw(g);

   Font f = new Font(Font.SANS_SERIF, 1, 30);
   g.setFont(f);
   g.drawString("RESUME",645-65,512);
   g.drawString("PAUSED",585,150);
   g.drawString("SURRENDER",645-95,512-80);
   Font f2 = new Font(Font.SANS_SERIF, 1, 20);
   g.setFont(f2);

   g.drawString(hint,645-(hint.length()*5),270);

  }
  //Draw game over screen
  if(gameOver){
   planes.clear();
   g.setColor(Color.WHITE);
   g.setFont(coinFont);
   g.setColor(Color.DARK_GRAY);
   g.drawRect(450, 150, 450, 500);
   g.fillRect(450,150,450,500);
   g.setColor(Color.WHITE);

   //Draw Victory Screen
   if(lives>0){
    g.drawString("Level Cleared!",605,190);
    Button menuBut=yellowBarBut.clone(550,570);
    menuBut.draw(g);

    g.setFont(coinFontBig);
    g.drawString("Menu",620,615);
    g.setFont(coinFont);
    g.drawString("Balloon Kills: "+balloonKilled,585,300);
    g.drawString("Wave: "+wave+" Subwave: "+subWave,585,355);
    g.drawString("Total Money: "+totalCoins,585,410);
    g.drawString("Points: "+coins*5+totalCoins*2,585,475);
   }
   //Draw Loss screen
   else{
    Button menBut = yellowBarBut.clone(550,570);
    menBut.draw(g);
    g.setFont(coinFontBig);
    g.drawString("MENU",620,615);
    g.setFont(coinFont);
    g.drawString("Balloon Kills: "+balloonKilled,585,300);
    g.drawString("Wave: "+wave+" Subwave: "+subWave,585,355);
    g.drawString("Total Money: "+totalCoins,585,410);
    g.drawString("Points: "+coins*5+totalCoins*2,585,475);
    g.drawString("Level Failed!",605,190);


   }
  }
  makeAnnouncement(g);



 }
 public boolean getQuitStatus(){
  return quit;
 }

 public void setPlanePath(Graphics g){
  for (Plane p: planes){
   if (p.getRect()!=null){
    if (p.getRect().contains(mx,my) && selectedPlane == null&& isButtonPressed&&planeButton.equals("released2")){
     selectedPlane = p;
     selectedBalloon=null;
     planeButton = "selected";
    }
   }


  }
  if(selectedPlane!=null&&!bottomBarRect.contains(mx,my)){
   setCursor(curRedFlag);
  }else{
   setCursor(normalCur);
  }
  g.setColor(Color.RED);

 }
 public void checkPlaneShoot(){
  for(Plane a:planes){
   if(a.shoot(balloons)!=null){
    bullets.add(a.shoot(balloons));
   }
  }
 }
 public void cleanseLists(){
  for(int i=0;i<bullets.size();i++){

   if(bullets.get(i).reachedEnd()){
    bullets.remove(i);
   }
  }
  for(int i=0;i<balloons.size();i++){
   if(balloons.get(i).getAnimationCounter()==27){
    totalCoins+=balloons.get(i).getCoinValue();
    coins+=balloons.get(i).getCoinValue();
    balloonKilled++;
    balloons.remove(i);
   }
  }
 }
 public boolean isOffScreen(int x,int y){
  if(x<0 || x>1280){
   return true;
  }
  if(y<0 || y>750){
   return true;
  }
 return false;
 }
 public boolean isOffScreen(Point p){
  return isOffScreen((int)p.x,(int)p.y);
 }
 public void tickTimers(){
  frameTimer++;
  jetTimer++;
  bomberTimer++;
  heliTimer++;
  nukeTimer++;
 }
 public void mouseEntered( MouseEvent e ) {}
   public void mouseExited( MouseEvent e ) {}
   public void mouseClicked( MouseEvent e ) {
     switch(e.getModifiers()) {
      case InputEvent.BUTTON1_MASK: {
        break;
        }
      case InputEvent.BUTTON2_MASK: {
        break;
        }
      case InputEvent.BUTTON3_MASK: {
        for (Plane p: planes){
         if (p.getRect().contains(mx,my)){
          p.clearPath();
         }

        }
        break;
        }
      }
    }
   public void mousePressed( MouseEvent e ) {
      isButtonPressed = true;
      if (planeButton.equals("released")){
       if (selectedPlane!=null&& selectedPlane.getRect().contains(mx,my)){
        selectedPlane = null;
        planeButton = "pressed2";
       }else{
        for (Plane p:planes){
         if (p.getRect().contains(mx,my)){
          selectedPlane = p;
         }
        }
       }

      }

      if (planeButton.equals("released")&&!bottomBarRect.contains(mx,my) && paused==false){
       selectedPlane.addPoint(mx,my);

      }


      e.consume();
   }
   public void spawnState(){
    if(balloons.size()==0 && wave<10){
     balSpawn=true;
     if (!gameOver){
      subWave+=1;
      if(subWave==5){
       subWave=0;
       makeAnnouncement("Wave "+wave+" Cleared!");
       wave+=1;
      }
     }

    }
    if(balloons.size()==0 && wave==10){
     wave+=1;
     makeAnnouncement("Boss Incoming! Prepare Yourself");
     balloons.add(new Balloon("boss",Path1,this,explosions));
     balSpawn=false;

    }
    if(wave<10 && spawnCounter>=waveStructure[wave-1][subWave]){
     balSpawn=false;
     spawnCounter=0;
    }
    if(wave==11 && balloons.size()==0){
     gameOver=true;
    }

   }
    public void mouseReleased( MouseEvent e ) {
      Rectangle yellowBarRectWin = new Rectangle(530,570,232,82);
      Rectangle greenBarRect = new Rectangle(645-greenBarNormal.getWidth(null)/2,465,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));
      Rectangle yellowBarRect = new Rectangle(645-greenBarNormal.getWidth(null)/2-5,465-greenBarNormal.getHeight(null)-10,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));
      if(gameOver && yellowBarRectWin.contains(mx,my)){
       quit=true;
      }
      if (paused==true&&greenBarRect.contains(mx,my)){
       paused = false;
      }
      if (paused == true && yellowBarRect.contains(mx,my)){
       quit = true;
      }
      Rectangle pauseButtonRect=new Rectangle(206,648,72,72);
      isButtonPressed = false;
      if (planeButton.equals("selected")){
       planeButton = "released";
      }
      if (planeButton.equals("pressed2")){
       planeButton = "released2";
      }
      if (selectedPlane!=null){
       String [] upStr = selectedPlane.getUpgradeStrings();
    int [] upInt = selectedPlane.getUpgradeCount();




      if (infoBuyable.contains("Speed") && upInt[Arrays.asList(upStr).indexOf("Speed")]<=4){
       Rectangle r = new Rectangle(900,655,upArrow.getWidth(null),upArrow.getHeight(null));
       if (r.contains(mx,my)){
        coins-=selectedPlane.getSpeedCost();
        selectedPlane.setSpeed(selectedPlane.getSpeed()+1);
        int [] a = upInt;
      a[Arrays.asList(upStr).indexOf("Speed")]++;
      selectedPlane.setUpgradeCount(a);

       }

      }
      if (infoBuyable.contains("Power")&& upInt[Arrays.asList(upStr).indexOf("Power")]<=4){
       Rectangle r = new Rectangle(900,655+15,upArrow.getWidth(null),upArrow.getHeight(null));
       if (r.contains(mx,my)){
        coins-=selectedPlane.getPowerCost();
        selectedPlane.setPower(selectedPlane.getPower()+5);
        int [] a = upInt;
      a[Arrays.asList(upStr).indexOf("Power")]++;
      selectedPlane.setUpgradeCount(a);
       }
      }
      if (infoBuyable.contains("Fire Rate")&& upInt[Arrays.asList(upStr).indexOf("Fire Rate")]<=4){
       Rectangle r = new Rectangle(900,655+30,upArrow.getWidth(null),upArrow.getHeight(null));
       if (r.contains(mx,my)){
        coins-=selectedPlane.getFireRateCost();
        selectedPlane.setFireRate(selectedPlane.getFireRate()-selectedPlane.getFireRate()/5);
        int [] a = upInt;
      a[Arrays.asList(upStr).indexOf("Fire Rate")]++;
      selectedPlane.setUpgradeCount(a);
       }
      }
      if (infoBuyable.contains("Radius")&& upInt[Arrays.asList(upStr).indexOf("Radius")]<=4){
       Rectangle r = new Rectangle(900,655+45,upArrow.getWidth(null),upArrow.getHeight(null));
       if (r.contains(mx,my)){
        coins-=selectedPlane.getRadiusCost();
        selectedPlane.setRange(selectedPlane.getRange()+50);
        int [] a = upInt;
      a[Arrays.asList(upStr).indexOf("Radius")]++;
      selectedPlane.setUpgradeCount(a);
       }
      }
      }
      if(pauseButtonRect.contains(mx,my)){
       paused=!paused;
       Random rnd = new Random();
       hint = hints[rnd.nextInt(hints.length)];
      }
      e.consume();
      }
    public void mouseMoved( MouseEvent e ) {
      mx = e.getX();
      my = e.getY();
      e.consume();
   }
   public void nuke(Graphics g){

    g.drawImage(nukePlaneShadow,nukePlanePos,217,this);
    if(nukePlanePos<1280){
     nukePlanePos+=10;
    }
    if(nukePlanePos==1280&&explosionGrowth<700){

     for(int i=0;i<360;i+=5){
      g.drawImage(nukeExplosion,645+(int)(Math.cos(Math.toRadians(i))*explosionGrowth),317+(int)(Math.sin(Math.toRadians(i))*explosionGrowth),this);
      for(Balloon b:balloons){
       if(b.getRect().contains(645+(int)(Math.cos(Math.toRadians(i))*explosionGrowth),317+(int)(Math.sin(Math.toRadians(i))*explosionGrowth))){
        b.getHit(60);
       }
      }
     }
     explosionGrowth+=10;
    }
    if(explosionGrowth==700){
     nuked=false;
     nukePlanePos=-300;
     explosionGrowth=0;
    }
   }
    public void mouseDragged( MouseEvent e ) {
      mx = e.getX();
      my = e.getY();
      e.consume();
   }

   public void drawInfoBar(Plane selectedPlane,Graphics g,Rectangle infoBar){
     g.setColor(Color.WHITE);
     g.drawRoundRect(infoBar.x,infoBar.y,infoBar.width,infoBar.height,10,10);
     //g.setFont(coinFont);
     g.drawString("Speed: "+selectedPlane.getSpeed(),infoBar.x+5,infoBar.y+15);
     g.drawString("Power: "+selectedPlane.getPower(),infoBar.x+5,infoBar.y+30);
     g.drawString("Fire Rate: "+selectedPlane.getFireRate(),infoBar.x+5,infoBar.y+45);
     g.drawString("Radius: "+selectedPlane.getRange(),infoBar.x+5,infoBar.y+60);

     String [] upStr = selectedPlane.getUpgradeStrings();
    int [] upInt = selectedPlane.getUpgradeCount();

     g.setColor(Color.RED);
     g.fillRect(infoBar.x+225,infoBar.y+6,80,5);
     g.setColor(Color.GREEN);
     g.fillRect(infoBar.x+225,infoBar.y+6,16*(upInt[Arrays.asList(upStr).indexOf("Speed")]),5);

     g.setColor(Color.RED);
     g.fillRect(infoBar.x+225,infoBar.y+21,80,5);
     g.setColor(Color.GREEN);
     g.fillRect(infoBar.x+225,infoBar.y+21,16*(upInt[Arrays.asList(upStr).indexOf("Power")]),5);

     g.setColor(Color.RED);
     g.fillRect(infoBar.x+225,infoBar.y+36,80,5);
     g.setColor(Color.GREEN);
     g.fillRect(infoBar.x+225,infoBar.y+36,16*(upInt[Arrays.asList(upStr).indexOf("Fire Rate")]),5);

     g.setColor(Color.RED);
     g.fillRect(infoBar.x+225,infoBar.y+51,80,5);
     g.setColor(Color.GREEN);
     g.fillRect(infoBar.x+225,infoBar.y+51,16*(upInt[Arrays.asList(upStr).indexOf("Radius")]),5);


     g.setColor(Color.WHITE);




     if (selectedPlane.getSpeedCost()<=coins&& upInt[Arrays.asList(upStr).indexOf("Speed")]<=4){
      g.drawImage(upArrow,900,infoBar.y+3,this);
      g.drawString(selectedPlane.getSpeedCost()+"",945,infoBar.y+14);
      g.drawImage(coinImg,920,infoBar.y,this);
      infoBuyable+="Speed";
     }
     if (selectedPlane.getPowerCost()<=coins&& upInt[Arrays.asList(upStr).indexOf("Power")]<=4){
      g.drawImage(upArrow,900,infoBar.y+18,this);
      g.drawString((selectedPlane.getPowerCost())+"",945,infoBar.y+29);
      g.drawImage(coinImg,920,infoBar.y+15,this);
      infoBuyable+="Power";
     }
     if (selectedPlane.getFireRateCost()<=coins&& upInt[Arrays.asList(upStr).indexOf("Fire Rate")]<=4){
      g.drawImage(upArrow,900,infoBar.y+33,this);
      g.drawString((selectedPlane.getFireRateCost())+"",945,infoBar.y+44);
      g.drawImage(coinImg,920,infoBar.y+30,this);
      infoBuyable+="Fire Rate";
     }
     if (selectedPlane.getRadiusCost()<=coins&& upInt[Arrays.asList(upStr).indexOf("Radius")]<=4){
      g.drawImage(upArrow,900,infoBar.y+48,this);
      g.drawString((selectedPlane.getRadiusCost())+"",945,infoBar.y+59);
      g.drawImage(coinImg,920,infoBar.y+45,this);
      infoBuyable+="Radius";
     }



   }

   public void drawBalloonInfo(Balloon selectedBalloon,Graphics g,Rectangle infoBar){
        g.setColor(Color.WHITE);
     g.drawRoundRect(infoBar.x,infoBar.y,infoBar.width,infoBar.height,10,10);
     g.drawString("Type: "+selectedBalloon.getType(),infoBar.x+5,infoBar.y+15);
     g.drawString("HP: "+selectedBalloon.getHP()+"/"+selectedBalloon.getMaxHP(),infoBar.x+5,infoBar.y+30);
  g.drawString("Coin Value: "+selectedBalloon.getCoinValue(),infoBar.x+5,infoBar.y+45);
  g.drawImage(coinImg,infoBar.x+80,infoBar.y+30,this);
  g.drawImage(selectedBalloon.getSprite(),infoBar.x+200,infoBar.y+10,this);
   }

   public void bottomBar(Graphics g){
    g.setColor(Color.DARK_GRAY);
    g.fillRect(80,  640,  1110, 100);
    Rectangle jetBtRect=new Rectangle(300,650,112,68);
    Rectangle bomberBtRect=new Rectangle(412,650,112,68);
    Rectangle heliBtRect=new Rectangle(524,650,112,68);
    Rectangle nukeBtRect=new Rectangle(636,650,112,68);
    Rectangle infoBar = new Rectangle(750,650,325,68);
    if (selectedPlane!=null){
     drawInfoBar(selectedPlane,g,infoBar);
    }
    if(selectedBalloon!=null){
     drawBalloonInfo(selectedBalloon,g,infoBar);
    }



    //START JET BUTTONS


    if(jetBuyable && coins>=20){
     g.drawImage(jetBt1,300,650,this);

    }
    else{
     g.drawImage(jetBt2,300,650,this);
     g.setColor(Color.RED);
     g.fillRect(305,690,100,5);
     g.setColor(Color.GREEN);
     if(jetTimer/10<=100){
      g.fillRect(305,690,100-jetTimer/10,5);
     }
     g.setColor(Color.WHITE);
    }
    g.setColor(Color.WHITE);
    g.drawString("20",340,707);
    if(jetTimer>=1000){
     jetBuyable=true;
    }
    if(jetBtRect.contains(mx,my) && isButtonPressed==true && jetBuyable==true && coins>=20){
     if (planes.size()<5){
      planes.add(new Plane("jet",this,new Point(100,100),50));
      jetBuyable=false;
      coins-=20;
      jetTimer=0;
     }else{
      makeAnnouncement("Too many planes. Sell one to buy another.");
     }

    }
    //END JET BUTTONS


    //START BOMBER BUTTONS
    if(bomberBuyable && coins>=50){
     g.drawImage(bomberBt1,412,650,this);
    }
    else{
     g.drawImage(bomberBt2,412,650,this);
     g.setColor(Color.RED);
     g.fillRect(417,690,100,5);
     g.setColor(Color.GREEN);
     if(bomberTimer/20<=100){
      g.fillRect(417,690,100-bomberTimer/20,5);
     }
     g.setColor(Color.WHITE);
    }
    g.setColor(Color.WHITE);
    g.drawString("50",452,707);
    if(bomberTimer>=2000){
     bomberBuyable=true;

    }
    if(bomberBtRect.contains(mx,my) && isButtonPressed==true && bomberBuyable==true && coins>=50){
     if (planes.size()<5){
      planes.add(new Plane("bomber",this,new Point(100,100),50));
      bomberBuyable=false;

      coins-=50;
      bomberTimer=0;
     }else{
      makeAnnouncement("Too many planes. Sell one to buy another.");
     }


    }
    //END BOMBER BUTTONS


    //START HELI BUTTONS
    if(heliBuyable && coins>=70){
     g.drawImage(heliBt1,524,650,this);
    }
    else{
     g.drawImage(heliBt2,524,650,this);
     g.setColor(Color.RED);
     g.fillRect(529,690,100,5);
     g.setColor(Color.GREEN);
     if(heliTimer/30<=100){
      g.fillRect(529,690,100-heliTimer/30,5);
     }
     g.setColor(Color.WHITE);
    }
    g.setColor(Color.WHITE);
    g.drawString("70",564,707);
    if(heliTimer>=3000){
     heliBuyable=true;
    }
    if(heliBtRect.contains(mx,my) && isButtonPressed==true && heliBuyable==true && coins>=70){
     if (planes.size()<5){
      planes.add(new Plane("heli",this,new Point(100,100),50));
      heliBuyable=false;
      coins-=70;
      heliTimer=0;
     }else{
      makeAnnouncement("Too many planes. Sell one to buy another.");
     }


    }
    //END HELI BUTTONS


    //START NUKE BUTTONS
    if(nukeBuyable && nukes>=1){
     g.drawImage(nukeBt1,636,650,this);
    }
    else{
     g.drawImage(nukeBt2,636,650,this);
     g.setColor(Color.RED);
     g.fillRect(641,690,100,5);
     g.setColor(Color.GREEN);
     if(nukeTimer/80<=100){
      g.fillRect(641,690,100-nukeTimer/80,5);
     }
     g.setColor(Color.WHITE);
    }
    g.setColor(Color.WHITE);
    g.drawString("X "+nukes,681,707);
    if(nukeTimer%8000==0){
     nukeBuyable=true;
    }
    if(nukeBtRect.contains(mx,my) && isButtonPressed==true && nukeBuyable==true && nukes>=1){
     nuked=true;
     nukeBuyable=false;
     nukes-=1;
     nukeTimer=0;
    }
    if(nukeBtRect.contains(mx,my) && isButtonPressed==true && nukes==0){
     makeAnnouncement("You don't have anymore nukes!");
    }
    //END NUKE BUTTONS

    //START SELL BUTTON
    Button sellBut=new Button(125,650,sellButton,sellButtonHover,sellButtonPushed,this);
    sellBut.draw(g);
    if(sellBut.isPushed() && paused==false){
      if(selectedPlane!=null && planes.size()>1 && paused==false){
       Plane tmp=selectedPlane;
       planes.remove(selectedPlane);
       selectedPlane=null;
       coins+=tmp.getSalePrice();
       planeButton="released2";
      }
      if(planes.size()==1 && selectedPlane!=null && paused==false){
       makeAnnouncement("You can't sell your last plane!");
      }
     }
    if (selectedPlane!=null && sellBut.isHover()){
     Font f = new Font(Font.SANS_SERIF, 1, 20);
     g.setFont(f);
     g.drawImage(priceTag,selectedPlane.getX()-50,selectedPlane.getY()+50,this);
     g.drawString(selectedPlane.getSalePrice()+"",selectedPlane.getX()-5,selectedPlane.getY()+70);
    }
    //END SELL BUTTON

    //START PAUSE BUTTON
    Button pauseBut=new Button(206,648,pauseButton,null,pauseButtonPushed,this);
    pauseBut.draw(g);
    //END PAUSE BUTTON

    //START FF BUTTON
    Button FFBut=new Button(fastMoveRect.x,fastMoveRect.y,FFButton,null,fastMove,this);
    FFBut.draw(g);
    if (FFBut.isPushed()&&paused==false){
   tickTimers();
   moveStuff(g);
 }



   }
   public void topBar(Graphics g){
     g.setColor(Color.DARK_GRAY);
     g.fillRect(405, 0, 450, 50);
     g.setFont(coinFont);
  g.setColor(Color.WHITE);
  g.drawString("Coins: " + coins +"",420,20);
  g.drawString("Lives: " + lives +"",770,17);
  g.drawString("Wave: " + wave,600,20);
   }
   public void spawnBalloon(){
    if(Path3.size()==0){
     if(wave>=1 && wave<=3){
      balloons.add(new Balloon("green",Path1,this,explosions));
      balloons.add(new Balloon("green",Path2,this,explosions));

     }
     if(wave>=4 && wave<=6){
      balloons.add(new Balloon("yellow",Path1,this,explosions));
      balloons.add(new Balloon("yellow",Path2,this,explosions));
     }
  if(wave>=7 && wave<=9){
   balloons.add(new Balloon("red",Path1,this,explosions));
      balloons.add(new Balloon("red",Path2,this,explosions));
  }
  spawnCounter+=2;
    }
    else{
      if(wave>=1 && wave<=3){
      balloons.add(new Balloon("green",Path1,this,explosions));
      balloons.add(new Balloon("green",Path2,this,explosions));
      balloons.add(new Balloon("green",Path3,this,explosions));

     }
     if(wave>=4 && wave<=6){
      balloons.add(new Balloon("yellow",Path1,this,explosions));
      balloons.add(new Balloon("yellow",Path2,this,explosions));
      balloons.add(new Balloon("yellow",Path3,this,explosions));
     }
  if(wave>=7 && wave<=9){
   balloons.add(new Balloon("red",Path1,this,explosions));
      balloons.add(new Balloon("red",Path2,this,explosions));
      balloons.add(new Balloon("red",Path3,this,explosions));
  }
  spawnCounter+=3;
    }
   }
}

//MIDDLE OF THE SCREEN IS 645,317
