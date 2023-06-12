
import java.math.BigInteger;
import java.security.SecureRandom;


public class ShamirSecretSharing {

private int threshold;
private int shareNumber;
private final BigInteger primeInt;
private final BigInteger[] polynomial;
private SecureRandom secureRandom;

// Constructor
// prime int is the finite field number, some large prime number
public ShamirSecretSharing(int threshold, int shareNumber, String message, BigInteger primeInt, SecureRandom secureRandom) {
    this.threshold = threshold;
    this.shareNumber = shareNumber;
    this.primeInt = primeInt;
    this.secureRandom = secureRandom;

    BigInteger messageNumber = new BigInteger(message);

    polynomial = new BigInteger[threshold];
    polynomial[0] = messageNumber;

    // fills in remaining terms in polynomial
    // first term is the message
    // remaing terms are all random
    for (int i = 1; i < polynomial.length; i ++) {
        BigInteger randomBigInt = new BigInteger(8, secureRandom);
        polynomial[i] = randomBigInt; 
    }
}

// evaluate polynomial
private BigInteger evaluatePolynomial(int x) {
    BigInteger sum = BigInteger.ZERO;
  
    sum = sum.add(polynomial[0]);
    for (int term = 1; term < polynomial.length; term ++) {
        sum = sum.add(polynomial[term].multiply(BigInteger.valueOf(x).pow(term)));
    }

    sum = sum.mod(primeInt);
    return sum;
}




// Generate Shares, returns array of points
// In the array, only the Y value, is returned. The x value can be implied from the index, starting from 1, add 1 each time. 
public BigInteger[] generateShares() {

    BigInteger[] shares = new BigInteger[shareNumber]; 
    for (int x = 0; x < shareNumber; x++ ) {
        shares[x] = evaluatePolynomial(x + 1);
    }

    return shares;
}




// interpolate method

// reconstruct



}   