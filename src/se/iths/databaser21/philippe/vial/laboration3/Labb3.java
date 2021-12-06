package se.iths.databaser21.philippe.vial.laboration3;

import java.sql.*;
import java.util.Scanner;


public class Labb3 {

    Scanner scanner = new Scanner(System.in);
    ResultSet resultSet;
    Statement statement;
    private String firstName;
    private String lastName;
    private int artistAge;
    private int artistID;
    Connection connection;


    public static void main(String[] args) {
        Labb3 labb = new Labb3();
        try {
            labb.connection();
            labb.runLoop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void runLoop() throws SQLException {
        boolean run = true;
        while (run) {
            menuChoices();
            int choice = inputReadInteger(" ");
            run = executeChoice(true, choice);
        }
    }

    public void menuChoices() {
        System.out.println("0. Create table");
        System.out.println("1. Add artist");
        System.out.println("2. Delete artist");
        System.out.println("3. Update artist");
        System.out.println("4. Show all artists");
        System.out.println("5. Search by ID");
        System.out.println("6. Search by age");
        System.out.println("7. Search by name");
        System.out.println("99. Terminate");
        System.out.println("---------------------------");

    }

    private boolean executeChoice(boolean run, int choice) throws SQLException {
        switch (choice) {
            case 0 -> createTable();
            case 1 -> add();
            case 2 -> delete();
            case 3 -> update();
            case 4 -> showAll();
            case 5 -> findByID();
            case 6 -> findByAge();
            case 7 -> findByName();
            case 99 -> run = false;
            default -> System.out.println("Wrong input, try again!");
        }
        return run;
    }

    private int inputReadInteger(String output) {
        System.out.println(output);
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong input, only numbers! Try Again!");
            scanner.next();
        }
        return Integer.parseInt(scanner.next());
    }

    private String inputReadString(String output) {
        System.out.println(output);
        while (scanner.hasNextInt()) {
            System.out.println("Wrong input, no numbers! Try Again!");
            scanner.next();
        }
        return scanner.next();
    }

    private void connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/laboration3";
        String user = "Philippe";
        String password = "Password123";
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    private void artistInfoInput() {
        firstName = inputReadString("First name: ");
        lastName = inputReadString("Last name: ");
        artistAge = inputReadInteger("Age: ");
    }

    private void createTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE Artist" +
                "(ID SMALLINT AUTO_INCREMENT PRIMARY KEY , " +
                "First_name VARCHAR(50), " +
                "Last_name VARCHAR(70), " +
                "age SMALLINT)");
    }

    private void add() throws SQLException {
        artistInfoInput();
        statement.executeUpdate("INSERT INTO Artist (first_name, last_name, age) " +
                "VALUES('" + firstName + "','" + lastName + "', '" + artistAge + "')");
    }

    private void delete() throws SQLException {
        artistID = inputReadInteger("Input ID: ");
        statement.executeUpdate("DELETE FROM Artist WHERE ID = " + artistID);
    }

    private void update() throws SQLException {
        artistID = inputReadInteger("Input ID: ");
        artistInfoInput();
        statement.executeUpdate("UPDATE artist SET first_name = '"
                + firstName + "', last_name = '"
                + lastName + "', age = '"
                + artistAge + "' WHERE id = "
                + artistID + ";");
    }

    private void showAll() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Artist");
        printSelectData();
    }

    private void findByID() throws SQLException {
        artistID = inputReadInteger("Input artistID: ");
        resultSet = statement.executeQuery("SELECT * FROM Artist WHERE ID = " + artistID);
        printSelectData();
    }

    private void printSelectData() throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id") + "  || FIRST NAME: " +
                    resultSet.getString("First_name") + " || LAST NAME: " +
                    resultSet.getString("Last_name") + " || AGE: " +
                    resultSet.getString("Age"));
            System.out.println("---------------------------------------");
        }
    }

    private void findByAge() throws SQLException {
        artistAge = inputReadInteger("Enter age: ");
        resultSet = statement.executeQuery("SELECT * FROM Artist WHERE age = " + artistAge);
        printSelectData();
    }

    private void findByName() throws SQLException {
        firstName = inputReadString("Enter First name: ");
        lastName = inputReadString("Enter Last name: ");
        resultSet = statement.executeQuery("SELECT * FROM Artist " +
                "WHERE first_name = '"
                + firstName + "' AND last_name = '"
                + lastName + "';");
        printSelectData();
    }
}
