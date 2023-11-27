package controller;

public class Search {
    /**
     * Search Function (Public)
     * @param keyword: the keyword of search
     * @param type:    the type of search
     * @return the result of search
     */
    public static String[][] search(String keyword, String type) {
        return model.SimpleDatabase.search(keyword, type);
    }


    /**
     * Search By Time Function (Public)
     * @param inputTime: the dateTime of search
     * @param type: the type of search
     * @return: the result of search
     */
    public static String[][] searchByTime(String inputTime, String type) {
        return model.SimpleDatabase.searchByTime(inputTime, type);
    }

    /**
     * Search with Logical Connectors Function (Public)
     * @param expression: the expression of search
     * @param type: the type of search
     * @return: the result of search
     */
    public static  String [][] searchWithLogicalConnectors(String expression, String type){
        return model.SimpleDatabase.searchWithLogicalConnectors(expression, type);
    }



}
