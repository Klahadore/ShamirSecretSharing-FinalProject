
import java.math.BigInteger;


public class ShamirSecretSharing {

private int threshold;
private int shareNumber;
private final BigInteger primeInt;
private final BigInteger[] polynomial;

// Constructor
// prime int is the finite field number, some large prime number
public ShamirSecretSharing(int threshold, int shareNumber, String message, BigInteger primeInt) {
    this.threshold = threshold;
    this.shareNumber = shareNumber;
    this.primeInt = primeInt;


    BigInteger messageNumber = new BigInteger(message);

    polynomial = new BigInteger[threshold];
    polynomial[0] = messageNumber;

    // fills in remaining terms in polynomial
    // first term is the message
    // remaing terms are all random
    for (int i = 1; i < polynomial.length; i ++) {

    }
}



// Generate Shares, returns array of points



// evaluate polynomial

// interpolate method

// reconstruct



}   