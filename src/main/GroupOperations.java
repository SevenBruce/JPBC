package main;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class GroupOperations {
	
	private static int count = PublicParameters.COUNT;
	private static int LIMIT = PublicParameters.LIMIT;
	private static long sl;
	private static long el;
	
	static int rBits = 160;
	static int qBits = 512;
	static TypeACurveGenerator pg = new TypeACurveGenerator(rBits, qBits);
	static PairingParameters typeAParams = pg.generate();
	static Pairing pairing = PairingFactory.getPairing(typeAParams);
	
	public static void GroupOperations(){
		
//		G1MulTime(pairing);
//		G1MulZrTime(pairing);
//	  	G1PowTime(pairing);
//	  	G1AddTime(pairing);
//	  	GTPairingTime(pairing);
//
//	  	GTMulTime(pairing);
//	  	GTAddTime(pairing);
//	  	GTPowTime(pairing);
//
		hashTimes();
//		Pol_time(pairing);
	}
	
	
	
	private static void hashTimes(){
		try {
			
			Hash2G1Time(pairing);
			Hash2ZrTime(pairing);
//			Hash2GtTime(pairing);
			GeneralHashTime();
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void Hash2GtTime(Pairing pairing) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String originalString = "asdfasdfasdfasdfasdfasdfasdfasdfafasfdfad";
		double total = 0;
		
		for(int i = 0; i < count; i++){
			originalString = originalString + i;
			total = total + Hash2GtTime(pairing,originalString);
		}
		
		System.out.println("Hash2GtTime time is " + total/count);
	}
	
	
	private static void Hash2ZrTime(Pairing pairing) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String originalString = "asdfasdfasdfasdfasdfasdfasdfasdfafasfdfad";
		double total = 0;
		
		for(int i = 0; i < count; i++){
			originalString = originalString + i;
			total = total + Hash2ZrTime(pairing,originalString);
		}
		total = total / 1000000;
		System.out.println("Hash2ZrTime time is " + total/count);
	}
	
	
	private static void Hash2G1Time(Pairing pairing) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String originalString = "asdfasdfasdfasdfasdfasdfasdfasdfafasfdfad";
		double total = 0;
		
		for(int i = 0; i < count; i++){
			originalString = originalString + i;
			total = total + Hash2G1Time(pairing,originalString);
		}
		total = total/1000000;
		System.out.println("Hash2G1Time time is " + total/count);
	}
	
	private static void Hash2G1TimeFromBytes(Pairing pairing) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String originalString = "asdfasdfasdfasdfasdfasdfasdfasdfafasfdfad";
		double total = 0;
		
		for(int i = 0; i < count; i++){
			originalString = originalString + i;
			byte []resultBytes = originalString.getBytes(StandardCharsets.UTF_8);
			total = total + Hash2G1Time(pairing,resultBytes);
		}
		
		System.out.println("Hash2G1Time time is " + total/count);
	}

	private static void GeneralHashTime() throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String originalString = "asdfasdfasdfasdfasdfasdfasdfasdfafasfdfad";
		String resultString = "";
		double total = 0;
		MessageDigest messageDigest;
		for(int i = 0; i < count; i++){
			originalString = originalString + i;
			sl = System.nanoTime();
//            resultString = DigestUtils.sha256Hex(originalString);
			resultString = Utilities.string2hash(originalString);
			el = System.nanoTime();
			total = total + (el-sl);
		}
		total = total/1000000;
		System.out.println("General Hash time is " + total/count);
	}

	private static void G1MulTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		
		for(int i = 0; i < count; i++){
			total = total + calculateG1MulTime(pairing);
		}
		total = total/1000000;
		System.out.println("G1Mul time is " + total/count);
	}
	
	private static long calculateG1MulTime(Pairing pairing)
	{
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element G_1_p = pairing.getG1().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_1_m_G_1 = G_1.mul(G_1_p);
		el = System.nanoTime();
		return (el-sl);
	
	}
	
	
	private static void G1MulZrTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		
		for(int i = 0; i < count; i++){
			total = total + calculateG1MulZrTime(pairing);
		}
		total = total/1000000;
		System.out.println("G1MulZn time is " + total/count);
	}
	
	private static long calculateG1MulZrTime(Pairing pairing)
	{
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element Zr = pairing.getZr().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_1_m_G_1 = G_1.mulZn(Zr);
		el = System.nanoTime();
		return (el-sl);
	
	}
	
	
	private static void G1PowTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		 
		for(int i = 0; i < count; i++){
			total = total + calculateG1PowTime(pairing);
		}
		total = total/1000000;
		System.out.println("G1Pow time is " + total/count);
	}
	
	private static long calculateG1PowTime(Pairing pairing)
	{
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element Z = pairing.getZr().newRandomElement().getImmutable();;
		
		sl = System.nanoTime();
		Element G_1_Pow_Zn = G_1.powZn(Z);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static void G1AddTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		 
		for(int i = 0; i < count; i++){
			total = total + calculateG1AddTime(pairing);
		}
		total = total / 1000000;
		System.out.println("G1Add time is " + total/count);
	}
	
	private static long calculateG1AddTime(Pairing pairing)
	{
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element G_1_Add = pairing.getG1().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_1_Add_G_1 = G_1.add(G_1_Add);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static void G2MulTime(Pairing pairing){
		
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		
		for(int i = 0; i < count; i++){
			total = total + calculateG2MulTime(pairing);
		}
		
		System.out.println("G2Mul time is " + total/count);
	}
	
	private static long calculateG2MulTime(Pairing pairing)
	{
		Element G_2 = pairing.getG2().newRandomElement().getImmutable();
		Element G_2_p = pairing.getG2().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_2_m_G_2 = G_2.mul(G_2_p);
		el = System.nanoTime();
		return (el-sl);
	}
	
	
	private static void G2PowTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		 
		for(int i = 0; i < count; i++){
			total = total + calculateG2PowTime(pairing);
		}
		
		System.out.println("G2Pow time is " + total/count);
	}
	
	private static long calculateG2PowTime(Pairing pairing)
	{
		Element G_2 = pairing.getG2().newRandomElement().getImmutable();
		Element Z = pairing.getZr().newRandomElement().getImmutable();;
		
		sl = System.nanoTime();
		Element G_2_Pow_Zn = G_2.powZn(Z);
		el = System.nanoTime();
		return (el-sl);
	}

	private static void GTMulTime(Pairing pairing){
		
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		
		for(int i = 0; i < count; i++){
			total = total + calculateGTMulTime(pairing);
		}
		total = total / 1000000;
		System.out.println("GTMul time is " + total/count);
	}
	
	private static long calculateGTMulTime(Pairing pairing)
	{
		Element G_T = pairing.getGT().newRandomElement().getImmutable();
		Element G_T_p = pairing.getGT().newRandomElement().getImmutable();
		
		sl = System.nanoTime();;
		Element G_T_m_G_T = G_T.mul(G_T_p);
		el = System.nanoTime();;
		return (el-sl);
	}
	
private static void GTAddTime(Pairing pairing){
		
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		
		for(int i = 0; i < count; i++){
			total = total + calculateGTAddTime(pairing);
		}
		total = total/1000000;
		System.out.println("GTAdd time is " + total/count);
	}
	
	private static long calculateGTAddTime(Pairing pairing)
	{
		Element G_T = pairing.getGT().newRandomElement().getImmutable();
		Element G_T_p = pairing.getGT().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_T_m_G_T = G_T.add(G_T_p);
		el = System.nanoTime();
		return (el-sl);
	}
	
	
	private static void GTPowTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		 
		for(int i = 0; i < count; i++){
			total = total + calculateGTPowTime(pairing);
		}
		total = total/1000000;
		System.out.println("GTPow time is " + total/count);
	}
	
	private static long calculateGTPowTime(Pairing pairing)
	{
		Element G_T = pairing.getGT().newRandomElement().getImmutable();
		Element Z = pairing.getZr().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_T_Pow_Zn = G_T.powZn(Z);
		el = System.nanoTime();
		return (el-sl);
	}

	private static void GTPairingTime(Pairing pairing){
		//this function is used to estimate the time needed to 
		//conduct an multiplication operation in group G1
		double total = 0;
		 
		for(int i = 0; i < count; i++){
			total = total + calculatepairingTime(pairing);
		}
		total = total/1000000;
		System.out.println("Pairing time is " + total/count);
	}
	
	private static long calculatepairingTime(Pairing pairing)
	{
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element G_2 = pairing.getG2().newRandomElement().getImmutable();
		
		sl = System.nanoTime();
		Element G_p_G = pairing.pairing(G_1, G_2);
//		System.out.println("Pairing result is " + G_p_G);
		el = System.nanoTime();
		return (el-sl);
	}
	
	
	private static long Hash2GtTime(Pairing pairing, String originalString) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//Hash the String originalString to G_1
		sl = System.nanoTime();
		byte[] oiginalBytes = originalString.getBytes(StandardCharsets.UTF_8);
		Element hash_Z_p = pairing.getGT().newElement().setFromHash(oiginalBytes, 0, oiginalBytes.length);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static long Hash2ZrTime(Pairing pairing, String originalString) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//Hash the String originalString to G_1
		sl = System.nanoTime();
		byte[] oiginalBytes = originalString.getBytes(StandardCharsets.UTF_8);
		Element hash_Z_p = pairing.getZr().newElement().setFromHash(oiginalBytes, 0, oiginalBytes.length);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static long Hash2G1Time(Pairing pairing, String originalString) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//Hash the String originalString to G_1
//		byte[] oiginalBytes = originalString.getBytes(StandardCharsets.UTF_8);
		sl = System.nanoTime();
		byte[] oiginalBytes = originalString.getBytes(StandardCharsets.UTF_8);
		Element hash_Z_p = pairing.getG1().newElement().setFromHash(oiginalBytes, 0, oiginalBytes.length);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static long Hash2G1Time(Pairing pairing, byte[] oiginalBytes){
		//try to hash byte[] byteArray_G_1 to group G_1
		sl = System.nanoTime();
		Element hash_Z_p = pairing.getG1().newElement().setFromHash(oiginalBytes, 0, oiginalBytes.length);
		el = System.nanoTime();
		return (el-sl);
	}
	
	private static void Pol_time(Pairing pairing){
//      generate and storage a generator, as the Elliptic Curve is an additive Group, 
//		any element of Group G can be a generator.
		Element generator = pairing.getG1().newRandomElement().getImmutable();
		int times = 0;
		double total = 0;
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < count;i++){
			long randomNum = random.nextInt(LIMIT);
//			Element num =  generator.pow(BigInteger.valueOf(1));
//			System.out.println(randomNum);
			Element num =  generator.pow(BigInteger.valueOf(randomNum));
			sl = System.nanoTime();
			int result = Utilities.longKangaroo(generator,num);
//			int result = Utilities.linearSearch(generator, num);
			el = System.nanoTime();
			total = total + el - sl;
//			System.out.println(" " + result +" " + randomNum);
			if (result == randomNum){
				times ++;
			}
//			total = total + Utilities.kangaroo(generator,num);
		}
		total = total/ 1000000;
		System.out.println("Pol_time " + total/count);
		System.out.println("times    " + times);
		System.out.println("count    " + count);
	}
	
	
	
	
//  


}
