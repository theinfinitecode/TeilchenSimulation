package me.timon.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import me.timon.ts.Main;
import me.timon.ts.MathHelper;
import me.timon.ts.Window.Scene;

public class Cone extends GeometryObject{

	public double omega;
	public double za;
	public double ze;
	public Vector vup;
	public Vector vdown;
	
	public Cone(){
		
	}
	
	public Cone(double omega, double za, double ze){
		this.omega = omega;
		this.za = za;
		this.ze = ze;
		vup = new Vector(MathHelper.sin(getOmega()), 0, MathHelper.cos(getOmega())).normalize();
		vdown = vup.clone();
		vdown.x = -vdown.x;
	}
	
	public double getOmega(){
		return omega;
	}
	
	public double getZA(){
		return za;
	}
	
	public double getZE(){
		return ze;
	}
	
	public Vector getVectorUp(){
		return vup;
	}
	
	public Vector getVectorDown(){
		return vdown;
	}

	public ConeCollision collide(Line l){
		
		Vector x = l.getSV();
		Vector v = l.getRV();
		double t = collideCalcTime(x, v);
		Point p = new Point(l.mul(t));
		if(p.z > getZE()){
			p.x = Double.NaN;
			p.y = Double.NaN;
			p.z = Double.NaN;
		}
		return new ConeCollision(t, p);
		
	}
	
	public ConeCollision collideFirst(Line l){
		
		Vector x = l.getSV();
		Vector v = l.getRV();
		double t = collideFirstCalcTime(x, v);
		Point p = new Point(l.mul(t));
		if(p.z > getZE()){
			p.x = Double.NaN;
			p.y = Double.NaN;
			p.z = Double.NaN;
		}
		return new ConeCollision(t, p);
		
	}
	
	public double collideCalcTime(Vector _x, Vector _v){
		
		double x = _x.x;
		double y = _x.y;
		double z = _x.z;
		double vx = _v.x;
		double vy = _v.y;
		double vz = _v.z;
		double tan = MathHelper.square(MathHelper.tan(getOmega()));
		
		double t1 = (x*vx + y*vy - z*vz*tan) / (vz*vz*tan - vx*vx - vy*vy);
		double t = t1*2;
		
		return t;
		
	}
	
	public double collideFirstCalcTime(Vector _x, Vector _v){
		
		double x = _x.x;
		double y = _x.y;
		double z = _x.z;
		double vx = _v.x;
		double vy = _v.y;
		double vz = _v.z;
		double tan = MathHelper.square(MathHelper.tan(getOmega()));
		
		double t1 = MathHelper.square((x*vx + y*vy - z*vz*tan) / (vx*vx + vy*vy - vz*vz*tan));
		double t2 = (z*z*tan - x*x - y*y) / (vx*vx + vy*vy - vz*vz*tan);
		double t3 = (x*vx + y*vy - z*vz*tan) / (vx*vx + vy*vy - vz*vz*tan);
		
		double gamma = Math.abs(MathHelper.arctan(MathHelper.root(MathHelper.square(vx)+MathHelper.square(vy)) / vz));
		double t = -1d;
		
		if(gamma > getOmega()){
			t = Math.abs(MathHelper.root( t1 + t2 ) - t3);
		}else{
			t = Math.abs(-MathHelper.root( t1 + t2 ) - t3);	
		}
		
		return t;
		
	}
	
	@Override
	public void render(Graphics2D g, Scene s){
		if(s == Scene.SIDE){
			
			Vector v1 = vup;
			Vector v2 = vdown;
			
			Point vs1 = new Point(v1.clone().mul(getZA()));
			Point vs2 = new Point(v2.clone().mul(getZA()));
			Point ve1 = new Point(v1.clone().mul(getZE()));
			Point ve2 = new Point(v2.clone().mul(getZE()));
			
			fixPoints(s, vs1, vs2, ve1, ve2);
			
			g.setColor(c);
			g.setStroke(new BasicStroke(3f));
			g.draw(new Line2D.Float(f(vs1.z), f(vs1.x), f(ve1.z), f(ve1.x)));
			g.draw(new Line2D.Float(f(vs2.z), f(vs2.x), f(ve2.z), f(ve2.x)));
			
		}
		
		if(s == Scene.FRONT){
			
			g.setColor(c);
			g.setStroke(new BasicStroke(3f));
			
			double rk = MathHelper.tan(getOmega()) * getZA() * Main.zoom_front;
			double rg = MathHelper.tan(getOmega()) * getZE() * Main.zoom_front;
			Point center = new Point(0, 0, 0);
			
			fixPoints(s, center);
			
			rk *= 2;
			rg *= 2;
			
			{
				
				double x = center.x-(rk/2);
				double y = center.y-(rk/2);
				g.draw(new Ellipse2D.Double(x, y, rk, rk));
				
			}
			
			{
				
				double x = center.x-(rg/2);
				double y = center.y-(rg/2);
				g.draw(new Ellipse2D.Double(x, y, rg, rg));
				
			}
			
		}
	}
	
}
