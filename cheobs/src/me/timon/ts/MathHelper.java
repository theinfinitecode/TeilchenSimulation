package me.timon.ts;

public class MathHelper {

	public static double sin(double angle){
		return Math.sin(Math.toRadians(angle));
	}
	
	public static double cos(double angle){
		return Math.cos(Math.toRadians(angle));
	}
	
	public static double tan(double angle){
		return Math.tan(Math.toRadians(angle));
	}
	
	public static double cot(double angle){
		return 1/Math.tan(Math.toRadians(angle));
	}

	public static double arcsin(double angle){
		return Math.toDegrees(Math.asin(angle));
	}

	public static double arccos(double angle){
		return Math.toDegrees(Math.acos(angle));
	}

	public static double arctan(double angle){
		return Math.toDegrees(Math.atan(angle));
	}
	
	public static double raise(double base, double exp){
		return Math.pow(base, exp);
	}
	
	public static double square(double base){
		return MathHelper.raise(base, 2);
	}
	
	public static double cube(double base){
		return MathHelper.raise(base, 3);
	}
	
	public static double root(double base){
		return Math.sqrt(base);
	}
	
}
