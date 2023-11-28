package ui;

import delegates.TerminalTransactionsDelegate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TerminalTransactions {

    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;
    private Scanner input;

    private BufferedReader bufferedReader = null;
    private TerminalTransactionsDelegate delegate = null;

    public TerminalTransactions() {
    }

    public void setupDatabase(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while(choice != 1 && choice != 2) {
            System.out.println("If you want to proceed, enter 1; if you want to quit, enter 2.");

            choice = readInteger(false);

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        delegate.databaseSetup();
                        break;
                    case 2:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
                        break;
                }
            }
        }
    }

    /**
     * Displays simple text interface
     */

    public void showMainMenu(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Show average project budget over status");
            System.out.println("2. Show filtered information from project");
            System.out.println("3. View any number of attributes from any table");
            // Add text
            System.out.print("Please choose one of the above 5 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleAverageProjectBudgetOption();
                        break;
                    case 2:
                        handleProjectSelectionOption();
                        break;
                    case 3:
                        handleProjectionOption();
                        break;
                    case 4:
                        //add options
                        //break;
                    //case 5:
                        //add options
                        //break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleProjectionOption() {
        //DatabaseMetaData metaData = conn.getMetaData();
        // User input is handled in Controller class due to issues pertaining to Connection
        delegate.showProjectionResult();

    }

    private void handleAverageProjectBudgetOption() {
        int threshold = INVALID_INPUT;
        while (threshold == INVALID_INPUT) {
            System.out.print("Please enter the threshold for project budget: ");
            threshold = readInteger(false);
        }

       delegate.showAverageBudgetOverStatus(threshold);
    }

    private void handleProjectSelectionOption() {
            StringBuilder whereClause = null;
            boolean keepGoing = true;
            boolean isEmpty = false;
            int addFilter;

            while (keepGoing) {
                System.out.println("Enter 1 to specify filters, 0 for no filters: ");
                addFilter = readInteger(false);

                if (addFilter == 1) {
                    // loop to add filters (conditions)
                    while (true) {
                        appendCategory(whereClause);

                        appendArithmeticOperator(whereClause);

                        appendValue(whereClause);

                        // ask user whether to add more filters
                        System.out.println("Enter 1 to add more filters, otherwise 2");
                        int moreFilters = readInteger(false);

                        // get out of while loop
                        if (moreFilters == 2) {break;}

                        appendLogicalOperator(whereClause);
                    }
                } else {
                    isEmpty = true;
                }

                delegate.showProjectSelectionInfo(String.valueOf(whereClause), isEmpty);
            }
    }

    private void appendValue(StringBuilder whereClause) {
        input = new Scanner(System.in);
        input.useDelimiter("\\n");
        System.out.print("Enter value: ");
        String value = input.next();
        input.nextLine();

        whereClause.append(value);
    }

    private void appendLogicalOperator(StringBuilder whereClause) {
        System.out.println("\n");
        System.out.println("1 for AND");
        System.out.println("2 for OR");
        System.out.print("Choose a logical operator: ");
        int logicalOperator = readInteger(false);

        // append logical operator
        switch (logicalOperator) {
            case 1 -> whereClause.append("AND ");
            case 2 -> whereClause.append("OR ");
        }
    }

    private void appendArithmeticOperator(StringBuilder whereClause) {
        System.out.println("\n");
        System.out.println("1 for = ");
        System.out.println("2 for <> ");
        System.out.println("3 for < ");
        System.out.println("4 for > ");
        System.out.println("5 for LIKE ");
        System.out.print("Choose an arithmetic operator: ");
        int arithmeticOperator = readInteger(false);

        // append arithmetic operator
        switch (arithmeticOperator) {
            case 1 -> whereClause.append("= ");
            case 2 -> whereClause.append("<> ");
            case 3 -> whereClause.append("< ");
            case 4 -> whereClause.append("> ");
            case 5 -> whereClause.append("LIKE ");
        }
    }

    private void appendCategory(StringBuilder whereClause) {
        System.out.println("\n");
        System.out.println("1 for Project ID");
        System.out.println("2 for Title");
        System.out.println("3 for Budget");
        System.out.println("4 for Status");
        System.out.println("5 for Start Date");
        System.out.println("6 for End Date");
        System.out.print("Choose a category: ");
        int category = readInteger(false);

        // append attribute
        switch (category) {
            case 1 -> whereClause.append("projectID ");
            case 2 -> whereClause.append("title ");
            case 3 -> whereClause.append("budget ");
            case 4 -> whereClause.append("status ");
            case 5 -> whereClause.append("startDate ");
            case 6 -> whereClause.append("endDate: ");
        }
    }

    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }


    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    private void handleQuitOption() {
        System.out.println("Good Bye!");

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("IOException!");
            }
        }

        delegate.terminalTransactionsFinished();
    }
}

