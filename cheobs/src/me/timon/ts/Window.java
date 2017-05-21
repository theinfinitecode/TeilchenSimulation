package me.timon.ts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window extends JFrame{

	public enum Scene{
		SIDE("Seitenansicht"),
		FRONT("Vorderansicht"),
		;private Scene(String name){
			this.name = name;
		}
		
		public String name;
		public String getName(){
			return name;
		}
	}
	
	public Scene view;
	public Render r;
	public Thread wt;
	
	public Window(Scene view){
		super("TeilchenSimulation - " + view.getName());
		this.view = view;
		wt = new Thread(new Runnable() {
			
			@Override
			public void run() {
				create();
			}
		});
		wt.start();
	}
	
	public void create(){
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(null);
		setAlwaysOnTop(true);
		toFront();
		
		try {
			ImageIcon ico = new ImageIcon(ImageIO.read(new File("cheobs_mini.png")));
			setIconImage(ico.getImage());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		r = new Render(view);
		r.setSize(18800, 18600);
		r.setBounds(-4450, -4500, 11300, 11100);
		add(r);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(16); // 60 fps
						Main.getInstance().update(view);
						r.repaint();
					} catch (InterruptedException e) {
						
					}
				}
			}
		});
		t.start();
		

		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_C){
					Main.enable_circles_f = !Main.enable_circles_f;
				}
				if(e.getKeyCode() == KeyEvent.VK_T){
					Main.trace = !Main.trace;
					if(Main.trace) Main.time = 0d;
				}
				if(e.getKeyCode() == KeyEvent.VK_P){
					Main.changeStart = !Main.changeStart;
				}
				if(e.getKeyCode() == KeyEvent.VK_A){
					System.out.println("[ POS " + view + "] " + getLocation().getX() + " -- " + getLocation().getY());
					System.out.println("[ ZOOM " + view + "] " + Main.zoom_front + ", side: " + Main.zoom_side);
					System.out.println("[ PANEL " + view + "] " + r.getLocation().x + " " + r.getLocation().y);
					System.out.println("[ window size " + view + "] " + getSize().width + " -- " + getSize().height);
				}
				if(e.getKeyCode() == KeyEvent.VK_1){
					Main.getInstance().loadWindowInfo(1);
				}
				if(e.getKeyCode() == KeyEvent.VK_2){
					Main.getInstance().loadWindowInfo(2);
				}	
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					Main.renderTeilchen = !Main.renderTeilchen;
				}
			}
		});
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
