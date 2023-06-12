
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
private BigInteger lagrangeBasis(int j, BigInteger[] xValues, BigInteger x) {
    BigInteger result = BigInteger.ONE;

    for (int m = 0; m < xValues.length; m++) {
        if (m != j) {
            BigInteger numerator = x.subtract(xValues[m]);
            BigInteger denominator = xValues[j].subtract(xValues[m]);
            BigInteger term = numerator.multiply(denominator.modInverse(this.primeInt));
            result = result.multiply(term).mod(this.primeInt);
        }
    }

    return result;

}


public BigInteger interpolate(BigInteger[] yValues) {
    BigInteger sum = BigInteger.ZERO;

    for (int j = 0; j < yValues.length; j++) {
        BigInteger xJ = BigInteger.valueOf(j + 1); // x value is index + 1
        BigInteger lagrangeProduct = BigInteger.ONE;

        for (int m = 0; m < yValues.length; m++) {
            if (m != j) {
                BigInteger xM = BigInteger.valueOf(m + 1); // x value is index + 1
                BigInteger numerator = this.primeInt.subtract(xM); // x - x_m
                BigInteger denominator = xJ.subtract(xM).mod(this.primeInt); // x_j - x_m
                BigInteger term = numerator.multiply(denominator.modInverse(this.primeInt));
                lagrangeProduct = lagrangeProduct.multiply(term).mod(this.primeInt);
            }
        }

        BigInteger term = yValues[j].multiply(lagrangeProduct);
        sum = sum.add(term).mod(this.primeInt);
    }

    return sum;
}







}