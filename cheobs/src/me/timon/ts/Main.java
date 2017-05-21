package me.timon.ts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import me.timon.geometry.Axis;
import me.timon.geometry.Cone;
import me.timon.geometry.ConeCollision;
import me.timon.geometry.GeometryObject;
import me.timon.geometry.Line;
import me.timon.geometry.Point;
import me.timon.geometry.Vector;
import me.timon.geometry.Axis.AxisType;
import me.timon.ts.Window.Scene;

public class Main {

	public static void main(String[] args){
		new Main();
	}
	
	public static Main self;
	public static Main getInstance(){
		return self;
	}
	
	public Window window1;
	public Window window2;
	
	public Cone cone;
	public Line line;
	public Point point;
	
	public ArrayList<GeometryObject> so = new ArrayList<>();
	
	public Main(){
		
		self = this;
		
		double z_a = 30;	//Kegel Anfang
		double z_e = 400;	//Kegel Ende
		double z_omega = 3;	//Kegel Öffnungswinkel
		cone = new Cone(z_omega, z_a, z_e);
		so.add(cone);
		
		Axis axis_x = new Axis(AxisType.X);
		Axis axis_y = new Axis(AxisType.Y);
		Axis axis_z = new Axis(AxisType.Z);
		so.add(axis_x);
		so.add(axis_y);
		so.add(axis_z);
		
		//Vector sx = new Vector(-10, -15, 400);		//Startpunkt des Teilchens
		//Vector sv = new Vector(0.8, -0.25, -5);		//Flugrichtung und Geschwindigkeit
		
		//Vector sx = new Vector(-10, -15, 400);		//Startpunkt des Teilchens
		//Vector sv = new Vector(0.5, -0.25, -5);		//Flugrichtung und Geschwindigkeit
		
		Vector sx = new Vector(-10, -15, 400);		//Startpunkt des Teilchens
		Vector sv = new Vector(0.25, -0.5, -4.3);		//Flugrichtung und Geschwindigkeit
			
		addTeilchen(sx, sv, 10000);
		
		window1 = new Window(Scene.SIDE);
		window2 = new Window(Scene.FRONT);
		
	}
	
	public void addTeilchen(Vector sx, Vector sv, int max){
		
		TeilchenInfo ti = new TeilchenInfo(TeilchenInfo.newID(), sx, sv);
		addTeilchen(ti);
		
		Point start = new Point(sx.clone());
		start.setColor(Color.blue.darker());
		ti.setPoint("start", start);
		
		Line sp = new Line(start.toVector(), sv.clone());
		sp.setColor(Color.green.darker());
		sp.setRenderingOptions(0, -1);
		ti.setLine("sp", sp);
		
		Vector x = sx;
		Vector v = sv;
		
		Line last = null;
		int r = 0;
		
		for(int i = 0; i<max; ++i){
			++r;
			
			Line l = new Line(x, v);
			ConeCollision cc = null;
			
			//Auftreffpunkt auf der Kegeloberfläche sowie die dafür benötigte Zeit t berechnen:
			
			if(i == 0){
				last = l;
				cc = cone.collideFirst(l);
			}else{
				cc = cone.collide(l);
				if(cc.t < 0){	//Ist die benötigte Zeit t negativ, wird das Teilchen den Trichter verlassen / keine weiteren Auftreffpunkte mehr haben
					return;
				}
			}
			
			Point p = cc.p;
			double t = cc.t;
			
			ti.setDouble(i, t);
			
			double omega = cone.getOmega();
			double phi = -1d;	//Winkel des Auftreffpunktes zur X-Achse
			
			if(p.x > 0 && p.y > 0){
				phi = MathHelper.arcsin(p.y / MathHelper.root((MathHelper.square(p.x) + MathHelper.square(p.y))));
			}
			if(p.x < 0 && p.y > 0){
				phi = 180-MathHelper.arcsin(p.y / MathHelper.root((MathHelper.square(p.x) + MathHelper.square(p.y))));
			}
			if(p.x < 0 && p.y < 0){
				phi = 180-MathHelper.arcsin(p.y / MathHelper.root((MathHelper.square(p.x) + MathHelper.square(p.y))));
			}
			if(p.x > 0 && p.y < 0){
				phi = MathHelper.arcsin(p.y / MathHelper.root((MathHelper.square(p.x) + MathHelper.square(p.y))));
			}
			
			
			//Orthonormalbasis
			
			Vector nN = new Vector(MathHelper.cos(phi)*MathHelper.cos(omega), MathHelper.sin(phi)*MathHelper.cos(omega), -MathHelper.sin(omega));
			Vector nA = new Vector(MathHelper.cos(phi)*MathHelper.sin(omega), MathHelper.sin(phi)*MathHelper.sin(omega), MathHelper.cos(omega));
			Vector nT = new Vector(-MathHelper.sin(phi), MathHelper.cos(phi), 0);
			
			//Berechnen der neuen Geschwindigkeit des Teilchens nach dem Auftreffen

			Vector nrv1 = nA.clone().mul(v.clone().dotMul(nA));
			Vector nrv2 = nT.clone().mul(v.clone().dotMul(nT));
			Vector nrv3 = nN.clone().mul(v.clone().dotMul(nN));
			
			Vector oldRV = v.clone();
			Vector newRV = nrv1.add(nrv2).subtract(nrv3);
			
			x = p.toVector();
			v = newRV.clone();
			
			Line newLine = new Line(x, v);
			ti.setLine(i, newLine);
			ti.setPoint(i, p);
			
			last.setRenderingOptions(0, -t);
			
			if(oldRV.z > 0){
				last.setColor(Color.red);
				p.setColor(Color.red.darker());
			}else{
				last.setColor(Color.green.darker());
				p.setColor(Color.green.darker().darker());
			}
			
			last = newLine;
		
			if(p.z > cone.ze){
				break;
			}
			
		}
		
		ti.setPoint("c" + r, new Point(x));
		last.setRenderingOptions(0, 0);
		
		try{
		
		sp = new Line(start.toVector(), ti.getPoint(0).toVector().subtract(start.toVector()));
		sp.setColor(Color.green.darker());
		sp.setRenderingOptions(0, -1);
		ti.setLine("sp", sp);
		
		}catch(java.lang.Exception ex){
			
		}
		
	}
	
	public void update(Scene s){
		
	}
	
	public static double zoom_side = 1d;
	public static double zoom_front = 1d;
	public static boolean enable_circles_f = false;
	public static boolean trace = false;
	public static boolean changeStart = false;
	public static double time = 0d;
	
	public static boolean renderTeilchen = false;
	
	public static ConcurrentHashMap<String, TeilchenInfo> ti = new ConcurrentHashMap<>();
	
	public static void addTeilchen(TeilchenInfo info){
		ti.put(info.id, info);
	}
	
	public static TeilchenInfo getTeilchen(String id){
		return ti.get(id);
	}
	
	public static void removeTeilchen(String id){
		ti.remove(id);
	}
	
	public static HashSet<TeilchenInfo> getAllTeilchen(){
		HashSet<TeilchenInfo> tis = new HashSet<>();
		for(String id : ti.keySet()){
			tis.add(getTeilchen(id));
		}
		return tis;
	}
	
	public void loadWindowInfo(int id){
		/*
		 * [ POS FRONT] -1.0 -- 223.0
[ ZOOM FRONT] 13.799999999999983, side: 3.2000000000000006
[ PANEL FRONT] -3656 -4181
[ POS SIDE] -1.0 -- 1.0
[ ZOOM SIDE] 13.799999999999983, side: 3.2000000000000006
[ PANEL SIDE] -4421 -4398


[ POS FRONT] -7.0 -- 314.0
[ ZOOM FRONT] 15.999999999999975, side: 4.000000000000001
[ PANEL FRONT] -3518 -4145
[ window size FRONT] 1932 -- 761
[ POS SIDE] -9.0 -- 0.0
[ ZOOM SIDE] 15.999999999999975, side: 4.000000000000001
[ PANEL SIDE] -4445 -4376
[ window size SIDE] 1931 -- 322


		 */
		if(id == 1){
			window2.setLocation(0, 223);
			window2.r.setLocation(-3656, -4181);
			window2.setSize(1603, 683);
			Main.zoom_front = 13.8;
			window1.setLocation(0, 0);
			window1.r.setLocation(-4421, -4398);
			window1.setSize(1603, 229);
			Main.zoom_side = 3.2;
		}
		if(id == 2){
			window2.setLocation(0, 314);
			window2.r.setLocation(-3518, -4145);
			window2.setSize(1932, 761);
			Main.zoom_front = 16;
			window1.setLocation(0, 0);
			window1.r.setLocation(-4445, -4376);
			window1.setSize(1931, 322);
			Main.zoom_side = 4;
		}
	}
	
}




