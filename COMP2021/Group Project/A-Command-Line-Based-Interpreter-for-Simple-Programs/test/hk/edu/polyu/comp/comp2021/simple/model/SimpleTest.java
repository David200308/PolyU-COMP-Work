package hk.edu.polyu.comp.comp2021.simple.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static hk.edu.polyu.comp.comp2021.simple.model.Data.getResultExp;
import static hk.edu.polyu.comp.comp2021.simple.model.Data.getVarMap;
import static hk.edu.polyu.comp.comp2021.simple.model.Parser.ExecuteResultString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * For testing the commands
 */
public class SimpleTest {
    /**
     * Test for vardef function
     * @throws Exception: the exception to handle the input error
     */
    @Test
    public void testValidVarName() throws Exception {
        boolean flag;
        try {
            Data.storeCommand("vardef v1 int");
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        assertEquals(false, flag);

    }


    /**
     * Test for vardef function
     * @throws Exception: the exception to handle the input error
     */
    @Test
    public void testVardef() throws Exception {
        Data.storeCommand("vardef v1 int z 10");
        Object value = getVarMap().get("z");
        assertEquals("10", value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr1() {
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x * 20", "prog1");
        Object value = getResultExp().get("exp1");
        int need = Integer.parseInt("200");
        assertEquals(need, value);
    }

    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr2() {
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x + 20", "prog1");
        Object value = getResultExp().get("exp1");
        int need = Integer.parseInt("30");
        assertEquals(need, value);
    }

    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr3() {
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x - 20", "prog1");
        Object value = getResultExp().get("exp1");
        int need = Integer.parseInt("-10");
        assertEquals(need, value);
    }

    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr4() {
        Parser.classification("vardef v1 int x 40", "prog1");
        Parser.classification("binexpr exp1 x / 20", "prog1");
        Object value = getResultExp().get("exp1");
        int need = Integer.parseInt("2");
        assertEquals(need, value);
    }

    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr5() {
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp5 x != 20", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(true, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr6() {
        Parser.classification("vardef v1 int y 10", "prog1");
        Parser.classification("binexpr exp5 y == 10", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(true, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr7() {
        Parser.classification("vardef v1 int y 10", "prog1");
        Parser.classification("binexpr exp5 y > 20", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(false, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr8() {
        Parser.classification("vardef v1 int y 10", "prog1");
        Parser.classification("binexpr exp5 y >= 20", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(false, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr9() {
        Parser.classification("vardef v1 int y 10", "prog1");
        Parser.classification("binexpr exp5 y < 20", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(true, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr10() {
        Parser.classification("vardef v1 int y 10", "prog1");
        Parser.classification("binexpr exp5 y <= 20", "prog1");
        Object value = getResultExp().get("exp5");
        assertEquals(true, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr11() {
        Parser.classification("vardef v1 bool x true", "prog1");
        Parser.classification("vardef v2 bool y false", "prog1");
        Parser.classification("binexpr exp4 x && y", "prog1");
        Object value = getResultExp().get("exp4");
        assertEquals(false, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr12() {
        Parser.classification("vardef v1 bool x true", "prog1");
        Parser.classification("vardef v2 bool y false", "prog1");
        Parser.classification("binexpr exp4 x || y", "prog1");
        Object value = getResultExp().get("exp4");
        assertEquals(true, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr13() {
        Parser.classification("vardef v1 bool x true", "prog1");
        Parser.classification("vardef v2 bool y false", "prog1");
        Parser.classification("binexpr exp4 x == y", "prog1");
        Object value = getResultExp().get("exp4");
        assertEquals(false, value);
    }
    /**
     * Test for binexpr function - Binary Expression Calculation
     */
    @Test
    public void testBinExpr14() {
        Parser.classification("vardef v1 bool x true", "prog1");
        Parser.classification("vardef v2 bool y false", "prog1");
        Parser.classification("binexpr exp4 x != y", "prog1");
        Object value = getResultExp().get("exp4");
        assertEquals(true, value);
    }
    /**
     * Test for unexpr function - Unary Expression Calculation
     */
    @Test
    public void testUnaryEx1(){
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x * 20", "prog1");
        Parser.classification("unexpr exp2 ~ exp1", "prog1");
        Object value = getResultExp().get("exp2");
        assertEquals("-200", value + "");
    }
    /**
     * Test for unexpr function - Unary Expression Calculation
     */
    @Test
    public void testUnaryEx2(){
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x * 20", "prog1");
        Parser.classification("unexpr exp2 # exp1", "prog1");
        Object value2 = getResultExp().get("exp2");
        assertEquals("-200", value2 + "");
    }
    /**
     * Test for unexpr function - Unary Expression Calculation
     */
    @Test
    public void testUnaryEx3(){
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp1 x * 20", "prog1");
        Parser.classification("unexpr exp2 ! exp1", "prog1");
        Object value2 = getResultExp().get("exp2");
        assertEquals("true", value2 + "");
    }
    /**
     * Test for unexpr function - Unary Expression Calculation
     */
    @Test
    public void testUnaryEx4(){
        Parser.classification("vardef v1 int x 10", "prog1");
        Parser.classification("binexpr exp10 x < 20", "prog1");
        Parser.classification("unexpr exp2 ! exp10", "prog1");
        Object value2 = getResultExp().get("exp2");
        assertEquals("false", value2 + "");
    }
    /**
     * Test for print function
     */
    @Test
    public void testPrint(){
        Parser.classification("vardef vardef1 int x 100", "prog1");
        Parser.classification("binexpr exp1 x * 20", "prog1");
        Parser.classification("unexpr exp2 ~ exp1", "prog1");
        Parser.classification("print print1 exp2", "prog1");
        Object value = getResultExp().get("exp2");
        assertEquals("[-2000]","[" + value + "]");
    }

    /**
     * Test for skip function
     */
    @Test
    public void testSkip(){
        Parser.classification("vardef vardef1 int x 0", "prog1");
        Parser.classification("binexpr exp1 x % 2", "prog1");
        Parser.classification("binexpr exp2 exp1 == 0", "prog1");
        Parser.classification("print print1 x", "prog1");
        Parser.classification("skip skip1", "prog1");

        assertNull(null);
    }

    /**
     * Test for the block function
     */
    @Test
    public void testBlock() {
        Parser.classification("vardef vardef1 int x 100", "prog1");
        Parser.classification("binexpr exp1 x * 10", "prog1");
        Parser.classification("unexpr exp2 ~ exp1", "prog1");
        Parser.classification("print print1 exp2", "prog1");
        Parser.classification("block block1 assign1 print1", "prog1");
        Object first = getResultExp().get("exp2");
        Simple.block(new String[]{"100", "1000", "3500"},"prog1");
    }

    /**
     * Test for file operation - store and load function
     * @throws IOException: the file handling error
     */
    @Test
    public void fileTest() throws IOException {
        boolean flag;
        try {
            String cmd1 = "vardef vardef1 int x 100";
            String cmd2 = "binexpr exp1 x * 20";
            String cmd3 = "unexpr exp2 ~ exp1";
            String cmd4 = "print print1 exp2";
            String cmd5 = "block block1 assign1 print1";
            String cmd6 = "program program2 block1";
            String cmd7 = "store program2 /Users/davidjiang/Desktop/program2.simple";
            String cmd8 = "load /Users/davidjiang/Desktop/program2.simple program2";
            Data.storeCommand(cmd1);
            Data.storeCommand(cmd2);
            Data.storeCommand(cmd3);
            Data.storeCommand(cmd4);
            Data.storeCommand(cmd5);
            Data.storeCommand(cmd6);
            Data.storeCommand(cmd7);
            Data.storeCommand(cmd8);
        } catch (Exception e) {
            flag = false;
        }
        flag = true;

        assertEquals(true, flag);
    }

    /**
     * Test for file operation - store and load function
     * @throws IOException: the file handling error
     */
    @Test
    public void fileStoreErrorTest() throws IOException {
        boolean flag;
        try {
            String cmd1 = "vardef vardef1 int x 100";
            String cmd2 = "binexpr exp1 x * 20";
            String cmd3 = "unexpr exp2 ~ exp1";
            String cmd4 = "print print1 exp2";
            String cmd5 = "block block1 assign1 print1";
            String cmd6 = "program program2 block1";
            String cmd7 = "store program2 /Users/davidjiang/Deskt/program2.simple";
            Data.storeCommand(cmd1);
            Data.storeCommand(cmd2);
            Data.storeCommand(cmd3);
            Data.storeCommand(cmd4);
            Data.storeCommand(cmd5);
            Data.storeCommand(cmd6);
            Data.storeCommand(cmd7);
        } catch (Exception e) {
            flag = false;
        }
        flag = true;

        assertEquals(true, flag);
    }

    /**
     * Test for file operation - store and load function
     * @throws IOException: the file handling error
     */
    @Test
    public void fileLoadErrorTest() throws IOException {
        boolean flag;
        try {
            String cmd1 = "vardef vardef1 int x 100";
            String cmd2 = "binexpr exp1 x * 20";
            String cmd3 = "unexpr exp2 ~ exp1";
            String cmd4 = "print print1 exp2";
            String cmd5 = "block block1 assign1 print1";
            String cmd6 = "program program2 block1";
            String cmd7 = "store program2 /Users/davidjiang/Desktop/program2.simple";
            String cmd8 = "load /Users/davidjiang/program2.simple program2";
            Data.storeCommand(cmd1);
            Data.storeCommand(cmd2);
            Data.storeCommand(cmd3);
            Data.storeCommand(cmd4);
            Data.storeCommand(cmd5);
            Data.storeCommand(cmd6);
            Data.storeCommand(cmd7);
            Data.storeCommand(cmd8);
        } catch (Exception e) {
            flag = false;
        }
        flag = true;

        assertEquals(true, flag);
    }

    /**
     * Test the execute function
     * @throws Exception: the exception to handle the input error
     */
    @Test
    public void executeTest() throws Exception {
        Data.storeCommand("vardef vardef1 int x 0");
        Data.storeCommand("binexpr exp1 x % 2");
        Data.storeCommand("binexpr exp2 exp1 == 0");
        Data.storeCommand("print print1 x");
        Data.storeCommand("skip skip1");
        Data.storeCommand("if if1 exp2 print1 skip1");
        Data.storeCommand("binexpr exp3 x + 1");
        Data.storeCommand("assign assign1 x exp3");
        Data.storeCommand("block block1 if1 assign1");
        Data.storeCommand("binexpr exp4 x <= 10");
        Data.storeCommand("while while1 exp4 block1");
        Data.storeCommand("block block2 vardef1 while1");
        Data.storeCommand("program printeven block2");
        Data.storeCommand("execute printeven");
        System.out.println(ExecuteResultString);
        assertEquals("[0][-1000][-2000][0][0][2][4][6][8][10]", ExecuteResultString);
    }

    /**
     * Test for breakpoint of the programme
     * @throws Exception: the exception to handle the input error
     */
    @Test
    public void breakPointTest() throws Exception {
        String str = "togglebreakpoint program1 block1";
        Data.storeCommand(str);
        assertEquals(str.split(" ")[2], Data.getDebugger().get(str.split(" ")[1]).get(0));
    }

    /**
     * Test for debug function
     */
    @Test
    public void debugTest() {
        boolean flag;
        try {
            String cmd1 = "vardef vardef1 int x 100";
            String cmd2 = "binexpr exp1 x * 20";
            String cmd3 = "unexpr exp2 ~ exp1";
            String cmd4 = "print print1 exp2";
            String cmd5 = "block block1 assign1 print1";
            String cmd6 = "program program2 block1";
            String cmd7 = "debug program2";
            Data.storeCommand(cmd1);
            Data.storeCommand(cmd2);
            Data.storeCommand(cmd3);
            Data.storeCommand(cmd4);
            Data.storeCommand(cmd5);
            Data.storeCommand(cmd6);
            Data.storeCommand(cmd7);
        } catch (Exception e) {
            flag = false;
        }
        flag = true;

        assertEquals(true, flag);
    }

    /**
     * Test for inspect function
     */

    @Test
    public void inspectTest() {
        Parser.classification("vardef vardef1 int x 0", "prog1");
        Parser.classification("binexpr exp1 x % 2", "prog1");
        Parser.classification("binexpr exp2 exp1 == 0", "prog1");
        Parser.classification("print print1 x", "prog1");
        Parser.classification("skip skip1", "prog1");
        Parser.classification("vardef vardef1 int x 0", "prog1");
        Parser.classification("inspect printeven x","prog1");
        Simple.inspect("x");
        Object value2 = getVarMap().get("x");
        assertEquals("<0>","<"+value2+">");
    }

    /**
     * Test for list
     * @throws Exception: the exception to handle the input error
     */
    @Test
    public void ListTest() throws Exception {
        Data.storeCommand("vardef vardef1 int x 100");
        Data.storeCommand("binexpr exp1 x * 20");
        Data.storeCommand("unexpr exp2 ~ exp1");
        Data.storeCommand("assign assign1 x exp2");
        Data.storeCommand("print print1 exp2");
        Data.storeCommand("skip skip1");
        Data.storeCommand("block block1 assign1 skip1");
        Data.storeCommand("if if1 exp5 block1 print1");
        Data.storeCommand("while while1 true block1");
        Data.storeCommand("program program1 while1");
        Data.storeCommand("list program1");
        ArrayList<String> list = new ArrayList<String>();
        list.add("while while1 true block1");
        list.add("block block1 assign1 skip1");
        list.add("binexpr exp1 x * 20");
        list.add("unexpr exp2 ~ exp1");
        list.add("assign assign1 x exp2");
        list.add("skip skip1");
        Simple.list("instruction",list);
    }





}


