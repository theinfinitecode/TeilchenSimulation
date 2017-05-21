package me.timon.ts;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import me.timon.geometry.GeometryObject;
import me.timon.geometry.Line;
import me.timon.geometry.Point;
import me.timon.geometry.Vector;
import me.timon.ts.Window.Scene;

public class Render extends JPanel{
	
	public Scene s;
	public WindowDrag drag = new WindowDrag();
	
	public Render(Scene s){
		this.s = s;
		
		drag.handleDrag(this);
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(s == Scene.SIDE){
					Main.zoom_side += e.getWheelRotation()/-5.0d;
				}
				if(s == Scene.FRONT){
					Main.zoom_front += e.getWheelRotation()/-5.0d;
				}
				
			}
		});
		
	}
	
	@Override
	public void paint(Graphics g){
		try{
			super.paint(g);
			for(GeometryObject o : Main.getInstance().so){
				o.render((Graphics2D) g, s);
			}
			
			if(Main.renderTeilchen){
				render(g, s);
			}
			
		}catch(java.lang.Exception ex){
			System.out.println("Error -- " + ex.getMessage());
		}
	}
	
	public void render(Graphics g, Scene s){

		Main.time += 0.15;
		
		for(TeilchenInfo i : Main.getAllTeilchen()){
			if(Main.trace){
				renderTeilchenFlying(g, s, i);
			}else{
				renderTeilchen(g, s, i);
			}
		}
		
	}
	
	public void renderTeilchen(Graphics g, Scene s, TeilchenInfo info){
		
		//System.out.println();
		//int e = 0;
		//while(info.exists(e+1)){
		//	++e;
		//	System.out.println(e + " " + info.getPoint(e).toVector().toString() + " " + info.getDouble(e));
		//}
		
		Graphics2D g2 = (Graphics2D) g;
		for(Point p : info.points.values()){
			p.render(g2, s);
		}
		for(Line l : info.lines.values()){
			l.render(g2, s);
		}
		
		if(s == Scene.FRONT){
		
			int i = -2;
			((Graphics2D)g).setStroke(new BasicStroke(1f));
			for(;;){
				++i;
				Point p = null;
				Point p2 = null;
				if(i == -1){
					p = info.getPoint("start");
					p2 = info.getPoint("0");
					g.setColor(info.getLine("sp").getColor());
				}else{
					if(info.points.get(""+(i+1)) == null) break;
					p = info.getPoint(i);
					p2 = info.getPoint(i+1);
					g.setColor(info.getLine(i).getColor());
				}
				p.x = -p.x;
				p2.x = -p2.x;
			
				p.x *= Main.zoom_front;
				p.y *= Main.zoom_front;
				p.z *= Main.zoom_front;
			
				p2.x *= Main.zoom_front;
				p2.y *= Main.zoom_front;
				p2.z *= Main.zoom_front;
			
				p.add(4500, 4500, 0);
				p2.add(4500, 4500, 0);
				g.drawLine(f(p.y), f(p.x), f(p2.y), f(p2.x));
			}
		}
	}
	
	public void renderTeilchenFlying(Graphics g, Scene s, TeilchenInfo info){
		
		Graphics2D g2 = (Graphics2D) g;
		
		double gp = Main.time;
		int pos = -1;
		while(gp > 0){
			++pos;
			if(!info.doubles.containsKey(""+pos)){
				pos--;
				break;
			}
			gp -= info.getDouble(pos);
		}
		
		for(String pn : info.points.keySet()){
			if(pn.matches("[0-9]+")){
				int pi = Integer.parseInt(pn);
				if(pi > pos-1){
					continue;
				}
			}
			Point p = info.getPoint(pn);
			p.render(g2, s);
		}
		for(String ln : info.lines.keySet()){
			boolean moving = false;
			if(ln.matches("[0-9]+")){
				int li = Integer.parseInt(ln);
				if(li > pos-1){
					continue;
				}
				if(li == pos-1){
					moving = true;
				}
			}
			//if(pos == 0){
			//	Line l = info.getLine("sp");
			//	double dist = l.getRV().getValue();
			//	l.render(g2, s, 0, -((dist-Main.time)/dist));
			//	System.out.println(-(1-((dist-Main.time)/dist)));
			//	return;
			//}
			Line l = info.getLine(ln);
			if(moving){
				l.render(g2, s, 0, l.renderend-gp);
			}else{
				l.render(g2, s);
			}
		}
		
		if(s == Scene.FRONT){
		
			int i = -2;
			((Graphics2D)g).setStroke(new BasicStroke(1f));
			for(;;){
				++i;
				Point p = null;
				Point p2 = null;
				double d = 0;
				if(i == -1){
					p = info.getPoint("start");
					p2 = info.getPoint("0");
					g.setColor(info.getLine("sp").getColor());
				}else{
					if(info.points.get(""+(i+1)) == null || i > pos-1) break;
					p = info.getPoint(i);
					p2 = info.getPoint(i+1);
					g.setColor(info.getLine(i).getColor());
					if(i == pos -1){
						d = gp / info.getDouble(i+1);
					}
				}
				
				p.x = -p.x;
				p2.x = -p2.x;
				
				p = new Point(p.toVector().mul(Main.zoom_front));
				p2 = new Point(p2.toVector().mul(Main.zoom_front));
				
				Point ps = p.clone();
				Vector rv = p2.toVector().subtract(ps.toVector());
				Point pe = new Point(ps.toVector().subtract(rv.mul(-((1+d)))));

				//ps.x *= -1;
				//ps.y *= -1;
				//ps.z *= -1;
				
				ps.add(4500, 4500, 0);
				pe.add(4500, 4500, 0);
				
				g.drawLine(f(ps.y), f(ps.x), f(pe.y), f(pe.x));
			}
		}
	}
	
	
	public int f(double d){
		return (int) d;
	}
	
}
