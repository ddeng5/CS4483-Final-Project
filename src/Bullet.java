import java.util.*;
import java.awt.*;
//import java.io.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Bullet {
	private Point p;
	private double Heading;
	private int Power;
	private int rectLength = 10;
	private int rectWidth = 10;
	private Image Sprite;
	private Image[] animation;
	private GamePanel panel;
	private int endx,endy,counter;
	private String type;


    public Bullet(Point p,int endx,int endy,GamePanel pan,String type) {
    	counter=0;
    	this.p=p;
    	this.endx=endx;
    	this.endy=endy;
    	this.Power=Power;
    	this.type=type;

    	//determine what type of bullet to show
    	if(type.equals("jet")){
    		Sprite = new ImageIcon("images/bullet.png").getImage();
    	}
    	if(type.equals("bomber")){
    		animation= new Image[3];
    		animation[0]=new ImageIcon("images/fire1.png").getImage();
    		animation[1]=new ImageIcon("images/fire2.png").getImage();
    		animation[2]=new ImageIcon("images/fire3.png").getImage();
    	}
    	if(type.equals("heli")){
    		Sprite=new ImageIcon("images/heliMiss.png").getImage();
    	}
    }

    //set up missle and bullet angle
    public void move(){
    	if (type.equals("heli") || type.equals("jet")){
    		Heading = Math.toDegrees(Math.atan2(p.y-endy,p.x-endx))+180;
    		p.setLocation(p.getX()+Math.cos(Math.toRadians(Heading))*10,p.getY()+Math.sin(Math.toRadians(Heading))*10);
    	}
    }

    //return bullet damage
    public int getPower(){
    	return Power;
    }

    public int getX(){
    	return (int)p.x;
    }
    public int getY(){
    	return (int)p.y;
    }

    //draw the bullet movement
    public void draw(Graphics g){
    	if(type.equals("jet")){
    		g.drawImage(Sprite,(int)p.x - Sprite.getWidth(null)/2,(int)p.y - Sprite.getHeight(null)/2,panel);
    	}
		if(type.equals("bomber")){
			if (counter/15<animation.length-1){
				g.drawImage(animation[counter/15],(int)p.x - animation[counter/15].getWidth(null)/2,(int)p.y - animation[counter/15].getHeight(null)/2,panel);
				counter++;
			}

		}
		if(type.equals("heli")){
			Graphics2D g2D = (Graphics2D)g;
			AffineTransform saveXform = g2D.getTransform();
			AffineTransform at = new AffineTransform();
			at.rotate(Math.toRadians(Heading),(p.x+Sprite.getWidth(null)/2),(p.y+Sprite.getHeight(null)/2));
			g2D.transform(at);
			g2D.drawImage(Sprite,(int)p.x,(int)p.y,panel);
			g2D.setTransform(saveXform);
		}
	}
	public String toString(){
		return (p.x+"    "+p.y);
	}
	public double distance(Point p, Point p2){
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
	public boolean reachedEnd(){
		if(type.equals("jet")|| type.equals("heli")){
			if(distance(p,new Point(endx,endy))<=15){
				return true;
			}
		}
		if(type.equals("bomber")){
			if(counter==30){
				return true;
			}
		}
	return false;
	}
}
