import static utils.Utils.*;
import java.math.BigInteger;

class Assignment2 implements Assignment2Interface {
	public static void main( String args[] ) {
		final BigInteger primeModulus = new BigInteger("127535493162319491367599324144113397151267257148437073602577007476663416682283880509178453980712280826088110844217588960715906052913674575697307662046615167011490493434401195847397477647858404441309473347516622650761427775164494413943726345063939069393839834502462904588970717436583818040772899540342776230691");
		final BigInteger generator = new BigInteger("48400272268446149001949240086817036655504892222066667076102118991277188776868539342358437315902355506279689976415512494001768854719270638359182186506891569799700611107926787284268168015589160654008081804779596215698189364060427211358575700634110443239682119451461713898704893457201278947928473195501316406632");

		Assignment2 a2 = new Assignment2();

		// Generate a random secret key x with 0 < x < p-1
		BigInteger privateKey = boundedRandom(0, primeModulus);
		// Compute the public key y as y = gx (mod p)
		BigInteger publicKey = a2.generateY(generator, privateKey, primeModulus);

		
	}
	/* the public key y and is generated from the given generator, secretKeyand modulus */
	BigInteger generateY(BigInteger generator, BigInteger secretKey, BigInteger modulus);
	
	/* the first part of the ElGamal signature from the given generator, random value k and modulus */
	BigInteger generateR(BigInteger generator, BigInteger k, BigInteger modulus);
	
	/* the second part of the ElGamal signature from the given plaintext, secretKey, first signature part r, random value k and modulus
	 * s = (H(m)-xr)k^-1 (mod p-1) where H is the hash function SHA-256.*/
	BigInteger generateS(byte[] plaintext, BigInteger secretKey, BigInteger r, BigInteger k, BigInteger modulus) {
		return new BigInteger(sha256sum(plaintext)).subtract(secretKey.multiply(r)).multiply(calculateInverse(k)).mod(modulus.subtract(BigInteger.ONE));
	}

	/* the GCD of the given val1 and val2 */
	BigInteger calculateGCD(BigInteger val1, BigInteger val2);
					
	/* the modular inverse of the given val using the given modulus */
	BigInteger calculateInverse(BigInteger val, BigInteger modulus);

}
