package hk.edu.polyu.comp.comp2021.simple;

import hk.edu.polyu.comp.comp2021.simple.model.Parser;

/**
 * Application for Initial the program
 */
public class Application {
    /**
     * Initialize the whole program
     * @param args Argument for main function
     * @throws Exception: the exception to handle the input error
     */
    
    public static void main(String[] args) throws Exception {

        Parser input = new Parser();
        input.inputCMD();

    }

}
