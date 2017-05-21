package me.timon.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import me.timon.ts.Window.Scene;

public class Line extends GeometryObject{

	public Vector sv;
	public Vector rv;
	public double renderstart = 99999d;
	public double renderend = 99999d;
	
	public Line(){
		
	}
	
	public Line(Vector sv, Vector rv){
		this.sv = sv;
		this.rv = rv;
	}
	
	public Vector getSV(){
		return sv.clone();
	}
	
	public Vector getRV(){
		return rv.clone();
	}
	
	public Vector mul(double t){
		return new Vector(sv.x+t*rv.x, sv.y+t*rv.y, sv.z+t*rv.z);
	}
	
	public void setRenderingOptions(double renderstart, double renderend){
		this.renderstart = renderstart;
		this.renderend = renderend;
	}
	
	public void render(Graphics2D g, Scene s, double renderstart, double renderend){
		double _renderstart = this.renderstart;
		double _renderend = this.renderend;
		this.renderstart = renderstart;
		this.renderend = renderend;
		render(g, s);
		this.renderstart = _renderstart;
		this.renderend = _renderend;
	}
	
	@Override
	public void render(Graphics2D g, Scene s) {
		if(s == Scene.SIDE){
			
			Point ps = new Point(getSV().add(getRV().mul(renderstart)));
			Point pe = new Point(getSV().add(getRV().mul(-renderend)));
			
			fixPoints(s, ps, pe);
			
			g.setStroke(new BasicStroke(2));
			g.setColor(c);
			g.draw(new Line2D.Float(f(ps.z), f(ps.x), f(pe.z), f(pe.x)));
		}
		if(s == Scene.FRONT){
			//Wird in der Klasse "Render" behandelt
		}
	}
	
}
