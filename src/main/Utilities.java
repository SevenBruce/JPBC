package main;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;


public class Utilities {
	
	public static byte [] string2bytes (String originalString) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		return encodedhash;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public static String string2hash(String originalString) throws NoSuchAlgorithmException{
		byte[] encodedhash = string2bytes(originalString);
		return bytesToHex(encodedhash);
//		DigestUtils.sha256Hex(originalString)
//		String sha256hex = DigestUtils.sha256Hex(originalString);
	}
	
	
	private static BigInteger LIMIT = BigInteger.valueOf(PublicParameters.LIMIT);
//	private static BigInteger LEAPS = new BigInteger("500");
	private static BigInteger LEAPS = BigInteger.valueOf(PublicParameters.LEAPES);
	private static BigInteger LEAPS_DIVIDE = BigInteger.valueOf(4);
//	private static BigInteger ALPHA = BigInteger.valueOf(303);
//	private static BigInteger MOD;
	
//	public static int kangaroo(BigInteger Num,BigInteger mod){
//		int kanResult = -1;
//		MOD = mod;
//		BigInteger x,trap;
//		BigInteger[] table = new BigInteger[32];
//		int i,j,m;
//		BigInteger dm, dn, s;
//		BigInteger [] distance = new BigInteger[32];
//		
//		for (s = BigInteger.ONE, m = 1 ; ; m++){
//			distance[m-1] = s;
//			s= s.add(s);
//			BigInteger aLEAPS = LEAPS.divide(LEAPS_DIVIDE);
//			if((s.add(s)).divide(BigInteger.valueOf(m)).compareTo(aLEAPS)>0)break;
//		}
//		
//		for (i = 0;i<m;i++){
//			table[i] = ALPHA.modPow(distance[i], MOD);
//		}
//		
//		x = ALPHA.modPow(LIMIT,MOD);
//		
////		System.out.println("setting trap...");
//		Random random = new Random(System.currentTimeMillis());
//		for(dn=BigInteger.ZERO,j=0;j<LEAPS.intValue();j++){
//			
//			i = x.mod(BigInteger.valueOf(m)).intValue();
////			i = random.nextInt(m);
////			System.out.println("org..." + i);
//			x = (x.multiply(table[i])).mod(MOD);
//			dn = dn.add(distance[i]);
//		}
//		
//		trap = x;
//		Random randomWild = new Random(System.currentTimeMillis()%m);
//		for(dm=BigInteger.ZERO;;){
//			i = Num.mod(BigInteger.valueOf(m)).intValue();
////			i = randomWild.nextInt(m);
////			System.out.println("com..." + i);
////			if(Num.multiply(table[i]).compareTo(MOD)>0){
////				System.out.println(Num.multiply(table[i]));
////				System.out.println(MOD);
////			}
//			Num = (Num.multiply(table[i])).mod(MOD);
//			dm = dm.add(distance[i]);
//			if(Num.equals(trap))break;
//			if(dm.compareTo(LIMIT.add(dn))>0)break;
//		}
//		
//		if (dm.compareTo(LIMIT.add(dn))>0)
//        { /* trap stepped over */
//			System.out.println("trap failed...");
//			return kanResult;
//        }
//		
////		System.out.println("get the kangaroo...");
////		System.out.println("MOD = " + MOD);
////		System.out.println("Discrete log of y = " + (LIMIT.add(dn).subtract(dm)));
//		kanResult = (LIMIT.add(dn).subtract(dm)).intValue();
//		return kanResult;
//	}
//	
	
	//kangaroo in the group G1
	public static int longKangaroo(Element generator,Element num){
	/* Pollard's lambda algorithm for finding discrete logs  *
     * which are known to be less than a certain limit LIMIT */
		int kanResult = -1;
		Element trap,x;
		Element Num = num;
		Element[] table = new Element[32];
		int i,j,m;
		BigInteger dm, dn, s;
		BigInteger [] distance = new BigInteger[32];
		
		for (s = BigInteger.ONE, m = 1 ; ; m++){
			distance[m-1] = s;
			s= s.add(s);
			BigInteger aLEAPS = LEAPS.divide(LEAPS_DIVIDE);
			if((s.add(s)).divide(BigInteger.valueOf(m)).compareTo(aLEAPS)>0)break;
		}
		
		for (i = 0;i<m;i++){
			/* create table */
			table[i] = generator.pow(distance[i]);
//			System.out.println("trap failed... : " + table[i]);
		}
		
		x = generator.pow(LIMIT);
//		System.out.println("setting trap..." + m);
		for(dn=BigInteger.ZERO,j=0;j<LEAPS.intValue();j++){
			/* set traps beyond LIMIT using tame kangaroo */
			try{
				i = (x.toBigInteger().intValue())%m; /* random function */
			}catch(Exception e){
				i = 0;
			}
			x = x.mul(table[i]);
			dn = dn.add(distance[i]);
		}
		
		trap = x;
//		Random randomWild = new Random(System.currentTimeMillis());
		for(dm=BigInteger.ZERO;;){
			try{
				i = (x.toBigInteger().intValue())%m;;
			}catch(Exception e){
				i = 0;
			}			
			Num = Num.mul(table[i]);
			dm = dm.add(distance[i]);
			
			if(Num.equals(trap))break;
			if(dm.compareTo(LIMIT.add(dn))>0)break;
		}
		if (dm.compareTo(LIMIT.add(dn))>0)
        { /* trap stepped over */
//			System.out.println("trap failed... : " + dm);
			return kanResult;
        }
		
//		System.out.println("get the kangaroo...");
//		System.out.println("MOD = " + dm);
//		System.out.println("Discrete log of y = " + (LIMIT.add(dn).subtract(dm)));
		kanResult = (LIMIT.add(dn).subtract(dm)).intValue();
		return kanResult;

		
	}
	
	
	//kangaroo in the group G1
		public static long kangaroo(Element generator,Element num){
		/* Pollard's lambda algorithm for finding discrete logs  *
	     * which are known to be less than a certain limit LIMIT */
			int kanResult = -1;
			Element trap,x;
			Element Num = num;
			Element[] table = new Element[32];
			int i,j,m;
			BigInteger dm, dn, s;
			BigInteger [] distance = new BigInteger[32];
			
			for (s = BigInteger.ONE, m = 1 ; ; m++){
				distance[m-1] = s;
				s= s.add(s);
				BigInteger aLEAPS = LEAPS.divide(LEAPS_DIVIDE);
				if((s.add(s)).divide(BigInteger.valueOf(m)).compareTo(aLEAPS)>0)break;
			}
			
			for (i = 0;i<m;i++){
				/* create table */
				table[i] = generator.pow(distance[i]);
//				System.out.println("trap failed... : " + table[i]);
			}
			
			
			x = generator.pow(LIMIT);
//			System.out.println("setting trap..." + m);
			for(dn=BigInteger.ZERO,j=0;j<LEAPS.intValue();j++){
				/* set traps beyond LIMIT using tame kangaroo */
				try{
					i = (x.toBigInteger().intValue())%m; /* random function */
				}catch(Exception e){
					i = 0;
				}
				x = x.mul(table[i]);
				dn = dn.add(distance[i]);
			}
			
			long sl = System.currentTimeMillis();
			trap = x;
//			Random randomWild = new Random(System.currentTimeMillis());
			for(dm=BigInteger.ZERO;;){
				try{
					i = (x.toBigInteger().intValue())%m;;
				}catch(Exception e){
					i = 0;
				}			
				Num = Num.mul(table[i]);
				dm = dm.add(distance[i]);
				
				if(Num.equals(trap))break;
				if(dm.compareTo(LIMIT.add(dn))>0)break;
			}
			if (dm.compareTo(LIMIT.add(dn))>0)
	        { /* trap stepped over */
//				System.out.println("trap failed... : " + dm);
				long el = System.currentTimeMillis();
				return el-sl;
	        }
			
//			System.out.println("get the kangaroo...");
//			System.out.println("MOD = " + dm);
//			System.out.println("Discrete log of y = " + (LIMIT.add(dn).subtract(dm)));
			kanResult = (LIMIT.add(dn).subtract(dm)).intValue();
			long el = System.currentTimeMillis();
			return el-sl;

			
		}
	
	
	
	//kangaroo in the group G1
		public static int linearSearch(Element generator,Element num){
			int kanResult = -1;

			Element Num = generator;
			BigInteger s = BigInteger.ONE;
			
			for (s = BigInteger.ONE; s.compareTo(LIMIT)<=0;s=s.add(BigInteger.ONE)){
				if(Num.equals(num))
					return s.intValue();
				Num = Num.mul(generator);
			}
			return kanResult;
		}

	
	
	
	

}
