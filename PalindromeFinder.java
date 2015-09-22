/**
 * Created by Tulskih Anton on 10.05.2015.
 *
 * Natural number given. It's need to find the lowest palindrome-number, that is greater than the given number.
 * Example: 256 given, the nearest palindrome, greater than 256 is 262.
 *
 * Input data: random natural number.
 * Output data: the lowest possible palindrome, greater than the given natural number.
 */
public class PalindromeFinder {

    /**
     * Checks if the last digit of the left half of the given number is greater than the first digit of the right half
     * (e.g. 459678  ->  __9 > 6__).
     * If so - returns true, if it is less - returns false, if they're equal - compare the last-1 digit of the first half
     * and the first+1 digit of the second half and so on (e.g. 159917  ->  __9 == 9__  ->  _5_ > _1_).
     *
     * @param first the first half of the given number.
     * @param second the second half of the given number.
     * @return true if the last digit of the left half of the given number is greater than the first digit of the right
     * half, false - if it's not.
     */
    static boolean firstHalfDigitGreaterThanSecond(String first, String second) {

        boolean isGreater = false;

        loop: for (int i = 0, j = first.length()-1; i < second.length(); i++, j--) {

            if (first.charAt(j) > second.charAt(i)) {

                isGreater = true;
                break loop;

            } else if (first.charAt(j) < second.charAt(i)) {

                isGreater = false;
                break loop;

            }

        }

        return isGreater;

    }

    /**
     * Checks if the given number is a palindrome consists of 9s (e.g. 99, 999, 9999 etc.), so the nearest palindrome
     * will be one digit more (e.g. 99  ->  101, 999  ->  1001, 9999  -> 10001 etc.)
     *
     * @param value is the given number.
     * @return true if the given number is a palindrome consists of 9s, otherwise - returns false.
     */
    static boolean digitsNumberWillGrow(String value) {

        boolean higherDigitNumber = true;

        loop: for (char i: value.toCharArray()) {

            if (i != '9') {

                higherDigitNumber = false;
                break loop;

            }

        }

        return higherDigitNumber;

    }

    public static void main(String[] args) {

        String number = args[0]; // read the first argument from the command line

        System.out.print("Your number is " + number + "\nThe nearest higher palindrome will be: ");

        StringBuilder palindrome = new StringBuilder();

        long middleDigit = -1;

        // If the given number is odd - store a middle digit separately
        if (number.length() % 2 == 1) {

            middleDigit = Long.parseLong(number.substring(number.length() / 2, number.length() / 2 + 1));

        }

        // If the given number less than 10 - the nearest palindrome is 11.
        if (Long.parseLong(number) < 10) {

            palindrome.append("11");

        } else if (digitsNumberWillGrow(number)) { // if the given number is 99, 999, 9999, 99999 etc.

                palindrome.append(Long.toString(Long.parseLong(number) + 2));

        } else {

            /* Store the first half of the given number (e.g. 12345678  ->  1234, 148624  ->  148 etc.).
             * If the given number is odd - the first half will be from the beginning to the middle digit
             * (exclusive) (e.g. 123456789  ->  1234, 45698  ->  45, 9367851  -> 936 etc.).*/
            StringBuilder firstHalf = new StringBuilder(number.substring(0, number.length() / 2));
            String firstHalfString = firstHalf.toString();
            StringBuilder secondHalf;
            String secondHalfString;

            /* If the given number is odd - second half will be from the middle digit(exclusive) to the end
             * of the number (e.g. 1457896  ->  896, 124  ->  4, 49782  ->  82 etc.).*/
            if (number.length() % 2 == 1) {

                secondHalf = new StringBuilder(number.substring(number.length() / 2 + 1));
                secondHalfString = secondHalf.toString();

            // Store the second half of the given number.
            } else {

                secondHalf = new StringBuilder(number.substring(number.length() / 2));
                secondHalfString = secondHalf.toString();

            }

            /* If true, palindrome will consist of the first half + the middle digit (if the given number is odd)
             * + the reversed first half.*/
            if (firstHalfDigitGreaterThanSecond(firstHalfString, secondHalfString)) {

                palindrome.append(firstHalf);

                if (number.length() % 2 == 1) {

                    palindrome.append(middleDigit);

                }

                palindrome.append(firstHalf.reverse());

            /* True if the given number is already a palindrome or if the left half of the given number is less than
             * the first digit of the right half (e.g. 444444 or 174984).
             * If so, palindrome will consist of:
             * 1. if the given number is odd: (first half + the middle digit) increased by one + (the reversed first half without
             *    the last digit (i.e. the middle digit)).
             * 2. if the given number is even: first half + the reversed first half.*/
            } else {

                if (number.length() % 2 == 1) {

                    firstHalf.append(middleDigit).replace(0, firstHalf.length(), Long.toString(Long.parseLong(firstHalf.toString()) + 1));
                    palindrome.append(firstHalf).append(firstHalf.delete(firstHalf.length() - 1, firstHalf.length()).reverse());

                } else {

                    firstHalf.replace(0, firstHalf.length(), Long.toString(Long.parseLong(firstHalfString) + 1));
                    palindrome.append(firstHalf).append(firstHalf.reverse());

                }

            }

        }

        System.out.println(palindrome);

    }

}