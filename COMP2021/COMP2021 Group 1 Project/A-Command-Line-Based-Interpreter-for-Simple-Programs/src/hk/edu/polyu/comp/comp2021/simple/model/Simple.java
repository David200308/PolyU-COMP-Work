package hk.edu.polyu.comp.comp2021.simple.model;

import java.util.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Simple class contains the commands and basic rules of Simple Language
 */
public class Simple extends Parser {
    /**
     * The max value of int in sample lang
     */
    final static int maxInt = 99999;
    /**
     * The min value of int in sample lang
     */
    final static int minInt = -99999;

    /**
     * Vardef command for set the new variable in integer value or boolean value format.
     * Usage: vardef vardef1 int x 100
     * @param str: the command array
     */
    protected static void vardef(String[] str) {   //* REQ1 - Works
        Data.addVarMap(str[3], expRef(str[4]));
        updateExp();
    }


    /**
     * BinExpr command for calculate the binary expression equation
     * Usage: binexpr exp4 x <= 10
     * @param str: the command array
     */
    protected static void binExpr(String[] str) { //* REQ2

        Object a = expRef(str[2]);
        Object b = expRef(str[4]);
        String operator = str[3];
        String label = str[1];

        if (a.equals(true) || a.equals(false) || b.equals(true) || b.equals(false)) evaluateBoolExp(Boolean.parseBoolean(a.toString()), Boolean.parseBoolean(b.toString()), operator, label);
        else evaluateIntExp(Integer.parseInt(a.toString()), Integer.parseInt(b.toString()), operator, label);

        // If integer
        // if (a instanceof Integer && b instanceof Integer) evaluateIntExp((int)a, (int)b, operator, label);

        // If boolean
        // else if (a instanceof Boolean && b instanceof Boolean) evaluateBoolExp((boolean)a, (boolean)b, operator, label);

    }

    /**
     * For int format variable to calculate in the binary expression
     * @param a: the first expression reference
     * @param b: the second expression reference
     * @param operator: +, -, *, /, >, >=, <, <=, ==, !=
     * @param label: the label of the command
     */
    protected static void evaluateIntExp(int a, int b, String operator, String label){   //* Req 2.1
        /*
         * Evaluate int expressions and adds int result
         */

        switch (operator){
            case "+":
                if (a + b > maxInt) Data.addResultExp(label, maxInt);
                else if (a + b < minInt) Data.addResultExp(label, minInt);
                else Data.addResultExp(label, a + b);
                break;
            case "-":
                Data.addResultExp(label, a - b);
                if (a - b > maxInt) Data.addResultExp(label, maxInt);
                else if (a - b < minInt) Data.addResultExp(label, minInt);
                else Data.addResultExp(label, a - b);
                break;
            case "*":
                if (a * b > maxInt) Data.addResultExp(label, maxInt);
                else if (a * b < minInt) Data.addResultExp(label, minInt);
                else Data.addResultExp(label, a * b);
                break;
            case "/":
                if (b != 0){
                    if (a / b > maxInt) Data.addResultExp(label, maxInt);
                    else if (a / b < minInt) Data.addResultExp(label, minInt);
                    else Data.addResultExp(label, a / b);
                }
                break;
            case ">":
                Data.addResultExp(label, a > b);
                break;
            case "<":
                Data.addResultExp(label, a < b);
                break;
            case ">=":
                Data.addResultExp(label, a >= b);
                break;
            case "<=":
                Data.addResultExp(label, a <= b);
                break;
            case "%":
                Data.addResultExp(label, a % b);
                break;
            case "==":
                Data.addResultExp(label, a == b);
                break;
            case "!=":
                Data.addResultExp(label, a != b);
                break;
        }

    }

    /**
     * For boolean format variable to calculate in the binary expression
     * @param a: the first statement
     * @param b: the second statement
     * @param operator: && or || or == or !=
     * @param label: the expression label
     */
    protected static void evaluateBoolExp(Boolean a, Boolean b, String operator, String label){ //* Req 2.2

        // Evaluates Boolean expression. 

        switch (operator){
            case "&&":
                Data.addResultExp(label, a && b);
                break;
            case "||":
                Data.addResultExp(label, a || b);
                break;
            case "==":
                Data.addResultExp(label, a == b);
                break;
            case "!=":
                Data.addResultExp(label, a != b);
                break;
        }
    }

    /**
     * Unexpr command for calculate the unary expression equation
     * @param expName: the expression name
     * @param operator: # or ~ or !
     * @param expRef1: the expression reference result
     */
    protected static void unexpr(String expName, String operator, String expRef1) { //* REQ3


        if (operator.equals("!")) {   // Negates Boolean expression
            if (Boolean.parseBoolean(expRef(expRef1).toString())) {
                Data.addResultExp(expName, false);
            } else {
                Data.addResultExp(expName, true);
            }
        } else if (operator.equals("~")) {  // Negates Int Expression by switching symbols
            int number = Integer.parseInt(expRef(expRef1).toString()) * -1;
            Data.addResultExp(expName, number);
        }
    }


    /**
     * Assign command is update a new value or replace a new value to an exist variable name
     * @param varName: the variable name
     * @param expRef: the expression reference result
     */
    protected static void assign(String varName, String expRef) {  //* REQ4

        // Get object in variable
        Object toAdd = expRef(expRef);
        
        // Change variable value
        Data.getVarMap().replace(varName, toAdd);
        updateExp();

    }

    /**
     * UpdateExp function is push the unexpr and binexpr commands to those command processors
     */
    protected static void updateExp(){
        // Update stored statements that contain this variable
        String[] command;
        for (String key: Data.getResultExp().keySet()){
            
            command = new String[]{};
            if (Data.getExpRefLabelCmd().containsKey(key)){
                
                command = Data.getExpRefLabelCmd().get(key).split(" ");

                if (command[0].equals("unexpr")) unexpr(command[1], command[2], command[3]);
                else if (command[0].equals("binexpr")) binExpr(command);
            }
        }
    }

    /**
     * print command will be let function printout result when execute
     * @param label: the command label
     * @param expRef: the reference of the expression
     */
    protected static void print(String label, String expRef) {   //* REQ5
        String value = "";
        value = value + expRef(expRef).toString();
        ExecuteResultString += "[" + value +"]";
        System.out.print("[" + value +"]");
        Data.addResultExp(label, '[' + value +']');
    }

    /**
     * skip function
     */
    protected static void skip(){return;}   //* REQ6

    /**
     * Block command will be set a block to include some statement together
     * @param instructions: those commands
     * @param programName: the program name
     */
    protected static void block(String[] instructions, String programName){  //* REQ7

        int n = instructions.length;
        for (int i = 0; i < n; i++){

            updateExp();
            if (Data.getLabelCMDMap().containsKey(instructions[i])){
                classification(Data.getLabelCMDMap().get(instructions[i]), programName);
            }
            else if (Data.getExpRefLabelCmd().containsKey(instructions[i])){
                classification(Data.getLabelCMDMap().get(instructions[i]), programName);
            }
        }

    }

    /**
     * If command will be created a Judge statement for those statements
     * @param expRef: the expression reference
     * @param statementLab1: the first label of statement
     * @param statementLab2: the second label of statement
     * @param programName: the program name
     */
    protected static void ifF(String expRef, String statementLab1, String statementLab2, String programName) {    //* REQ

        // Check if the condition is TRUE or FALSE:
        // if true - <K,V> save V for label ex1 in labelCMDMap
        // if false - <K,V> save V for label ex2 in labelCMDMap

        if ((boolean)expRef(expRef)){
            classification(Data.getLabelCMDMap().get(statementLab1), programName);
        }
        else {
            classification(Data.getLabelCMDMap().get(statementLab2), programName);
        }

    }

    /**
     * While command will be created a loop for that statement
     * @param expRef: the expression reference (true or false for that result)
     * @param statementLab1: the label for the statement want to include to while
     * @param programName: the program name
     */
    protected static void whileW(String expRef, String statementLab1, String programName) {   //* REQ9

        while((boolean)expRef(expRef)) {
            classification(Data.getLabelCMDMap().get(statementLab1), programName);
        }
    }

    /**
     * Program command will be set the program
     * @param programName: the program name
     * @param statementLabel: the label for the statement want to include to program
     */
    protected static void program(String programName, String statementLabel) {  //* REQ10
        Data.addDebugger(programName, new ArrayList<String>());
        Data.addProgramMap(programName, statementLabel);
    }

    /**
     * Execute will be run the whole program
     * @param programName: the program name
     */
    protected static void execute(String programName){  //* REQ11

        declare(programName);

        classification(Data.getLabelCMDMap().get(Data.getProgramMap().get(programName)), "");
    }

    /**
     * @param programName: the program name
     */
    private static void declare(String programName){

        ArrayList<String> instructions = new ArrayList<String>();
        internList(Data.getProgramMap().get(programName), instructions);
        ArrayList<String> declare = getDeclare(instructions);

        for (int i = 0; i < declare.size(); i++){
            if (Data.getLabelCMDMap().containsKey(declare.get(i))) classification(Data.getLabelCMDMap().get(declare.get(i)), programName);
            else if (Data.getExpRefLabelCmd().containsKey(declare.get(i))) classification(Data.getExpRefLabelCmd().get(declare.get(i)), programName);

        }


    }

    /**
     * Get instruction from the map
     * @param instruction: the command
     * @param added: the list of added function
     */
    private static void internList(String instruction, ArrayList<String> added){

        // Get instruction from the map
        
        String[] fullInst = null;
        if (Data.getLabelCMDMap().containsKey(instruction)) fullInst = Data.getLabelCMDMap().get(instruction).split(" ");
        else if (Data.getExpRefLabelCmd().containsKey(instruction)) fullInst = Data.getExpRefLabelCmd().get(instruction).split(" ");

        if (fullInst != null) {
            if (!added.contains(instruction)) {

                if (fullInst[0].equals("block")) {  // If program statement is a block

                    String[] block = Arrays.copyOfRange(fullInst, 2, fullInst.length);

                    if (!added.contains(instruction)) added.add(instruction);

                    for (int i = 0; i < block.length; i++) internList(block[i], added); // Recurse over the instructions

                } else if (fullInst[0].equals("while")) {    // If while loop
                    if (!added.contains(instruction)) added.add(instruction);
                    internList(fullInst[2], added);
                    internList(fullInst[3], added);
                } else if (fullInst[0].equals("if")) {
                    if (!added.contains(instruction)) added.add(instruction);
                    internList(fullInst[2], added);
                    internList(fullInst[3], added);
                    internList(fullInst[4], added);
                }

                // Print if instruction is not a while or block or if
                else {

                    // Check expresions in these declarations:
                    if (fullInst[0].equals("vardef")) {
                        if (Data.getExpRefLabelCmd().containsKey(fullInst[4])) internList(fullInst[4], added);
                    } else if (fullInst[0].equals("unexpr") || fullInst[0].equals("assign")) {
                        if (Data.getExpRefLabelCmd().containsKey(fullInst[3])) internList(fullInst[3], added);
                    } else if (fullInst[0].equals("print")) {
                        if (Data.getExpRefLabelCmd().containsKey(fullInst[2])) internList(fullInst[2], added);
                    } else if (fullInst[0].equals("binexpr")) {
                        if (Data.getExpRefLabelCmd().containsKey(fullInst[2])) internList(fullInst[2], added);
                        if (Data.getExpRefLabelCmd().containsKey(fullInst[4])) internList(fullInst[4], added);
                    }

                    if (!added.contains(instruction)) {
                        added.add(instruction);
                    }
                }
            }
        }

    }

    /**
     * A function to get the Declare list
     * @param instructions: the list of commands
     * @return a list of declare
     */
    private static ArrayList<String> getDeclare (ArrayList<String> instructions){

        // Get commands of variables and expression declarations to initialize first

        ArrayList<String> returnList = new ArrayList<String>();
        String cur;

        for (int i = 0; i < instructions.size(); i++){
            cur = instructions.get(i);
            if (Data.getLabelCMDMap().containsKey(cur)) cur = Data.getLabelCMDMap().get(cur);
            else if (Data.getExpRefLabelCmd().containsKey(cur)) cur = Data.getExpRefLabelCmd().get(cur);

            if (cur.split(" ")[0].equals("vardef") || cur.split(" ")[0].equals("unexpr") || cur.split(" ")[0].equals("binexpr"))
                returnList.add(instructions.get(i));
        }

        return returnList;
    }

    /**
     * List command will be list the command of that program
     * @param instruction: the list command
     * @param added: A ArrayList hold the command add to the list
     */
    protected static void list(String instruction, ArrayList<String> added) {    //* REQ12

        internList(instruction, added);
        String cur;
        for (int i = 0; i < added.size(); i++){
            cur = added.get(i);
            if (Data.getLabelCMDMap().containsKey(cur)) System.out.println(Data.getLabelCMDMap().get(cur));
            else if (Data.getExpRefLabelCmd().containsKey(cur)) System.out.println(Data.getExpRefLabelCmd().get(cur));
        }
    }

    /**
     * Store command will be saving the program to the computer
     * @param programName: the program name
     * @param address: the program path that where the user want to save
     */
    public static void store(String programName, String address) {
    
        // Create File
        try {
            java.io.File myObj = new java.io.File(address + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created " + myObj.getName());
            } else {
                System.out.println("Program already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }

        // Write into file
        try {
            
            FileWriter myWriter = new FileWriter(address + ".txt");
            ArrayList<String> programCommands = new ArrayList<String>();
            internList(Data.getProgramMap().get(programName), programCommands);
            String instruction;
            
            for (int i = 0; i < programCommands.size(); i++){
                instruction = programCommands.get(i);

                if (Data.getLabelCMDMap().containsKey(instruction)) myWriter.write(Data.getLabelCMDMap().get(instruction) + "\n");
                else if (Data.getExpRefLabelCmd().containsKey(instruction)) myWriter.write(Data.getExpRefLabelCmd().get(instruction) + "\n");
            }
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Load command will be loading the program to the Interpreter
     * @param fileAddress: the program file path
     * @param programName: the program name
     * @throws IOException: for handle file operation error
     */
    public static void load(String fileAddress, String programName) throws Exception {

        
        BufferedReader in = new BufferedReader(new FileReader(fileAddress + ".txt"));
        String str;

        ArrayList<String> instructions = new ArrayList<String>();
        while((str = in.readLine()) != null){
            instructions.add(str);
        }

        in.close();
        
        Data.addProgramMap(programName, instructions.get(0).split(" ")[1]);
        
        for (String command: instructions) Data.storeCommand(command);

    }

    /**
     * Togglebreakpoint command will be set the breakpoint for debugger in matched program
     * Also input second time can remove the breakpoint
     * @param programName: the program name
     * @param label: the label of statement at that breakpoint statement
     */
    protected static void togglebreakpoint(String programName, String label) {
        
        ArrayList<String> add = Data.getDebugger().get(programName);

        if (add.contains(label)) add.remove(label);
        else add.add(label);

        Data.addDebugger(programName, add);

    }

    /**
     * Debug command is for the program debugging
     * @param programName: the program name
     */
    protected static void debug(String programName) {

        declare(programName);
        classification(Data.getLabelCMDMap().get(Data.getProgramMap().get(programName)), programName);
        System.out.println();
       
    }

    /**
     * waitDebug function is the commands which need to waiting for debug
     * @param programName: the program name
     */
    protected static void waitDebug(String programName){

        Scanner inputLine = Parser.getScanner();
        String input;


        while(inputLine.hasNextLine()){
            input = inputLine.nextLine();
            if (input != null){
                if (input.equals("debug " + programName)) return;
                else if (input.contains("togglebreakpoint " + programName)) togglebreakpoint(programName, input.split(" ")[2]);
                else if (input.contains("inspect " + programName)){
                    
                    String[] inst = input.split(" ");

                    // If instruction valid
                    if (inst.length == 3){

                        ArrayList<String> insts = new ArrayList<String>();
                        internList(Data.getProgramMap().get(programName), insts);
                        boolean inScope = false;
    
                        for (String instruction : insts){
                            if (instruction.contains(inst[2])){inScope = true; break;}
                        }
    
                        if (inScope) inspect(inst[2]);
                    }
                }

            }

        }

        
    }


    /**
     * Inspect command will be printout the variable value that user want to know
     * @param variable: the variable name
     */
    public static void inspect(String variable) {

        if (Data.getVarMap().containsKey(variable)) System.out.println("<" + Data.getVarMap().get(variable) + ">");
    }

}

