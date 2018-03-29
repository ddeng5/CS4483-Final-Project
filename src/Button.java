import java.awt.*;
import java.util.*;
import javax.swing.*;
class Button{
	private Image normal,hover,pushed;
	private Rectangle rect;
	private boolean mousePushed;
	private int mx,my;
	private int x,y;
	private GamePanel panel;

	public Button(int x,int y,Image normal,Image hover,Image pushed, GamePanel pan){
		panel=pan;
		mx=0;
		my=0;
		this.x=x;
		this.y=y;
		this.normal=normal;
		this.hover=hover;
		this.pushed=pushed;
		rect=new Rectangle(x,y,normal.getWidth(null),normal.getHeight(null));
	}
	public void update(){
		this.mx=panel.mx;
		this.my=panel.my;
		this.mousePushed=panel.isButtonPressed;
	}
	public void draw(Graphics g){
		update();
		g.drawImage(normal,x,y,panel);
		if(rect.contains(mx,my)){
			g.drawImage(hover,x,y,panel);
			if(mousePushed){
				g.drawImage(pushed,x,y,panel);
			}
		}
	}
	public boolean isHover(){
		update();
		if(rect.contains(mx,my)){
			return true;
		}
		return false;
	}

	//determine whether button is pushed
	public boolean isPushed(){
		update();
		if(rect.contains(mx,my) && mousePushed){
			return true;
		}
		return false;
	}
	public Button clone(int x1,int y1){
		return new Button(x1,y1,normal,hover,pushed,panel);
	}
}
