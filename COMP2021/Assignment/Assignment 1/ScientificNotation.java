// JIANG Guanlin 21093962d

package hk.edu.polyu.comp.comp2022.assignment1;

public class ScientificNotation {

    public static double getValueFromAeB(String strSequence){
        int exponent = 0;
        double significand = 0.0, retValue;

        // TODO:To parse significand and exponent from strSequence.
        String[] strArr = strSequence.split("e");
        String[] numPart = strArr[0].split("\\.");

        int numPart1 = numPart[0].charAt(0) - '0';

        int i = 0, count = 0;
        double number = 0;
        while(i < numPart[1].length()){
            number *= 10;
            number += (numPart[1].charAt(i++) - '0');
            count++;
        }

        int powerRes = 1;
        for (int power = count; power != 0; power--) {
            powerRes = powerRes * 10;
        }
//        significand = number + numPart1 * Math.pow(10, count);
        significand = number + numPart1 * powerRes;

        for (int j = 0; j < strArr[1].length(); j++) {
            char digit2 = strArr[1].charAt(j);
            exponent = digit2 - '0' - count;
        }

        retValue = significand * Math.pow(10, exponent);
        return retValue;
    }
}
