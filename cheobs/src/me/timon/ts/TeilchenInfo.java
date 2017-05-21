package me.timon.ts;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import me.timon.geometry.Line;
import me.timon.geometry.Point;
import me.timon.geometry.Vector;

public class TeilchenInfo {

	public static String newID(){
		return "t" + (Math.abs(new Random().nextInt()));
	}
	
	public String id;
	public Vector x;
	public Vector v;
	
	public TeilchenInfo(String id, Vector x, Vector v){
		this.id = id;
		this.x = x;
		this.v = v;
	}
	
	public ConcurrentHashMap<String, Point> points = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, Vector> vectors = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, Line> lines = new ConcurrentHashMap<>();
	public ConcurrentHashMap<String, Double> doubles = new ConcurrentHashMap<>();
	
	public void setPoint(Object name, Point p){
		if(Double.isNaN(p.x)) return;
		points.put(name.toString(), p);
	}
	
	public void setVector(Object name, Vector v){
		vectors.put(name.toString(), v);
	}
	
	public void setLine(Object name, Line l){
		lines.put(name.toString(), l);
	}
	
	public void setDouble(Object name, Double d){
		if(Double.isNaN(d)) return;
		doubles.put(name.toString(), d);
	}
	
	public Point getPoint(Object name){
		return points.get(name.toString()).clone();
	}
	
	public Vector getVector(Object name){
		return vectors.get(name.toString()).clone();
	}
	
	public Double getDouble(Object name){
		return doubles.get(name.toString());
	}
	
	public Line getLine(Object name){
		return lines.get(name.toString());
	}
	
	public boolean exists(Object name){
		return points.containsKey(name.toString()) || vectors.containsKey(name.toString()) ||
				lines.containsKey(name.toString()) || doubles.containsKey(name.toString());
	}
	
}
