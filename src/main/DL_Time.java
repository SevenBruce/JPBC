package main;

import java.math.BigInteger;
import java.util.Random;

public class DL_Time {
	
	static int count = PublicParameters.COUNT;
	private static long sl;
	private static long el;
	public static void DiscreteLogarithmTime(){
		Random rnd = new Random();
		long seed = System.currentTimeMillis();
		rnd.setSeed(seed);
		BigInteger bigMod = BigInteger.probablePrime(1024, rnd);
		BigInteger bigbase = BigInteger.probablePrime(160, rnd);
		
		// the time of exponent operation when l is small, 60 bit long.
		double total = 0;
		for(int i = 0;i<count;i++){
			BigInteger bigExp =  BigInteger.probablePrime(60, rnd);
			sl = System.nanoTime();
			BigInteger bigResult = bigbase.modPow(bigExp, bigMod);
			el = System.nanoTime();
			total = total + (el-sl);
		}
		total = total / 1000000;
		System.out.println("DL small exponentiation time is " + total/count);
		
		// the time of exponent operation when l is 60~1024 bit long.
		total = 0;
		int bits = 0;
		for(int i = 0;i<count;i++){
			long l = System.currentTimeMillis();
			bits  = (int)( l % 965 ) + 60;
			BigInteger bigExp =  BigInteger.probablePrime(bits, rnd);
			sl = System.nanoTime();
			BigInteger bigResult = bigbase.modPow(bigExp, bigMod);
			el = System.nanoTime();
			total = total + (el-sl);
		}
		total = total / 1000000;
		System.out.println("DL exponentiation time is " + total/count);
			
		
		//multiplication in discrete logarithm(DL) problem 
		total = 0;
		for(int i = 0;i<count;i++){
			long l = System.currentTimeMillis();
			bits  = (int)( l % 965 ) + 60;
			BigInteger bigExp =  BigInteger.probablePrime(bits, rnd);
			bigbase  =  BigInteger.probablePrime(bits, rnd);
			sl = System.nanoTime();
			BigInteger bigResult = bigbase.multiply(bigExp);
			el = System.nanoTime();
			total = total + (el-sl);
		}
		
		total = total / 1000000;
		System.out.println("DL mul time is " + total/count);
	}

}
