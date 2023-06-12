import java.math.BigInteger;
import java.security.SecureRandom;

public class Testing {
    public static void main(String args[]) {
        SecureRandom secureRandom = new SecureRandom();

   
        // BigInteger primeField = BigInteger.probablePrime(128, secureRandom);
        BigInteger primeField = BigInteger.valueOf(1631);
        BigInteger secret = BigInteger.valueOf(1234);
        ShamirSecretSharing sss = new ShamirSecretSharing(3, 6, secret, primeField, secureRandom); 

       

        BigInteger[] cheese = sss.generateShares();
     
        
        BigInteger finalPolynomial = sss.interpolate(cheese);
        System.out.println(finalPolynomial);
    
    }

}
