//This class keeps track of all the information for the plane.
import java.util.*;
import java.awt.*;
//import java.io.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.RenderingHints;

public class Plane {
private String type;
Image flag = new ImageIcon("images/redflag.png").getImage();
private int HP;
private int Power;
private int Speed;
private int Armor;
private int Radius;
private int coinCost;
private int fireRate;
private String [] upgradeStrings = new String[]{"Speed","Power","Radius","Fire Rate"};
private int [] upgradeCount = new int[]{0,0,0,0};

private ArrayList<Point> Pos = new ArrayList();
private Point p = new Point(400,300);
private double Heading = 48;
private Image sprite; //this is what the image will be depending on the type of plane
private String moveType = "straight"; //or it could be moving from the array so the moveType = "array"
private Rectangle planeRect;
private GamePanel panel;
private int planeHeight,planeWidth;
private int counter=0;
private int shotCount = 0;
private int glowx,glowy;
private boolean loop = false;
private Image[] animation;

private int fireRateM;
private int powerM;
private int speedM;
private int radiusM;

public String[] getUpgradeStrings(){return upgradeStrings;}
public void setUpgradeStrings(String [] l){upgradeStrings = l;}
public int[] getUpgradeCount(){return upgradeCount;}
public void setUpgradeCount(int [] l){upgradeCount = l;}


  public Plane(String t, GamePanel pan, Point p1,double heading ) {
    panel = pan;
    p=p1;
    Heading=heading;
    if (t.equals("heli")){ //there are three types of planes with all their stats below
      makeHeli();
    }else if (t.equals("bomber")){
      makeBomber();
    }else if (t.equals("jet")){
      makeJet();
    }
  }
  public void makeHeli(){
    glowx=25;
    glowy=25;

    fireRateM=75;
    powerM = 60;
    speedM = 90;
    radiusM = 85;


    type = "heli";
    HP = 150;
    Power = 15;
    Speed = 3;
    Armor = 3;
    Radius = 800;
    coinCost=70;
    fireRate=30;
    sprite = new ImageIcon("images/heli.png").getImage();
    animation=new Image[4];
    for(int i=0;i<4;i++){
      animation[i]=new ImageIcon("images/blade"+(i+1)+".png").getImage();
    }
    planeHeight = sprite.getHeight(null);
    planeWidth= sprite.getWidth(null);
  }
  public void makeBomber(){
    glowx=25;
    glowy=25;
    type = "bomber";
    HP = 125;
    Power = 10;
    Speed = 1;
    Armor = 1;
    Radius = 100;
    coinCost=50;

    fireRateM=100;
    powerM = 150;
    speedM = 130;
    radiusM = 95;

    sprite = new ImageIcon("images/bomber.png").getImage();
    fireRate=50;
    planeHeight = sprite.getHeight(null);
    planeWidth= sprite.getWidth(null);
  }
  public void makeJet(){
    type = "jet";
    HP = 200;
    glowx=25;
    glowy=25;
    Power = 3;
    Speed = 2;
    Armor = 8;
    Radius  = 600;
    coinCost=20;
    fireRate=5;

    fireRateM=50;
    powerM = 45;
    speedM = 75;
    radiusM = 60;

    sprite = new ImageIcon("images/jet.png").getImage();
    planeHeight = 100;
    planeWidth=100;
  }
  public void setMoveType(String n){
    moveType = n;
  }

  public void draw (Graphics g){
    Graphics2D g2D = (Graphics2D)g;
  AffineTransform saveXform = g2D.getTransform();
  AffineTransform at = new AffineTransform();
  at.rotate(Math.toRadians(Heading),(p.x+sprite.getWidth(null)/2),(p.y+sprite.getHeight(null)/2));
  g2D.transform(at);
  g2D.drawImage(sprite,(int)p.x,(int)p.y,panel);
  if(type.equals("heli")){
    g2D.drawImage(animation[counter/10],getX()-50,getY()-50,panel);
    counter=(counter+1)%30;
  }
  g2D.setTransform(saveXform);
  }
public int getX(){return (int)p.x + (int)sprite.getWidth(null)/2;}
public int getY(){return (int)p.y + (int)sprite.getHeight(null)/2;}
public int getX2(){return (int)p.x ;}
public int getY2(){return (int)p.y ;}
  public String getType(){return type;}
  public int getHP(){return HP;}
  public int getPower(){return Power;}
  public int getSpeed(){return Speed;}
  public void setHP(int l){HP = l;}
  public void setPower(int l){
    coinCost+=getPowerCost();
    Power = l;
  }
  public void setSpeed(int l){
    coinCost+=getSpeedCost();
    Speed = l;
  }
  public Rectangle getRect(){return planeRect;}
  public String getMoveType(){return moveType;}
  public int getRange(){return Radius;}
  public void setRange(int l){
    coinCost+=getRadiusCost();
    Radius=l;
  }
  public int getCost(){return coinCost;}
  public int getGlowX(){return -glowx;}
  public int getGlowY(){return -glowy;}
  public int getFireRate(){return fireRate;}
  public void setFireRate(int l){
    coinCost+=getFireRateCost();
    fireRate=l;
  }
  public void setHeading(double h){Heading = h;}
  public int Attack(){
    return Power;
  }
  public int getFireRateCost(){return fireRateM*(upgradeCount[3]+1);}
  public int getPowerCost(){return powerM*(upgradeCount[1]+1);}
  public int getSpeedCost(){return speedM*(upgradeCount[0]+1);}
  public int getRadiusCost(){return radiusM*(upgradeCount[2]+1);}

  public int getSalePrice(){
    return (coinCost)/2;
  }


  public void move(Graphics g,Plane pl,GamePanel game){
    shotCount++;
    if (moveType.equals("straight")){

      p.setLocation(p.getX()+Math.cos(Math.toRadians(Heading))*Speed,p.getY()+Math.sin(Math.toRadians(Heading))*Speed);
      planeRect = new Rectangle((int)p.getX(),(int)p.getY(),planeHeight,planeWidth);

    }
    if (Pos.size()!=0){
      Graphics2D g2d=(Graphics2D)g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      if (this==pl){
        for (int i=0; i<Pos.size();i++){
          g.drawImage(flag,(int)Pos.get(i).x,(int)Pos.get(i).y,game);
        }

        g.setColor(Color.RED);
        if (loop){
          g.setColor(Color.GREEN);
        }
        for (int i=0; i<Pos.size()-1;i++){
          g2d.drawLine((int)Pos.get(i).x+10,(int)Pos.get(i).y+10,(int)Pos.get(i+1).x+10,(int)Pos.get(i+1).y+10);
        }
      }

      double h1 = ((180+Math.toDegrees(Math.atan2((getY()-Pos.get(0).y),(getX()-Pos.get(0).x)))));
      if(isOffScreen(getX(),getY())==false || Math.abs(Math.abs(Heading%360)-h1)>=5){
        Heading+=2*greaterAngle(Heading,h1);
      }
      if(planeRect.contains((int)Pos.get(0).x,(int)Pos.get(0).y)){
        if (loop && Pos.size()>0){
          Pos.add(Pos.get(0));
        }
        Pos.remove(0);
      }
    }
    Heading=Heading%360;
  }


  public double distance(Point p, Point p2){
    return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
  }
  public double distance(int x,int y, Point p2){
    return distance(new Point(x,y),p2);
  }
  public void addPoint(int x, int y){
    if (Pos.size()>0&&distance(Pos.get(0),new Point(x,y))<=50){
      loop = true;
    }else{
      Pos.add(new Point (x,y));
    }

  }
  public String getStats(){
    return ("Type: "+type+"\n HP: "+HP+"\nPower: "+"\nSpeed: "+Speed+"\nArmor: "+Armor+"\nRadius: "+Radius);
  }
  public void display(){
    System.out.println(getStats());
  }
  public String toString(){
    return getStats();
  }
  public ArrayList<Point> getPath(){
    return Pos;
  }
  public void clearPath(){
    Pos.clear();
    loop = false;
  }

  public Bullet shoot(ArrayList<Balloon> balloons){
  if(type.equals("jet")){
    return jetShoot(balloons);
  }
  if(type.equals("bomber")){
    return bomberShoot(balloons);
  }
  if(type.equals("heli")){
    return heliShoot(balloons);
  }
return null;
  }
public Bullet jetShoot(ArrayList<Balloon> balloons){
  for(Balloon b:balloons){
    double h = Math.toDegrees(Math.atan2(p.y-b.getY(),p.x-b.getX()))+180;
    if(distance(getX(),getY(),b.getPoint())<=Radius && Math.abs(Heading%360-h%360)<=30 && shotCount%fireRate==0){
      b.getHit(Attack());
      return new Bullet(new Point((int)getX(),(int)getY()),b.getX(),b.getY(),panel,"jet");
    }
  }
  return null;
}
public Bullet bomberShoot(ArrayList<Balloon> balloons){
  boolean shot=false;
  for(Balloon b:balloons){
    if(distance(getX(),getY(),b.getPoint())<=Radius && shotCount%fireRate==0){
      b.getHit(Attack());
      shot=true;
    }
  }
  if(shot){
    return new Bullet(new Point((int)getX(),(int)getY()),0,0,panel,"bomber");
  }
  return null;
}

public Bullet heliShoot(ArrayList<Balloon> balloons){
  for(Balloon b:balloons){
    double h = Math.toDegrees(Math.atan2(p.y-b.getY(),p.x-b.getX()))+180;
    if(distance(getX(),getY(),b.getPoint())<=Radius && Math.abs(Heading%360-h%360)<=30 && shotCount%fireRate==0){
      b.getHit(Attack());
      return new Bullet(new Point((int)getX(),(int)getY()),b.getX(),b.getY(),panel,"heli");
    }
  }
  return null;
}


public boolean isOffScreen(int x,int y){
  if(x+50<0 || x-50>1280){
    return true;
  }
  if(y+50<0 || y-50>750){
    return true;
  }
return false;
}
public boolean isOffScreen(Point p){
  return isOffScreen((int)p.x,(int)p.y);
}
private int greaterAngle(double a1,double a2)
  {
  int ang1 = ((int)(a1)+360)%360;
  int ang2 = ((int)(a2)+360)%360;

  if(ang2 == ang1)
      return 0;
  if(ang2 > ang1 && ang2 - ang1 <= 180)
      return 1;
  if(ang2 < ang1 && (ang2+360) - ang1 <= 180)
      return 1;
  return -1;
  }
}

//(int)((Math.random())*chanceShoot)==1
