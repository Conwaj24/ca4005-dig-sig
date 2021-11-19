import static utils.Utils.*;
import java.math.BigInteger;

class Assignment2 {
	public static void main( String args[] ) {
		
	}
	/* the second part of the ElGamal signature from the given plaintext, secretKey, first signature part r, random value k and modulus
	 * s = (H(m)-xr)k^-1 (mod p-1) where H is the hash function SHA-256.*/
	BigInteger generateS(byte[] plaintext, BigInteger secretKey, BigInteger r, BigInteger k, BigInteger modulus) {
		return new BigInteger(sha256sum(plaintext)).subtract(secretKey.multiply(r)).multiply(this.calculateInverse(k)).mod(modulus.subtract(BigInteger.ONE));
	}
}
