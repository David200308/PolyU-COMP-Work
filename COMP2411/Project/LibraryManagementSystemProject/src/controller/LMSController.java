package controller;

import model.*;
import view.MainView;

import java.util.*;

public class LMSController {
    private static ModelController modelController = new ModelController();
    private static final MainView mainView = new MainView();
    private static DataAnalyser dataAnalyser = new DataAnalyser();

    /**
     * Get the current model controller
     * @return the current model controller
     */
    public static ModelController getModelController() {
        return modelController;
    }

    /**
     * To listen the user keyboard input
     * @return the user input
     */
    public static String inputListener() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * To check whether a String is in the format of int
     * @param s the String to be checked
     * @return true if the String is in the format of int, false otherwise
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> The entry of the entire application <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
     * @param args
     */
    public static void main(String[] args) {
        while(true) {
            mainView.welcomePage();
            String input = inputListener();
            // Set date done
            if (input.equals("L")) {
                mainView.mainPageWelcome();
                // compulsory set date
                System.out.println("Please set today's date first");
                while(true) {
                    System.out.print(">>> Please enter the Year: ");
                    String y = inputListener();
                    if (!isInteger(y)) {
                        System.out.println("Invalid input, please enter again");
                        continue;
                    }
                    System.out.print(">>> Please enter the Month: ");
                    String m = inputListener();
                    if (!isInteger(m)) {
                        System.out.println("Invalid input, please enter again");
                        continue;
                    }
                    System.out.print(">>> Please enter the Day: ");
                    String d = inputListener();
                    if (!isInteger(d)) {
                        System.out.println("Invalid input, please enter again");
                        continue;
                    }
                    modelController.setDate(y, m, d);
                    break;
                }
                while (true) {
                    mainView.mainPage();
                    String mainOption = inputListener();
                    if (mainOption.equals("A")) {
                        while (true) {
                            System.out.print("Please enter book ID (- [Back] for Back):");
                            String inBookID = inputListener();
                            if (inBookID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(inBookID)) {
                                System.out.println("Book ID should be an integer, please try again!");
                                continue;
                            }
                            System.out.print("Please enter book ISBN (- [Back] for Back):");
                            String inBookISBN = inputListener();
                            if (inBookISBN.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            System.out.print("Please enter book name (- [Back] for Back):");
                            String inBookName = inputListener();
                            if (inBookName.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            System.out.print("Please enter book author (- [Back] for Back):");
                            String inBookAuthor = inputListener();
                            if (inBookAuthor.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            System.out.print("Please enter book category (- [Back] for Back):");
                            String inBookCategory = inputListener();
                            if (inBookCategory.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            Book book = new Book(inBookID, inBookISBN, inBookName, inBookAuthor, inBookCategory,0,0);
                            if (modelController.addRecord(book)) {
                                mainView.successPage();
                                mainView.linePage();
                                mainView.breakPointPage();
                                String breakPoint = inputListener();
                                break;
                            }
                            else {
                                mainView.unSuccessPage();
                            }
                        }
                    } else if (mainOption.equals("B")) {
                        while (true) {
                            mainView.inputUserPage();
                            String accountID = inputListener();
                            if (accountID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(accountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            User user = new User(accountID, true, "Notification \n ");
                            if (modelController.addRecord(user)) {
                                mainView.successPage();
                                mainView.linePage();
                                mainView.breakPointPage();
                                String breakPoint = inputListener();
                                break;
                            } else {
                                mainView.unSuccessPage();
                            }
                        }
                    } else if (mainOption.equals("C")) {
                        while (true) {
                            mainView.inputBookIDPage();
                            String bookID = inputListener();
                            if (bookID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(bookID)) {
                                System.out.println("Book ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.deleteBookRecord(bookID)) {
                                mainView.successPage();
                                mainView.linePage();
                                mainView.breakPointPage();
                                String breakPoint = inputListener();
                                break;
                            } else {
                                mainView.unSuccessPage();
                            }
                        }
                    } else if (mainOption.equals("D")) {
                        while (true) {
                            mainView.inputUserPage();
                            String accountID = inputListener();
                            if (accountID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(accountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.deleteUserRecord(accountID)) {
                                mainView.successPage();
                                mainView.linePage();
                                mainView.breakPointPage();
                                String breakPoint = inputListener();
                                break;
                            } else {
                                mainView.unSuccessPage();
                            }
                        }
                    } else if (mainOption.equals("E")) {
                        while (true) {
                            mainView.inputUserPage();
                            String userAccountID = inputListener();
                            if (userAccountID.equals("Back"))
                                break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(userAccountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.searchUserOnAccountID(userAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                continue;
                            }
                            User user = modelController.searchUserOnAccountID(userAccountID).get(0);
                            if (user.getAccountStatus()) {
                                mainView.processUserRentPage();
                                String inBookID = inputListener();
                                if (inBookID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                                if (!isInteger(inBookID)) {
                                    System.out.println("Book ID should be an integer, please try again!");
                                    continue;
                                }
                                if (modelController.rentBookFromUser(userAccountID, inBookID)) { //used rentBookFromUser return boolean
                                    mainView.successPage();
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                    break;
                                } else {
                                    mainView.unSuccessPage();
                                }

                            } else {
                                mainView.accountBannedPage();
                            }
                        }

                    } else if (mainOption.equals("F")) {
                        while (true) {
                            mainView.inputUserPage();
                            String userAccountID = inputListener();
                            if (userAccountID.equals("Back"))
                                break; // back to previous page (assumption: there is no input value named "Back")
                            if (!isInteger(userAccountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.searchUserOnAccountID(userAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                continue;
                            }
                            User user = modelController.searchUserOnAccountID(userAccountID).get(0);
                            if (user.getAccountStatus()) {
                                mainView.processUserRentPage();
                                String inBookID = inputListener();
                                if (inBookID.equals("Back")) {
                                    break; // back to previous page (assumption: there is no input value named "Back")
                                }
                                if (!isInteger(inBookID)) {
                                    System.out.println("Book ID should be an integer, please try again!");
                                    continue;
                                }
                                if (modelController.rentBookFromPlacedBook(userAccountID, inBookID)) { //used rentBookFromUser return boolean
                                    mainView.successPage();
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                    break;
                                } else {
                                    mainView.unSuccessPage();
                                }
                            } else {
                                mainView.accountBannedPage();
                            }
                        }

                    } else if (mainOption.equals("G")) {
                        while (true) {
                            mainView.inputUserPage();
                            String userAccountID = inputListener();
                            if (userAccountID.equals("Back")) {
                                break; // back to previous page (assumption: there is no input value named "Back")
                            }
                            if (!isInteger(userAccountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.searchUserOnAccountID(userAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                continue;
                            }
                            User user = modelController.searchUserOnAccountID(userAccountID).get(0);
                            if (user.getAccountStatus()) {
                                mainView.processUserWantPage();
                                String inISBN = inputListener();
                                if (inISBN.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                                if (modelController.reserveBook(userAccountID, inISBN)) {
                                    mainView.successPage();
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                    break;
                                } else {
                                    mainView.unSuccessPage();
                                }
                            } else {
                                mainView.accountBannedPage();
                            }
                        }
                    } else if (mainOption.equals("H")) {
                        while (true) {
                            mainView.inputUserPage();
                            String userAccountID = inputListener();
                            if (userAccountID.equals("Back")) {
                                break; // back to previous page (assumption: there is no input value named "Back")
                            }
                            if (!isInteger(userAccountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.searchUserOnAccountID(userAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                continue;
                            }
                            User user = modelController.searchUserOnAccountID(userAccountID).get(0);
                            if (user.getAccountStatus()) {
                                mainView.processUserCancelReservePage();
                                String inISBN = inputListener();
                                if (inISBN.equals("Back"))
                                    break; // back to previous page (assumption: there is no input value named "Back")
                                if (modelController.cancelReservedBook(inISBN, userAccountID)) {
                                    mainView.successPage();
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                    break;
                                } else {
                                    mainView.unSuccessPage();
                                }
                            } else {
                                mainView.accountBannedPage();
                            }
                        }

                    } else if (mainOption.equals("I")) {
                        while (true) {
                            mainView.inputUserPage();
                            String userAccountID = inputListener();
                            if (userAccountID.equals("Back")) {
                                break; // back to previous page (assumption: there is no input value named "Back")
                            }
                            if (!isInteger(userAccountID)) {
                                System.out.println("Account ID should be an integer, please try again!");
                                continue;
                            }
                            if (modelController.searchUserOnAccountID(userAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                continue;
                            }
                            User user = modelController.searchUserOnAccountID(userAccountID).get(0);
                            if (user.getAccountStatus()) {
                                mainView.processUserCancelPlacedPage();
                                String inBookID = inputListener();
                                if (inBookID.equals("Back")) {
                                    break; // back to previous page (assumption: there is no input value named "Back")
                                }
                                if (!isInteger(inBookID)) {
                                    System.out.println("Book ID should be an integer, please try again!");
                                    continue;
                                }
                                if (modelController.cancelPlacedBook(inBookID, userAccountID)) {
                                    mainView.successPage();
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                    break;
                                } else {
                                    mainView.unSuccessPage();
                                }
                            } else {
                                mainView.accountBannedPage();
                            }
                        }
                    } else if (mainOption.equals("J")) {
                        while (true) {
                            mainView.processUserSearchPage();
                            String searchOption = inputListener();
                            if (searchOption.equals("A")) { // ISBN
                                mainView.inputISBNPage();
                                String inISBN = inputListener();
                                try {
                                    if (modelController.searchBookOnBookISBN(inISBN).size() == 0) {
                                        mainView.emptyPage();
                                    }
                                    else {
                                        mainView.showListPage();
                                        mainView.showTitle();
                                        System.out.println("book(s) search on ISBN:");
                                        for (Book book : modelController.searchBookOnBookISBN(inISBN)) {
                                            System.out.println(book.showInfo());
                                        }
                                    }
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else if (searchOption.equals("B")) { // name
                                mainView.inputNamePage();
                                String inName = inputListener();
                                try {
                                    if (modelController.searchBookOnBookName(inName).size() == 0) {
                                        mainView.emptyPage();
                                    } else {
                                        mainView.showListPage();
                                        mainView.showTitle();
                                        System.out.println("book(s) search on book name:");
                                        for (Book book : modelController.searchBookOnBookName(inName)) {
                                            System.out.println(book.showInfo());
                                        }
                                    }
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else if (searchOption.equals("C")) { // author
                                mainView.inputAuthorPage();
                                String inAuthor = inputListener();
                                try {
                                    if (modelController.searchBookOnBookAuthor(inAuthor).size() == 0) {
                                        mainView.emptyPage();
                                    } else {
                                        mainView.showListPage();
                                        mainView.showTitle();
                                        System.out.println("book(s) search on author name:");
                                        for (Book book : modelController.searchBookOnBookAuthor(inAuthor)) {
                                            System.out.println(book.showInfo());
                                        }
                                    }
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else if (searchOption.equals("D")) { // category
                                mainView.inputCategoryPage();
                                String inCategory = inputListener();
                                try {
                                    if (modelController.searchBookOnBookCategory(inCategory).size() == 0) {
                                        mainView.emptyPage();
                                    }
                                    else {
                                        mainView.showListPage();
                                        mainView.showTitle();
                                        System.out.println("book(s) search on category:");
                                        for (Book book : modelController.searchBookOnBookCategory(inCategory)) {
                                            System.out.println(book.showInfo());
                                        }
                                    }
                                    mainView.linePage();
                                    mainView.breakPointPage();
                                    String breakPoint = inputListener();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else if (searchOption.equals("Back")) { // back to previous page (assumption: there is no input value named "Back")
                                break;
                            } else {
                                mainView.errorPage();
                            }
                        }
                    } else if (mainOption.equals("L")) {
                        while (true) {
                            mainView.processUserReturnPage();
                            String inBookID = inputListener();
                            if (inBookID.equals("Back")) break; // back to previous page (assumption: there is no input value named "Back")
                            if (modelController.returnBookFromUser(inBookID)) {
                                mainView.successPage();
                                mainView.linePage();
                                mainView.breakPointPage();
                                String breakPoint = inputListener();
                                break;
                            } else {
                                mainView.unSuccessPage();
                            }
                        }
                    } else if (mainOption.equals("K")) {
                        mainView.inputUserPage();
                        String inAccountID = inputListener();
                        try {
                            if (isInteger(inAccountID) && modelController.searchUserOnAccountID(inAccountID).size() == 0) {
                                System.out.println("No such user found!");
                                mainView.emptyPage();
                            } else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("book(s) search on account ID:");
                                for (User user : modelController.searchUserOnAccountID(inAccountID)) {
                                    System.out.println(user.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("M")) {
                        modelController.refreshExpiredPlacedBook();
                        mainView.successPage();
                        mainView.linePage();
                        mainView.breakPointPage();
                        String breakPoint = inputListener();
                    } else if (mainOption.equals("N")) {
                        try {
                            List<User> out = modelController.deactivateUserForExpiredRentBook();
                            if (out.size() == 0) {
                                mainView.emptyPage();
                            }
                            else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("expired rent book(s) and associated user(s):");
                                for (User user : out) {
                                    System.out.println(user.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("O")) {
                        try {
                            if (modelController.getAllBooks().size() == 0) {
                                mainView.emptyPage();
                            } else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("all book(s):");
                                for (Book book : modelController.getAllBooks()) {
                                    System.out.println(book.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("P")) {
                        try {
                            if (modelController.getAllRentBooks().size() == 0) {
                                mainView.emptyPage();
                            } else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("all rent book(s):");
                                for (RentBook rentBook : modelController.getAllRentBooks()) {
                                    System.out.println(rentBook.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("Q")) {
                        try {
                            if (modelController.getAllWantBooks().size() == 0) {
                                mainView.emptyPage();
                            }
                            else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("all want book(s):");
                                for (WantBook wantBook : modelController.getAllWantBooks()) {
                                    System.out.println(wantBook.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("R")) {
                        try {
                            if (modelController.getAllPlacedBooks().size() == 0) {
                                mainView.emptyPage();
                            }
                            else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("all placed book(s):");
                                for (PlacedBook placedBook : modelController.getAllPlacedBooks()) {
                                    System.out.println(placedBook.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("S")) {
                        try {
                            if (modelController.getAllUsers().size() == 0) {
                                mainView.emptyPage();
                            }
                            else {
                                mainView.showListPage();
                                mainView.showTitle();
                                System.out.println("all user(s):");
                                for (User user : modelController.getAllUsers()) {
                                    System.out.println(user.showInfo());
                                }
                            }
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("T")) {
                        try {
                            int totalBookNumber = dataAnalyser.getTotalBookNumber();
                            int totalRentBookNumber = dataAnalyser.getTotalRentBook();
                            List<String> mostRentBookISBN = dataAnalyser.getMostRentBookISBN();
                            List<Book> mostRentBookID = dataAnalyser.getMostRentBookID();
                            List<String> leastRentBookISBN = dataAnalyser.getLeastRentBookISBN();
                            List<Book> leastRentBookID = dataAnalyser.getLeastRentBookID();
                            List<String> mostWantBookISBN = dataAnalyser.getMostWantBookISBN();
                            List<Book> mostWantBookID = dataAnalyser.getMostWantBookID();
                            List<String> leastWantBookISBN = dataAnalyser.getLeastWantBookISBN();
                            List<Book> leastWantBookID = dataAnalyser.getLeastWantBookID();
                            List<String> mostRentBookISBNByCategory = dataAnalyser.getMostRentBookISBNByCategory();
                            List<String> mostRentBookISBNByAuthor = dataAnalyser.getMostRentBookISBNByAuthor();
                            List<String> mostWantBookISBNByCategory = dataAnalyser.getMostWantBookISBNByCategory();
                            List<String> mostWantBookISBNByAuthor = dataAnalyser.getMostWantBookISBNByAuthor();
                            mainView.analysisReportPage(totalBookNumber, totalRentBookNumber, mostRentBookISBN, mostRentBookID,
                                    leastRentBookISBN, leastRentBookID, mostWantBookISBN, mostWantBookID,
                                    leastWantBookISBN, leastWantBookID, mostRentBookISBNByCategory, mostRentBookISBNByAuthor,
                                    mostWantBookISBNByCategory, mostWantBookISBNByAuthor);
                            mainView.linePage();
                            mainView.breakPointPage();
                            String breakPoint = inputListener();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (mainOption.equals("U")) {
                        while(true) {
                            System.out.print(">>> Please enter the Year: ");
                            String y = inputListener();
                            if (!isInteger(y)) {
                                System.out.println("Invalid input, please enter again");
                                continue;
                            }
                            System.out.print(">>> Please enter the Month: ");
                            String m = inputListener();
                            if (!isInteger(m)) {
                                System.out.println("Invalid input, please enter again");
                                continue;
                            }
                            System.out.print(">>> Please enter the Day: ");
                            String d = inputListener();
                            if (!isInteger(d)) {
                                System.out.println("Invalid input, please enter again");
                                continue;
                            }
                            modelController.setDate(y, m, d);
                            break;
                        }
                        mainView.successPage();
                        mainView.linePage();
                        mainView.breakPointPage();
                        String breakPoint = inputListener();
                    } else if (mainOption.equals("X")) {
                        modelController = new ModelController();
                        mainView.linePage();
                        mainView.breakPointPage();
                        String breakPoint = inputListener();
                    } else if (mainOption.equals("-1")) { // exit
                        modelController.closeDB();
                        System.exit(0);
                    } else {
                        mainView.errorPage();
                    }
                }
            } else if (input.equals("-1")) {
                modelController.closeDB();
                System.exit(0);
            } else {
                mainView.errorPage();
            }
        }
    }
}
