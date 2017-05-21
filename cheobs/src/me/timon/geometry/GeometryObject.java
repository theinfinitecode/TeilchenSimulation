package me.timon.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import me.timon.ts.Main;
import me.timon.ts.Window.Scene;

public class GeometryObject {

	public Color c;
	
	public GeometryObject(){
		
	}
	
	public void render(Graphics2D g, Scene s){
		
	}
	
	public float f(double d){
		return (float) d;
	}

	public void fixPoints(Scene s, Point...p){
		if(s == Scene.SIDE){
			for(Point pi : p){
				pi.x = -pi.x;
				pi.y = -pi.y;

				pi.x *= Main.zoom_side;
				pi.y *= Main.zoom_side;
				pi.z *= Main.zoom_side;
				
				pi.add(4500, 0, 4500);
			}
		}
		if(s == Scene.FRONT){
			for(Point pi : p){
				
				pi.x *= Main.zoom_front;
				pi.y *= Main.zoom_front;
				pi.z *= Main.zoom_front;
				
				pi.add(4500, 4500, 0);
			}
		}
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public Color getColor(){
		return c;
	}
	
}
