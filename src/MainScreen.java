
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class MainScreen extends JPanel implements KeyListener,MouseListener, MouseMotionListener{
	Image main = new ImageIcon("menuImages/main.jpg").getImage();			//Loading Screen Picture
	Image blue = new ImageIcon("menuImages/blue.jpg").getImage();			//Blue BackGround for Main Menu
	Image cloud1 = new ImageIcon("menuImages/cloud/3.png").getImage();		//Cloud Picture for Main Menu
	Image [] settings = new Image[7];									//List for All Images for Settings Pop-up Menu
	Image [] levels = new Image[1];										//List for Menu Level Preview
	Image settingspic = new ImageIcon("menuImages/setting.png").getImage();	//Pop-up Image for Settings
	boolean settingEnabled = false;										//If enabled Opens settings
	private int loc=0;													//Cloud Location
	private boolean[] keys;
	Font loadingFont,settingFont; 										//Calibri
	private int loading,count,loadingCounter,cloudmove1;
    boolean isButtonPressed,soundSlide = false;
    int mx,loaded=0; int my = 0;
    int sound=600;														//Sound Value Pixels
    boolean soundEnabled = true;										//If true, sound is Enabled
    Rectangle soundSlideRect2 = new Rectangle(600,240,226,40);			//Sound slider container
    Rectangle soundSlideRect = new Rectangle(600,240,32,40);
    Rectangle soundBox = new Rectangle(389,235,40,40);
    Rectangle settingBox = new Rectangle(330,120,620,480);				//Setting pop-up image container
    Rectangle settingBtn = new Rectangle(100,600,132,104);				//Settings Btns image container
    static GamePanel game;

    Image rightArrowNormal = new ImageIcon("images/rightArrowNormal.png").getImage();
    Image rightArrowHover = new ImageIcon("images/rightArrowHover.png").getImage();
    Image rightArrowPushed = new ImageIcon("images/rightArrowPushed.png").getImage();
    Image leftArrowNormal = new ImageIcon("images/leftArrowNormal.png").getImage();
    Image leftArrowHover = new ImageIcon("images/leftArrowHover.png").getImage();
    Image leftArrowPushed = new ImageIcon("images/leftArrowPushed.png").getImage();


    Font coinFont,coinFontBig;

    //Rectangle mapRightRect = new Rectangle(1100,460,rightArrowNormal.getWidth(null),rightArrowNormal.getHeight(null));
   //Rectangle mapLeftRect = new Rectangle(180-rightArrowNormal.getWidth(null),460,rightArrowNormal.getWidth(null),rightArrowNormal.getHeight(null));

    static Image grass1 = new ImageIcon("images/grass.png").getImage();
	static Image grass2 = new ImageIcon("images/grass2.png").getImage();


	static ArrayList<Point> Level1a = new ArrayList();
	static ArrayList<Point> Level1b = new ArrayList();
	static ArrayList<Point> Level1c = new ArrayList();

	Image greyBarNormal = new ImageIcon("images/greyBarNormal.png").getImage();
	Image greyBarHover = new ImageIcon("images/greyBarHover.png").getImage();
	Image greyBarPushed = new ImageIcon("images/greyBarPushed.png").getImage();
	Image yellowBarNormal = new ImageIcon("images/yellowBarNormal.png").getImage();
	Image yellowBarHover = new ImageIcon("images/yellowBarHover.png").getImage();
	Image yellowBarPushed = new ImageIcon("images/yellowBarPressed.png").getImage();
	Image greenBarNormal = new ImageIcon("images/greenBar1.png").getImage();
	Image greenBarHover = new ImageIcon("images/greenBar2.png").getImage();
	Image greenBarPushed = new ImageIcon("images/greenBar3.png").getImage();

	Cursor normalCur;
	Image imgCursor;



	boolean helpEnabled = false;

	Image[] maps1=new Image[4];
	Image [] helpPics = new Image[]{new ImageIcon("images/helpPic1.png").getImage(),//FONT IS BODONI MT BOLDED
									new ImageIcon("images/helpPic2.png").getImage(),//500 X 292 IMAGES
									new ImageIcon("images/helpPic3.png").getImage(),
									new ImageIcon("images/helpPic4.png").getImage(),
									new ImageIcon("images/helpPic5.png").getImage(),
									new ImageIcon("images/helpPic6.png").getImage(),
									new ImageIcon("images/helpPic7.png").getImage(),
									new ImageIcon("images/helpPic8.png").getImage(),
									new ImageIcon("images/helpPic9.png").getImage(),
									new ImageIcon("images/helpPic10.png").getImage(),
									new ImageIcon("images/helpPic11.png").getImage(),
									new ImageIcon("images/helpPic12.png").getImage(),
									new ImageIcon("images/helpPic13.png").getImage()};
   	int helpPicCounter = 10000000*helpPics.length;
   	Image[]maps2=new Image[4];

   	String [] paths1=new String[4];
   	String [] paths2=new String[4];
   	String [] paths3=new String[4];


	Rectangle helpBarRect = new Rectangle(1000,610,greyBarNormal.getWidth(null),greyBarNormal.getHeight(null));
	Rectangle helpNextRect = new Rectangle(685,475,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));
	Rectangle helpPrevRect = new Rectangle(375,475,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));

    public MainScreen(GamePanel game){
    	super();
    	imgCursor=new ImageIcon("images/cursor.png").getImage();
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	normalCur = tk.createCustomCursor( imgCursor, new java.awt.Point( 5, 5 ), "cursor" );
    	setCursor(normalCur);

    	try{
			coinFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,12);
			coinFontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,25);
		}
		catch(IOException ioe){
			System.out.println("error loading AgentOrange.tff");
		}
		catch(FontFormatException e){
		}

    	maps1[0]=grass1;

   		maps2[0]=grass2;

   		for(int i=0;i<4;i++){   ///making text files
   			paths1[i]="level"+(i+1)+"a.txt";
   			paths2[i]="level"+(i+1)+"b.txt";
   			if(i>1){
   				paths3[i]="level"+(i+1)+"c.txt";
   			}
   			else{
   				paths3[i]=null;
   			}
   		}
    	this.game=game;																							//Loading Menu Level Preview
			Image img = Toolkit.getDefaultToolkit().getImage("menuImages/"+(1)+".png");
			levels[0]=img;


		for(int i=0;i<7;i++){											//Loading All Images for Settings Pop-up Menu
			Image img1 = Toolkit.getDefaultToolkit().getImage("menuImages/btns/"+(i+1)+".png");
			settings[i]=img1;
		}

		keys = new boolean[KeyEvent.KEY_LAST+1];

		try{															//Loading Font
			loadingFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,15);
			settingFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,20);
		}
		catch(IOException ioe){
			System.out.println("error loading AgentOrange.tff");
		}
		catch(FontFormatException e){
		}
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

    }
    public void addNotify(){
		super.addNotify();
		requestFocus();
	}



	public static void textFile(ArrayList l, String a) throws IOException {
    	Scanner inFile = new Scanner (new BufferedReader (new FileReader(a)));
    	while(inFile.hasNextLine()){
			String [] per = inFile.nextLine().split(" ");
			l.add(new Point(Double.parseDouble(per[0]),Double.parseDouble(per[1]),Double.parseDouble(per[2])));
		}
		inFile.close();
    }


	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if(loaded<50){													//Loading Screen Display
			g.drawImage(main,0,0,this);									//Draws images that will be used later on
			g.setFont(loadingFont);
			g.setColor(Color.WHITE);
			g.drawString("Loading",600,680);
			g.drawImage(levels[0],5000,5000,this);
			g.drawImage(blue,5000,5000,this);
			g.drawImage(settings[0],5000,5000,null);
			g.drawImage(settings[1],5000,5000,null);
			g.drawImage(settings[2],5000,5000,null);
			g.drawImage(cloud1,cloudmove1%(1280+512) - 512,400,this);
			loaded+=1;
		}
		if(loaded>=50){													//Once Loading is Done, Goes to Main Menu
			cloudmove1++;
				if(loc==0){

					g.drawImage(blue,0,0,this);							//Draws Blue Background for Main Menu
					g.drawImage(cloud1,cloudmove1%(1280+512) - 512,400,this);//Draws cloud, then adds it back to front once reaches end
					g.drawImage(levels[0],0,0,this);					//Draws Level Previews
				}

			if(settingBtn.contains(mx,my)){								//Settings Pop-up
				if(isButtonPressed==true){
					g.drawImage(settings[2],50,600,null);
					settingEnabled=true;
				}else{
					g.drawImage(settings[1],50,600,null);
				}
			}
			else{
				g.drawImage(settings[0],50,600,null);
			}
			setFont(coinFontBig);
			g.setColor(Color.WHITE);
			g.drawImage(greyBarNormal,helpBarRect.x,helpBarRect.y,this);


			if (helpBarRect.contains(mx,my)){
				g.drawImage(greyBarHover,helpBarRect.x,helpBarRect.y,this);
			}
			if (helpBarRect.contains(mx,my)&&isButtonPressed){
				g.drawImage(greyBarPushed,helpBarRect.x,helpBarRect.y,this);
			}

			g.drawString("HELP",helpBarRect.x+73,helpBarRect.y+50);


			if (helpEnabled){
				g.drawImage(settingspic,330,120,null);
				g.drawImage(greenBarNormal,helpNextRect.x,helpNextRect.y,this);
				if (helpNextRect.contains(mx,my)){
					g.drawImage(greenBarHover,helpNextRect.x,helpNextRect.y,this);
				}
				if (helpNextRect.contains(mx,my)&&isButtonPressed){
					g.drawImage(greenBarPushed,helpNextRect.x,helpNextRect.y,this);
				}
				g.drawString("NEXT",helpNextRect.x+65,helpNextRect.y+42);

				g.drawImage(greenBarNormal,helpPrevRect.x,helpPrevRect.y,this);
				if (helpPrevRect.contains(mx,my)){
					g.drawImage(greenBarHover,helpPrevRect.x,helpPrevRect.y,this);
				}
				if (helpPrevRect.contains(mx,my)&&isButtonPressed){
					g.drawImage(greenBarPushed,helpPrevRect.x,helpPrevRect.y,this);
				}
				g.drawString("PREV",helpPrevRect.x+65,helpPrevRect.y+42);

				g.drawImage(helpPics[helpPicCounter%helpPics.length],settingBox.x+50,settingBox.y+100,this);
			}

			if(settingEnabled==true){
				g.drawImage(settingspic,330,120,null);
				g.drawImage(settings[5],389,235,null);
				g.drawImage(settings[6],600,248,null);
				if (soundEnabled){
					g.drawImage(settings[4],soundSlideRect.x,soundSlideRect.y,null);
				}else{
					g.drawImage(settings[4],600,248,null);
				}

				g.setFont(settingFont);
				g.setColor(Color.WHITE);
				g.drawString("Sound",456,260);

				if(soundEnabled){										//Sound Settings
					g.drawImage(settings[3],389,235,null);
				}
			}

			checkKeys();
		}
	}

	public void checkKeys(){
		if((keys[KeyEvent.VK_ESCAPE] && settingEnabled) || (keys[KeyEvent.VK_ESCAPE] && helpEnabled)){				//If Escape is pressed, closes settings
			settingEnabled=false;
			helpEnabled=false;
			helpPicCounter = 10000000*helpPics.length;
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){

		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){							//Goes Next/Prev. if Right or Left arrows is pressed
		keys[e.getKeyCode()]=false;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && settingEnabled==false && helpEnabled==false){
			loc=(loc+1)%4;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && helpEnabled){
			helpPicCounter++;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT && helpEnabled){
			helpPicCounter--;
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER && settingEnabled==false){
			try{
				Level1a.clear();
				Level1b.clear();
				Level1c.clear();
				textFile(Level1a,paths1[loc]);
    			textFile(Level1b,paths2[loc]);
    			if(paths3[loc]!=null){
    				textFile(Level1c,paths3[loc]);
    			}
				game.resetEverything(Level1a,Level1b,Level1c,maps1[loc],maps2[loc]);

			}
			catch(IOException ex){
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT&& settingEnabled==false && helpEnabled==false){
			if (loc!=0){
				loc=Math.abs((loc-1)%4);
			}else{
				loc = 3;
			}

		}
	}

	public void mouseEntered( MouseEvent e ) {}
  	public void mouseExited( MouseEvent e ) {}
  	public void mouseClicked( MouseEvent e ) {}
  	public void mousePressed( MouseEvent e ) {
      	isButtonPressed = true;
      	if((settingBox.contains(mx,my))==false && isButtonPressed == true){	//Settings is closed if mouse is clicked outside the settings pop-up
			settingEnabled=false;
		}
   }
   	public void mouseReleased( MouseEvent e ) {
   		if(helpBarRect.contains(mx,my)){
   			helpEnabled = !helpEnabled;
   		}
   		if (helpEnabled&&helpNextRect.contains(mx,my)){
   			helpPicCounter++;
   		}
   		if (helpEnabled&&helpPrevRect.contains(mx,my)){
   			helpPicCounter--;
   		}
   		if (!settingBox.contains(mx,my)&&!helpBarRect.contains(mx,my)){
   			helpEnabled = false;
   			helpPicCounter = 10000000*helpPics.length;
   		}
   		if(soundBox.contains(mx,my)){										//Toggles Sound on/off
					soundEnabled = !soundEnabled;
		}
      isButtonPressed = false;
   }
   	public void mouseMoved( MouseEvent e ) {
      	mx = e.getX();
      	my = e.getY();
   }
   	public void mouseDragged( MouseEvent e ) {
   		mx = e.getX();
      	my = e.getY();
   		if (soundSlideRect2.contains(mx,my)&&isButtonPressed&&mx>600&&mx<600+226){	//Moves the sound slider
      			soundSlideRect = new Rectangle(mx-10,240,32,40);
      		}
      	}
}
