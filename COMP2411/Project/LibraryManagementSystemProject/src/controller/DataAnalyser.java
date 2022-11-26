package controller;

import controller.database.DataBase;
import model.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The class DataAnalyser is used to analyse the data from the database.
 */
public class DataAnalyser {
    ModelController modelController = new ModelController();

    public int getTotalBookNumber() throws SQLException {

        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT COUNT(b.bookID) FROM BOOK b";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public int getTotalRentBook() throws SQLException {
        int totalBorrowedNumber = 0;
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT SUM(bookRentNum) FROM BOOK";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                return resultSet.getInt(1);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return totalBorrowedNumber;
    }

    public List<String> getMostRentBookISBN() throws SQLException{
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT bookName, ISBN, author, bookCategory, SUM(bookRentNum) FROM book GROUP BY bookName, ISBN, author, bookCategory HAVING (SUM(bookRentNum) >= ALL(SELECT SUM(B.bookRentNum) FROM book B GROUP BY B.ISBN))";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String category = resultSet.getString(4);
                String num = resultSet.getInt(5)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + "[Category]: " + category + " [Times of Rent]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> getMostRentBookID() throws SQLException {
        List<Book> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT A.bookId" +
                        " FROM book A" +
                        " WHERE A.bookRentNum >= ALL (SELECT bookRentNum" +
                        " FROM book B)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String targetBookId= resultSet.getInt(1)+"";
                Book book = modelController.searchBookOnBookID(targetBookId).get(0);
                result.add(book);
                }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getLeastRentBookISBN() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT bookName, ISBN, author, bookCategory, SUM(bookRentNum) FROM book GROUP BY bookName, ISBN, author, bookCategory HAVING (SUM(bookRentNum) <= ALL(SELECT SUM(B.bookRentNum) FROM book B GROUP BY B.ISBN))";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String category = resultSet.getString(4);
                String num = resultSet.getInt(5)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + "[Category]: " + category + " [Times of Rent]: " + num;
                result.add(out);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> getLeastRentBookID() throws SQLException {
        List<Book> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT A.bookId" +
                        " FROM book A" +
                        " WHERE A.bookRentNum <= ALL (SELECT bookRentNum" +
                        " FROM book B)";

        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String targetBookId= resultSet.getInt(1)+"";
                Book book = modelController.searchBookOnBookID(targetBookId).get(0);
                result.add(book);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getMostWantBookISBN() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT bookName, ISBN, author, bookCategory, SUM(bookWantNum) FROM book GROUP BY bookName, ISBN, author, bookCategory HAVING (SUM(bookWantNum) >= ALL(SELECT SUM(B.bookWantNum) FROM book B GROUP BY B.ISBN))";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String category = resultSet.getString(4);
                String num = resultSet.getInt(5)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + "[Category]: " + category + " [Times of Rent]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> getMostWantBookID() throws SQLException {
        List<Book> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT A.bookId" +
                        " FROM book A" +
                        " WHERE A.bookWantNum >= ALL (SELECT bookWantNum" +
                        " FROM book B)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String targetBookId= resultSet.getInt(1)+"";
                Book book = modelController.searchBookOnBookID(targetBookId).get(0);
                result.add(book);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getLeastWantBookISBN() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT bookName, ISBN, author, bookCategory, SUM(bookWantNum) FROM book GROUP BY bookName, ISBN, author, bookCategory HAVING (SUM(bookWantNum) <= ALL(SELECT SUM(B.bookWantNum) FROM book B GROUP BY B.ISBN))";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String category = resultSet.getString(4);
                String num = resultSet.getInt(5)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + "[Category]: " + category + " [Times of Want]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Book> getLeastWantBookID() throws SQLException {
        List<Book> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT A.bookId" +
                        " FROM book A" +
                        " WHERE A.bookWantNum <= ALL (SELECT bookWantNum" +
                        " FROM book B)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String targetBookId= resultSet.getInt(1)+"";
                Book book = modelController.searchBookOnBookID(targetBookId).get(0);
                result.add(book);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getMostRentBookISBNByCategory() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT N, I, C, S FROM (SELECT B.bookName AS N, B.ISBN AS I, B.bookCategory AS C, SUM(B.bookRentNum) AS S FROM book B GROUP BY B.bookName, B.bookCategory,B.ISBN) WHERE S >= ALL(SELECT DS FROM (SELECT D.ISBN AS DI, D.bookCategory AS DC, SUM(D.bookRentNum) AS DS FROM book D GROUP BY D.bookCategory,D.ISBN) WHERE C = DC)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String category = resultSet.getString(3);
                String num = resultSet.getInt(4)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Category]: " + category + " [Times of Rent]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getMostRentBookISBNByAuthor() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT N, I, C, S FROM (SELECT B.bookName AS N, B.ISBN AS I, B.author AS C, SUM(B.bookRentNum) AS S FROM book B GROUP BY B.bookName, B.author,B.ISBN) WHERE S >= ALL(SELECT DS FROM (SELECT D.ISBN AS DI, D.author AS DC, SUM(D.bookRentNum) AS DS FROM book D GROUP BY D.author,D.ISBN) WHERE C = DC)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String num = resultSet.getInt(4)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + " [Times of Rent]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getMostWantBookISBNByCategory() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT N, I, C, S FROM (SELECT B.bookName AS N, B.ISBN AS I, B.bookCategory AS C, SUM(B.bookWantNum) AS S FROM book B GROUP BY B.bookName, B.bookCategory,B.ISBN) WHERE S >= ALL(SELECT DS FROM (SELECT D.ISBN AS DI, D.bookCategory AS DC, SUM(D.bookWantNum) AS DS FROM book D GROUP BY D.bookCategory,D.ISBN) WHERE C = DC)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String category = resultSet.getString(3);
                String num = resultSet.getInt(4)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Category]: " + category + " [Times of Want]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getMostWantBookISBNByAuthor() throws SQLException {
        List<String> result = new ArrayList<>();
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql =
                "SELECT N, I, C, S FROM (SELECT B.bookName AS N, B.ISBN AS I, B.author AS C, SUM(B.bookWantNum) AS S FROM book B GROUP BY B.bookName, B.author,B.ISBN) WHERE S >= ALL(SELECT DS FROM (SELECT D.ISBN AS DI, D.author AS DC, SUM(D.bookWantNum) AS DS FROM book D GROUP BY D.author,D.ISBN) WHERE C = DC)";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String name = resultSet.getString(1);
                String ISBN = resultSet.getString(2);
                String author = resultSet.getString(3);
                String num = resultSet.getInt(4)+"";
                String out = "[Book Name]: "+name + " [Book ISBN]: "+ISBN + " [Author]: " + author + " [Times of Want]: " + num;
                result.add(out);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
