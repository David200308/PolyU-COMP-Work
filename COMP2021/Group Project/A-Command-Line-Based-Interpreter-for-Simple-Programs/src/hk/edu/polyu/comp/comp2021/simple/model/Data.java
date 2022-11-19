package hk.edu.polyu.comp.comp2021.simple.model;

import java.util.*;

/**
 * Parser that receives the user input and processes it in simple language
 */
public class Data {
    /**
     * Stores Labels and its commands (Label - Command)
     */
    private static final Map<String, String> labelCMDMap = new HashMap<>();
    /**
     * Stores expRefs and its commands (Label - Command)
     */
    private static final Map<String, String> expRefLabelCmd = new HashMap<>();
    /**
     * Stores Variables and Values (Variable - Value)
     */
    private static final Map<String, Object> varMap = new HashMap<>();
    /**
     * Stores Results of Expressions (Label - Result)
     */
    private static final Map<String, Object> resultExp = new HashMap<>();
    /**
     * Stores the programName and the label of command
     */
    private static final Map<String, String> programMap = new HashMap<>();
    
     /**
     * Stores the programName and the debug command list
     */
    private static final Map<String, ArrayList<String>> debugger = new HashMap<String, ArrayList<String>>();

    /**
     * @return labelCMDMap HashMap
     */
    public static Map<String, String> getLabelCMDMap() {
        return labelCMDMap;
    }

    /**
     * @return expRefLabelCmd HashMap
     */
    public static Map<String, String> getExpRefLabelCmd() {
        return expRefLabelCmd;
    }

    /**
     * @return varMap HashMap
     */
    public static Map<String, Object> getVarMap() {
        return varMap;
    }

    /**
     * @return resultExp HashMap
     */
    public static Map<String, Object> getResultExp() {
        return resultExp;
    }

    /**
     * @return programMap HashMap
     */
    public static Map<String, String> getProgramMap() {
        return programMap;
    }

    /**
     * getDebugger function will get the debugger HashMap
     * @return debugger
     */
    public static Map<String, ArrayList<String>> getDebugger() {
        return debugger;
    }


    /**
     * storeCommand will be classifying the command input by user and store to the right HashMap
     * @param command: the commands that user input
     * @throws Exception: the exception to handle the input error
     */
    public static void storeCommand(String command) throws Exception {

        // Check if instruction is valid first
        String[] splitStr = command.split(" ");  // Split instruction into words
        
        if (checkLength(splitStr[0], splitStr.length)){

            if (splitStr[0].equals("vardef")){

                if (validVarName(splitStr[3])){
                    labelCMDMap.put(splitStr[1], command);
                    varMap.put(splitStr[3], splitStr[4]);
                }
            }
            else if (splitStr[0].equals("binexpr") || splitStr[0].equals("unexpr")){

                expRefLabelCmd.put(splitStr[1], command);
            }
            else if (splitStr[0].equals("block")){
                // String[] instructions = Arrays.copyOfRange(splitStr, 2, splitStr.length);
                labelCMDMap.put(splitStr[1], command);
            }
            else if (splitStr[0].equals("program") || splitStr[0].equals("execute") || splitStr[0].equals("list") || splitStr[0].equals("store") || splitStr[0].equals("load")) {
                Parser.classification(command, "");
            }
            else if (splitStr[0].equals("debug") || splitStr[0].equals("togglebreakpoint")){
                if (!debugger.containsKey(splitStr[1])) debugger.put(splitStr[1], new ArrayList<String>());
                Parser.classification(command, splitStr[1]);
            }
            else {
                labelCMDMap.put(splitStr[1], command);
            }

        } else {
            throw new Exception();
        }
       

    }

    /**
     * checkLength function will be checking the command length is right or not
     * @param command: the command input by user
     * @param n: the length of the command
     * @return the length is right or not
     */
    private static boolean checkLength(String command, int n){

        if (n == 1) return false;

        // 5 letter instructions
        if (command.equals("vardef") || command.equals("binexpr") || command.equals("if")){if (n != 5) return false;}
        // 4 letter instructions
        else if (command.equals("unexpr") || command.equals("assign") || command.equals("while")){if (n != 4) return false;}
        // 3 letter instructions
        else if (command.equals("print") || command.equals("program") || command.equals("store") || command.equals("load") || command.equals("inspect") || command.equals("togglebreakpoint"))
        {if (n != 3) return false;}
        // 2 letter instructions
        else if (command.equals("skip") || command.equals("execute") || command.equals("list") || command.equals("debug")){if (n != 2) return false;}
        // Block case
        else if (command.equals("block")){if (n <= 2) return false;}


        return true;

    }

    /**
     * validVarName function checks if the variable name is right or not
     * @param varName: the variable name
     * @return boolean
     */
    private static boolean validVarName(String varName) {

        List<String> forbidden = Arrays.asList("vardef", "unexpr", "binexpr", "assign", "print", 
        "skip", "block", "if", "while", "program", "execute", "list", "store", "load",
        "debug", "quit", "togglebreakpoint", "inspect", "instrument", "int", "bool", 
        "true", "false");

        if (Character.isDigit(varName.charAt(0))) return false;    // Check first character

        if (varName.length() > 8) return false; // Check length

        if (forbidden.contains(varName)) return false;  

        for (int i = 0; i < varName.length(); i++) {
            if (Character.isDigit(varName.charAt(i)) || Character.isLetter(varName.charAt(i))){}   
            else return false;
        }


        return true;

    }

    /**
     * addResultExp function is put the result and label to resultExp this HashMap
     * @param label: the label of expression statement
     * @param object: the result of expression
     */
    public static void addResultExp(String label, Object object){resultExp.put(label, object);}
    /**
     * addDebugger function is put the program name and list of commands to HashMap
     * @param programName: the program name
     * @param a: the list transfer to debugger HashMap
     */
    public static void addDebugger(String programName, ArrayList<String> a){debugger.put(programName, a);}
    /**
     * addProgramMap function is put the program name and command to HashMap
     * @param programName: the program name
     * @param instruction: the command
     */
    public static void addProgramMap(String programName, String instruction){programMap.put(programName, instruction);}
    /**
     * addVarMap function is put the variable name and result to varMap this HashMap
     * @param label: the label of expression statement
     * @param object: the result
     */
    public static void addVarMap(String label, Object object){varMap.put(label, object);}

}
