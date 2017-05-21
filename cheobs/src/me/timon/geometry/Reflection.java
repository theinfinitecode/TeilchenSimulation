package me.timon.geometry;

import me.timon.ts.MathHelper;

public class Reflection {

	public static double getAngle(Vector v1, Vector v2){
		//v1 = normale
		//v2 = rv
		return MathHelper.arccos( v1.dotMul(v2) / (v1.getValue()*v2.getValue()) );
	}
	
	public static Vector reflect(){
		return null;
	}
	
}
