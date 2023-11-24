package delegates;

public interface TerminalTransactionsDelegate {
    //public void execute();
    public void databaseSetup();
    public void showAverageBudgetOverStatus(int threshold);

    public void terminalTransactionsFinished();
}
