package me.timon.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import me.timon.ts.Window.Scene;

public class Axis extends GeometryObject{

	public enum AxisType{
		X,Y,Z;
	}
	
	public Axis(){
		
	}
	
	public AxisType t;
	
	public Axis(AxisType t){
		this.t = t;
	}
	
	@Override
	public void render(Graphics2D g, Scene s){
		if(s == Scene.SIDE){
			if(t == AxisType.X){
				Point ps = new Point(0, 0, -800);
				Point pe = new Point(0, 0, 1000);
				
				fixPoints(s, ps, pe);
				
				g.setColor(c);
				g.setStroke(new BasicStroke(1));
				g.draw(new Line2D.Float(f(ps.z), f(ps.x), f(pe.z), f(pe.x)));
			}
			if(t == AxisType.Z){
				Point ps = new Point(-1000, 0, 0);
				Point pe = new Point(1000, 0, 0);
				
				fixPoints(s, ps, pe);
				
				g.setColor(c);
				g.setStroke(new BasicStroke(1));
				g.draw(new Line2D.Float(f(ps.z), f(ps.x), f(pe.z), f(pe.x)));
			}
		}
		if(s == Scene.FRONT){
			if(t == AxisType.X){
				Point ps = new Point(-1000, 0, 0);
				Point pe = new Point(1000, 0, 0);
				
				fixPoints(s, ps, pe);
				
				g.setColor(c);
				g.setStroke(new BasicStroke(1));
				g.draw(new Line2D.Float(f(ps.y), f(ps.x), f(pe.y), f(pe.x)));
			}
			if(t == AxisType.Y){
				Point ps = new Point(0, -1000, 0);
				Point pe = new Point(0, 1000, 0);
				
				fixPoints(s, ps, pe);
				
				g.setColor(c);
				g.setStroke(new BasicStroke(1));
				g.draw(new Line2D.Float(f(ps.y), f(ps.x), f(pe.y), f(pe.x)));
			}
		}
	}
	
}
