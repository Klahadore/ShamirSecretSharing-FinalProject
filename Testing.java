import java.math.BigInteger;
import java.security.SecureRandom;

public class Testing {
    public static void main(String args[]) {
        SecureRandom secureRandom = new SecureRandom();

   
        // BigInteger primeField = BigInteger.probablePrime(128, secureRandom);
        BigInteger primeField = BigInteger.valueOf(1631);
        BigInteger secret = BigInteger.valueOf(1234);
        ShamirSecretSharing sss = new ShamirSecretSharing(3, 6, secret, primeField, secureRandom); 

        sss.generateShares();

        BigInteger[] shares = new BigInteger[6];
        
        for (BigInteger i : shares) {
            System.out.println(i);
        }

        BigInteger[] finalPolynomial = sss.interpolate(shares);
        System.out.println(finalPolynomial[0]);
    
    }

}
