package me.timon.geometry;

public class Vector extends GeometryObject{

	public double x;
	public double y;
	public double z;
	
	public Vector(){
		x = 0d;
		y = 0d;
		z = 0d;
	}
	
	public Vector(Vector copy){
		x = copy.x;
		y = copy.y;
		z = copy.z;
	}
	
	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getValue(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public Vector mul(double d){
		x *= d;
		y *= d;
		z *= d;
		return this;
	}
	
	public Vector div(double d){
		x /= d;
		y /= d;
		z /= d;
		return this;
	}
	
	public Vector add(Vector v){
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public Vector subtract(Vector v){
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vector reverse(){
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	public Vector normalize(){
		div(getValue());
		return this;
	}
	
	public double dotMul(Vector v){
		return x*v.x+y*v.y+z*v.z;
	}
	
	public Vector crossMul(Vector v){
		return new Vector(
				y*v.z-z*v.y,
				z*v.x-x*v.z,
				x*v.y-y*v.x
						);
	}
	
	@Override
	public Vector clone(){
		return new Vector(this);
	}
	
	@Override
	public String toString(){
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
}
