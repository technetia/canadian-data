/*
 Calculates the check digit of a Canadian Social Insurance Number (SIN) if given a stream of eight digits,
 or verifies the validity of a SIN if given a stream of nine digits.
 
 ---
 
 I, Nicholas Kim, the copyright holder of this work, release this work into the public domain.
 This applies worldwide.
 
 In some countries this may not be legally possible; if so:
 I grant anyone the right to use this work for any purpose, without any conditions,
 unless such conditions are required by law.
 */

public class CalcSINCheckDigit {
    
    // calculate the check digit from a string of given digits
    private static int calcCheckDigit(String digits) {
        
        // Step 1 and 2: double the even-positioned digits, take the digits of each result,
        // and sum them with the odd-positioned digits.
        
        int digit_sum = 0;
        int curr_digit = 0;
        for (int i = 0; i < digits.length(); i++) {
            curr_digit = Integer.parseInt(digits.substring(i, i+1));
            if (i % 2 == 0) {
                // odd-positioned digit; add directly
                digit_sum += curr_digit;
            }
            else {
                // even-positioned digit; double first
                curr_digit *= 2;
                if (curr_digit < 10) {
                    // if the doubling results in a number < 10, simply add that number
                    digit_sum += curr_digit;
                }
                else {
                    // if the doubling results in a number >= 10, it will still be < 20,
                    // so the two digits will be 1, and the result modulo 10
                    digit_sum += (1 + curr_digit % 10);
                }
            }
        }
        
        // Step 3 and 4: multiply by 9, take the last digit, and return the result
        return (digit_sum * 9) % 10;
        
    }
    
    // verify the check digit from a string of given digits
    private static boolean checkCheckDigit(String digits) {
        return calcCheckDigit(digits.substring(0, digits.length()-1)) == Integer.parseInt(digits.substring(digits.length()-1));
    }
    
    private static void usage() {
        System.err.println("\nUsage: java CalcSINCheckDigit n");
        System.err.println("\nn: the first eight digits of a Canadian SIN, if you wish to");
        System.err.println("calculate the check digit. Otherwise, the full nine digits,");
        System.err.println("if you wish to verify the check digit is correct.\n");
        System.exit(1);
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }
        
        String n = args[0];
        try {
            if (n.length() == 8) {
                System.out.println("The check digit for this SIN is " + String.valueOf(calcCheckDigit(n)) + ".");
            }
            else if (n.length() == 9) {
                System.out.println("The SIN is " + (checkCheckDigit(n) ? "valid" : "invalid") + ".");
            }
            else {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e) {
            usage();
        }
        
    }
}
