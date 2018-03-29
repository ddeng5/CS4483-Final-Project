import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ZeroHourBaseDefense extends JFrame implements ActionListener{
	static GamePanel game; //game panel
	static MainScreen main;
	javax.swing.Timer myTimer;// timer
	javax.swing.Timer spawnTimer;
	static ArrayList<Point> Level1a = new ArrayList();
	static ArrayList<Point> Level1b = new ArrayList();

	static Image i1 = new ImageIcon("images/Level1.png").getImage();
	static Image i2 = new ImageIcon("images/grass_obj.png").getImage();

	boolean quit = false;

	public static void textFile(ArrayList l, String a) throws IOException {
    	Scanner inFile = new Scanner (new BufferedReader (new FileReader(a)));
    	while(inFile.hasNextLine()){
			String [] per = inFile.nextLine().split(" ");
			l.add(new Point(Double.parseDouble(per[0]),Double.parseDouble(per[1])));
		}
		inFile.close();
    }



    public ZeroHourBaseDefense(ArrayList<Point> Path1, ArrayList<Point> Path2, Image bg1,Image bg2){
    	super("Zero Hour Base Defense");
    	game = new GamePanel(Level1a,Level1b,i1,i2);
    	game.quit=true;
    	main = new MainScreen(game);
    	main.setVisible(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(1280,750);
    	setLayout(new BorderLayout());
    	setResizable(false);
    	myTimer = new javax.swing.Timer(1000/70,this);
    	myTimer.start();
    	spawnTimer = new javax.swing.Timer(1000,this);
    	spawnTimer.start();
    	add(game);
    	setVisible(true);
    }
    public void actionPerformed(ActionEvent e){ //called with timer
    	Object source=e.getSource();
    	if(source==myTimer){
    		quit = game.quit;
    		if (quit==false){
    			game.requestFocus();
    			game.checkKeys();
    			game.checkPlaneShoot();
    			game.cleanseLists();
    			game.repaint();
    			main.setVisible(false);
    			game.setVisible(true);
    		}else{

    			main.requestFocus();
    			add(main);
    			main.repaint();
    		}


    	}
    	if(source==spawnTimer && game.paused==false && quit==false){
    		game.spawnState();
    		if(game.balSpawn){
    			game.spawnBalloon();
    		}

    	}
    	if (quit){
    		game.setVisible(false);
    		main.setVisible(true);
    	}
    }

    public static void main (String [] args)throws IOException {
    	textFile(Level1a,"level1a.txt");
    	textFile(Level1b,"level1b.txt");
    	ZeroHourBaseDefense a = new ZeroHourBaseDefense(Level1a,Level1b,i1,i2);

    }


}
