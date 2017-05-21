package me.timon.ts;

import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import me.timon.geometry.Point;
import me.timon.geometry.Vector;
import me.timon.ts.Window.Scene;

public class WindowDrag {

	public int x;
	public int y;
	
	public void handleDrag(final Render renderer){
		
	    renderer.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent me) {
	             x = me.getX();
	             y = me.getY();
	        }
	    });
	    
	    renderer.addMouseMotionListener(new MouseMotionAdapter() {
	        @Override
	        public void mouseDragged(MouseEvent me) {
	            
	        	if(Main.changeStart){
	    			java.awt.Point mouse = MouseInfo.getPointerInfo().getLocation();
	    			for(TeilchenInfo t : Main.getAllTeilchen()){
	    				Point s = t.getPoint("start");
	    				if(renderer.s == Scene.SIDE){
	    					s.z -= (x-me.getX())/Main.zoom_side;
	    					s.x += (y-me.getY())/Main.zoom_side;
	    				}else{
	    					s.y -= (x-me.getX())/Main.zoom_front;
	    					s.x += (y-me.getY())/Main.zoom_front;
	    				}
	    				x = me.getX();
    					y = me.getY();
	    				Main.removeTeilchen(t.id);
	    				Vector x = t.x.clone();
	    				Main.getInstance().addTeilchen(s.toVector(), t.v, 1000);
	    			}
	    			
	    		}else{
	    			me.translatePoint(me.getComponent().getLocation().x-x, me.getComponent().getLocation().y-y);
		        	renderer.setLocation(me.getX(), me.getY());
	  	        }
	            
	        }
	    });
	    
	}
	
}
