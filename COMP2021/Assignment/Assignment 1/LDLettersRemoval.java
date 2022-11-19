// JIANG Guanlin 21093962d

package hk.edu.polyu.comp.comp2022.assignment1;

public class LDLettersRemoval {

    public static String removeLDLetters(String str){
        String strLettters = "";

        for (int i = 0; i < str.length(); i++) {
            boolean flag = false;
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    flag = true;
                }
            }
            if (flag == false) {
                strLettters += str.charAt(i);
            }
        }

        return strLettters;
    }
}
