package database;

import model.*;
import util.PrintablePreparedStatement;
import util.ScriptRunner;

import java.awt.print.Printable;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database related transactions
 * This is based off the Java Sample Project (DatabaseConnectionHandler.java)
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    //private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
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

    public void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // runs the database setup script
    public void databaseSetup() throws RuntimeException {
        Reader reader = null;

        // program MUST BE RAN in the parent of the project folder for the file to open
        try {
            reader = new java.io.FileReader("project_m1p7e_t4v5i_z8v1d/src/sql/scripts/databaseSetup.sql");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error opening databaseSetup.sql");
        }

        // execute entire file at once using script runner
        try {
            ScriptRunner script = new ScriptRunner(connection, false, true);
            script.runScript(reader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error running script.  Cause: " + e, e);
        }

        System.out.println("database has been reset to initial values!");
    }

    public void insertEvent(EventModel event) throws SQLException{
        // CITE SAMPLE PROJECT
       // try {
            String query = "INSERT INTO EVENT VALUES (?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, event.getEventID());
            ps.setInt(2, event.getTicketSold());
            ps.setString(3, event.getLocation());
            ps.setString(4, event.getDate());
            ps.setInt(5,event.getCapacity());
            ps.setString(6, event.getTitle());
            ps.setNull(7, java.sql.Types.INTEGER);
            ps.executeUpdate();
            connection.commit();

            ps.close();
//        } catch (SQLException e) {
//            handleSQLException(e);
//        }
    }

    private void insertEmployee(EmployeeModel employee) throws SQLException{
      //  try {
            String query = "INSERT INTO EMPLOYEES VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1,employee.getEmployeeID());
            ps.setString(2,employee.getPhoneNum());
            ps.setString(3, employee.getName());
            ps.executeUpdate();
            connection.commit();

            ps.close();
//        } catch (SQLException e) {
//            handleSQLException(e);
//        }
    }

    private void insertEventStaffSupervision(EventStaffModel staff, EventModel event) throws SQLException {

            String query = "INSERT INTO EventStaffSupervises VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1,staff.getEmployeeID());
            ps.setString(2,staff.getDepartment());
            ps.setInt(3, event.getEventID());
            ps.executeUpdate();
            connection.commit();

            ps.close();
//        } catch (SQLException e) {
//            handleSQLException(e);
//        }
    }
    public void insertArtwork(ArtworkModel art) throws SQLException {
            // Check for null values
            String query = "INSERT INTO Artwork VALUES (?,?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, art.getArtworkID());
            ps.setInt(2, art.getArtistID());
            ps.setString(3, art.getTitle()); // not null
            ps.setString(4, art.getDimensions());
            ps.setString(5, art.getDateCreated()); //not null
            ps.setString(6, art.getDisplayMedium()); //not null
            ps.setInt(7, art.getDonorID());
            ps.setInt(8, art.getFeatureID());
            ps.setInt(9, art.getValue());
            ps.executeUpdate();
            connection.commit();
            ps.close();
//        } catch (SQLException e) {
//            handleSQLException(e);
//        }
    }

    public void deleteArtist(int artistID) throws SQLException {
        String query = "DELETE FROM Artist WHERE artistID = ?";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setInt(1, artistID);
        ps.executeUpdate();
        connection.commit();
        ps.close();
        // this should also delete the associated artwork pieces
    }

    public void deleteEvent(int eventID) throws SQLException {
        String query = "DELETE FROM Artist WHERE artistID = ?";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ps.setInt(1, eventID);
        ps.executeUpdate();
        connection.commit();
        ps.close();
        // this should also delete the associated artwork pieces
    }
    private void dropBranchTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("employees")) {
                    ps.execute("DROP TABLE Employees");
                    break;
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public EventModel[] getAverageTicketsSoldPerEvent() throws SQLException {
        String query = """
                SELECT title, AVG(ticketsSold) as average_tickets_sold
                FROM Event
                GROUP BY title;""";
        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
        ResultSet rs = ps.executeQuery();
        ArrayList<EventModel> rows = new ArrayList<>();
        while(rs.next()){
           EventModel model = new EventModel(
                   rs.getInt("ticketsSold"),
                   rs.getString("location"),
                   rs.getString("date"),
                   rs.getInt("capacity"),
                   rs.getInt("eventID"),
                   rs.getString("title")
           );
           rows.add(model);
        }
        return rows.toArray(new EventModel[rows.size()]);
    }
    public double getAverageBudgetOverStatus(int threshold) {
        //ProjectModel result = null;
        double result = 0;
        try {
            String query = "SELECT AVG(average_budget_per_status) " +
                    "FROM (" + "SELECT status, AVG(budget) AS average_budget_per_status " + "FROM Project " + "WHERE budget > ? " + "GROUP BY status " + ")"; //") AS subquery";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();

            //result = rs.getDouble("AVG(average_budget_per_status)");
            //ResultSet (rs.getDouble(1))

            /*
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                System.out.println("Column " + i + ": " + metaData.getColumnLabel(i) + " - Type: " + metaData.getColumnTypeName(i));
            }*/

            if (rs.next()) {
                result = rs.getDouble(1); // Retrieve the value from the first column
                //System.out.println("TEST: " + result);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    public ResultSet getProjectionResults (String query) {
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

        public ProjectModel[] getProjectSelectionInfo (String whereClause, boolean isEmpty) {
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
