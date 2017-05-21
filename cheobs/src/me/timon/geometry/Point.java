package me.timon.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import me.timon.ts.Main;
import me.timon.ts.MathHelper;
import me.timon.ts.Window.Scene;

public class Point extends GeometryObject{

	public double x;
	public double y;
	public double z;
	
	public Point(){
		
	}
	
	public Point(Vector v){
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Point(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector toVector(){
		return new Vector(x, y, z);
	}
	
	public void add(Point p){
		add(p.x, p.y, p.z);
	}
	
	public void add(double x, double y, double z){
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	@Override
	public Point clone(){
		Point clone = new Point(x, y, z);
		clone.setColor(c);
		return clone;
	}
	
	@Override
	public void render(Graphics2D g, Scene s) {
		if(s == Scene.SIDE){
			Point p = clone();
			fixPoints(s, p);
			g.setColor(getColor());
			if(c != null)
			g.fillOval(((int)p.z-5), ((int)p.x-5), 10, 10);
		}
		if(s == Scene.FRONT){
			Point p = clone();
			p.x = -p.x;
			fixPoints(s, p);
			g.setColor(c);
			g.fillOval(((int)p.y-5), ((int)p.x-5), 10, 10);
			
			if(Main.enable_circles_f){
			
				double r = MathHelper.tan(Main.getInstance().cone.getOmega()) * z * Main.zoom_front;
				Point center = new Point(0, 0, 0);
				fixPoints(s, center);
				
				g.setColor(getColor());
				g.setStroke(new BasicStroke(1f));
				r *= 2;
			
				{
				
					double x = center.x-(r/2);
					double y = center.y-(r/2);
					g.draw(new Ellipse2D.Double(x, y, r, r));
				
				}
			
			}
			
		}
	}
	
}




