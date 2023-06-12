
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
public ShamirSecretSharing(int threshold, int shareNumber, BigInteger secret, BigInteger primeInt, SecureRandom secureRandom) {
    this.threshold = threshold;
    this.shareNumber = shareNumber;
    this.primeInt = primeInt;
    this.secureRandom = secureRandom;

    

    polynomial = new BigInteger[threshold];
    polynomial[0] = secret;

    // fills in remaining terms in polynomial
    // first term is the message
    // remaing terms are all random
    for (int i = 1; i < polynomial.length; i ++) {
        BigInteger randomBigInt = new BigInteger(8, secureRandom);
        polynomial[i] = randomBigInt; 
    }
    for (BigInteger j : polynomial) {
        System.out.println(j.toString(10));
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
    System.out.println("sum" + sum.toString());
    return sum;
}




// Generate Shares, returns array of points
// In the array, only the Y value, is returned. The x value can be implied from the index, starting from 1, add 1 each time. 
public BigInteger[] generateShares() {

    BigInteger[] shares = new BigInteger[shareNumber]; 
    for (int x = 0; x < shareNumber; x++ ) {
        shares[x] = evaluatePolynomial(x + 1);
        
    }
    System.out.println("shares" + shares[0].toString());
    return shares;
}




// interpolate method

// public BigInteger interpolate() {
    
// }
// reconstruct
    public BigInteger[] interpolate(BigInteger[] yValues) {
        int n = yValues.length;
        BigInteger[] polynomial = new BigInteger[n];

        for (int i = 0; i < n; i++) {
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    numerator = numerator.multiply(BigInteger.valueOf(j + 1).negate());
                    denominator = denominator.multiply(BigInteger.valueOf(i + 1).subtract(BigInteger.valueOf(j + 1)));
                }
            }

            BigInteger coefficient = yValues[i].multiply(numerator).multiply(denominator.modInverse(primeInt));
            polynomial[i] = coefficient.mod(primeInt);
        }

        return polynomial;
    }
}   