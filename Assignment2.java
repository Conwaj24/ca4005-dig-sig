import static Utils.*;
import java.math.BigInteger;

class Assignment2 implements Assignment2Interface {
	public static void main( String args[] ) {
		final BigInteger primeModulus = new BigInteger("127535493162319491367599324144113397151267257148437073602577007476663416682283880509178453980712280826088110844217588960715906052913674575697307662046615167011490493434401195847397477647858404441309473347516622650761427775164494413943726345063939069393839834502462904588970717436583818040772899540342776230691");
		final BigInteger generator = new BigInteger("48400272268446149001949240086817036655504892222066667076102118991277188776868539342358437315902355506279689976415512494001768854719270638359182186506891569799700611107926787284268168015589160654008081804779596215698189364060427211358575700634110443239682119451461713898704893457201278947928473195501316406632");

		Assignment2 a2 = new Assignment2();

		// Generate a random secret key x with 0 < x < p-1
		BigInteger privateKey = boundedRandom(BigInteger.ZERO, primeModulus.subtract(BigInteger.ONE));
		BigInteger publicKey = a2.generateY(generator, privateKey, primeModulus);

		byte[] plaintext = readFileBytes(args[0]);
		BigInteger k, r = BigInteger.ZERO, s = BigInteger.ZERO;
		while(s.equals( BigInteger.ZERO )) {
			k = boundedRandom(biggify(2), primeModulus.subtract(biggify(2)));
			while (!a2.calculateGCD(k, primeModulus).equals(BigInteger.ONE))
				k = boundedRandom(biggify(2), primeModulus.subtract(biggify(2)));
			r = a2.generateR(generator, k, primeModulus);
			s = a2.generateS(plaintext, privateKey, r, k, primeModulus);
		}

		writeFile("y.txt", hexEncode(publicKey));
		writeFile("r.txt", hexEncode(r));
		writeFile("s.txt", hexEncode(s));
	}

	/* the public key y and is generated from the given generator, secretKey and modulus
	* Compute the public key y = g^x (mod p)
	*/
	public BigInteger generateY(BigInteger generator, BigInteger secretKey, BigInteger modulus) {
		return generator.modPow(secretKey, modulus);
	}

	/* the first part of the ElGamal signature from the given generator, random value k and modulus */
	public BigInteger generateR(BigInteger generator, BigInteger k, BigInteger modulus) {
		return generator.modPow(k, modulus);
	}

	/* the second part of the ElGamal signature from the given plaintext, secretKey, first signature part r, random value k and modulus
	* s = (H(m)-xr)k^-1 (mod p-1) where H is the hash function SHA-256.*/
	public BigInteger generateS(byte[] plaintext, BigInteger secretKey, BigInteger r, BigInteger k, BigInteger modulus) {
		return new BigInteger(sha256sum(plaintext)).subtract(secretKey.multiply(r)).multiply(calculateInverse(k, modulus.subtract(BigInteger.ONE)));
	}

	/* the GCD of the given val1 and val2 */
	public BigInteger calculateGCD(BigInteger a, BigInteger b) {
		if (a.equals(BigInteger.ZERO))
			return b;
		if (b.equals(BigInteger.ZERO))
			return a;
		return calculateGCD(b, a.remainder(b));
	}

	public BigInteger[] extendedGCD(BigInteger a, BigInteger b, BigInteger x, BigInteger y) {
		if (a.equals(BigInteger.ZERO))
			return new BigInteger[] {b, BigInteger.ZERO, BigInteger.ONE};

		BigInteger[] gcd = extendedGCD(b.remainder(a), a, x, y);
		return new BigInteger[] {
			gcd[0],
			gcd[2].subtract(b.divide(a).multiply(gcd[1])),
			gcd[1]
		};
	}

	public BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
		return extendedGCD(a,b,BigInteger.ZERO,BigInteger.ZERO);
	}

	/* the modular inverse of the given val using the given modulus */
	public BigInteger calculateInverse(BigInteger val, BigInteger modulus) {
		return extendedGCD(val, modulus)[1].mod(modulus).add(modulus).mod(modulus);
	}
}
