package controller;

import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import ui.LoginWindow;
/**
 * This is the main controller class that will orchestrate everything.
 * This is based off the Sample Java Project (Bank.java)
 */
public class Controller implements LoginWindowDelegate {
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

//            TerminalTransactions transaction = new TerminalTransactions();
//            transaction.setupDatabase(this);
//            transaction.showMainMenu(this);
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
       int average = dbHandler.getAverageBudgetOverStatus(threshold);
        System.out.println("Average Budget Over Status for Projects with budgets higher than " + threshold + ": " + average);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
}