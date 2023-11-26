package database;

import model.ProjectModel;
import util.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 * This is based off the Java Sample Project (DatabaseConnectionHandler.java)
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    //	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    public void databaseSetup() {
        // TODO: IMPLEMENT
    }

    public Connection getConnection() {
        return connection;
    }

    public int getAverageBudgetOverStatus(int threshold) {
        //ProjectModel result = null;
        int result = 0;
        try {
            String query = "SELECT AVG(average_budget_per_status) " +
                    "FROM ( " +
                    "    SELECT status, AVG(budget) AS average_budget_per_status " +
                    "    FROM Project " +
                    "    WHERE budget > ? " +
                    "    GROUP BY status " +
                    ")"; //") AS subquery";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();

            result = rs.getInt("AVG(average_budget_per_status)");

            /*result =  new ProjectModel(rs.getInt("projectID"),
                    rs.getString("title"),
                    rs.getDouble("budget"),
                    rs.getString("status"),
                    rs.getString("startDate"),
                    rs.getString("endDate"));
             */
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ResultSet getProjectionResults(String query) {
        // since we dont know what is returned, just print it here or RETURN ResultSet rs
        // return null;// stub

        ResultSet rs;

        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            //int columnCount = metaData.getColumnCount();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

        public ProjectModel[] getProjectSelectionInfo(String whereClause, boolean isEmpty) {
        ArrayList<ProjectModel> result = new ArrayList<ProjectModel>();

        try {
            String query;
            if (isEmpty) {
                query = "SELECT * FROM Project";
            } else {
                query = "SELECT * FROM Project WHERE ";
                query = query + whereClause;
            }
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();


            while(rs.next()) {
                ProjectModel model = new ProjectModel(rs.getInt("projectID"),
                        rs.getString("title"),
                        rs.getInt("budget"),
                        rs.getString("status"),
                        rs.getString("startDate"),
                        rs.getString("endDate"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ProjectModel[result.size()]);
    }

}