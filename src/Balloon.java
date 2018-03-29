import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
class Balloon{
	private int Speed,HP,maxHP,count;
	private String type;
	private Point p;
	private Image Sprite,coin;
	private ArrayList<Point> path;
	private Rectangle balloonRect;
	private int rectLength = 38;
	private int rectWidth = 44;
	private int coinValue;
	private boolean balFlag = true;
	private GamePanel panel;
	private int anCount =0;
	private int hpScale=1;
	private Image [] animation;

	public Balloon(String type,ArrayList<Point> path, GamePanel pan, Image [] an){
		panel = pan;
		this.type=type;
		this.path=path;
		count=0;
		p=path.get(count);
		coin = new ImageIcon("images/coin.png").getImage();
		balloonRect = new Rectangle ((int)p.getX(),(int)p.getY(),rectLength,rectWidth);
		animation = an;

		//create balloons and boss
		if(type.equals("green")){
			makeGreen();
		}
		if(type.equals("yellow")){
			makeYellow();
		}
		if(type.equals("red")){
			makeRed();
		}
		if(type.equals("boss")){
			makeBoss();
		}
	}

	//set up green balloon stats
	public void makeGreen(){
		HP=30;
		maxHP=HP;
		Speed=10;
		coinValue=2;
		Sprite = new ImageIcon("images/greenBalloon.png").getImage();
	}

	//set up yellow balloon stats
	public void makeYellow(){
		HP=45;
		maxHP=HP;
		Speed=15;
		coinValue=4;
		Sprite = new ImageIcon("images/yellowBalloon.png").getImage();
	}

	//set up red balloon stats
	public void makeRed(){
		HP=60;
		maxHP=HP;
		Speed=20;
		coinValue=6;
		Sprite = new ImageIcon("images/redBalloon.png").getImage();
	}

	//set up boss stats
	public void makeBoss(){
		HP=2000;
		maxHP=HP;
		Speed=20;
		coinValue=30;
		hpScale=10;
		Sprite = new ImageIcon("images/nukeBoss.png").getImage();
	}

	//if balloon is alive, set up balloon sprite
	public boolean move(){
		if (HP>0){
			p=path.get(count);
			balloonRect = new Rectangle ((int)p.getX()- Sprite.getWidth(null)/2,(int)p.getY()- Sprite.getWidth(null)/2,rectLength,rectWidth);
			count++;
			if (count>=path.size()-1){
				return false;
			}

			return true;
		}
	return true;
	}

	public void draw(Graphics g){
		if(HP>0){
			g.setColor(Color.RED);
			g.fillRect((int)p.x-maxHP/(2*hpScale),(int)p.y-30,maxHP/hpScale,3);
			g.setColor(Color.GREEN);
			g.fillRect((int)p.x-maxHP/(2*hpScale),(int)p.y-30,HP/hpScale,3);
			g.setColor(Color.RED);
			g.drawImage(Sprite,(int)p.x - Sprite.getWidth(null)/2,(int)p.y - Sprite.getHeight(null)/2,panel);
		}
		else{
			//System.out.println(anCount);
			if (anCount<animation.length-1){
				g.drawImage(animation[anCount],(int)p.x-19,(int)p.y-22,panel);
				g.drawImage(coin,(int)p.x,(int)p.y-anCount,panel);
				g.setColor(Color.WHITE);
				g.drawString("+"+coinValue,(int)p.x+22,(int)p.y-anCount+17);
				anCount++;
			}
		}
	}

	//lose hp when hit
	public void getHit(int power){
		HP-=power;
	}

	public Point getPoint(){
		return p;
	}
	public int getX(){
		return (int)p.x;
	}
	public int getY(){
		return (int)p.y;
	}
	public int getZ(){
		return (int)p.z;
	}
	public int getHP(){
		return HP;
	}
	public int getMaxHP(){
		return maxHP;
	}
	public Rectangle getRect(){
		return balloonRect;
	}
	public int getCoinValue(){
		return coinValue;
	}
	public int getAnimationCounter(){
		return anCount;
	}
	public double distance(Point p, Point p2){
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
    public String getType(){
    	return type;
    }
    public Image getSprite(){
    	return Sprite;
    }
}
