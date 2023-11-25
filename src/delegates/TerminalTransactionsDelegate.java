package delegates;

public interface TerminalTransactionsDelegate {
    //public void execute();
    public void databaseSetup();
    public void showAverageBudgetOverStatus(int threshold);
    public void showProjectSelectionInfo(String whereClause, boolean isEmpty);

    public void terminalTransactionsFinished();
}
