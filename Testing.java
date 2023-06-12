import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        final BigInteger field = new BigInteger("309485009821345068724781371"); 

        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to (1) generate shares or (2) interpolate the secret?");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.println("What secret would you like to use?");
            String secret = scanner.nextLine();
            BigInteger secretNumber = new BigInteger(secret.getBytes());

            System.out.println("How many shares would you like to generate?");
            int shareNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.println("What is the threshold?");
            int thresholdNumber = scanner.nextInt();
            scanner.nextLine();

            if (thresholdNumber >= shareNumber) {
                System.out.println("Threshold Number must be less than shareNumber");
                System.exit(0);
            }
            SecureRandom secure = new SecureRandom();

            ShamirSecretSharing shamir = new ShamirSecretSharing(thresholdNumber, shareNumber, secretNumber , field, secure);

            BigInteger[] generatedShares = shamir.generateShares();

            System.out.println("Here are your shares:");
            for (int i = 0; i < generatedShares.length; i++) {
                System.out.println("Share #" + (i+1) + " Share Value " + generatedShares[i]);
            }

          }  else if (choice == 2) {
                System.out.println("How many shares do you want to use for interpolation?");
                int numberOfShares = scanner.nextInt();
                scanner.nextLine();

                BigInteger[] shares = new BigInteger[numberOfShares];
                BigInteger[] shareNumbers = new BigInteger[numberOfShares];
                for (int i = 0; i < numberOfShares; i++) {
                    System.out.println("Enter value for share #" + (i+1));
                    BigInteger shareValue = new BigInteger(scanner.nextLine());
                    System.out.println("Please enter the share number:");
                    BigInteger shareNumber = new BigInteger(scanner.nextLine());
                    shareNumbers[i] = shareNumber;
                    shares[i] = shareValue;

                }

                BigInteger secretBigInt = ShamirSecretSharing.interpolate(shareNumbers ,shares, field);
                
                // Convert BigInteger back into a string
                String secretString = new String(secretBigInt.toByteArray());
                
                System.out.println("The interpolated secret is: " + secretString);
    }

        scanner.close();
    }
}
