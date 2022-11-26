package controller.database;
import java.sql.*;
import oracle.jdbc.driver.*;

public class DataBase {
    private static final DataBase dataBase;
    static {
        try {
            dataBase = new DataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static OracleConnection connection;
    private static final String username = "\"21098431d\"";

        /** The password below is intentionally designed for the LMS Project Database connection,
        it will be expired after the overall assessment grade is released and finalised. */
    private static final String pwd = "getPassword";
    private static final String url = "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms";

    /**
     * Private constructor for Singleton pattern
     * Ensure that only one instance of DataBase can be created
     * Meaning only one database connection can be applied at a time
     */
    private DataBase() throws SQLException {
        try{
            initializeConnection();
        }
        catch(SQLException e){
            System.out.println("Connection Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get the only instance of the database
     * @return the only instance of the database
     */
    public static DataBase getDataBase() {
        return dataBase;
    }

    /**
     * Reconnect to the database
     */
    public void reConnect() {
        try {
            connection.close();
            initializeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * initialize the connection to the database
     */
    private void initializeConnection() throws SQLException {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            connection = (OracleConnection)DriverManager.getConnection(url,username,pwd);
            //System.out.println("DB Connected!");
        }
        catch (SQLException e) {
            System.out.println("DB Connection Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * close the connection
     */
    public void closeConnection() throws SQLException {
        try{
            connection.close();
            System.out.println("DB Connection Closed!");
        }
        catch (SQLException e) {
            System.out.println("Close Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * query the database and get the ResultSet
     * @param inSql: the sql statement
     * @return: the result set
     */
    public ResultSet query(String inSql) throws SQLException {
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(inSql);
        }
        catch (SQLException e) {
            System.out.println("Query Failed!");
            e.printStackTrace();
            throw e;
        }
        return resultSet;
    }

    /**
     * check whether inObject is in the inTable's inAttr
     * @param inTable: the table to be checked
     * @param inAttr: the attribute to be checked
     * @param inObject: the object to be checked
     * @return: true if inObject is in the inTable's inAttr
     */
    public boolean contains(String inTable, String inAttr, String inObject) throws SQLException {
        String sql = "SELECT * FROM " + inTable + " WHERE " + inAttr + " = \'" + inObject+"\'";
        try{
            ResultSet resultSet = query(sql);
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Query Failed!");
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    /**
     * Overload the contains method
     */
    public boolean contains(String inTable, String inAttr, int inObject) throws SQLException {
        String sql = "SELECT * FROM " + inTable + " WHERE " + inAttr + " = " + inObject;
        try{
            ResultSet resultSet = query(sql);
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Query Failed!");
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    /**
     * check whether inObjects are in the inTable's inAttrs
     * @param inTable: the table to be checked
     * @param inAttr1: the attribute to be checked
     * @param inObject1: the object to be checked
     * @param inAttr2: the attribute to be checked
     * @param inObject2: the object to be checked
     * @return: true if inObjects are in the inTable's inAttrs
     */
    public boolean contains(String inTable, String inAttr1, String inAttr2, String inObject1, String inObject2) throws SQLException {
        String sql = "SELECT * FROM " + inTable + " WHERE " + inAttr1 + " = \'" + inObject1 + "\' AND " + inAttr2 + " = " + inObject2;
        try{
            ResultSet resultSet = query(sql);
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Query Failed!");
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    /**
     * update the database
     * @param inSql: the sql statement
     */
    public void update(String inSql) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(inSql);
        }
        catch (SQLException e) {
            System.out.println("Update Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Delete the data in the table
     * @param inSql: the sql statement
     */
    public void delete(String inSql) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(inSql);
        }
        catch (SQLException e) {
            System.out.println("Delete Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * insert the data into the table
     * @param inSql: the sql statement
     */
    public void insert(String inSql) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(inSql);
        }
        catch (SQLException e) {
            System.out.println("Insert Failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * commit the changes
     */
    public void commit() throws SQLException {
        try {
            connection.commit();
        }
        catch (SQLException e) {
            System.out.println("Commit Failed!");
            e.printStackTrace();
            throw e;
        }
    }
}
