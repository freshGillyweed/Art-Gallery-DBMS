package controller;

import database.DatabaseConnectionHandler;
import delegates.*;
import model.ProjectModel;
import ui.*;
import util.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the main controller class that will orchestrate everything.
 * This is based off the Sample Java Project (Bank.java)
 */
public class Controller implements LoginWindowDelegate, MainWindowDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public Controller() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            MainWindow mainWindow = new MainWindow(this, dbHandler);
            mainWindow.showFrame(); // show the main menu (should run until program is halted)
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void showAverageBudgetOverStatus(int threshold) {
       double average = dbHandler.getAverageBudgetOverStatus(threshold);
       System.out.println("\nAverage Budget Over Status for Projects with budgets higher than " + threshold + ": " + average);
    }

    public void showProjectSelectionInfo(String whereClause, boolean isEmpty) {

        ProjectModel[] models = dbHandler.getProjectSelectionInfo(whereClause, isEmpty);

        for (ProjectModel model : models) {
            System.out.println("\n");
            System.out.println("Project ID: " + model.getProjectID());
            System.out.printf("Title: " + model.getTitle());
            System.out.println("Budget: " + model.getBudget());
            System.out.println("Status: " + model.getStatus());
            System.out.println("Start Date: " + model.getStartDate());
            System.out.println("End Date: " + model.getEndDate());
            System.out.println();
        }
    }

    public void showProjectionResult() {

        // dynamically print all tables and their attributes
        try {
            Connection connection = dbHandler.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT USER FROM DUAL");//
            resultSet.next();//
            String currentUser = resultSet.getString(1);
            
            ResultSet tables = metaData.getTables(null, currentUser, "%", new String[]{"TABLE"});
            //ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table name: " + tableName);

                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    System.out.println("  Column name: " + columnName);
                }
                System.out.println("\n");
                columns.close();
            }
            tables.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Prompt user for table and attributes to perform projection
        StringBuilder query = new StringBuilder("SELECT ");
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\\n");
        System.out.print("Enter table name to perform projection: ");
        String tableName = input.next();
        input.nextLine();
        //query.append(tableName + " ");

        while (true) {
            System.out.print("Enter attribute(column) to view: ");
            String attribute = input.next();
            input.nextLine();
            query.append(attribute + " ");

            System.out.print("Enter 1 to view more columns, otherwise 2: ");
            int moreColumns = input.nextInt();
            if (moreColumns == 2) {
                break;
            }
            query.append(", ");
        }
        query.append("FROM " + tableName);

        ResultSet rs = dbHandler.getProjectionResults(String.valueOf(query));

        // display tuples returned by getProjectionResults
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Display column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Display data as strings
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + "\t");
                }
                System.out.println();
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MADE FOR UPDATE BOX
    // =======================================================================
    // made for update window, grabs every title of artwork in the database
    public String[] getArtworkTitles() {
        String query = "SELECT title FROM Artwork";
        PrintablePreparedStatement ps;
        ResultSet res;
        ArrayList<String> out = new ArrayList<String>();
        try {
             ps = new PrintablePreparedStatement(
                    dbHandler.getConnection().prepareStatement(query), query, true);
             ps.execute();
             res = ps.getResultSet();
            while (res.next()) {
                out.add(res.getString(1));
            }
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            return new String[] {"Error getting artwork titles"};
        }

        String[] arr = new String[out.size()];
        arr = out.toArray(arr);
        return arr;
    }

    public void update(String title, String attr, String newAttr) throws Exception {
        System.out.println(title);
        System.out.println(attr);
        System.out.println(newAttr);
        String query = "UPDATE artwork SET " + attr + " = '" + newAttr + "' WHERE title = '" + title + "'";
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(
                    dbHandler.getConnection().prepareStatement(query), query, true);
            ps.execute();
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            Exception p = new Exception("error");
            throw p;
        }
    }

    // =======================================================================
    // methods for division:
    // find every visitor who visited every exhibit
    public String[] getAllLoyalVisitors() throws Exception {
        ResultSet res;
        ArrayList<String> out = new ArrayList<String>();
        String query = "SELECT name FROM visitor V WHERE NOT EXISTS (" +
                "SELECT exhibitionID FROM Exhibition E WHERE NOT EXISTS (" +
                "SELECT visitorID FROM Visit I WHERE E.exhibitionID = I.exhibitionID AND V.visitorID = I.visitorID))";
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(
                    dbHandler.getConnection().prepareStatement(query), query, true);
            ps.execute();
            res = ps.getResultSet();
            while (res.next()) {
                out.add(res.getString(1));
            }
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error");
        }
        String[] arr = new String[out.size()];
        arr = out.toArray(arr);
        return arr;
    }

    // ====================================================================
    // methods for join:

    public String[] getArtistsValue(String op, int Value) throws Exception {
        ResultSet res;
        ArrayList<String> out = new ArrayList<String>();
        String query = "SELECT UNIQUE name FROM Artist INNER JOIN " +
                "Artwork ON Artist.ArtistID = Artwork.artistID WHERE Artwork.value " + op + " " + Value;
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(
                    dbHandler.getConnection().prepareStatement(query), query, true);
            ps.execute();
            res = ps.getResultSet();
            while (res.next()) {
                out.add(res.getString(1));
            }
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error");
        }
        String[] arr = new String[out.size()];
        arr = out.toArray(arr);
        return arr;
    }

    // ====================================================================
    // methods for having:
    public String[] getDonorsValue(String op, int value) throws Exception {
        ResultSet res;
        ArrayList<String> out = new ArrayList<String>();
        String query = "SELECT country FROM Donor GROUP BY country HAVING sum(totalDonationValue) " + op + " " + value + " ORDER BY sum(totalDonationValue) DESC";
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(
                    dbHandler.getConnection().prepareStatement(query), query, true);
            ps.execute();
            res = ps.getResultSet();
            while (res.next()) {
                out.add(res.getString(1));
            }
        }   catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("error");
        }
        String[] arr = new String[out.size()];
        arr = out.toArray(arr);
        return arr;
    }

    // =====================================================================

    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    public void databaseSetup() {
        dbHandler.databaseSetup();
    }

    public String[] getTables() {
        // TODO: make this dynamic
        return new String[]{"Artist", "Artwork", "Curator", "Donor", "Employee", "Event", "EventStaff",
                "Exhibition", "Host", "Participate in", "Project", "Researcher", "Visit", "Visitor"};
    }

    public static void main(String[] args) {
        Controller controller = new Controller();

        MainWindow mainWindow = new MainWindow(controller, controller.dbHandler);

        // uncomment this and comment controller.start() to skip to bypass login screen
        // this will cause the connection to the database to not be initialized (only use for making the gui)
        //mainWindow.showFrame();

        controller.start();
    }
}
