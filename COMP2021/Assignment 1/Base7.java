// JIANG Guanlin 21093962d

package hk.edu.polyu.comp.comp2022.assignment1;

public class Base7 {

    public static String convertToBase7(int num) {
        String base7NumStr = "";
        int base7Num = 0;
        int temp = 1;
        if (num == 0) {
            base7NumStr = "0";
        }else {
            while (num != 0) {
                int remainder = num % 7;
                num = num / 7;
                base7Num += remainder * temp;
                temp = temp * 10;
                base7NumStr = base7Num + "";
            }
        }
        
        return base7NumStr;
    }
}
